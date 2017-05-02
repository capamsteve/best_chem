/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import best_chem.AbstractController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.UserModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class ProgramSwitchController extends AbstractController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }  
    
    @FXML
    private Text useridfld;
    
    @FXML
    private Button smbtn;

    @FXML
    private Button mmbtn;
    
    private int tp;

    @FXML
    public void salesmanagementhandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/main/Main.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        MainController mvc = fxmlloader.<MainController>getController();
        tp = 1;
        mvc.initData(super.getGlobalUser(), tp);
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) smbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Best Chem Management System Sales Management");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
    }

    @FXML
    public void materialmanagementhandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/main/MMain.fxml"));
        Parent root = (Parent) fxmlloader.load();
        
        MainController_1 mvc = fxmlloader.<MainController_1>getController();
        mvc.initData(super.getGlobalUser(), 2);

        Scene scene = new Scene(root);
        Stage stage = (Stage) smbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Best Chem Management System Material Management");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
    }

    @Override
    public void initData(UserModel user, int type) {
        super.setGlobalUser(user);
        useridfld.setText(String.valueOf(super.getGlobalUser().getId()));
    }
    
}
