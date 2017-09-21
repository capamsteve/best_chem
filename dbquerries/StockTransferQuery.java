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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import models.InventoryModel;
import models.StockTransferModel;

/**
 *
 * @author Steven
 */
public class StockTransferQuery {
    
    public void addStockTransfer(StockTransferModel stm) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call `ST_ADD`(?,?,?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        
        st.setDate(1, Date.valueOf(stm.getSt_dte().toString()));
        st.setDate(2, Date.valueOf(stm.getDr_dte().toString()));
        st.setString(3, stm.getSlto());
        st.setString(4, stm.getAttention());
        st.setString(5, stm.getRefnum());
        st.setString(6, stm.getAddress());
        st.setString(7, stm.getPonum());
        st.setString(8, stm.getTrucknme());
        st.setString(9, stm.getDrvrnme());
        st.setString(10, stm.getPlate());
        st.setString(11, stm.getStdesc());
        st.setString(12, stm.getRemarks());
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int stid = generatedKeys.getInt(1);
                this.addSTItems(stm.getItems().iterator(), stid, stm.getItems2().iterator());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void addSTItems(Iterator items, int stid, Iterator items2) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call `ST_ITEM_ADD`(?,?,?,?);");
        while(items.hasNext() && items2.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            InventoryModel item2 = (InventoryModel) items2.next();
            
            ps.setInt(1, stid);
            ps.setInt(2, item.getIdinventory());
            ps.setInt(3, item.getSoh());
            ps.setInt(4, item2.getIdinventory());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public Iterator getStockTransfers() throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<StockTransferModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.stocktransfers;");
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            StockTransferModel stm = new StockTransferModel();
            
            stm.setIdst(Integer.valueOf(map.get("idstocktransfers").toString()));
            stm.setSt_dte(Date.valueOf(map.get("st_dte").toString()));
            stm.setSlto(map.get("sldto").toString());
            stm.setStat(map.get("st_stat").toString());
            stm.setDr_dte(Date.valueOf(map.get("dr_dte").toString()));
            stm.setAttention(map.get("attent").toString());
            stm.setRefnum(map.get("refernum").toString());
            stm.setAddress(map.get("address").toString());
            stm.setPonum(map.get("ponum").toString());
            stm.setTrucknme(map.get("trucknme").toString());
            stm.setDrvrnme(map.get("drvrnme").toString());
            stm.setPlate(map.get("pltno").toString());
            stm.setStdesc(map.get("stdesc").toString());
            stm.setRemarks(map.get("remarks").toString());
            
            list.add(stm);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getSTItems(int id) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL ST_ITEMS_GET(?);");
        
        st.setInt(1, id);
        
        Iterator rs = dbq.getQueryResultSet2(st);
        
        return rs;
    }
    
    public void editST(StockTransferModel stm) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call `ST_EDIT`(?,?,?,?,?,?,?,?,?,?,?,?,?);");
        
        st.setDate(1, Date.valueOf(stm.getSt_dte().toString()));
        st.setDate(2, Date.valueOf(stm.getDr_dte().toString()));
        st.setString(3, stm.getSlto());
        st.setString(4, stm.getAttention());
        st.setString(5, stm.getRefnum());
        st.setString(6, stm.getAddress());
        st.setString(7, stm.getPonum());
        st.setString(8, stm.getTrucknme());
        st.setString(9, stm.getDrvrnme());
        st.setString(10, stm.getPlate());
        st.setString(11, stm.getStdesc());
        st.setString(12, stm.getRemarks());
        st.setInt(13, stm.getIdst());
        
        st.executeUpdate();
        
    }
    
    public void editSTItems(Iterator items) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `st_items` SET `invent_qty`=? WHERE `idstitems`=?;");
        while(items.hasNext() ){
            InventoryModel item = (InventoryModel) items.next();
            
            if(item.getStitem_id() != 0){
                ps.setInt(1, item.getSoh());
                ps.setInt(2, item.getStitem_id());

                ps.addBatch();
            }
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void deleteSTItems(Iterator items) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `st_items` SET `st_status`='DELETED' WHERE `idstitems`=?;");
        while(items.hasNext() ){
            InventoryModel item = (InventoryModel) items.next();
            
            if(item.getStitem_id() != 0){
                ps.setInt(1, item.getStitem_id());

                ps.addBatch();
            }
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();

    }
    
    public void changeSTStat(int id) throws SQLException{
        
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `stocktransfers` SET `st_stat`='COMPLETE' WHERE `idstocktransfers`=?;");
        ps.setInt(1, id);
        
        ps.executeUpdate();
        dbc.closeConnection();

        
    }
    
}
