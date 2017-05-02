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
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call bestchem_db2.SALESORDERITEMS_ADD(?,?,?,?,?,?);");
        while(items.hasNext()){
            SOItemModel item = (SOItemModel) items.next();
            
            ps.setInt(1, item.getQty());
            ps.setInt(2, salesid);
            ps.setInt(3, item.getIdinventory());
            ps.setDouble(4, item.getDiscnt());
            ps.setDouble(5, item.getAmount());
            ps.setDouble(6, item.getVat());
            
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
    
    public Iterator getSalesOrderItems(int salesid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.salesorderitems where salesorder_idsalesorder = ?;");
        ps.setInt(1, salesid);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        return rs;
        
    }
    
}
