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
public class SOItemModel {
    
    private Integer soitemid;
    private String sku;
    private String desc;
    private int qty;
    private String qty1;
    private String uom;
    private Integer idinventory;
    private double uprice;
    private String uprice1;
    private double discnt;
    private String discnt1;
    private double amount;
    private String amount1;
    private double vat;
    private String vat1;
    private String stat;
    
    private NumberFormat nf= NumberFormat.getInstance();
    private final NumberFormat nf2= NumberFormat.getInstance();
    
    
    public SOItemModel(Integer idinventory){
        this.idinventory = idinventory;
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);
        
        nf2.setMaximumFractionDigits(0);
        nf2.setMinimumFractionDigits(0);
        nf2.setRoundingMode(RoundingMode.HALF_EVEN);
    }
    
    public Integer getId(){
        return idinventory;
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
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
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
     * @return the uprice
     */
    public double getUprice() {
        return uprice;
    }

    /**
     * @param uprice the uprice to set
     */
    public void setUprice(double uprice) {
        this.uprice = uprice;
    }

    /**
     * @return the discnt
     */
    public double getDiscnt() {
        return discnt;
    }

    /**
     * @param discnt the discnt to set
     */
    public void setDiscnt(double discnt) {
        this.discnt = discnt;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the vat
     */
    public double getVat() {
        return vat;
    }

    /**
     * @param vat the vat to set
     */
    public void setVat(double vat) {
        this.vat = vat;
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
     * @return the stat
     */
    public String getStat() {
        return stat;
    }

    /**
     * @param stat the stat to set
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

    /**
     * @return the soitemid
     */
    public int getSoitemid1() {
        return soitemid;
    }
    
    public Integer getSoitemid() {
        return soitemid;
    }

    /**
     * @param soitemid the soitemid to set
     */
    public void setSoitemid(int soitemid) {
        this.soitemid = soitemid;
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
        if (!(object instanceof SOItemModel)) {
            return false;
        }
        SOItemModel other = (SOItemModel) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    /**
     */
    public void setUprice1() {
        this.uprice1 = nf.format(this.uprice);
    }

    /**
     */
    public void setDiscnt1() {
        this.discnt1 = nf.format(this.discnt);
    }

    /**
     */
    public void setAmount1() {
        this.amount1 = nf.format(this.amount);
    }

    /**
     */
    public void setVat1() {
        this.vat1 = nf.format(this.vat);
    }

    /**
     * @return the uprice1
     */
    public String getUprice1() {
        return uprice1;
    }

    /**
     * @return the discnt1
     */
    public String getDiscnt1() {
        return discnt1;
    }

    /**
     * @return the amount1
     */
    public String getAmount1() {
        return amount1;
    }

    /**
     * @return the vat1
     */
    public String getVat1() {
        return vat1;
    }

    /**
     * @return the qty1
     */
    public String getQty1() {
        return qty1;
    }

    /**
     */
    public void setQty1() {
        this.qty1 = this.nf2.format(this.qty);
    }
}
