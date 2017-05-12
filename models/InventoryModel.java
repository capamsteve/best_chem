/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author Steven
 */
public class InventoryModel {
    
    private Integer idinventory;
    private String sku;
    private String description;
    private String uom;
    private String wh;
    private int soh;
    private int csl;
    private double poprice;
    private double sellprice;
    private String mov;
    private int iadjid;
    private int iadjid_item;
    private int mgi_id;
    private int mgiid_item;
    private String pgistat;
    private ArrayList<PricesModel> prices;
    
    //public InventoryModel(){
    //    this.prices = new ArrayList();
    //}
    public InventoryModel(Integer id){
        this.prices = new ArrayList();
        this.idinventory = id;
    }

    /**
     * @return the idinventory
     */
    public int getIdinventory() {
        return idinventory;
    }
    
    public Integer getId() {
        return idinventory;
    }

    /**
     * @param idinventory the idinventory to set
     */
    public void setIdinventory(int idinventory) {
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
     * @return the wh
     */
    public String getWh() {
        return wh;
    }

    /**
     * @param wh the wh to set
     */
    public void setWh(String wh) {
        this.wh = wh;
    }

    /**
     * @return the soh
     */
    public int getSoh() {
        return soh;
    }

    /**
     * @param soh the soh to set
     */
    public void setSoh(int soh) {
        this.soh = soh;
    }

    /**
     * @return the csl
     */
    public int getCsl() {
        return csl;
    }

    /**
     * @param csl the csl to set
     */
    public void setCsl(int csl) {
        this.csl = csl;
    }

    /**
     * @return the prices
     */
    public ArrayList<PricesModel> getPrices() {
        return prices;
    }

    /**
     * @param prices the prices to set
     */
    public void setPrices(ArrayList<PricesModel> prices) {
        this.prices = prices;
    }
    
    public void addPrices(PricesModel price){
        this.prices.add(price);
    }
    
    public PricesModel getPrice(int i){
        return this.prices.get(i);
    }

    /**
     * @return the poprice
     */
    public double getPoprice() {
        return poprice;
    }

    /**
     * @param poprice the poprice to set
     */
    public void setPoprice(double poprice) {
        this.poprice = poprice;
    }

    /**
     * @return the sellprice
     */
    public double getSellprice() {
        return sellprice;
    }

    /**
     * @param sellprice the sellprice to set
     */
    public void setSellprice(double sellprice) {
        this.sellprice = sellprice;
    }

    /**
     * @return the mov
     */
    public String getMov() {
        return mov;
    }

    /**
     * @param mov the mov to set
     */
    public void setMov(String mov) {
        this.mov = mov;
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
        if (!(object instanceof InventoryModel)) {
            return false;
        }
        InventoryModel other = (InventoryModel) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    /**
     * @return the iadjid
     */
    public int getIadjid() {
        return iadjid;
    }

    /**
     * @param iadjid the iadjid to set
     */
    public void setIadjid(int iadjid) {
        this.iadjid = iadjid;
    }

    /**
     * @return the iadjid_item
     */
    public int getIadjid_item() {
        return iadjid_item;
    }

    /**
     * @param iadjid_item the iadjid_item to set
     */
    public void setIadjid_item(int iadjid_item) {
        this.iadjid_item = iadjid_item;
    }

    /**
     * @return the mgi_id
     */
    public int getMgi_id() {
        return mgi_id;
    }

    /**
     * @param mgi_id the mgi_id to set
     */
    public void setMgi_id(int mgi_id) {
        this.mgi_id = mgi_id;
    }

    /**
     * @return the mgiid_item
     */
    public int getMgiid_item() {
        return mgiid_item;
    }

    /**
     * @param mgiid_item the mgiid_item to set
     */
    public void setMgiid_item(int mgiid_item) {
        this.mgiid_item = mgiid_item;
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
