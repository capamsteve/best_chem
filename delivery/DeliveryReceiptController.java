/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

import best_chem.AbstractController;
import customer.CustomerViewController;
import dbquerries.DeliveryReceiptsQuery;
import dbquerries.InventoryQuery;
import dbquerries.SalesInvoiceQuery;
import dbquerries.SalesOrderQuery;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
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
import models.InventoryModel;
import models.SIModel;
import models.SItemsModel;
import models.UserModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    
    private boolean isCancelled = true;
    
    private final DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();
    
    private final SalesInvoiceQuery siq = new SalesInvoiceQuery();
    
    private final SalesOrderQuery soq = new SalesOrderQuery();
    
    private final InventoryQuery iq = new InventoryQuery();
    
    private DRViewModel drm;
    
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
        iterate = drq.getSalesOrderItems(this.sovm.getIdso(), "OPEN");
        
        while(iterate.hasNext()){
            HashMap map = (HashMap) iterate.next();
            DRItemViewModel viewm = new DRItemViewModel(Integer.valueOf(map.get("idinventory").toString()));
            viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
            viewm.setSku(map.get("sku").toString());

            if(Integer.valueOf(map.get("soh").toString()) >= Integer.parseInt(map.get("ordrqty").toString())){
                viewm.setDeliveryqty(Integer.parseInt(map.get("ordrqty").toString()));
            }else{
                viewm.setDeliveryqty(Integer.parseInt(map.get("soh").toString()));
            }

            viewm.setQtyremaining(Integer.parseInt(map.get("ordrqty").toString()));
            viewm.setSkudesc(map.get("skudesc").toString());
            viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
            viewm.setUom(map.get("skuom").toString());
            viewm.setSoh(Integer.parseInt(map.get("soh").toString()));

            this.items.add(viewm);
        }
        
        Iterator iterate2 = drq.getSalesOrderItemsWRemaining(this.sovm.getIdso());
        
        while(iterate2.hasNext()){
            HashMap map = (HashMap) iterate2.next();
            DRItemViewModel viewm = new DRItemViewModel(Integer.valueOf(map.get("idinventory").toString()));
            int dex = this.items.indexOf(viewm);
            
            this.items.get(dex).setDeliveryqty(Double.valueOf(map.get("Remaining_Quantity").toString()).intValue());
            this.items.get(dex).setQtyremaining(Double.valueOf(map.get("Remaining_Quantity").toString()).intValue());
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
        
        Iterator iterate = null;
        
        iterate = drq.getDRItems(dr.getDrnum());

        while(iterate.hasNext()){
            HashMap map = (HashMap) iterate.next();
            DRItemViewModel viewm = new DRItemViewModel(Integer.valueOf(map.get("idinventory").toString()));
            viewm.setDritemid(Integer.valueOf(map.get("iddeliveryorderitems").toString()));
            viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
            viewm.setSku(map.get("sku").toString());
            viewm.setDeliveryqty(Integer.parseInt(map.get("drqty").toString()));
            viewm.setSkudesc(map.get("skudesc").toString());
            viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
            viewm.setUom(map.get("skuom").toString());
            viewm.setSoh(Integer.parseInt(map.get("soh").toString()));

            viewm.setQtyremaining(Integer.parseInt(map.get("ordrqty").toString()));

            this.items.add(viewm);
        }
        
        Iterator iterate2 = drq.getSalesOrderItemsWRemaining(this.sovm.getIdso());
        
        while(iterate2.hasNext()){
            HashMap map = (HashMap) iterate2.next();
            DRItemViewModel viewm = new DRItemViewModel(Integer.valueOf(map.get("idinventory").toString()));
            int dex = this.items.indexOf(viewm);
            
            this.items.get(dex).setQtyremaining(Double.valueOf(map.get("Remaining_Quantity").toString()).intValue());
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
        
        iterate = drq.getDRItems(dr.getDrnum());

        while(iterate.hasNext()){
            HashMap map = (HashMap) iterate.next();
            DRItemViewModel viewm = new DRItemViewModel(Integer.valueOf(map.get("idinventory").toString()));
            viewm.setDritemid(Integer.valueOf(map.get("iddeliveryorderitems").toString()));
            viewm.setIdsoitem(Integer.valueOf(map.get("idsalesorderitem").toString()));
            viewm.setSku(map.get("sku").toString());
            viewm.setDeliveryqty(Integer.parseInt(map.get("drqty").toString()));
            viewm.setSkudesc(map.get("skudesc").toString());
            viewm.setOrdrqty(Integer.parseInt(map.get("ordrqty").toString()));
            viewm.setUom(map.get("skuom").toString());
            viewm.setSoh(Integer.parseInt(map.get("soh").toString()));

            viewm.setQtyremaining(Integer.parseInt(map.get("ordrqty").toString()));

            this.items.add(viewm);
        }
        
        Iterator iterate2 = drq.getSalesOrderItemsWRemaining(this.sovm.getIdso());
        
        while(iterate2.hasNext()){
            HashMap map = (HashMap) iterate2.next();
            DRItemViewModel viewm = new DRItemViewModel(Integer.valueOf(map.get("idinventory").toString()));
            int dex = this.items.indexOf(viewm);
            this.items.get(dex).setQtyremaining(Double.valueOf(map.get("Remaining_Quantity").toString()).intValue());
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
        String[] arr = {"sku", "skudesc", "ordrqty1", "deliveryqty1", "qtyremaining1", "uom", "soh1"};
        this.itemlist.getItems().removeAll(this.itemlist.getItems());
        ObservableList<DRItemViewModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.items.size(); i++){
            
            this.items.get(i).setDeliveryqty1();
            this.items.get(i).setOrdrqty1();
            this.items.get(i).setQtyremaining1();
            this.items.get(i).setSoh1();
            data.add(this.items.get(i));
        }
        ObservableList<TableColumn<DRItemViewModel, ?>> olist = (ObservableList<TableColumn<DRItemViewModel, ?>>) itemlist.getColumns();
        
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
            
            if(i == 2 || i == 3 || i == 4){
                olist.get(i).setStyle("-fx-alignment: CENTER-RIGHT;");
            }
            else if(i == 5){
                olist.get(i).setStyle("-fx-alignment: CENTER;");
            }
            else{
                olist.get(i).setStyle("-fx-alignment: CENTER-LEFT;");
            }
        }
        
        this.itemlist.setItems(data);
    }

    @FXML
    public void EditItem(ActionEvent event) throws IOException {
        
        if(this.itemlist.getSelectionModel().getSelectedItem().getQtyremaining() != 0){
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
                this.items.get(this.itemlist.getSelectionModel().getSelectedIndex()).setDeliveryqty(edqc.getQty());

                this.itemlist.getItems().clear();
                this.RefreshItems();
            }
        
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("This item is complete");

            alert.showAndWait();
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
        
        this.isCancelled = false;
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }
    
    @FXML
    public void cancelHandler(ActionEvent event) {
        this.isCancelled = true;
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
        NumberFormat nf= NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);
        
        FileInputStream file = new FileInputStream("C:\\res\\drform.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(1);
        XSSFRow sheetrow = null;
        Cell cell = null;
        int rownum = 0;
        int cellnum = 0;
        
        /**
         * 
         * UPDATE HEADERS
         *              ROW     COL
         * SOLD TO      7       1
         * DATE         7       3
         * address      8       1
         * attention    9       1
         * NO           9       3
         * 
         * Items
         *      12-32
         * 
         * 
         */
        
        //Supplier
        rownum = 7;
        cellnum = 1;
        String str = "SOLD TO: " + this.model.getCompany();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str);
        
        //Date
        rownum = 7;
        cellnum = 3;
        String str3 = ""; 
        for(int i = 0; i < 20; i++){
            str3 += "\u00a0 ";
        }
        str3 += "DATE: ";
        for(int i = 0; i < 18; i++){
            str3 += "\u00a0 ";
        }
        str3 += this.datefld.getValue().toString();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str3);
        
        //Address
        rownum = 8;
        cellnum = 1;
        String str2 = "ADDRESS: " + this.model.getAddress();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str2);
        
        XSSFCellStyle txtstyle3 = workbook.createCellStyle();
        XSSFFont txtfont1 = workbook.createFont();
        txtfont1.setFontName("Calibri");
        txtfont1.setFontHeightInPoints((short)14);
        txtfont1.setBold(true);
        txtstyle3.setFont(txtfont1);
        txtstyle3.setAlignment(HorizontalAlignment.RIGHT);
        
        //DR num
        rownum = 9;
        cellnum = 3;
        String str4 = "P.O No. " + this.dridfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str4, txtstyle3);
        
        //Items
        int start = 12;
        XSSFCellStyle txtstyle = workbook.createCellStyle();
        XSSFFont txtfont = workbook.createFont();
        txtfont.setFontName("Calibri");
        txtfont.setFontHeightInPoints((short)11);
        txtstyle.setFont(txtfont);
        txtstyle.setBorderBottom(BorderStyle.THIN);
        txtstyle.setBorderTop(BorderStyle.THIN);
        txtstyle.setBorderRight(BorderStyle.THIN);
        txtstyle.setBorderLeft(BorderStyle.THIN);
        
        XSSFCellStyle txtstyle5 = workbook.createCellStyle();
        txtstyle5.setFont(txtfont);
        txtstyle5.setAlignment(HorizontalAlignment.RIGHT);
        txtstyle5.setBorderBottom(BorderStyle.THIN);
        txtstyle5.setBorderTop(BorderStyle.THIN);
        txtstyle5.setBorderRight(BorderStyle.THIN);
        txtstyle5.setBorderLeft(BorderStyle.THIN);
        
        XSSFCellStyle txtstyle6 = workbook.createCellStyle();
        txtstyle6.setFont(txtfont);
        txtstyle6.setAlignment(HorizontalAlignment.CENTER);
        txtstyle6.setBorderBottom(BorderStyle.THIN);
        txtstyle6.setBorderTop(BorderStyle.THIN);
        txtstyle6.setBorderRight(BorderStyle.THIN);
        txtstyle6.setBorderLeft(BorderStyle.THIN);
        
        for(int x = 0; x < this.items.size(); x++){
            
            if(this.items.get(x).getDeliveryqty() != 0){
                rownum = start;
                cellnum = 1;
                this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items.get(x).getDeliveryqty1(), txtstyle5);

                rownum = start;
                cellnum = 2;
                this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items.get(x).getUom(), txtstyle6);

                rownum = start;
                cellnum = 3;
                this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items.get(x).getSkudesc(), txtstyle);

                start++;
            }
            
        }
        
        XSSFCellStyle txtstyle7 = workbook.createCellStyle();
        XSSFFont txtfont4 = workbook.createFont();
        txtfont4.setFontName("Calibri");
        txtfont4.setFontHeightInPoints((short)12);
        txtstyle7.setFont(txtfont4);
        txtstyle7.setBorderBottom(BorderStyle.THIN);
        txtstyle7.setBorderTop(BorderStyle.THIN);
        txtstyle7.setBorderRight(BorderStyle.THIN);
        txtstyle7.setBorderLeft(BorderStyle.THIN);
        
        if(this.items.size() != 21){
            rownum = start;
            cellnum = 3;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, "******************NOTHING FOLLOWS******************", txtstyle7);
        }
        
        XSSFCellStyle txtstyle8 = workbook.createCellStyle();
        txtstyle8.setFont(txtfont);
        
        rownum = 35;
        cellnum = 1;
        String prepared = "Prepared By:    " + super.getGlobalUser().getName();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, prepared, txtstyle8);
        
        //DR num
        rownum = 37;
        cellnum = 3;
        String str5 = "";
        for(int i = 0; i < 18; i++){
            str5 += "\u00a0 ";
        }        
        str5 += "NO. " + this.dridfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str5);
        
        file.close();
        
        String currentUsersHomeDir = System.getProperty("user.home");
        File dir = new File(currentUsersHomeDir + "\\Documents\\Exports");
        if(!dir.exists()){
            System.out.println("Directory Created");
            dir.mkdir();
        }
        
        String filename = "[DR]" + this.model.getCompany() + "-(" + this.drm.getDrnum() + ").xlsx";
        
        File file2 = new File(dir.getAbsolutePath()+ "\\" + filename);
        if(!file2.exists()){
            file2.createNewFile();
        }
        
        FileOutputStream outFile =new FileOutputStream(file2);
        workbook.write(outFile);
        outFile.close();
        
        drq.changeDRPrint(this.drm.getDrnum());

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
    void PostToInventory(ActionEvent event) throws SQLException {
        
        SIModel sim = new SIModel();
                
        sim.setCby(super.getGlobalUser().getId());
        sim.setCustomerid(Integer.parseInt(model.getIdcustomer()));
        sim.setDrvnme("N/A");
        sim.setTrcnme("N/A");
        sim.setPlateno("N/A");
        sim.setDte(Date.valueOf(LocalDate.now()));
        sim.setRemarks("N/A");
        sim.setSoidnvc(sovm.getIdso());
        sim.setPrntstat("N");
        sim.setStatus("open");
        
        Iterator ir = this.itemlist.getItems().iterator();
        ArrayList<SItemsModel> simods = new ArrayList();
        SItemsModel simod = new SItemsModel();
        simod.setDrid(this.drm.getDrnum());
            
        simods.add(simod);
        
        sim.setSitems(simods);
        
        if(this.model.isAuto_create()){
            siq.addSalesInvoice(sim);
        }
        
        ArrayList<InventoryModel> models = new ArrayList();
        for(DRItemViewModel item : items){
            InventoryModel im = new InventoryModel(item.getInventory_id());
            im.setSoh(item.getDeliveryqty());
            im.setMov("DEC");
            im.setRemarks("");
            models.add(im);
        }
        
        iq.PostUpdateInventory(models.iterator(), super.getGlobalUser().getUsername(), "DeliveryReceipt", Integer.valueOf(this.model.getIdcustomer()), this.model.getCompany(), "Customer", this.drm.getDrnum(), "", this.rmksfld.getText(), super.getType());
        drq.changePGIStatusDR(this.drm.getDrnum());
        drq.changeStatusDR(this.drm.getDrnum(), "complete");
        drq.changePGIStatusItems(this.itemlist.getItems().iterator());
        
        if(this.model.isAuto_create()){
            soq.changeStatSalesOrder(this.sovm.getIdso(), Integer.parseInt(this.model.getIdcustomer()), "complete");
        }else{
            /**
             * CHECK HERE IF ALL ITEMS HAVE BEEN SERVED
             */
            Iterator items = drq.getSalesOrderItemsWRemaining(this.sovm.getIdso());
            
            int item_count = 0;
            int item_actual = 0;
            while(items.hasNext()){
                HashMap map = (HashMap) items.next();
                int qwt = Double.valueOf(map.get("Remaining_Quantity").toString()).intValue();
                
                if(qwt == 0){
                    item_actual++;
                }
                item_count++;
            }
            
            if(item_count == item_actual){
                soq.changeStatSalesOrder(this.sovm.getIdso(), Integer.parseInt(this.model.getIdcustomer()), "complete");
            }else{ 
                soq.changeStatSalesOrder(this.sovm.getIdso(), Integer.parseInt(this.model.getIdcustomer()), "Partially Delivered");
            }
            
            
        }
        

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
        drq.changeStatusDR(Integer.valueOf(this.dridfld.getText()), "cancelled");
        
        if(drq.getDRCount(this.sovm.getIdso()) == 0){
            System.out.println("pasok");
            soq.changeStatSalesOrder(this.sovm.getIdso(), Integer.valueOf(this.model.getIdcustomer()), "open");
        }
        else {
            soq.changeStatSalesOrder(this.sovm.getIdso(), Integer.valueOf(this.model.getIdcustomer()), "With DR");
        }
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    /**
     * @return the isCancelled
     */
    public boolean isIsCancelled() {
        return isCancelled;
    }

    /**
     * @param isCancelled the isCancelled to set
     */
    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
