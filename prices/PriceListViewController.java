/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices;

import best_chem.AbstractController;
import dbquerries.PMInventoryQuery;
import dbquerries.ReportsQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.PricesModel;
import models.SupplierModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class PriceListViewController extends AbstractController implements Initializable {
    
    @FXML
    private TextField prifld;

    @FXML
    private Button addPricebtn;

    @FXML
    private Button editPricebtn;
    
    @FXML
    private Button exportPricebtn;

    @FXML
    private TableView<PricesModel> pricetable;
    
    private ArrayList<PricesModel> prices = new ArrayList();

    @FXML
    private Button prsearchbtn;
    
    private SupplierModel sup_og;
    
    private final PMInventoryQuery pmiq = new PMInventoryQuery();
    private final ReportsQuery roq = new ReportsQuery();

    @FXML
    void addPrice(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/prices/AddPoPriceView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        AddPoPriceViewController apvc = fxmlloader.<AddPoPriceViewController>getController();
        
        apvc.initData(super.getGlobalUser(), super.getType());
        apvc.setSupplier(this.sup_og);

        Scene scene = new Scene(root);
        Stage stage = (Stage) addPricebtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Add Prices");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.RefreshItems();
    }

    @FXML
    void editPrice(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/prices/EditPoPriceView.fxml"));
        Parent root = (Parent) fxmlloader.load();

        EditPoPriceViewController epvc = fxmlloader.<EditPoPriceViewController>getController();

        epvc.initData(super.getGlobalUser(), super.getType());
        epvc.setPrice(this.pricetable.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addPricebtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Edit Prices");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.RefreshItems();
    }
    
    public void exportPrice() throws SQLException, IOException{
        roq.PRICE_REPORT(sup_og);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Report Created please see My Document/Reports");
        
        alert.showAndWait();
    }
    
    public void RefreshItems() throws SQLException{
        String[] arr = {"sku", "skudesc", "poprice1", "skuom", "whs" ,"effdte"};
        ObservableList<PricesModel> data = FXCollections.observableArrayList();
        Iterator ir = pmiq.getPricesSupplier(this.sup_og.getSupid());
        
        prices.clear();
        
        while(ir.hasNext()){
            PricesModel prm = (PricesModel) ir.next();
            
            prices.add(prm);
        }
        
        data.addAll(prices);
        
        ObservableList<TableColumn<PricesModel, ?>> olist;
        olist = (ObservableList<TableColumn<PricesModel, ?>>) pricetable.getColumns();

        for (int i = 0; i < olist.size(); i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
            
            if(i == 2 || i == 3){
                olist.get(i).setStyle("-fx-alignment: CENTER-RIGHT;");
            }
            else if(i == 4){
                olist.get(i).setStyle("-fx-alignment: CENTER;");
            }
            else{
                olist.get(i).setStyle("-fx-alignment: CENTER-LEFT;");
            }
        }
        pricetable.getItems().clear();
        pricetable.setItems(data);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setSupplier(SupplierModel sup) throws SQLException{
        
        this.sup_og = sup;
        this.RefreshItems();
    }

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        super.setType(type);
    }
    
}
