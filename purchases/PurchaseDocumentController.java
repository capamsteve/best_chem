/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases;

import dbquerries.SupplierQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.SOItemModel;
import models.SupplierContactModel;
import models.SupplierModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class PurchaseDocumentController implements Initializable {
    
    @FXML
    private TextField addressfld;

    @FXML
    private Button editbtn;

    @FXML
    private TextField compfld;

    @FXML
    private TextField tinfld;

    @FXML
    private ComboBox<String> contactbx;

    @FXML
    private DatePicker drdatefld;

    @FXML
    private Button cancelbtn;

    @FXML
    private TableView<SOItemModel> itemlist;

    @FXML
    private TextField bsnstylefld;

    @FXML
    private Button addbtn;

    @FXML
    private TextField totalfld;

    @FXML
    private Button deletebtn;

    @FXML
    private Button resetbtn;

    @FXML
    private TextField pymttermfld;

    @FXML
    private DatePicker datefld;

    @FXML
    private TextField idlfd;

    @FXML
    private Button pendingbtn;

    @FXML
    private TextField poidfld;
    
    private SupplierModel supplier;
    
    private final SupplierQuery supq = new SupplierQuery();

    @FXML
    public void AddItem(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/purchases/POItems.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        POItemsController poic = fxmlloader.<POItemsController>getController();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Item");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();

    }

    @FXML
    void EditItem(ActionEvent event) {

    }

    @FXML
    void DeleteItem(ActionEvent event) {

    }

    @FXML
    void ResetItems(ActionEvent event) {

    }

    @FXML
    void saveHandler(ActionEvent event) {

    }

    @FXML
    void cancelHandler(ActionEvent event) {

    }
    
    public void setSupplier(SupplierModel supplier){
        this.supplier = supplier;
    }
    
    public void AddMode() throws SQLException{
        
        this.poidfld.setDisable(true);
        this.idlfd.setText(String.valueOf(this.supplier.getSupid()));
        this.idlfd.setEditable(false);
        this.compfld.setText(this.supplier.getSupname());
        this.compfld.setEditable(false);
        this.bsnstylefld.setText(this.supplier.getSupbustyp());
        this.bsnstylefld.setEditable(false);
        this.tinfld.setText(this.supplier.getSuptin());
        this.tinfld.setEditable(false);
        this.datefld.setValue(LocalDate.now());
        this.pymttermfld.setText(this.supplier.getSuppymttrm());
        this.pymttermfld.setEditable(false);
        this.addressfld.setText(this.supplier.getSupaddress());
        this.addressfld.setEditable(false);
        
        ArrayList<SupplierContactModel> supcon = supq.getContacts(this.supplier.getSupid());
        
        for(SupplierContactModel sup: supcon){
            this.contactbx.getItems().add(sup.getSupcname());
        }
        
        this.contactbx.getSelectionModel().selectFirst();
    }
    
    public void EditMode(){
        
    }
    
    public void ViewMode(){
        
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
