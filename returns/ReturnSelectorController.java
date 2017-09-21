/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package returns;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import dbquerries.ReturnsQuery;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.InventoryModel;
import models.ReturnsModel;
import models.UserModel;
import salesorder.SOItemsController;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class ReturnSelectorController extends AbstractController implements Initializable {
@FXML
    private TextField qtyfld;

    @FXML
    private TableView<ReturnsModel> inventorytable;

    @FXML
    private Button saveitembtn;

    @FXML
    private Button searchbtn;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField inventoryidfld;
    
    @FXML
    private RadioButton incbtn;

    @FXML
    private RadioButton decbtn;
    
    @FXML
    private RadioButton descbtn;

    @FXML
    private ToggleGroup togglegroup1;
    
    private ReturnsModel item;
    
    private boolean isCancelled = false;
    
    private final ReturnsQuery rq = new ReturnsQuery();
    
    @FXML
    public void additem(ActionEvent event) throws SQLException {
        
        RadioButton selectedRadioButton = (RadioButton) this.togglegroup1.getSelectedToggle();
        
        this.item = rq.getReturn(this.inventorytable.getSelectionModel().getSelectedItem().getIdreturns(), super.getType());
        this.item.setSoh(Integer.valueOf(this.qtyfld.getText()));
        Stage stage = (Stage) saveitembtn.getScene().getWindow();
        
        if(selectedRadioButton == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please select either Increment or Decrement");

            alert.showAndWait();
        }
        else if(selectedRadioButton.getText().equals("INCREMENT")){
            System.out.println(selectedRadioButton.getText());
            this.item.setMov("INC");
            stage.close();
        }
        else if(selectedRadioButton.getText().equals("DECREMENT")){
            System.out.println(selectedRadioButton.getText());
            this.item.setMov("DEC");
            stage.close();
        }
    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        this.isCancelled = true;
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    private void searchSKU(String sku) throws SQLException{

        String[] arr = {"sku", "skudesc", "retuom", "retwhs", "soh", "mov"};
        ObservableList<ReturnsModel> data
                = FXCollections.observableArrayList();
        
        Iterator rs = null;
        
        if(this.descbtn.isSelected()){
            rs = rq.getReturnsByDesc(sku, super.getType());
        }else{
            rs = rq.getReturnsBySku(sku, super.getType());
        }
        
        while(rs.hasNext()){
            data.add((ReturnsModel)rs.next());
        }
        
        ObservableList<TableColumn<ReturnsModel, ?>> olist;
        olist = (ObservableList<TableColumn<ReturnsModel, ?>>) inventorytable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        inventorytable.setItems(data);
    }
    
    public ReturnsModel getItem(){
        return item;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        this.togglegroup1 = new ToggleGroup();
        
        this.incbtn.setToggleGroup(togglegroup1);
        this.decbtn.setToggleGroup(togglegroup1);
        
        this.inventoryidfld.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                System.out.println(inventoryidfld.getText());
                try {
                    this.searchSKU(inventoryidfld.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.searchbtn.setOnAction((ActionEvent event) -> {
            System.out.println(inventoryidfld.getText());
            try {
                this.searchSKU(inventoryidfld.getText());
            } catch (SQLException ex) {
                Logger.getLogger(SOItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    

    /**
     * @return the isCancelled
     */
    public boolean IsCancelled() {
        return isCancelled;
    }

    @Override
    public void initData(UserModel user, int type) {
        super.setType(type);
    }
    
}
