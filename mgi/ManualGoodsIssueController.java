/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgi;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.InventoryModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class ManualGoodsIssueController extends AbstractController implements Initializable {
    
    @FXML
    private TextField customerfld;

    @FXML
    private TextField addressfld;

    @FXML
    private Button editbtn;

    @FXML
    private TextArea descfld;

    @FXML
    private TextField contactfld;

    @FXML
    private TextField attentionfld;

    @FXML
    private TextField cbyfld;

    @FXML
    private TableView<InventoryModel> itemlist;

    @FXML
    private Button addbtn;

    @FXML
    private TextField refnumfld;

    @FXML
    private Button deletebtn;

    @FXML
    private Button resetbtn;

    @FXML
    private TextField idfld;

    @FXML
    private DatePicker datefld;

    @FXML
    private Button pendingbtn;

    @FXML
    private TextField drifld;
    
    @FXML
    private Button cancelbtn;

    @FXML
    private Button savebtn;

    private ArrayList<InventoryModel> items = new ArrayList();
    
    private InventoryQuery iq = new InventoryQuery();

    @FXML
    public void saveHandler(ActionEvent event) {

    }

    @FXML
    public void PendingHandler(ActionEvent event) {

    }
    
    @FXML
    void postToInventory(ActionEvent event) {

    }

    @FXML
    void cancelHandler(ActionEvent event) {

    }
    

    @FXML
    public void AddItem(ActionEvent event) throws IOException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mgi/MGItemSelector.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        MGItemSelectorController mgic = fxmlloader.<MGItemSelectorController>getController();
        
        mgic.initData(null,super.getType());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Item");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.items.add(mgic.getitem());
        this.RefreshItems();

    }

    @FXML
    public void EditItem(ActionEvent event) {

    }

    @FXML
    public void DeleteItem(ActionEvent event) {

    }

    @FXML
    public void ResetItems(ActionEvent event) {

    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "description", "wh", "uom", "soh"};
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
    
    public void AddMode(){
        this.idfld.setDisable(true);
        
    }
    
    public void EditMode(){
        
    }
    
    public void ViewMode(){
        
    }

    /**
     * Initializes the controller class.
     */
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
