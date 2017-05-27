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
import dbquerries.ReportsQuery;
import dbquerries.ReturnsQuery;
import dbquerries.SupplierQuery;
import dbquerries.UtilitiesQuery;
import inventory.InventoryAdjustmentEntryController;
import inventory.InventoryItemController;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mgi.ManualGoodsIssueController;
import models.InventoryAdjustmentModel;
import models.MGIModel;
import models.PricesModel;
import models.ReturnAdjustmentModel;
import models.ReturnsModel;
import models.SupplierModel;
import models.UOMmodel;
import models.UserModel;
import models.WHSModel;
import prices.AddPriceViewController;
import prices.EditPriceViewController;
import purchases.PurchaseController;
import returns.ReturnsAdjustmentController;
import returns.ReturnsController;
import salesorder.SalesOrderController;
import supplier.SupplierController;
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
    private Button editreturnbtn;

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
    
    @FXML
    private TableView<InventoryAdjustmentModel> inventoryadjtable;

    @FXML
    private TableView<MGIModel> mgitable;

    @FXML
    private Button addmgibtn;

    @FXML
    private Button editiabtn;

    @FXML
    private Button viewmgibtn;

    @FXML
    private Button editmgibtn;

    @FXML
    private Button viewiabtn;
    
    @FXML
    private Tab customertab;

    @FXML
    private Tab suppliertab;

    @FXML
    private Tab inventorytab;

    @FXML
    private Tab returnstab;

    @FXML
    private Tab pricestab;

    @FXML
    private Tab reports;

    @FXML
    private Tab whstab;

    @FXML
    private Tab uomtab;
    
    @FXML
    private Button viewreturnadjbtn;

    @FXML
    private Button editreturnadjbtn;
    
    @FXML
    private ComboBox<String> reportsbox;
    
    @FXML
    private TableView<ReturnAdjustmentModel> returnadjtable;
    
    @FXML
    private TabPane bstchmpane;
    
    //Queries
    private final CustomerQuery cq = new CustomerQuery();
    private final UtilitiesQuery uq = new UtilitiesQuery();
    private final SupplierQuery sq = new SupplierQuery();
    private final InventoryQuery iq = new InventoryQuery();
    private final ReturnsQuery rq = new ReturnsQuery();
    private final ReportsQuery roq = new ReportsQuery();
    
    private final ArrayList<String> reportslist = new ArrayList();
    
    //ArrayLists
    private ArrayList<String> custnamelist;
    private ArrayList<String> custidlist = new ArrayList();
    
     /**
     * Initializes the controller class.
     **/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //TODO
        reportslist.add("Purchases Report");
        reportslist.add("Sales Order Report");
        reportslist.add("Sales Invoice Report");
        reportslist.add("Delivery Receipt Report");
        reportslist.add("Sales Return Summary Report");
        reportslist.add("Sales Return Detailed Report");
        reportslist.add("FG Inventory Summary Report");
        reportslist.add("FG Inventory Detailed Report");
        reportslist.add("FG Inventory Critical Stock Report");
        
        this.reportsbox.getItems().addAll(reportslist);
        
    }
    
    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        super.setType(type);
        useridfld.setText(String.valueOf(super.getGlobalUser().getId()));
        try {
            this.getCustomers();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bstchmpane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    switch(Integer.parseInt(newValue.toString())){
                        case 0 : getCustomers(); 
                            break;
                        case 1 : getSuppliers(); 
                            break;
                        case 2 : getInventory(); 
                                 getInventoryAdjustments();
                                 getMGI();
                            break;
                        case 3 : getReturns();
                                 getReturnsAdjustments();
                            break;
                        case 4 : getPrices();
                            break;
                        case 5 : //Reports
                            break;
                        case 6 : getWHS(); 
                            break;
                        case 7 : getUOMS();
                            break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }
    
    /**
     *  CUSTOMER SECTION
     *  1. Add Customer - DONE
     *      a. Add Contact - from list - DONE
     *      b. Delete Contact - from list - DONE
     *  2. Edit Customer
     *      a. Can add or delete contacts 
     *  3. View Customer
     *  4. View Transactions
     * 
     * 
     *  SUPPLIER SECTION
     *  1. Add Supplier
     *  2. Edit Supplier
     *  3. View Supplier
     *  4. View Purchases
     * 
     *  INVENTORY SECTION
     *  1. Add Inventory
     *  2. Edit Inventory - SKU or Description or UOM or WHS only
     *  3. Inventory Adjustment
     *  4. Manual Goods Issue
     * 
     *  RETURNS SECTION
     *  1. Add Returns
     *  2. Edit Returns
     *  3. Returns Adjustment
     *  4. Manual Goods Receipt
     * 
     *  PRICES SECTION
     *  1. Add Prices
     *  2. Edit Prices
     *  
     *  
     * 
     * 
     * @param event
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    
    //DONE
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
    
    //DONE
    @FXML
    public void editCustomer(ActionEvent event) throws IOException, SQLException {
        try{
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
        
            this.getCustomers();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Customer");

            alert.showAndWait();
        }
    }
    
    //DONE
    @FXML
    public void viewCustomerDetails(ActionEvent event) throws IOException, SQLException {
        try{
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
        }catch(NullPointerException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Customer");

            alert.showAndWait();
        }

    }

    @FXML
    public void viewTransactions(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesorder/SalesOrderView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            SalesOrderController soc = fxmlloader.<SalesOrderController>getController();
            soc.initData(this.getGlobalUser(), 0);
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
        }catch(NullPointerException e){
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Customer");

            alert.showAndWait();
        }
    }
    
    public void getCustomers() throws SQLException{
        System.out.println("TABLE BEFORE: " + super.getType());
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
    
    /**
     * END OF CUSTOMER SECTION
     */
    
    /**
     * SUPPLIER SECTION
     *  
     */
    
    @FXML
    public void addSupplier(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/supplier/SupplierView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        SupplierController sc = fxmlloader.<SupplierController>getController();
        sc.AddMode();
        sc.initData(null, super.getType());

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
    public void editSupplier(ActionEvent event) throws SQLException, IOException {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/supplier/SupplierView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            SupplierController sc = fxmlloader.<SupplierController>getController();
            sc.initData(null, super.getType());
            sc.EditMode(this.suppliertable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) this.editsupbtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setResizable(false);
            substage.sizeToScene();
            substage.setTitle("Edit Supplier");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();
        
            this.getSuppliers();
        }catch(IOException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Supplier");

            alert.showAndWait();
        }
    }

    @FXML
    public void viewSupplier(ActionEvent event) throws IOException, SQLException {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/supplier/SupplierView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            SupplierController sc = fxmlloader.<SupplierController>getController();
            sc.initData(null, super.getType());
            sc.ViewMode(this.suppliertable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) this.viewsupbtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setResizable(false);
            substage.sizeToScene();
            substage.setTitle("View Supplier");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();
        
            this.getSuppliers();
        }catch(IOException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Supplier");

            alert.showAndWait();
        }
    }
    
    @FXML
    public void viewPurchases(ActionEvent event) throws IOException {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/purchases/PurchaseView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            PurchaseController poc = fxmlloader.<PurchaseController>getController();
            poc.initData(this.getGlobalUser(), super.getType());
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
        }catch(NullPointerException e){
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Supplier");

            alert.showAndWait();
        }
    }
    
    public void getSuppliers() throws SQLException{
        
        System.out.println("TABLE BEFORE: " + super.getType());
        Iterator rs = sq.getAllSuppliers(super.getType());
        String[] arr = {"supid", "supname", "suptin", "supaddress"};
        
        ObservableList<SupplierModel> data
                = FXCollections.observableArrayList();
        
        
        while(rs.hasNext()){
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
    
    /**
     * END OF SUPPLIER SECTION 
     */
    
    /**
     * INVENTORY SECTION 
     */
    
    @FXML
    public void addInventory(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryItemView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        InventoryItemController iic = fxmlloader.<InventoryItemController>getController();
        
        iic.AddMode();
        iic.initData(null, super.getType());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addInventorybtn.getScene().getWindow();
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
    public void editInventory(ActionEvent event) throws IOException, SQLException {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryItemView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            InventoryItemController iic = fxmlloader.<InventoryItemController>getController();

            iic.EditMode(this.inventorytable.getSelectionModel().getSelectedItem());
            iic.initData(null, super.getType());

            Scene scene = new Scene(root);
            Stage stage = (Stage) editInventorybtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setResizable(false);
            substage.sizeToScene();
            substage.setTitle("Edit Inventory");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();

            this.getInventory();
            this.getInventory();
        }catch(NullPointerException e){
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select an inventory");

            alert.showAndWait();
        }
    }

    @FXML
    public void inventoryAdjustment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryAdjustmentEntry.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        InventoryAdjustmentEntryController iaec = fxmlloader.<InventoryAdjustmentEntryController>getController();
        iaec.initData(null, super.getType());
        iaec.AddMode();
        
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
        
        this.getInventoryAdjustments();
    }

    @FXML
    void editAdjustment(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryAdjustmentEntry.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        InventoryAdjustmentEntryController iaec = fxmlloader.<InventoryAdjustmentEntryController>getController();
        iaec.initData(null, super.getType());
        iaec.Edit(this.inventoryadjtable.getSelectionModel().getSelectedItem());
        
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
        
        this.getInventoryAdjustments();
    }

    @FXML
    void viewAdjustment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryAdjustmentEntry.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        InventoryAdjustmentEntryController iaec = fxmlloader.<InventoryAdjustmentEntryController>getController();
        iaec.initData(null, super.getType());
        iaec.ViewMode(this.inventoryadjtable.getSelectionModel().getSelectedItem());
        
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
        
        this.getInventory();
        this.getInventoryAdjustments();
    }
    
    @FXML
    public void addMGI(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mgi/ManualGoodsIssue.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ManualGoodsIssueController mgic = fxmlloader.<ManualGoodsIssueController>getController();
        mgic.initData(super.getGlobalUser(), super.getType());
        mgic.AddMode();
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) iabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Manual Goods Issue");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getMGI();
    }

    @FXML
    void editMGI(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mgi/ManualGoodsIssue.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ManualGoodsIssueController mgic = fxmlloader.<ManualGoodsIssueController>getController();
        mgic.initData(super.getGlobalUser(), super.getType());
        mgic.EditMode(this.mgitable.getSelectionModel().getSelectedItem());
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) iabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Edit Manual Goods Issue");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getMGI();
    }

    @FXML
    void viewMGI(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mgi/ManualGoodsIssue.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ManualGoodsIssueController mgic = fxmlloader.<ManualGoodsIssueController>getController();
        mgic.initData(super.getGlobalUser(), super.getType());
        mgic.ViewMode(this.mgitable.getSelectionModel().getSelectedItem());
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) iabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("View Manual Goods Issue");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getMGI();
    }
    
    public void getMGI() throws SQLException{
        
        String[] arr = {"ginum", "gidte", "custname", "pgistat"};
        ObservableList<MGIModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = iq.getMGI(super.getType());
        
        while(rs.hasNext()){
            data.add((MGIModel)rs.next());
        }
        
        ObservableList<TableColumn<MGIModel, ?>> olist;
        olist = (ObservableList<TableColumn<MGIModel, ?>>) this.mgitable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.mgitable.setItems(data);
    }
    
    public void getInventory() throws SQLException{
        String[] arr = {"sku", "description", "uom", "wh", "soh", "csl"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = iq.getInventories(super.getType());
        
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
        inventorytable.getItems().clear();
        inventorytable.setItems(data);
    }
    
    public void getInventoryAdjustments() throws SQLException{
        String[] arr = {"iamid", "iam_dte", "desc", "refnum", "pgistat"};
        ObservableList<InventoryAdjustmentModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = iq.getInventoryAdjustments(super.getType());
        
        while(rs.hasNext()){
            data.add((InventoryAdjustmentModel)rs.next());
        }
        
        ObservableList<TableColumn<InventoryAdjustmentModel, ?>> olist;
        olist = (ObservableList<TableColumn<InventoryAdjustmentModel, ?>>) this.inventoryadjtable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.inventoryadjtable.setItems(data);
    }
    
    /**
     * END OF INVENTORY SECTION 
     */
    
    /**
     * RETURNS SECTION 
     */
    
    @FXML
    public void addReturns(ActionEvent event) throws SQLException, IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ReturnsController rc = fxmlloader.<ReturnsController>getController();
        rc.AddMode();
        rc.initData(null, super.getType());
        
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
    public void editReturns(ActionEvent event) throws IOException, SQLException {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            ReturnsController rc = fxmlloader.<ReturnsController>getController();
            rc.EditMode(this.returntable.getSelectionModel().getSelectedItem());
            rc.initData(null, super.getType());

            Scene scene = new Scene(root);
            Stage stage = (Stage) this.addreturnbtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setResizable(false);
            substage.sizeToScene();
            substage.setTitle("Edit Returns");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();

            this.getReturns();
        }catch(NullPointerException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a returns adjustment");

            alert.showAndWait();
        }
        

    }
    
    @FXML
    public void returnsAdjustment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsAdjustmentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ReturnsAdjustmentController rac = fxmlloader.<ReturnsAdjustmentController>getController();
        rac.initData(null, super.getType());
        rac.AddMode();
        
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
        
        this.getReturnsAdjustments();
    }
    
    @FXML
    void editReturnAdjustment(ActionEvent event) throws IOException, SQLException {
        if(this.returnadjtable.getSelectionModel().getSelectedItem().getPgistat().equals("N")){
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsAdjustmentView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            ReturnsAdjustmentController rac = fxmlloader.<ReturnsAdjustmentController>getController();
            rac.initData(null, super.getType());
            rac.EditMode(this.returnadjtable.getSelectionModel().getSelectedItem());

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

            this.getReturnsAdjustments();
        }
    }

    @FXML
    void viewReturnAdjustment(ActionEvent event) throws IOException, SQLException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsAdjustmentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ReturnsAdjustmentController rac = fxmlloader.<ReturnsAdjustmentController>getController();
        rac.initData(null, super.getType());
        rac.ViewMode(this.returnadjtable.getSelectionModel().getSelectedItem());
        
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
    
    //DONE
    public void getReturns() throws SQLException{
        
        String[] arr = {"sku", "skudesc", "retuom", "retwhs", "soh"};
        ObservableList<ReturnsModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = rq.getReturns(super.getType());
        
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
        this.returntable.getItems().clear();
        this.returntable.setItems(data);
    }
    
    //DONE
    public void getReturnsAdjustments() throws SQLException{
        
        String[] arr = {"ramid", "ramdte", "refnum", "desc", "pgistat"};
        ObservableList<ReturnAdjustmentModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = rq.getReturnAdjustments(super.getType());
        
        while(rs.hasNext()){
            data.add((ReturnAdjustmentModel)rs.next());
        }
        
        ObservableList<TableColumn<ReturnAdjustmentModel, ?>> olist;
        olist = (ObservableList<TableColumn<ReturnAdjustmentModel, ?>>) this.returnadjtable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
    
        this.returnadjtable.setItems(data);
    }
    
    /**
     * END OF RETURNS SECTION 
     */
    
    /**
     * Improvements: Check for Empty values
     * PRICE SECTION 
     */
    
    //DONE
    @FXML
    public void addPrice(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/prices/AddPriceView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        AddPriceViewController apvc = fxmlloader.<AddPriceViewController>getController();
        
        apvc.initData(null, super.getType());

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
    
    //DONE
    @FXML
    public void editPrice(ActionEvent event) throws SQLException, IOException {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/prices/EditPriceView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            EditPriceViewController epvc = fxmlloader.<EditPriceViewController>getController();

            epvc.initData(null, super.getType());
            epvc.setPrice(this.pricetable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) addPricebtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setResizable(false);
            substage.sizeToScene();
            substage.setTitle("Edit Prices");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();

            this.getPrices();
        }catch(NullPointerException e){
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Price");

            alert.showAndWait();
        }
    }
    
    //DONE
    public void getPrices() throws SQLException{
        String[] arr = {"sku", "skudesc", "poprice", "sellingprice", "skuom", "effdte"};
        ObservableList<PricesModel> data = FXCollections.observableArrayList();
        Iterator rs = iq.getAllPrices(super.getType());
        
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
    
    /**
     * DONE!!
     * UTILITIES SECTION 
     */
    
    //DONE
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
    
    //DONE
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
    
    //DONE
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
    
    //DONE
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
    
    //DONE
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
    
    
    //DONE
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
    
    /**
     * END OF UTILITIES SECTION
     */
    
    /**
     * REPORTS SECTION
     */
    
    @FXML
    private Button exportbtn;

    @FXML
    private DatePicker todte;

    @FXML
    private DatePicker fromdte;

    @FXML
    void exportReport(ActionEvent event) throws SQLException, IOException {
        
        String report = this.reportsbox.getSelectionModel().getSelectedItem();
        
        switch(report){
            case "Purchases Report" : 
                roq.POReport(Date.valueOf(this.fromdte.getValue()), Date.valueOf(this.todte.getValue())); 
                break;
            case "Sales Order Report" :  
                roq.SOReport(Date.valueOf(this.fromdte.getValue()), Date.valueOf(this.todte.getValue())); 
                break;
            case "Sales Invoice Report" :  
                roq.SIReport(Date.valueOf(this.fromdte.getValue()), Date.valueOf(this.todte.getValue())); 
                break;
            case "Delivery Receipt Report" :  
                roq.DRReport(Date.valueOf(this.fromdte.getValue()), Date.valueOf(this.todte.getValue())); 
                break;
            case "Sales Return Summary Report" :  
                roq.SRSummaryReport(); 
                break;
            case "Sales Return Detailed Report" :  
                roq.SRDetailedReport(); 
                break;
            case "FG Inventory Summary Report" :  
                roq.InventorySummaryReport(); 
                break;
            case "FG Inventory Detailed Report" :  
                roq.InventoryDetailedReport(); 
                break;
            case "FG Inventory Critical Stock Report" :  
                roq.InventoryCriticalStockReport(); 
                break;
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Report Created please see My Document/Reports");

        alert.showAndWait();

    }
}
