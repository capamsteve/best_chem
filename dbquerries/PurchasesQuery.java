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
import models.PurchaseItemModel;
import models.PurchasesModel;

/**
 *
 * @author Steven
 */
public class PurchasesQuery {
    
    public void addPurchases(PurchasesModel pomod, int table) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call bestchem_db2.PURCHASE_ADD(?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        
        st.setInt(1, pomod.getSup_id());
        st.setString(2, pomod.getSupcname());
        st.setDate(3, Date.valueOf(pomod.getPo_dte().toString()));
        st.setDate(4, Date.valueOf(pomod.getPo_dr_dte().toString()));
        st.setInt(5, pomod.getCbyid());
        st.setString(6, "N");
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int poid = generatedKeys.getInt(1);
                this.addPurchaseItems(pomod.getPurchases().iterator(), poid, table);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void addPurchaseItems(Iterator items, int poid, int table) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call bestchem_db2.PURCHASE_ADD_ITEMS(?,?,?,?,?);");
        while(items.hasNext()){
            PurchaseItemModel item = (PurchaseItemModel) items.next();
            
            ps.setInt(1, item.getIdinventory());
            ps.setInt(2, item.getQty());
            ps.setDouble(3, item.getAmount());
            ps.setDouble(4, item.getVat());
            ps.setInt(5, poid);
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public Iterator getPurchaseOrder(int sup_id, int table) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.purchases where sup_id = ?;");
        ps.setInt(1, sup_id);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        return rs;
    }
    
}
