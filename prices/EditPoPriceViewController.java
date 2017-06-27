/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices;

import best_chem.AbstractController;
import dbquerries.InventoryQuery;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.PricesModel;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class EditPoPriceViewController extends AbstractController implements Initializable {
    
    @FXML
    private TextField pofld;

    @FXML
    private Button saveitembtn;
    
    @FXML
    private DatePicker edtefld;

    @FXML
    private Button cancelbtn;
    
    private PricesModel price;
    
    private InventoryQuery iq = new InventoryQuery();

    @FXML
    void savePrice(ActionEvent event) throws SQLException {
        
        PricesModel pmod = new PricesModel();
        
        pmod.setEffdte(Date.valueOf(this.edtefld.getValue()));
        pmod.setPoprice(Double.valueOf(this.pofld.getText()));
        pmod.setIdinventory(this.price.getIdinventory());
        pmod.setIdprices(this.price.getIdprices());
        
        iq.editPrice(pmod, super.getType());
        
        Stage stage = (Stage) saveitembtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    void cancelHandler(ActionEvent event) {
        
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

    @Override
    public void initData(UserModel user, int type) {
        super.setType(type);
    }
    
    public void setPrice(PricesModel price){
        this.price = price;
        
        this.pofld.setText(String.valueOf(this.price.getPoprice()));
        
        this.edtefld.setValue(LocalDate.parse(this.price.getEffdte().toString()));
    }
}
