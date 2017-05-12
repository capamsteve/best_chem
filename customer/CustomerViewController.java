/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import models.CustomerModel;
import models.ContactModel;
import dbquerries.CustomerQuery;
import dbquerries.UtilitiesQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Miguel
 */
public class CustomerViewController implements Initializable {
    
    private UserModel globaluser;
    
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
    private Button addbtn;

    @FXML
    private TextField bsnstylefld;

    @FXML
    private ComboBox<String> uombx;

    @FXML
    private Button deletebtn;

    @FXML
    private TextField vndrcdfld;

    @FXML
    private TableView<ContactModel> contactList;

    @FXML
    private TextField dscntfld;

    @FXML
    private Button resetbtn;

    @FXML
    private CheckBox autochck;

    @FXML
    private TextField idfld;

    @FXML
    private TextField pstlcdfld;

    @FXML
    private TextField vatfld;

    @FXML
    private Button savebtn;
    
    //Models
    private ArrayList<ContactModel> contacts = new ArrayList();
    private ArrayList<ContactModel> removed = new ArrayList();
    private ArrayList<ContactModel> addedinEdit = new ArrayList();
    private ArrayList<String> uoms = new ArrayList();
    
    //Queries
    private UtilitiesQuery uq = new UtilitiesQuery();
    private CustomerQuery cq = new CustomerQuery();
    
    //Checks
    private boolean isEdit = false;

    @FXML
    public void addContact(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/customer/ContactView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        /**
         * Opens the Controller to the Contact View
         */
        ContactViewController cvc = fxmlloader.<ContactViewController>getController();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("View Customer");
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
            contacts.add(new ContactModel(cvc.getContactName(), cvc.getContactNumber(), cvc.getContactEmail()));
        }
        
