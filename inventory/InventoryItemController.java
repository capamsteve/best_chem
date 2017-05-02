/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import best_chem.AbstractController;
import models.InventoryModel;
import customer.CustomerViewController;
import dbquerries.InventoryQuery;
import dbquerries.UtilitiesQuery;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class InventoryItemController extends AbstractController implements Initializable {
    
    private ArrayList<String> uoms = new ArrayList();
    private ArrayList<String> whs = new ArrayList();
    
    private boolean isEdit = false;
    
    
    @FXML
    private TextField cslfld;

    @FXML
    private ComboBox<String> wrhsfld;

    @FXML
    private TextField skufld;

    @FXML
    private TextField sdescfld;

    @FXML
    private TextField sohfld;

    @FXML
    private Button cancelbtn;

    @FXML
    private ComboBox<String> uomfld;

    @FXML
    private Button savebtn;

    //Queries
    UtilitiesQuery uq = new UtilitiesQuery();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Iterator rs = null;
        Iterator rs2 = null;
        try {
            rs = uq.getUOM();
            rs2 = uq.getWHS();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        while(rs.hasNext()){
            uoms.add((String)rs.next());
        }
        while(rs2.hasNext()){
            whs.add((String)rs2.next());
        }
        
        this.uomfld.getItems().addAll(uoms);
        this.wrhsfld.getItems().addAll(whs);
        this.uomfld.getSelectionModel().selectFirst();
        this.wrhsfld.getSelectionModel().selectFirst();
    }    
    
    
    public void AddMode(){
        
    }
    
    public void EditMode(){
        
    }
    
    @FXML
    public void saveInventory(ActionEvent event) throws SQLException {
        InventoryModel inventory;
        
        inventory = new InventoryModel();
        inventory.setSku(this.skufld.getText());
        inventory.setDescription(this.sdescfld.getText());
        inventory.setUom(this.uomfld.getSelectionModel().getSelectedItem());
        inventory.setWh(this.wrhsfld.getSelectionModel().getSelectedItem());
        inventory.setSoh(Integer.parseInt(this.sohfld.getText()));
        inventory.setCsl(Integer.parseInt(this.cslfld.getText()));
        
        InventoryQuery iq = new InventoryQuery();
        
        iq.addInventory(inventory, super.getType());
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initData(UserModel user, int type) {
        super.setType(type);
    }
    
}
