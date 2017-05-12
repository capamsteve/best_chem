/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgi;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import models.MGIModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class ManualGoodsIssueController extends AbstractController implements Initializable {
    
    @FXML
    private TextField customerfld;

    @FXML
    private TextField addressfld;

    @FXML
    private Button editbtn;

    @FXML
    private TextArea descfld;

    @FXML
    private TextField contactfld;

    @FXML
    private TextField attentionfld;

    @FXML
    private TableView<InventoryModel> itemlist;

    @FXML
    private Button addbtn;

    @FXML
    private TextField refnumfld;

    @FXML
    private Button deletebtn;

    @FXML
    private Button resetbtn;

    @FXML
    private TextField idfld;

    @FXML
    private DatePicker datefld;
    
    @FXML
    private Button cancelbtn;

    @FXML
    private Button savebtn;
    
    @FXML
    private Button pgibtn;
    
    private boolean isEdit;
    
    private MGIModel mod_og;

    private ArrayList<InventoryModel> items = new ArrayList();
    private ArrayList<InventoryModel> deleted = new ArrayList();
    
    private InventoryQuery iq = new InventoryQuery();

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        MGIModel mod = new MGIModel();
        mod.setGidte(Date.valueOf(this.datefld.getValue()));
        mod.setCustname(this.customerfld.getText());
        mod.setContname(this.contactfld.getText());
        mod.setRef(this.refnumfld.getText());
        mod.setAttention(this.attentionfld.getText());
        mod.setAddress(this.addressfld.getText());
        mod.setDescription(this.descfld.getText());
        mod.setCby(super.getGlobalUser().getId());
        
        mod.setItems(items);
        if(isEdit){
            mod.setGinum(Integer.valueOf(this.idfld.getText()));
            ArrayList<InventoryModel> ims = new ArrayList();
            iq.editMGI(mod, super.getType());
            
            for(InventoryModel im : this.items){
                if(im.getMgiid_item() == 0){
                    ims.add(im);
                }
            }
            
            if(!this.items.isEmpty()){
                iq.editMGItems(this.items.iterator(), super.getType());
            }
            if(!this.deleted.isEmpty()){
                iq.deleteMGIItems(this.deleted.iterator(), super.getType());
            }
            if(!ims.isEmpty()){
                iq.addMGItems(ims.iterator(), mod.getGinum(), super.getType());
            }
        }else{
            if(!this.items.isEmpty()){
                iq.addMGI(mod, super.getType());
            }
            
        }
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();

    }
    
    @FXML
    void postToInventory(ActionEvent event) throws SQLException {
        /**
         * 
         * CHANGE MGI STAT
         * 
         */
        
        for(int i = 0; i < this.items.size(); i++){
            this.items.get(i).setMov("DEC");
        }
        
        iq.PostUpdateInventory(this.items.iterator());
        iq.changeMGIPost(this.items.iterator(), this.mod_og.getGinum());
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    

    @FXML
    public void AddItem(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mgi/MGItemSelector.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        MGItemSelectorController mgic = fxmlloader.<MGItemSelectorController>getController();
        
        mgic.initData(null,super.getType());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Item");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        InventoryModel mod = mgic.getitem();
        
        if(!this.items.contains(mod)){
            this.items.add(mod);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You have already selected this item.");

            alert.showAndWait();
        }
        
        this.RefreshItems();

    }

    @FXML
    public void EditItem(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Item Quantity");
        dialog.setHeaderText("Item: " + this.itemlist.getSelectionModel().getSelectedItem().getSku() + "-" 
                + this.itemlist.getSelectionModel().getSelectedItem().getDescription() + "\n" 
                + "Current Quantity: " + this.itemlist.getSelectionModel().getSelectedItem().getSoh());

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            
            System.out.println(this.itemlist.getSelectionModel().getSelectedIndex());
            System.out.println(this.itemlist.getSelectionModel().getFocusedIndex());
          
            this.items.get(this.itemlist.getSelectionModel().getSelectedIndex()).setSoh(Integer.valueOf(result.get()));
            this.itemlist.getItems().clear();
            this.RefreshItems();
        }
    }

    @FXML
    public void DeleteItem(ActionEvent event) {
        if(isEdit){
            if(!this.items.isEmpty()){
                this.deleted.add(this.items.remove(this.itemlist.getSelectionModel().getFocusedIndex()));
                
                this.RefreshItems();
            }
        }else{
            if(!this.items.isEmpty()){
                this.items.remove(this.itemlist.getSelectionModel().getSelectedItem());
                this.RefreshItems();
            }
        }
    }

    @FXML
    public void ResetItems(ActionEvent event) {
        if(isEdit){
            if(!this.items.isEmpty()){
                for(InventoryModel mod : this.items){
                    this.deleted.add(mod);
                }
                this.items.clear();
                this.RefreshItems();
            }
            
        }else{
            if(!this.items.isEmpty()){
                this.items.clear();
                this.RefreshItems();
            }
        }
    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "description", "wh", "uom", "soh"};
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
        this.itemlist.setItems(data);
    }
    
    public void AddMode(){
        this.isEdit = false;
        this.idfld.setDisable(true);
        this.pgibtn.setDisable(true);
        
    }
    
    public void EditMode(MGIModel mod) throws SQLException{
        this.isEdit = true;
        
        this.mod_og = mod;
        this.idfld.setText(String.valueOf(mod.getGinum()));
        this.idfld.setEditable(false);
        this.customerfld.setText(mod.getCustname());
        this.contactfld.setText(mod.getContname());
        this.datefld.setValue(LocalDate.parse(mod.getGidte().toString()));
        this.refnumfld.setText(mod.getRef());
        this.attentionfld.setText(mod.getAttention());
        this.addressfld.setText(mod.getAddress());
        this.descfld.setText(mod.getDescription());
        
        Iterator ir = iq.getMGItems(super.getType(), mod.getGinum());
        
        while(ir.hasNext()){
            items.add((InventoryModel) ir.next());
        }
        
        this.RefreshItems();
        this.pgibtn.setDisable(true);
    }
    
    public void ViewMode(MGIModel mod) throws SQLException{
        
        this.mod_og = mod;
        this.idfld.setText(String.valueOf(mod.getGinum()));
        this.idfld.setEditable(false);
        this.customerfld.setText(mod.getCustname());
        this.customerfld.setEditable(false);
        this.contactfld.setText(mod.getContname());
        this.contactfld.setEditable(false);
        this.datefld.setValue(LocalDate.parse(mod.getGidte().toString()));
        this.datefld.setEditable(false);
        this.refnumfld.setText(mod.getRef());
        this.refnumfld.setEditable(false);
        this.attentionfld.setText(mod.getAttention());
        this.attentionfld.setEditable(false);
        this.addressfld.setText(mod.getAddress());
        this.addressfld.setEditable(false);
        this.descfld.setText(mod.getDescription());
        this.descfld.setEditable(false);
        
        Iterator ir = iq.getMGItems(super.getType(), mod.getGinum());
        
        while(ir.hasNext()){
            items.add((InventoryModel) ir.next());
        }
        
        this.RefreshItems();
        
        this.savebtn.setDisable(true);
        this.addbtn.setDisable(true);
        this.editbtn.setDisable(true);
        this.deletebtn.setDisable(true);
        
        if(mod.getPgistat().equals("Y")){
            this.pgibtn.setDisable(true);
        }
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
    
}
