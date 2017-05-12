/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesorder;

import best_chem.AbstractController;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import customer.CustomerViewController;
import dbquerries.DeliveryReceiptsQuery;
import dbquerries.SalesInvoiceQuery;
import dbquerries.SalesOrderQuery;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CustomerModel;
import models.InventoryModel;
import models.SOItemModel;
import models.SalesOrderModel;
import models.UserModel;
import viewmodels.SOViewModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class TransactionItemsViewController extends AbstractController implements Initializable {

    @FXML
    private TableView<SOItemModel> itemlist;
    @FXML
    private Button addbtn;
    @FXML
    private Button editbtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button resetbtn;
    
    @FXML
    private Button vcbtn;

    @FXML
    private TextField soidfld;

    @FXML
    private DatePicker drdatefld;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField totalfld;

    @FXML
    private TextField cpofld;

    @FXML
    private DatePicker datefld;
    
    @FXML
    private Button pendingbtn;
    
    @FXML
    private TextField statfld;
    
    @FXML
    private Button cancelsobtn;
    
    @FXML
    private Label itemNum;
    
    @FXML
    private Button prntbtn;
    
    private boolean isEdit;
    
    private SOViewModel sovm1;
    
    private ArrayList<SOItemModel> itemsList = new ArrayList();
    private ArrayList<SOItemModel> deletedList = new ArrayList();
    private CustomerModel customer;
    
    private SalesOrderQuery soq = new SalesOrderQuery();
    private DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();
    private SalesInvoiceQuery siq = new SalesInvoiceQuery();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void AddMode(CustomerModel cust){
        this.isEdit = false;
        this.customer = cust;
        this.statfld.setText("open");
        this.statfld.setEditable(false);
        this.soidfld.setDisable(true);
        this.datefld.setValue(LocalDate.now());
        this.cancelsobtn.setDisable(true);
        this.prntbtn.setDisable(true);
    }
    
    public void EditMode(CustomerModel cust, SOViewModel somodel) throws SQLException{
        this.isEdit = true;
        this.sovm1 = somodel;
        this.customer = cust;
        this.statfld.setText(somodel.getStatus());
        this.statfld.setEditable(false);
        this.soidfld.setText(String.valueOf(somodel.getIdso()));
        this.soidfld.setEditable(false);
        this.cpofld.setText(somodel.getCustomerpo());
        this.datefld.setValue(LocalDate.parse(somodel.getSodate()));
        this.drdatefld.setValue(LocalDate.parse(somodel.getSodrdate()));
        
        this.cancelsobtn.setDisable(true);
        if(somodel.getStatus().equals("WithDR")){
            this.addbtn.setDisable(true);
            this.editbtn.setDisable(true);
            this.resetbtn.setDisable(true);
        }
        
        Iterator ir = soq.getSalesOrderItems(somodel.getIdso(), "OPEN");
        while(ir.hasNext()){
            HashMap map = (HashMap) ir.next();
            SOItemModel model = new SOItemModel(Integer.parseInt(map.get("inventory_idinventory").toString()));
            
            model.setSoitemid(Integer.parseInt(map.get("idsalesorderitem").toString()));
            model.setSku(map.get("sku").toString());
            model.setDesc(map.get("skudesc").toString());
            model.setQty(Integer.parseInt(map.get("ordrqty").toString()));
            model.setUprice(Double.parseDouble(map.get("unitprice").toString()));
            model.setUom(map.get("skuom").toString());
            model.setDiscnt(Double.parseDouble(map.get("discnt").toString()));
            model.setAmount(Double.parseDouble(map.get("amount").toString()));
            model.setVat(Double.parseDouble(map.get("vat").toString()));
            
            itemsList.add(model);
        }
        
        this.RefreshItems();
        this.itemNum.setText(String.valueOf(this.itemsList.size()));
        this.prntbtn.setDisable(true);
        this.computeTotal();
    }
    
    public void ViewMode(CustomerModel cust, SOViewModel somodel) throws SQLException{
        
        this.customer = cust;
        
        this.statfld.setText(somodel.getStatus());
        this.statfld.setEditable(false);
        
        this.soidfld.setText(String.valueOf(somodel.getIdso()));
        this.soidfld.setEditable(false);
        this.cpofld.setText(somodel.getCustomerpo());
        this.datefld.setValue(LocalDate.parse(somodel.getSodate()));
        this.datefld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(somodel.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.datefld.setValue(LocalDate.now());
        
        this.resetbtn.setDisable(true);
        this.addbtn.setDisable(true);
        this.editbtn.setDisable(true);
        this.deletebtn.setDisable(true);
        this.pendingbtn.setDisable(true);
        
        Iterator ir = soq.getSalesOrderItems(somodel.getIdso(), "OPEN");
        while(ir.hasNext()){
            HashMap map = (HashMap) ir.next();
            SOItemModel model = new SOItemModel(Integer.parseInt(map.get("inventory_idinventory").toString()));
            
            model.setSoitemid(Integer.parseInt(map.get("idsalesorderitem").toString()));
            model.setSku(map.get("sku").toString());
            model.setDesc(map.get("skudesc").toString());
            model.setQty(Integer.parseInt(map.get("ordrqty").toString()));
            model.setUprice(Double.parseDouble(map.get("unitprice").toString()));
            model.setUom(map.get("skuom").toString());
            model.setDiscnt(Double.parseDouble(map.get("discnt").toString()));
            model.setAmount(Double.parseDouble(map.get("amount").toString()));
            model.setVat(Double.parseDouble(map.get("vat").toString()));
            
            itemsList.add(model);
        }
        
        if(somodel.getStatus().equals("cancelled") || somodel.getStatus().equals("Patially Delivered") || somodel.getStatus().equals("complete")){
            this.cancelsobtn.setDisable(true);
        }
        
        this.RefreshItems();
        this.itemNum.setText(String.valueOf(this.itemsList.size()));
        this.computeTotal();
        
    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "desc", "qty", "uom", "uprice", "amount", "vat"};
        ObservableList<SOItemModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.itemsList.size(); i++){
            data.add(itemsList.get(i));
        }
        ObservableList<TableColumn<SOItemModel, ?>> olist = (ObservableList<TableColumn<SOItemModel, ?>>) itemlist.getColumns();
        
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        
        this.itemlist.setItems(data);
    }
    
    @FXML
    public void AddItem(ActionEvent event) throws IOException, SQLException {
        
        if(this.itemsList.size() != 26){
            
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesorder/SOItems.fxml"));
            Parent root = (Parent) fxmlloader.load();

            SOItemsController soic = fxmlloader.<SOItemsController>getController();

            Scene scene = new Scene(root);
            Stage stage = (Stage) addbtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setTitle("Add Sales Order");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();

            if(!soic.IsCancelled()){
                InventoryModel item = soic.getItem();
                SOItemModel soitem = new SOItemModel(item.getId());
                soitem.setSku(item.getSku());
                soitem.setDesc(item.getDescription());
                soitem.setUom(item.getUom());
                soitem.setQty(soic.getQty());
                soitem.setDiscnt(this.customer.getDiscount());
                soitem.setUprice(item.getSellprice());
                double amount = item.getSellprice() * soic.getQty();

                double vatVal = 1 + (this.customer.getVAT() / 100);
                double vat = amount * vatVal;
                vat = Math.round(vat * 100.0)/100.0;
                System.out.println(vat);

                soitem.setAmount(amount);
                soitem.setVat(vat);
                if(!itemsList.contains(soitem)){
                    System.out.println("Nope");
                    itemsList.add(soitem);
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("You have already selected this item.");

                    alert.showAndWait();
                }
                
                this.itemNum.setText(String.valueOf(itemsList.size()));
                this.RefreshItems();
                this.computeTotal();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You have exceeded the maximum line items.");

            alert.showAndWait();
        }
        
    }
    
    public void computeTotal(){
        
        double total = 0.0;
        
        for(SOItemModel model : this.itemsList){
            total += model.getAmount();
        }
        
        this.totalfld.setText(String.valueOf(total));
    }

    @FXML
    public void EditItem(ActionEvent event) {
        
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

            double vatVal = 1 + (this.customer.getVAT() / 100);
            double vat = amount * vatVal;
            vat = Math.round(vat * 100.0)/100.0;
            System.out.println(vat);
          
            this.itemsList.get(this.itemlist.getSelectionModel().getSelectedIndex()).setQty(Integer.valueOf(result.get()));
            this.itemsList.get(this.itemlist.getSelectionModel().getSelectedIndex()).setAmount(amount);
            this.itemsList.get(this.itemlist.getSelectionModel().getSelectedIndex()).setVat(vat);
            this.itemlist.getItems().clear();
            this.RefreshItems();
            this.computeTotal();
        }
    }

    @FXML
    public void DeleteItem(ActionEvent event) {
        
        if(isEdit){
            if(!this.itemsList.isEmpty()){
                this.deletedList.add(this.itemsList.remove(this.itemlist.getSelectionModel().getFocusedIndex()));
                this.computeTotal();
                this.RefreshItems();
            }
        }else{
            if(!this.itemsList.isEmpty()){
                this.itemsList.remove(this.itemlist.getSelectionModel().getSelectedItem());
                this.computeTotal();
                this.RefreshItems();
            }
        }
        
        
    }

    @FXML
    public void ResetItems(ActionEvent event) {
        
        if(isEdit){
            if(!this.itemsList.isEmpty()){
                for(SOItemModel mod : this.itemsList){
                    this.deletedList.add(mod);
                }
                this.itemsList.clear();
                this.totalfld.setText("0.0");
                this.RefreshItems();
            }
            
        }else{
            if(!this.itemsList.isEmpty()){
                this.itemsList.clear();
                this.totalfld.setText("0.0");
                this.RefreshItems();
            }
        }

    }
    
    @FXML
    void cancelSO(ActionEvent event) throws SQLException {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        String s = "Cancel this sales order?";
        alert.setContentText(s);
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            soq.cancelSalesOrder(Integer.parseInt(this.soidfld.getText()), Integer.parseInt(this.customer.getIdcustomer()), "cancelled", "DELETED");
            stage.close();
        }
    }
    
    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        SalesOrderModel somodel = new SalesOrderModel();
        
        somodel.setCustid(Integer.valueOf(customer.getIdcustomer()));
        somodel.setCustomerpo(this.cpofld.getText());
        somodel.setSodate(Date.valueOf(this.datefld.getValue()));
        somodel.setDeliverydate(Date.valueOf(this.drdatefld.getValue()));
        somodel.setSoItems(itemsList);
        
        if(isEdit){
            somodel.setSoid(Integer.valueOf(this.soidfld.getText()));
            ArrayList<SOItemModel> models = new ArrayList();
            for(SOItemModel mod: this.itemsList){
                if(mod.getSoitemid() == null){
                    models.add(mod);
                }
            }
            soq.editSalesOrder(somodel);
            if(!this.itemsList.isEmpty()){
                soq.editSalesOrderItems(itemsList.iterator(), somodel.getSoid());
            }
            if(!this.deletedList.isEmpty()){
                soq.deleteSalesOrdetItems(this.deletedList.iterator(), somodel.getSoid());
            }
            if(!models.isEmpty()){
                soq.addSalesOrderItems(models.iterator(), somodel.getSoid());
            }
            
            
        }else{
            if(!this.itemsList.isEmpty()){
                soq.addSalesOrder(somodel);
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("No items have been selected.");

                alert.showAndWait();
            }
            
        }

        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
    }
    
    @FXML
    void viewCustomer(ActionEvent event) throws SQLException, IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/customer/CustomerView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        CustomerViewController cvc = fxmlloader.<CustomerViewController>getController();
        cvc.ViewMode(this.customer.getCompany());

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.vcbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("View Customer");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();

    }
    
    @FXML
    void Export(ActionEvent event) throws IOException {
        String currentUsersHomeDir = System.getProperty("user.home");
        File dir = new File(currentUsersHomeDir + "\\Documents\\Exports");
        if(!dir.exists()){
            System.out.println("Directory Created");
            dir.mkdir();
        }
        
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate.replaceAll("[\\s-:]",""));
        
        String filename = "[SALESORDER]" + this.customer.getCompany() + "(" + this.soidfld.getText() + ")_" + datefld.getValue().toString().replace("/", "") + "-" + formattedDate.replaceAll("[\\s-:]","") + ".pdf";
        File file = new File(dir.getAbsolutePath()+ "\\" + filename);
        if(!file.exists()){
            file.createNewFile();
        }
        PdfWriter writer = new PdfWriter(dir.getAbsolutePath()+ "\\" + filename);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        // Create a PdfFont
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        
        document.add(new Paragraph("").setFont(font));
        document.add(new Paragraph("\nSold To:" + this.customer.getCompany()
                + "\nBusiness: " + this.customer.getBusinessstyle()
                + "\nCustomer PO: " + this.cpofld.getText()
                + "\nAdddress: " + this.customer.getAddress()
        ).setFont(font));

        List list = new List()
            .setSymbolIndent(12)
            .setListSymbol("\u00a0")
            .setFont(font);

        for(int i = 0; i < this.itemsList.size(); i++){
            list.add(new ListItem(this.itemsList.get(i).getSku() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.itemsList.get(i).getDesc() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.itemsList.get(i).getQty() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.itemsList.get(i).getUprice() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.itemsList.get(i).getAmount() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + ""));
        }
       
        // Add the list
        list.add(new ListItem("\u00a0 \u00a0 \u00a0 \u00a0 **************Nothing Follows**************"));
        document.add(list.setFont(font));
        
        document.add(new Paragraph("Total: " + this.totalfld.getText()).setFont(font));
        
        document.close();

        System.out.println("HHHHHH");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please check the export in My Documents/Export");

        alert.showAndWait();
        
        Stage stage = (Stage) this.prntbtn.getScene().getWindow();
        stage.close();
    }
}
