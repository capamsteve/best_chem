/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import dbquerries.PMInventoryQuery;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.BOModel;
import models.InventoryModel;
import models.UserModel;
import salesorder.SOItemsController;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class BOMSelectorController extends AbstractController implements Initializable {
    
    @FXML
    private TextField qtyfld;

    @FXML
    private TableView<InventoryModel> inventorytable;

    @FXML
    private Button saveitembtn;

    @FXML
    private Button searchbtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField inventoryidfld;
    
    private InventoryModel item;
    
    private BOModel bomitem;
    
    private boolean isCancelled = false;
    
    private final PMInventoryQuery piq = new PMInventoryQuery();

    @FXML
    void additem(ActionEvent event) throws SQLException {
        
        this.item = this.inventorytable.getSelectionModel().getSelectedItem();
        
        System.out.println("Selector: " + this.item.getIdinventory());
        
        this.bomitem = new BOModel(this.item.getIdinventory());
        this.bomitem.setSku(this.item.getSku());
        this.bomitem.setDescription(this.item.getDescription());
        this.bomitem.setUom(this.item.getUom());
        this.bomitem.setWhouse(this.item.getWh());
        this.bomitem.setBom_qty(Integer.valueOf(this.qtyfld.getText()));
        
        System.out.println(this.bomitem.getSku());
        System.out.println(this.bomitem.getDescription());
        System.out.println(this.bomitem.getBom_qty());
        
        Stage stage = (Stage) saveitembtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    void cancelHandler(ActionEvent event) {
        this.isCancelled = true;
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    private void searchSKU(String sku) throws SQLException{

        String[] arr = {"sku", "description", "uom", "wh", "soh", "csl"};
        ObservableList<InventoryModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = piq.getInventories(sku);
        
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
    }   
    
    public boolean IsCancelled() {
        return isCancelled;
    }

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        super.setType(type);
    }

    /**
     * @return the item
     */
    public BOModel getItem() {
        return bomitem;
    }
    
}
