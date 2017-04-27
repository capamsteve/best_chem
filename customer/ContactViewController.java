/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Miguel
 */
public class ContactViewController implements Initializable {

    @FXML
    private TextField cnpersonfld;
    @FXML
    private TextField cnnumfld;
    @FXML
    private TextField cnemailfld;
    @FXML
    private Button addbtn;
    @FXML
    private Button cancelbtn;

    private boolean isCanceled;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        isCanceled = false;
    }    

    @FXML
    private void AddHandler(MouseEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void CancelHandler(MouseEvent event) {
        isCanceled = true;
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
    
    public String getContactName(){
        return cnpersonfld.getText();
    }
    
    public String getContactNumber(){
        return cnnumfld.getText();
    }
    
    public String getContactEmail(){
        return cnemailfld.getText();
    }
    
    public boolean checkifCanceled(){
        return isCanceled;
    }
    
}
