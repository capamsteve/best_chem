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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import models.SIModel;
import models.SItemsModel;

/**
 *
 * @author Steven
 */
public class SalesInvoiceQuery {
    
    public void addSalesInvoice(SIModel simod) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call SI_ADD(?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        
        st.setInt(1, simod.getCustomerid());
        st.setInt(2, simod.getSoidnvc());
        st.setString(3, simod.getRemarks());
        st.setInt(4, simod.getCby());
        st.setString(5, simod.getStatus());
        st.setString(6, simod.getTrcnme());
        st.setString(7, simod.getDrvnme());
        st.setString(8, simod.getPlateno());
        st.setDate(9, Date.valueOf(simod.getDte().toString()));
        st.setString(10, simod.getPrntstat());
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int siid = generatedKeys.getInt(1);
                this.addSalesInvoiceItems(simod.getSitems().iterator(), siid);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    public void addSalesInvoiceItems(Iterator items, int siid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call SI_ITEMS_ADD(?,?);");
        while(items.hasNext()){
            SItemsModel item = (SItemsModel) items.next();
            
            ps.setInt(1, siid);
            ps.setInt(2, item.getDrid());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public void editSalesInvoice(SIModel simod) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call `SI_EDIT`(?,?,?,?,?,?);");
        
        st.setString(1, simod.getRemarks());
        st.setString(2, simod.getTrcnme());
        st.setString(3, simod.getDrvnme());
        st.setString(4, simod.getPlateno());
        st.setDate(5, Date.valueOf(simod.getDte().toString()));
        st.setInt(6, simod.getSiid());
        
        
        st.executeUpdate();
    }
    
    public void deleteInvoiceItems(Iterator items) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `salesinvoiceitems` SET `status`='DELETED' WHERE `idsalesinvoiceitems`=?;");
        while(items.hasNext()){
            SItemsModel item = (SItemsModel) items.next();
            
            ps.setInt(1, item.getSitemid());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public Iterator getSalesInvoice(int soid) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM salesinvoices where soidinvc = ?;");
        
        st.setInt(1, soid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
    }
    
    public SIModel getSalesInvoiceModel(int id) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM salesinvoices where idsalesinvoices = ?;");
        
        st.setInt(1, id);
        
        SIModel model = null;
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        while(iterate.hasNext()){
            HashMap map = (HashMap) iterate.next();
            model = new SIModel();
            
            model.setSiid(Integer.parseInt(map.get("idsalesinvoices").toString()));
            model.setRemarks(map.get("remarks").toString());
            model.setStatus(map.get("status").toString());
            model.setTrcnme(map.get("truckername").toString());
            model.setPlateno(map.get("plateno").toString());
            model.setDrvnme(map.get("drivername").toString());
            model.setDte(Date.valueOf(map.get("sidte").toString()));
            
            
        }
        
        return model;
    }
    
    public Iterator getLineItems(int soid, int custid) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `GET_ALL_LINE_ITEMS`(?,?)");
        
        st.setInt(1, soid);
        st.setInt(2, custid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
    }
    
    public Iterator getSitems(int siid) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `SI_ITEMS_GET`(?)");
        
        st.setInt(1, siid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
    }
    
    public void Print(int siid, int biid, String rep) throws SQLException{
        
        
        if(rep.equals("Print")){
            DBQuery dbq = DBQuery.getInstance();
            DBConnect dbc = DBConnect.getInstance();

            PreparedStatement st = dbc.getConnection().prepareStatement("CALL `PRINT_ADD`(?,?,?,?)");

            st.setInt(1, siid);
            st.setInt(3, biid);
            st.setString(2, "PRINT");
            st.setDate(4, Date.valueOf(LocalDate.now()));

            st.execute();
            dbc.closeConnection();
        }else if(rep.equals("Re-print")){
            
            DBQuery dbq = DBQuery.getInstance();
            DBConnect dbc = DBConnect.getInstance();

            PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM print_invoice where billing_id = ? and `status` = 'PRINT';");

            st.setInt(1, biid);

            Iterator ir = dbq.getQueryResultSet(st);
            int prnt_id = 0;
            
            while(ir.hasNext()){
                HashMap map = (HashMap) ir.next();
                
                prnt_id = Integer.parseInt(map.get("idprint_invoice").toString());
            }
            

            PreparedStatement st2 = dbc.getConnection().prepareStatement("UPDATE `print_invoice` SET `status`='CANCELLED' WHERE `idprint_invoice`=?;");

            st2.setInt(1, prnt_id);
            
            st2.executeUpdate();
            
            PreparedStatement st3 = dbc.getConnection().prepareStatement("CALL `PRINT_ADD`(?,?,?,?)");

            st3.setInt(1, siid);
            st3.setInt(3, biid);
            st3.setString(2, "PRINT");
            st3.setDate(4, Date.valueOf(LocalDate.now()));

            st3.execute();
            dbc.closeConnection();
            
        }
        
    }
    
    public void changePrintStatus(int biid) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();

        PreparedStatement st = dbc.getConnection().prepareStatement("UPDATE `salesinvoices` SET `printstat`='Y' WHERE `idsalesinvoices`=?;");

        st.setInt(1, biid);
        
        st.executeUpdate();
    }
    
    public void changeStatus(int biid) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();

        PreparedStatement st = dbc.getConnection().prepareStatement("UPDATE `salesinvoices` SET `status`='complete' WHERE `idsalesinvoices`=?;");

        st.setInt(1, biid);
        
        st.executeUpdate();
    }
    
}
