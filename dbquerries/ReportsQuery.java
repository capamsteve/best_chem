/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbquerries;

import dbconn.DBConnect;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import models.SupplierModel;

/**
 *
 * @author Steven
 */
public class ReportsQuery {
    
    public void InventorySummaryReport() throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `INVENTORY_SUMMARY_REPORT`()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "INVENTORY_SUMMARY_REPORT");
        
    }
    
    public void MM_InventorySummaryReport() throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `MM_INVENTORY_SUMMARY_REPORT`()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "MM_INVENTORY_SUMMARY_REPORT");
        
    }
    
    public void InventoryDetailedReport(Date fromdte, Date todte) throws SQLException, IOException{
        
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `INVENTORY_DTLD_REPORT` (?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "INVENTORY_DTLD_REPORT");
    }
    
    public void MM_InventoryDetailedReport(Date fromdte, Date todte) throws SQLException, IOException{
        
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `MM_INVENTORY_DTLD_REPORT` (?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "MM_INVENTORY_DTLD_REPORT");
    }
    
    public void InventoryCriticalStockReport() throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `INVENTORY_CRITICAL_STOCK_REPORT`()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "INVENTORY_CRITICAL_STOCK_REPORT");
    }
    
    public void MM_InventoryCriticalStockReport() throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `MM_INVENTORY_CRITICAL_STOCK_REPORT`()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "MM_INVENTORY_CRITICAL_STOCK_REPORT");
    }
    
    public void POReport(Date fromdte, Date todte) throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `PO_REPORT`(?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "PO_REPORT");
        
    }
    
    public void MM_POReport(Date fromdte, Date todte) throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `MM_PO_REPORT`(?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "MM_PO_REPORT");
        
    }
    
    public void SOReport(Date fromdte, Date todte) throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `SO_REPORT`(?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "SO_REPORT");
        
    }
    
    public void SIReport(Date fromdte, Date todte) throws SQLException, IOException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `SI_REPORT`(?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "SI_REPORT");
    }
    
    public void DRReport(Date fromdte, Date todte) throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `DR_REPORT`(?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "DR_REPORT");
        
    }
    
    public void SRSummaryReport() throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `RETURNS_SUMMARY_REPORT`()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "SR_SUMMARY_REPORT");
    }
    
    public void MM_SRSummaryReport() throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `MM_RETURNS_SUMMARY_REPORT`()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "MM_SR_SUMMARY_REPORT");
    }
    
    public void SRDetailedReport(Date fromdte, Date todte) throws SQLException, IOException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `SR_DTLD_REPORT`(?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "SR_DTLD_REPORT");
    }
    
    public void MM_SRDetailedReport(Date fromdte, Date todte) throws SQLException, IOException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `MM_SR_DTLD_REPORT`(?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "MM_SR_DTLD_REPORT");
    }
    
    public void StockTransferReport(Date fromdte, Date todte) throws SQLException, IOException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `ST_REPORT`(?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "STOCK_TRANSFER_REPORT");
    }
    
    public void BOM_REPORT() throws IOException, SQLException{
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `BOM_REPORT`()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "BOM_REPORT");
    }
    
    public void PRICE_REPORT(SupplierModel mod) throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `MM_PRICES_GET`(?);");
        
        ps.setInt(1, mod.getSupid());
        
        ResultSet rs = ps.executeQuery();
        
        String price_name = "PRICE_REPORT_" + mod.getSupname().toUpperCase();
        
        this.exportToCSV(rs, price_name);
        
        
    }
    
    private void exportToCSV(ResultSet rs, String filename) throws IOException, SQLException{
       
       String currentUsersHomeDir = System.getProperty("user.home");
       File dir = new File(currentUsersHomeDir + "\\Documents\\Reports");
       if(!dir.exists()){
           System.out.println("Directory Created");
           dir.mkdir();
       }
       
       java.util.Date dte = new java.util.Date();
       SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy h:mm:ss a");
       String formattedDate = sdf.format(dte);
       
       String trufname = filename + "-" + formattedDate.replaceAll("[\\s-:]","") + ".csv"; 
       
       File file = new File(dir.getAbsolutePath()+ "\\" + trufname);
       if(!file.exists()){
           file.createNewFile();
       }
        
       FileWriter fw = new FileWriter(dir.getAbsolutePath()+ "\\" + trufname);
       
       while(rs.next()){
           int size = rs.getMetaData().getColumnCount();
           for(int i = 1; i <= size; i++){
               fw.append(rs.getString(i).replaceAll(",", ""));
               if(i == size){
                   fw.append('\n');
               }else{
                   fw.append(',');
               }
           }
       }
       
       fw.flush();
       fw.close();
       
       rs.close();
        System.out.println("Report Created.");
    }
    
}
