/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 *
 * @author Steven
 */
public class BOModel {
    
    private int bomid;
    private Integer idinventory;
    private String sku;
    private String description;
    private int bom_qty;
    private String bom_qty1;
    private String uom;
    private String whouse;
    private String status;
    private int fg_inventory;
    private int pm_inventory;
    private int stitemid;
    
    private final NumberFormat nf2= NumberFormat.getInstance();
    
    public BOModel(Integer idinventory){
        this.idinventory = idinventory;
        
        
        nf2.setMaximumFractionDigits(0);
        nf2.setMinimumFractionDigits(0);
        nf2.setRoundingMode(RoundingMode.HALF_EVEN);
    }
    
    /**
     * @return the bomid
     */
    public int getBomid() {
        return bomid;
    }

    /**
     * @param bomid the bomid to set
     */
    public void setBomid(int bomid) {
        this.bomid = bomid;
    }
    
    public int getIdinventory() {
        return idinventory;
    }

    /**
     * @return the idinventory
     */
    public Integer getId() {
        return idinventory;
    }

    /**
     * @param idinventory the idinventory to set
     */
    public void setIdinventory(Integer idinventory) {
        this.idinventory = idinventory;
    }

    /**
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the bom_qty
     */
    public int getBom_qty() {
        return bom_qty;
    }

    /**
     * @param bom_qty the bom_qty to set
     */
    public void setBom_qty(int bom_qty) {
        this.bom_qty = bom_qty;
    }

    /**
     * @return the uom
     */
    public String getUom() {
        return uom;
    }

    /**
     * @param uom the uom to set
     */
    public void setUom(String uom) {
        this.uom = uom;
    }

    /**
     * @return the whouse
     */
    public String getWhouse() {
        return whouse;
    }

    /**
     * @param whouse the whouse to set
     */
    public void setWhouse(String whouse) {
        this.whouse = whouse;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BOModel)) {
            return false;
        }
        BOModel other = (BOModel) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    /**
     * @return the fg_inventory
     */
    public int getFg_inventory() {
        return fg_inventory;
    }

    /**
     * @param fg_inventory the fg_inventory to set
     */
    public void setFg_inventory(int fg_inventory) {
        this.fg_inventory = fg_inventory;
    }

    /**
     * @return the stitemid
     */
    public int getStitemid() {
        return stitemid;
    }

    /**
     * @param stitemid the stitemid to set
     */
    public void setStitemid(int stitemid) {
        this.stitemid = stitemid;
    }

    /**
     * @return the pm_inventory
     */
    public int getPm_inventory() {
        return pm_inventory;
    }

    /**
     * @param pm_inventory the pm_inventory to set
     */
    public void setPm_inventory(int pm_inventory) {
        this.pm_inventory = pm_inventory;
    }

    /**
     * @return the bom_qty1
     */
    public String getBom_qty1() {
        return bom_qty1;
    }

    /**
     */
    public void setBom_qty1() {
        this.bom_qty1 = this.nf2.format(this.bom_qty);
    }
    
}
