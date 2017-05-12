/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import dbquerries.PurchasesQuery;
import dbquerries.SupplierQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.InventoryModel;
import models.PurchaseItemModel;
import models.PurchasesModel;
import models.SupplierContactModel;
import models.SupplierModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class PurchaseDocumentController extends AbstractController implements Initializable {
    
    @FXML
    private TextField addressfld;

    @FXML
    private Button editbtn;

    @FXML
    private TextField compfld;

    @FXML
    private TextField tinfld;

    @FXML
    private TextField contactbx;

    @FXML
    private DatePicker drdatefld;

    @FXML
    private Button cancelbtn;

    @FXML
    private TableView<PurchaseItemModel> itemlist;

    @FXML
    private TextField bsnstylefld;

    @FXML
    private Button addbtn;

    @FXML
    private TextField totalfld;

    @FXML
    private Button deletebtn;

    @FXML
    private Button resetbtn;

    @FXML
    private TextField pymttermfld;

    @FXML
    private DatePicker datefld;

    @FXML
    private TextField idlfd;

    @FXML
    private Button pendingbtn;
    
    @FXML
    private Button printbtn;
    
    @FXML
    private Button pgrbtn;

    @FXML
    private TextField poidfld;
    
    private SupplierModel supplier;
    
    private final SupplierQuery supq = new SupplierQuery();
    
    private final PurchasesQuery pq = new PurchasesQuery();
    private final InventoryQuery iq = new InventoryQuery();
    
    private ArrayList<PurchaseItemModel> items;
    private ArrayList<PurchaseItemModel> deletedList = new ArrayList();
    
    private PurchasesModel pm;
    
    private boolean isEdit;

    @FXML
    public void AddItem(ActionEvent event) throws IOException, SQLException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/purchases/POItems.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        POItemsController poic = fxmlloader.<POItemsController>getController();
        poic.setType(super.getType());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Item");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        if(!poic.IsCancelled()){
            InventoryModel item = poic.getItem();
            PurchaseItemModel poitem = new PurchaseItemModel(item.getIdinventory());
            poitem.setDesc(item.getDescription());
            poitem.setQty(poic.getQty());
            poitem.setSku(item.getSku());
            poitem.setUom(item.getUom());
            poitem.setUprice(item.getPoprice());
            
            double amount = item.getPoprice() * poic.getQty();
            System.out.println(amount);
            double vatVal = 1 + (12.0 / 100.0);
            System.out.println(vatVal);
            double vat = amount * vatVal;
            vat = Math.round(vat * 100.0)/100.0;
            
            poitem.setAmount(amount);
            poitem.setVat(vat);
            System.out.println(amount);
            System.out.println(vat);
            
            if(!this.items.contains(poitem)){
                items.add(poitem);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("You have already selected this item.");

                alert.showAndWait();
            }
            
            this.RefreshItems();
            this.computeTotal();
            
        }

    }
    
    public void computeTotal(){
        
        double total = 0.0;
        
        for(PurchaseItemModel model : this.items){
            total += model.getAmount();
        }
        
        this.totalfld.setText(String.valueOf(total));
    }

    @FXML
    void EditItem(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Item Quantity");
        dialog.setHeaderText("Item: " + this.itemlist.getSelectionModel().getSelectedItem().getSku() + "-" 
                + this.itemlist.getSelectionModel().getSelectedItem().getDesc() + "\n" 
                + "Current Quantity: " + this.itemlist.getSelectionModel().getSelectedItem().getQty());

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            
            System.out.println(this.itemlist.getSelectionModel().getSelectedIndex());
            System.out.println(this.itemlist.getSelectionModel().getFocusedIndex());
            
            double amount = this.itemlist.getSelectionModel().getSelectedItem().getUprice() * Integer.valueOf(result.get());
            
            double vatVal = 1 + (12.0 / 100.0);
            double vat = amount * vatVal;
            vat = Math.round(vat * 100.0)/100.0;
            System.out.println(vat);
          
            this.items.get(this.itemlist.getSelectionModel().getSelectedIndex()).setQty(Integer.valueOf(result.get()));
            this.items.get(this.itemlist.getSelectionModel().getSelectedIndex()).setAmount(amount);
            this.items.get(this.itemlist.getSelectionModel().getSelectedIndex()).setVat(vat);
            this.itemlist.getItems().clear();
            this.RefreshItems();
        }
    }

    @FXML
    void DeleteItem(ActionEvent event) {
        if(isEdit){
            if(!this.items.isEmpty()){
                this.deletedList.add(this.items.remove(this.itemlist.getSelectionModel().getFocusedIndex()));
                this.computeTotal();
                this.RefreshItems();
            }
        }else{
            if(!this.items.isEmpty()){
                this.items.remove(this.itemlist.getSelectionModel().getSelectedItem());
                this.computeTotal();
                this.RefreshItems();
            }
        }
    }

    @FXML
    void ResetItems(ActionEvent event) {
        if(isEdit){
            if(!this.items.isEmpty()){
                for(PurchaseItemModel mod : this.items){
                    this.deletedList.add(mod);
                }
                this.items.clear();
                this.totalfld.setText("0.0");
                this.RefreshItems();
            }
            
        }else{
            if(!this.items.isEmpty()){
                this.items.clear();
                this.totalfld.setText("0.0");
                this.RefreshItems();
            }
        }
    }

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        PurchasesModel pomod = new PurchasesModel();
        
        pomod.setSup_id(this.supplier.getSupid());
        pomod.setSupcname(this.contactbx.getText());
        pomod.setPo_dte(Date.valueOf(this.datefld.getValue().toString()));
        pomod.setPo_dr_dte(Date.valueOf(this.drdatefld.getValue().toString()));
        pomod.setCbyid(super.getGlobalUser().getId());
        
        pomod.setPurchases(this.items);
        
        if(isEdit){
            pomod.setIdpurchases(this.pm.getIdpurchases());
            ArrayList<PurchaseItemModel> models = new ArrayList();
            for(PurchaseItemModel mod: this.items){
                if(mod.getIdpurchaseitem() == 0){
                    models.add(mod);
                }
            }
            
            pq.editPurchases(pm);
            if(!this.items.isEmpty()){
                pq.editPurchaseItems(this.items.iterator());
            }
            if(!this.deletedList.isEmpty()){
                pq.deletePurchaseItems(this.deletedList.iterator());
            }
            if(!models.isEmpty()){
                pq.addPurchaseItems(models.iterator(), this.pm.getIdpurchases(), super.getType());
            }
        }else{
            pq.addPurchases(pomod, super.getType());
        }
        
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "desc", "qty", "uom", "uprice", "amount", "vat"};
        ObservableList<PurchaseItemModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.items.size(); i++){
            data.add(items.get(i));
        }
        ObservableList<TableColumn<PurchaseItemModel, ?>> olist = (ObservableList<TableColumn<PurchaseItemModel, ?>>) itemlist.getColumns();
        
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        
        this.itemlist.setItems(data);
    }

    @FXML
    void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    public void setSupplier(SupplierModel supplier){
        this.supplier = supplier;
    }
    
    public void AddMode() throws SQLException{
        
        this.poidfld.setDisable(true);
        this.idlfd.setText(String.valueOf(this.supplier.getSupid()));
        this.idlfd.setEditable(false);
        this.compfld.setText(this.supplier.getSupname());
        this.compfld.setEditable(false);
        this.bsnstylefld.setText(this.supplier.getSupbustyp());
        this.bsnstylefld.setEditable(false);
        this.tinfld.setText(this.supplier.getSuptin());
        this.tinfld.setEditable(false);
        this.datefld.setValue(LocalDate.now());
        this.pymttermfld.setText(this.supplier.getSuppymttrm());
        this.pymttermfld.setEditable(false);
        this.addressfld.setText(this.supplier.getSupaddress());
        this.addressfld.setEditable(false);
        this.pgrbtn.setDisable(true);
    }
    
    public void EditMode(PurchasesModel purchase) throws SQLException{
        
        this.pm = purchase;
        this.isEdit = true;
        this.poidfld.setText(String.valueOf(purchase.getIdpurchases()));
        this.poidfld.setEditable(false);
        this.idlfd.setText(String.valueOf(this.supplier.getSupid()));
        this.idlfd.setEditable(false);
        this.compfld.setText(this.supplier.getSupname());
        this.compfld.setEditable(false);
        this.bsnstylefld.setText(this.supplier.getSupbustyp());
        this.bsnstylefld.setEditable(false);
        this.tinfld.setText(this.supplier.getSuptin());
        this.tinfld.setEditable(false);
        this.datefld.setValue(LocalDate.parse(purchase.getPo_dte().toString()));
        this.pymttermfld.setText(this.supplier.getSuppymttrm());
        this.pymttermfld.setEditable(false);
        this.addressfld.setText(this.supplier.getSupaddress());
        this.addressfld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(purchase.getPo_dr_dte().toString()));
        this.contactbx.setText(purchase.getSupcname());
        
        this.pgrbtn.setDisable(true);
        this.printbtn.setDisable(true);
        
        Iterator iterate = pq.getPurchaseOrderItems(purchase.getIdpurchases(), super.getType());
        
        while(iterate.hasNext()){
            HashMap map = (HashMap) iterate.next();
            
            PurchaseItemModel pim = new PurchaseItemModel(Integer.valueOf(map.get("idinventory").toString()));
            
            pim.setIdpurchaseitem(Integer.parseInt(map.get("idpurchaseitems").toString()));
            pim.setSku(map.get("sku").toString());
            pim.setDesc(map.get("skudesc").toString());
            pim.setQty(Integer.parseInt(map.get("po_qty").toString()));
            pim.setUom(map.get("skuom").toString());
            pim.setUprice(Double.parseDouble(map.get("unitprice").toString()));
            pim.setAmount(Double.parseDouble(map.get("amount").toString()));
            pim.setVat(Double.parseDouble(map.get("vat_amount").toString()));
            
            this.items.add(pim);
            
        }
        
        this.pgrbtn.setDisable(true);
        this.RefreshItems();
        this.computeTotal();
        
    }
    
    public void ViewMode(PurchasesModel purchase) throws SQLException{
        
        this.pm = purchase;
        this.poidfld.setText(String.valueOf(purchase.getIdpurchases()));
        this.poidfld.setEditable(false);
        this.idlfd.setText(String.valueOf(this.supplier.getSupid()));
        this.idlfd.setEditable(false);
        this.compfld.setText(this.supplier.getSupname());
        this.compfld.setEditable(false);
        this.bsnstylefld.setText(this.supplier.getSupbustyp());
        this.bsnstylefld.setEditable(false);
        this.tinfld.setText(this.supplier.getSuptin());
        this.tinfld.setEditable(false);
        this.datefld.setValue(LocalDate.parse(purchase.getPo_dte().toString()));
        this.pymttermfld.setText(this.supplier.getSuppymttrm());
        this.pymttermfld.setEditable(false);
        this.addressfld.setText(this.supplier.getSupaddress());
        this.addressfld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(purchase.getPo_dr_dte().toString()));
        this.contactbx.setText(purchase.getSupcname());
        
        Iterator iterate = pq.getPurchaseOrderItems(purchase.getIdpurchases(), super.getType());
        
        while(iterate.hasNext()){
            HashMap map = (HashMap) iterate.next();
            
            PurchaseItemModel pim = new PurchaseItemModel(Integer.valueOf(map.get("idinventory").toString()));
            
            pim.setIdpurchaseitem(Integer.parseInt(map.get("idpurchaseitems").toString()));
            pim.setSku(map.get("sku").toString());
            pim.setDesc(map.get("skudesc").toString());
            pim.setQty(Integer.parseInt(map.get("po_qty").toString()));
            pim.setUom(map.get("skuom").toString());
            pim.setUprice(Double.parseDouble(map.get("unitprice").toString()));
            pim.setAmount(Double.parseDouble(map.get("amount").toString()));
            pim.setVat(Double.parseDouble(map.get("vat_amount").toString()));
            
            this.items.add(pim);
            
        }
        
        this.pendingbtn.setDisable(true);
        this.addbtn.setDisable(true);
        this.deletebtn.setDisable(true);
        this.resetbtn.setDisable(true);
        this.RefreshItems();
        this.computeTotal();
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        this.items = new ArrayList();
    }    

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        super.setType(type);
    }

    @FXML
    void PostToInventory(ActionEvent event) throws SQLException {
        
        ArrayList<InventoryModel> mods = new ArrayList();
        for(int i = 0; i < this.items.size(); i++){
            InventoryModel mod = new InventoryModel(this.items.get(i).getIdinventory());
            
            mod.setSoh(this.items.get(i).getQty());
            mod.setMov("INC");
            
            mods.add(mod);
        }
        
        iq.PostUpdateInventory(mods.iterator());
        pq.PGItems(this.items.iterator(), this.pm.getIdpurchases());
    }

    @FXML
    void export(ActionEvent event) {

    }
    
}
