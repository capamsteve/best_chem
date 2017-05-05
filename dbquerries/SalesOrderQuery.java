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
import models.DRItemsModel;
import models.DRModel;
import models.SIModel;
import models.SItemsModel;
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
    
    public void autoAddDRandInvoice(SalesOrderModel somodel, int cby) throws SQLException{
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
                
                DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();
                SalesInvoiceQuery siq = new SalesInvoiceQuery();
                
                DRModel drmod = new DRModel();
                drmod.setCby(cby);
                drmod.setCustid(somodel.getCustid());
                drmod.setDrdate(somodel.getDeliverydate());
                drmod.setDrprint("N");
                drmod.setDrstatus("open");
                drmod.setPgi("N");
                drmod.setRemarks("N/A");
                drmod.setSoid(salesid);
                
                SIModel sim = new SIModel();
                
                sim.setCby(cby);
                sim.setCustomerid(somodel.getCustid());
                sim.setDrvnme("N/A");
                sim.setTrcnme("N/A");
                sim.setPlateno("N/A");
                sim.setDte(somodel.getSodate());
                sim.setRemarks("N/A");
                sim.setSoidnvc(salesid);
                sim.setPrntstat("N");
                sim.setStatus("open");
                
                this.addSalesOrderItems(somodel.getSoItems().iterator(), salesid);
                
                Iterator ir = this.getSalesOrderItems(salesid, 2);
                ArrayList<DRItemsModel> items = new ArrayList();
                
                while(ir.hasNext()){
                    HashMap map = (HashMap) ir.next();
                    
                    DRItemsModel item = new DRItemsModel();
                    item.setDrqty(Integer.parseInt(map.get("ordrqty").toString()));
                    item.setSiditem(Integer.parseInt(map.get("idsalesorderitem").toString()));
                    
                    items.add(item);
                }
                
                drmod.setDritems(items);
                
                int drid = drq.addDeliveryReceiptRet(drmod);
                
                SItemsModel sitem = new SItemsModel();
                sitem.setDrid(drid);
                
                ArrayList<SItemsModel> siitems = new ArrayList();
                
                siitems.add(sitem);
                
                sim.setSitems(siitems);
                
                siq.addSalesInvoice(sim);
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void addSalesOrderItems(Iterator items, int salesid) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call bestchem_db2.SALESORDERITEMS_ADD(?,?,?,?,?,?,?);");
        while(items.hasNext()){
            SOItemModel item = (SOItemModel) items.next();
            
            ps.setInt(1, item.getQty());
            ps.setInt(2, salesid);
            ps.setInt(3, item.getIdinventory());
            ps.setDouble(4, item.getDiscnt());
            ps.setDouble(5, item.getAmount());
            ps.setDouble(6, item.getVat());
            ps.setString(7, "OPEN");
            
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
    
    public Iterator getSalesOrderItems(int salesid, int type) throws SQLException{
        
        String query = "";
        
        if(type == 1){
            query = "CALL `SOITEMS_EDIT_GET`(?);";
        }
        else if(type == 2){
            query = "SELECT * FROM bestchem_db2.salesorderitems where salesorder_idsalesorder = ?;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
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
    
    
    
}
