/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.InventoryAdjustmentModel;
import models.InventoryModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class InventoryAdjustmentEntryController extends AbstractController implements Initializable {
    
    @FXML
    private Button postbtn;

    @FXML
    private TextField invadjfld;

    @FXML
    private Button editbtn;

    @FXML
    private DatePicker datefld;

    @FXML
    private Button pendingbtn;

    @FXML
    private TextArea descriptionfld;

    @FXML
    private Button cancelbtn;

    @FXML
    private TableView<InventoryModel> itemlist;

    @FXML
    private Label ptifld;

    @FXML
    private TextField reffld;

    @FXML
    private Button addbtn;
    
    private ArrayList<InventoryModel> items = new ArrayList();
    private ArrayList<InventoryModel> deleted = new ArrayList();
    
    private boolean isEdit;
    
    private InventoryQuery iq = new InventoryQuery();
    
    private InventoryAdjustmentModel iam_og;

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        InventoryAdjustmentModel iam = new InventoryAdjustmentModel();
        
        iam.setIam_dte(Date.valueOf(this.datefld.getValue().toString()));
        iam.setDesc(this.descriptionfld.getText());
        iam.setRefnum(this.reffld.getText());
        iam.setPgistat("N");
        
        iam.setItemslist(items);
        
        if(isEdit){
            iam.setIamid(Integer.parseInt(this.invadjfld.getText()));
            iq.editInventoryAdjustment(iam);
            
            ArrayList<InventoryModel> models = new ArrayList();
            for(InventoryModel mod: this.items){
                if(mod.getIadjid_item() == 0){
                    models.add(mod);
                }
            }
            
            if(!this.deleted.isEmpty()){
                iq.deleteInventoryAdjustmentItems(deleted.iterator(), iam.getIamid(), super.getType());
            }
            if(!models.isEmpty()){
                iq.addInventoryAdjustmentItems(models.iterator(), iam.getIamid(), super.getType());
            }
            
        }else{
            iq.addInventoryAdjustment(iam, super.getType());
        }
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addItem(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/ItemSelector.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ItemSelectorController soic = fxmlloader.<ItemSelectorController>getController();
        
        soic.initData(null,super.getType());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Item");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.items.add(soic.getitem());
        this.RefreshItems();
    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "description", "wh", "uom", "soh", "mov"};
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
        this.itemlist.getItems().clear();
        this.itemlist.setItems(data);
    }

    @FXML
    public void removeItem(ActionEvent event) {
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
    public void postToInventory(ActionEvent event) throws SQLException {
        
        iq.PostUpdateInventory(this.items.iterator());
        iq.changeInventoryAdjPost(this.items.iterator(), this.iam_og.getIamid());
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    public void AddMode(){
        this.invadjfld.setDisable(true);
        this.postbtn.setDisable(true);
    }
    
    public void Edit(InventoryAdjustmentModel iam) throws SQLException{
        
        this.iam_og = iam;
        this.isEdit = true;
        this.invadjfld.setText(String.valueOf(iam.getIamid()));
        this.invadjfld.setEditable(false);
        this.datefld.setValue(LocalDate.parse(iam.getIam_dte().toString()));
        this.descriptionfld.setText(iam.getDesc());
        this.reffld.setText(iam.getRefnum());
        this.ptifld.setText(iam.getPgistat());
        
        Iterator ir = iq.getInventoryAdjustmentsItems(iam.getIamid());
        
        while(ir.hasNext()){
            items.add((InventoryModel) ir.next());
        }
        
        this.postbtn.setDisable(true);
        this.RefreshItems();
    }
    
    public void ViewMode(InventoryAdjustmentModel iam) throws SQLException{

        this.iam_og = iam;
        this.invadjfld.setText(String.valueOf(iam.getIamid()));
        this.invadjfld.setEditable(false);
        this.datefld.setValue(LocalDate.parse(iam.getIam_dte().toString()));
        this.descriptionfld.setText(iam.getDesc());
        this.reffld.setText(iam.getRefnum());
        this.ptifld.setText(iam.getPgistat());
        
        Iterator ir = iq.getInventoryAdjustmentsItems(iam.getIamid());
        
        while(ir.hasNext()){
            items.add((InventoryModel) ir.next());
        }
        
        this.RefreshItems();
        this.addbtn.setDisable(true);
        this.editbtn.setDisable(true);
        this.pendingbtn.setDisable(true);
        
        if(iam.getPgistat().equals("Y")){
            this.postbtn.setDisable(true);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.ptifld.setText("N");
    }    

    @Override
    public void initData(UserModel user, int type) {
        super.setType(type);
    }
    
}
