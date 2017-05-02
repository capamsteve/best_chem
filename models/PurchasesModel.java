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
public class PurchasesModel {
    
    private int idpurchases;
    private int sup_id;
    private String supcname;
    private Date po_dte;
    private Date po_dr_dte;
    private int cbyid;
    private String pgistat;
    
    private ArrayList<PurchaseItemModel> purchases;
    
    public PurchasesModel(){
        this.purchases = new ArrayList();
    }

    /**
     * @return the idpurchases
     */
    public int getIdpurchases() {
        return idpurchases;
    }

    /**
     * @param idpurchases the idpurchases to set
     */
    public void setIdpurchases(int idpurchases) {
        this.idpurchases = idpurchases;
    }

    /**
     * @return the sup_id
     */
    public int getSup_id() {
        return sup_id;
    }

    /**
     * @param sup_id the sup_id to set
     */
    public void setSup_id(int sup_id) {
        this.sup_id = sup_id;
    }

    /**
     * @return the supcname
     */
    public String getSupcname() {
        return supcname;
    }

    /**
     * @param supcname the supcname to set
     */
    public void setSupcname(String supcname) {
        this.supcname = supcname;
    }

    /**
     * @return the po_dte
     */
    public Date getPo_dte() {
        return po_dte;
    }

    /**
     * @param po_dte the po_dte to set
     */
    public void setPo_dte(Date po_dte) {
        this.po_dte = po_dte;
    }

    /**
     * @return the po_dr_dte
     */
    public Date getPo_dr_dte() {
        return po_dr_dte;
    }

    /**
     * @param po_dr_dte the po_dr_dte to set
     */
    public void setPo_dr_dte(Date po_dr_dte) {
        this.po_dr_dte = po_dr_dte;
    }

    /**
     * @return the cbyid
     */
    public int getCbyid() {
        return cbyid;
    }

    /**
     * @param cbyid the cbyid to set
     */
    public void setCbyid(int cbyid) {
        this.cbyid = cbyid;
    }

    /**
     * @return the purchases
     */
    public ArrayList<PurchaseItemModel> getPurchases() {
        return purchases;
    }

    /**
     * @param purchases the purchases to set
     */
    public void setPurchases(ArrayList<PurchaseItemModel> purchases) {
        this.purchases = purchases;
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
