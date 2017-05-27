/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import dbquerries.PurchasesQuery;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import models.InventoryModel;
import models.PurchaseItemModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class PGRDocumentController extends AbstractController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private Button resetbtn;

    @FXML
    private Button editbtn;

    @FXML
    private TableView<PurchaseItemModel> itemlist;

    @FXML
    private Button pgrbtn;
    
    private int pmiid;
    
    private ArrayList<PurchaseItemModel> purchases = new ArrayList();
    
    private final PurchasesQuery pq = new PurchasesQuery();
    private final InventoryQuery iq = new InventoryQuery();

    @FXML
    void EditItem(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Item Quantity");
        dialog.setHeaderText("Item: " + this.itemlist.getSelectionModel().getSelectedItem().getSku() + "-" 
                + this.itemlist.getSelectionModel().getSelectedItem().getDesc() + "\n" 
                + "Current Quantity: " + this.itemlist.getSelectionModel().getSelectedItem().getQty());

        Optional<String> result = dialog.showAndWait();
        
        if(result.isPresent()){
            this.purchases.get(this.itemlist.getSelectionModel().getFocusedIndex()).setActualqty(Integer.valueOf(result.get()));
            this.itemlist.getItems().clear();
            this.RefreshItems();
        }
    }

    @FXML
    void ResetItems(ActionEvent event) {
        for(int i = 0; i < this.purchases.size(); i++){
            this.purchases.get(i).setActualqty(0);
        }
        this.RefreshItems();
    }

    @FXML
    void PostToInventory(ActionEvent event) throws SQLException {
        ArrayList<InventoryModel> mods = new ArrayList();
        for(int i = 0; i < this.purchases.size(); i++){
            InventoryModel mod = new InventoryModel(this.purchases.get(i).getIdinventory());
            
            mod.setSoh(this.purchases.get(i).getActualqty());
            mod.setMov("INC");
            
            mods.add(mod);
        }
        
        iq.PostUpdateInventory(mods.iterator());
        pq.PGItems(this.purchases.iterator(), this.pmiid);
    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "desc", "qty", "actualqty", "uom"};
        ObservableList<PurchaseItemModel> data
                = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.purchases.size(); i++){
            data.add(purchases.get(i));
        }
        ObservableList<TableColumn<PurchaseItemModel, ?>> olist = (ObservableList<TableColumn<PurchaseItemModel, ?>>) itemlist.getColumns();
        
        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        
        
        this.itemlist.setItems(data);
    }
    
    public void setPOItems(Iterator ir, int pmid){
        
        while(ir.hasNext()){
            PurchaseItemModel itm = (PurchaseItemModel) ir.next();
            itm.setActualqty(itm.getQty());
            this.purchases.add(itm);
        }
        
        this.pmiid = pmid;
        this.RefreshItems();
        
    }

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        super.setType(type);
    }

    
}
