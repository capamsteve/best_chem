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
public class ReturnAdjustmentModel {
    
    private int ramid;
    private Date ramdte;
    private String refnum;
    private String desc;
    private String pgistat;
    
    private ArrayList<ReturnsModel> items;
    
    public ReturnAdjustmentModel(){
        this.items = new ArrayList();
    }

    /**
     * @return the ramid
     */
    public int getRamid() {
        return ramid;
    }

    /**
     * @param ramid the ramid to set
     */
    public void setRamid(int ramid) {
        this.ramid = ramid;
    }

    /**
     * @return the ramdte
     */
    public Date getRamdte() {
        return ramdte;
    }

    /**
     * @param ramdte the ramdte to set
     */
    public void setRamdte(Date ramdte) {
        this.ramdte = ramdte;
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
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
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

    /**
     * @return the items
     */
    public ArrayList<ReturnsModel> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<ReturnsModel> items) {
        this.items = items;
    }
    
}
