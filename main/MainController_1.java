package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import best_chem.AbstractController;
import dbquerries.SupplierQuery;
import dbquerries.UtilitiesQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private Button removereturnbtn;

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
    private TableView<?> inventorytable;

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
    
    private final UtilitiesQuery uq = new UtilitiesQuery();
    private final SupplierQuery sq = new SupplierQuery();
    
    @FXML
    void viewSupplier(ActionEvent event) {

    }

    @FXML
    void editSupplier(ActionEvent event) {

    }

    @FXML
    void addSupplier(ActionEvent event) throws SQLException, IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/supplier/SupplierView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        SupplierController sc = new SupplierController();
        sc.AddMode();
        sc.setType(super.getType());

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
    void viewPurchases(ActionEvent event) {

    }

    @FXML
    void addInventory(ActionEvent event) {

    }

    @FXML
    void editInventory(ActionEvent event) {

    }

    @FXML
    void inventoryAdjustment(ActionEvent event) {

    }

    @FXML
    void addReturns(ActionEvent event) {

    }

    @FXML
    void removeReturns(ActionEvent event) {

    }

    @FXML
    void viewReturns(ActionEvent event) {

    }

    @FXML
    void returnsAdjustment(ActionEvent event) {

    }

    @FXML
    void addPrice(ActionEvent event) {

    }

    @FXML
    void editPrice(ActionEvent event) {

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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try {
             // TOD
             this.getUOMS();
             this.getWHS();
             this.getSuppliers();
             
         } catch (SQLException ex) {
             Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
}
