/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import dbquerries.UtilitiesQuery;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class UOMController implements Initializable {
    
    @FXML
    private Button cancelbtn;

    @FXML
    private TextField uomfld;

    @FXML
    private Button savebtn;
    
    //Check Flags 
    private boolean isEdit = false;
    
    //Old Val
    private String oldVal = "";
    
    //Queries
    UtilitiesQuery uq = new UtilitiesQuery();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    } 

    @FXML
    public void saveUOM(ActionEvent event) throws SQLException {
        
        if(isEdit){
            System.out.println("Boom Edit");
            uq.editUOM(oldVal, this.uomfld.getText());
        }
        else{
            uq.addUOM(this.uomfld.getText());
        }
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
        
    }
    
    public void EditMode(String oldVal){
        this.uomfld.setText(oldVal);
        this.oldVal = oldVal;
        this.isEdit = true;
    }
    
    public void AddMode(){
        this.isEdit = false;
    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }  
}