        /**
         * Refresh the Items on the view to see if the view was added
         */
        this.RefreshItems();
    }
    
    //DONE
    @FXML
    public void deletecontact(ActionEvent event) {
        
        if(isEdit){
            /**
             * Get id list of contacts to be deleted
             */
            if(!contactList.getItems().isEmpty()){
                int select = contactList.getSelectionModel().getSelectedIndex();
                
                ContactModel con = contactList.getItems().get(select);
                
                System.out.println(con.getContactName());
                System.out.println(con.getContactid());
                
                removed.add(con);
                contacts.remove(con);
                //contactList.getItems().remove(select);
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
    
    //DONE
    @FXML
    public void resetContact(ActionEvent event) {
        
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
    
    //DONE
    private void RefreshItems(){
        String[] arr = {"contactName", "contactNumber", "contactEmail"};
        ObservableList<ContactModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < contacts.size(); i++){
            data.add(contacts.get(i));
        }
        ObservableList<TableColumn<ContactModel, ?>> olist = (ObservableList<TableColumn<ContactModel, ?>>) contactList.getColumns();
        
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        
        contactList.setItems(data);
    }
    
    @FXML
    public void saveCustomer(ActionEvent event) throws SQLException {
        
        CustomerModel customer;
        
        customer = new CustomerModel();
        customer.setCompany(this.compfld.getText());
        customer.setTin(this.tinfld.getText());
        customer.setBusinessstyle(this.bsnstylefld.getText());
        customer.setAddress(this.addressfld.getText());
        customer.setPaymentterm(this.pymtrmfld.getText());
        customer.setDiscount(Double.parseDouble(this.dscntfld.getText()));
        customer.setUom(this.uombx.getSelectionModel().getSelectedItem());
        customer.setPostal_code(Integer.parseInt(this.pstlcdfld.getText()));
        customer.setVendor_code(this.vndrcdfld.getText());
        customer.setVAT(Double.parseDouble(this.vatfld.getText()));
        customer.setAuto_create(this.autochck.isSelected());
        
        
        if(isEdit){
            customer.setIdcustomer(this.idfld.getText());
            cq.editCustomer(customer);
            if(!contacts.isEmpty()){

                for (int i = 0; i < contacts.size(); i++){
                    if(contacts.get(i).getContactid() == 0){
                        System.out.println(contacts.get(i).getContactName());
                        addedinEdit.add(contacts.get(i));
                    }
                }
                
                cq.editContact(contacts.iterator());
            }
            if(!removed.isEmpty()){
                cq.deleteContacts(removed.iterator());
            }
            if(!addedinEdit.isEmpty()){
                for (ContactModel contact : addedinEdit) {
                    contact.setCustomerid(Integer.valueOf(customer.getIdcustomer()));
                }
                cq.addContact(addedinEdit.iterator());
            }
        }
        else{
            cq.addCustomer(customer);
        
            customer = cq.getCustomer(customer.getCompany());
            
            if(!contacts.isEmpty()){
                for (ContactModel contact : contacts) {
                    contact.setCustomerid(Integer.valueOf(customer.getIdcustomer()));
                }
                cq.addContact(contacts.iterator());
            }
            
        }
        
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    public void AddMode(){
        this.idfld.setDisable(true);
        this.savebtn.setText("Add Customer");
        this.vatfld.setText("12");
    }
    
    public void EditMode(String company) throws SQLException{
        System.out.println(company);
        this.isEdit = true;
        this.idfld.setEditable(false);
        this.savebtn.setText("Edit Customer");
        
        CustomerModel customer = cq.getCustomer(company);
        
        this.idfld.setText(customer.getIdcustomer());
        this.idfld.setEditable(false);
        this.compfld.setText(customer.getCompany());
        this.tinfld.setText(customer.getTin());
        this.bsnstylefld.setText(customer.getBusinessstyle());
        this.addressfld.setText(customer.getAddress());
        this.pymtrmfld.setText(customer.getPaymentterm());
        this.dscntfld.setText(String.valueOf(customer.getDiscount()));
        this.uombx.getSelectionModel().select(customer.getUom());
        this.pstlcdfld.setText(String.valueOf(customer.getPostal_code()));
        this.vndrcdfld.setText(customer.getVendor_code());
        this.vatfld.setText(String.valueOf(customer.getVAT()));
        if(customer.isAuto_create()){
            this.autochck.setSelected(true);
        }
        else{
            this.autochck.setSelected(false);
        }
        
        Iterator rs = cq.getContacts(Integer.parseInt(customer.getIdcustomer()));
        
        while(rs.hasNext()){
            contacts.add((ContactModel) rs.next());
        }
        
        this.RefreshItems();
    }
    
    public void ViewMode(String company) throws SQLException{
        System.out.println(company);
        this.idfld.setEditable(false);
        this.savebtn.setText("Save Customer");
        this.savebtn.setDisable(true);
        
        CustomerModel customer = cq.getCustomer(company);
        
        this.idfld.setText(customer.getIdcustomer());
        this.idfld.setEditable(false);
        this.compfld.setText(customer.getCompany());
        this.compfld.setEditable(false);
        this.tinfld.setText(customer.getTin());
        this.tinfld.setEditable(false);
        this.bsnstylefld.setText(customer.getBusinessstyle());
        this.bsnstylefld.setEditable(false);
        this.addressfld.setText(customer.getAddress());
        this.addressfld.setEditable(false);
        this.pymtrmfld.setText(customer.getPaymentterm());
        this.pymtrmfld.setEditable(false);
        this.dscntfld.setText(String.valueOf(customer.getDiscount()));
        this.dscntfld.setEditable(false);
        this.uombx.getSelectionModel().select(customer.getUom());
        this.uombx.setEditable(false);
        this.pstlcdfld.setText(String.valueOf(customer.getPostal_code()));
        this.pstlcdfld.setEditable(false);
        this.vndrcdfld.setText(customer.getVendor_code());
        this.vndrcdfld.setEditable(false);
        this.vatfld.setText(String.valueOf(customer.getVAT()));
        this.vatfld.setEditable(false);
        if(customer.isAuto_create()){
            this.autochck.setSelected(true);
            this.autochck.setDisable(true);
        }
        else{
            this.autochck.setSelected(false);
            this.autochck.setDisable(true);
        }
        
        this.addbtn.setDisable(true);
        this.deletebtn.setDisable(true);
        this.resetbtn.setDisable(true);
        this.savebtn.setDisable(true);
        
        Iterator rs = cq.getContacts(Integer.parseInt(customer.getIdcustomer()));
        
        while(rs.hasNext()){
            contacts.add((ContactModel) rs.next());
        }
        
        this.RefreshItems();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Iterator rs = null;
        try {
            rs = uq.getUOM();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        while(rs.hasNext()){
            uoms.add((String)rs.next());
        }
        
        this.uombx.getItems().addAll(uoms);
        this.uombx.getSelectionModel().selectFirst();
    }
    
}
