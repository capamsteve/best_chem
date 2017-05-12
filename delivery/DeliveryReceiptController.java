/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

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
import dbquerries.InventoryQuery;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CustomerModel;
import models.DRItemsModel;
import models.DRModel;
import models.SIModel;
import models.SItemsModel;
import models.UserModel;
import viewmodels.DRViewModel;
import viewmodels.SOViewModel;

/**
 * FXML Controller class
 *
 * @author Steven
 * 
 * 
 * TODO FOR TODAY MAY 10,2017
 *  -will not add a DR if their is a pending DR
 *  -Bring back the QTY REMAINING ITEMS
 */
public class DeliveryReceiptController extends AbstractController implements Initializable {

    @FXML
    private Button vcbtn;

    @FXML
    private Button editbtn;

    @FXML
    private DatePicker drdatefld;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField soidfld;

    @FXML
    private TableView<DRItemViewModel> itemlist;

    @FXML
    private TextField cpofld;

    @FXML
    private TextField prntdfld;

    @FXML
    private Button resetbtn;
    
    @FXML
    private DatePicker sodtefld;

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
    
    @FXML
    private Button canceldrbtn;
    
    private boolean isEdit;
    
    private CustomerModel model;
    
    private SOViewModel sovm;
    
    private ArrayList<DRItemViewModel> items; 
    
    private final DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();
    
    private final SalesInvoiceQuery siq = new SalesInvoiceQuery();
    
    private final SalesOrderQuery soq = new SalesOrderQuery();
    
    private final InventoryQuery iq = new InventoryQuery();
    
