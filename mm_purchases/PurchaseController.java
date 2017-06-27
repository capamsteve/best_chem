/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mm_purchases;

import best_chem.AbstractController;
import dbquerries.PurchasesQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.PurchasesModel;
import models.SupplierModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class PurchaseController extends AbstractController implements Initializable {
    
    @FXML
    private TextField comfld;

    @FXML
    private Button viewbtn;

    @FXML
    private Button edtibtn;

    @FXML
    private TableView<PurchasesModel> purchtble;

    @FXML
    private TextField tinfld;

    @FXML
    private TextField addfld;

    @FXML
    private Button addpobtn;
    
    private SupplierModel supplier;
    
    private final PurchasesQuery pq = new PurchasesQuery();
    
    @FXML
    public void addPurchaseOrder(ActionEvent event) throws IOException, SQLException {
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mm_purchases/PurchaseDocumentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        PurchaseDocumentController povc = fxmlloader.<PurchaseDocumentController>getController();
        povc.setSupplier(this.supplier);
        povc.initData(super.getGlobalUser(), super.getType());
        povc.AddMode();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addpobtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Add Supplier Orders");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getPurchases();
    }

    @FXML
    public void editPurchaseOrder(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mm_purchases/PurchaseDocumentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        PurchaseDocumentController povc = fxmlloader.<PurchaseDocumentController>getController();
        povc.setSupplier(this.supplier);
        povc.initData(super.getGlobalUser(), super.getType());
        povc.EditMode(this.purchtble.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addpobtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("Edit Supplier Orders");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getPurchases();
    }

    @FXML
    public void viewPurchaseOrder(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/mm_purchases/PurchaseDocumentView.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        PurchaseDocumentController povc = fxmlloader.<PurchaseDocumentController>getController();
        povc.setSupplier(this.supplier);
        povc.initData(super.getGlobalUser(), super.getType());
        povc.ViewMode(this.purchtble.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        Stage stage = (Stage) addpobtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setTitle("View Supplier Orders");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getPurchases();
    }
    
    public void getPurchases() throws SQLException{
        
        String[] arr = {"idpurchases", "po_dte", "po_dr_dte", "stat", "pgistat", "prntstat"};
        
        ObservableList<PurchasesModel> data
                = FXCollections.observableArrayList();
        
        Iterator map = this.pq.getPurchaseOrder(this.supplier.getSupid(), super.getType());
        
        while(map.hasNext()){
            
            HashMap temp = (HashMap) map.next();
            PurchasesModel povm = new PurchasesModel();
            povm.setIdpurchases(Integer.valueOf(temp.get("idpurchases").toString()));
            povm.setPo_dte(Date.valueOf(temp.get("po_dte").toString()));
            povm.setPo_dr_dte(Date.valueOf(temp.get("po_dr_dte").toString()));
            povm.setPgistat(temp.get("pgistat").toString());
            povm.setStat(temp.get("status").toString());
            povm.setPrntstat(temp.get("prntstat").toString());
            povm.setSupcname(temp.get("sup_c_name").toString());
            
            data.add(povm);
        }
        
        ObservableList<TableColumn<PurchasesModel, ?>> olist;
        olist = (ObservableList<TableColumn<PurchasesModel, ?>>) this.purchtble.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setSortable(false);
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.purchtble.setItems(data);
    }
    
    public void setSupplier(SupplierModel supmod){
        this.supplier = supmod;
        
        this.comfld.setText(this.supplier.getSupname());
        this.comfld.setEditable(false);
        this.tinfld.setText(this.supplier.getSuptin());
        this.tinfld.setEditable(false);
        this.addfld.setText(this.supplier.getSupaddress());
        this.addfld.setEditable(false);
        
        try {
            this.getPurchases();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
