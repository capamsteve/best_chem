/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.SupplierModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class PurchaseController implements Initializable {
    
    @FXML
    private TextField comfld;

    @FXML
    private Button viewbtn;

    @FXML
    private Button edtibtn;

    @FXML
    private TableView<?> purchtble;

    @FXML
    private TextField tinfld;

    @FXML
    private TextField addfld;

    @FXML
    private Button addpobtn;
    
    private SupplierModel supplier;
    
    @FXML
    public void addPurchaseOrder(ActionEvent event) throws IOException, SQLException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/purchases/PurchaseDocumentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        PurchaseDocumentController povc = fxmlloader.<PurchaseDocumentController>getController();
        povc.setSupplier(this.supplier);
        povc.AddMode();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addpobtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("View Supplier Orders");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();

    }

    @FXML
    public void editPurchaseOrder(ActionEvent event) {

    }

    @FXML
    public void viewPurchaseOrder(ActionEvent event) {

    }
    
    public void setSupplier(SupplierModel supmod){
        this.supplier = supmod;
        
        this.comfld.setText(this.supplier.getSupname());
        this.tinfld.setText(this.supplier.getSuptin());
        this.addfld.setText(this.supplier.getSupaddress());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
