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
public class MGIModel {
    
    private Integer ginum;
    private Date gidte;
    private String custname;
    private String contname;
    private String address;
    private String ref;
    private String attention;
    private int cby;
    private String description;
    private String pgistat;
    
    private ArrayList<InventoryModel> items;

    /**
     * @return the ginum
     */
    public int getGinum() {
        return ginum;
    }

    /**
     * @param ginum the ginum to set
     */
    public void setGinum(int ginum) {
        this.ginum = ginum;
    }

    /**
     * @return the gidte
     */
    public Date getGidte() {
        return gidte;
    }

    /**
     * @param gidte the gidte to set
     */
    public void setGidte(Date gidte) {
        this.gidte = gidte;
    }

    /**
     * @return the custname
     */
    public String getCustname() {
        return custname;
    }

    /**
     * @param custname the custname to set
     */
    public void setCustname(String custname) {
        this.custname = custname;
    }

    /**
     * @return the contname
     */
    public String getContname() {
        return contname;
    }

    /**
     * @param contname the contname to set
     */
    public void setContname(String contname) {
        this.contname = contname;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the ref
     */
    public String getRef() {
        return ref;
    }

    /**
     * @param ref the ref to set
     */
    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     * @return the attention
     */
    public String getAttention() {
        return attention;
    }

    /**
     * @param attention the attention to set
     */
    public void setAttention(String attention) {
        this.attention = attention;
    }

    /**
     * @return the cby
     */
    public int getCby() {
        return cby;
    }

    /**
     * @param cby the cby to set
     */
    public void setCby(int cby) {
        this.cby = cby;
    }

    /**
     * @return the items
     */
    public ArrayList<InventoryModel> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<InventoryModel> items) {
        this.items = items;
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
