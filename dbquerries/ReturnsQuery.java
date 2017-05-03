/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbquerries;

import dbconn.DBConnect;
import dbconn.DBQuery;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import models.ReturnAdjustmentModel;
import models.ReturnsModel;

/**
 *
 * @author Steven
 * 
 * ALSO USED WITH
 * 
 * SM
 * 
 * MM
 * 
 */
public class ReturnsQuery {
    
    public void addReturns(ReturnsModel model, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call bestchem_db2.RETURNS_ADD(?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call bestchem_db2.MM_RETURNS_ADD(?,?,?,?,?);";
        }
                
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        st.setString(1, model.getSku());
        st.setString(2, model.getSkudesc());
        st.setString(3, model.getRetuom());
        st.setString(4, model.getRetwhs());
        st.setInt(5, model.getSoh());
        
        dbq.executeUpdateQuery(st);
        
        dbc.closeConnection();
        
    }
    
    public Iterator getReturns(int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM bestchem_db2.returns;";
        }
        else if(table == 2){
            query = "SELECT * FROM bestchem_db2.mm_returns;";
        }
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<ReturnsModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            ReturnsModel returns = new ReturnsModel();
            returns.setIdreturns((int) map.get("idreturns"));
            returns.setSku((String) map.get("sku"));
            returns.setSkudesc((String) map.get("desc"));
            returns.setRetuom((String) map.get("retuom"));
            returns.setRetwhs((String) map.get("retwhs"));
            returns.setSoh((int) map.get("soh"));
                        
            list.add(returns);
        }
        
        dbc.closeConnection();
        return list.iterator();
        
    }
    
    public ReturnsModel getReturn(int id, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM bestchem_db2.returns where idreturns = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM bestchem_db2.mm_returns where idreturns = ?;";
        }
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ReturnsModel returns = null;
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setInt(1, id);
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            returns = new ReturnsModel();
            returns.setIdreturns((int) map.get("idreturns"));
            returns.setSku((String) map.get("sku"));
            returns.setSkudesc((String) map.get("desc"));
            returns.setRetuom((String) map.get("retuom"));
            System.out.println((String) map.get("retwhs"));
            returns.setRetwhs((String) map.get("retwhs"));
            returns.setSoh((int) map.get("soh"));
        }
        
        dbc.closeConnection();
        return returns;
    }
    
    public Iterator getReturnsBySku(String sku, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM bestchem_db2.returns where sku LIKE ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM bestchem_db2.mm_returns where sku LIKE ?;";
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<ReturnsModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        sku = "%" + sku + "%";
        
        st.setString(1, sku);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            ReturnsModel returns = new ReturnsModel();
            returns.setIdreturns((int) map.get("idreturns"));
            returns.setSku((String) map.get("sku"));
            returns.setSkudesc((String) map.get("desc"));
            returns.setRetuom((String) map.get("retuom"));
            returns.setRetwhs((String) map.get("retwhs"));
            returns.setSoh((int) map.get("soh"));
            
            list.add(returns);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public void editReturns(int table){
        
        String query = "";
        
        if(table == 1){
            query = "";
        }
        else if(table == 2){
            query = "";
        }
        
    }
    
    public void addReturnAdjustment(ReturnAdjustmentModel ram, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "CALL RETURNS_ADJ_ADD(?,?,?,?)";
        }
        else if(table == 2){
            query = "CALL MM_RETURNS_ADJ_ADD(?,?,?,?)";
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setDate(1, Date.valueOf(ram.getRamdte().toString()));
        st.setString(2, ram.getRefnum());
        st.setString(3, ram.getDesc());
        st.setString(4, "N");
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int ramid = generatedKeys.getInt(1);
                
                this.addReturnAdjustmentItems(ram.getItems().iterator(), ramid, table);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void addReturnAdjustmentItems(Iterator items, int id, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call `RETURNS_ADJ_ADD_ITEMS`(?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call `MM_RETURNS_ADJ_ADD_ITEMS`(?,?,?,?,?);";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        while(items.hasNext()){
            ReturnsModel item = (ReturnsModel) items.next();
            
            ps.setInt(1, item.getIdreturns());
            ps.setInt(2, item.getSoh());
            ps.setString(3, item.getMov());
            ps.setString(4, "N");
            ps.setInt(5, id);
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
}
