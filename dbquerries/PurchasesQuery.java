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
        
        String query = "";
        
        if(table == 1){
            query = "call bestchem_db2.PURCHASE_ADD(?,?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call bestchem_db2.MM_PURCHASE_ADD(?,?,?,?,?,?);";
        }
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        
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
        
        String query = "";
        
        if(table == 1){
            query = "call bestchem_db2.PURCHASE_ADD_ITEMS(?,?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call bestchem_db2.MM_PURCHASE_ADD_ITEMS(?,?,?,?,?);";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        while(items.hasNext()){
            PurchaseItemModel item = (PurchaseItemModel) items.next();
            
            ps.setInt(1, item.getIdinventory());
            ps.setInt(2, item.getQty());
            ps.setDouble(3, item.getUprice());
            ps.setDouble(4, item.getAmount());
            ps.setDouble(5, item.getVat());
            ps.setInt(6, poid);
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public Iterator getPurchaseOrder(int sup_id, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM bestchem_db2.purchases where sup_id = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM bestchem_db2.mm_purchases where sup_id = ?;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        ps.setInt(1, sup_id);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        return rs;
    }
    
    public Iterator getPurchaseOrderItems(int sup_id, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "CALL `PURCHASE_GET_ITEMS`(?);";
        }
        else if(table == 2){
            query = "SELECT * FROM bestchem_db2.mm_purchaseitems where sup_id = ?;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        ps.setInt(1, sup_id);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        return rs;
    }
    
    public void editPurchases(PurchasesModel pomod) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call `PURCHASE_EDIT`(?,?,?,?)");
        
        st.setString(1, pomod.getSupcname());
        st.setDate(2, Date.valueOf(pomod.getPo_dte().toString()));
        st.setDate(3, Date.valueOf(pomod.getPo_dr_dte().toString()));
        st.setInt(4, pomod.getIdpurchases());
        
        st.executeUpdate();
        
    }
    
    public void editPurchaseItems(Iterator items) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `bestchem_db2`.`purchaseitems` SET `po_qty`=?, `amount`=?, `vat_amount`=?, `unitprice`=? WHERE `idpurchaseitems`=?;");
        while(items.hasNext()){
            PurchaseItemModel item = (PurchaseItemModel) items.next();
            
            if(item.getIdpurchaseitem() != 0){
                ps.setInt(1, item.getQty());
                ps.setDouble(2, item.getAmount());
                ps.setDouble(3, item.getVat());
                ps.setDouble(4, item.getUprice());
                ps.setInt(5, item.getIdpurchaseitem());

                ps.addBatch();
            }
            
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void deletePurchaseItems(Iterator items) throws SQLException{

        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `bestchem_db2`.`purchaseitems` SET `status`='DELETED' WHERE `idpurchaseitems`=?;");
        while(items.hasNext()){
            PurchaseItemModel item = (PurchaseItemModel) items.next();
            
            if(item.getIdpurchaseitem() != 0){
                ps.setInt(1, item.getIdpurchaseitem());
            
                ps.addBatch();
            }
            
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public void PGItems(Iterator items, int pid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps2 = dbc.getConnection().prepareStatement("UPDATE `bestchem_db2`.`purchases` SET `pgistat`='Y' WHERE `idpurchases`=?;");
        
        ps2.setInt(1, pid);
        ps2.executeUpdate();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `bestchem_db2`.`purchaseitems` SET `pgistat`='Y' WHERE `idpurchaseitems`=?;");
        while(items.hasNext()){
            PurchaseItemModel item = (PurchaseItemModel) items.next();
            
            ps.setInt(1, item.getIdpurchaseitem());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
}
