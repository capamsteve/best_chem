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

/**
 *
 * @author Steven
 */
public class ReportsQuery {
    
    public void InventorySummaryReport() throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `INVENTORY_SUMMARY_REPORT`()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "INVENTORY_SUMMARY_REPORT.csv");
        
    }
    
    public void InventoryDetailedReport(){
        
    }
    
    public void InventoryCriticalStockReport() throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `INVENTORY_CRITICAL_STOCK_REPORT`()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "INVENTORY_CRITICAL_STOCK_REPORT.csv");
    }
    
    public void POReport(){
        
    }
    
    public void SOReport(Date fromdte, Date todte) throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `SO_REPORT`(?,?)");
        
        ps.setDate(1, fromdte);
        ps.setDate(2, todte);
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "SO_REPORT.csv");
        
    }
    
    public void SIReport(){
        
    }
    
    public void DRReport(){
        
    }
    
    public void SRSummaryReport() throws SQLException, IOException{
        
        DBConnect dbc = DBConnect.getInstance();
        
        PreparedStatement ps = dbc.getConnection().prepareStatement("CALL `RETURNS_SUMMARY_REPORT`()()");
        
        ResultSet rs = ps.executeQuery();
        
        this.exportToCSV(rs, "SR_SUMMARY_REPORT.csv");
    }
    
    public void SRDetailedReport(){
        
    }
    
    
    private void exportToCSV(ResultSet rs, String filename) throws IOException, SQLException{
       
       String currentUsersHomeDir = System.getProperty("user.home");
       File dir = new File(currentUsersHomeDir + "\\Documents\\Reports");
       if(!dir.exists()){
           System.out.println("Directory Created");
           dir.mkdir();
       }
       
       File file = new File(dir.getAbsolutePath()+ "\\" + filename);
       if(!file.exists()){
           file.createNewFile();
       }
        
       FileWriter fw = new FileWriter(dir.getAbsolutePath()+ "\\" + filename);
       
       while(rs.next()){
           int size = rs.getMetaData().getColumnCount();
           for(int i = 1; i <= size; i++){
               fw.append(rs.getString(i));
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
