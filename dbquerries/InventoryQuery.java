/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbquerries;

import dbconn.DBConnect;
import dbconn.DBQuery;
import delivery.DRItemViewModel;
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
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
            inventory.setSku(map.get("sku").toString());
            inventory.setDescription(map.get("skudesc").toString());
            inventory.setUom(map.get("skuom").toString());
            inventory.setWh(map.get("skuwh").toString());
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
    
    public void updateInventories(Iterator ir, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "call `UPDATE_INVENTORY`(?,?);";
        }
        else if(table == 2){
            query = "call `MM_UPDATE_INVENTORY`(?,?);";
        }
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement(query);
        
        while(ir.hasNext()){
            DRItemViewModel item = (DRItemViewModel) ir.next();
            st.setInt(1, item.getInventory_id());
            System.out.println(item.getInventory_id());
            st.setInt(2, item.getDeliveryqty());
            System.out.println(item.getDeliveryqty());
            
            st.executeUpdate();
        }
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
            InventoryModel inventory = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
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
        
        InventoryModel im = null;
        
        Iterator rs = dbq.getQueryResultSet(st);

        if(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            im = new InventoryModel(Integer.valueOf(map.get("idinventory").toString()));
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
    
    public Iterator getInventoryAdjustments(int soid) throws SQLException{

        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.inventory_adjustments;");
        
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
    
    public void editInventoryAdjustment(InventoryAdjustmentModel iam) throws SQLException{
        
        
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `INVENTORY_ADJ_EDIT` (?,?,?,?)");
        
        st.setDate(1, Date.valueOf(iam.getIam_dte().toString()));
        st.setString(2, iam.getRefnum());
        st.setString(3, iam.getDesc());
        st.setInt(4, iam.getIamid());
        
        st.executeUpdate();
    }
    
    public void deleteInventoryAdjustmentItems(Iterator ir, int iaid, int table) throws SQLException{
    
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("UPDATE `bestchem_db2`.`inventory_adjustment_items` SET `iastat`='DELETED' WHERE `idia_items`= ?;");
        
        while(ir.hasNext()){
            InventoryModel mod = (InventoryModel) ir.next();
            
            st.setInt(1, mod.getIadjid_item());
            st.addBatch();
            
        }
        st.executeBatch();
    }
    
    public Iterator getInventoryAdjustmentsItems(int iaid) throws SQLException{

        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `INVENTORY_ADJ_GET_ITEMS`(?)");
        
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
    
    public void addMGI(MGIModel mgimod, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call `MGI_ADD`(?,?,?,?,?,?,?,?)";
        }
        else if(table == 2){
            query = "";
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
                this.addMGItems(mgimod.getItems().iterator(), mgiid, table);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void addMGItems(Iterator items, int mgiid, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "call `MGI_ITEM_ADD`(?,?,?);";
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
    
    public Iterator getMGI(int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "SELECT * FROM bestchem_db2.manual_goods_issue;";
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
    
    public Iterator getMGItems(int table, int mgiid) throws SQLException{
        DBQuery dbq = DBQuery.getInstance();
        DBConnect dbc = DBConnect.getInstance();
        PreparedStatement st = dbc.getConnection().prepareStatement("CALL `MGI_ITEM_GET`(?);");
        
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
    
    public void editMGI(MGIModel mgimod, int table) throws SQLException{
        
        String query = "";
        
        if(table == 1){
            query = "call `MGI_EDIT`(?,?,?,?,?,?,?,?)";
        }
        else if(table == 2){
            query = "";
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
    
    public void editMGItems(Iterator items, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "UPDATE `bestchem_db2`.`mgi_items` SET `valdec`=? WHERE `idmgi_items`=? and `mgi_invent_id` = ? and `mgi_id` = ?;";
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
    
    public void deleteMGIItems(Iterator items, int table) throws SQLException{
        String query = "";
        
        if(table == 1){
            query = "UPDATE `bestchem_db2`.`mgi_items` SET `status`='DELETED' WHERE `idmgi_items`=? and `mgi_invent_id` = ? and `mgi_id` = ?;";
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
    
    public void PostUpdateInventory(Iterator items) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();

        PreparedStatement ps1 = dbc.getConnection().prepareStatement("CALL `UPDATE_INVENTORY`(?,?)");
        PreparedStatement ps2 = dbc.getConnection().prepareStatement("CALL `UPDATE_INVENTORY_INC`(?,?)");
        
        while(items.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            
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
        
        ps1.executeBatch();
        ps2.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void changeMGIPost(Iterator items, int mgiid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps1 = dbc.getConnection().prepareStatement("UPDATE `bestchem_db2`.`manual_goods_issue` SET `pgistat`='Y' WHERE `idmgi`=?;");
        
        ps1.setInt(1, mgiid);
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `bestchem_db2`.`mgi_items` SET `pgistat`='Y' WHERE `idmgi_items`=?;");
        while(items.hasNext()){
            InventoryModel item = (InventoryModel) items.next();
            
            ps.setInt(1, item.getMgiid_item());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        ps1.executeUpdate();
        
        dbc.closeConnection();
    }
    
    public void changeInventoryAdjPost(Iterator items, int iaid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps1 = dbc.getConnection().prepareStatement("UPDATE `bestchem_db2`.`inventory_adjustments` SET `pgistat`='Y' WHERE `idadjustments`=?;");
        
        ps1.setInt(1, iaid);
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `bestchem_db2`.`inventory_adjustment_items` SET `status`='Y' WHERE `idia_items`=?;");
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
