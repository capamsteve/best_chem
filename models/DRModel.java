/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Steven
 */
public class DRModel {
    
    private int drid;
    private Date drdate;
    private int custid;
    private int soid;
    private String remarks;
    private int cby;
    private String drprint;
    private String drstatus;
    private String pgi;
    
    private ArrayList<DRItemsModel> dritems;
    
    public DRModel(){
        this.dritems = new ArrayList();
    }

    /**
     * @return the drdate
     */
    public Date getDrdate() {
        return drdate;
    }

    /**
     * @param drdate the drdate to set
     */
    public void setDrdate(Date drdate) {
        this.drdate = drdate;
    }

    /**
     * @return the custid
     */
    public int getCustid() {
        return custid;
    }

    /**
     * @param custid the custid to set
     */
    public void setCustid(int custid) {
        this.custid = custid;
    }

    /**
     * @return the soid
     */
    public int getSoid() {
        return soid;
    }

    /**
     * @param soid the soid to set
     */
    public void setSoid(int soid) {
        this.soid = soid;
    }
    
    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the cby
     */
    public int getCby() {
        return cby;
    }

    /**
     * @param cby the cby to set
     */
    public void setCby(int cby) {
        this.cby = cby;
    }

    /**
     * @return the drprint
     */
    public String getDrprint() {
        return drprint;
    }

    /**
     * @param drprint the drprint to set
     */
    public void setDrprint(String drprint) {
        this.drprint = drprint;
    }

    /**
     * @return the drstatus
     */
    public String getDrstatus() {
        return drstatus;
    }

    /**
     * @param drstatus the drstatus to set
     */
    public void setDrstatus(String drstatus) {
        this.drstatus = drstatus;
    }

    /**
     * @return the dritems
     */
    public ArrayList<DRItemsModel> getDritems() {
        return dritems;
    }

    /**
     * @param dritems the dritems to set
     */
    public void setDritems(ArrayList<DRItemsModel> dritems) {
        this.dritems = dritems;
    }

    /**
     * @return the drid
     */
    public int getDrid() {
        return drid;
    }

    /**
     * @param drid the drid to set
     */
    public void setDrid(int drid) {
        this.drid = drid;
    }

    /**
     * @return the pgi
     */
    public String getPgi() {
        return pgi;
    }

    /**
     * @param pgi the pgi to set
     */
    public void setPgi(String pgi) {
        this.pgi = pgi;
    }
    
    
    
}
