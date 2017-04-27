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
    
    private int idinventory;
    private String sku;
    private String description;
    private String uom;
    private String wh;
    private int soh;
    private int csl;
    private double poprice;
    private double sellprice;
    private ArrayList<PricesModel> prices;
    
    public InventoryModel(){
        this.prices = new ArrayList();
    }

    /**
     * @return the idinventory
     */
    public int getIdinventory() {
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
    
}
