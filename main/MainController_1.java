package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import best_chem.AbstractController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.UserModel;

public class MainController_1 extends AbstractController implements Initializable {

    @FXML
    private Button editinventbtn;

    @FXML
    private Button editsuppbtn;

    @FXML
    private Button editpendingbtn;

    @FXML
    private TextField salestotalvat;

    @FXML
    private Button sisearchbtn;

    @FXML
    private Button addsuppbtn;

    @FXML
    private TableView<?> pendingtable;

    @FXML
    private Button additembtn;

    @FXML
    private Button viewpendingbtn;

    @FXML
    private DatePicker fromrpdate;

    @FXML
    private Button rpbutton;

    @FXML
    private TextField totalamt;

    @FXML
    private Button addcustbtn;

    @FXML
    private TableView<?> inventorytable;

    @FXML
    private Button viewdonebtn;

    @FXML
    private DatePicker salesfromrpdate;

    @FXML
    private Button addpendingbtn;

    @FXML
    private TableColumn<?, ?> purchasetable;

    @FXML
    private DatePicker torpdate;

    @FXML
    private Button editcustbtn;

    @FXML
    private TextField totalwtx;

    @FXML
    private TableView<?> suppliertable;

    @FXML
    private TextField salestotalamt;

    @FXML
    private Button salesrpbutton2;

    @FXML
    private Button salesrpbutton3;

    @FXML
    private ComboBox<?> sicmb;

    @FXML
    private TextField totalvatex;

    @FXML
    private TableView<?> customertable;

    @FXML
    private Button viewtransactbtn;

    @FXML
    private DatePicker salestorpdate;

    @FXML
    private TableView<?> ptable;

    @FXML
    private TextField totalpaid;

    @FXML
    private TableView<?> stable;

    @FXML
    private TableView<?> donetable;

    @FXML
    private Button viewcontactsbtn;

    @FXML
    private Button salesrpbutton;
    
    @FXML
    private Text useridfld;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void addCustomer(ActionEvent event) {

    }

    @FXML
    void editCustomer(ActionEvent event) {

    }

    @FXML
    void viewContactDetails(ActionEvent event) {

    }

    @FXML
    void viewTransactions(ActionEvent event) {

    }

    @FXML
    void addInventoryItem(ActionEvent event) {

    }

    @FXML
    void editInventoryItem(ActionEvent event) {

    }

    @FXML
    void addSupplier(ActionEvent event) {

    }

    @FXML
    void editSupplier(ActionEvent event) {

    }

    @FXML
    void addPurchases(ActionEvent event) {

    }

    @FXML
    void viewPendingPurchaseItems(ActionEvent event) {

    }

    @FXML
    void editPendingPurchases(ActionEvent event) {

    }

    @FXML
    void viewDonePurchaseItems(ActionEvent event) {

    }

    @FXML
    void getPurchasesReports(ActionEvent event) {

    }

    @FXML
    void getSalesInvoiceReports(ActionEvent event) {

    }

    @FXML
    void getSalesInvoiceReports2(ActionEvent event) {

    }

    @FXML
    void getSalesInvoiceReports3(ActionEvent event) {

    }

    @FXML
    void REFRESH(ActionEvent event) {

    }

    @Override
    public void initData(UserModel user) {
        super.setGlobalUser(user);
        useridfld.setText(String.valueOf(super.getGlobalUser().getId()));
    }
}
