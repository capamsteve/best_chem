/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

import best_chem.AbstractController;
import dbquerries.DeliveryReceiptsQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mgi.ManualGoodsIssueController;
import models.CustomerModel;
import models.DRItemsModel;
import models.DRModel;
import models.UserModel;
import viewmodels.DRViewModel;
import viewmodels.SOViewModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class DeliveryReceiptController extends AbstractController implements Initializable {
    
    @FXML
    private TextField pltfld;

    @FXML
    private TextField addressfld;

    @FXML
    private Button editbtn;

    @FXML
    private TextField trckrfld;

    @FXML
    private TextField compfld;

    @FXML
    private TextField tinfld;

    @FXML
    private TextField cidlfd;

    @FXML
    private DatePicker drdatefld;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField soidfld;

    @FXML
    private TableView<DRItemViewModel> itemlist;

    @FXML
    private TextField bsnstylefld;

    @FXML
    private TextField cpofld;

    @FXML
    private TextField prntdfld;

    @FXML
    private Button resetbtn;

    @FXML
    private TextField pymttermfld;

    @FXML
    private DatePicker sodtefld;

    @FXML
    private TextField drvfld;

    @FXML
    private DatePicker datefld;

    @FXML
    private TextField pgifld;

    @FXML
    private TextField statusfld;

    @FXML
    private Button pendingbtn;

    @FXML
    private TextField dridfld;
    
    @FXML
    private TextArea rmksfld;
        
    @FXML
    private Button pgibtn;

    @FXML
    private Button printbtn;
    
    private boolean isEdit;
    
    private CustomerModel model;
    
    private SOViewModel sovm;
    
    private ArrayList<DRItemViewModel> items; 
    
    private final DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();
    
    
    public void setInit(CustomerModel cust, SOViewModel sovm){
        this.model = cust;
        this.sovm = sovm;
        
        System.out.println(cust.getCompany());
        System.out.println(sovm.getCustomerpo());
        
    }
    
    public void AddMode() throws SQLException{
        this.isEdit = false;
        items = new ArrayList(); 
        this.dridfld.setDisable(true);
        this.cpofld.setText(this.sovm.getCustomerpo());
        this.cpofld.setEditable(false);
        this.compfld.setText(this.model.getCompany());
        this.compfld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(this.sovm.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.tinfld.setText(this.model.getTin());
        this.tinfld.setEditable(false);
        this.addressfld.setText(this.model.getAddress());
        this.addressfld.setEditable(false);
        this.pymttermfld.setText(this.model.getPaymentterm());
        this.pymttermfld.setEditable(false);
        this.bsnstylefld.setText(this.model.getBusinessstyle());
        this.bsnstylefld.setEditable(false);
        this.soidfld.setText(String.valueOf(this.sovm.getIdso()));
        this.soidfld.setEditable(false);
        this.sodtefld.setValue(LocalDate.parse(this.sovm.getSodate()));
        this.sodtefld.setEditable(false);
        this.cidlfd.setText(this.model.getIdcustomer());
        this.cidlfd.setEditable(false);
        this.pgifld.setText("N");
        this.pgifld.setEditable(false);
        this.statusfld.setText("open");
        this.statusfld.setEditable(false);
        this.prntdfld.setText("N");
        this.prntdfld.setEditable(false);
        
        this.pgibtn.setDisable(true);
        this.printbtn.setDisable(true);
        
        // Get all of the Sales Order items
        Iterator iterate = null;
        
        if(drq.getDRCount(this.sovm.getIdso()) == 0){
            iterate = drq.getSalesOrderItems(this.sovm.getIdso());
            
            while(iterate.hasNext()){
                HashMap map = (HashMap) iterate.next();
                DRItemViewModel viewm = new DRItemViewModel();
                viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
                viewm.setSku(map.get("sku").toString());
                viewm.setDeliveryqty(0);
                //System.out.println(map.get("Remaining_Quantity").toString());
                viewm.setQtyremaining(Integer.parseInt(map.get("ordrqty").toString()));
                viewm.setSkudesc(map.get("skudesc").toString());
                viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
                viewm.setUom(map.get("skuom").toString());

                this.items.add(viewm);
            }
        }
        else{
            iterate = drq.getSalesOrderItemsWRemaining(this.sovm.getIdso());
            
            while(iterate.hasNext()){
                HashMap map = (HashMap) iterate.next();
                DRItemViewModel viewm = new DRItemViewModel();
                viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
                viewm.setSku(map.get("sku").toString());
                viewm.setDeliveryqty(0);
                //System.out.println(map.get("Remaining_Quantity").toString());
                viewm.setQtyremaining(Double.valueOf(map.get("Remaining_Quantity").toString()).intValue());
                viewm.setSkudesc(map.get("skudesc").toString());
                viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
                viewm.setUom(map.get("skuom").toString());

                this.items.add(viewm);
            }
        }
        
        this.RefreshItems();
    }
    
    public void EditMode(DRViewModel dr) throws SQLException{
        
        this.isEdit = true;
        
        this.dridfld.setText(String.valueOf(dr.getDrnum()));
        this.dridfld.setEditable(false);
        this.cpofld.setText(this.sovm.getCustomerpo());
        this.cpofld.setEditable(false);
        this.compfld.setText(this.model.getCompany());
        this.compfld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(this.sovm.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.tinfld.setText(this.model.getTin());
        this.tinfld.setEditable(false);
        this.addressfld.setText(this.model.getAddress());
        this.addressfld.setEditable(false);
        this.pymttermfld.setText(this.model.getPaymentterm());
        this.pymttermfld.setEditable(false);
        this.bsnstylefld.setText(this.model.getBusinessstyle());
        this.bsnstylefld.setEditable(false);
        this.soidfld.setText(String.valueOf(this.sovm.getIdso()));
        this.soidfld.setEditable(false);
        this.sodtefld.setValue(LocalDate.parse(this.sovm.getSodate()));
        this.sodtefld.setEditable(false);
        this.cidlfd.setText(this.model.getIdcustomer());
        this.cidlfd.setEditable(false);
        this.datefld.setValue(LocalDate.parse(dr.getDrdate()));
        
        DRModel drm = drq.getDR(dr.getDrnum());
        
        System.out.println(dr.getDrnum());
        
        if(drm == null){
            System.out.println("null");
        }
        
        this.rmksfld.setText(drm.getRemarks());
        this.pgifld.setText(dr.getPgi());
        this.pgifld.setEditable(false);
        this.statusfld.setText(drm.getDrstatus());
        this.statusfld.setEditable(false);
        this.prntdfld.setText(drm.getDrprint());
        this.prntdfld.setEditable(false);
        
        this.pgibtn.setDisable(true);
        this.printbtn.setDisable(true);
        
        /**
         * GET ITEMS 
         * 
         * BUT must be inline with the quantity remaining for those not PGI
         * 
         */
        
        //this.RefreshItems();
        
    }
    
    public void ViewMode(DRViewModel dr) throws SQLException{
        
        this.dridfld.setText(String.valueOf(dr.getDrnum()));
        this.dridfld.setEditable(false);
        this.cpofld.setText(this.sovm.getCustomerpo());
        this.cpofld.setEditable(false);
        this.compfld.setText(this.model.getCompany());
        this.compfld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(this.sovm.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.tinfld.setText(this.model.getTin());
        this.tinfld.setEditable(false);
        this.addressfld.setText(this.model.getAddress());
        this.addressfld.setEditable(false);
        this.pymttermfld.setText(this.model.getPaymentterm());
        this.pymttermfld.setEditable(false);
        this.bsnstylefld.setText(this.model.getBusinessstyle());
        this.bsnstylefld.setEditable(false);
        this.soidfld.setText(String.valueOf(this.sovm.getIdso()));
        this.soidfld.setEditable(false);
        this.sodtefld.setValue(LocalDate.parse(this.sovm.getSodate()));
        this.sodtefld.setEditable(false);
        this.cidlfd.setText(this.model.getIdcustomer());
        this.cidlfd.setEditable(false);
        this.datefld.setValue(LocalDate.parse(dr.getDrdate()));
        
        DRModel drm = drq.getDR(dr.getDrnum());
        
        System.out.println(dr.getDrnum());
        
        if(drm == null){
            System.out.println("null");
        }
        
        this.rmksfld.setText(drm.getRemarks());
        this.rmksfld.setEditable(false);
        this.pgifld.setText(dr.getPgi());
        this.pgifld.setEditable(false);
        this.statusfld.setText(drm.getDrstatus());
        this.statusfld.setEditable(false);
        this.prntdfld.setText(drm.getDrprint());
        this.prntdfld.setEditable(false);

        this.pendingbtn.setDisable(true);
        
        /**
         * GETS ALL THE LINE ITEMS READY FOR PGI
         */
        
        // Get all of the Sales Order items
        Iterator iterate = null;
        
        iterate = drq.getDeliverOrderItemsWRemaining(dr.getDrnum(), sovm.getIdso());
        
        if(iterate == null){
            drq.getDeliveryOrderItemsIfNull(dr.getDrnum(), sovm.getIdso());
            
            while(iterate.hasNext()){
                HashMap map = (HashMap) iterate.next();
                DRItemViewModel viewm = new DRItemViewModel();
                viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
                viewm.setSku(map.get("sku").toString());
                viewm.setDeliveryqty(0);
                //System.out.println(map.get("Remaining_Quantity").toString());
                viewm.setQtyremaining(Integer.parseInt(map.get("ordrqty").toString()));
                viewm.setSkudesc(map.get("skudesc").toString());
                viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
                viewm.setUom(map.get("skuom").toString());

                this.items.add(viewm);
            }
        }
        else{
            
        }
//        }
//        else{
//            iterate = drq.getDeliverOrderItemsWRemaining(, soid)
//            
//            while(iterate.hasNext()){
//                HashMap map = (HashMap) iterate.next();
//                DRItemViewModel viewm = new DRItemViewModel();
//                viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
//                viewm.setSku(map.get("sku").toString());
//                viewm.setDeliveryqty(0);
//                //System.out.println(map.get("Remaining_Quantity").toString());
//                viewm.setQtyremaining(Double.valueOf(map.get("Remaining_Quantity").toString()).intValue());
//                viewm.setSkudesc(map.get("skudesc").toString());
//                viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
//                viewm.setUom(map.get("skuom").toString());
//
//                this.items.add(viewm);
//            }
//        }
        
        //this.RefreshItems();
        
    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "skudesc", "ordrqty", "deliveryqty", "qtyremaining", "uom"};
        this.itemlist.getItems().removeAll(this.itemlist.getItems());
        ObservableList<DRItemViewModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.items.size(); i++){
            data.add(this.items.get(i));
        }
        ObservableList<TableColumn<DRItemViewModel, ?>> olist = (ObservableList<TableColumn<DRItemViewModel, ?>>) itemlist.getColumns();
        
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        
        this.itemlist.setItems(data);
    }

    @FXML
    public void EditItem(ActionEvent event) throws IOException { 
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/delivery/EditDeliveryQuantityView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        EditDeliveryQuantityController edqc = fxmlloader.<EditDeliveryQuantityController>getController();
        edqc.setlimit(this.itemlist.getSelectionModel().getSelectedItem().getQtyremaining());
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) editbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Edit Delivery Quantity");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        if(!edqc.isCanceled()){
            DRItemViewModel temp = this.itemlist.getSelectionModel().getSelectedItem();
            System.out.println("temp: " + temp.getDeliveryqty());
            this.items.remove(temp);
            
            temp.setDeliveryqty(edqc.getQty());
            
            this.items.add(temp);
            this.RefreshItems();
        }
        
        this.RefreshItems();

    }

    @FXML
    public void ResetItems(ActionEvent event) {

    }

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        DRModel drm = new DRModel();
        drm.setCby(this.getGlobalUser().getId());
        drm.setCustid(Integer.valueOf(this.model.getIdcustomer()));
        drm.setSoid(this.sovm.getIdso());
        drm.setDrdate(Date.valueOf(this.drdatefld.getValue()));
        drm.setDrprint("N");
        drm.setDrstatus("open");
        drm.setPgi("N");
        drm.setRemarks(this.rmksfld.getText());
        ArrayList<DRItemsModel> dritems = new ArrayList();
        for(DRItemViewModel drvm : itemlist.getItems()){
            DRItemsModel drit = new DRItemsModel();
            drit.setDrqty(drvm.getDeliveryqty());
            drit.setSiditem(drvm.getIdsoitem());
            
            dritems.add(drit);
        }
        drm.setDritems(dritems);
        
        drq.addDeliveryReceipt(drm);
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
    }
    
    @FXML
    void export(ActionEvent event) {

    }

    @FXML
    void PostToInventory(ActionEvent event) {

    }
}
