/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoices;

import best_chem.AbstractController;
import dbquerries.DeliveryReceiptsQuery;
import dbquerries.SalesInvoiceQuery;
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
import models.SIModel;
import models.SItemsModel;
import models.SOItemModel;
import models.UserModel;
import viewmodels.DRViewModel;
import viewmodels.SOViewModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class SalesInvoiceController extends AbstractController implements Initializable {
    
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
    private TableView<DRViewModel> itemlist;
    
    @FXML
    private TableView<SOItemModel> itemlist1;

    @FXML
    private TextField bsnstylefld;

    @FXML
    private TextField cpofld;

    @FXML
    private Button resetbtn;

    @FXML
    private TextField pymttermfld;

    @FXML
    private DatePicker sodtefld;

    @FXML
    private DatePicker datefld;

    @FXML
    private Button pendingbtn;

    @FXML
    private TextField sidfld;
    
    @FXML
    private TextField vendrfld;

    @FXML
    private TextField prntfld;

    @FXML
    private TextField drvfld;

    @FXML
    private TextField statusfld;

    @FXML
    private TextArea rmksfld;
    
    private final SalesInvoiceQuery siq = new SalesInvoiceQuery();
    
    private final DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();

    @FXML
    void EditItem(ActionEvent event) {

    }

    @FXML
    void ResetItems(ActionEvent event) {

    }

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        SIModel salesinvoice = new SIModel();
        
        salesinvoice.setCustomerid(Integer.valueOf(this.model.getIdcustomer()));
        salesinvoice.setCby(super.getGlobalUser().getId());
        salesinvoice.setSoidnvc(this.sovm.getIdso());
        salesinvoice.setRemarks(this.rmksfld.getText());
        salesinvoice.setStatus("open");
        salesinvoice.setDrvnme(this.drvfld.getText());
        salesinvoice.setTrcnme(this.trckrfld.getText());
        salesinvoice.setPlateno(this.pltfld.getText());
        salesinvoice.setDte(Date.valueOf(this.datefld.getValue()));
        salesinvoice.setPrntstat("N");
        
        Iterator ir = this.itemlist.getItems().iterator();
        ArrayList<SItemsModel> simods = new ArrayList();
        while(ir.hasNext()){
            DRViewModel mod = (DRViewModel) ir.next();
            SItemsModel simod = new SItemsModel();
            simod.setDrid(mod.getDrnum());
            
            simods.add(simod);
        }
        
        salesinvoice.setSitems(simods);
        
        siq.addSalesInvoice(salesinvoice);
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    private CustomerModel model;
    
    private SOViewModel sovm;
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
    
    public void setInit(CustomerModel cust, SOViewModel sovm){
        this.model = cust;
        this.sovm = sovm;
        
        System.out.println(cust.getCompany());
        System.out.println(sovm.getCustomerpo());
        
    }
    
    public void AddMode() throws SQLException{
        
        this.sidfld.setDisable(true);
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
        this.vendrfld.setText(this.model.getVendor_code());
        this.vendrfld.setEditable(false);
        
        this.statusfld.setText("open");
        this.statusfld.setEditable(false);
        
        this.prntfld.setText("N");
        this.prntfld.setEditable(false);
        
        drq.getDeliverReceipts(this.sovm.getIdso());
        
        String[] arr = {"drnum", "drdate", "prnt", "pgi", "status"};
        
        ObservableList<DRViewModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = this.drq.getDeliverReceipts(this.sovm.getIdso());
        
        while(map.hasNext()){
            HashMap temp = (HashMap) map.next();
            
            //System.out.println(temp.get("iddeliver").toString());
            
            DRViewModel drvm = new DRViewModel();
            
            drvm.setDrnum(Integer.valueOf(temp.get("iddeliveryorders").toString()));
            drvm.setDrdate(temp.get("drdate").toString());
            drvm.setPgi(temp.get("drpgi").toString());
            drvm.setPrnt(temp.get("drprint").toString());
            drvm.setStatus(temp.get("drstatus").toString());
            
            data.add(drvm);
        }
        
        ObservableList<TableColumn<DRViewModel, ?>> olist;
        olist = (ObservableList<TableColumn<DRViewModel, ?>>) this.itemlist.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.itemlist.setItems(data);
        
        //Get the line items
        
        String[] arr2 = {"sku", "desc", "qty", "uom", "uprice", "discnt", "amount", "vat"};
        
        ObservableList<SOItemModel> data2
                = FXCollections.observableArrayList();
        
        Iterator map2 = this.siq.getLineItems(this.sovm.getIdso(), Integer.valueOf(this.model.getIdcustomer()));
        
        while(map2.hasNext()){
            HashMap temp2 = (HashMap) map2.next();
            
            //System.out.println(temp.get("iddeliver").toString());
            
            SOItemModel soitem = new SOItemModel();
            
            soitem.setSku(temp2.get("sku").toString());
            soitem.setDesc(temp2.get("skudesc").toString());
            soitem.setUom(temp2.get("skuom").toString());
            soitem.setQty(Double.valueOf(temp2.get("sumdrqty").toString()).intValue());
            soitem.setIdinventory(Integer.valueOf(temp2.get("idinventory").toString()));
            soitem.setDiscnt(this.model.getDiscount());
            soitem.setUprice(Double.valueOf(temp2.get("sellingPrice").toString()));
            soitem.setAmount(Double.valueOf(temp2.get("totamt").toString()));
            soitem.setVat(Double.valueOf(temp2.get("vatinc").toString()));
            
            data2.add(soitem);
        }
        
        ObservableList<TableColumn<SOItemModel, ?>> olist2;
        olist2 = (ObservableList<TableColumn<SOItemModel, ?>>) this.itemlist1.getColumns();

        for (int i = 0; i < arr2.length; i++) {
            olist2.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr2[i])
            );
        }
        this.itemlist1.setItems(data2);
        
        
    }
    
}
