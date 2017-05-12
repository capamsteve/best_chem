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
public class PurchaseItemModel {
    
    private Integer idpurchaseitem;
    private String sku;
    private String desc;
    private int qty;
    private String uom;
    private Integer idinventory;
    private double uprice;
    private double amount;
    private double vat;
    
    public PurchaseItemModel(Integer idinventory){
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
     * @return the idpurchaseitem
     */
    public int getIdpurchaseitem() {
        return idpurchaseitem;
    }

    /**
     * @param idpurchaseitem the idpurchaseitem to set
     */
    public void setIdpurchaseitem(int idpurchaseitem) {
        this.idpurchaseitem = idpurchaseitem;
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
        if (!(object instanceof PurchaseItemModel)) {
            return false;
        }
        PurchaseItemModel other = (PurchaseItemModel) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }
    
}
