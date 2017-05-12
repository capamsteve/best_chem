/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoices;

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
import dbquerries.SalesInvoiceQuery;
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
    
    @FXML
    private TableView<SOItemModel> itemlist1;

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
        
        Iterator map = this.drq.getDeliverReceipts(this.sovm.getIdso(), "Y", "open");
        
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
        this.computeTotal(data2.iterator());
    }
    
    public void EditMode(){
        
    }
    
    public void ViewMode(SIViewModel simod) throws SQLException{
        
        SIModel sim = siq.getSalesInvoiceModel(simod.getInvnum());
        
        this.sidfld.setText(String.valueOf(simod.getInvnum()));
        this.sidfld.setEditable(true);
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
        
        Iterator map = this.drq.getDeliverReceipts(this.sovm.getIdso(), "Y", "open");
        
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
            
            SOItemModel soitem = new SOItemModel(Integer.valueOf(temp2.get("idinventory").toString()));
            
            soitem.setSku(temp2.get("sku").toString());
            soitem.setDesc(temp2.get("skudesc").toString());
            soitem.setUom(temp2.get("skuom").toString());
            soitem.setQty(Double.valueOf(temp2.get("sumdrqty").toString()).intValue());
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

        SOItemsController soic = fxmlloader.<SOItemsController>getController();

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.editbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Sales Order");
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
        
        String filename = "[SALESINVOICE]" + this.model.getCompany() + "(" + this.sidfld.getText() + ")_" + datefld.getValue().toString().replace("/", "") + "-" + formattedDate.replaceAll("[\\s-:]","") + ".pdf";
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

        for(int i = 0; i < this.itemlist1.getItems().size(); i++){
            list.add(new ListItem(this.itemlist1.getItems().get(i).getSku() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.itemlist1.getItems().get(i).getDesc() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.itemlist1.getItems().get(i).getQty() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.itemlist1.getItems().get(i).getUprice() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + this.itemlist1.getItems().get(i).getAmount() + "\u00a0 \u00a0 \u00a0 \u00a0"
                    + "\n"));
        }
       
        // Add the list
        list.add(new ListItem("\n \u00a0 \u00a0 \u00a0 \u00a0 **************Nothing Follows**************"));
        document.add(list.setFont(font));
        
        document.add(new Paragraph("Total: " + this.totalfld.getText()).setFont(font));
        
        document.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please check the export in My Documents/Export");

        alert.showAndWait();
        
        Stage stage = (Stage) this.printbtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void completeHandler(ActionEvent event) {

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
