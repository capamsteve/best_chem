/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package best_chem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Miguel Sietereales
 */
public class ServiceInvoiceController implements Initializable {

    @FXML
    private TableView<?> itemlist;
    @FXML
    private TextField soldfld;
    @FXML
    private DatePicker datefld;
    @FXML
    private Button savebtn;
    @FXML
    private Button cancelbtn;
    @FXML
    private Button exportbtn;

    @FXML
    private Button addbtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button resetbtn;

    @FXML
    private TextField addressfld;
    @FXML
    private TextField totalfld;
    @FXML
    private Label vattxt;
    @FXML
    private CheckBox vatbox;
//    @FXML
//    private TextField pofld;
    @FXML
    private ComboBox<String> pocmb;

    @FXML
    private TextField bstylefld;
    @FXML
    private TextField termsfld;
    @FXML
    private TextField tinfld;
    @FXML
    private TableView<?> deliverylist;
    @FXML
    private TextField invoicefld;

    private String sinvoiceid;
    private boolean isEdit;

    private String customerid;

    private String companyname;
    private String tinno;
    private String address;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //DONE
    public void setInfo(String customerid, String companyname, String tinno, String address, String vat) {
        
    }

    //DONE
    public void isEdit(String sinvoiceid, String invoiceid, String date, String bstyle, String terms, String vat, String idpo) {
        
    }

    //DONE
    public void setView(String sinvoiceid, String invoiceid, String date, String bstyle, String terms, String vat, String idpo) throws SQLException {
        
    }

    //DONE
    public void refreshItems() {
        
    }

    public void refreshdo() throws SQLException {
       
    }

    //DONE
    public void refreshVat() {
        
    }

    public void Export() throws FileNotFoundException, IOException {
       
    }

    //DONE
    @FXML
    private void SaveHandler(MouseEvent event) throws SQLException {
       
    }

    //DONE
    @FXML
    private void CancelHandler(MouseEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void AddItemHandler() throws IOException, SQLException {
        
    }

    @FXML
    private void DeleteItemHandler() throws SQLException {
        
    }

//    @FXML
//    private void ResetItemsHandler() {
//
//    }
    @FXML
    private void RefreshValues() throws SQLException {
    }
}
