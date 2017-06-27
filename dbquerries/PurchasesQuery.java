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
            query = "call PURCHASE_ADD(?,?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call MM_PURCHASE_ADD(?,?,?,?,?,?);";
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
            query = "call PURCHASE_ADD_ITEMS(?,?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call MM_PURCHASE_ADD_ITEMS(?,?,?,?,?,?);";
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
            query = "SELECT * FROM purchases where sup_id = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM mm_purchases where sup_id = ?;";
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
            query = "CALL `MM_PURCHASE_GET_ITEMS`(?);";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        ps.setInt(1, sup_id);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        return rs;
    }
    
    public Iterator getPurchases() throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("SELECT * FROM purchases INNER JOIN pgr on purchases.idpurchases = pgr.idpo_doc INNER JOIN suppliers on suppliers.idsuppliers = purchases.sup_id where stdoc = 'N' ORDER BY pgr_dtetme DESC, idpurchases DESC;");
        
        Iterator rs = dbq.getQueryResultSet(ps);
        ArrayList<PurchasesModel> models = new ArrayList();
        
        while(rs.hasNext()){
            HashMap temp = (HashMap) rs.next();
            PurchasesModel povm = new PurchasesModel();
            povm.setIdpurchases(Integer.valueOf(temp.get("idpurchases").toString()));
            povm.setPo_dte(Date.valueOf(temp.get("po_dte").toString()));
            povm.setPo_dr_dte(Date.valueOf(temp.get("po_dr_dte").toString()));
            povm.setPgistat(temp.get("pgistat").toString());
            povm.setStat(temp.get("status").toString());
            povm.setPrntstat(temp.get("prntstat").toString());
            povm.setSupcname(temp.get("sup_c_name").toString());
            povm.setSupplier(temp.get("supname").toString());
            povm.setAddress(temp.get("supaddress").toString());
            
            models.add(povm);
            
        }
        
        return models.iterator();   
    }
    
    public Iterator getPurchases(String poid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("SELECT * FROM purchases INNER JOIN pgr on purchases.idpurchases = pgr.idpo_doc INNER JOIN suppliers on suppliers.idsuppliers = purchases.sup_id where idpurchases LIKE ? ORDER BY pgr_dtetme DESC, idpurchases DESC;");
        
        poid = "%" + poid + "%";
        
        ps.setString(1, poid);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        ArrayList<PurchasesModel> models = new ArrayList();
        
        while(rs.hasNext()){
            HashMap temp = (HashMap) rs.next();
            PurchasesModel povm = new PurchasesModel();
            povm.setIdpurchases(Integer.valueOf(temp.get("idpurchases").toString()));
            povm.setPo_dte(Date.valueOf(temp.get("po_dte").toString()));
            povm.setPo_dr_dte(Date.valueOf(temp.get("po_dr_dte").toString()));
            povm.setPgistat(temp.get("pgistat").toString());
            povm.setStat(temp.get("status").toString());
            povm.setPrntstat(temp.get("prntstat").toString());
            povm.setSupcname(temp.get("sup_c_name").toString());
            povm.setSupplier(temp.get("supname").toString());
            povm.setAddress(temp.get("supaddress").toString());
            
            models.add(povm);
            
        }
        
        return models.iterator();
        
        
    }
    
    public void editPurchases(PurchasesModel pomod, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call `PURCHASE_EDIT`(?,?,?,?)";
        }
        else if(table == 2){
            query = "call `MM_PURCHASE_EDIT`(?,?,?,?)";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setString(1, pomod.getSupcname());
        st.setDate(2, Date.valueOf(pomod.getPo_dte().toString()));
        st.setDate(3, Date.valueOf(pomod.getPo_dr_dte().toString()));
        st.setInt(4, pomod.getIdpurchases());
        
        st.executeUpdate();
        
    }
    
    public void editPurchaseItems(Iterator items, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "UPDATE `purchaseitems` SET `po_qty`=?, `amount`=?, `vat_amount`=?, `unitprice`=? WHERE `idpurchaseitems`=?;";
        }
        else if(table == 2){
            query = "UPDATE `mm_purchaseitems` SET `po_qty`=?, `amount`=?, `vat_amount`=?, `unitprice`=? WHERE `idpurchaseitems`=?;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
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
    
    public void deletePurchaseItems(Iterator items, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "UPDATE `purchaseitems` SET `status`='DELETED' WHERE `idpurchaseitems`=?;";
        }
        else if(table == 2){
            query = "UPDATE `mm_purchaseitems` SET `status`='DELETED' WHERE `idpurchaseitems`=?;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
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
    
    public void PGItems(Iterator items, int pid, int table) throws SQLException{
        
        String query = "";
        String query1 = "";
        
        if(table == 1){
            query = "UPDATE `purchases` SET `status`='complete', `pgistat`='Y' WHERE `idpurchases`=?;";
            query1 = "UPDATE `purchaseitems` SET `pgistat`='Y', `actual_qty`=?, `batchnum`=? WHERE `idpurchaseitems`=?;";
        }
        else if(table == 2){
            query = "UPDATE `mm_purchases` SET `status`='complete', `pgistat`='Y' WHERE `idpurchases`=?;";
            query1 = "UPDATE `mm_purchaseitems` SET `pgistat`='Y', `actual_qty`=?, `batchnum`=? WHERE `idpurchaseitems`=?;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps2 = dbc.getConnection().prepareStatement(query);
        
        ps2.setInt(1, pid);
        ps2.executeUpdate();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query1);
        while(items.hasNext()){
            PurchaseItemModel item = (PurchaseItemModel) items.next();
            
            ps.setInt(3, item.getIdpurchaseitem());
            ps.setInt(1, item.getActualqty());
            ps.setString(2, item.getBatchnum());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public void printed(int pid, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "UPDATE `purchases` SET `prntstat`='Y' WHERE `idpurchases`=?;";
        }
        else if(table == 2){
            query = "UPDATE `mm_purchases` SET `prntstat`='Y' WHERE `idpurchases`=?;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps2 = dbc.getConnection().prepareStatement(query);
        
        ps2.setInt(1, pid);
        ps2.executeUpdate();
        
        dbc.closeConnection();
    }
    
    public void GeneratePGR(int po_doc, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "CALL `PRG_ADD`(?)";
        }
        else if(table == 2){
            query = "CALL `MM_PRG_ADD`(?)";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setInt(1, po_doc);
        
        st.execute();
        
    }
    
}
