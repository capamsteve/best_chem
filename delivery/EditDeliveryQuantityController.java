/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class EditDeliveryQuantityController implements Initializable {
    
    @FXML
    private TextField qtyfld;

    @FXML
    private Button saveitembtn;

    @FXML
    private Button cancelbtn;
    
    private boolean isCancelled = false;
    
    private int limit = 0;
    
    private int qty;
    
    @FXML
    private Text limfld;

    @FXML
    void saveItem(ActionEvent event) {
        this.isCancelled = false;
        
        if(Integer.valueOf(this.qtyfld.getText()) > this.limit){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("The quantity if bigger than the limit");

            alert.showAndWait();
        }
        else{
            
            this.qty = Integer.valueOf(this.qtyfld.getText());
            
            Stage stage = (Stage) cancelbtn.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void cancelHandler(ActionEvent event) {
        
        this.isCancelled = true;
        
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
    
    public int getQty(){
        return this.qty;
    }
    
    public void setlimit(int limit){
        this.limit = limit;
        
        this.limfld.setText(String.valueOf(this.limit));
    }
    
    public boolean isCanceled(){
        return this.isCancelled;
    }
    
}
