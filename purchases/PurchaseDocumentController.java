/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases;

import best_chem.AbstractController;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private ComboBox<String> contactbx;

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
    private TextField poidfld;
    
    private SupplierModel supplier;
    
    private final SupplierQuery supq = new SupplierQuery();
    
    private final PurchasesQuery pq = new PurchasesQuery();
    
    private ArrayList<PurchaseItemModel> items;

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
            PurchaseItemModel poitem = new PurchaseItemModel();
            InventoryModel item = poic.getItem();
            poitem.setIdinventory(item.getIdinventory());
            poitem.setDesc(item.getDescription());
            poitem.setQty(poic.getQty());
            poitem.setSku(item.getSku());
            poitem.setUom(item.getUom());
            poitem.setUprice(item.getPoprice());
            
            double amount = item.getPoprice() * poic.getQty();
            
            double vatVal = 1 + (12 / 100);
            double vat = amount * vatVal;
            vat = Math.round(vat * 100.0)/100.0;
            System.out.println(vat);
            
            poitem.setAmount(amount);
            poitem.setVat(vat);
            
            items.add(poitem);
            
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

    }

    @FXML
    void DeleteItem(ActionEvent event) {

    }

    @FXML
    void ResetItems(ActionEvent event) {

    }

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        PurchasesModel pomod = new PurchasesModel();
        
        pomod.setSup_id(this.supplier.getSupid());
        pomod.setSupcname(this.contactbx.getSelectionModel().getSelectedItem());
        pomod.setPo_dte(Date.valueOf(this.datefld.getValue().toString()));
        pomod.setPo_dr_dte(Date.valueOf(this.drdatefld.getValue().toString()));
        pomod.setCbyid(super.getGlobalUser().getId());
        
        pomod.setPurchases(this.items);
        
        pq.addPurchases(pomod, super.getType());
        
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
    }
    
    public void EditMode(PurchasesModel purchase){
        
    }
    
    public void ViewMode(PurchasesModel purchase) throws SQLException{
        
        this.poidfld.setDisable(true);
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
        this.contactbx.getSelectionModel().select(purchase.getSupcname());
        
        Iterator iterate = pq.getPurchaseOrderItems(purchase.getSup_id(), super.getType());
        
        while(iterate.hasNext()){
            HashMap map = (HashMap) iterate.next();
            
            
        }
        
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
        
        ArrayList<SupplierContactModel> supcon = new ArrayList();
        try {
            supcon = supq.getContacts(this.supplier.getSupid(), super.getType());
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(SupplierContactModel sup: supcon){
            this.contactbx.getItems().add(sup.getSupcname());
        }
        
        this.contactbx.getSelectionModel().selectFirst();
    }
    
}
