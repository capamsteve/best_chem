/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import best_chem.AbstractController;
import dbquerries.PMInventoryQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.BOModel;
import models.InventoryModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class BOMViewController extends AbstractController implements Initializable {
    
    @FXML
    private Button resetbtn;

    @FXML
    private TextField warehousefld;

    @FXML
    private Button editbtn;

    @FXML
    private TextField skufld;

    @FXML
    private TextField fgidfld;

    @FXML
    private Button pendingbtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private TableView<BOModel> itemlist;

    @FXML
    private Button addbtn;

    @FXML
    private TextField uomfld;

    @FXML
    private TextField skudescfld;

    @FXML
    private Button deletebtn;
    
    private InventoryModel im_og;
    
    private ArrayList<BOModel> boms = new ArrayList();
    
    private final PMInventoryQuery pim = new PMInventoryQuery();

    @FXML
    void AddItem(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/BOMSelector.fxml"));
        Parent root = (Parent) fxmlloader.load();

        BOMSelectorController bsc = fxmlloader.<BOMSelectorController>getController();
        bsc.initData(super.getGlobalUser(), super.getType());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add BOM");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        if(!bsc.IsCancelled()){
            System.out.println("Controller: " + bsc.getItem().getIdinventory());
            if(!boms.contains(bsc.getItem())){
                if(bsc.getItem() != null){
                    this.pim.addBOM(bsc.getItem(), this.im_og.getIdinventory());
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("You did not select an item.");

                    alert.showAndWait();
                }
                
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
    }

    @FXML
    void EditItem(ActionEvent event) throws SQLException {
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Item Quantity");
        dialog.setHeaderText("Item: " + this.itemlist.getSelectionModel().getSelectedItem().getSku() + "-" 
                + this.itemlist.getSelectionModel().getSelectedItem().getDescription()+ "\n" 
                + "Current Quantity: " + this.itemlist.getSelectionModel().getSelectedItem().getBom_qty());

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
          
            BOModel btemp = this.boms.get(this.itemlist.getSelectionModel().getSelectedIndex());
            btemp.setBom_qty(Integer.valueOf(result.get()));
            this.pim.editBOM(btemp);
            this.RefreshItems();
        }

    }

    @FXML
    void DeleteItem(ActionEvent event) throws SQLException {
        if(!this.boms.isEmpty()){
            
            this.pim.deleteBOM(this.itemlist.getSelectionModel().getSelectedItem());
            
            this.RefreshItems();
        }
    }

    @FXML
    void ResetItems(ActionEvent event) throws SQLException {
        
        if(!this.boms.isEmpty()){
            for(BOModel mod : this.boms){
                this.pim.deleteBOM(mod);
            }
            this.RefreshItems();
        }

    }

    @FXML
    void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    public void RefreshItems() throws SQLException{
        String[] arr = {"sku", "description", "bom_qty", "uom", "whouse",};
        ObservableList<BOModel> data
                = FXCollections.observableArrayList();
        
        //GET BOM ITEMS
        boms = new ArrayList();
        
        Iterator ir = this.pim.getBOM(this.im_og.getIdinventory());
        
        while(ir.hasNext()){
            BOModel bom = (BOModel) ir.next();
            System.out.println("FROM DB: " + bom.getIdinventory());
            this.boms.add(bom);
        }
        
        data.addAll(boms);
        
        ObservableList<TableColumn<BOModel, ?>> olist;
        olist = (ObservableList<TableColumn<BOModel, ?>>) this.itemlist.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.itemlist.getItems().clear();
        this.itemlist.setItems(data);
    }
    
    public void EditMode(InventoryModel mod) throws SQLException{
        
        this.im_og = mod;
        
        this.fgidfld.setText(String.valueOf(mod.getIdinventory()));
        this.fgidfld.setEditable(false);
        this.skufld.setEditable(false);
        this.skudescfld.setEditable(false);
        this.uomfld.setEditable(false);
        this.warehousefld.setEditable(false);
        this.skudescfld.setText(mod.getDescription());
        this.skufld.setText(mod.getSku());
        this.uomfld.setText(mod.getUom());
        this.warehousefld.setText(mod.getWh());
        
        this.RefreshItems();
    }
    
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
