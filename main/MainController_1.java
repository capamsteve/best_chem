package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import dbquerries.PMInventoryQuery;
import dbquerries.ReportsQuery;
import dbquerries.ReturnsQuery;
import dbquerries.StockTransferQuery;
import dbquerries.StockTransmittalQuery;
import dbquerries.SupplierQuery;
import dbquerries.UtilitiesQuery;
import inventory.BOMViewController;
import inventory.InventoryAdjustmentEntryController;
import inventory.InventoryItemController;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mgi.ManualGoodsIssueController;
import mgr.ManualGoodsReceiptController;
import models.InventoryAdjustmentModel;
import models.InventoryModel;
import models.MGIModel;
import models.PricesModel;
import models.ReturnAdjustmentModel;
import models.ReturnsModel;
import models.StockTransferModel;
import models.StockTransmittalModel;
import models.SupplierModel;
import models.UOMmodel;
import models.UserModel;
import models.WHSModel;
import prices.PriceListViewController;
import mm_purchases.PurchaseController;
import models.BOModel;
import purchases.PurchaseOrderSelectorController;
import returns.ReturnsAdjustmentController;
import returns.ReturnsController;
import salesorder.SOItemsController;
import stocktransfer.StockTransferController;
import supplier.SupplierController;
import transmittal.StockTransmittalViewController;
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
    private ComboBox<String> reportsbox;

    @FXML
    private Button viewmgrbtn;

    @FXML
    private Button exportbtn;

    @FXML
    private Button editmgrbtn;

    @FXML
    private Button addmgrbtn;

    @FXML
    private Tab suppliertab1;

    @FXML
    private TableView<MGIModel> mgrtable;

    @FXML
    private Button viewprchbtn;

    @FXML
    private TableView<BOModel> pminventorytable;

    @FXML
    private Button viewbombtn;

    @FXML
    private Button viewprcbtn;

    @FXML
    private DatePicker todte;
    
    @FXML
    private TableView<InventoryModel> fginventorytable;

    @FXML
    private DatePicker fromdte;
    
    @FXML
    private TabPane mm_pane;

    @FXML
    private Button viewstbtn;

    @FXML
    private TableView<StockTransferModel> sttable;

    @FXML
    private Button addstbtn;

    @FXML
    private Button editstbtn;
    
        
    @FXML
    private Button editstmbtn;

    @FXML
    private Button viewstmbtn;

    @FXML
    private Button addstmbtn;
    
    @FXML
    private Button exportbombtn;

    @FXML
    private TextField searchpm2;

    @FXML
    private TextField searchpm1;
    
    @FXML
    private TabPane invtabs;
    
    @FXML
    private TabPane rettabs;

    @FXML
    private TableView<StockTransmittalModel> stmtable;

    @FXML
    private TableView<ReturnAdjustmentModel> returnadjtable;
    
    private final ArrayList<String> reportslist = new ArrayList();
    
    private final UtilitiesQuery uq = new UtilitiesQuery();
    private final SupplierQuery sq = new SupplierQuery();
    private final InventoryQuery iq = new InventoryQuery();
    private final ReturnsQuery rq = new ReturnsQuery();
    private final ReportsQuery roq = new ReportsQuery();
    private final StockTransferQuery stq = new StockTransferQuery();
    private final StockTransmittalQuery stq2 = new StockTransmittalQuery();
    private final PMInventoryQuery pim = new PMInventoryQuery();
    
    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        super.setType(type);
        useridfld.setText(String.valueOf(super.getGlobalUser().getId()));
        
        try {
            this.getSuppliers();
        } catch (SQLException ex) {
            Logger.getLogger(MainController_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.fginventorytable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                try {
                    this.getBOM(this.fginventorytable.getSelectionModel().getSelectedItem().getIdinventory());
                } catch (SQLException ex) {
                    Logger.getLogger(MainController_1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.searchpm1.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                try {
                    this.searchPM(searchpm1.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(InventoryModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.searchpm2.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                try {
                    this.searchSKU(searchpm2.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(MainController_1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.mm_pane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    switch(Integer.parseInt(newValue.toString())){
                        case 0 : getSuppliers(); 
                            break;
                        case 1 : getInventory();
                            break;
                        case 2 : getFGInventory();//BOM    
                                 
                            break;
                        case 3 : getReturns();
                            break;
                        case 4 :  
                            break;
                        case 5 : getWHS();
                            break;
                        case 6 : getUOMS();
                            break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        invtabs.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    switch(Integer.parseInt(newValue.toString())){
                        case 0 : getInventory();
                            break;
                        case 1 : getInventoryAdjustments();//PM INVENTORY 
                            break;
                        case 2 : getMGI();   
                            break;
                        case 3 : getMGR();
                            break;
                        case 4 :  getStockTransfers();
                            break;
                        case 5 : getStockTransmittals();
                            break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainController_1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        rettabs.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    switch(Integer.parseInt(newValue.toString())){
                        case 0 : getReturns();
                            break;
                        case 1 : getReturnsAdjustments();
                            break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportslist.add("Purchases Report");
        reportslist.add("PM Stock Transfer Report");
        reportslist.add("PM Return Summary Report");
        reportslist.add("PM Return Detailed Report");
        reportslist.add("PM Inventory Summary Report");
        reportslist.add("PM Inventory Detailed Report");
        reportslist.add("PM Inventory Critical Stock Report");
        reportslist.add("PM Stock Transfer Report");
        
        this.reportsbox.getItems().addAll(reportslist);
    }
    
    /**
     * 
     * SUPPLIER
     * 1. add
     * 2. edit
     * 3. view purchases
     * 4. view price list
     * 
     * PM INVENTORY
     * 
     * BOM
     * 
     * PM RETURNS
     * 
     * REPORTS
     * 
     * WHS
     * 
     * UOM
     * 
     * 
     */

    @FXML
    public void addSupplier(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/supplier/SupplierView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        SupplierController sc = fxmlloader.<SupplierController>getController();
        sc.AddMode();
        sc.initData(super.getGlobalUser(), super.getType());

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
            sc.initData(super.getGlobalUser(), super.getType());
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
            sc.initData(super.getGlobalUser(), super.getType());
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
    
    //DONE
    @FXML
    public void viewPurchases(ActionEvent event) {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mm_purchases/PurchaseView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            PurchaseController poc = fxmlloader.<PurchaseController>getController();
            poc.initData(this.getGlobalUser(), super.getType());
            poc.setSupplier(this.suppliertable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) this.viewprchbtn.getScene().getWindow();
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
    void viewPriceList(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/prices/PriceListView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        PriceListViewController plvc = fxmlloader.<PriceListViewController>getController();
        plvc.initData(super.getGlobalUser(), super.getType());
        plvc.setSupplier(this.suppliertable.getSelectionModel().getSelectedItem());
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.viewprchbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("View Price List");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
    }
    
    //DONE
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
    
    //DONE
    @FXML
    public void addInventory(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryItemView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        InventoryItemController iic = fxmlloader.<InventoryItemController>getController();
        iic.AddMode();
        iic.initData(super.getGlobalUser(), super.getType());

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
        iic.initData(super.getGlobalUser(), super.getType());

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
    void viewBOM(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/BOMView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        BOMViewController bvc = fxmlloader.<BOMViewController>getController();
        
        bvc.EditMode(this.fginventorytable.getSelectionModel().getSelectedItem());
        bvc.initData(super.getGlobalUser(), super.getType());

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
    }
    
    @FXML
    void exportBOM(ActionEvent event) throws IOException, SQLException {
        
        this.roq.BOM_REPORT();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please check My Documents/Reports");

        alert.showAndWait();
    }

    @FXML
    public void inventoryAdjustment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryAdjustmentEntry.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        InventoryAdjustmentEntryController iaec = fxmlloader.<InventoryAdjustmentEntryController>getController();
        iaec.AddMode();
        iaec.initData(super.getGlobalUser(), super.getType());
        
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
        
        this.getInventoryAdjustments();
    }
    
    @FXML
    void editAdjustment(ActionEvent event) throws SQLException, IOException {
        if(this.inventoryadjtable.getSelectionModel().getSelectedItem().getPgistat().equals("N")){
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryAdjustmentEntry.fxml"));
            Parent root = (Parent) fxmlloader.load();

            InventoryAdjustmentEntryController iaec = fxmlloader.<InventoryAdjustmentEntryController>getController();
            iaec.initData(super.getGlobalUser(), super.getType());
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
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("This has been posted already and cannot be edited.");

            alert.showAndWait();
        }
    }

    @FXML
    void viewAdjustment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/InventoryAdjustmentEntry.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        InventoryAdjustmentEntryController iaec = fxmlloader.<InventoryAdjustmentEntryController>getController();
        iaec.initData(super.getGlobalUser(), super.getType());
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
    void addMGI(ActionEvent event) throws IOException, SQLException {
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
        if(this.mgitable.getSelectionModel().getSelectedItem().getPgistat().equals("N")){
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
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("This has been posted already and cannot be edited.");

            alert.showAndWait();
        }
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
        this.getInventory();
    }
    
    @FXML
    void addMGR(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mgr/ManualGoodsReceipt.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ManualGoodsReceiptController mgic = fxmlloader.<ManualGoodsReceiptController>getController();
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
        
        this.getMGR();
    }

    @FXML
    void editMGR(ActionEvent event) throws SQLException, IOException {
        if(this.mgrtable.getSelectionModel().getSelectedItem().getPgistat().equals("N")){
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mgr/ManualGoodsReceipt.fxml"));
            Parent root = (Parent) fxmlloader.load();

            ManualGoodsReceiptController mgic = fxmlloader.<ManualGoodsReceiptController>getController();
            mgic.initData(super.getGlobalUser(), super.getType());
            mgic.EditMode(this.mgrtable.getSelectionModel().getSelectedItem());

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

            this.getMGR();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("This has been posted already and cannot be edited.");

            alert.showAndWait();
        }
    }

    @FXML
    void viewMGR(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mgr/ManualGoodsReceipt.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ManualGoodsReceiptController mgic = fxmlloader.<ManualGoodsReceiptController>getController();
        mgic.initData(super.getGlobalUser(), super.getType());
        mgic.ViewMode(this.mgrtable.getSelectionModel().getSelectedItem());
        
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
        
        this.getMGR();
        this.getInventory();
    }
    
    @FXML
    void addST(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/stocktransfer/StockTransfer.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        StockTransferController stc = fxmlloader.<StockTransferController>getController();
        stc.initData(super.getGlobalUser(), super.getType());
        stc.AddMode();
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) iabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Stock Transfer");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getStockTransfers();

    }

    @FXML
    void editST(ActionEvent event) throws IOException, SQLException {
        if(this.sttable.getSelectionModel().getSelectedItem().getStat().equals("OPEN")){
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/stocktransfer/StockTransfer.fxml"));
            Parent root = (Parent) fxmlloader.load();

            StockTransferController stc = fxmlloader.<StockTransferController>getController();
            stc.initData(super.getGlobalUser(), super.getType());
            stc.EditMode(this.sttable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) iabtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setResizable(false);
            substage.sizeToScene();
            substage.setTitle("Stock Transfer");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();
            
            this.getStockTransfers();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("This has been posted already and cannot be edited.");

            alert.showAndWait();
        }

    }

    @FXML
    void viewST(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/stocktransfer/StockTransfer.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        StockTransferController stc = fxmlloader.<StockTransferController>getController();
        stc.initData(super.getGlobalUser(), super.getType());
        stc.ViewMode(this.sttable.getSelectionModel().getSelectedItem());
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) iabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Stock Transfer");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getStockTransfers();

    }

    @FXML
    void addSTM(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/transmittal/StockTransmittalView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        StockTransmittalViewController stc = fxmlloader.<StockTransmittalViewController>getController();
        stc.initData(super.getGlobalUser(), super.getType());
        stc.AddMode();
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) iabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Stock Transfer");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getStockTransmittals();

    }

    @FXML
    void editSTM(ActionEvent event) throws IOException, SQLException {
        if(this.stmtable.getSelectionModel().getSelectedItem().getPgistat().equals("N")){
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/transmittal/StockTransmittalView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            StockTransmittalViewController stc = fxmlloader.<StockTransmittalViewController>getController();
            stc.initData(super.getGlobalUser(), super.getType());
            stc.EditMode(this.stmtable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) iabtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setResizable(false);
            substage.sizeToScene();
            substage.setTitle("Stock Transfer");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();

            this.getStockTransmittals();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("This has been posted already and cannot be edited.");

            alert.showAndWait();
        }
    }

    @FXML
    void viewSTM(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/transmittal/StockTransmittalView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        StockTransmittalViewController stc = fxmlloader.<StockTransmittalViewController>getController();
        stc.initData(super.getGlobalUser(), super.getType());
        stc.ViewMode(this.stmtable.getSelectionModel().getSelectedItem());
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) iabtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Stock Transfer");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getStockTransmittals();
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
        this.inventorytable.getItems().clear();
        this.inventorytable.setItems(data);
    }
    
    public void searchPM(String sku) throws SQLException{
        InventoryQuery iq = new InventoryQuery();
        String[] arr = {"sku", "description", "uom", "wh", "soh", "csl"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = iq.getInventories(sku, super.getType());
        
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
        this.inventorytable.getItems().clear();
        this.inventorytable.setItems(data);
    }
    
    public void getFGInventory() throws SQLException{
        InventoryQuery iq = new InventoryQuery();
        String[] arr = {"sku", "description", "uom", "wh", "soh", "csl"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = iq.getInventoriesByWH("BC",1);
        
        while(rs.hasNext()){
            data.add((InventoryModel)rs.next());
        }
        
        ObservableList<TableColumn<InventoryModel, ?>> olist;
        olist = (ObservableList<TableColumn<InventoryModel, ?>>) this.fginventorytable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.fginventorytable.getItems().clear();
        this.fginventorytable.setItems(data);
    }
    
    public void searchSKU(String sku) throws SQLException{
        InventoryQuery iq = new InventoryQuery();
        String[] arr = {"sku", "description", "uom", "wh", "soh", "csl"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = iq.getInventoriesByWH(sku, "BC",1);
        
        while(rs.hasNext()){
            data.add((InventoryModel)rs.next());
        }
        
        ObservableList<TableColumn<InventoryModel, ?>> olist;
        olist = (ObservableList<TableColumn<InventoryModel, ?>>) this.fginventorytable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.fginventorytable.getItems().clear();
        this.fginventorytable.setItems(data);
    }
    
    public void getBOM(int id) throws SQLException{
        String[] arr = {"sku", "description", "bom_qty1", "uom", "whouse",};
        ObservableList<BOModel> data
                = FXCollections.observableArrayList();
        
        Iterator ir = this.pim.getBOM(id);
        
        while(ir.hasNext()){
            BOModel bom = (BOModel) ir.next();
            bom.setBom_qty1();
            System.out.println("FROM DB: " + bom.getIdinventory());
            data.add(bom);
        }
        
        ObservableList<TableColumn<BOModel, ?>> olist;
        olist = (ObservableList<TableColumn<BOModel, ?>>) this.pminventorytable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.pminventorytable.getItems().clear();
        this.pminventorytable.setItems(data);
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
        this.inventoryadjtable.getItems().clear();
        this.inventoryadjtable.setItems(data);
    }
    
    public void getMGI() throws SQLException{
        String[] arr = {"ginum", "gidte", "custname", "pgistat"};
        ObservableList<MGIModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = iq.getMGI(super.getType(), 1);
        
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
        this.mgitable.getItems().clear();
        this.mgitable.setItems(data);
    }
    
    public void getMGR() throws SQLException{
        String[] arr = {"ginum", "gidte", "custname", "pgistat"};
        ObservableList<MGIModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = iq.getMGI(super.getType(), 2);
        
        while(rs.hasNext()){
            data.add((MGIModel)rs.next());
        }
        
        ObservableList<TableColumn<MGIModel, ?>> olist;
        olist = (ObservableList<TableColumn<MGIModel, ?>>) this.mgrtable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.mgrtable.getItems().clear();
        this.mgrtable.setItems(data);
    }
    
    public void getStockTransfers() throws SQLException{
        
        String[] arr = {"idst", "st_dte", "slto", "stat"};
        ObservableList<StockTransferModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = stq.getStockTransfers();
        
        while(rs.hasNext()){
            data.add((StockTransferModel)rs.next());
        }
        
        ObservableList<TableColumn<StockTransferModel, ?>> olist;
        olist = (ObservableList<TableColumn<StockTransferModel, ?>>) this.sttable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.sttable.getItems().clear();
        this.sttable.setItems(data);
    }
    
    public void getStockTransmittals() throws SQLException{
        
        String[] arr = {"stid", "gi_dte", "supname", "pgistat"};
        ObservableList<StockTransmittalModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = stq2.getTransmittals();
        
        while(rs.hasNext()){
            data.add((StockTransmittalModel)rs.next());
        }
        
        ObservableList<TableColumn<StockTransmittalModel, ?>> olist;
        olist = (ObservableList<TableColumn<StockTransmittalModel, ?>>) this.stmtable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.stmtable.getItems().clear();
        this.stmtable.setItems(data);
    }

    @FXML
    public void addReturns(ActionEvent event) throws SQLException, IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ReturnsController rc = fxmlloader.<ReturnsController>getController();
        rc.AddMode();
        rc.initData(super.getGlobalUser(), super.getType());
        
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
    public void editReturns(ActionEvent event) throws SQLException, IOException {
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            ReturnsController rc = fxmlloader.<ReturnsController>getController();
            rc.EditMode(this.returntable.getSelectionModel().getSelectedItem());
            rc.initData(super.getGlobalUser(), super.getType());

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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a returns adjustment");

            alert.showAndWait();
        }
    }
    
    @FXML
    public void returnsAdjustment(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsAdjustmentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ReturnsAdjustmentController rac = fxmlloader.<ReturnsAdjustmentController>getController();
        rac.initData(super.getGlobalUser(), super.getType());
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

    }
    
    @FXML
    void editReturnAdjustment(ActionEvent event) throws SQLException, IOException {
        if(this.returnadjtable.getSelectionModel().getSelectedItem().getPgistat().equals("N")){
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsAdjustmentView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            ReturnsAdjustmentController rac = fxmlloader.<ReturnsAdjustmentController>getController();
            rac.initData(super.getGlobalUser(), super.getType());
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
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("This has been posted already and cannot be edited.");

            alert.showAndWait();
        }
    }

    @FXML
    void viewReturnAdjustment(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnsAdjustmentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ReturnsAdjustmentController rac = fxmlloader.<ReturnsAdjustmentController>getController();
        rac.initData(super.getGlobalUser(), super.getType());
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
        
        this.getReturnsAdjustments();
        this.getReturns();
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
        this.returntable.getItems().clear();
        this.returntable.setItems(data);
    }
    
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
    
    @FXML
    void exportReport(ActionEvent event) throws SQLException, IOException {
        String report = this.reportsbox.getSelectionModel().getSelectedItem();
        reportslist.add("PM Stock Transfer Report");
        reportslist.add("PM Return Summary Report");
        reportslist.add("PM Return Detailed Report");
        reportslist.add("PM Inventory Summary Report");
        reportslist.add("PM Inventory Detailed Report");
        reportslist.add("PM Inventory Critical Stock Report");
        switch(report){
            case "Purchases Report" : 
                roq.MM_POReport(Date.valueOf(this.fromdte.getValue()), Date.valueOf(this.todte.getValue())); 
                break;
            case "PM Return Summary Report" :  
                roq.MM_SRSummaryReport(); 
                break;
            case "PM Return Detailed Report" :  
                roq.MM_SRDetailedReport(Date.valueOf(this.fromdte.getValue()), Date.valueOf(this.todte.getValue())); 
                break;
            case "PM Inventory Summary Report" :  
                roq.MM_InventorySummaryReport(); 
                break;
            case "PM Inventory Detailed Report" :  
                roq.MM_InventoryDetailedReport(Date.valueOf(this.fromdte.getValue()), Date.valueOf(this.todte.getValue())); 
                break;
            case "PM Inventory Critical Stock Report" :  
                roq.MM_InventoryCriticalStockReport(); 
                break;
            case "PM Stock Transfer Report" :  
                roq.StockTransferReport(Date.valueOf(this.fromdte.getValue()), Date.valueOf(this.todte.getValue())); 
                break;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Report Created please see My Document/Reports");

        alert.showAndWait();
    }
}
