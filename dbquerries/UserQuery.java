/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbquerries;

import dbconn.DBConnect;
import dbconn.DBQuery;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.bind.DatatypeConverter;
import models.UserModel;

/**
 *
 * @author Steven
 */
public class UserQuery {
    
    public UserModel getUser(String username, String password) throws SQLException, UnsupportedEncodingException{
        
        UserModel model = null;
        MessageDigest md = null;

        try {
                md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
        }
        md.update(password.getBytes("UTF-8"));

        byte raw[] = md.digest();
        String hash = DatatypeConverter.printBase64Binary(raw);
        System.out.println(hash);
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `USER_GET`(?,?);");
        st.setString(1, username);
        st.setString(2, hash);
        Iterator set = db.getQueryResultSet(st);
        
        if(set.hasNext()){
            HashMap map = (HashMap)set.next();
            model = new UserModel((int)map.get("idusers"), (String)map.get("username"), (String)map.get("usertype"), (String)map.get("name"));
        }
        
        return model;
    }
    
    public void addUser(UserModel user) throws SQLException, UnsupportedEncodingException{
        DBConnect dbc = DBConnect.getInstance();
        
        MessageDigest md = null;

        try {
                md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
        }
        md.update(user.getPassword().getBytes("UTF-8"));

        byte raw[] = md.digest();
        String hash = DatatypeConverter.printBase64Binary(raw);
        System.out.println(hash);
        
        PreparedStatement st = dbc.getConnection().prepareCall("CALL `USER_ADD`(?,?,?)");
        
        st.setString(1, user.getUsername());
        st.setString(2, hash);
        st.setString(3, user.getRole());
        
        st.execute();
    }
    
    public Iterator getUserTypes() throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM user_type;");
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        return rs;
    }
    
    public void editUder(UserModel user){
        
    }
    
    public Iterator getUsers() throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM users;");
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        return rs;
        
    }
    
}
