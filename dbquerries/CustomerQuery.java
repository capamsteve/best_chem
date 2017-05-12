/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbquerries;

import dbconn.DBConnect;
import models.ContactModel;
import models.CustomerModel;
import dbconn.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Steven
 */
public class CustomerQuery {
    
    public Iterator getCustomers() throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<CustomerModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call CUSTOMER_GET_ALL();");
        Iterator rs = dbq.getQueryResultSet(st);
        
        /*
        *PASS THE LIST OF ITEMS ALREADY
        *
        */
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            CustomerModel cust = new CustomerModel();
            cust.setIdcustomer(String.valueOf(map.get("idcustomer")));
            //System.out.println(String.valueOf(map.get("idcustomer")));
            cust.setCompany((String) map.get("name"));
            //System.out.println((String) map.get("name"));
            cust.setTin((String) map.get("tin"));
            cust.setBusinessstyle((String) map.get("businessstyle"));
            cust.setAddress((String) map.get("address"));
            cust.setPaymentterm((String) map.get("paymentterm"));
            cust.setDiscount(Double.parseDouble(map.get("discount").toString()));
            cust.setUom((String) map.get("uom_iduom"));
            cust.setPostal_code(Integer.parseInt(map.get("postal_cd").toString()));
            cust.setVendor_code((String) map.get("vendor_cd"));
            cust.setVAT(Double.parseDouble(map.get("vat_prcnt").toString()));
            
            int auto = Integer.parseInt(map.get("auto_invice").toString());
            if(auto == 1){
                cust.setAuto_create(true);
            }
            else {
                cust.setAuto_create(false);
            }
            list.add(cust);
        }
        
        //dbc.closeConnection();
        return list.iterator();
    }
    
    public CustomerModel getCustomer(String customer) throws SQLException{
        
        CustomerModel cust;
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `CUSTOMER_GET`(?);");
        st.setString(1, customer);
        Iterator rs = dbq.getQueryResultSet(st);
        
        if(rs.hasNext()){
            HashMap map = (HashMap)rs.next();
            cust = new CustomerModel();
            cust.setIdcustomer(map.get("idcustomer").toString());
            cust.setCompany(map.get("name").toString());
            cust.setTin(map.get("tin").toString());
            cust.setBusinessstyle(map.get("businessstyle").toString());
            cust.setAddress(map.get("address").toString());
            cust.setPaymentterm(map.get("paymentterm").toString());
            cust.setDiscount(Double.parseDouble(map.get("discount").toString()));
            cust.setUom(map.get("uom_iduom").toString());
            cust.setPostal_code(Integer.parseInt(map.get("postal_cd").toString()));
            cust.setVendor_code(map.get("vendor_cd").toString());
            cust.setVAT(Double.parseDouble(map.get("vat_prcnt").toString()));
            
            int auto = Integer.parseInt(map.get("auto_invice").toString());
            if(auto == 1){
                cust.setAuto_create(true);
            }
            else {
                cust.setAuto_create(false);
            }
            
        }
        else{
            cust = null;
        }
        
        return cust;
    }
    
    public Iterator getContacts(int custid) throws SQLException{
        
        ArrayList<ContactModel> model = new ArrayList();
        ContactModel contact;
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `CONTACT_GET`(?);");
        st.setInt(1, custid);
        Iterator rs = dbq.getQueryResultSet(st);
        
        /*
        *PASS THE LIST OF ITEMS ALREADY
        *
        */
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            contact = new ContactModel((String)map.get("name"), (String)map.get("number"), (String) map.get("email"));
            contact.setContactid((int) map.get("idcontacts"));
            contact.setCustomerid(custid);
            
            model.add(contact);
        }
        
        dbc.closeConnection();
        return model.iterator();
    }
    
    public void addCustomer(CustomerModel customer) throws SQLException{
        
        int is_auto = 0;
        
        if(customer.isAuto_create()){
            is_auto = 1;
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `CUSTOMER_ADD` (?,?,?,?,?,?,?,?,?,?,?);");
        st.setString(1, customer.getCompany());
        st.setString(2, customer.getTin());
        st.setString(3, customer.getBusinessstyle());
        st.setString(4, customer.getAddress());
        st.setString(5, customer.getPaymentterm());
        st.setDouble(6, customer.getDiscount());
        st.setString(7, customer.getUom());
        st.setInt(8, customer.getPostal_code());
        st.setString(9, customer.getVendor_code());
        st.setDouble(10, customer.getVAT());
        st.setString(11, String.valueOf(is_auto));
        dbq.executeUpdateQuery(st);
    }
    
    public void addContact(Iterator contacts) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `CONTACT_ADD`(?,?,?,?)");
        
        while(contacts.hasNext()){
            ContactModel contact = (ContactModel) contacts.next();
            ps.setInt(1, contact.getCustomerid());
            ps.setString(2, contact.getContactName());
            ps.setString(3, contact.getContactNumber());
            ps.setString(4, contact.getContactEmail());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void editCustomer(CustomerModel customer) throws SQLException{
        int is_auto = 0;
        
        if(customer.isAuto_create()){
            is_auto = 1;
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `CUSTOMER_EDIT` (?,?,?,?,?,?,?,?,?,?,?,?);");
        st.setInt(1, Integer.valueOf(customer.getIdcustomer()));
        st.setString(2, customer.getCompany());
        st.setString(3, customer.getTin());
        st.setString(4, customer.getBusinessstyle());
        st.setString(5, customer.getAddress());
        st.setString(6, customer.getPaymentterm());
        st.setDouble(7, customer.getDiscount());
        st.setString(8, customer.getUom());
        st.setInt(9, customer.getPostal_code());
        st.setString(10, customer.getVendor_code());
        st.setDouble(11, customer.getVAT());
        st.setString(12, String.valueOf(is_auto));
        dbq.executeUpdateQuery(st);
    }
    
    public void editContact(Iterator contacts) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `CONTACT_EDIT`(?,?,?,?,?)");
        
        while(contacts.hasNext()){
            ContactModel contact = (ContactModel) contacts.next();
            if(contact.getContactid() != 0){
                ps.setInt(1, contact.getContactid());
                ps.setInt(2, contact.getCustomerid());
                ps.setString(3, contact.getContactName());
                ps.setString(4, contact.getContactNumber());
                ps.setString(5, contact.getContactEmail());

                ps.addBatch();
            }
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void deleteContacts(Iterator contacts) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `CONTACT_DELETE`(?)");
        
        while(contacts.hasNext()){
            ContactModel contact = (ContactModel) contacts.next();
            if(contact.getContactid() != 0){
                ps.setInt(1, contact.getContactid());
            
                ps.addBatch();
            }
            
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
}
