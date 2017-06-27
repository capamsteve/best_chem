/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Miguel
 */
public class DBQuery {
    private static final DBQuery instance = new DBQuery();
    
    private DBQuery(){

    }
    
    public static DBQuery getInstance(){
        return instance;
    }
    
    public Iterator getQueryResultSet(PreparedStatement pt) {
        try {
            DBConnect dbc = DBConnect.getInstance();
            Connection con = dbc.getConnection();
            Iterator list;
            try (ResultSet rs = pt.executeQuery()) {
                list = this.resultSetToArrayList(rs);
            }
            dbc.closeConnection();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Iterator getQueryResultSet2(PreparedStatement pt) {
        try {
            DBConnect dbc = DBConnect.getInstance();
            Connection con = dbc.getConnection();
            Iterator list;
            try (ResultSet rs = pt.executeQuery()) {
                list = this.resultSetToArrayListLabels(rs);
            }
            dbc.closeConnection();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void executeUpdateQuery(PreparedStatement pt) {
        try {
            DBConnect dbc = DBConnect.getInstance();
            Connection con = dbc.getConnection();
            pt.executeUpdate();
            dbc.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Iterator resultSetToArrayList(ResultSet rs) throws SQLException{
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList(50);
        while (rs.next()){
           HashMap row = new HashMap(columns);
           for(int i=1; i<=columns; ++i){
            row.put(md.getColumnName(i),rs.getObject(i));
           }
            list.add(row);
        }

       return list.iterator();
      }
    
    private Iterator resultSetToArrayListLabels(ResultSet rs) throws SQLException{
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList list = new ArrayList(50);
        while (rs.next()){
           HashMap row = new HashMap(columns);
           for(int i=1; i<=columns; ++i){
            row.put(md.getColumnLabel(i),rs.getObject(i));
           }
            list.add(row);
        }

       return list.iterator();
      }
}
