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
import models.ContactModel;
import models.SupplierContactModel;
import models.SupplierModel;

/**
 *
 * @author Steven
 */
public class SupplierQuery {
    
    public void addSupplier(SupplierModel sup) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `SUPPLIER_ADD` (?,?,?,?,?);");
        
        st.setString(1, sup.getSupname());
        st.setString(2, sup.getSuptin());
        st.setString(3, sup.getSupaddress());
        st.setString(4, sup.getSupbustyp());
        st.setString(5, sup.getSuppymttrm());
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int supid = generatedKeys.getInt(1);
                this.addContactSupplier(sup.getScm().iterator(), supid);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void editSupplier(SupplierModel sup){
        
    }
    
    public void addContactSupplier(Iterator supcon, int supid) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `SUP_CONTACT_ADD`(?,?,?,?)");
        
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
    
    public void editContactSupplier(SupplierContactModel supcon){
        
    }
    
    public Iterator getAllSuppliers() throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        ArrayList<SupplierModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.suppliers;");
        
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
            
            list.add(supmod);
        }
        
        return list.iterator();
    }
    
    public SupplierModel getSupplier(int id) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.suppliers where idsuppliers = ?;");
        
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
            
        }
        
        supmod.setScm(this.getContacts(id));
        
        return supmod;
        
    }
    
    private ArrayList<SupplierContactModel> getContacts(int id) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        ArrayList<SupplierContactModel> listahan = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.supcontacts where sup_id = ?;");
        
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
