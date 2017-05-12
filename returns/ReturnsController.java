/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package returns;

import best_chem.AbstractController;
import dbquerries.ReturnsQuery;
import dbquerries.UtilitiesQuery;
import inventory.InventoryItemController;
import java.net.URL;
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
import models.ReturnsModel;
import models.UserModel;

public class ReturnsController extends AbstractController implements Initializable{

    private ArrayList<String> uoms = new ArrayList();
    private ArrayList<String> whs = new ArrayList();
    
    private boolean isEdit = false;

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
    
    private ReturnsModel retmod;

    //Queries
    UtilitiesQuery uq = new UtilitiesQuery();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
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
        this.isEdit = false;
    }
    
    public void EditMode(ReturnsModel retmod){
        this.retmod = retmod;
        this.isEdit = true;
        this.skufld.setText(retmod.getSku());
        this.sdescfld.setText(retmod.getSkudesc());
        this.uomfld.getSelectionModel().select(retmod.getRetuom());
        this.wrhsfld.getSelectionModel().select(retmod.getRetwhs());
        this.sohfld.setDisable(true);
        this.sohfld.setText(String.valueOf(retmod.getSoh()));
        
        this.savebtn.setText("Save Item");
    }
    
    public void ViewMode(){
        
    }
    
    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveReturns(ActionEvent event) throws SQLException {
        ReturnsModel returns;
        
        returns = new ReturnsModel(null);
        returns.setSku(this.skufld.getText());
        returns.setSkudesc(this.sdescfld.getText());
        returns.setRetuom(this.uomfld.getSelectionModel().getSelectedItem());
        returns.setRetwhs(this.wrhsfld.getSelectionModel().getSelectedItem());
        
        
        ReturnsQuery rq = new ReturnsQuery();
 
        if(this.isEdit){
            returns.setIdreturns(this.retmod.getIdreturns());
            rq.editReturns(returns, super.getType());
        }
        else{
            returns.setSoh(Integer.parseInt(this.sohfld.getText()));
            rq.addReturns(returns, super.getType());
        }
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initData(UserModel user, int type) {
        super.setType(type);
    }

}

