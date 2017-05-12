/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesorder;

import best_chem.AbstractController;
import dbquerries.DeliveryReceiptsQuery;
import dbquerries.SalesInvoiceQuery;
import dbquerries.SalesOrderQuery;
import delivery.DeliveryReceiptController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CustomerModel;
import models.InventoryModel;
import models.SalesOrderModel;
import models.UserModel;
import salesinvoices.SalesInvoiceController;
import viewmodels.DRViewModel;
import viewmodels.SIViewModel;
import viewmodels.SOViewModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class SalesOrderController extends AbstractController implements Initializable {
    
    private CustomerModel cust;
    
    @FXML
    private TextField comfld;

    @FXML
    private TextField tinfld;

    @FXML
    private TextField addfld;

    @FXML
    private Button addSalesOrder;
    
    @FXML
    private Button editsobtn;
    
    @FXML
    private Button viewsobtn;
    
    @FXML
    private Button draddbtn;
        
    @FXML
    private Button editdrbtn;

    @FXML
    private Button viewdrbtn;
    
    @FXML
    private Button addinvoicebtn;
    
    @FXML
    private Button editsibtn;
    
    @FXML
    private Button viewsibtn;
    
    @FXML
    private Button searchbtn;
    
    @FXML
    private TextField sofld;
    
    @FXML
    private TableView<SOViewModel> salestble;
    
    @FXML
    private TableView<DRViewModel> drtable;
    
    @FXML
    private TableView<SIViewModel> invoicetble;
    
    private SOViewModel somod;
    
    private final SalesOrderQuery soq = new SalesOrderQuery();
    private final DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();
    private final SalesInvoiceQuery siq = new SalesInvoiceQuery();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.sofld.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                System.out.println(sofld.getText());
                try {
                    this.searchSOrders(sofld.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.searchbtn.setOnAction((ActionEvent event) -> {
            
            try {
                this.searchSOrders(sofld.getText());
            } catch (SQLException ex) {
                Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    public void setCustomer(CustomerModel model){
        this.cust = model;
        
        this.comfld.setText(this.cust.getCompany());
        this.comfld.setEditable(false);
        this.tinfld.setText(this.cust.getTin());
        this.tinfld.setEditable(false);
        this.addfld.setText(this.cust.getAddress());
        this.addfld.setEditable(false);
        
        try {
            // TODO
            this.getSalesOrders();
        } catch (SQLException ex) {
            Logger.getLogger(SalesOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.salestble.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                System.out.println(salestble.getSelectionModel().getSelectedItem().getIdso());
                System.out.println(salestble.getSelectionModel().getSelectedItem().getCustomerpo());
                
                
                try {
                    //GET DELIVERY RECEIPTS
                    this.somod = salestble.getSelectionModel().getSelectedItem();
                    this.getDeliveryReceipts(salestble.getSelectionModel().getSelectedItem().getIdso());
                    this.getSalesInvoice(salestble.getSelectionModel().getSelectedItem().getIdso());
                    
                    //GET SALES INVOICE RECEIPTS
                } catch (SQLException ex) {
                    Logger.getLogger(SalesOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    @FXML
    public void addSalesOrder(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesorder/TransactionItemsView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        TransactionItemsViewController tivc = fxmlloader.<TransactionItemsViewController>getController();
        tivc.initData(super.getGlobalUser(), 0);
        tivc.AddMode(this.cust);

        Scene scene = new Scene(root);
        Stage stage = (Stage) addSalesOrder.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("View Customer Orders");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getSalesOrders();
    }
    
    @FXML
    void ediSO(ActionEvent event) throws IOException, SQLException {
        
        try{
            if(this.salestble.getSelectionModel().getSelectedItem().getStatus().equals("open")){
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesorder/TransactionItemsView.fxml"));
                Parent root = (Parent) fxmlloader.load();

                TransactionItemsViewController tivc = fxmlloader.<TransactionItemsViewController>getController();
                tivc.initData(super.getGlobalUser(), 0);
                tivc.EditMode(cust, this.salestble.getSelectionModel().getSelectedItem());

                Scene scene = new Scene(root);
                Stage stage = (Stage) addSalesOrder.getScene().getWindow();
                Stage substage = new Stage();
                substage.setScene(scene);
                substage.setTitle("Edit Sales Order");
                substage.initModality(Modality.WINDOW_MODAL);
                substage.initOwner(stage);
                substage.showAndWait();

                this.getSalesOrders();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                if(this.salestble.getSelectionModel().getSelectedItem().getStatus().equals("cancelled")){
                    alert.setContentText("This Sales Order is already cancelled.");
                }else if(this.salestble.getSelectionModel().getSelectedItem().getStatus().equals("With DR")){
                    alert.setContentText("This Sales Order already has open delivery receipts.");
                }
                alert.showAndWait();
            }
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a sales order.");

            alert.showAndWait();
        }
        
        
    }

    @FXML
    void viewSalesOrder(ActionEvent event) throws SQLException, IOException {
        
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesorder/TransactionItemsView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            TransactionItemsViewController tivc = fxmlloader.<TransactionItemsViewController>getController();
            tivc.initData(super.getGlobalUser(), 0);
            tivc.ViewMode(cust, this.salestble.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) addSalesOrder.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setTitle("View Customer Orders");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();
            
            /**
             * 
             * SYSTEM CHECK HERE
             * 
             * ALL THE DRs are Cancelled.
             * 
             */

            this.getSalesOrders();
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a sales order.");

            alert.showAndWait();
        }
    }
    
    public void getSalesOrders() throws SQLException{
        
        String[] arr = {"idso", "sodate", "status", "sodrdate", "Customerpo"};
        
        ObservableList<SOViewModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = this.soq.getSalesOrders(Integer.valueOf(this.cust.getIdcustomer()));
        
        while(map.hasNext()){
            //System.out.println("HELLO");
            HashMap temp = (HashMap) map.next();
            
            SOViewModel sovm = new SOViewModel();
            sovm.setIdso(Integer.valueOf(temp.get("idsalesorder").toString()));
            sovm.setSodate(temp.get("sodate").toString());
            sovm.setStatus(temp.get("status").toString());
            sovm.setSodrdate(temp.get("sodeliverydate").toString());
            sovm.setCustomerpo(temp.get("customerpo").toString());
            data.add(sovm);
        }
        
        ObservableList<TableColumn<SOViewModel, ?>> olist;
        olist = (ObservableList<TableColumn<SOViewModel, ?>>) this.salestble.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setSortable(false);
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.salestble.setItems(data);
    }
    
    @FXML
    public void addDR(ActionEvent event) throws IOException, SQLException {
        
        try{
            if(!this.salestble.getSelectionModel().getSelectedItem().getStatus().equals("cancelled")){
                if(this.drq.getDRPGICount(this.salestble.getSelectionModel().getSelectedItem().getIdso()) == 0){
                    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/delivery/DeliveryReceiptView.fxml"));
                    Parent root = (Parent) fxmlloader.load();

                    DeliveryReceiptController drvc = fxmlloader.<DeliveryReceiptController>getController();
                    drvc.initData(this.getGlobalUser(), 0);
                    drvc.setInit(cust, this.somod);
                    drvc.AddMode();

                    Scene scene = new Scene(root);
                    Stage stage = (Stage) draddbtn.getScene().getWindow();
                    Stage substage = new Stage();
                    substage.setScene(scene);
                    substage.setTitle("Add Delivery Receipt");
                    substage.initModality(Modality.WINDOW_MODAL);
                    substage.initOwner(stage);
                    substage.showAndWait();

                    this.getDeliveryReceipts(this.salestble.getSelectionModel().getSelectedItem().getIdso());

                    /**
                    * 
                    * SYSTEM CHECK HERE
                    * 
                    * 
                    * 
                    */
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("You have at least one delivery receipt not post good issued. Please PGI the DR before proceeding.");

                    alert.showAndWait();
                }
                
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("This Sales Order is already cancelled.");

                alert.showAndWait();
                
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a sales order.");

            alert.showAndWait();
        }
        
    }

    @FXML
    public void editDR(ActionEvent event) throws SQLException, IOException {
        try{
            if(this.drtable.getSelectionModel().getSelectedItem().getPgi().equals("N") && !this.drtable.getSelectionModel().getSelectedItem().getStatus().equals("cancelled")){
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/delivery/DeliveryReceiptView.fxml"));
                Parent root = (Parent) fxmlloader.load();

                DeliveryReceiptController drvc = fxmlloader.<DeliveryReceiptController>getController();
                drvc.initData(this.getGlobalUser(), 0);
                drvc.setInit(cust, this.somod);
                drvc.EditMode(this.drtable.getSelectionModel().getSelectedItem());

                Scene scene = new Scene(root);
                Stage stage = (Stage) draddbtn.getScene().getWindow();
                Stage substage = new Stage();
                substage.setScene(scene);
                substage.setTitle("Add Delivery Receipt");
                substage.initModality(Modality.WINDOW_MODAL);
                substage.initOwner(stage);
                substage.showAndWait();
                
                /**
                * 
                * SYSTEM CHECK HERE
                * 
                */

                this.getDeliveryReceipts(this.salestble.getSelectionModel().getSelectedItem().getIdso());
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("This delivery order is already post good issued cannot be edited.");

                alert.showAndWait();
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please select a delivery order.");

            alert.showAndWait();
        }
    }

    @FXML
    public void viewDR(ActionEvent event) throws SQLException, IOException {
        try{
            
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/delivery/DeliveryReceiptView.fxml"));
            Parent root = (Parent) fxmlloader.load();

            DeliveryReceiptController drvc = fxmlloader.<DeliveryReceiptController>getController();
            drvc.initData(this.getGlobalUser(), 1);
            drvc.setInit(cust, this.somod);
            drvc.ViewMode(this.drtable.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) draddbtn.getScene().getWindow();
            Stage substage = new Stage();
            substage.setScene(scene);
            substage.setTitle("Add Delivery Receipt");
            substage.initModality(Modality.WINDOW_MODAL);
            substage.initOwner(stage);
            substage.showAndWait();
            
            /**
             * 
             * SYSTEM CHECK HERE
             * 
             */

            this.getDeliveryReceipts(this.salestble.getSelectionModel().getSelectedItem().getIdso());
            this.getSalesOrders();
            
        }catch(NullPointerException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please select a delivery order.");

            alert.showAndWait();
        }
    }
    
    public void getDeliveryReceipts(int soid) throws SQLException{
        
        String[] arr = {"drnum", "drdate", "prnt", "pgi", "status"};
        
        ObservableList<DRViewModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = this.drq.getAllDeliveryReceipts(soid);
        
        while(map.hasNext()){
            HashMap temp = (HashMap) map.next();
            
            DRViewModel drvm = new DRViewModel();
            
            drvm.setDrnum(Integer.valueOf(temp.get("iddeliveryorders").toString()));
            drvm.setDrdate(temp.get("drdate").toString());
            drvm.setPgi(temp.get("drpgi").toString());
            drvm.setPrnt(temp.get("drprint").toString());
            drvm.setStatus(temp.get("drstatus").toString());
            
            data.add(drvm);
        }
        
        ObservableList<TableColumn<DRViewModel, ?>> olist;
        olist = (ObservableList<TableColumn<DRViewModel, ?>>) this.drtable.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.drtable.getItems().clear();
        this.drtable.setItems(data);
        
    }
    
    @FXML
    public void addSI(ActionEvent event) throws IOException, SQLException {
        
        try{
            if(this.salestble.getSelectionModel().getSelectedItem().getStatus().equals("With DR")){
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesinvoices/SalesInvoiceView.fxml"));
                Parent root = (Parent) fxmlloader.load();

                SalesInvoiceController sivc = fxmlloader.<SalesInvoiceController>getController();
                sivc.initData(this.getGlobalUser(), 0);
                sivc.setInit(cust, this.somod);
                sivc.AddMode();

                Scene scene = new Scene(root);
                Stage stage = (Stage) addinvoicebtn.getScene().getWindow();
                Stage substage = new Stage();
                substage.setScene(scene);
                substage.setTitle("Add Sales Invoice");
                substage.initModality(Modality.WINDOW_MODAL);
                substage.initOwner(stage);
                substage.showAndWait();

                this.getSalesInvoice(this.salestble.getSelectionModel().getSelectedItem().getIdso());
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                
                if(this.salestble.getSelectionModel().getSelectedItem().getStatus().equals("cancelled")){
                    alert.setContentText("This Sales Order is already cancelled.");
                }
                else if(this.salestble.getSelectionModel().getSelectedItem().getStatus().equals("open")){
                    alert.setContentText("This Sales order does not have a delivery receipt.");
                }
                
                

                alert.showAndWait();
            }
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a sales order.");

            alert.showAndWait();
        }
    }
    
    @FXML
    void editSI(ActionEvent event) throws SQLException, IOException {
        try{
            if(this.invoicetble.getSelectionModel().getSelectedItem().getStatus().equals("open")){
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesinvoices/SalesInvoiceView.fxml"));
                Parent root = (Parent) fxmlloader.load();

                SalesInvoiceController sivc = fxmlloader.<SalesInvoiceController>getController();
                sivc.initData(this.getGlobalUser(), 0);
                sivc.setInit(cust, this.somod);
                sivc.EditMode();

                Scene scene = new Scene(root);
                Stage stage = (Stage) addinvoicebtn.getScene().getWindow();
                Stage substage = new Stage();
                substage.setScene(scene);
                substage.setTitle("Edit Sales Invoice");
                substage.initModality(Modality.WINDOW_MODAL);
                substage.initOwner(stage);
                substage.showAndWait();

                this.getSalesInvoice(this.salestble.getSelectionModel().getSelectedItem().getIdso());
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("This sales invoice is already complete.");

                alert.showAndWait();
            }
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a sales invoice.");

            alert.showAndWait();
        }
    }

    @FXML
    void viewSI(ActionEvent event) throws SQLException, IOException {
        try{
            if(this.invoicetble.getSelectionModel().getSelectedItem().getStatus().equals("open")){
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesinvoices/SalesInvoiceView.fxml"));
                Parent root = (Parent) fxmlloader.load();

                SalesInvoiceController sivc = fxmlloader.<SalesInvoiceController>getController();
                sivc.initData(this.getGlobalUser(), 0);
                sivc.setInit(cust, this.somod);
                sivc.ViewMode(this.invoicetble.getSelectionModel().getSelectedItem());

                Scene scene = new Scene(root);
                Stage stage = (Stage) addinvoicebtn.getScene().getWindow();
                Stage substage = new Stage();
                substage.setScene(scene);
                substage.setTitle("View Sales Invoice");
                substage.initModality(Modality.WINDOW_MODAL);
                substage.initOwner(stage);
                substage.showAndWait();

                this.getSalesInvoice(this.salestble.getSelectionModel().getSelectedItem().getIdso());
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("This Sales Invoice is already cancelled.");

                alert.showAndWait();
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a sales order.");

            alert.showAndWait();
        }
    }
    
    public void getSalesInvoice(int soid) throws SQLException{
        
        String[] arr = {"invnum", "dte", "print", "Status"};
        
        ObservableList<SIViewModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = this.siq.getSalesInvoice(soid);
        
        while(map.hasNext()){
            HashMap temp = (HashMap) map.next();
            
            //System.out.println(temp.get("iddeliver").toString());
            
            SIViewModel siitem = new SIViewModel();
            
            siitem.setInvnum(Integer.valueOf(temp.get("idsalesinvoices").toString()));
            siitem.setDte(temp.get("sidte").toString());
            siitem.setPrint(temp.get("printstat").toString());
            siitem.setStatus(temp.get("status").toString());
            
            data.add(siitem);
        }
        
        ObservableList<TableColumn<SIViewModel, ?>> olist;
        olist = (ObservableList<TableColumn<SIViewModel, ?>>) this.invoicetble.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.invoicetble.setItems(data);
        
    }
    
    public void searchSOrders(String soid) throws SQLException{
        String[] arr = {"idso", "sodate", "status", "sodrdate", "Customerpo"};
        
        ObservableList<SOViewModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = this.soq.getSOs(Integer.valueOf(this.cust.getIdcustomer()), soid);
        
        while(map.hasNext()){
            //System.out.println("HELLO");
            HashMap temp = (HashMap) map.next();
            
            SOViewModel sovm = new SOViewModel();
            sovm.setIdso(Integer.valueOf(temp.get("idsalesorder").toString()));
            sovm.setSodate(temp.get("sodate").toString());
            sovm.setStatus(temp.get("status").toString());
            sovm.setSodrdate(temp.get("sodeliverydate").toString());
            sovm.setCustomerpo(temp.get("customerpo").toString());
            data.add(sovm);
        }
        
        ObservableList<TableColumn<SOViewModel, ?>> olist;
        olist = (ObservableList<TableColumn<SOViewModel, ?>>) this.salestble.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setSortable(false);
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.salestble.getItems().clear();
        this.salestble.setItems(data);
    }

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
    }
    
}
