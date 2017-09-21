/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;

/**
 *
 * @author Steven
 */
public class PricesModel {
    
    private Integer idprices;
    private double poprice;
    private String poprice1;
    private double sellingprice;
    private String sellingprice1;
    private Date effdte;
    private int idinventory;
    private String sku;
    private String skudesc;
    private String skuom;
    private String whs;
    private NumberFormat nf= NumberFormat.getInstance();
    
    public PricesModel(){
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);
    }

    /**
     * @return the idprices
     */
    public int getIdprices() {
        return idprices;
    }

    /**
     * @param idprices the idprices to set
     */
    public void setIdprices(int idprices) {
        this.idprices = idprices;
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
     * @return the sellingprice
     */
    public double getSellingprice() {
        return sellingprice;
    }

    /**
     * @param sellingprice the sellingprice to set
     */
    public void setSellingprice(double sellingprice) {
        this.sellingprice = sellingprice;
    }

    /**
     * @return the effdte
     */
    public Date getEffdte() {
        return effdte;
    }

    /**
     * @param effdte the effdte to set
     */
    public void setEffdte(Date effdte) {
        this.effdte = effdte;
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
     * @return the skudesc
     */
    public String getSkudesc() {
        return skudesc;
    }

    /**
     * @param skudesc the skudesc to set
     */
    public void setSkudesc(String skudesc) {
        this.skudesc = skudesc;
    }

    /**
     * @return the skuom
     */
    public String getSkuom() {
        return skuom;
    }

    /**
     * @param skuom the skuom to set
     */
    public void setSkuom(String skuom) {
        this.skuom = skuom;
    }

    /**
     * @return the poprice1
     */
    public String getPoprice1() {
        return poprice1;
    }

    /**
     */
    public void setPoprice1() {
        this.poprice1 = nf.format(this.poprice);
    }

    /**
     * @return the sellingprice1
     */
    public String getSellingprice1() {
        return sellingprice1;
    }

    /**
     */
    public void setSellingprice1() {
        this.sellingprice1 = nf.format(this.sellingprice);
    }

    /**
     * @return the whs
     */
    public String getWhs() {
        return whs;
    }

    /**
     * @param whs the whs to set
     */
    public void setWhs(String whs) {
        this.whs = whs;
    }
    
}
