/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Steven
 */
public class STItemModel {
    
    private int stitemid;
    private int stid;
    private Integer idinventory;
    private String fg_sku;
    private int fg_id;
    private String fg_description;
    private int order_qty;
    private String pm_sku;
    private String pm_desc;
    private String bom_wh;
    private int unit_qty;
    private String item_stat;
    private int pm_id;

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
     * @return the idinventory
     */
    public Integer getIdinventory() {
        return idinventory;
    }

    /**
     * @param idinventory the idinventory to set
     */
    public void setIdinventory(Integer idinventory) {
        this.idinventory = idinventory;
    }

    /**
     * @return the fg_sku
     */
    public String getFg_sku() {
        return fg_sku;
    }

    /**
     * @param fg_sku the fg_sku to set
     */
    public void setFg_sku(String fg_sku) {
        this.fg_sku = fg_sku;
    }

    /**
     * @return the fg_description
     */
    public String getFg_description() {
        return fg_description;
    }

    /**
     * @param fg_description the fg_description to set
     */
    public void setFg_description(String fg_description) {
        this.fg_description = fg_description;
    }

    /**
     * @return the order_qty
     */
    public int getOrder_qty() {
        return order_qty;
    }

    /**
     * @param order_qty the order_qty to set
     */
    public void setOrder_qty(int order_qty) {
        this.order_qty = order_qty;
    }

    /**
     * @return the pm_sku
     */
    public String getPm_sku() {
        return pm_sku;
    }

    /**
     * @param pm_sku the pm_sku to set
     */
    public void setPm_sku(String pm_sku) {
        this.pm_sku = pm_sku;
    }

    /**
     * @return the pm_desc
     */
    public String getPm_desc() {
        return pm_desc;
    }

    /**
     * @param pm_desc the pm_desc to set
     */
    public void setPm_desc(String pm_desc) {
        this.pm_desc = pm_desc;
    }

    /**
     * @return the bom_wh
     */
    public String getBom_wh() {
        return bom_wh;
    }

    /**
     * @param bom_wh the bom_wh to set
     */
    public void setBom_wh(String bom_wh) {
        this.bom_wh = bom_wh;
    }

    /**
     * @return the unit_qty
     */
    public int getUnit_qty() {
        return unit_qty;
    }

    /**
     * @param unit_qty the unit_qty to set
     */
    public void setUnit_qty(int unit_qty) {
        this.unit_qty = unit_qty;
    }

    /**
     * @return the item_stat
     */
    public String getItem_stat() {
        return item_stat;
    }

    /**
     * @param item_stat the item_stat to set
     */
    public void setItem_stat(String item_stat) {
        this.item_stat = item_stat;
    }

    /**
     * @return the fg_id
     */
    public int getFg_id() {
        return fg_id;
    }

    /**
     * @param fg_id the fg_id to set
     */
    public void setFg_id(int fg_id) {
        this.fg_id = fg_id;
    }

    /**
     * @return the pm_id
     */
    public int getPm_id() {
        return pm_id;
    }

    /**
     * @param pm_id the pm_id to set
     */
    public void setPm_id(int pm_id) {
        this.pm_id = pm_id;
    }
    
    
}
