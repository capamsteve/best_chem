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
import java.util.Iterator;
import models.SOItemModel;
import models.SalesOrderModel;

/**
 *
 * @author Steven
 */
public class SalesOrderQuery {
    
    public void addSalesOrder(SalesOrderModel somodel) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call bestchem_db2.SalesOrder_ADD(?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        
        st.setString(1, somodel.getCustomerpo());
        st.setInt(2, somodel.getCustid());
        st.setDate(3, Date.valueOf(somodel.getSodate().toString()));
        st.setDate(4, Date.valueOf(somodel.getDeliverydate().toString()));
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int salesid = generatedKeys.getInt(1);
                this.addSalesOrderItems(somodel.getSoItems().iterator(), salesid);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void addSalesOrderItems(Iterator items, int salesid) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call bestchem_db2.SALESORDERITEMS_ADD(?,?,?,?,?,?,?,?);");
        while(items.hasNext()){
            SOItemModel item = (SOItemModel) items.next();
            
            ps.setInt(1, item.getQty());
            ps.setInt(2, salesid);
            ps.setInt(3, item.getIdinventory());
            ps.setDouble(4, item.getDiscnt());
            ps.setDouble(5, item.getUprice());
            ps.setDouble(6, item.getAmount());
            ps.setDouble(7, item.getVat());
            ps.setString(8, "OPEN");
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public Iterator getSalesOrders(int customer) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("SELECT idsalesorder, sodate, status, sodeliverydate, customerpo FROM bestchem_db2.salesorders where customer_idcustomer = ?;");
        ps.setInt(1, customer);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        return rs;
    }
    
    public Iterator getSalesOrderItems(int salesid, String status) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `SOITEMS_EDIT_GET`(?,?);");
        ps.setInt(1, salesid);
        ps.setString(2, status);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        return rs;
        
    }
    
    public Iterator getSILineItems(int salesid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `SOITEMS_LINE_ITEMS`(?);");
        ps.setInt(1, salesid);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        return rs;
        
    }
    
    public void cancelSalesOrder(int soid, int custid, String status1, String status2) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `SO_CHANGE_STATUS`(?,?,?)");
        ps.setInt(1, soid);
        ps.setInt(2, custid);
        ps.setString(3, status1);
        
        dbq.executeUpdateQuery(ps);
        
        ps = dbc.getConnection().prepareStatement("CALL `SO_CHANGE_ITEMS_STATUS`(?,?)");
        ps.setInt(1, soid);
        ps.setString(2, status2);
        
        dbq.executeUpdateQuery(ps);
        
        dbc.closeConnection();
    }
    
    public void changeStatSalesOrder(int soid, int custid, String status1) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `SO_CHANGE_STATUS`(?,?,?)");
        ps.setInt(1, soid);
        ps.setInt(2, custid);
        ps.setString(3, status1);
        
        dbq.executeUpdateQuery(ps);
        
        dbc.closeConnection();
    }
    
    public void editSalesOrder(SalesOrderModel somodel) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call `SALESORDER_EDIT`(?,?,?,?,?);");
        
        st.setString(1, somodel.getCustomerpo());
        st.setInt(2, somodel.getCustid());
        st.setDate(3, Date.valueOf(somodel.getSodate().toString()));
        st.setDate(4, Date.valueOf(somodel.getDeliverydate().toString()));
        st.setInt(5, somodel.getSoid());
        
        st.executeUpdate();
    }
    
    public void editSalesOrderItems(Iterator items, int soid) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call `SALESORDERITEMS_EDIT`(?,?,?,?,?,?,?,?);");
        while(items.hasNext()){
            SOItemModel item = (SOItemModel) items.next();
            
            if(item.getSoitemid() != null){
                ps.setInt(1, item.getQty());
                ps.setInt(2, soid);
                ps.setInt(3, item.getIdinventory());
                ps.setDouble(4, item.getDiscnt());
                ps.setDouble(5, item.getUprice());
                ps.setDouble(6, item.getAmount());
                ps.setDouble(7, item.getVat());
                ps.setInt(8, item.getSoitemid());

                ps.addBatch();
            }
            
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void deleteSalesOrdetItems(Iterator items, int soid) throws SQLException{
        
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call `SOITEM_DELETE`(?,?,?);");
        while(items.hasNext()){
            SOItemModel item = (SOItemModel) items.next();
            
            if(item.getSoitemid() != null){
                ps.setInt(1, item.getSoitemid1());
                ps.setInt(2, soid);
                ps.setInt(3, item.getIdinventory());

                ps.addBatch();
            }
            
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public Iterator getSOs(int custid, String soid) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.salesorders where customer_idcustomer = ? AND salesorders.idsalesorder LIKE ?;");
        
        soid = "%" + soid + "%";
        
        st.setInt(1, custid);
        st.setString(2, soid);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        dbc.closeConnection();
        return rs;
    }
}
