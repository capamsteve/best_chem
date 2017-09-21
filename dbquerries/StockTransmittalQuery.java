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
import models.BOModel;
import models.InventoryModel;
import models.STItemModel;
import models.StockTransmittalModel;

/**
 *
 * @author Steven
 */
public class StockTransmittalQuery {
    
    private final InventoryQuery iq = new InventoryQuery();
    
    public void addStockTransmittal(StockTransmittalModel stmod) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call TRM_ADD(?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        
        st.setString(1, stmod.getSupname());
        st.setString(2, stmod.getConname());
        st.setDate(3, Date.valueOf(stmod.getGi_dte().toString()));
        st.setString(4, stmod.getAddress());
        st.setString(5, stmod.getRefnum());
        st.setString(6, stmod.getGidesc());
        st.setInt(7, stmod.getGrnum());
        st.setString(8, stmod.getTrm_type());
        
        ResultSet generatedKeys = st.executeQuery();
        
        try{
            
            if(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
                int stid = generatedKeys.getInt(1);
                this.addStockTransmittalItems(stmod.getItems().iterator(), stid);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void addStockTransmittalItems(Iterator items, int stid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `TRM_ITEM_ADD`(?,?,?,?,?,?,?,?,?,?)");
        while(items.hasNext()){
            STItemModel item = (STItemModel) items.next();
            
            ps.setInt(1, item.getFg_id());
            ps.setString(2, item.getFg_sku());
            ps.setString(3, item.getFg_description());
            ps.setInt(4, item.getOrder_qty());
            ps.setString(5, item.getPm_sku());
            ps.setString(6, item.getPm_desc());
            ps.setString(7, item.getBom_wh());
            ps.setInt(8, item.getUnit_qty());
            ps.setInt(9, stid);
            ps.setInt(10, item.getPm_id());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public void editStockTransMittal(StockTransmittalModel stmod) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement st = dbc.getConnection().prepareStatement("call TRM_EDIT(?,?,?,?,?,?,?);");
        
        st.setString(1, stmod.getSupname());
        st.setString(2, stmod.getConname());
        st.setDate(3, Date.valueOf(stmod.getGi_dte().toString()));
        st.setString(4, stmod.getAddress());
        st.setString(5, stmod.getRefnum());
        st.setString(6, stmod.getGidesc());
        st.setInt(7, stmod.getStid());
        
        st.executeUpdate();
    }
    
    public void editStockTransmittalItems(Iterator items) throws SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `tritems` SET `order_qty`=?, `bom_wh`=?, `unit_qty`=? WHERE `idtritems`=?;");
        while(items.hasNext()){
            STItemModel item = (STItemModel) items.next();
            
            ps.setInt(1, item.getOrder_qty());
            ps.setString(2, item.getBom_wh());
            ps.setInt(3, item.getUnit_qty());
            ps.setInt(4, item.getStitemid());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
    }
    
    public void deleteStockTransmittalItems(Iterator items) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("UPDATE `tritems` SET `item_stat`='DELETED' WHERE `idtritems`=?;");
        while(items.hasNext()){
            BOModel item = (BOModel) items.next();
            
            ps.setInt(1, item.getStitemid());
            
            ps.addBatch();
        }
        
        ps.executeBatch();
        
        dbc.closeConnection();
        
    }
    
    public Iterator getTransmittals() throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("SELECT * FROM transmittal;");
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        ArrayList<StockTransmittalModel> mods = new ArrayList();
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            StockTransmittalModel model = new StockTransmittalModel();
            
            model.setSupname(map.get("supname").toString());
            model.setConname(map.get("conname").toString());
            model.setAddress(map.get("addres").toString());
            model.setStid(Integer.valueOf(map.get("idtransmittal").toString()));
            model.setGrnum(Integer.valueOf(map.get("grnum").toString()));
            model.setGidesc(map.get("gidesc").toString());
            model.setGi_dte(Date.valueOf(map.get("gi_date").toString()));
            model.setRefnum(map.get("refnum").toString());
            model.setTrm_type(map.get("trn_type").toString());
            model.setPgistat(map.get("pgistat").toString());
            
            mods.add(model);
        }
        
        return mods.iterator();
        
    }
    
    public Iterator getTransmittalPMItems(int stmid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.tritems where transmittal_id = ? and item_stat = 'OPEN';");
        
        ps.setInt(1, stmid);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        ArrayList<BOModel> mods = new ArrayList();
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            BOModel inventory = new BOModel(Integer.valueOf(map.get("pm_invent_id").toString()));
            inventory.setSku(map.get("pm_sku").toString());
            inventory.setStitemid(Integer.valueOf(map.get("idtritems").toString()));
            inventory.setDescription(map.get("pm_desc").toString());
            inventory.setBom_qty(Integer.valueOf(map.get("unit_qty").toString()));
            inventory.setWhouse(map.get("bom_wh").toString());
            inventory.setFg_inventory(Integer.valueOf(map.get("fginvent_id").toString()));
            inventory.setStitemid(Integer.valueOf(map.get("idtritems").toString()));
            inventory.setBom_qty1();
            mods.add(inventory);
        }
        
        return mods.iterator();
        
        
    }
    
    public Iterator getTransmittalFGItems(int stmid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        DBQuery dbq = DBQuery.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("SELECT * FROM bestchem_db2.tritems where transmittal_id = ? and item_stat = 'OPEN' group by fginvent_id;");
        
        ps.setInt(1, stmid);
        
        Iterator rs = dbq.getQueryResultSet(ps);
        
        ArrayList<InventoryModel> mods = new ArrayList();
        
        while(rs.hasNext()){
            HashMap map = (HashMap) rs.next();
            InventoryModel model = iq.getInventory(Integer.valueOf(map.get("fginvent_id").toString()), 1);
            model.setSoh(Integer.valueOf(map.get("order_qty").toString()));
            
            mods.add(model);
        }
        
        return mods.iterator();
        
    }
    
    public void changePGIStat(int stmid) throws SQLException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps1 = dbc.getConnection().prepareStatement("UPDATE `transmittal` SET `pgistat`='Y' WHERE `idtransmittal`=?;");
        
        ps1.setInt(1, stmid);
        
        ps1.executeUpdate();
        
    }
    
}
