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
public class InventoryAdjustmentModel {
    
    private int iamid;
    private Date iam_dte;
    private String refnum;
    private String desc;
    private String pgistat;
    
    private ArrayList<InventoryModel> itemslist;
    
    public InventoryAdjustmentModel(){
        this.itemslist = new ArrayList();
    }

    /**
     * @return the iamid
     */
    public int getIamid() {
        return iamid;
    }

    /**
     * @param iamid the iamid to set
     */
    public void setIamid(int iamid) {
        this.iamid = iamid;
    }

    /**
     * @return the iam_dte
     */
    public Date getIam_dte() {
        return iam_dte;
    }

    /**
     * @param iam_dte the iam_dte to set
     */
    public void setIam_dte(Date iam_dte) {
        this.iam_dte = iam_dte;
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
     * @return the itemslist
     */
    public ArrayList<InventoryModel> getItemslist() {
        return itemslist;
    }

    /**
     * @param itemslist the itemslist to set
     */
    public void setItemslist(ArrayList<InventoryModel> itemslist) {
        this.itemslist = itemslist;
    }
    
}
