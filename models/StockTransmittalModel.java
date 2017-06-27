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
public class StockTransmittalModel {
    
    private int stid;
    private String supname;
    private String conname;
    private Date gi_dte;
    private String address;
    private String refnum;
    private String gidesc;
    private int grnum;
    private String trm_type;
    private String pgistat;
    
    private ArrayList<STItemModel> items;
    
    public StockTransmittalModel(){
        this.items = new ArrayList();
    }

    /**
     * @return the stid
     */
    public int getStid() {
        return stid;
    }

    /**
     * @param stid the stid to set
     */
    public void setStid(int stid) {
        this.stid = stid;
    }

    /**
     * @return the supname
     */
    public String getSupname() {
        return supname;
    }

    /**
     * @param supname the supname to set
     */
    public void setSupname(String supname) {
        this.supname = supname;
    }

    /**
     * @return the conname
     */
    public String getConname() {
        return conname;
    }

    /**
     * @param conname the conname to set
     */
    public void setConname(String conname) {
        this.conname = conname;
    }

    /**
     * @return the gi_dte
     */
    public Date getGi_dte() {
        return gi_dte;
    }

    /**
     * @param gi_dte the gi_dte to set
     */
    public void setGi_dte(Date gi_dte) {
        this.gi_dte = gi_dte;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the refnum
     */
    public String getRefnum() {
        return refnum;
    }

    /**
     * @param refnum the refnum to set
     */
    public void setRefnum(String refnum) {
        this.refnum = refnum;
    }

    /**
     * @return the gidesc
     */
    public String getGidesc() {
        return gidesc;
    }

    /**
     * @param gidesc the gidesc to set
     */
    public void setGidesc(String gidesc) {
        this.gidesc = gidesc;
    }

    /**
     * @return the grnum
     */
    public int getGrnum() {
        return grnum;
    }

    /**
     * @param grnum the grnum to set
     */
    public void setGrnum(int grnum) {
        this.grnum = grnum;
    }

    /**
     * @return the trm_type
     */
    public String getTrm_type() {
        return trm_type;
    }

    /**
     * @param trm_type the trm_type to set
     */
    public void setTrm_type(String trm_type) {
        this.trm_type = trm_type;
    }

    /**
     * @return the items
     */
    public ArrayList<STItemModel> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<STItemModel> items) {
        this.items = items;
    }

    /**
     * @return the pgistat
     */
    public String getPgistat() {
        return pgistat;
    }

    /**
     * @param pgistat the pgistat to set
     */
    public void setPgistat(String pgistat) {
        this.pgistat = pgistat;
    }
    
}
