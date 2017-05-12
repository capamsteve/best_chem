/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supplier;

import best_chem.AbstractController;
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
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class SupplierController extends AbstractController implements Initializable {
    
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
    private ArrayList<SupplierContactModel> removed = new ArrayList();
    private ArrayList<SupplierContactModel> addedinEdit = new ArrayList();
    private final SupplierQuery sq = new SupplierQuery();
    
    public void AddMode(){
        this.idfld.setDisable(true);
    }
    
    public void EditMode(SupplierModel supplier) throws SQLException{
        this.isEdit = true;
        
        this.idfld.setText(String.valueOf(supplier.getSupid()));
        this.compfld.setText(supplier.getSupname());
        this.tinfld.setText(supplier.getSuptin());
        this.bsnstylefld.setText(supplier.getSupbustyp());
        this.addressfld.setText(supplier.getSupaddress());
        this.pymtrmfld.setText(supplier.getSuppymttrm());
        this.pstlcdfld.setText(supplier.getPostal());
        
        this.savebtn.setText("Edit Supplier");
        
        ArrayList<SupplierContactModel> scm = sq.getContacts(supplier.getSupid(), super.getType());

        for(int i = 0; i < scm.size(); i++){
            contacts.add(scm.get(i));
        }
        this.RefreshItems();
    }
    
    public void ViewMode(SupplierModel supplier) throws SQLException{
        this.idfld.setText(String.valueOf(supplier.getSupid()));
        this.idfld.setEditable(false);
        this.compfld.setText(supplier.getSupname());
        this.compfld.setEditable(false);
        this.tinfld.setText(supplier.getSuptin());
        this.tinfld.setEditable(false);
        this.bsnstylefld.setText(supplier.getSupbustyp());
        this.bsnstylefld.setEditable(false);
        this.addressfld.setText(supplier.getSupaddress());
        this.addressfld.setEditable(false);
        this.pymtrmfld.setText(supplier.getSuppymttrm());
        this.pymtrmfld.setEditable(false);
        this.pstlcdfld.setText(supplier.getPostal());
        this.pstlcdfld.setEditable(false);
        
        this.addbtn.setDisable(true);
        this.savebtn.setText("Save Supplier");
        this.resetbtn.setDisable(true);
        this.deletebtn.setDisable(true);
        this.savebtn.setDisable(true);
        
        System.out.println("HERE");
        
        ArrayList<SupplierContactModel> scm = sq.getContacts(supplier.getSupid(), super.getType());

        for(int i = 0; i < scm.size(); i++){
            System.out.println(scm.get(i).getSupcname());
            contacts.add(scm.get(i));
        }
        this.RefreshItems();
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
        if(isEdit){
            /**
             * Get id list of contacts to be deleted
             */
            if(!contactList.getItems().isEmpty()){
               int select = contactList.getSelectionModel().getSelectedIndex();
                
                SupplierContactModel con = contactList.getItems().get(select);
                
                removed.add(con);
                contacts.remove(con);

                this.RefreshItems();
            }
            
        }
        else{
            if(!contactList.getItems().isEmpty()){
                int select = contactList.getSelectionModel().getFocusedIndex();

                contacts.remove(select);
                contactList.getItems().remove(select);
                this.RefreshItems();
            }
        }

    }

    @FXML
    void resetContact(ActionEvent event) {
        if(isEdit){
            int size =  contacts.size();
            for(int i = 0; i < size; i++){
                removed.add(contacts.get(i));
            }
            this.contacts.clear();
            this.RefreshItems();
        }
        else{
            if(!contacts.isEmpty()){
                contacts.clear();
                this.RefreshItems();
            }
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
        supmod.setPostal(this.pstlcdfld.getText());
        
        supmod.setScm(this.contacts);
        
        if(isEdit){
            supmod.setSupid(Integer.parseInt(this.idfld.getText()));
            sq.editSupplier(supmod, super.getType());
            
            if(!contacts.isEmpty()){
                
                for (int i = 0; i < contacts.size(); i++){
                    if(contacts.get(i).getCid() == 0){
                        addedinEdit.add(contacts.get(i));
                    }
                }
                
                sq.editContactSupplier(contacts.iterator(), super.getType());
            }
            if(!removed.isEmpty()){
                sq.deleteContacts(removed.iterator(), super.getType());
            }
            if(!addedinEdit.isEmpty()){
                for (SupplierContactModel contact : addedinEdit) {
                    contact.setSupid(supmod.getSupid());
                }
                sq.addContactSupplier(addedinEdit.iterator(), supmod.getSupid(), super.getType());
            }
        }
        else{
            sq.addSupplier(supmod, super.getType());
            
        }
        
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

    @Override
    public void initData(UserModel user, int type) {
        super.setType(type);
    }
    
}
