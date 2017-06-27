/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases;

import best_chem.AbstractController;
import dbquerries.PurchasesQuery;
import java.io.IOException;
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
import models.PurchasesModel;
import models.UserModel;
import salesorder.SOItemsController;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class PurchaseOrderSelectorController extends AbstractController implements Initializable {
    
    @FXML
    private TableView<PurchasesModel> inventorytable;

    @FXML
    private Button saveitembtn;

    @FXML
    private Button searchbtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField inventoryidfld;
    
    private PurchasesModel pm_og;
    
    private boolean cancelled = true;
    
    private final PurchasesQuery pq = new PurchasesQuery();

    @FXML
    void saveItem(ActionEvent event) throws IOException {
        
        this.cancelled = false;
        
        this.pm_og = this.inventorytable.getSelectionModel().getSelectedItem();
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    public void searchPO(String poid) throws SQLException{
        
        Iterator ir = this.pq.getPurchases(poid);
        
        this.RefreshItems(ir);
        
    }
    
    public void RefreshItems(Iterator ir){
        String[] arr = {"idpurchases", "pgr_dtetme", "po_dr_dte", "supplier"};
        
        ObservableList<PurchasesModel> data
                = FXCollections.observableArrayList();
        
        while(ir.hasNext()){
            data.add((PurchasesModel) ir.next());
        }
        
        ObservableList<TableColumn<PurchasesModel, ?>> olist;
        olist = (ObservableList<TableColumn<PurchasesModel, ?>>) this.inventorytable.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setSortable(false);
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        
        this.inventorytable.getItems().clear();
        this.inventorytable.setItems(data);
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
                    this.searchPO(inventoryidfld.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.searchbtn.setOnAction((ActionEvent event) -> {
            System.out.println(inventoryidfld.getText());
            try {
                this.searchPO(inventoryidfld.getText());
            } catch (SQLException ex) {
                Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        super.setType(type);
        
        try {
            Iterator ir = pq.getPurchases();
            
            this.RefreshItems(ir);
            
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseOrderSelectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @return the pm_og
     */
    public PurchasesModel getPm_og() {
        return pm_og;
    }
}
