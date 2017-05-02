/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesorder;

import best_chem.AbstractController;
import dbquerries.SalesOrderQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CustomerModel;
import models.InventoryModel;
import models.SOItemModel;
import models.SalesOrderModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class TransactionItemsViewController extends AbstractController implements Initializable {

    @FXML
    private TableView<SOItemModel> itemlist;
    @FXML
    private Button addbtn;
    @FXML
    private Button editbtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button resetbtn;
    
    @FXML
    private TextField addressfld;

    @FXML
    private TextField compfld;

    @FXML
    private TextField tinfld;

    @FXML
    private TextField soidfld;

    @FXML
    private DatePicker drdatefld;

    @FXML
    private Button cancelbtn;
    
    @FXML
    private TextField bsnstylefld;

    @FXML
    private TextField totalfld;

    @FXML
    private TextField cpofld;

    @FXML
    private TextField pymttermfld;

    @FXML
    private DatePicker datefld;

    @FXML
    private TextField idlfd;

    @FXML
    private Button pendingbtn;
    
    private ArrayList<SOItemModel> itemsList = new ArrayList();
    private CustomerModel customer;
    
    private SalesOrderQuery soq = new SalesOrderQuery();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void AddMode(CustomerModel cust){
        this.customer = cust;
        this.soidfld.setDisable(true);
        this.compfld.setText(cust.getCompany());
        this.compfld.setEditable(false);
        this.idlfd.setText(cust.getIdcustomer());
        this.idlfd.setEditable(false);
        this.bsnstylefld.setText(cust.getBusinessstyle());
        this.bsnstylefld.setEditable(false);
        this.tinfld.setText(cust.getTin());
        this.tinfld.setEditable(false);
        this.addressfld.setText(cust.getAddress());
        this.addressfld.setEditable(false);
        this.pymttermfld.setText(cust.getPaymentterm());
        this.pymttermfld.setEditable(false);
        this.datefld.setValue(LocalDate.now());
    }
    
    public void EditMode(){
        
    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "desc", "qty", "uom", "uprice", "discnt", "amount", "vat"};
        ObservableList<SOItemModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.itemsList.size(); i++){
            data.add(itemsList.get(i));
        }
        ObservableList<TableColumn<SOItemModel, ?>> olist = (ObservableList<TableColumn<SOItemModel, ?>>) itemlist.getColumns();
        
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        
        this.itemlist.setItems(data);
    }
    
    @FXML
    public void AddItem(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/salesorder/SOItems.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        SOItemsController soic = fxmlloader.<SOItemsController>getController();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Sales Order");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        if(!soic.IsCancelled()){
            SOItemModel soitem = new SOItemModel();
            InventoryModel item = soic.getItem();
            soitem.setSku(item.getSku());
            soitem.setDesc(item.getDescription());
            soitem.setUom(item.getUom());
            soitem.setQty(soic.getQty());
            soitem.setIdinventory(item.getIdinventory());
            soitem.setDiscnt(this.customer.getDiscount());
            soitem.setUprice(item.getSellprice());
            double amount = item.getSellprice() * soic.getQty();
            
            double vatVal = 1 + (this.customer.getVAT() / 100);
            double vat = amount * vatVal;
            vat = Math.round(vat * 100.0)/100.0;
            System.out.println(vat);
            
            soitem.setAmount(amount);
            soitem.setVat(vat);
            itemsList.add(soitem);
            
            this.RefreshItems();
            this.computeTotal();
        }
    }
    
    public void computeTotal(){
        
        double total = 0.0;
        
        for(SOItemModel model : this.itemsList){
            total += model.getAmount();
        }
        
        this.totalfld.setText(String.valueOf(total));
    }

    @FXML
    public void EditItem(ActionEvent event) {

    }

    @FXML
    public void DeleteItem(ActionEvent event) {

    }

    @FXML
    public void ResetItems(ActionEvent event) {

    }
    
    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        SalesOrderModel somodel = new SalesOrderModel();
        
        somodel.setCustid(Integer.valueOf(customer.getIdcustomer()));
        somodel.setCustomerpo(this.cpofld.getText());
        somodel.setSodate(Date.valueOf(this.datefld.getValue()));
        somodel.setDeliverydate(Date.valueOf(this.drdatefld.getValue()));
        somodel.setSoItems(itemsList);
        soq.addSalesOrder(somodel);
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initData(UserModel user, int type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
