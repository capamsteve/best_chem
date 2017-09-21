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
import models.MGIModel;
import models.PricesModel;

/**
 *
 * @author Steven
 */
public class InventoryQuery {
    
    public Iterator getInventories(int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call INVENTORY_GET_ALL()";
        }
        else if(table == 2){
            query = "call MM_INVENTORY_GET_ALL()";
        }
        
        DBQuery db = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        Iterator rs = db.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku(map.get("sku").toString());
            inventory.setDescription(map.get("skudesc").toString());
            inventory.setUom(map.get("skuom").toString());
            inventory.setWh(map.get("skuwh").toString());
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            inventory.setInvent_type(map.get("inv_type").toString());
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public void addInventory(InventoryModel im, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call INVENTORY_PRICE_ADD(?,?,?,?,?,?,'0.0','0.0',?,?,?);";
        }
        else if(table == 2){
            query = "call MM_INVENTORY_PRICE_ADD(?,?,?,?,?,?,'0.0','0.0',?,?,?);";
        }
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
        st.setInt(8, im.getUnits());
        st.setString(9, im.getInvent_type());
        
        dbq.executeUpdateQuery(st);
        
        dbc.closeConnection();
        
    }
    
    public boolean checkInventory(InventoryModel im, int table) throws SQLException{
        
        boolean check = false;
        String query = "";
        
        if(table == 1){
            query = "SELECT count(*) as cnt from inventory where sku = ? and skudesc = ? and skuom = ? and skuwh = ?";
        }
        else if(table == 2){
            query = "SELECT count(*) as cnt from pm_inventory where sku = ? and skudesc = ? and skuom = ? and skuwh = ?";
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        st.setString(1, im.getSku());
        st.setString(2, im.getDescription());
        st.setString(3, im.getUom());
        st.setString(4, im.getWh());
        
        Iterator rs = dbq.getQueryResultSet2(st);
        
        int num = 0;
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            num = Integer.valueOf(map.get("cnt").toString());
        }
        
        if(num == 0){
            check = false;
        }else{
            check = true;
        }
        
        return check;
    }

    public Iterator getInventories(String sku, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where sku LIKE ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where sku LIKE ?";
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
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            inventory.setInvent_type(map.get("inv_type").toString());
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getInventoriesByDesc1(String sku, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where skudesc LIKE ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where skudesc LIKE ?";
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
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            inventory.setInvent_type(map.get("inv_type").toString());
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getInventories(String sku, String uom, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where sku LIKE ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where sku LIKE ? and skuom = ?";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        sku = "%" + sku + "%";
        
        st.setString(1, sku);
        st.setString(2, uom);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            inventory.setInvent_type(map.get("inv_type").toString());
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getInventoriesByWH(String wh, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where skuwh = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where sku LIKE ?;";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setString(1, wh);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            inventory.setInvent_type(map.get("inv_type").toString());
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getInventoriesByWH(String sku, String wh, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where sku LIKE ? and skuwh = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where sku LIKE ?;";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        sku = "%" + sku + "%";
        
        st.setString(1, sku);
        st.setString(2, wh);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            inventory.setInvent_type(map.get("inv_type").toString());
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getInventoriesByWHDesc(String sku, String wh, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where skudesc LIKE ? and skuwh = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where skudesc LIKE ?;";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        sku = "%" + sku + "%";
        
        st.setString(1, sku);
        st.setString(2, wh);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            inventory.setInvent_type(map.get("inv_type").toString());
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getInventories(String sku, String warehouse, String uom, int table) throws SQLException{
        System.out.println(table);
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where sku LIKE ? and skuom = ? and skuwh = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where sku LIKE ? and skuom = ? and skuwh = ?;";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        sku = "%" + sku + "%";
        
        st.setString(1, sku);
        st.setString(2, uom);
        st.setString(3, warehouse);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            inventory.setInvent_type(map.get("inv_type").toString());
            
            list.add(inventory);
        }
        
        dbc.closeConnection();
        return list.iterator();
    }
    
    public Iterator getInventoriesByDesc2(String sku, String warehouse, String uom, int table) throws SQLException{
        System.out.println(table);
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where skudesc LIKE ? and skuom = ? and skuwh = ?;";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where skudesc LIKE ? and skuom = ? and skuwh = ?;";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        ArrayList<InventoryModel> list = new ArrayList();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        sku = "%" + sku + "%";
        
        st.setString(1, sku);
        st.setString(2, uom);
        st.setString(3, warehouse);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku((String) map.get("sku"));
            inventory.setDescription((String) map.get("skudesc"));
            inventory.setUom((String) map.get("skuom"));
            inventory.setWh((String) map.get("skuwh"));
            inventory.setSoh((int) map.get("soh"));
            inventory.setCsl((int) map.get("csl"));
            inventory.setUnits((int) map.get("units"));
            inventory.setInvent_type(map.get("inv_type").toString());
            
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
        
        InventoryModel im = null;
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        
        if(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            im = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            im.setSku((String) map.get("sku"));
            im.setDescription((String) map.get("skudesc"));
            im.setUom((String) map.get("skuom"));
            im.setSellprice(Double.valueOf(map.get("sellingPrice").toString()));
            im.setPoprice(Double.valueOf(map.get("poPrice").toString()));
            im.setUnits((int) map.get("units"));
            
        }
        return im;
    }
    
    public InventoryModel getInventoryWPoPrice(int id, int table) throws SQLException{
        System.out.println(table);
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("");
        st.setInt(1, id);
        
        InventoryModel im = null;
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        
        if(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            im = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            im.setSku((String) map.get("sku"));
            im.setDescription((String) map.get("skudesc"));
            im.setUom((String) map.get("skuom"));
            im.setSellprice(Double.valueOf(map.get("sellingPrice").toString()));
            im.setPoprice(Double.valueOf(map.get("poPrice").toString()));
            im.setUnits((int) map.get("units"));
            
        }
        return im;
    }
    
    public InventoryModel getInventory(int id, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where idinventory = ?";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where idinventory = ?";
        }
        System.out.println(table);
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        st.setInt(1, id);
        
        InventoryModel im = null;
        
        Iterator rs = dbq.getQueryResultSet(st);

        if(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            im = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            im.setSku((String) map.get("sku"));
            im.setDescription((String) map.get("skudesc"));
            im.setUom((String) map.get("skuom"));
            im.setWh(map.get("skuwh").toString());
            im.setUnits((int) map.get("units"));
            im.setInvent_type(map.get("inv_type").toString());
        }
        return im;
    }
    
    public InventoryModel getInventory(String sku, String uom, String wh, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory where idinventory = ?";
        }
        else if(table == 2){
            query = "SELECT * FROM pm_inventory where sku = ? and skuom = ? and skuwh = ?";
        }
        System.out.println(table);
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        st.setString(1, sku);
        st.setString(2, uom);
        st.setString(3, wh);
        
        InventoryModel im = null;
        
        Iterator rs = dbq.getQueryResultSet(st);

        if(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            im = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            im.setSku((String) map.get("sku"));
            im.setDescription((String) map.get("skudesc"));
            im.setUom((String) map.get("skuom"));
            im.setWh(map.get("skuwh").toString());
            im.setUnits((int) map.get("units"));
            im.setInvent_type(map.get("inv_type").toString());
            
        }
        return im;
    }
    
    public void editInventory(InventoryModel model, int table) throws SQLException{
        
        System.out.println(table);
        
        String query = "";
        
        if(table == 1){
            query = "call INVENTORY_EDIT(?,?,?,?,?,?,?,?);";
        }
        else if(table == 2){
            query = "call MM_INVENTORY_EDIT(?,?,?,?,?,?,?,?);";
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
        st.setInt(7, model.getUnits());
        st.setString(8, model.getInvent_type());
        
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

        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM inventory_adjustments;";
        }
        else {
            query = "SELECT * FROM mm_inventory_adjustments;";
        }
        
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
    
    public void editInventoryAdjustment(InventoryAdjustmentModel iam, int table) throws SQLException{
        
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        String query = "";
        
        if(table == 1){
            query = "CALL `INVENTORY_ADJ_EDIT` (?,?,?,?)";
        }
        else{
            query = "CALL `MM_INVENTORY_ADJ_EDIT` (?,?,?,?)";
        }
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setDate(1, Date.valueOf(iam.getIam_dte().toString()));
        st.setString(2, iam.getRefnum());
        st.setString(3, iam.getDesc());
        st.setInt(4, iam.getIamid());
        
        st.executeUpdate();
    }
    
    public void deleteInventoryAdjustmentItems(Iterator ir, int iaid, int table) throws SQLException{
    
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        String query = "";
        
        if(table == 1){
            query = "UPDATE `inventory_adjustment_items` SET `iastat`='DELETED' WHERE `idia_items`= ?;";
        }
        else{
            query = "UPDATE `mm_inventory_adjustment_items` SET `iastat`='DELETED' WHERE `idia_items`= ?;";
        }
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        while(ir.hasNext()){
            InventoryModel mod = (InventoryModel) ir.next();
            
            st.setInt(1, mod.getIadjid_item());
            st.addBatch();
            
        }
        st.executeBatch();
    }
    
    public Iterator getInventoryAdjustmentsItems(int iaid, int table) throws SQLException{

        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        String query = "";
        
        if(table == 1){
            query = "CALL `INVENTORY_ADJ_GET_ITEMS`(?)";
        }
        else{
            query = "CALL `MM_INVENTORY_ADJ_GET_ITEMS`(?)";
        }
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setInt(1, iaid);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        ArrayList<InventoryModel> iams = new ArrayList();
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel iam = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            
            iam.setIadjid(Integer.valueOf(map.get("ia_id").toString()));
            iam.setIadjid_item(Integer.valueOf(map.get("idia_items").toString()));
            iam.setSoh(Integer.valueOf(map.get("stock_qty").toString()));
            iam.setSku(map.get("sku").toString());
            iam.setDescription(map.get("skudesc").toString());
            iam.setUom(map.get("skuom").toString());
            iam.setWh(map.get("skuwh").toString());
            iam.setMov(map.get("mov").toString());
            
            iams.add(iam);
        }

        return iams.iterator();
        
    }
    
    public Iterator getAllPrices(int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call GET_PRICE_INVENTORY();";
        }
        else if(table == 2){
            query = "call MM_GET_PRICE_INVENTORY();";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        Iterator rs = dbq.getQueryResultSet(st);

        return rs;
    }
    
    public Iterator getAllPrices(String sku, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call `GET_PRICE_INVENTORY_SKU`(?);";
        }
        else if(table == 2){
            query = "call MM_GET_PRICE_INVENTORY();";
        }
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        String par = "%";
        
        par += sku + "%";
        
        st.setString(1, par);
        
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
    
    public void addPrice(PricesModel price, int table, int supplier) throws SQLException{
        
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
    
    public void addMGI(MGIModel mgimod, int table, int r) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call `MGI_ADD`(?,?,?,?,?,?,?,?)";
        }
        else if(table == 2){
            if(r == 1){
                query = "call `MM_MGI_ADD`(?,?,?,?,?,?,?,?)";
            }else{
                query = "call `MGR_ADD`(?,?,?,?,?,?,?,?)";
            }
            
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setDate(1, Date.valueOf(mgimod.getGidte().toString()));
        st.setString(2, mgimod.getCustname());
        st.setString(3, mgimod.getContname());
        st.setString(4, mgimod.getAddress());
        st.setString(5, mgimod.getDescription());
        st.setString(6, mgimod.getRef());
        st.setString(7, mgimod.getAttention());
        st.setInt(8, mgimod.getCby());
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int mgiid = generatedKeys.getInt(1);
                this.addMGItems(mgimod.getItems().iterator(), mgiid, table, r);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void addMGItems(Iterator items, int mgiid, int table, int r) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "call `MGI_ITEM_ADD`(?,?,?);";
        }
        else{
            if(r == 1){
                query = "call `MM_MGI_ITEM_ADD`(?,?,?);";
            }else{
                query = "call `MGR_ITEM_ADD`(?,?,?);";
            }
            
        }

        System.out.println(table);
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        while(items.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            
            ps.setInt(1, mgiid);
            ps.setInt(2, item.getIdinventory());
            ps.setInt(3, item.getSoh());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public Iterator getMGI(int table, int r) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM manual_goods_issue;";
        }
        else{
            if(r == 1){
                query = "SELECT * FROM mm_manual_goods_issue;";
            }
            else{
                query = "SELECT * FROM manual_goods_receipt;";
            }
            
        }
        
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        ArrayList<MGIModel> mgis = new ArrayList();
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            MGIModel mgi = new MGIModel();
            
            mgi.setGinum(Integer.valueOf(map.get("idmgi").toString()));
            mgi.setGidte(Date.valueOf(map.get("mgidte").toString()));
            mgi.setCustname(map.get("custnme").toString());
            mgi.setContname(map.get("contnme").toString());
            mgi.setRef(map.get("renum").toString());
            mgi.setAttention(map.get("attention").toString());
            mgi.setAddress(map.get("address").toString());
            mgi.setDescription(map.get("description").toString());
            mgi.setCby(Integer.parseInt(map.get("cby").toString()));
            mgi.setPgistat(map.get("pgistat").toString());
            
            mgis.add(mgi);
        }

        return mgis.iterator();
        
    }
    
    public Iterator getMGItems(int table, int mgiid, int r) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        String query = "";
        if(table == 1){
            query = "CALL `MGI_ITEM_GET`(?);";
        }
        else{
            if(r == 1){
                query = "CALL `MM_MGI_ITEM_GET`(?);";
            }
            else{
                System.out.println("HERE");
                query = "CALL `MGR_ITEM_GET`(?);"; 
            }
            
        }
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setInt(1, mgiid);
        
        Iterator rs = dbq.getQueryResultSet(st);
        
        ArrayList<InventoryModel> mgitems = new ArrayList();
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel mgi = new InventoryModel(Integer.valueOf(map.get("mgi_invent_id").toString()));
            
            mgi.setMgiid_item(Integer.parseInt(map.get("idmgi_items").toString()));
            mgi.setMgi_id(Integer.parseInt(map.get("mgi_id").toString()));
            mgi.setSku(map.get("sku").toString());
            mgi.setDescription(map.get("skudesc").toString());
            mgi.setUom(map.get("skuom").toString());
            mgi.setWh(map.get("skuwh").toString());
            mgi.setPgistat(map.get("pgistat").toString());
            mgi.setSoh(Integer.parseInt(map.get("valdec").toString()));
            
            mgitems.add(mgi);
        }

        return mgitems.iterator();
    }
    
    public void editMGI(MGIModel mgimod, int table, int r) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call `MGI_EDIT`(?,?,?,?,?,?,?,?)";
        }
        else if(table == 2){
            if(r == 1){
                query = "call `MM_MGI_EDIT`(?,?,?,?,?,?,?,?)";
            }
            else{
                query = "call `MGR_EDIT`(?,?,?,?,?,?,?,?)";
            }
            
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        st.setDate(1, Date.valueOf(mgimod.getGidte().toString()));
        st.setString(2, mgimod.getCustname());
        st.setString(3, mgimod.getContname());
        st.setString(4, mgimod.getAddress());
        st.setString(5, mgimod.getDescription());
        st.setString(6, mgimod.getRef());
        st.setString(7, mgimod.getAttention());
        st.setInt(8, mgimod.getGinum());
        
        st.executeUpdate();
        
    }
    
    public void editMGItems(Iterator items, int table, int r) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "UPDATE `mgi_items` SET `valdec`=? WHERE `idmgi_items`=? and `mgi_invent_id` = ? and `mgi_id` = ?;";
        }
        else{
            if(r == 1){
                query = "UPDATE `mm_mgi_items` SET `valdec`=? WHERE `idmgi_items`=? and `mgi_invent_id` = ? and `mgi_id` = ?;";
            }
            else{
                query = "UPDATE `mgr_items` SET `valdec`=? WHERE `idmgi_items`=? and `mgi_invent_id` = ? and `mgi_id` = ?;";
            }
        }

        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        while(items.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            
            if(item.getMgiid_item() != 0){
                System.out.println(item.getMgiid_item() + " " + item.getSoh());
                ps.setInt(2, item.getMgiid_item());
                ps.setInt(3, item.getIdinventory());
                ps.setInt(4, item.getMgi_id());
                ps.setInt(1, item.getSoh());
                System.out.println(ps.toString());
                ps.addBatch();
            }
            
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void deleteMGIItems(Iterator items, int table, int r) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "UPDATE `mgi_items` SET `status`='DELETED' WHERE `idmgi_items`=? and `mgi_invent_id` = ? and `mgi_id` = ?;";
        }
        else{
           if(r == 1){
              query = "UPDATE `mm_mgi_items` SET `status`='DELETED' WHERE `idmgi_items`=? and `mgi_invent_id` = ? and `mgi_id` = ?;";
           }
           else{
              query = "UPDATE `mgr_items` SET `status`='DELETED' WHERE `idmgi_items`=? and `mgi_invent_id` = ? and `mgi_id` = ?;";
           }
                    
        }

        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query);
        while(items.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            
            if(item.getMgiid_item() != 0){
                System.out.println(item.getMgiid_item());
                ps.setInt(1, item.getMgiid_item());
                ps.setInt(2, item.getIdinventory());
                ps.setInt(3, item.getMgi_id());
                System.out.println(ps.toString());
                ps.addBatch();
            }
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public void PostUpdateInventory(Iterator items, String username, String doc_type, int client_id, String client_name, String client_type, int doc_it, String ref_doc, String doc_remark, int table) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        String query1 = "";
        String query2 = "";
        String query3 = "";
        
        if(table == 1){
            query1 = "CALL `UPDATE_INVENTORY`(?,?)";
            query2 = "CALL `UPDATE_INVENTORY_INC`(?,?)";
            query3 = "CALL `INVENTORY_TRANSACT_ADD`(?,?,?,?,?,?,?,?,?,?,?)";
        }
        else{
            query1 = "CALL `MM_UPDATE_INVENTORY`(?,?)";
            query2 = "CALL `MM_UPDATE_INVENTORY_INC`(?,?)";
            query3 = "CALL `MM_INVENTORY_TRANSACT_ADD`(?,?,?,?,?,?,?,?,?,?,?)";
        }

        PreparedStatement ps1 = dbc.getConnection().prepareStatement(query1);
        PreparedStatement ps2 = dbc.getConnection().prepareStatement(query2);
        PreparedStatement ps3 = dbc.getConnection().prepareStatement(query3);
        
        while(items.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            
            ps3.setString(1, username);
            ps3.setInt(2, item.getIdinventory());
            ps3.setInt(3, item.getSoh());
            ps3.setString(4, item.getMov());
            ps3.setString(5, doc_type);
            ps3.setInt(6, client_id);
            ps3.setString(7, client_name);
            ps3.setString(8, client_type);
            ps3.setInt(9, doc_it);
            ps3.setString(10, ref_doc);
            ps3.setString(11, item.getRemarks());
            
            if(item.getSoh() != 0){
                ps3.addBatch();
            }
            
            
            if(item.getSoh() != 0){
                if(item.getMov().equals("INC")){
                
                    ps2.setInt(1, item.getIdinventory());
                    ps2.setInt(2, item.getSoh());

                    ps2.addBatch();
                }else if(item.getMov().equals("DEC")){

                    ps1.setInt(1, item.getIdinventory());
                    ps1.setInt(2, item.getSoh());

                    ps1.addBatch();
                }
            }
            
            
            
        }
        
        ps1.executeBatch();
        ps2.executeBatch();
        ps3.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void changeMGIPost(Iterator items, int mgiid, int table, int r) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        String query1 = "";
        String query2 = "";
        
        if(table == 1){
            query1 = "UPDATE `manual_goods_issue` SET `pgistat`='Y' WHERE `idmgi`=?;";
            query2 = "UPDATE `mgi_items` SET `pgistat`='Y' WHERE `idmgi_items`=?;";
        }
        else{
            if(r == 1){
                query1 = "UPDATE `mm_manual_goods_issue` SET `pgistat`='Y' WHERE `idmgi`=?;";
                query2 = "UPDATE `mm_mgi_items` SET `pgistat`='Y' WHERE `idmgi_items`=?;";
            }
            else{
                query1 = "UPDATE `manual_goods_receipt` SET `pgistat`='Y' WHERE `idmgi`=?;";
                query2 = "UPDATE `mgr_items` SET `pgistat`='Y' WHERE `idmgi_items`=?;";
            }
        }
        
        PreparedStatement ps1 = dbc.getConnection().prepareStatement(query1);
        
        ps1.setInt(1, mgiid);
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query2);
        while(items.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            
            ps.setInt(1, item.getMgiid_item());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        ps1.executeUpdate();
        
        dbc.closeConnection();
    }
    
    public void changeInventoryAdjPost(Iterator items, int iaid, int table) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        String query1 = "";
        String query2 = "";
        
        if(table == 1){
            query1 = "UPDATE `inventory_adjustments` SET `pgistat`='Y' WHERE `idadjustments`=?;";
            query2 = "UPDATE `inventory_adjustment_items` SET `status`='Y' WHERE `idia_items`=?;";
        }
        else{
            query1 = "UPDATE `mm_inventory_adjustments` SET `pgistat`='Y' WHERE `idadjustments`=?;";
            query2 = "UPDATE `mm_inventory_adjustment_items` SET `status`='Y' WHERE `idia_items`=?;";
        }
        
        PreparedStatement ps1 = dbc.getConnection().prepareStatement(query1);
        
        ps1.setInt(1, iaid);
        
        PreparedStatement ps = dbc.getConnection().prepareStatement(query2);
        while(items.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            
            ps.setInt(1, item.getIadjid_item());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        ps1.executeUpdate();
        
        dbc.closeConnection();
    }
}
