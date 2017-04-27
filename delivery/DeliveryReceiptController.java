/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

import best_chem.AbstractController;
import dbquerries.DeliveryReceiptsQuery;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.CustomerModel;
import models.DRItemsModel;
import models.DRModel;
import models.UserModel;
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
    
    private CustomerModel model;
    
    private SOViewModel sovm;
    
    private ArrayList<DRItemViewModel> items = new ArrayList(); 
    
    private final DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();
    
    public void setInit(CustomerModel cust, SOViewModel sovm){
        this.model = cust;
        this.sovm = sovm;
        
        System.out.println(cust.getCompany());
        System.out.println(sovm.getCustomerpo());
        
    }
    
    public void AddMode() throws SQLException{
        
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

                items.add(viewm);
            }
        }
        else{
            iterate = drq.getSalesOrderItems(this.sovm.getIdso());
            
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

                items.add(viewm);
            }
        }
        
        
        
        
//        private String sku;
//        private String skudesc;
//        private int ordrqty;
//        private int deliveryqty;
//        private int qtyremaining;
//        private String uom;
        
        String[] arr = {"sku", "skudesc", "ordrqty", "deliveryqty", "qtyremaining", "uom"};
        ObservableList<DRItemViewModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.items.size(); i++){
            data.add(items.get(i));
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
    public void EditItem(ActionEvent event) {

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
        drm.setTrnme(this.trckrfld.getText());
        drm.setDrvrnme(this.drvfld.getText());
        drm.setPltno(this.pltfld.getText());
        drm.setRemarks(null);
        drm.setDrprint("N");
        drm.setDrstatus("open");
        drm.setPgi("N");
        drm.setRemarks(this.rmksfld.getText());
        ArrayList<DRItemsModel> dritems = new ArrayList();
        for(DRItemViewModel drvm : items){
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
    public void initData(UserModel user) {
        super.setGlobalUser(user);
    }
    
}
