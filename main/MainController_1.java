package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import dbquerries.ReturnsQuery;
import dbquerries.SupplierQuery;
import dbquerries.UtilitiesQuery;
import inventory.InventoryAdjustmentEntryController;
import inventory.InventoryItemController;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.InventoryAdjustmentModel;
import models.InventoryModel;
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
import supplier.SupplierController;
import utilities.UOMController;
import utilities.WHSController;

public class MainController_1 extends AbstractController implements Initializable {

    @FXML
    private Button adduombtn;

    @FXML
    private Button viewPurchases;

    @FXML
    private Button viewreturnbtn;

    @FXML
    private Button returnsabtn;

    @FXML
    private TableView<SupplierModel> suppliertable;

    @FXML
    private Button editreturnbtn;

    @FXML
    private Button editwhsbtn;

    @FXML
    private Button addwhsbtn;

    @FXML
    private Button edituombtn;

    @FXML
    private TableView<PricesModel> pricetable;

    @FXML
    private TableView<WHSModel> whstable;

    @FXML
    private Button iabtn;

    @FXML
    private Button addreturnbtn;

    @FXML
    private Button addPricebtn;

    @FXML
    private Button editInventorybtn;

    @FXML
    private TableView<ReturnsModel> returntable;

    @FXML
    private TableView<UOMmodel> uomtable;

    @FXML
    private TableView<InventoryModel> inventorytable;

    @FXML
    private Button viewsupbtn;

    @FXML
    private Button addInventorybtn;

    @FXML
    private Text useridfld;

    @FXML
    private Button addsupbtn;

    @FXML
    private Button editPricebtn;

    @FXML
    private Button editsupbtn;
    
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
    private TableView<ReturnAdjustmentModel> returnadjtable;
    
    private final UtilitiesQuery uq = new UtilitiesQuery();
    private final SupplierQuery sq = new SupplierQuery();

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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Supplier");

            alert.showAndWait();
        }
    }
    
    public void getSuppliers() throws SQLException{
        
        Iterator rs = sq.getAllSuppliers(super.getType());
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
    public void viewPurchases(ActionEvent event) {
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
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select A Supplier");

            alert.showAndWait();
        }
    }

    @FXML
    public void addInventory(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryItemView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        InventoryItemController iic = fxmlloader.<InventoryItemController>getController();
        System.out.println("BEFORE: " + super.getType());
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
    }

    @FXML
    public void inventoryAdjustment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryAdjustmentEntry.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        InventoryAdjustmentEntryController iaec = fxmlloader.<InventoryAdjustmentEntryController>getController();
        iaec.initData(null, super.getType());
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.iabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Inventory Adjustment Entry");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getReturnsAdjustments();
    }
    
    @FXML
    void editReturnAdjustment(ActionEvent event) {

    }

    @FXML
    void viewReturnAdjustment(ActionEvent event) {

    }
    
    @FXML
    void addMGI(ActionEvent event) {

    }

    @FXML
    void editAdjustment(ActionEvent event) {

    }

    @FXML
    void viewAdjustment(ActionEvent event) {

    }

    @FXML
    void editMGI(ActionEvent event) {

    }

    @FXML
    void viewMGI(ActionEvent event) {

    }
    
    public void getInventory() throws SQLException{
        InventoryQuery iq = new InventoryQuery();
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
        inventorytable.setItems(data);
    }

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
    public void editReturns(ActionEvent event) {

    }

    @FXML
    public void viewReturns(ActionEvent event) {

    }
    
    @FXML
    public void returnsAdjustment(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsAdjustmentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ReturnsAdjustmentController rac = fxmlloader.<ReturnsAdjustmentController>getController();
        rac.initData(null, super.getType());
        
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
    
    public void getReturns() throws SQLException{
        ReturnsQuery rq = new ReturnsQuery();
        
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
        this.returntable.setItems(data);
    }
    
    public void getReturnsAdjustments() throws SQLException{
        
        ReturnsQuery rq = new ReturnsQuery();
        
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

    @FXML
    public void editPrice(ActionEvent event) throws IOException, SQLException {
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
        substage.setTitle("Add Prices");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getPrices();

    }
    
     @FXML
    public void viewPrice(ActionEvent event) {
        System.out.println(this.pricetable.getSelectionModel().getSelectedItem().getIdprices());
    }
    
    public void getPrices() throws SQLException{
        InventoryQuery iq = new InventoryQuery();
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

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        super.setType(type);
        useridfld.setText(String.valueOf(super.getGlobalUser().getId()));
        
        try {
             // TOD
             this.getUOMS();
             this.getWHS();
             this.getSuppliers();
             this.getInventory();
             this.getPrices();
             this.getReturns();
             this.getReturnsAdjustments();
             
         } catch (SQLException ex) {
             Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
