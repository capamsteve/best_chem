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
public class SIModel {
    
    private Integer siid;
    private int customerid;
    private int soidnvc;
    private String remarks;
    private int cby;
    private String status;
    private String trcnme;
    private String drvnme;
    private String plateno;
    private Date dte;
    private String prntstat;
    
    private ArrayList<SItemsModel> sitems;
    
    public SIModel(){
        this.sitems = new ArrayList();
    }

    /**
     * @return the customerid
     */
    public int getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid the customerid to set
     */
    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    /**
     * @return the soidnvc
     */
    public int getSoidnvc() {
        return soidnvc;
    }

    /**
     * @param soidnvc the soidnvc to set
     */
    public void setSoidnvc(int soidnvc) {
        this.soidnvc = soidnvc;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the sitems
     */
    public ArrayList<SItemsModel> getSitems() {
        return sitems;
    }

    /**
     * @param sitems the sitems to set
     */
    public void setSitems(ArrayList<SItemsModel> sitems) {
        this.sitems = sitems;
    }

    /**
     * @return the trcnme
     */
    public String getTrcnme() {
        return trcnme;
    }

    /**
     * @param trcnme the trcnme to set
     */
    public void setTrcnme(String trcnme) {
        this.trcnme = trcnme;
    }

    /**
     * @return the drvnme
     */
    public String getDrvnme() {
        return drvnme;
    }

    /**
     * @param drvnme the drvnme to set
     */
    public void setDrvnme(String drvnme) {
        this.drvnme = drvnme;
    }

    /**
     * @return the plateno
     */
    public String getPlateno() {
        return plateno;
    }

    /**
     * @param plateno the plateno to set
     */
    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    /**
     * @return the dte
     */
    public Date getDte() {
        return dte;
    }

    /**
     * @param dte the dte to set
     */
    public void setDte(Date dte) {
        this.dte = dte;
    }

    /**
     * @return the prntstat
     */
    public String getPrntstat() {
        return prntstat;
    }

    /**
     * @param prntstat the prntstat to set
     */
    public void setPrntstat(String prntstat) {
        this.prntstat = prntstat;
    }

    /**
     * @return the siid
     */
    public int getSiid() {
        return siid;
    }

    /**
     * @param siid the siid to set
     */
    public void setSiid(int siid) {
        this.siid = siid;
    }
    
}
