package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import best_chem.AbstractController;
import models.CustomerModel;
import customer.CustomerViewController;
import dbquerries.CustomerQuery;
import dbquerries.InventoryQuery;
import dbquerries.ReturnsQuery;
import dbquerries.SupplierQuery;
import dbquerries.UtilitiesQuery;
import models.InventoryModel;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.PricesModel;
import models.ReturnsModel;
import models.SupplierModel;
import models.UOMmodel;
import models.UserModel;
import models.WHSModel;
import purchases.PurchaseController;
import salesorder.SalesOrderController;
import utilities.UOMController;
import utilities.WHSController;

public class MainController extends AbstractController implements Initializable {

    @FXML
    private Button editcustbtn;

    @FXML
    private Button addcustbtn;

    @FXML
    private TableView<CustomerModel> customertable;

    @FXML
    private Button viewcustomerbtn;

    @FXML
    private Button viewtransactbtn;
    
    @FXML
    private Text useridfld;
    
    @FXML
    private TableView<PricesModel> pricetable;

    @FXML
    private Button iabtn;

    @FXML
    private Button addPricebtn;

    @FXML
    private Button editInventorybtn;
    
    @FXML
    private TableView<InventoryModel> inventorytable;
     
    @FXML
    private Button addInventorybtn;
 
    @FXML
    private Button editPricebtn;
    
    @FXML
    private TableView<UOMmodel> uomtable;
    
    @FXML
    private Button adduombtn;

    @FXML
    private Button viewreturnbtn;

    @FXML
    private Button removereturnbtn;

    @FXML
    private Button editwhsbtn;

    @FXML
    private TableView<WHSModel> whstable;

    @FXML
    private TableView<ReturnsModel> returntable;

    @FXML
    private Button viewsupbtn;

    @FXML
    private Button addsupbtn;

    @FXML
    private TableView<?> purchasetable;

    @FXML
    private Button editsupbtn;

    @FXML
    private Button viewPurchases;

    @FXML
    private TableView<SupplierModel> suppliertable;

    @FXML
    private Button addwhsbtn;

    @FXML
    private Button edituombtn;

    @FXML
    private Button addreturnbtn;
    
    @FXML
    private Button returnsabtn;
    
    //Queries
    private final CustomerQuery cq = new CustomerQuery();
    private final UtilitiesQuery uq = new UtilitiesQuery();
    private final SupplierQuery sq = new SupplierQuery();
    
    //ArrayLists
    private ArrayList<String> custnamelist;
    private ArrayList<String> custidlist = new ArrayList();
    
     /**
     * Initializes the controller class.
     **/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
             // TODO
             this.getCustomers();
             this.getInventory();
             this.getPrices();
             
