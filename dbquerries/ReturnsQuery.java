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
            query = "call RETURNS_ADD(?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call MM_RETURNS_ADD(?,?,?,?,?);";
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
            query = "SELECT * FROM returns;";
        }
        else if(table == 2){
            query = "SELECT * FROM mm_returns;";
        }
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<ReturnsModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            ReturnsModel returns = new ReturnsModel(Integer.valueOf(map.get("idreturns").toString()));
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
            query = "SELECT * FROM returns where idreturns = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM mm_returns where idreturns = ?;";
        }
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ReturnsModel returns = null;
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setInt(1, id);
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            returns = new ReturnsModel(Integer.valueOf(map.get("idreturns").toString()));
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
            query = "SELECT * FROM returns where sku LIKE ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM mm_returns where sku LIKE ?;";
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
            ReturnsModel returns = new ReturnsModel(Integer.valueOf(map.get("idreturns").toString()));
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
    
    public void editReturns(ReturnsModel model, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call RETURNS_EDIT(?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call MM_RETURNS_EDIT(?,?,?,?,?);";
        }
                
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        st.setString(1, model.getSku());
        st.setString(2, model.getSkudesc());
        st.setString(3, model.getRetuom());
        st.setString(4, model.getRetwhs());
        st.setInt(5, model.getIdreturns());
        
        dbq.executeUpdateQuery(st);
        
        dbc.closeConnection();
        
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
    
    public void editReturnAdjustment(ReturnAdjustmentModel ram, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "CALL RETURNS_ADJ_EDIT(?,?,?,?)";
        }
        else{
            query = "CALL MM_RETURNS_ADJ_EDIT(?,?,?,?)";
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setDate(1, Date.valueOf(ram.getRamdte().toString()));
        st.setString(2, ram.getRefnum());
        st.setString(3, ram.getDesc());
        st.setInt(4, ram.getRamid());
        
        st.executeUpdate();
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
    
    public void editReturnAdjustmentItems(Iterator items, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "call `RETURNS_ADJ_EDIT_ITEMS`(?);";
        }
        else{
            query = "call `MM_RETURNS_ADJ_EDIT_ITEMS`(?);";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        while(items.hasNext()){
            ReturnsModel item = (ReturnsModel) items.next();
            
            if(item.getRetadjitemid() != 0){
                ps.setInt(1, item.getRetadjitemid());

                ps.addBatch();
            }
            
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public Iterator getReturnAdjustments(int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM returns_adjustments;";
        }
        else if(table == 2){
            query = "SELECT * FROM mm_returns_adjustments;";
        }
        
        ArrayList<ReturnAdjustmentModel> ramlist = new ArrayList();
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            ReturnAdjustmentModel returnsadj = new ReturnAdjustmentModel();
            returnsadj.setRamid(Integer.parseInt(map.get("idreturns_adjustments").toString()));
            returnsadj.setRamdte(Date.valueOf(map.get("rs_dte").toString()));
            returnsadj.setDesc(map.get("description").toString());
            returnsadj.setRefnum(map.get("refnum").toString());
            returnsadj.setPgistat(map.get("pgistat").toString());
            
            ramlist.add(returnsadj);
        }
        
        dbc.closeConnection();
        return ramlist.iterator();
    }
    
    public Iterator getReturnAdjustmentItems(int ramid, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "CALL `RETURNS_ADJ_GET_ITEMS`(?)";
        }
        else if(table == 2){
            query = "CALL `MM_RETURNS_ADJ_GET_ITEMS`(?)";
        }
        
        ArrayList<ReturnsModel> ramlist = new ArrayList();
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        st.setInt(1, ramid);
        
        Iterator rs = db.getQueryResultSet(st);
        
        int i = 0;
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            ReturnsModel returnsadj = new ReturnsModel(Integer.parseInt(map.get("return_id").toString()));
            returnsadj.setRetadjid(Integer.parseInt(map.get("retadj_id").toString()));
            returnsadj.setRetadjitemid(Integer.parseInt(map.get("idretadtems").toString()));
            returnsadj.setSoh(Integer.parseInt(map.get("stock_qty").toString()));
            returnsadj.setSku(map.get("sku").toString());
            returnsadj.setSkudesc(map.get("desc").toString());
            returnsadj.setMov(map.get("mov").toString());
            returnsadj.setRetwhs(map.get("retwhs").toString());
            returnsadj.setRetuom(map.get("retuom").toString());
            
            ramlist.add(returnsadj);
            i++;
        }
        System.out.println(i);
        
        dbc.closeConnection();
        return ramlist.iterator();
    }
    
    public void PostReturnAdjustment(Iterator items, int ramid, int type) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        String query1 = "";
        String query2 = "";
        String query3 = "";
        
        if(type == 1){
            query1 = "CALL `RETURNS_ADJ_PGI_STAT`(?)";
            query2 = "CALL `UPDATE_RETURNS_DEC`(?,?,?)";
            query3 = "CALL `UPDATE_RETURNS_INC`(?,?,?)";
        }
        else{
            query1 = "CALL `MM_RETURNS_ADJ_PGI_STAT`(?)";
            query2 = "CALL `MM_UPDATE_RETURNS_DEC`(?,?,?)";
            query3 = "CALL `MM_UPDATE_RETURNS_INC`(?,?,?)";
        }
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query1);
        PreparedStatement ps1 = dbc.getConnection().prepareStatement(query2);
        PreparedStatement ps2 = dbc.getConnection().prepareStatement(query3);
        
        ps.setInt(1, ramid);
        
        while(items.hasNext()){
            ReturnsModel item = (ReturnsModel) items.next();
            
            if(item.getMov().equals("INC")){
                
                ps2.setInt(1, item.getRetadjitemid());
                ps2.setInt(2, item.getIdreturns());
                ps2.setInt(3, item.getSoh());
                
                ps2.addBatch();
            }else if(item.getMov().equals("DEC")){
                
                ps1.setInt(1, item.getRetadjitemid());
                ps1.setInt(2, item.getIdreturns());
                ps1.setInt(3, item.getSoh());
                
                ps1.addBatch();
            }
            
            
        }
        
        ps.executeUpdate();
        ps1.executeBatch();
        ps2.executeBatch();
        
        dbc.closeConnection();
        
    }
    
}
