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
        
        String query = "";
        
        if(table == 1){
            query = "call bestchem_db2.INVENTORY_GET_ALL()";
        }
        else if(table == 2){
            query = "call bestchem_db2.MM_INVENTORY_GET_ALL()";
        }
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
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
        
        String query = "";
        
        if(table == 1){
            query = "call bestchem_db2.INVENTORY_PRICE_ADD(?,?,?,?,?,?,'0.0','0.0',?);";
        }
        else if(table == 2){
            query = "call bestchem_db2.MM_INVENTORY_PRICE_ADD(?,?,?,?,?,?,'0.0','0.0',?);";;
        }
        System.out.println(query);
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
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
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM bestchem_db2.inventory where sku LIKE ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM bestchem_db2.mm_inventory where sku LIKE ?;";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
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
        
        String query = "";
        
        if(table == 1){
            query = "CALL INVENTORY_PRICE_GET(?)";
        }
        else if(table == 2){
            query = "CALL MM_INVENTORY_PRICE_GET(?)";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
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
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM bestchem_db2.inventory where idinventory = ?";
        }
        else if(table == 2){
            query = "SELECT * FROM bestchem_db2.mm_inventory where idinventory = ?";
        }
        System.out.println(table);
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
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
    
    public void editInventory(InventoryModel model, int table) throws SQLException{
        
        System.out.println(table);
        
        String query = "";
        
        if(table == 1){
            query = "call bestchem_db2.INVENTORY_EDIT(?,?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call bestchem_db2.MM_INVENTORY_EDIT(?,?,?,?,?,?);";;
        }
        System.out.println(query);
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        st.setString(1, model.getSku());
        st.setString(2, model.getDescription());
        st.setString(3, model.getUom());
        st.setString(4, model.getWh());
        st.setInt(5, model.getCsl());
        st.setInt(6, model.getIdinventory());
        
        dbq.executeUpdateQuery(st);
        
        dbc.closeConnection();
        
    }
    
    public void addInventoryAdjustment(InventoryAdjustmentModel iam, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "CALL INVENTORY_ADJ_ADD(?,?,?,?)";
        }
        else if(table == 2){
            query = "CALL MM_INVENTORY_ADJ_ADD(?,?,?,?)";
        }
        System.out.println(table);
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
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
        
        String query = "";
        
        if(table == 1){
            query = "call `INVENTORY_ADJ_ADD_ITEMS`(?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call `MM_INVENTORY_ADJ_ADD_ITEMS`(?,?,?,?,?);";
        }
        System.out.println(table);
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
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
    
    public Iterator getInventoryAdjustments(int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM bestchem_db2.inventory_adjustments;";
        }
        else if(table == 2){
            query = "SELECT * FROM bestchem_db2.mm_inventory_adjustments;";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        ArrayList<InventoryAdjustmentModel> iams = new ArrayList();
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryAdjustmentModel iam = new InventoryAdjustmentModel();
            
            iam.setIamid(Integer.parseInt(map.get("idadjustments").toString()));
            iam.setIam_dte(Date.valueOf(map.get("iadte").toString()));
            iam.setDesc(map.get("description").toString());
            iam.setRefnum(map.get("refnum").toString());
            iam.setPgistat(map.get("pgistat").toString());
            
            iams.add(iam);
        }

        return iams.iterator();
        
    }
    
    public Iterator getAllPrices(int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call bestchem_db2.GET_PRICE_INVENTORY();";
        }
        else if(table == 2){
            query = "call bestchem_db2.MM_GET_PRICE_INVENTORY();";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        Iterator rs = dbq.getQueryResultSet(st);

        return rs;
    }
    
    public void addPrice(PricesModel price, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call PRICES_ADD(?,?,?,?)";
        }
        else if(table == 2){
            query = "call MM_PRICES_ADD(?,?,?,?)";
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setDouble(1, price.getSellingprice());
        st.setDouble(2, price.getPoprice());
        st.setDate(3, Date.valueOf(price.getEffdte().toString()));
        st.setInt(4, price.getIdinventory());
        dbq.executeUpdateQuery(st); 
    }
    
    public void editPrice(PricesModel price, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "call PRICES_EDIT(?,?,?,?,?)";
        }
        else if(table == 2){
            query = "call MM_PRICES_EDIT(?,?,?,?,?)";
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setInt(1, price.getIdprices());
        st.setDouble(2, price.getSellingprice());
        st.setDouble(3, price.getPoprice());
        st.setDate(4, Date.valueOf(price.getEffdte().toString()));
        st.setInt(5, price.getIdinventory());
        dbq.executeUpdateQuery(st); 
    }
}
