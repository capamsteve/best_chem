/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package useradmin;

import dbquerries.UserQuery;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class AddUserAdminController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> utypes = new ArrayList();
        try {
            // TODO
            Iterator rs = uq.getUserTypes();
            
            while(rs.hasNext()){
                HashMap map = (HashMap) rs.next();
                
                utypes.add(map.get("type_name").toString());
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddUserAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.usertyp.getItems().addAll(utypes);
    }    
    
    @FXML
    private TextField cnpersonfld;

    @FXML
    private PasswordField passfld;

    @FXML
    private Button cancelbtn;
    
    @FXML
    private TextField namefld;

    @FXML
    private ComboBox<String> usertyp;

    @FXML
    private Button addbtn;
    
    private final UserQuery uq = new UserQuery(); 

    @FXML
    public void AddHandler(ActionEvent event) throws SQLException, UnsupportedEncodingException {
        
        UserModel model = new UserModel(this.cnpersonfld.getText(), this.passfld.getText(), this.usertyp.getSelectionModel().getSelectedItem(), this.namefld.getText());
        
        uq.addUser(model);
        
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }
}
