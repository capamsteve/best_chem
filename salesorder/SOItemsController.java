/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesorder;

import dbquerries.InventoryQuery;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.InventoryModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class SOItemsController implements Initializable {
    
    @FXML
    private TextField qtyfld;

    @FXML
    private Button saveitembtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField inventoryidfld;
    
    private InventoryModel item;
    
    @FXML
    private TableView<InventoryModel> inventorytable;
    
    @FXML
    private Button searchbtn;
    
    @FXML
    private RadioButton descbtn;
    
    private boolean isCancelled = true;
    
    private final InventoryQuery iq = new InventoryQuery();

    @FXML
    public void saveItem(ActionEvent event) throws SQLException {
        
        //System.out.println(this.inventorytable.getSelectionModel().getSelectedItem().getIdinventory());
        
        this.isCancelled = false;
        try{
            if(!this.qtyfld.getText().isEmpty()){
                item = iq.getInventoryWPrice(this.inventorytable.getSelectionModel().getSelectedItem().getIdinventory(), 1);
            
                Stage stage = (Stage) cancelbtn.getScene().getWindow();
                stage.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter a Quantity");

                alert.showAndWait();
            }
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item");

            alert.showAndWait();
        }
        
    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        this.isCancelled = true;
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    public InventoryModel getItem() throws SQLException{
        return item;
    }
    
    public int getQty(){
        return Integer.valueOf(this.qtyfld.getText());
    }
    
    private void searchSKU(String sku) throws SQLException{

        String[] arr = {"sku", "description", "uom", "wh", "soh", "csl"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = null;
        
        if(this.descbtn.isSelected()){
            rs = iq.getInventoriesByDesc1(sku, 1);
        }else{
            rs = iq.getInventories(sku, 1);
        }
        
        while(rs.hasNext()){
            data.add((InventoryModel)rs.next());
        }
        
        ObservableList<TableColumn<InventoryModel, ?>> olist;
        olist = (ObservableList<TableColumn<InventoryModel, ?>>) inventorytable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        inventorytable.setItems(data);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.inventoryidfld.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                System.out.println(inventoryidfld.getText());
                try {
                    this.searchSKU(inventoryidfld.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.searchbtn.setOnAction((ActionEvent event) -> {
            System.out.println(inventoryidfld.getText());
            try {
                this.searchSKU(inventoryidfld.getText());
            } catch (SQLException ex) {
                Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.qtyfld.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                System.out.println(inventoryidfld.getText());
                try {
                    try{
                        if(!this.qtyfld.getText().isEmpty()){
                            item = iq.getInventoryWPrice(this.inventorytable.getSelectionModel().getSelectedItem().getIdinventory(), 1);

                            Stage stage = (Stage) cancelbtn.getScene().getWindow();
                            stage.close();
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("Please Enter a Quantity");

                            alert.showAndWait();
                        }
                    }catch(NullPointerException e){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select an item");

                        alert.showAndWait();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    

    /**
     * @return the isCancelled
     */
    public boolean IsCancelled() {
        return isCancelled;
    }
    
}
