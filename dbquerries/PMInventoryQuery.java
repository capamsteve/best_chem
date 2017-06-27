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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import models.BOModel;
import models.InventoryModel;
import models.PricesModel;

/**
 *
 * @author Steven
 */
public class PMInventoryQuery {
    
    public void addBOM(BOModel item, int fg_invent) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `BOM_ADD`(?,?,?,?,?)");
            
        ps.setInt(1, fg_invent);
        ps.setInt(2, item.getBom_qty());
        ps.setString(3, item.getSku());
        ps.setString(4, item.getDescription());
        ps.setInt(5, item.getIdinventory());
        ps.execute();
        
        dbc.closeConnection();
        
    }
    
    public void editBOM(BOModel item) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `BOM_EDIT`(?,?)");
            
        ps.setInt(1, item.getBomid());
        ps.setInt(2, item.getBom_qty());
        ps.execute();
        
        dbc.closeConnection();
        
    }
    
    public void deleteBOM(BOModel item) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `bom` SET `bom_stat`='DELETED' WHERE `idbom`= ?;");
            
        ps.setInt(1, item.getBomid());
        ps.execute();
        
        dbc.closeConnection();
        
    }
    
    public Iterator getInventories(String sku) throws SQLException{
        
        String query = "SELECT * FROM pm_inventory where sku LIKE ? and skuom = 'PC' GROUP BY sku ORDER BY idinventory;";
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        sku = "%" + sku + "%";
        
        st.setString(1, sku);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUnits((int) map.get("units"));
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public void addPriceSupplier(PricesModel prm, int sup_id) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `PRICES_SUPPLIER_ADD`(?,?,?,?)");
            
        ps.setDouble(1, prm.getPoprice());
        ps.setInt(3, prm.getIdinventory());
        ps.setDate(2, Date.valueOf(prm.getEffdte().toString()));
        ps.setInt(4, sup_id);
        ps.execute();
        
        dbc.closeConnection();

    }
    
    public void editPriceSupplier(){
        
    }
    
    public Iterator getPricesSupplier(int supid) throws SQLException{
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<PricesModel> list = new ArrayList();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `MM_PRICES_GET`(?);");
        
        st.setInt(1, supid);
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            PricesModel model = new PricesModel();
            
            model.setIdprices(Integer.parseInt(map.get("idPrices").toString()));
            model.setIdinventory(Integer.parseInt(map.get("idinventory").toString()));
            model.setSku(map.get("sku").toString());
            model.setSkudesc(map.get("skudesc").toString());
            model.setPoprice(Double.parseDouble(map.get("poPrice").toString()));
            model.setSkuom(map.get("skuom").toString());
            model.setWhs(map.get("skuwh").toString());
            model.setEffdte(Date.valueOf(map.get("effectivedte").toString()));
            model.setPoprice1();
            
            list.add(model);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getBOM(int idinventory) throws SQLException{
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<BOModel> list = new ArrayList();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `BOM_GET_ITEMS`(?);");
        
        st.setInt(1, idinventory);
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            BOModel inventory = new BOModel(Integer.valueOf(map.get("pm_invent").toString()));
            inventory.setSku(map.get("pm_sku").toString());
            inventory.setDescription(map.get("pm_desc").toString());
            inventory.setBom_qty(Integer.valueOf(map.get("units_bom").toString()));
            inventory.setBomid(Integer.valueOf(map.get("idbom").toString()));
            inventory.setFg_inventory(Integer.valueOf(map.get("fg_inventorysku").toString()));
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getBOMbyId(int idinventory) throws SQLException{
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<BOModel> list = new ArrayList();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `BOM_GET_ITEMS`(?);");
        
        st.setInt(1, idinventory);
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            BOModel inventory = new BOModel(Integer.valueOf(map.get("idbom").toString()));
            inventory.setPm_inventory(Integer.valueOf(map.get("pm_invent").toString()));
            inventory.setSku(map.get("pm_sku").toString());
            inventory.setDescription(map.get("pm_desc").toString());
            inventory.setBom_qty(Integer.valueOf(map.get("units_bom").toString()));
            inventory.setBomid(Integer.valueOf(map.get("idbom").toString()));
            inventory.setFg_inventory(Integer.valueOf(map.get("fg_inventorysku").toString()));
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getInventories(int supplier) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL PO_PRICES_GET_BY_SUP(?);");
        
        st.setInt(1, supplier);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setPoprice(Double.valueOf(map.get("poPrice").toString()));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getInventories(String sku, int supplier) throws SQLException{
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL PO_PRICES_GET_BY_SUP_SKU(?,?);");
        
        sku = "%" + sku + "%";
        
        st.setInt(1, supplier);
        st.setString(2, sku);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setPoprice(Double.valueOf(map.get("poPrice").toString()));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
}
