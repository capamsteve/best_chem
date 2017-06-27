/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbquerries;

import dbconn.DBConnect;
import dbconn.DBQuery;
import delivery.DRItemViewModel;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import models.DRItemsModel;
import models.DRModel;
import models.SItemsModel;
import viewmodels.DRViewModel;

/**
 *
 * @author Steven
 */
public class DeliveryReceiptsQuery {
    
    public void addDeliveryReceipt(DRModel dr) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL DR_ADD (?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
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
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL DR_ADD (?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
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
    
    public Iterator getAllDeliveryReceipts(int soid) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * from deliveryorders where soid = ?;");
        
        st.setInt(1, soid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
    }
    
    public Iterator getDeliverReceipts(int soid, String status1, String status2) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * from deliveryorders where soid = ? and drpgi = ? and drstatus =?;");
        
        st.setInt(1, soid);
        st.setString(2, status1);
        st.setString(3, status2);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
        
    }
    
    public Iterator getDeliverReceiptsByInvoice(int soid, String status1, String status2, String status3) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * from deliveryorders where soid = ? and drpgi = ? and drstatus =? and invcstat = ?;");
        
        st.setInt(1, soid);
        st.setString(2, status1);
        st.setString(3, status2);
        st.setString(4, status3);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
        
    }
    
    public Iterator getSalesOrderItems(int soid, String stat) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `SALES_ORDERITEMS_GET` (?,?);");
        
        st.setInt(1, soid);
        st.setString(2, stat);
        
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
    
    public int getDRCount(int soid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT count(*) from deliveryorders where deliveryorders.soid = ? and drstatus = 'open';");
        
        st.setInt(1, soid);
        
        ResultSet rs = st.executeQuery();
        
        int pass = 0;
        
        try{
            
            if(rs.next()){
                pass = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        dbc.closeConnection();
        
        return pass;
    }
    
    public int getDRPGICount(int soid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT count(*) from deliveryorders where deliveryorders.soid = ? and deliveryorders.drpgi = 'N' and drstatus = 'open';");
        
        st.setInt(1, soid);
        
        ResultSet rs = st.executeQuery();
        
        int pass = 0;
        
        try{
            
            if(rs.next()){
                pass = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        dbc.closeConnection();
        
        return pass;
    }
    
    public int getDRPGICount1(int soid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT count(*) from deliveryorders where deliveryorders.soid = ? and deliveryorders.drpgi = 'Y' and drstatus = 'open';");
        
        st.setInt(1, soid);
        
        ResultSet rs = st.executeQuery();
        
        int pass = 0;
        
        try{
            
            if(rs.next()){
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
            HashMap temp = (HashMap) map.next();
            
            //System.out.println(temp.get("iddeliver").toString());
            
            drvm = new DRModel(Integer.valueOf(temp.get("iddeliveryorders").toString()));

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
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `DR_ITEM_GET`(?)");
        
        st.setInt(1, id);
        
        Iterator map = dbq.getQueryResultSet(st);
        
        return map;
    }
    
    public void changePGIStatusItems(Iterator ir) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL`DR_UPDATE_ITEMS`(?)");
        
        while(ir.hasNext()){
            DRItemViewModel item = (DRItemViewModel) ir.next();
            System.out.println(item.getDritemid());
            st.setInt(1, item.getDritemid());

            st.addBatch();
        }
        
        st.executeBatch();
    }
    
    public void changePGIStatusDR(int drid) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `DR_CHANGE_PGI`(?,?);");
        
        st.setInt(1, drid);
        st.setString(2, "Y");
        
        st.execute();
    }
    
    public void changeStatusDR(int drid, String stat) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `DR_CHANGE_STATUS`(?,?);");
        
        st.setInt(1, drid);
        st.setString(2, stat);
        
        st.execute();
    }
    
    public void changeInvcStatus(Iterator items, String stat) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("UPDATE `deliveryorders` SET `invcstat`=? WHERE `iddeliveryorders`=?;");
        
        while(items.hasNext()){
            SItemsModel drm = (SItemsModel) items.next();
            
            st.setInt(2, drm.getDrid());
            st.setString(1, stat);
            
            st.addBatch();
        }
        
        st.executeBatch();
    }
    
    public void editDR(DRModel dr) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `DR_EDIT`(?,?,?);");
        st.setDate(1, Date.valueOf(dr.getDrdate().toString()));
        st.setString(2, dr.getRemarks());
        st.setInt(3, dr.getDrid());
        
        st.executeUpdate();
    }
    
    public void editDRItems(Iterator items) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call DR_ITEM_EDIT(?,?);");
        while(items.hasNext()){
            DRItemsModel item = (DRItemsModel) items.next();
            System.out.println(item.getDrqty());
            ps.setInt(1, item.getDritemid());
            ps.setInt(2, item.getDrqty());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public Iterator getDRbyID(String soid) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<DRModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM deliveryorders where iddeliveryorders LIKE ? and drpgi = 'Y' and drstatus ='complete' and invcstat = 'N';");
        
        soid = "%" + soid + "%";
        
        st.setString(1, soid);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap temp = (HashMap) rs.next();
            DRModel drvm = new DRModel(Integer.valueOf(temp.get("iddeliveryorders").toString()));

            drvm.setDrdate(Date.valueOf(temp.get("drdate").toString()));
            drvm.setPgi(temp.get("drpgi").toString());
            drvm.setDrprint(temp.get("drprint").toString());
            drvm.setDrstatus(temp.get("drstatus").toString());
            drvm.setRemarks(temp.get("remarks").toString());
            list.add(drvm);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public void changeDRPrint(int drid) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("UPDATE `deliveryorders` SET `drprint`='Y' WHERE `iddeliveryorders`=?;");
        
        st.setInt(1, drid);
        
        st.execute();
        
    }
}
