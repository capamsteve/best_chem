/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoices;

import best_chem.AbstractController;
import customer.CustomerViewController;
import dbquerries.DeliveryReceiptsQuery;
import dbquerries.SalesInvoiceQuery;
import dbquerries.SalesOrderQuery;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CustomerModel;
import models.DRModel;
import models.SIModel;
import models.SItemsModel;
import models.SOItemModel;
import models.UserModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import salesorder.SOItemsController;
import viewmodels.DRViewModel;
import viewmodels.SIViewModel;
import viewmodels.SOViewModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class SalesInvoiceController extends AbstractController implements Initializable {
    
    @FXML
    private Button vcbtn;
    
    @FXML
    private TextField pltfld;

    @FXML
    private Button editbtn;

    @FXML
    private TextField trckrfld;

    @FXML
    private DatePicker drdatefld;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField soidfld;

    @FXML
    private TableView<DRViewModel> itemlist;
    
    private ArrayList<DRViewModel> items = new ArrayList();
    
    @FXML
    private TableView<SOItemModel> itemlist1;
    
    private ArrayList<SOItemModel> items1 = new ArrayList();
    
    @FXML
    private TextField cpofld;

    @FXML
    private Button resetbtn;

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
    
    @FXML
    private Button deletebtn;
    
    @FXML
    private Button printbtn;
    
    @FXML
    private Button completebtn;
    
    private final SalesInvoiceQuery siq = new SalesInvoiceQuery();
    
    private final DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();
    
    private final SalesOrderQuery soq = new SalesOrderQuery();
    
    private ArrayList<DRViewModel> deletedList = new ArrayList();
    
    private CustomerModel model;
    
    private SOViewModel sovm;
    
    private SIViewModel sivm;
    
    private boolean isEdit;
    
    @FXML
    private TextField totalfld;
    
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
        this.drdatefld.setValue(LocalDate.parse(this.sovm.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.soidfld.setText(String.valueOf(this.sovm.getIdso()));
        this.soidfld.setEditable(false);
        this.sodtefld.setValue(LocalDate.parse(this.sovm.getSodate()));
        this.sodtefld.setEditable(false);
        this.vendrfld.setText(this.model.getVendor_code());
        this.vendrfld.setEditable(false);
        this.statusfld.setText("open");
        this.statusfld.setEditable(false);  
        this.prntfld.setText("N");
        this.prntfld.setEditable(false);
        this.completebtn.setDisable(true);
        this.printbtn.setDisable(true);
        
        Iterator map2 = soq.getSILineItems(this.sovm.getIdso());
        
        while(map2.hasNext()){
            HashMap temp2 = (HashMap) map2.next();
            
            //System.out.println(temp.get("iddeliver").toString());
            
            SOItemModel soitem = new SOItemModel(Integer.valueOf(temp2.get("idinventory").toString()));
            
            soitem.setSku(temp2.get("sku").toString());
            soitem.setDesc(temp2.get("skudesc").toString());
            soitem.setUom(temp2.get("skuom").toString());
            soitem.setQty(0);
            soitem.setDiscnt(Double.valueOf(temp2.get("discnt").toString()));
            soitem.setUprice(Double.valueOf(temp2.get("unitprice").toString()));
            soitem.setAmount(0.0);
            soitem.setVat(0.0);
            
            this.items1.add(soitem);
        }
        
        this.RefreshItems();
        this.RefreshLineItems();
    }
    
    public void EditMode(SIViewModel simod) throws SQLException{
        SIModel sim = siq.getSalesInvoiceModel(simod.getInvnum());
        
        this.isEdit = true;
        this.sivm = simod;
        this.sidfld.setText(String.valueOf(simod.getInvnum()));
        this.sidfld.setEditable(false);
        this.cpofld.setText(this.sovm.getCustomerpo());
        this.cpofld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(this.sovm.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.soidfld.setText(String.valueOf(this.sovm.getIdso()));
        this.soidfld.setEditable(false);
        this.sodtefld.setValue(LocalDate.parse(this.sovm.getSodate()));
        this.sodtefld.setEditable(false);
        this.vendrfld.setText(this.model.getVendor_code());
        this.vendrfld.setEditable(false);
        
        this.trckrfld.setText(sim.getTrcnme());
        this.drvfld.setText(sim.getDrvnme());
        this.pltfld.setText(sim.getPlateno());
        this.rmksfld.setText(sim.getRemarks());
        this.datefld.setValue(LocalDate.parse(sim.getDte().toString()));
        
        this.statusfld.setText("open");
        this.statusfld.setEditable(false);
        
        this.prntfld.setText("N");
        this.prntfld.setEditable(false);
        
        this.printbtn.setDisable(true);
        this.completebtn.setDisable(true);
        
        Iterator map2 = soq.getSILineItems(this.sovm.getIdso());
        
        while(map2.hasNext()){
            HashMap temp2 = (HashMap) map2.next();
            
            //System.out.println(temp.get("iddeliver").toString());
            
            SOItemModel soitem = new SOItemModel(Integer.valueOf(temp2.get("inventory_idinventory").toString()));
            
            soitem.setSku(temp2.get("sku").toString());
            soitem.setDesc(temp2.get("skudesc").toString());
            soitem.setUom(temp2.get("skuom").toString());
            soitem.setQty(0);
            soitem.setDiscnt(Double.valueOf(temp2.get("discnt").toString()));
            soitem.setUprice(Double.valueOf(temp2.get("unitprice").toString()));
            soitem.setAmount(Double.valueOf(temp2.get("amount").toString()));
            soitem.setVat(Double.valueOf(temp2.get("vat").toString()));
            
            this.items1.add(soitem);
        }
        
        Iterator ir = siq.getSitems(sim.getSiid());
        
        while(ir.hasNext()){
            HashMap temp = (HashMap) ir.next();
            
            DRViewModel drvm = new DRViewModel(Integer.valueOf(temp.get("iddeliveryorders").toString()));
            
            drvm.setDrdate(temp.get("drdate").toString());
            drvm.setPgi(temp.get("drpgi").toString());
            drvm.setPrnt(temp.get("drprint").toString());
            drvm.setStatus(temp.get("drstatus").toString());
            drvm.setSiitem_id(Integer.valueOf(temp.get("idsalesinvoiceitems").toString()));
            
            this.items.add(drvm);
            
            //Get the line items
            
            this.updateLineItems(drvm.getDrnum(), 1);
            
        }
        
        this.RefreshItems();
        this.RefreshLineItems();
        this.computeTotal(this.items1.iterator());
        
    }
    
    public void updateLineItems(int drid, int tp) throws SQLException{
        Iterator iterate = null;
        
        iterate = drq.getDRItems(drid);

        while(iterate.hasNext()){
            HashMap map = (HashMap) iterate.next();

            SOItemModel viewm = new SOItemModel(Integer.valueOf(map.get("idinventory").toString()));

            int dex = this.items1.indexOf(viewm);
            System.out.println(this.items1.get(dex).getSku() + "-" + this.items1.get(dex).getIdinventory());
            int tempQty = 0;
            if(tp == 1){
                tempQty = this.items1.get(dex).getQty() + Integer.parseInt(map.get("drqty").toString());
            }else{
                tempQty = this.items1.get(dex).getQty() - Integer.parseInt(map.get("drqty").toString());
            }

            double amt = tempQty * this.items1.get(dex).getUprice();
            double vatVal = 1 + (this.model.getVAT() / 100);
            double vat = amt * vatVal;
            vat = Math.round(vat * 100.0)/100.0;
            
            this.items1.get(dex).setQty(tempQty);
            this.items1.get(dex).setAmount(amt);
            this.items1.get(dex).setVat(vat);

        }
    }
    
    public void ViewMode(SIViewModel simod) throws SQLException{
        
        SIModel sim = siq.getSalesInvoiceModel(simod.getInvnum());
        
        this.sidfld.setText(String.valueOf(simod.getInvnum()));
        this.sidfld.setEditable(false);
        this.cpofld.setText(this.sovm.getCustomerpo());
        this.cpofld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(this.sovm.getSodrdate()));
        this.drdatefld.setEditable(false);
        this.soidfld.setText(String.valueOf(this.sovm.getIdso()));
        this.soidfld.setEditable(false);
        this.sodtefld.setValue(LocalDate.parse(this.sovm.getSodate()));
        this.sodtefld.setEditable(false);
        this.vendrfld.setText(this.model.getVendor_code());
        this.vendrfld.setEditable(false);
        
        this.trckrfld.setEditable(false);
        this.trckrfld.setText(sim.getTrcnme());
        this.drvfld.setEditable(false);
        this.drvfld.setText(sim.getDrvnme());
        this.pltfld.setEditable(false);
        this.pltfld.setText(sim.getPlateno());
        this.rmksfld.setEditable(false);
        this.rmksfld.setText(sim.getRemarks());
        this.datefld.setValue(LocalDate.parse(sim.getDte().toString()));
        
        this.statusfld.setText("open");
        this.statusfld.setEditable(false);
        
        this.prntfld.setText("N");
        this.prntfld.setEditable(false);
        
        this.editbtn.setDisable(true);
        this.deletebtn.setDisable(true);
        this.resetbtn.setDisable(true);
        
        Iterator map2 = soq.getSILineItems(this.sovm.getIdso());
        
        while(map2.hasNext()){
            HashMap temp2 = (HashMap) map2.next();
            
            //System.out.println(temp.get("iddeliver").toString());
            
            SOItemModel soitem = new SOItemModel(Integer.valueOf(temp2.get("inventory_idinventory").toString()));
            
            soitem.setSku(temp2.get("sku").toString());
            soitem.setDesc(temp2.get("skudesc").toString());
            soitem.setUom(temp2.get("skuom").toString());
            soitem.setQty(0);
            soitem.setDiscnt(Double.valueOf(temp2.get("discnt").toString()));
            soitem.setUprice(Double.valueOf(temp2.get("unitprice").toString()));
            soitem.setAmount(Double.valueOf(temp2.get("amount").toString()));
            soitem.setVat(Double.valueOf(temp2.get("vat").toString()));
            
            this.items1.add(soitem);
        }
        
        Iterator ir = siq.getSitems(sim.getSiid());
        
        while(ir.hasNext()){
            HashMap temp = (HashMap) ir.next();
            
            DRViewModel drvm = new DRViewModel(Integer.valueOf(temp.get("iddeliveryorders").toString()));
            
            drvm.setDrdate(temp.get("drdate").toString());
            drvm.setPgi(temp.get("drpgi").toString());
            drvm.setPrnt(temp.get("drprint").toString());
            drvm.setStatus(temp.get("drstatus").toString());
            
            this.items.add(drvm);
            
            //Get the line items
            
            this.updateLineItems(drvm.getDrnum(), 1);
            
        }
        
        this.RefreshItems();
        this.RefreshLineItems();
        this.computeTotal(this.items1.iterator());
        
        if(simod.getPrint().equals("Y")){
            this.printbtn.setText("Re-print");
        }
        this.pendingbtn.setDisable(true);
        
    }
    
    @FXML
    void EditItem(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesinvoices/DRSelectorView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        DRSelectorController drc = fxmlloader.<DRSelectorController>getController();
        drc.setDRs(this.sovm.getIdso());
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.editbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Delivery Order");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        DRModel mod = drc.getDrm_og();
        DRViewModel model = new DRViewModel(mod.getDrid());
        model.setDrdate(mod.getDrdate().toString());
        model.setPgi(mod.getPgi());
        model.setPrnt(mod.getDrprint());
        model.setStatus(mod.getDrstatus());
        
        if(!this.items.contains(model)){
            this.items.add(model);
            this.updateLineItems(model.getDrnum(), 1);
            this.RefreshItems();
            this.RefreshLineItems();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("This delivery receipt has been added.");
            alert.showAndWait();
        }
        
    }

    @FXML
    void ResetItems(ActionEvent event) throws SQLException {
        
        if(isEdit){
            if(!this.items.isEmpty()){
                for(DRViewModel drvm: this.items){
                    if(drvm.getSiitem_id() != 0){
                        this.deletedList.add(drvm);
                    }
                }
            }
        }
        else{
            if(!this.items.isEmpty()){
                this.items.clear();
                for(int i = 0; i < this.items1.size(); i++){
                    this.items1.get(i).setQty(0);
                    this.items1.get(i).setAmount(0.0);
                    this.items1.get(i).setVat(0.0);
                }
                this.RefreshLineItems();
                this.RefreshItems();
            }
            
            
        }
        
    }
    
    @FXML
    void DeleteItem(ActionEvent event) throws SQLException {

        if(isEdit){
            if(!this.items.isEmpty()){
                DRViewModel drvm = this.items.remove(this.itemlist.getSelectionModel().getFocusedIndex());
                this.deletedList.add(drvm);
                this.updateLineItems(drvm.getDrnum(), 2);
                this.RefreshLineItems();
                this.RefreshItems();
            }
        }else{
            if(!this.items.isEmpty()){
                DRViewModel drvm = this.items.remove(this.itemlist.getSelectionModel().getFocusedIndex());
                this.updateLineItems(drvm.getDrnum(), 2);
                this.RefreshLineItems();
                this.RefreshItems();
            }
        }
        
    }
    
    public void RefreshLineItems() throws SQLException{
        String[] arr2 = {"sku", "desc", "qty", "uom", "uprice", "discnt", "amount", "vat"};
        
        ObservableList<SOItemModel> data2
                = FXCollections.observableArrayList();
        
        data2.addAll(this.items1);
        
        ObservableList<TableColumn<SOItemModel, ?>> olist2;
        olist2 = (ObservableList<TableColumn<SOItemModel, ?>>) this.itemlist1.getColumns();

        for (int i = 0; i < arr2.length; i++) {
            olist2.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr2[i])
            );
        }
        
        this.itemlist1.getItems().clear();
        this.itemlist1.setItems(data2);
        this.computeTotal(this.items1.iterator());
    }
    
    public void RefreshItems(){
        String[] arr = {"drnum", "drdate", "prnt", "pgi", "status"};
        
        ObservableList<DRViewModel> data
                = FXCollections.observableArrayList();
        
        data.addAll(this.items);
        
        ObservableList<TableColumn<DRViewModel, ?>> olist;
        olist = (ObservableList<TableColumn<DRViewModel, ?>>) this.itemlist.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.itemlist.setItems(data);
        
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
        ArrayList<SItemsModel> addinEdit = new ArrayList();
        if(isEdit){
            salesinvoice.setSiid(Integer.valueOf(this.sidfld.getText()));
            siq.editSalesInvoice(salesinvoice);
            if(!this.deletedList.isEmpty()){
                System.out.println("HELLO");
                Iterator ir2 = this.deletedList.iterator();
                ArrayList<SItemsModel> delsims = new ArrayList();
                while(ir2.hasNext()){
                    DRViewModel mod2 = (DRViewModel) ir2.next();
                    SItemsModel simod2 = new SItemsModel();
                    simod2.setSitemid(mod2.getSiitem_id());
                    System.out.println(mod2.getSiitem_id());
                    delsims.add(simod2);
                }
                
                siq.deleteInvoiceItems(delsims.iterator());
            }
            if(!this.items.isEmpty()){
                for(int i = 0; i < this.items.size(); i++){
                    if(this.items.get(i).getSiitem_id() == 0){
                        SItemsModel simod = new SItemsModel();
                        simod.setDrid(this.items.get(i).getDrnum());
                        addinEdit.add(simod);
                    }
                }
                
                siq.addSalesInvoiceItems(addinEdit.iterator(), Integer.valueOf(this.sidfld.getText()));
            }
        }else{
            siq.addSalesInvoice(salesinvoice);
        }
        
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    
    @FXML
    void export(ActionEvent event) throws IOException, SQLException, URISyntaxException {
        
        NumberFormat nf= NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.CEILING);
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Sales Invoice Printing");
        dialog.setHeaderText("Please input your sales number");

        Optional<String> result = dialog.showAndWait();

        // TODO code application logic here
        //URL url = getClass().getResource("/res/invoicesample.xlsx");
        //File f = new File(url.toURI());
        FileInputStream file = new FileInputStream("C:\\res\\invoicesample.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow sheetrow = null;
        Cell cell = null;
        int rownum = 0;
        int cellnum = 0;
        /**
         * UPDATE THE HEADERS
         *           Row   col
         * Date      2     9  
         * Company name 5  0
         * Business type 5 6
         * PO number    5  8
         * 
         * address   7 0
         * vendor code: 7 1
         * 
         * Start of line Items:
         * 
         * Row 10             ends at row 36
         * 
         * column 0 to 9
         * 
         * Values start at
         * 
         * total sales less discount : 37 9
         * vatable sales             : 38 9
         * Zero rated sales          : 39 9
         * Vat exempt                : 40 9
         * 
         * Sales Invoice id          : 42 8
         * 
         */
        
        //Date
        rownum = 2;
        cellnum = 9;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.datefld.getValue().toString());

        //Company Name
        rownum = 5;
        cellnum = 0;
        String comname = "";
        for(int i = 0; i < 24; i++){
            comname += "\u00a0 ";
        }
        comname += this.model.getCompany();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, comname);
        
        //Business Type
        rownum = 5;
        cellnum = 6;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.model.getBusinessstyle());
        
        //CPO
        rownum = 5;
        cellnum = 8;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.cpofld.getText());
        
        //Address
        rownum = 7;
        cellnum = 0;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.model.getAddress());
        
        //Vendor Code
        rownum = 7;
        cellnum = 1;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.model.getVendor_code());
        
        //Items
        int start = 10;
        XSSFCellStyle txtstyle = workbook.createCellStyle();
        XSSFFont txtfont = workbook.createFont();
        txtfont.setFontName("Calibri");
        txtfont.setFontHeightInPoints((short)10);
        txtstyle.setFont(txtfont);
        for(int i = 0; i < this.itemlist1.getItems().size(); i++){
            
            rownum = start;
            cellnum = 0;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.itemlist1.getItems().get(i).getSku(), txtstyle);
            
            rownum = start;
            cellnum = 2;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.itemlist1.getItems().get(i).getDesc(), txtstyle);
            
            rownum = start;
            cellnum = 6;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, String.valueOf(this.itemlist1.getItems().get(i).getQty()), txtstyle);
            
            rownum = start;
            cellnum = 7;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, nf.format(this.itemlist1.getItems().get(i).getUprice()), txtstyle);
            
            rownum = start;
            cellnum = 9;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, nf.format(this.itemlist1.getItems().get(i).getAmount()), txtstyle);
            
            start++;
        }
        
        rownum = start;
        cellnum = 2;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, "********NOTHING FOLLOWS********", txtstyle);
        
        //Less Discount
        rownum = 37;
        cellnum = 9;
        Double lessdcnt = Double.parseDouble(this.totalfld.getText()) * (this.model.getDiscount() / 100);
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, nf.format(lessdcnt));

        //Total Sales
        rownum = 38;
        cellnum = 9;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, nf.format(Double.parseDouble(this.totalfld.getText())));

        //Vatable
        rownum = 39;
        cellnum = 9;
        Double vatable = Double.parseDouble(this.totalfld.getText()) - lessdcnt;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, nf.format(vatable));
        
        //User Name
        rownum = 38;
        cellnum = 2;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, super.getGlobalUser().getUsername());

        //Vatable less
        rownum = 40;
        cellnum = 9;
        Double vatableless = vatable * (1 + (this.model.getVAT()/ 100));
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, nf.format(vatableless));

        //Sales Invoice ID:
        rownum = 42;
        cellnum = 8;
        
        String siid = "No. " + result.get();
        
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, siid);
        
        file.close();
        
        String currentUsersHomeDir = System.getProperty("user.home");
        File dir = new File(currentUsersHomeDir + "\\Documents\\Exports");
        if(!dir.exists()){
            System.out.println("Directory Created");
            dir.mkdir();
        }
        File file2 = new File(dir.getAbsolutePath()+ "\\" + "invoicesample.xlsx");
        if(!file2.exists()){
            file2.createNewFile();
        }
        
        FileOutputStream outFile =new FileOutputStream(file2);
        workbook.write(outFile);
        outFile.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please check the export in My Documents/Export");

        alert.showAndWait();
        
        if (result.isPresent()) {
            siq.Print(Integer.parseInt(result.get()), Integer.parseInt(this.sidfld.getText()), this.printbtn.getText());
            siq.changePrintStatus(Integer.parseInt(this.sidfld.getText()));
        }
        
        Stage stage = (Stage) this.printbtn.getScene().getWindow();
        stage.close();
    }
    
    private void createCell(XSSFRow sheetrow, XSSFSheet sheet, Cell cell, int rownum, int cellnum, String value){
        sheetrow = sheet.getRow(rownum);
        sheetrow = this.checkRow(sheetrow, sheet, rownum);
        
        cell = sheetrow.getCell(cellnum);
        cell = this.checkCell(cell, sheetrow, cellnum);
        cell.setCellValue(value);
    }
    
    private void createCell(XSSFRow sheetrow, XSSFSheet sheet, Cell cell, int rownum, int cellnum, String value, XSSFCellStyle txtstyle){
        sheetrow = sheet.getRow(rownum);
        sheetrow = this.checkRow(sheetrow, sheet, rownum);
        
        cell = sheetrow.getCell(cellnum);
        cell = this.checkCell(cell, sheetrow, cellnum);
        cell.setCellValue(value);
        cell.setCellStyle(txtstyle);
    }
    
    private XSSFRow checkRow(XSSFRow sheetrow, XSSFSheet sheet, int rownum){
        if(sheetrow == null){
            sheetrow = sheet.createRow(rownum);
        }
        
        return sheetrow;
    }
    
    private Cell checkCell(Cell cell, XSSFRow sheetrow, int cellnum){
        if(cell == null){
            cell = sheetrow.createCell(cellnum);
        }
        
        return cell;
    }
    
    @FXML
    void completeHandler(ActionEvent event) throws SQLException {
        siq.changeStatus(Integer.valueOf(this.sidfld.getText()));
        
        Stage stage = (Stage) this.completebtn.getScene().getWindow();
        stage.close();
        
    }
    
    public void computeTotal(Iterator ir){
        
        double total = 0.0;
        
        while(ir.hasNext()){
            SOItemModel model = (SOItemModel) ir.next();
            
            total += model.getAmount();
        }
        this.totalfld.setText(String.valueOf(total));
    }
    
    @FXML
    void viewCustomer(ActionEvent event) throws IOException, SQLException {
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
}
