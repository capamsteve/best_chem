/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package useradmin;

import dbquerries.UserQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import viewmodels.UserViewModel;

/**
 * FXML Controller class
 *
 * @author Steven
 */
public class UserAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private final UserQuery uq = new UserQuery();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            // TODO
            this.getUsers();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void getUsers() throws SQLException{
        Iterator ir = uq.getUsers();
        
        String[] arr = {"id", "name", "passwords", "type"};
        
        ObservableList<UserViewModel> data
                = FXCollections.observableArrayList();
        
        while(ir.hasNext()){
            HashMap map = (HashMap) ir.next();
            UserViewModel uv = new UserViewModel();
            uv.setId(Integer.valueOf(map.get("idusers").toString()));
            uv.setName(map.get("username").toString());
            uv.setPasswords(map.get("password").toString());
            uv.setType(map.get("usertype").toString());
            
            data.add(uv);
        }
        
        ObservableList<TableColumn<UserViewModel, ?>> olist;
        olist = (ObservableList<TableColumn<UserViewModel, ?>>) this.customerList.getColumns();

        for (int i = 0; i < arr.length; i++) {
            olist.get(i).setCellValueFactory(
                    new PropertyValueFactory<>(arr[i])
            );
        }
        this.customerList.setItems(data);
    }
    
     @FXML
    private TableView<UserViewModel> customerList;

    @FXML
    private Button addbtn;

    @FXML
    public void AddUser(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AddUserAdmin.fxml"));
        Parent root = (Parent) fxmlloader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage) addbtn.getScene().getWindow();
        Stage substage = new Stage();
        substage.setScene(scene);
        substage.setResizable(false);
        substage.sizeToScene();
        substage.setTitle("Add User");
        substage.initModality(Modality.WINDOW_MODAL);
        substage.initOwner(stage);
        substage.showAndWait();
        
        this.getUsers();
    }
}
