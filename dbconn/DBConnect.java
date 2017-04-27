/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Steven
 */
public class DBConnect {

    private static final DBConnect instance = new DBConnect();
    private static Connection connect;

    private DBConnect() {
        connect();
    }

    private void connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/bestchem_db2",
                user = "admin",
                password = "admin";
            
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, password);
            System.out.println("Connected!");
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage());
        }
    }

    public static DBConnect getInstance() {
        return instance;
    }

    public Connection getConnection() {
        try {
            if(connect.isClosed()){
                connect();
            }
            return connect;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    public void closeConnection() {
        try {
            connect.close();
            System.out.println("Connection has been closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