             this.getReturns();
             this.getUOMS();
             this.getWHS();
             this.getSuppliers();
             
         } catch (SQLException ex) {
             Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    /**
     *  CUSTOMER SECTION
     *  1. Add Customer - DONE
     *      a. Add Contact - from list - DONE
     *      b. Delete Contact - from list - DONE
     *  2. Edit Customer
     *      a. Ca
     *  3. View Customer
     * @param event
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    
    @FXML
    public void addCustomer(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/customer/CustomerView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        CustomerViewController mvc = fxmlloader.<CustomerViewController>getController();
        
        mvc.AddMode();
        Scene scene = new Scene(root);
        Stage stage = (Stage) addcustbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Add Customer");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getCustomers();

    }

    @FXML
    public void editCustomer(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/customer/CustomerView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        CustomerViewController cvc = fxmlloader.<CustomerViewController>getController();
        cvc.EditMode(customertable.getSelectionModel().getSelectedItem().getCompany());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addcustbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Edit Customer");
        substage.setResizable(false);
        substage.sizeToScene();
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();

    }

    @FXML
    public void viewCustomerDetails(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/customer/CustomerView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        CustomerViewController cvc = fxmlloader.<CustomerViewController>getController();
        cvc.ViewMode(customertable.getSelectionModel().getSelectedItem().getCompany());

        Scene scene = new Scene(root);
        Stage stage = (Stage) viewcustomerbtn.getScene().getWindow();
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
    public void viewTransactions(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesorder/SalesOrderView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            SalesOrderController soc = fxmlloader.<SalesOrderController>getController();
            soc.initData(this.getGlobalUser());
            soc.setCustomer(customertable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) viewtransactbtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setResizable(false);
            substage.sizeToScene();
            substage.setTitle("View Orders");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Customer");

            alert.showAndWait();
        }
    }
    
    @FXML
    public void viewPurchases(ActionEvent event) {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/purchases/PurchaseView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            PurchaseController poc = fxmlloader.<PurchaseController>getController();
            //poc.initData(this.getGlobalUser());
            poc.setSupplier(this.suppliertable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) this.viewPurchases.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setResizable(false);
            substage.sizeToScene();
            substage.setTitle("View Purchase Orders");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Supplier");

            alert.showAndWait();
        }
    }
    
    public void getCustomers() throws SQLException{
        String[] arr = {"idcustomer", "company", "tin", "address"};
        
        ObservableList<CustomerModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = cq.getCustomers();
        
        while(map.hasNext()){
            //System.out.println("HELLO");
            CustomerModel cust = (CustomerModel) map.next();
            System.out.println(cust.getCompany());
            data.add(cust);
        }
        
        ObservableList<TableColumn<CustomerModel, ?>> olist;
        olist = (ObservableList<TableColumn<CustomerModel, ?>>) customertable.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        customertable.setItems(data);
    }
    
    public void getInventory() throws SQLException{
        InventoryQuery iq = new InventoryQuery();
        String[] arr = {"sku", "description", "uom", "wh", "soh", "csl"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = iq.getInventories();
        
        while(rs.hasNext()){
            data.add((InventoryModel)rs.next());
        }
        
        ObservableList<TableColumn<InventoryModel, ?>> olist;
        olist = (ObservableList<TableColumn<InventoryModel, ?>>) inventorytable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        inventorytable.setItems(data);
    }
    
    public void getPrices() throws SQLException{
        InventoryQuery iq = new InventoryQuery();
        String[] arr = {"sku", "skudesc", "poprice", "sellingprice", "skuom", "effdte"};
        ObservableList<PricesModel> data = FXCollections.observableArrayList();
        Iterator rs = iq.getAllPrices();
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            PricesModel model = new PricesModel();
            
            model.setIdprices(Integer.parseInt(map.get("idPrices").toString()));
            model.setIdinventory(Integer.parseInt(map.get("idinventory").toString()));
            model.setSku(map.get("sku").toString());
            model.setSkudesc(map.get("skudesc").toString());
            model.setPoprice(Double.parseDouble(map.get("poPrice").toString()));
            model.setSellingprice(Double.parseDouble(map.get("sellingPrice").toString()));
            model.setSkuom(map.get("skuom").toString());
            model.setEffdte(Date.valueOf(map.get("effectivedte").toString()));
            data.add(model);
        }
        ObservableList<TableColumn<PricesModel, ?>> olist;
        olist = (ObservableList<TableColumn<PricesModel, ?>>) pricetable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        pricetable.setItems(data);
    }
    
    public void getReturns() throws SQLException{
        ReturnsQuery rq = new ReturnsQuery();
        
        String[] arr = {"sku", "skudesc", "retuom", "retwhs", "soh"};
        ObservableList<ReturnsModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = rq.getReturns();
        
        while(rs.hasNext()){
            data.add((ReturnsModel)rs.next());
        }
        
        ObservableList<TableColumn<ReturnsModel, ?>> olist;
        olist = (ObservableList<TableColumn<ReturnsModel, ?>>) this.returntable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.returntable.setItems(data);
    }
    
    @FXML
    public void returnsAdjustment(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsAdjustmentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.returnsabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Returns Adjustment Entry");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();

    }
    
    public void getUOMS() throws SQLException{
        Iterator rs = uq.getUOM();
        String[] arr = {"model"};
        ObservableList<UOMmodel> data = FXCollections.observableArrayList();
        
        while(rs.hasNext()){
            String str = rs.next().toString();
            System.out.println(str);
            UOMmodel mod = new UOMmodel(str);
            mod.setHello(str);
            data.add(mod);
        }
        ObservableList<TableColumn<UOMmodel, ?>> olist;
        olist = (ObservableList<TableColumn<UOMmodel, ?>>) uomtable.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        uomtable.setItems(data);
    }
    
    public void getWHS() throws SQLException{
        Iterator rs = uq.getWHS();
        String[] arr = {"whsmod"};
        ObservableList<WHSModel> data = FXCollections.observableArrayList();
        
        while(rs.hasNext()){
            data.add(new WHSModel(rs.next().toString()));
        }
        ObservableList<TableColumn<WHSModel, ?>> olist;
        olist = (ObservableList<TableColumn<WHSModel, ?>>) whstable.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        whstable.setItems(data);
    }
    
    public void getSuppliers() throws SQLException{
        
        Iterator rs = sq.getAllSuppliers();
        String[] arr = {"supid", "supname", "suptin", "supaddress"};
        
        ObservableList<SupplierModel> data
                = FXCollections.observableArrayList();
        
        
        while(rs.hasNext()){
            //System.out.println("HELLO");
            SupplierModel sup = (SupplierModel) rs.next();
            System.out.println(sup.getSupname());
            data.add(sup);
        }
        
        ObservableList<TableColumn<SupplierModel, ?>> olist;
        olist = (ObservableList<TableColumn<SupplierModel, ?>>) this.suppliertable.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.suppliertable.setItems(data);
        
    }
    
    @FXML
    public void addInventory(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryItemView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) viewtransactbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Add Inventory");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getInventory();
    }

    @FXML
    public void editInventory(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryItemView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) viewtransactbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Edit Inventory");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
    }

    @FXML
    public void inventoryAdjustment(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryAdjustmentEntry.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) iabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Inventory Adjustment Entry");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
    }

    @FXML
    public void addPrice(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/prices/AddPriceView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addPricebtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Add Prices");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getPrices();
    }

    @FXML
    public void editPrice(ActionEvent event) {

    }
    
     @FXML
    public void viewPrice(ActionEvent event) {
        System.out.println(this.pricetable.getSelectionModel().getSelectedItem().getIdprices());
    }

    @Override
    public void initData(UserModel user) {
        super.setGlobalUser(user);
        useridfld.setText(String.valueOf(super.getGlobalUser().getId()));
    }

    @FXML
    public void addSupplier(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/supplier/SupplierView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addsupbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Add Supplier");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getSuppliers();
    }

    @FXML
    public void editSupplier(ActionEvent event) {

    }

    @FXML
    public void viewSupplier(ActionEvent event) {

    }

    @FXML
    public void addReturns(ActionEvent event) throws SQLException, IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.addreturnbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Add Returns");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getReturns();

    }

    @FXML
    public void removeReturns(ActionEvent event) {

    }

    @FXML
    public void viewReturns(ActionEvent event) {

    }

    @FXML
    public void addWHS(ActionEvent event) throws IOException, SQLException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/utilities/WHSView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        WHSController whs = fxmlloader.<WHSController>getController();
        whs.AddMode();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addwhsbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Add Warehouse");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getWHS();

    }

    @FXML
    public void editWHS(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/utilities/WHSView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        WHSController whs = fxmlloader.<WHSController>getController();
        //System.out.println(this.whstable.getSelectionModel().getSelectedItem().getWhsmod());
        whs.EditMode(this.whstable.getSelectionModel().getSelectedItem().getWhsmod());

        Scene scene = new Scene(root);
        Stage stage = (Stage) editwhsbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Edit Warehouse");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getWHS();
    }

    @FXML
    public void editUOM(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/utilities/UOMView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        UOMController uom = fxmlloader.<UOMController>getController();
        //System.out.println(this.uomtable.getSelectionModel().getSelectedItem().getModel());
        uom.EditMode(this.uomtable.getSelectionModel().getSelectedItem().getModel());

        Scene scene = new Scene(root);
        Stage stage = (Stage) edituombtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Edit UOM");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getUOMS();
    }

    @FXML
    public void addUOM(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/utilities/UOMView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        UOMController uom = fxmlloader.<UOMController>getController();
        uom.AddMode();

        Scene scene = new Scene(root);
        Stage stage = (Stage) adduombtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Add UOM");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getUOMS();
    }
}
