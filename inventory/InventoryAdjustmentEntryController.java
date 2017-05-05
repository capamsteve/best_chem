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
import java.util.ArrayList;
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
    
    private InventoryQuery iq = new InventoryQuery();

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        InventoryAdjustmentModel iam = new InventoryAdjustmentModel();
        
        iam.setIam_dte(Date.valueOf(this.datefld.getValue().toString()));
        iam.setDesc(this.descriptionfld.getText());
        iam.setRefnum(this.reffld.getText());
        iam.setPgistat("N");
        
        iam.setItemslist(items);
        
        iq.addInventoryAdjustment(iam, super.getType());
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
        this.itemlist.setItems(data);
    }

    @FXML
    public void removeItem(ActionEvent event) {

    }

    @FXML
    public void postToInventory(ActionEvent event) {

    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    public void AddMode(){
        this.invadjfld.setDisable(true);
    }
    
    public void Edit(){
        
    }
    
    public void ViewMode(){
        
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
