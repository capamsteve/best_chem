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
import java.util.HashMap;
import java.util.Iterator;
import models.DRItemsModel;
import models.DRModel;

/**
 *
 * @author Steven
 */
public class DeliveryReceiptsQuery {
    
    public void addDeliveryReceipt(DRModel dr) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL bestchem_db2.DR_ADD (?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        st.setDate(1, Date.valueOf(dr.getDrdate().toString()));
        st.setInt(2, dr.getCustid());
        st.setInt(3, dr.getSoid());
        st.setString(4, dr.getRemarks());
        st.setInt(5, dr.getCby());
        st.setString(6, dr.getDrprint());
        st.setString(7, dr.getDrstatus());
        st.setString(8, dr.getPgi());
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int salesid = generatedKeys.getInt(1);
                this.addDeliveryItemOrders(dr.getDritems().iterator(), salesid);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public int addDeliveryReceiptRet(DRModel dr) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL bestchem_db2.DR_ADD (?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        st.setDate(1, Date.valueOf(dr.getDrdate().toString()));
        st.setInt(2, dr.getCustid());
        st.setInt(3, dr.getSoid());
        st.setString(4, dr.getRemarks());
        st.setInt(5, dr.getCby());
        st.setString(6, dr.getDrprint());
        st.setString(7, dr.getDrstatus());
        st.setString(8, dr.getPgi());
        
        ResultSet generatedKeys = st.executeQuery();
        int salesid = 0;
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                salesid = generatedKeys.getInt(1);
                this.addDeliveryItemOrders(dr.getDritems().iterator(), salesid);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return salesid;
        
    }
    
    public void addDeliveryItemOrders(Iterator items, int drid) throws SQLException{
        
        //Add DeliveryItems
        //1.
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call DR_ITEM_ADD(?,?,?);");
        while(items.hasNext()){
            DRItemsModel item = (DRItemsModel) items.next();
            
            ps.setInt(1, drid);
            ps.setInt(2, item.getDrqty());
            ps.setInt(3, item.getSiditem());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void editDeliveryReceipt(){
        
    }
    
    public Iterator getDeliverReceipts(int soid) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * from deliveryorders where soid = ?");
        
        st.setInt(1, soid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
        
    }
    
    public Iterator getSalesOrderItems(int soid) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `SALES_ORDERITEMS_GET` (?);");
        
        st.setInt(1, soid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
    }
    
    public Iterator getSalesOrderItemsWRemaining(int soid) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `REMAINING_DR_TEMS` (?);");
        
        st.setInt(1, soid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
    }
    
    public Iterator getDeliveryOrderItemsIfNull(int drid, int soid) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `REMAINING_DR_TEMS` (?);");
        
        st.setInt(1, soid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
    }
    
    public Iterator getDeliverOrderItemsWRemaining(int drid, int soid) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `REMAINING_DR_TEMS` (?);");
        
        st.setInt(1, soid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
    }
    
    public int getDRCount(int soid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT count(*) from deliveryorders where deliveryorders.soid = ?;");
        
        st.setInt(1, soid);
        
        ResultSet rs = st.executeQuery();
        
        int pass = 0;
        
        try{
            
            if(rs.next()){
                System.out.println(rs.getInt(1));
                pass = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        dbc.closeConnection();
        
        return pass;
    }
    
    public DRModel getDR(int id) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        DRModel drvm = null;
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * from deliveryorders where iddeliveryorders= ?");
        
        st.setInt(1, id);
        
        Iterator map = dbq.getQueryResultSet(st);
        
        while(map.hasNext()){
            System.out.println("1");
            HashMap temp = (HashMap) map.next();
            
            //System.out.println(temp.get("iddeliver").toString());
            
            drvm = new DRModel();
            
            drvm.setDrid(Integer.valueOf(temp.get("iddeliveryorders").toString()));
            drvm.setDrdate(Date.valueOf(temp.get("drdate").toString()));
            drvm.setPgi(temp.get("drpgi").toString());
            drvm.setDrprint(temp.get("drprint").toString());
            drvm.setDrstatus(temp.get("drstatus").toString());
            drvm.setRemarks(temp.get("remarks").toString());
        }
        
        return drvm;
    }
    
    public Iterator getDRItems(int id) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        DRModel drvm = null;
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.deliveryorderitems where drorder = ?");
        
        st.setInt(1, id);
        
        Iterator map = dbq.getQueryResultSet(st);
        
        return map;
    }
    
}
