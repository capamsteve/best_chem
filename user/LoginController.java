/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import models.UserModel;
import dbquerries.UserQuery;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.MainController;
import main.ProgramSwitchController;
import useradmin.UserAdminController;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class LoginController implements Initializable {
    
    /**
     * Fields
     */
    
    @FXML
    private TextField descfld;

    @FXML
    private PasswordField passfld;
    
    @FXML
    private Button loginbtn;
    
    private UserQuery uq;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        uq = new UserQuery();
    }    
    
    //DONE
    @FXML
    public void SendLogin(ActionEvent event) throws IOException, SQLException {
        
        /**
         * After Login
         * If Successful:
         *  1. Sales Management User
         *  2. Material Management User
         *  3. User Administrator Admin
         */
        
        UserModel rs = uq.getUser(descfld.getText(), passfld.getText());
        String path = "/useradmin/UserAdminView.fxml";
        if(rs.getRole().equals("ADMINISTRATOR")){
            path = "/useradmin/UserAdminView.fxml";
        }
        else {
            path = "/main/ProgramSwitch.fxml";
        }
        
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(path));
        Parent root = (Parent) fxmlloader.load();
       
        Scene scene = new Scene(root);
        Stage mainstage = new Stage();
        mainstage.setScene(scene);
        mainstage.setTitle("Best Chem Management System");
        mainstage.setResizable(false);
        mainstage.sizeToScene();
        
        if(rs.getRole().equals("ADMINISTRATOR")){
            UserAdminController uac = fxmlloader.<UserAdminController>getController();
            
        }
        else {
            ProgramSwitchController mvc = fxmlloader.<ProgramSwitchController>getController();
            mvc.initData(rs, 0);
        }
        
        mainstage.show();
        Stage stage1 = (Stage) loginbtn.getScene().getWindow();
        
        stage1.close();
    }

    
}
