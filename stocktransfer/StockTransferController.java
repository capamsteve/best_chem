/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stocktransfer;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import dbquerries.StockTransferQuery;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import models.InventoryModel;
import models.UserModel;
import models.StockTransferModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class StockTransferController extends AbstractController implements Initializable {
    @FXML
    private TextField pofld;

    @FXML
    private TextField addressfld;

    @FXML
    private Button editbtn;

    @FXML
    private TextField driverfld;

    @FXML
    private TextField attentionfld;

    @FXML
    private Button cancelbtn;

    @FXML
    private DatePicker drdatefld;

    @FXML
    private TableView<InventoryModel> itemlist;

    @FXML
    private Button addbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private TextField refnumfld;

    @FXML
    private Button pgibtn;

    @FXML
    private TableView<InventoryModel> itemlist1;

    @FXML
    private TextField soldtofld;

    @FXML
    private Button resetbtn;

    @FXML
    private TextField platefld;

    @FXML
    private Button printbtn;

    @FXML
    private TextField idfld;

    @FXML
    private DatePicker datefld;

    @FXML
    private TextArea remarksfld;

    @FXML
    private TextField truckerfld;

    @FXML
    private TextArea stdescfld;

    @FXML
    private Button savebtn;
    private boolean isEdit = false;
    
    private ArrayList<InventoryModel> items = new ArrayList();
    private ArrayList<InventoryModel> deleted = new ArrayList();
    
    private ArrayList<InventoryModel> items2 = new ArrayList();
    private ArrayList<InventoryModel> deleted2 = new ArrayList();
    
    private StockTransferModel stm;
    
    private final InventoryQuery iq = new InventoryQuery();
    private final StockTransferQuery stq = new StockTransferQuery();

    @FXML
    void postToInventory(ActionEvent event) throws SQLException {
        for(int i = 0; i < this.items.size(); i++){
            this.items.get(i).setMov("DEC");
            this.items.get(i).setRemarks(this.stm.getRemarks());
        }
        
        for(int i = 0; i < this.items2.size(); i++){
            this.items2.get(i).setMov("INC");
            this.items2.get(i).setRemarks(this.stm.getRemarks());
        }
        
        iq.PostUpdateInventory(this.items.iterator(), super.getGlobalUser().getUsername(), "StockTransfer", 0, this.soldtofld.getText(), "Supplier", this.stm.getIdst(), this.stm.getRefnum(), this.stm.getRemarks(), super.getType());
        iq.PostUpdateInventory(this.items2.iterator(), super.getGlobalUser().getUsername(), "StockTransfer", 0, this.soldtofld.getText(), "Supplier", this.stm.getIdst(), this.stm.getRefnum(), this.stm.getRemarks(), super.getType());
        stq.changeSTStat(this.stm.getIdst());
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void AddItem(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/stocktransfer/STItems.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        STItemsController stic = fxmlloader.<STItemsController>getController();
        stic.initData(super.getGlobalUser(),super.getType());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Item");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        if(!stic.IsCancelled()){
            if(stic.getItem() != null && stic.getParallel_item() != null){
                if(!this.items.contains(stic.getItem())){
                    this.items.add(stic.getItem());
                    this.items2.add(stic.getParallel_item());
                    
                    this.RefreshItems();
                    this.RefreshItems2();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("This Item has already been added.");

                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("A Parallel item is null.");

                alert.showAndWait();
            }
        }
    }

    @FXML
    void EditItem(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Item Quantity");
        dialog.setHeaderText("Item: " + this.itemlist.getSelectionModel().getSelectedItem().getSku() + "-" 
                + this.itemlist.getSelectionModel().getSelectedItem().getDescription() + "\n" 
                + "Current Quantity: " + this.itemlist.getSelectionModel().getSelectedItem().getSoh());

        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            this.items.get(this.itemlist.getSelectionModel().getSelectedIndex()).setSoh(Integer.valueOf(result.get()));
            this.RefreshItems();
        }
    }

    @FXML
    void DeleteItem(ActionEvent event) {
        if(isEdit){
            if(!this.items.isEmpty()){
                int stitemid = this.itemlist.getSelectionModel().getSelectedItem().getStitem_id();
                this.deleted.add(this.items.remove(this.itemlist.getSelectionModel().getFocusedIndex()));
                
                for(int i = 0; i < this.items2.size(); i++){
                    if(this.items2.get(i).getStitem_id() == stitemid){
                        this.items2.remove(i);
                        break;
                    }
                }
                
                this.RefreshItems2();
                this.RefreshItems();
            }
        }else{
            if(!this.items.isEmpty()){
                this.items2.remove(this.itemlist.getSelectionModel().getFocusedIndex());
                this.items.remove(this.itemlist.getSelectionModel().getSelectedItem());
                this.RefreshItems();
            }
        }
    }

    @FXML
    void ResetItems(ActionEvent event) {
        
        if(isEdit){
            if(!this.items.isEmpty()){
                for(InventoryModel mod : this.items){
                    this.deleted.add(mod);
                }
                this.items2.clear();
                this.items.clear();
                this.RefreshItems();
                this.RefreshItems2();
            }
            
        }else{
            if(!this.items.isEmpty()){
                this.items.clear();
                this.items2.clear();
                this.RefreshItems();
                this.RefreshItems2();
            }
        }

    }

    @FXML
    void saveHandler(ActionEvent event) throws SQLException {
        
        StockTransferModel stm = new StockTransferModel();
        
        stm.setSt_dte(Date.valueOf(this.datefld.getValue().toString()));
        stm.setDr_dte(Date.valueOf(this.drdatefld.getValue().toString()));
        stm.setSlto(this.soldtofld.getText());
        stm.setAttention(this.attentionfld.getText());
        stm.setRefnum(this.refnumfld.getText());
        stm.setAddress(this.addressfld.getText());
        stm.setPonum(this.pofld.getText());
        stm.setTrucknme(this.truckerfld.getText());
        stm.setDrvrnme(this.driverfld.getText());
        stm.setPlate(this.platefld.getText());
        stm.setStdesc(this.stdescfld.getText());
        stm.setRemarks(this.remarksfld.getText());
        
        stm.setItems(items);
        stm.setItems2(items2);
        
        if(isEdit){
            stm.setIdst(Integer.parseInt(this.idfld.getText()));
            stq.editST(stm);
            
            ArrayList<InventoryModel> addedinEdit1 = new ArrayList();
            ArrayList<InventoryModel> addedinEdit2 = new ArrayList();
            for(int x = 0; x < this.items.size(); x++){
                if(this.items.get(x).getStitem_id() == 0){
                    addedinEdit1.add(this.items.get(x));
                }
            }
            for(int y = 0; y < this.items2.size(); y++){
                if(this.items2.get(y).getStitem_id() == 0){
                    addedinEdit2.add(this.items2.get(y));
                }
            }
            
            if(!this.deleted.isEmpty()){
                
            }
            if(!this.items.isEmpty()){
                if(!addedinEdit1.isEmpty()){
                    this.stq.addSTItems(addedinEdit1.iterator(), stm.getIdst(), addedinEdit2.iterator());
                }
                this.stq.editSTItems(this.items.iterator());
            }
        }else{
            stq.addStockTransfer(stm);
        }
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }
    
    public void AddMode(){
        this.idfld.setDisable(true);
        this.datefld.setValue(LocalDate.now());
        this.printbtn.setDisable(true);
        this.pgibtn.setDisable(true);
        
    }
    
    public void EditMode(StockTransferModel stm) throws SQLException{
        
        this.stm = stm;
        this.isEdit = true;
        
        this.idfld.setText(String.valueOf(stm.getIdst()));
        this.idfld.setEditable(false);
        this.datefld.setValue(LocalDate.parse(stm.getSt_dte().toString()));
        this.drdatefld.setValue(LocalDate.parse(stm.getDr_dte().toString()));
        this.soldtofld.setText(stm.getSlto());
        this.attentionfld.setText(stm.getAttention());
        this.refnumfld.setText(stm.getRefnum());
        this.addressfld.setText(stm.getAddress());
        this.pofld.setText(stm.getPonum());
        this.truckerfld.setText(stm.getTrucknme());
        this.driverfld.setText(stm.getDrvrnme());
        this.platefld.setText(stm.getPlate());
        this.stdescfld.setText(stm.getStdesc());
        this.remarksfld.setText(stm.getRemarks());
        
        //GET ITEMS
        Iterator ir = this.stq.getSTItems(stm.getIdst());
        
        while(ir.hasNext()){
            HashMap map = (HashMap) ir.next();
            
            InventoryModel item1 = new InventoryModel(Integer.parseInt(map.get("invent_id_frm").toString()));
            item1.setSku(map.get("sku1").toString());
            item1.setDescription(map.get("skudesc1").toString());
            item1.setWh(map.get("skuwh1").toString());
            item1.setSoh(Integer.parseInt(map.get("invent_qty").toString()));
            item1.setSt_id(Integer.parseInt(map.get("st_idinvent").toString()));
            item1.setStitem_id(Integer.parseInt(map.get("idstitems").toString()));
            
            this.items.add(item1);
            
            InventoryModel item2 = new InventoryModel(Integer.parseInt(map.get("invent_id_to").toString()));
            item2.setSku(map.get("sku2").toString());
            item2.setDescription(map.get("skudesc2").toString());
            item2.setWh(map.get("skuwh2").toString());
            item2.setSoh(Integer.parseInt(map.get("invent_qty").toString()));
            item2.setSt_id(Integer.parseInt(map.get("st_idinvent").toString()));
            item2.setStitem_id(Integer.parseInt(map.get("idstitems").toString()));
            
            this.items2.add(item2);
        }
        
        this.RefreshItems();
        this.RefreshItems2();
        
        this.printbtn.setDisable(true);
        this.pgibtn.setDisable(true);
        
    }
    
    public void ViewMode(StockTransferModel stm) throws SQLException{
        
        this.stm = stm;
        
        this.idfld.setText(String.valueOf(stm.getIdst()));
        this.idfld.setEditable(false);
        this.datefld.setValue(LocalDate.parse(stm.getSt_dte().toString()));
        this.datefld.setEditable(false);
        this.drdatefld.setValue(LocalDate.parse(stm.getDr_dte().toString()));
        this.drdatefld.setEditable(false);
        this.soldtofld.setText(stm.getSlto());
        this.soldtofld.setEditable(false);
        this.attentionfld.setText(stm.getAttention());
        this.attentionfld.setEditable(false);
        this.refnumfld.setText(stm.getRefnum());
        this.refnumfld.setEditable(false);
        this.addressfld.setText(stm.getAddress());
        this.addressfld.setEditable(false);
        this.pofld.setText(stm.getPonum());
        this.pofld.setEditable(false);
        this.truckerfld.setText(stm.getTrucknme());
        this.truckerfld.setEditable(false);
        this.driverfld.setText(stm.getDrvrnme());
        this.driverfld.setEditable(false);
        this.platefld.setText(stm.getPlate());
        this.platefld.setEditable(false);
        this.stdescfld.setText(stm.getStdesc());
        this.stdescfld.setEditable(false);
        this.remarksfld.setText(stm.getRemarks());
        this.remarksfld.setEditable(false);
        
        //GET ITEMS
        Iterator ir = this.stq.getSTItems(stm.getIdst());
        
        while(ir.hasNext()){
            HashMap map = (HashMap) ir.next();
            
            InventoryModel item1 = new InventoryModel(Integer.parseInt(map.get("invent_id_frm").toString()));
            item1.setSku(map.get("sku1").toString());
            item1.setDescription(map.get("skudesc1").toString());
            item1.setWh(map.get("skuwh1").toString());
            item1.setSoh(Integer.parseInt(map.get("invent_qty").toString()));
            
            this.items.add(item1);
            
            InventoryModel item2 = new InventoryModel(Integer.parseInt(map.get("invent_id_to").toString()));
            item2.setSku(map.get("sku2").toString());
            item2.setDescription(map.get("skudesc2").toString());
            item2.setWh(map.get("skuwh2").toString());
            item2.setSoh(Integer.parseInt(map.get("invent_qty").toString()));
            
            this.items2.add(item2);
        }
        
        this.RefreshItems();
        this.RefreshItems2();
        
        this.addbtn.setDisable(true);
        this.editbtn.setDisable(true);
        this.deletebtn.setDisable(true);
        this.resetbtn.setDisable(true);
        this.savebtn.setDisable(true);
        
        if(this.stm.getStat().equals("COMPLETE")){
            this.pgibtn.setDisable(true);
        }
        
    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "description", "wh", "soh"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        data.addAll(items);
        
        ObservableList<TableColumn<InventoryModel, ?>> olist;
        olist = (ObservableList<TableColumn<InventoryModel, ?>>) this.itemlist.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.itemlist.getItems().clear();
        this.itemlist.setItems(data);
    }
    
    public void RefreshItems2(){
        String[] arr = {"sku", "description", "wh"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        data.addAll(items2);
        
        ObservableList<TableColumn<InventoryModel, ?>> olist;
        olist = (ObservableList<TableColumn<InventoryModel, ?>>) this.itemlist1.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.itemlist1.getItems().clear();
        this.itemlist1.setItems(data);
    }

    @FXML
    void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void export(ActionEvent event) throws FileNotFoundException, IOException {
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
        String str = "SOLD TO: " + this.soldtofld.getText();
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
        String str2 = "ADDRESS: " + this.addressfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str2);
        
        //Address
        rownum = 9;
        cellnum = 1;
        String str6 = "ATTENTION: " + this.attentionfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str6);
        
        //DR num
        rownum = 9;
        cellnum = 3;
        String str4 = "P.O No. " + this.idfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str4);
        
        //Items
        //Items
        int start = 12;
        XSSFCellStyle txtstyle = workbook.createCellStyle();
        XSSFFont txtfont = workbook.createFont();
        txtfont.setFontName("Calibri");
        txtfont.setFontHeightInPoints((short)10);
        txtstyle.setFont(txtfont);
        txtstyle.setBorderBottom(BorderStyle.THIN);
        txtstyle.setBorderTop(BorderStyle.THIN);
        txtstyle.setBorderRight(BorderStyle.THIN);
        txtstyle.setBorderLeft(BorderStyle.THIN);
        
        for(int x = 0; x < this.items.size(); x++){
            
            rownum = start;
            cellnum = 1;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, String.valueOf(this.items.get(x).getSoh()), txtstyle);
            
            rownum = start;
            cellnum = 2;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items.get(x).getUom(), txtstyle);
            
            rownum = start;
            cellnum = 3;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items.get(x).getDescription(), txtstyle);
            
            start++;
        }
        
        //DR num
        rownum = 37;
        cellnum = 3;
        String str5 = "";
        for(int i = 0; i < 18; i++){
            str5 += "\u00a0 ";
        }        
        str5 += "NO. " + this.idfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str5);
        
        file.close();
        
        String currentUsersHomeDir = System.getProperty("user.home");
        File dir = new File(currentUsersHomeDir + "\\Documents\\Exports");
        if(!dir.exists()){
            System.out.println("Directory Created");
            dir.mkdir();
        }
        
        String filename = "[StockTransmittal]" + this.soldtofld.getText() + "-(" + this.stm.getIdst() + ").xlsx";
        
        File file2 = new File(dir.getAbsolutePath()+ "\\" + filename);
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

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        super.setType(type);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
