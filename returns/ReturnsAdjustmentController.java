/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package returns;

import best_chem.AbstractController;
import dbquerries.ReturnsQuery;
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
import javafx.scene.control.Alert;
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
    
    private boolean isEdit;
    
    private ArrayList<ReturnsModel> items = new ArrayList();
    private ArrayList<ReturnsModel> deleted = new ArrayList();
    
    private ReturnAdjustmentModel ram_og;
    
    private ReturnsQuery rq = new ReturnsQuery();

    @FXML
    public void saveHandler(ActionEvent event) throws SQLException {
        
        ReturnAdjustmentModel ram = new ReturnAdjustmentModel();
        
        ram.setRamdte(Date.valueOf(this.datefld.getValue().toString()));
        ram.setDesc(this.descriptionfld.getText());
        ram.setRefnum(this.reffld.getText());
        ram.setPgistat("N");
        
        ram.setItems(items);
        
        ArrayList<ReturnsModel> addedinEdit = new ArrayList();
        
        if(isEdit){
            ram.setRamid(Integer.parseInt(this.invadjfld.getText()));
            rq.editReturnAdjustment(ram, super.getType());
            if(!this.items.isEmpty()){
                for(ReturnsModel mod : this.items){
                    if(mod.getRetadjitemid() == 0){
                        addedinEdit.add(mod);
                    }
                }
                rq.addReturnAdjustmentItems(addedinEdit.iterator(), ram.getRamid(), super.getType());
            }
            if(!this.deleted.isEmpty()){
                rq.editReturnAdjustmentItems(this.deleted.iterator(), super.getType());
            }
        }else{
            rq.addReturnAdjustment(ram, super.getType());
        }
        
        
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
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
        
        ReturnsModel ro = roic.getItem();
        
        if(!this.items.contains(ro)){
            this.items.add(ro);
            this.RefreshItems();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You have already added this item.");

            alert.showAndWait();
        }
        
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
        /**
         * Get ITEMS convert to Y
         */
        
        rq.PostReturnAdjustment(this.items.iterator(), this.ram_og.getRamid(), super.getType());
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
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
        super.setGlobalUser(user);
        super.setType(type);
    }
    
    public void AddMode(){
        this.isEdit = false;
        this.datefld.setValue(LocalDate.now());
        this.invadjfld.setDisable(true);
        this.postbtn.setDisable(true);
    }
    
    public void EditMode(ReturnAdjustmentModel ram) throws SQLException{
        this.isEdit = true;
        this.ram_og = ram;
        this.datefld.setValue(LocalDate.parse(ram.getRamdte().toString()));
        this.reffld.setText(ram.getRefnum());
        this.ptifld.setText(ram.getPgistat());
        this.invadjfld.setText(String.valueOf(ram.getRamid()));
        this.invadjfld.setEditable(false);
        this.descriptionfld.setText(ram.getDesc());
        
        Iterator ir = rq.getReturnAdjustmentItems(ram.getRamid(), super.getType());
        
        int i = 0;
        while(ir.hasNext()){
            ReturnsModel rm = (ReturnsModel) ir.next();
            
            items.add(rm);
            i++;
        }
        
        System.out.println(i);
        this.postbtn.setDisable(true);
        this.RefreshItems();
        
    }
    
    public void ViewMode(ReturnAdjustmentModel ram) throws SQLException{
        this.ram_og = ram;
        this.datefld.setValue(LocalDate.parse(ram.getRamdte().toString()));
        this.datefld.setEditable(false);
        this.reffld.setText(ram.getRefnum());
        this.reffld.setEditable(false);
        this.ptifld.setText(ram.getPgistat());
        this.invadjfld.setText(String.valueOf(ram.getRamid()));
        this.invadjfld.setEditable(false);
        this.descriptionfld.setText(ram.getDesc());
        this.descriptionfld.setEditable(false);
        
        Iterator ir = rq.getReturnAdjustmentItems(ram.getRamid(), super.getType());
        
        while(ir.hasNext()){
            ReturnsModel rm = (ReturnsModel) ir.next();
            
            items.add(rm);
        }
        
        this.RefreshItems();
        this.pendingbtn.setDisable(true);
        this.addbtn.setDisable(true);
        this.editbtn.setDisable(true);
        
        if(ram.getPgistat().equals("Y")){
            this.postbtn.setDisable(true);
        }
    }
}
