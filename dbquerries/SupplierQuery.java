/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbquerries;

import dbconn.DBConnect;
import dbconn.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import models.SupplierContactModel;
import models.SupplierModel;

/**
 *
 * @author Steven
 * 
 * ALSO USED WITH 
 * 
 * SM
 * 
 * MM
 */
public class SupplierQuery {
    
    public void addSupplier(SupplierModel sup, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "CALL `SUPPLIER_ADD` (?,?,?,?,?,?);";
        }
        else if(table == 2){
            query = "CALL `MM_SUPPLIER_ADD` (?,?,?,?,?,?);";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setString(1, sup.getSupname());
        st.setString(2, sup.getSuptin());
        st.setString(3, sup.getSupaddress());
        st.setString(4, sup.getSupbustyp());
        st.setString(5, sup.getSuppymttrm());
        st.setString(6, sup.getPostal());
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int supid = generatedKeys.getInt(1);
                
                if(!sup.getScm().isEmpty()){
                    this.addContactSupplier(sup.getScm().iterator(), supid, table);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void editSupplier(SupplierModel sup, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "CALL `SUPPLIER_EDIT` (?,?,?,?,?,?,?);";
        }
        else if(table == 2){
            query = "CALL `MM_SUPPLIER_EDIT` (?,?,?,?,?,?,?);";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setString(1, sup.getSupname());
        st.setString(2, sup.getSuptin());
        st.setString(3, sup.getSupaddress());
        st.setString(4, sup.getSupbustyp());
        st.setString(5, sup.getSuppymttrm());
        st.setString(6, sup.getPostal());
        st.setInt(7, sup.getSupid());
        
        st.executeUpdate();
    }
    
    public void editContactSupplier(Iterator supcon, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "CALL `SUP_CONTACT_EDIT`(?,?,?,?)";
        }
        else if(table == 2){
            query = "CALL `MM_SUP_CONTACT_EDIT`(?,?,?,?)";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        
        while(supcon.hasNext()){
            SupplierContactModel contact = (SupplierContactModel) supcon.next();
            if(contact.getCid() != 0){
                ps.setString(1, contact.getSupcname());
                ps.setString(2, contact.getContact());
                ps.setString(3, contact.getSupemail());
                ps.setInt(4, contact.getCid());

                ps.addBatch();
            }
            
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public void deleteContacts(Iterator supcon, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "CALL `SUP_CONTACT_DEL`(?)";
        }
        else if(table == 2){
            query = "CALL `MM_SUP_CONTACT_DEL`(?)";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        
        while(supcon.hasNext()){
            SupplierContactModel contact = (SupplierContactModel) supcon.next();
            if(contact.getCid() != 0){
                ps.setInt(1, contact.getCid());
            
                ps.addBatch();
            }
            
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public void addContactSupplier(Iterator supcon, int supid, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "CALL `SUP_CONTACT_ADD`(?,?,?,?)";
        }
        else if(table == 2){
            query = "CALL `MM_SUP_CONTACT_ADD`(?,?,?,?)";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        
        while(supcon.hasNext()){
            SupplierContactModel contact = (SupplierContactModel) supcon.next();
            ps.setString(1, contact.getSupcname());
            ps.setString(2, contact.getContact());
            ps.setString(3, contact.getSupemail());
            ps.setInt(4, supid);
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    
    
    public Iterator getAllSuppliers(int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM suppliers;";
        }
        else if(table == 2){
            query = "SELECT * FROM mm_suppliers;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        System.out.println("TABLE: " + table);
        ArrayList<SupplierModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        Iterator ir = dbq.getQueryResultSet(st);
        
        while(ir.hasNext()){
            HashMap map = (HashMap) ir.next();
            
            SupplierModel supmod = new SupplierModel();
            
            supmod.setSupid(Integer.valueOf(map.get("idsuppliers").toString()));
            supmod.setSupname(map.get("supname").toString());
            supmod.setSupaddress(map.get("supaddress").toString());
            supmod.setSuptin(map.get("suptin").toString());
            supmod.setSupbustyp(map.get("supbusines").toString());
            supmod.setSuppymttrm(map.get("suppayment").toString());
            supmod.setPostal(map.get("postal").toString());
            
            list.add(supmod);
        }
        
        return list.iterator();
    }
    
    public SupplierModel getSupplier(int id, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM suppliers where idsuppliers = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM mm_suppliers where idsuppliers = ?;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setInt(1, id);
        
        Iterator ir = dbq.getQueryResultSet(st);
        
        SupplierModel supmod = new SupplierModel();
        
        while(ir.hasNext()){
            
            HashMap map = (HashMap) ir.next();
            supmod.setSupid(Integer.valueOf(map.get("idsuppliers").toString()));
            supmod.setSupname(map.get("supname").toString());
            supmod.setSupaddress(map.get("supaddress").toString());
            supmod.setSuptin(map.get("suptin").toString());
            supmod.setSupbustyp(map.get("supbusines").toString());
            supmod.setSuppymttrm(map.get("suppayment").toString());
            supmod.setPostal(map.get("postal").toString());
            
        }
        
        supmod.setScm(this.getContacts(id, table));
        
        return supmod;
        
    }
    
    public ArrayList<SupplierContactModel> getContacts(int id, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM supcontacts where sup_id = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM mm_supcontacts where sup_id = ?;";
        }
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        ArrayList<SupplierContactModel> listahan = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setInt(1, id);
        
        Iterator ir = dbq.getQueryResultSet(st);
        
        while(ir.hasNext()){
            HashMap map = (HashMap) ir.next();
            
            SupplierContactModel supcon = new SupplierContactModel();
            
            supcon.setCid(Integer.valueOf(map.get("idsupcontacts").toString()));
            supcon.setSupcname(map.get("supcname").toString());
            supcon.setContact(map.get("supccontact").toString());
            supcon.setSupemail(map.get("supcemail").toString());
            
            listahan.add(supcon);
        }
        
        return listahan;
    }
    
}
