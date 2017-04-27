/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supplier;

import customer.ContactViewController;
import dbquerries.SupplierQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.SupplierContactModel;
import models.SupplierModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class SupplierController implements Initializable {
    
    @FXML
    private TextField pymtrmfld;

    @FXML
    private TextField addressfld;

    @FXML
    private TextField compfld;

    @FXML
    private TextField tinfld;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField bsnstylefld;

    @FXML
    private Button addbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private TableView<SupplierContactModel> contactList;

    @FXML
    private Button resetbtn;

    @FXML
    private TextField idfld;

    @FXML
    private TextField pstlcdfld;

    @FXML
    private Button savebtn;
    
    private boolean isEdit = false;
    
    private ArrayList<SupplierContactModel> contacts = new ArrayList();
    private final SupplierQuery sq = new SupplierQuery();
    
    public void AddMode(){
        
    }
    
    public void EditMode(){
        
    }

    @FXML
    void addContact(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/supplier/SupplierContactView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        /**
         * Opens the Controller to the Contact View
         */
        SupplierContactController cvc = fxmlloader.<SupplierContactController>getController();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Contact");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        /**
         * Checks if the View Was Cancelled 
         * 
         * if yes then dont add
         * 
         * if no then add to the arraylist
         */
        
        if(!cvc.checkifCanceled()){
            contacts.add(new SupplierContactModel(cvc.getContactName(), cvc.getContactNumber(), cvc.getContactEmail()));
        }
        
        /**
         * Refresh the Items on the view to see if the view was added
         */
        this.RefreshItems();

    }
    
    //DONE
    private void RefreshItems(){
        
        //private String supcname;
        //private String contact;
        //private String supemail;
        
        String[] arr = {"supcname", "contact", "supemail"};
        ObservableList<SupplierContactModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < contacts.size(); i++){
            data.add(contacts.get(i));
        }
        ObservableList<TableColumn<SupplierContactModel, ?>> olist = (ObservableList<TableColumn<SupplierContactModel, ?>>) contactList.getColumns();
        
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        
        contactList.setItems(data);
    }

    @FXML
    void deletecontact(ActionEvent event) {
        if(!contactList.getItems().isEmpty()){
            int select = contactList.getSelectionModel().getFocusedIndex();
        
            contacts.remove(select);
            contactList.getItems().remove(select);
            this.RefreshItems();
        }

    }

    @FXML
    void resetContact(ActionEvent event) {
        if(!contacts.isEmpty()){
            contacts.clear();
            this.RefreshItems();
        }
    }

    @FXML
    void saveSupplier(ActionEvent event) throws SQLException {
        
        SupplierModel supmod = new SupplierModel();
        
        supmod.setSupname(this.compfld.getText());
        supmod.setSuptin(this.tinfld.getText());
        supmod.setSupbustyp(this.bsnstylefld.getText());
        supmod.setSupaddress(this.addressfld.getText());
        supmod.setSuppymttrm(this.pymtrmfld.getText());
        
        supmod.setScm(this.contacts);
        
        sq.addSupplier(supmod);
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    void cancelHandler(ActionEvent event) {
        
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
    
}
