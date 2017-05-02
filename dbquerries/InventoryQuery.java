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
import models.InventoryAdjustmentModel;
import models.PricesModel;

/**
 *
 * @author Steven
 */
public class InventoryQuery {
    
    public Iterator getInventories(int table) throws SQLException{
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        System.out.println(table);
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
    
    public void addInventory(InventoryModel im, int table) throws SQLException{
        System.out.println(table);
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
    
    public Iterator getInventories(String sku, int table) throws SQLException{
        System.out.println(table);
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
    
    public InventoryModel getInventoryWPrice(int id, int table) throws SQLException{
        System.out.println(table);
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
    
    public InventoryModel getInventory(int id, int table) throws SQLException{
        System.out.println(table);
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.inventory where idinventory = ?");
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
            im.setWh(map.get("skuwh").toString());
            
        }
        return im;
    }
    
    public void editInventory(int table){
        
    }
    
    public void addInventoryAdjustment(InventoryAdjustmentModel iam, int table) throws SQLException{
        System.out.println(table);
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL INVENTORY_ADJ_ADD(?,?,?,?)");
        
        st.setDate(1, Date.valueOf(iam.getIam_dte().toString()));
        st.setString(2, iam.getRefnum());
        st.setString(3, iam.getDesc());
        st.setString(4, "N");
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int iamid = generatedKeys.getInt(1);
                this.addInventoryAdjustmentItems(iam.getItemslist().iterator(), iamid, table);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void addInventoryAdjustmentItems(Iterator items, int iam_id, int table) throws SQLException{
        System.out.println(table);
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("call `INVENTORY_ADJ_ADD_ITEMS`(?,?,?,?,?);");
        while(items.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            
            ps.setInt(1, item.getIdinventory());
            ps.setInt(2, item.getSoh());
            ps.setString(3, item.getMov());
            ps.setString(4, "N");
            ps.setInt(5, iam_id);
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public Iterator getAllPrices(int table) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("call bestchem_db2.GET_PRICE_INVENTORY();");
        
        Iterator rs = dbq.getQueryResultSet(st);

        return rs;
    }
    
    public void addPrice(PricesModel price, int table) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call PRICES_ADD(?,?,?,?)");
        
        st.setDouble(1, price.getSellingprice());
        st.setDouble(2, price.getPoprice());
        st.setDate(3, Date.valueOf(price.getEffdte().toString()));
        st.setInt(4, price.getIdinventory());
        dbq.executeUpdateQuery(st); 
    }
}
