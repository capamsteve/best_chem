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
import models.InventoryModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import models.PricesModel;

/**
 *
 * @author Steven
 */
public class InventoryQuery {
    
    public Iterator getInventories() throws SQLException{
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call bestchem_db2.INVENTORY_GET_ALL()");
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel();
            inventory.setIdinventory((int) map.get("idinventory"));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public void addInventory(InventoryModel im) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call bestchem_db2.INVENTORY_PRICE_ADD(?,?,?,?,?,?,'0.0','0.0',?);");
        st.setString(1, im.getSku());
        st.setString(2, im.getDescription());
        st.setString(3, im.getUom());
        st.setString(4, im.getWh());
        st.setInt(5, im.getSoh());
        st.setInt(6, im.getCsl());
        st.setDate(7, Date.valueOf(LocalDate.now()));
        
        dbq.executeUpdateQuery(st);
        
        dbc.closeConnection();
        
    }
    
    public Iterator getInventories(String sku) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.inventory where sku LIKE ?;");
        
        sku = "%" + sku + "%";
        
        st.setString(1, sku);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel();
            inventory.setIdinventory((int) map.get("idinventory"));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public InventoryModel getInventoryWPrice(int id) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL INVENTORY_PRICE_GET(?)");
        st.setInt(1, id);
        
        InventoryModel im;
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        im = new InventoryModel();
        if(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            im.setIdinventory((int) map.get("idinventory"));
            im.setSku((String) map.get("sku"));
            im.setDescription((String) map.get("skudesc"));
            im.setUom((String) map.get("skuom"));
            im.setSellprice(Double.valueOf(map.get("sellingPrice").toString()));
            im.setPoprice(Double.valueOf(map.get("poPrice").toString()));
            
        }
        return im;
    }
    
    
    
    public void editInventory(){
        
    }
    
    public Iterator getAllPrices() throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("call bestchem_db2.GET_PRICE_INVENTORY();");
        
        Iterator rs = dbq.getQueryResultSet(st);

        return rs;
    }
    
    public void addPrice(PricesModel price) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call PRICES_ADD(?,?,?,?)");
        
        st.setDouble(1, price.getSellingprice());
        st.setDouble(2, price.getPoprice());
        st.setDate(3, Date.valueOf(price.getEffdte().toString()));
        st.setInt(4, price.getIdinventory());
        dbq.executeUpdateQuery(st); 
//                + price.getSellingprice() + "', '"
//                + price.getPoprice() + "', '"
//                + price.getEffdte() + "', '"
//                + price.getIdinventory() + "');");
//        
    }
}
