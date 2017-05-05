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
import models.SIModel;
import models.SItemsModel;

/**
 *
 * @author Steven
 */
public class SalesInvoiceQuery {
    
    public void addSalesInvoice(SIModel simod) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call bestchem_db2.SI_ADD(?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        
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
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call bestchem_db2.SI_ITEMS_ADD(?,?);");
        while(items.hasNext()){
            SItemsModel item = (SItemsModel) items.next();
            
            ps.setInt(1, siid);
            ps.setInt(2, item.getDrid());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public Iterator getSalesInvoice(int soid) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.salesinvoices where soidinvc = ?;");
        
        st.setInt(1, soid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
    }
    
    public SIModel getSalesInvoiceModel(int id){
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.salesinvoices where soidinvc = ?;");
        
        st.setInt(1, soid);
        
        Iterator iterate = dbq.getQueryResultSet(st);
        
        return iterate;
        
        return null;
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
    
}
