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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import models.SIModel;
import models.SItemsModel;
import models.SOItemModel;
import models.UserModel;
import org.apache.poi.ss.usermodel.Cell;
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
    
    private ArrayList<DRViewModel> items;
    
    @FXML
    private TableView<SOItemModel> itemlist1;
    
    private ArrayList<SOItemModel> items1;
    
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
    
    private CustomerModel model;
    
    private SOViewModel sovm;
    
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
        
        String[] arr = {"drnum", "drdate", "prnt", "pgi", "status"};
        
        ObservableList<DRViewModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = this.drq.getDeliverReceipts(this.sovm.getIdso(), "Y", "complete");
        
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
            
            SOItemModel soitem = new SOItemModel(Integer.valueOf(temp2.get("idinventory").toString()));
            
            soitem.setSku(temp2.get("sku").toString());
            soitem.setDesc(temp2.get("skudesc").toString());
            soitem.setUom(temp2.get("skuom").toString());
            soitem.setQty(Double.valueOf(temp2.get("sumdrqty").toString()).intValue());
            soitem.setDiscnt(this.model.getDiscount());
            soitem.setUprice(Double.valueOf(temp2.get("unitprice").toString()));
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
        this.computeTotal(data2.iterator());
    }
    
    public void EditMode(SIViewModel simod) throws SQLException{
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
        
        String[] arr = {"drnum", "drdate", "prnt", "pgi", "status"};
        
        ObservableList<DRViewModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = this.drq.getDeliverReceipts(this.sovm.getIdso(), "Y", "complete");
        
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
            
            SOItemModel soitem = new SOItemModel(Integer.valueOf(temp2.get("idinventory").toString()));
            
            soitem.setSku(temp2.get("sku").toString());
            soitem.setDesc(temp2.get("skudesc").toString());
            soitem.setUom(temp2.get("skuom").toString());
            soitem.setQty(Double.valueOf(temp2.get("sumdrqty").toString()).intValue());
            soitem.setDiscnt(this.model.getDiscount());
            soitem.setUprice(Double.valueOf(temp2.get("unitprice").toString()));
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
        
        this.pendingbtn.setDisable(true);
        this.printbtn.setDisable(true);
        
        System.out.println("Compute Total");
        this.computeTotal(data2.iterator());
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
        
        String[] arr = {"drnum", "drdate", "prnt", "pgi", "status"};
        
        ObservableList<DRViewModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = this.drq.getDeliverReceipts(this.sovm.getIdso(), "Y", "complete");
        
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
            
            SOItemModel soitem = new SOItemModel(Integer.valueOf(temp2.get("idinventory").toString()));
            
            soitem.setSku(temp2.get("sku").toString());
            soitem.setDesc(temp2.get("skudesc").toString());
            soitem.setUom(temp2.get("skuom").toString());
            soitem.setQty(Double.valueOf(temp2.get("sumdrqty").toString()).intValue());
            soitem.setDiscnt(this.model.getDiscount());
            soitem.setUprice(Double.valueOf(temp2.get("unitprice").toString()));
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
        
        this.pendingbtn.setDisable(true);
        
        if(simod.getPrint().equals("Y")){
            this.printbtn.setText("Re-print");
        }
        
        System.out.println("Compute Total");
        this.computeTotal(data2.iterator());
        
    }
    
    @FXML
    void EditItem(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesinvoices/DRSelectorView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        DRSelectorController drc = fxmlloader.<DRSelectorController>getController();

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.editbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Delivery Order");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
    }

    @FXML
    void ResetItems(ActionEvent event) {
    }
    
    @FXML
    void DeleteItem(ActionEvent event) {
        this.itemlist.getSelectionModel().getSelectedItem();
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
    
    
    @FXML
    void export(ActionEvent event) throws IOException, SQLException {
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Sales Invoice Printing");
        dialog.setHeaderText("Please input your sales number");

        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            siq.Print(Integer.parseInt(result.get()), Integer.parseInt(this.sidfld.getText()), this.printbtn.getText());
            siq.changePrintStatus(Integer.parseInt(this.sidfld.getText()));
        }
        
        // TODO code application logic here
        FileInputStream file = new FileInputStream("C:\\Users\\Steven\\Documents\\invoicesample.xlsx");

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
        for(int i = 0; i < this.itemlist1.getItems().size(); i++){
            
            rownum = start;
            cellnum = 0;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.itemlist1.getItems().get(i).getSku());
            
            rownum = start;
            cellnum = 2;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.itemlist1.getItems().get(i).getDesc());
            
            rownum = start;
            cellnum = 6;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, String.valueOf(this.itemlist1.getItems().get(i).getQty()));
            
            rownum = start;
            cellnum = 7;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, String.valueOf(this.itemlist1.getItems().get(i).getUprice()));
            
            rownum = start;
            cellnum = 9;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, String.valueOf(this.itemlist1.getItems().get(i).getAmount()));
            
            start++;
        }
        
        //Total Sales
        rownum = start;
        cellnum = 2;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, "********NOTHING FOLLOWS********");
        
        //Total Sales
        rownum = 37;
        cellnum = 9;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.totalfld.getText());

        //Discount
        rownum = 38;
        cellnum = 9;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.totalfld.getText());

        //Vatable
        rownum = 39;
        cellnum = 9;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.totalfld.getText());

        //Vatable less
        rownum = 40;
        cellnum = 9;
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.totalfld.getText());

        //Sales Invoice ID:
        rownum = 42;
        cellnum = 8;
        
        String siid = "No. " + result.get();
        
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, siid);

        
        file.close();
        
        FileOutputStream outFile =new FileOutputStream(new File("C:\\Users\\Steven\\Documents\\invoicesample2.xlsx"));
        workbook.write(outFile);
        outFile.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please check the export in My Documents/Export");

        alert.showAndWait();
        
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
