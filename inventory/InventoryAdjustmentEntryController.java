/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import salesorder.SOItemsController;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class InventoryAdjustmentEntryController implements Initializable {
    
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
    private TableView<?> itemlist;

    @FXML
    private Label ptifld;

    @FXML
    private TextField reffld;

    @FXML
    private Button addbtn;

    @FXML
    public void saveHandler(ActionEvent event) {

    }

    @FXML
    public void addItem(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/inventory/ItemSelector.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        ItemSelectorController soic = fxmlloader.<ItemSelectorController>getController();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Item");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
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
    }    
    
}
