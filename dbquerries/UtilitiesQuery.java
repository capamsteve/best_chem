/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbquerries;

import dbconn.DBConnect;
import dbconn.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import models.UOMmodel;
import models.WHSModel;

/**
 *
 * @author Steven
 */
public class UtilitiesQuery {
    
    public Iterator getUOM() throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        DBQuery db = DBQuery.getInstance();
        ArrayList list = new ArrayList();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call GET_UOM()");
        
        Iterator rs = db.getQueryResultSet(ps);
        
        while(rs.hasNext()){
            HashMap hello = (HashMap)rs.next();
            list.add(hello.get("uomcol"));
            System.out.println(hello.get("uomcol"));
            
        }
        
        return list.iterator();
    }
    
    public Iterator getWHS() throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        DBQuery db = DBQuery.getInstance();
        ArrayList list = new ArrayList();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call WAREHOUSE_GET()");
        
        Iterator rs = db.getQueryResultSet(ps);
        
        while(rs.hasNext()){
            HashMap hello = (HashMap)rs.next();
            list.add(hello.get("idwarehouses"));
            System.out.println(hello.get("idwarehouses"));
            
        }
        
        return list.iterator();
    }
    
    
    public void addUOM(String model) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement ps = dbc.getConnection().prepareStatement("call ADD_UOM(?);");
        
        ps.setString(1, model);
        
        ps.execute();
        
        dbc.closeConnection();
    }
    
    public void addWHS(String model) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call WAREHOUSE_ADD(?);");
        
        ps.setString(1, model);
        
        ps.execute();
        
        dbc.closeConnection();
    }
    
    public void editUOM(String model, String newVal) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call EDIT_UOM(?,?);");
        
        ps.setString(1, newVal);
        ps.setString(2, model);
        
        ps.execute();
        
    }
    
    public void editWHS(String model, String newVal) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call EDIT_WHS(?,?);");
        ps.setString(1, newVal);
        ps.setString(2, model);
        
        ps.execute();
    }
}
