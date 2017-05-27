/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoices;

import dbquerries.DeliveryReceiptsQuery;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.DRModel;
import salesorder.SOItemsController;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class DRSelectorController implements Initializable {
    
    @FXML
    private TableView<DRModel> inventorytable;

    @FXML
    private Button saveitembtn;

    @FXML
    private Button searchbtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField inventoryidfld;
    
    private DRModel drm_og;
    
    private boolean isCancelled;
    
    private final DeliveryReceiptsQuery rq = new DeliveryReceiptsQuery();
    
    public void setDRs(int soid) throws SQLException{
        String[] arr = {"drid", "drdate", "remarks", "drprint", "pgi", "drstatus"};
        ObservableList<DRModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = rq.getDeliverReceipts(soid, "Y", "complete");
        
        while(rs.hasNext()){
            HashMap temp = (HashMap) rs.next();
            DRModel drvm = new DRModel(Integer.valueOf(temp.get("iddeliveryorders").toString()));

            drvm.setDrdate(Date.valueOf(temp.get("drdate").toString()));
            drvm.setPgi(temp.get("drpgi").toString());
            drvm.setDrprint(temp.get("drprint").toString());
            drvm.setDrstatus(temp.get("drstatus").toString());
            drvm.setRemarks(temp.get("remarks").toString());
            data.add(drvm);
        }
        
        ObservableList<TableColumn<DRModel, ?>> olist;
        olist = (ObservableList<TableColumn<DRModel, ?>>) inventorytable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        inventorytable.setItems(data);
    }
    
    @FXML
    void saveItem(ActionEvent event) throws SQLException {
        
        this.isCancelled = false;
        try{
            
            drm_og = rq.getDR(this.inventorytable.getSelectionModel().getSelectedItem().getDrid());
            
            Stage stage = (Stage) cancelbtn.getScene().getWindow();
            stage.close();
            
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item");

            alert.showAndWait();
        }
    }

    @FXML
    void cancelHandler(ActionEvent event) {
        this.isCancelled = true;
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    private void searchDRID(String sku) throws SQLException{

        String[] arr = {"drid", "drdate", "remarks", "drprint", "pgi", "drstatus"};
        ObservableList<DRModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = rq.getDRbyID(sku);
        
        while(rs.hasNext()){
            data.add((DRModel)rs.next());
        }
        
        ObservableList<TableColumn<DRModel, ?>> olist;
        olist = (ObservableList<TableColumn<DRModel, ?>>) inventorytable.getColumns();

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
                    this.searchDRID(this.inventoryidfld.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.searchbtn.setOnAction((ActionEvent event) -> {
            System.out.println(inventoryidfld.getText());
            try {
                this.searchDRID(inventoryidfld.getText());
            } catch (SQLException ex) {
                Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    

    /**
     * @return the drm_og
     */
    public DRModel getDrm_og() {
        return drm_og;
    }

    /**
     * @return the isCancelled
     */
    public boolean isIsCancelled() {
        return isCancelled;
    }
    
}
