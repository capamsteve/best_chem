/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transmittal;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import dbquerries.PMInventoryQuery;
import dbquerries.PurchasesQuery;
import dbquerries.StockTransmittalQuery;
import dbquerries.UtilitiesQuery;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.BOModel;
import models.InventoryModel;
import models.PurchasesModel;
import models.STItemModel;
import models.StockTransmittalModel;
import models.UserModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import purchases.PurchaseOrderSelectorController;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class StockTransmittalViewController extends AbstractController implements Initializable {
    
    @FXML
    private TextField addressfld;

    @FXML
    private Button editbtn;

    @FXML
    private TextField connfld;

    @FXML
    private Button cancelbtn;

    @FXML
    private TableView<InventoryModel> itemlist;

    @FXML
    private Button addbtn;

    @FXML
    private Button selpgrbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private TextField refnumfld;

    @FXML
    private Button pgibtn;

    @FXML
    private TextField supfld;

    @FXML
    private TableView<BOModel> itemlist1;

    @FXML
    private Button resetbtn;

    @FXML
    private TextField grfld;

    @FXML
    private Button printbtn;

    @FXML
    private TextField idfld;

    @FXML
    private DatePicker datefld;

    @FXML
    private TextArea stdescfld;

    @FXML
    private Button savebtn;
    
    @FXML
    private Button editpmbtn;
    
    private StockTransmittalModel stm_og;
    
    private String porm = "MANUAL";
    
    private boolean isEdit = false;
    
    private final ArrayList<InventoryModel> items = new ArrayList();
    private final ArrayList<BOModel> items1 = new ArrayList();
    private final ArrayList<BOModel> deleted = new ArrayList();
    
    private final StockTransmittalQuery stm = new StockTransmittalQuery();
    private final PurchasesQuery pq = new PurchasesQuery();
    private final PMInventoryQuery pim = new PMInventoryQuery();
    private final UtilitiesQuery uq = new UtilitiesQuery();
    private final InventoryQuery iq = new InventoryQuery();

    @FXML
    void postToInventory(ActionEvent event) throws SQLException {
        
        ArrayList<InventoryModel> mods = new ArrayList();
        
        for(int i = 0; i < this.items1.size(); i++){
            int index = this.items.indexOf(new InventoryModel(this.items1.get(i).getFg_inventory()));
            
            InventoryModel inventory = iq.getInventory(this.items1.get(i).getSku(), "PC", this.items1.get(i).getWhouse(), super.getType());
            inventory.setSoh(this.items1.get(i).getBom_qty());
            inventory.setMov("DEC");
            
            mods.add(inventory);
        }
        
        iq.PostUpdateInventory(mods.iterator(), super.getGlobalUser().getName(), "STOCK TRANSMITTAL", 0, this.supfld.getText(), "SUPPLIER", this.stm_og.getStid(), this.refnumfld.getText(), this.stdescfld.getText(), super.getType());
        stm.changePGIStat(this.stm_og.getStid());
    }

    @FXML
    void AddItem(ActionEvent event) throws IOException, SQLException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/transmittal/TransmittalSelector.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        TransmittalSelectorController tsc = fxmlloader.<TransmittalSelectorController>getController();
        
        tsc.initData(null,super.getType());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Item");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        if(!tsc.IsCancelled()){
            InventoryModel mod = tsc.getitem();
            if(!this.items.contains(mod)){
                this.items.add(mod);
                this.RefreshItems();
                
                Iterator ir = this.pim.getBOMbyId(mod.getIdinventory());
        
                while(ir.hasNext()){
                    BOModel bom = (BOModel) ir.next();
                    System.out.println("FROM DB: " + bom.getIdinventory());
                    bom.setWhouse("SG");
                    int total = bom.getBom_qty() * mod.getSoh();
                    bom.setBom_qty(total);
                    bom.setBom_qty1();
                    this.items1.add(bom);
                }
                
                this.RefreshItems2();
            }
        }
        
        this.checkNDisableSelPGR();

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
            
            InventoryModel temp = this.itemlist.getSelectionModel().getSelectedItem(); 
            this.items.remove(temp);
            ArrayList<BOModel> bomTemp = new ArrayList();
            for(int i = 0; i < this.items1.size(); i++){
                if(this.items1.get(i).getFg_inventory() == temp.getIdinventory()){
                    bomTemp.add(this.items1.get(i));
                }
            }
            
            for(BOModel mod : bomTemp){
                this.items1.remove(mod);
                if(mod.getStitemid() != 0){
                    this.deleted.add(mod);
                }
                
            }
            
        }else{
            
            InventoryModel temp = this.itemlist.getSelectionModel().getSelectedItem(); 
            this.items.remove(temp);
            ArrayList<BOModel> bomTemp = new ArrayList();
            for(int i = 0; i < this.items1.size(); i++){
                if(this.items1.get(i).getFg_inventory() == temp.getIdinventory()){
                    bomTemp.add(this.items1.get(i));
                }
            }
            
            for(BOModel mod : bomTemp){
                this.items1.remove(mod);
            }
            
            
        }
        
        this.RefreshItems();
        this.RefreshItems2();
        
        this.checkNDisableSelPGR();

    }

    @FXML
    void ResetItems(ActionEvent event) {
        if(isEdit){
            for(BOModel mod : this.items1){
                if(mod.getStitemid() != 0){
                    this.deleted.add(mod);
                }
            }
            this.items.clear();
            this.items1.clear();
            this.RefreshItems();
            this.RefreshItems2();
        }
        else{
            this.items.clear();
            this.items1.clear();
            this.RefreshItems();
            this.RefreshItems2();
        }
        
        this.checkNDisableSelPGR();
    }
    
    @FXML
    void EditItemPM(ActionEvent event) throws SQLException {
        Dialog<BMPMVal> dialog = new Dialog<>();

        dialog.setTitle("Edit PM Value");
        
        BOModel mod = this.itemlist1.getSelectionModel().getSelectedItem();
        dialog.setHeaderText("Item: " + mod.getSku()+ "-" 
                + mod.getDescription() + "\n" 
                + "Current Quantity: " + mod.getBom_qty() + "\n"
                + "Current Warehouse: " + mod.getWhouse());

        dialog.setResizable(true);
        
        Label label1 = new Label("Qty: ");
        Label label2 = new Label("Warehouse: ");
        TextField text1 = new TextField();
        
        text1.setText(String.valueOf(mod.getBom_qty()));
        ComboBox<String> text2 = new ComboBox();
        
        Iterator ir = uq.getWHS();
        
        while(ir.hasNext()){
            text2.getItems().add((String)ir.next());
        }
        
        text2.getSelectionModel().selectFirst();
        
        GridPane grid = new GridPane();

        grid.add(label1, 1, 1);

        grid.add(text1, 2, 1);

        grid.add(label2, 1, 2);

        grid.add(text2, 2, 2);

        dialog.getDialogPane().setContent(grid);


        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.setResultConverter(new Callback<ButtonType, BMPMVal>() {
            @Override
            public BMPMVal call(ButtonType b) {
                if (b == buttonTypeOk) {
                    return new BMPMVal(Integer.valueOf(text1.getText()), text2.getSelectionModel().getSelectedItem());
                }
                return null;
            }
        });
        Optional<BMPMVal> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Result: " + result.get());
            
            this.items1.get(this.itemlist1.getSelectionModel().getSelectedIndex()).setBom_qty(result.get().getVal());
            this.items1.get(this.itemlist1.getSelectionModel().getSelectedIndex()).setWhouse(result.get().getWH());
            this.items1.get(this.itemlist1.getSelectionModel().getSelectedIndex()).setBom_qty1();
            
            this.RefreshItems2();
        }

    }

    @FXML
    void saveHandler(ActionEvent event) throws SQLException {
        
        this.stm_og = new StockTransmittalModel();
        
        stm_og.setSupname(this.supfld.getText());
        stm_og.setConname(this.connfld.getText());
        stm_og.setAddress(this.addressfld.getText());
        
        if(!this.grfld.getText().isEmpty()){
            stm_og.setGrnum(Integer.valueOf(this.grfld.getText()));
        }else{
            stm_og.setGrnum(0);
        }
        
        
        stm_og.setGidesc(this.stdescfld.getText());
        stm_og.setGi_dte(Date.valueOf(this.datefld.getValue()));
        stm_og.setRefnum(this.refnumfld.getText());
        stm_og.setTrm_type(this.porm);
        
        ArrayList<STItemModel> stms = new ArrayList();
        ArrayList<STItemModel> stmsEdit = new ArrayList();
        
        for(int i = 0; i < this.items1.size(); i++){
            
            STItemModel stm1 = new STItemModel();
            int index = this.items.indexOf(new InventoryModel(this.items1.get(i).getFg_inventory()));
            System.out.println(index);
            stm1.setFg_id(this.items1.get(i).getFg_inventory());
            stm1.setFg_sku(this.items.get(index).getSku());
            stm1.setFg_description(this.items.get(index).getDescription());
            stm1.setOrder_qty(this.items.get(index).getSoh());
            
            stm1.setBom_wh(this.items1.get(i).getWhouse());
            stm1.setPm_desc(this.items1.get(i).getDescription());
            stm1.setPm_sku(this.items1.get(i).getSku());
            stm1.setUnit_qty(this.items1.get(i).getBom_qty());
            stm1.setPm_id(this.items1.get(i).getIdinventory());
            
            if(this.items1.get(i).getStitemid() == 0){
                stms.add(stm1);
            }else{
                stm1.setStitemid(this.items1.get(i).getStitemid());
                stmsEdit.add(stm1);
            }
            
        }
        
        if(isEdit){
            stm_og.setStid(Integer.valueOf(this.idfld.getText()));
            stm.editStockTransMittal(stm_og);
            
            if(stm_og.getTrm_type().equals("MANUAL")){
                if(!this.deleted.isEmpty()){
                    stm.deleteStockTransmittalItems(deleted.iterator());
                }

                if(!stms.isEmpty()){
                    stm.addStockTransmittalItems(stms.iterator(), stm_og.getStid());
                }
                if(!stmsEdit.isEmpty()){
                    stm.editStockTransmittalItems(stmsEdit.iterator());
                }
            }
            else{
                if(!stmsEdit.isEmpty()){
                    stm.editStockTransmittalItems(stmsEdit.iterator());
                }
            }
            
        }
        else{
            this.stm_og.setItems(stms);
            stm.addStockTransmittal(stm_og);
        }
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    void cancelHandler(ActionEvent event) {
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    void checkNDisableSelPGR(){
        if(!this.items.isEmpty()){
            this.selpgrbtn.setDisable(true);
        }else{
            this.selpgrbtn.setDisable(false);
        }
    }
    
    @FXML
    void selectPGR(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/purchases/PurchaseOrderSelector.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        PurchaseOrderSelectorController stc = fxmlloader.<PurchaseOrderSelectorController>getController();
        stc.initData(super.getGlobalUser(), super.getType());
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.selpgrbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Stock Transfer");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        if(!stc.isCancelled()){
            this.addbtn.setDisable(true);
            this.editbtn.setDisable(true);
            this.deletebtn.setDisable(true);
            this.resetbtn.setDisable(true);
            this.selpgrbtn.setDisable(true);
            
            PurchasesModel pm = stc.getPm_og();
            
            this.grfld.setText(String.valueOf(pm.getIdpurchases()));
            this.grfld.setEditable(false);
            this.supfld.setText(pm.getSupplier());
            this.supfld.setEditable(false);
            this.connfld.setText(pm.getSupcname());
            this.connfld.setEditable(false);
            this.addressfld.setText(pm.getAddress());
            this.addressfld.setEditable(false);
            this.porm = "PGR";
            
            Iterator iterate = pq.getPurchaseOrderItems(pm.getIdpurchases(), 1);
        
            while(iterate.hasNext()){
                HashMap map = (HashMap) iterate.next();

                InventoryModel pim = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
                System.out.println(map.get("sku").toString());
                pim.setSku(map.get("sku").toString());
                pim.setDescription(map.get("skudesc").toString());
                pim.setSoh(Integer.valueOf(map.get("actual_qty").toString()));
                pim.setWh(map.get("skuwh").toString());

                this.items.add(pim);
                
                Iterator ir = this.pim.getBOMbyId(pim.getIdinventory());
        
                while(ir.hasNext()){
                    BOModel bom = (BOModel) ir.next();
                    System.out.println("FROM DB: " + bom.getIdinventory());
                    bom.setWhouse("BC");
                    int total = bom.getBom_qty() * pim.getSoh();
                    bom.setBom_qty(total);
                    bom.setBom_qty1();
                    this.items1.add(bom);
                }

            }
            
            System.out.println(this.items.size());
            
            this.RefreshItems();
            this.RefreshItems2();
            
        }
    }
    
    public void AddMode(){
        this.printbtn.setDisable(true);
        this.pgibtn.setDisable(true);
        this.grfld.setDisable(true);
        this.idfld.setDisable(true);
        
    }
    
    public void EditMode(StockTransmittalModel mod) throws SQLException{
        
        this.stm_og = mod;
        this.isEdit = true;
        
        this.grfld.setDisable(true);
        this.idfld.setDisable(true);
        
        if(mod.getTrm_type().equals("PGR")){
            this.grfld.setEditable(false);
            this.supfld.setEditable(false);
            this.connfld.setEditable(false);
            this.addressfld.setEditable(false);
            
            this.addbtn.setDisable(true);
            this.editbtn.setDisable(true);
            this.deletebtn.setDisable(true);
            this.resetbtn.setDisable(true);
            this.selpgrbtn.setDisable(true);
            this.printbtn.setDisable(true);
            this.pgibtn.setDisable(true);
            
            this.porm = "PGR";
        }
        
        else if(mod.getTrm_type().equals("MANUAL")){
            this.selpgrbtn.setDisable(true);
            this.printbtn.setDisable(true);
            this.pgibtn.setDisable(true);
        }
        
        if(mod.getGrnum() != 0){
            this.grfld.setText(String.valueOf(mod.getGrnum()));
        }
        
        this.supfld.setText(mod.getSupname());
        this.connfld.setText(mod.getConname());
        this.addressfld.setText(mod.getAddress());
        this.idfld.setText(String.valueOf(mod.getStid()));
        this.datefld.setValue(LocalDate.parse(mod.getGi_dte().toString()));
        this.refnumfld.setText(mod.getRefnum());
        this.stdescfld.setText(mod.getGidesc());
        
        Iterator ir = this.stm.getTransmittalFGItems(mod.getStid());
        
        while(ir.hasNext()){
            this.items.add((InventoryModel) ir.next());
        }
        
        Iterator ir2 = this.stm.getTransmittalPMItems(mod.getStid());
        
        while(ir2.hasNext()){
            this.items1.add((BOModel) ir2.next());
        }
        
        this.RefreshItems();
        this.RefreshItems2();
        
    }
    
    public void ViewMode(StockTransmittalModel mod) throws SQLException{
        
        this.stm_og = mod;
        
        System.out.println(mod.getStid());
        
        this.grfld.setDisable(true);
        this.idfld.setDisable(true);
        this.grfld.setEditable(false);
        this.supfld.setEditable(false);
        this.connfld.setEditable(false);
        this.addressfld.setEditable(false);
        this.datefld.setEditable(false);
        this.refnumfld.setEditable(false);
        this.stdescfld.setEditable(false);
        
        this.addbtn.setDisable(true);
        this.editbtn.setDisable(true);
        this.deletebtn.setDisable(true);
        this.resetbtn.setDisable(true);
        this.selpgrbtn.setDisable(true);
        this.savebtn.setDisable(true);
        this.editpmbtn.setDisable(true);
        
        if(this.stm_og.getPgistat().equals("Y")){
            this.pgibtn.setDisable(true);
        }
        
        this.supfld.setText(mod.getSupname());
        this.connfld.setText(mod.getConname());
        this.addressfld.setText(mod.getAddress());
        this.idfld.setText(String.valueOf(mod.getStid()));
        this.datefld.setValue(LocalDate.parse(mod.getGi_dte().toString()));
        this.refnumfld.setText(mod.getRefnum());
        this.stdescfld.setText(mod.getGidesc());
        
        Iterator ir = this.stm.getTransmittalFGItems(mod.getStid());
        
        while(ir.hasNext()){
            this.items.add((InventoryModel) ir.next());
        }
        
        Iterator ir2 = this.stm.getTransmittalPMItems(mod.getStid());
        
        while(ir2.hasNext()){
            this.items1.add((BOModel) ir2.next());
        }
        
        this.RefreshItems();
        this.RefreshItems2();
        
    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "description", "uom", "wh", "soh"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.items.size(); i++){
            data.add(items.get(i));
        }
        
        ObservableList<TableColumn<InventoryModel, ?>> olist = (ObservableList<TableColumn<InventoryModel, ?>>) itemlist.getColumns();
        
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        
        this.itemlist.getItems().clear();
        this.itemlist.setItems(data);
    }
    
    public void RefreshItems2(){
        String[] arr = {"sku", "description", "whouse", "bom_qty1"};
        ObservableList<BOModel> data
                = FXCollections.observableArrayList();
        
        data.addAll(this.items1);
        
        ObservableList<TableColumn<BOModel, ?>> olist;
        olist = (ObservableList<TableColumn<BOModel, ?>>) this.itemlist1.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.itemlist1.getItems().clear();
        this.itemlist1.setItems(data);
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
    void export(ActionEvent event) throws FileNotFoundException, IOException {
        NumberFormat nf= NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);
        
        FileInputStream file = new FileInputStream("C:\\res\\bomexport.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
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
        rownum = 0;
        cellnum = 0;
        String str = "SOLD TO: " + this.supfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str);
        
        //Date
        rownum = 0;
        cellnum = 6;
        String str3 = ""; 
        str3 += "DATE: ";
        str3 += this.datefld.getValue().toString();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str3);
        
        //Address
        rownum = 1;
        cellnum = 0;
        String str2 = "ADDRESS: " + this.addressfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str2);
        
        //Address
        rownum = 2;
        cellnum = 0;
        String str6 = "ATTENTION: " + this.connfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str6);
        
        //DR num
        rownum = 2;
        cellnum = 6;
        String str4 = "No. " + this.idfld.getText();
        this.createCell(sheetrow, sheet, cell, rownum, cellnum, str4);
        
        //Items
        int start = 4;
        XSSFCellStyle txtstyle = workbook.createCellStyle();
        XSSFFont txtfont = workbook.createFont();
        txtfont.setFontName("Calibri");
        txtfont.setFontHeightInPoints((short)10);
        txtstyle.setFont(txtfont);
        txtstyle.setBorderBottom(BorderStyle.THIN);
        txtstyle.setBorderTop(BorderStyle.THIN);
        txtstyle.setBorderRight(BorderStyle.THIN);
        txtstyle.setBorderLeft(BorderStyle.THIN);
        
        for(int x = 0; x < this.items1.size(); x++){
            
            int index = this.items.indexOf(new InventoryModel(this.items1.get(x).getFg_inventory()));
            
            rownum = start;
            cellnum = 0;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items.get(index).getSku(), txtstyle);
            
            rownum = start;
            cellnum = 1;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items.get(index).getDescription(), txtstyle);
            
            rownum = start;
            cellnum = 2;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items.get(index).getUom(), txtstyle);
            
            rownum = start;
            cellnum = 3;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, String.valueOf(this.items.get(index).getSoh()), txtstyle);
            
            rownum = start;
            cellnum = 4;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items1.get(x).getSku(), txtstyle);
            
            
            rownum = start;
            cellnum = 5;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items1.get(x).getDescription(), txtstyle);
            
            rownum = start;
            cellnum = 6;
            this.createCell(sheetrow, sheet, cell, rownum, cellnum, this.items1.get(x).getBom_qty1(), txtstyle);
            
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
        
        String filename = "[StockTransmittal]" + this.supfld.getText() + "-(" + this.stm_og.getStid() + ").xlsx";
        
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
    
    
}
