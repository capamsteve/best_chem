/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package returns;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import dbquerries.ReturnsQuery;
import inventory.ItemSelectorController;
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
import models.ReturnAdjustmentModel;
import models.ReturnsModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class ReturnsAdjustmentController extends AbstractController implements Initializable {

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
    private TableView<ReturnsModel> itemlist;

    @FXML
    private Label ptifld;

    @FXML
    private TextField reffld;

    @FXML
    private Button addbtn;
    
    private ArrayList<ReturnsModel> items = new ArrayList();
    
    private ReturnsQuery rq = new ReturnsQuery();

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        ReturnAdjustmentModel ram = new ReturnAdjustmentModel();
        
        ram.setRamdte(Date.valueOf(this.datefld.getValue().toString()));
        ram.setDesc(this.descriptionfld.getText());
        ram.setRefnum(this.reffld.getText());
        ram.setPgistat("N");
        
        ram.setItems(items);
        
        rq.addReturnAdjustment(ram, super.getType());
    }

    @FXML
    public void addItem(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/returns/ReturnSelector.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ReturnSelectorController roic = fxmlloader.<ReturnSelectorController>getController();
        roic.setType(super.getType());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Item");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.items.add(roic.getItem());
        this.RefreshItems();
    }
    
    public void RefreshItems(){
        String[] arr = {"sku", "skudesc", "retwhs", "retuom", "soh", "mov"};
        ObservableList<ReturnsModel> data
                = FXCollections.observableArrayList();
        
        data.addAll(items);
        
        ObservableList<TableColumn<ReturnsModel, ?>> olist;
        olist = (ObservableList<TableColumn<ReturnsModel, ?>>) this.itemlist.getColumns();

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
