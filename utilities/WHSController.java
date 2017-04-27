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
public class WHSController implements Initializable {
    
    @FXML
    private Button cancelbtn;

    @FXML
    private TextField whsfld;

    @FXML
    private Button savebtn;
    
    //Check Flags 
    private boolean isEdit = false;
    
    //Old String
    private String oldVal = "";
    
    //Utility Query
    private UtilitiesQuery uq = new UtilitiesQuery();

    @FXML
    public void saveWHS(ActionEvent event) throws SQLException {
        if(isEdit){
            System.out.println("boom Edit");
            System.out.println(oldVal);
            System.out.println(this.whsfld.getText());
            uq.editWHS(oldVal, this.whsfld.getText());
        }
        else{
            uq.addWHS(this.whsfld.getText());
        }
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    public void EditMode(String oldVal){
        this.whsfld.setText(oldVal);
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