    private DRViewModel drm = new DRViewModel();
    
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
        this.drdatefld.setValue(LocalDate.parse(this.sovm.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.soidfld.setText(String.valueOf(this.sovm.getIdso()));
        this.soidfld.setEditable(false);
        this.sodtefld.setValue(LocalDate.parse(this.sovm.getSodate()));
        this.sodtefld.setEditable(false);
        this.pgifld.setText("N");
        this.pgifld.setEditable(false);
        this.statusfld.setText("open");
        this.statusfld.setEditable(false);
        this.prntdfld.setText("N");
        this.prntdfld.setEditable(false);
        
        this.pgibtn.setDisable(true);
        this.printbtn.setDisable(true);
        this.canceldrbtn.setDisable(true);
        
        // Get all of the Sales Order items
        Iterator iterate = null;
        
        if(drq.getDRCount(this.sovm.getIdso()) == 0){
            iterate = drq.getSalesOrderItems(this.sovm.getIdso(), "OPEN");
            
            while(iterate.hasNext()){
                HashMap map = (HashMap) iterate.next();
                DRItemViewModel viewm = new DRItemViewModel();
                viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
                viewm.setSku(map.get("sku").toString());

                if(Integer.valueOf(map.get("soh").toString()) >= Integer.parseInt(map.get("ordrqty").toString())){
                    viewm.setDeliveryqty(Integer.parseInt(map.get("ordrqty").toString()));
                }else{
                    viewm.setDeliveryqty(Integer.parseInt(map.get("soh").toString()));
                }

                //System.out.println(map.get("Remaining_Quantity").toString());
                viewm.setInventory_id(Integer.valueOf(map.get("idinventory").toString()));
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
                if(Integer.valueOf(map.get("soh").toString()) >= Integer.parseInt(map.get("ordrqty").toString())){
                    viewm.setDeliveryqty(Integer.parseInt(map.get("ordrqty").toString()));
                }else{
                    viewm.setDeliveryqty(Integer.parseInt(map.get("soh").toString()));
                }
                //System.out.println(map.get("Remaining_Quantity").toString());
                viewm.setQtyremaining(Double.valueOf(map.get("Remaining_Quantity").toString()).intValue());
                viewm.setSkudesc(map.get("skudesc").toString());
                viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
                viewm.setUom(map.get("skuom").toString());

                items.add(viewm);
            }
        }

        this.RefreshItems();
    }
    
    public void EditMode(DRViewModel dr) throws SQLException{
        
        this.isEdit = true;
        items = new ArrayList(); 
        this.dridfld.setText(String.valueOf(dr.getDrnum()));
        this.dridfld.setEditable(false);
        this.cpofld.setText(this.sovm.getCustomerpo());
        this.cpofld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(this.sovm.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.soidfld.setText(String.valueOf(this.sovm.getIdso()));
        this.soidfld.setEditable(false);
        this.sodtefld.setValue(LocalDate.parse(this.sovm.getSodate()));
        this.sodtefld.setEditable(false);
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
        
        //if()
        Iterator iterate = null;
        
        if(drq.getDRCount(this.sovm.getIdso()) == 1){
           iterate = drq.getDRItems(dr.getDrnum());
            
           while(iterate.hasNext()){
               HashMap map = (HashMap) iterate.next();
               DRItemViewModel viewm = new DRItemViewModel();
               viewm.setDritemid(Integer.valueOf(map.get("iddeliveryorderitems").toString()));
               viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
               viewm.setSku(map.get("sku").toString());
               viewm.setInventory_id(Integer.valueOf(map.get("idinventory").toString()));
               viewm.setDeliveryqty(Integer.parseInt(map.get("drqty").toString()));
               viewm.setSkudesc(map.get("skudesc").toString());
               viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
               viewm.setUom(map.get("skuom").toString());

               viewm.setQtyremaining(Integer.parseInt(map.get("ordrqty").toString()));

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
                viewm.setDeliveryqty(Integer.parseInt(map.get("drqty").toString()));
                viewm.setQtyremaining(Double.valueOf(map.get("Remaining_Quantity").toString()).intValue());
                viewm.setSkudesc(map.get("skudesc").toString());
                viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
                viewm.setUom(map.get("skuom").toString());

                items.add(viewm);
            }
        }

        this.RefreshItems();
        
    }
    
    public void ViewMode(DRViewModel dr) throws SQLException{
        items = new ArrayList(); 
        this.drm = dr;
        this.dridfld.setText(String.valueOf(dr.getDrnum()));
        this.dridfld.setEditable(false);
        this.cpofld.setText(this.sovm.getCustomerpo());
        this.cpofld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(this.sovm.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.soidfld.setText(String.valueOf(this.sovm.getIdso()));
        this.soidfld.setEditable(false);
        this.sodtefld.setValue(LocalDate.parse(this.sovm.getSodate()));
        this.sodtefld.setEditable(false);
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
        
        if(drq.getDRCount(this.sovm.getIdso()) == 1){
           iterate = drq.getDRItems(dr.getDrnum());
            
           while(iterate.hasNext()){
               HashMap map = (HashMap) iterate.next();
               DRItemViewModel viewm = new DRItemViewModel();
               viewm.setDritemid(Integer.valueOf(map.get("iddeliveryorderitems").toString()));
               viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
               viewm.setSku(map.get("sku").toString());
               viewm.setInventory_id(Integer.valueOf(map.get("idinventory").toString()));
               viewm.setDeliveryqty(Integer.parseInt(map.get("drqty").toString()));
               viewm.setSkudesc(map.get("skudesc").toString());
               viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
               viewm.setUom(map.get("skuom").toString());

               viewm.setQtyremaining(Integer.parseInt(map.get("ordrqty").toString()));

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
                viewm.setDeliveryqty(Integer.parseInt(map.get("drqty").toString()));
                viewm.setQtyremaining(Double.valueOf(map.get("Remaining_Quantity").toString()).intValue());
                viewm.setSkudesc(map.get("skudesc").toString());
                viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
                viewm.setUom(map.get("skuom").toString());

                items.add(viewm);
            }
        }
        
        this.RefreshItems();
        
        this.pendingbtn.setDisable(true);
        this.editbtn.setDisable(true);
        this.resetbtn.setDisable(true);
        
        if(dr.getPgi().equals("Y") || dr.getStatus().equals("cancelled")){
            this.pgibtn.setDisable(true);
            this.canceldrbtn.setDisable(true);
        }
        
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
            System.out.println(temp.getDritemid());
            this.items.add(temp);
            this.RefreshItems();
        }
        
        this.RefreshItems();

    }

    @FXML
    public void ResetItems(ActionEvent event) {
        for(int i = 0; i < this.items.size(); i++){
            this.items.get(i).setDeliveryqty(0);
        }
        this.RefreshItems();
    }

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        DRModel drm = new DRModel(null);
        drm.setCby(this.getGlobalUser().getId());
        drm.setCustid(Integer.valueOf(this.model.getIdcustomer()));
        drm.setSoid(this.sovm.getIdso());
        drm.setDrdate(Date.valueOf(this.drdatefld.getValue()));
        drm.setDrprint(this.prntdfld.getText());
        drm.setDrstatus(this.statusfld.getText());
        drm.setPgi(this.pgifld.getText());
        drm.setRemarks(this.rmksfld.getText());
        ArrayList<DRItemsModel> dritems = new ArrayList();
        for(DRItemViewModel drvm : itemlist.getItems()){
            DRItemsModel drit = new DRItemsModel();
            drit.setDritemid(drvm.getDritemid());
            drit.setDrqty(drvm.getDeliveryqty());
            drit.setSiditem(drvm.getIdsoitem());

            dritems.add(drit);
        }
        drm.setDritems(dritems);
        
        if(isEdit){
            drm.setDrid(Integer.valueOf(this.dridfld.getText()));
            drq.editDR(drm);
            drq.editDRItems(dritems.iterator());
        }else{

            drq.addDeliveryReceiptRet(drm);
            if(!this.sovm.getStatus().equals("open")){
                soq.changeStatSalesOrder(this.sovm.getIdso(), Integer.parseInt(this.model.getIdcustomer()), "With DR");
            }
                
        }
        
        
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }
    
    private boolean checkItemsifNull(){
        
        boolean isAllNull = false;
        int numNull = 0;
        for(DRItemViewModel drvm : itemlist.getItems()){
            DRItemsModel drit = new DRItemsModel();
            if(drvm.getDeliveryqty() == 0){
                numNull += 1;
            }
        }
        
        if(numNull == itemlist.getItems().size()){
            isAllNull = true;
        }
        
        return isAllNull;
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
        super.setType(type);
    }
    
    @FXML
    void export(ActionEvent event) throws IOException, SQLException {
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
        
        String filename = "[DeliveryReceipt]" + this.model.getCompany() + "(" + this.dridfld.getText() + ")_" + datefld.getValue().toString().replace("/", "") + "-" + formattedDate.replaceAll("[\\s-:]","") + ".pdf";
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
        document.add(new Paragraph("\nSold To:" + this.model.getCompany() 
                + "\nBusiness: " + this.model.getBusinessstyle()
                + "\nCustomer PO: " + this.cpofld.getText()
                + "\nAdddress: " + this.model.getAddress()
        ).setFont(font));

        List list = new List()
            .setSymbolIndent(12)
            .setListSymbol("\u00a0")
            .setFont(font);

        for(int i = 0; i < this.items.size(); i++){
            list.add(new ListItem(this.items.get(i).getSku() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.items.get(i).getSkudesc() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.items.get(i).getDeliveryqty() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + "\n"));
        }
       
        // Add the list
        list.add(new ListItem("\n \u00a0 \u00a0 \u00a0 \u00a0 **************Nothing Follows**************"));
        document.add(list.setFont(font));
        
        document.close();
        
        drq.changeDRPrint(this.drm.getDrnum());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please check the export in My Documents/Export");

        alert.showAndWait();
        
        Stage stage = (Stage) this.printbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void PostToInventory(ActionEvent event) throws SQLException {
        
        SIModel sim = new SIModel();
                
        sim.setCby(super.getGlobalUser().getId());
        sim.setCustomerid(Integer.parseInt(model.getIdcustomer()));
        sim.setDrvnme("N/A");
        sim.setTrcnme("N/A");
        sim.setPlateno("N/A");
        sim.setDte(Date.valueOf(this.sovm.getSodate()));
        sim.setRemarks("N/A");
        sim.setSoidnvc(sovm.getIdso());
        sim.setPrntstat("N");
        sim.setStatus("open");
        
        Iterator ir = this.itemlist.getItems().iterator();
        ArrayList<SItemsModel> simods = new ArrayList();
        SItemsModel simod = new SItemsModel();
        simod.setDrid(this.drm.getDrnum() );
            
            simods.add(simod);
        
        sim.setSitems(simods);
        
        if(this.model.isAuto_create()){
            siq.addSalesInvoice(sim);
        }
        
        iq.updateInventories(this.itemlist.getItems().iterator(), super.getType());
        drq.changePGIStatusDR(this.drm.getDrnum());
        drq.changePGIStatusItems(this.itemlist.getItems().iterator());
        soq.changeStatSalesOrder(this.sovm.getIdso(), Integer.parseInt(this.model.getIdcustomer()), "Partially Delivered");

        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void viewCustomer(ActionEvent event) throws SQLException, IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/customer/CustomerView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        CustomerViewController cvc = fxmlloader.<CustomerViewController>getController();
        cvc.ViewMode(this.model.getCompany());

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
    void cancelDR(ActionEvent event) throws SQLException {
        drq.changeStatusDR(Integer.valueOf(this.dridfld.getText()));
        
        if(drq.getDRCount(this.sovm.getIdso()) == 0){
            System.out.println("pasok");
            soq.changeStatSalesOrder(this.sovm.getIdso(), Integer.valueOf(this.model.getIdcustomer()), "open");
        }
    }
}
