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
public class StockTransferModel {
    
    private int idst;
    private Date st_dte;
    private Date dr_dte;
    private String slto;
    private String attention;
    private String refnum;
    private String address;
    private String ponum;
    private String trucknme;
    private String drvrnme;
    private String plate;
    private String stdesc;
    private String remarks;
    private String stat;
    
    private ArrayList<InventoryModel> items;
    private ArrayList<InventoryModel> items2;
    
    public StockTransferModel(){
        this.items = new ArrayList();
        this.items2 = new ArrayList();
    }

    /**
     * @return the idst
     */
    public int getIdst() {
        return idst;
    }

    /**
     * @param idst the idst to set
     */
    public void setIdst(int idst) {
        this.idst = idst;
    }

    /**
     * @return the st_dte
     */
    public Date getSt_dte() {
        return st_dte;
    }

    /**
     * @param st_dte the st_dte to set
     */
    public void setSt_dte(Date st_dte) {
        this.st_dte = st_dte;
    }

    /**
     * @return the dr_dte
     */
    public Date getDr_dte() {
        return dr_dte;
    }

    /**
     * @param dr_dte the dr_dte to set
     */
    public void setDr_dte(Date dr_dte) {
        this.dr_dte = dr_dte;
    }

    /**
     * @return the slto
     */
    public String getSlto() {
        return slto;
    }

    /**
     * @param slto the slto to set
     */
    public void setSlto(String slto) {
        this.slto = slto;
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
     * @return the ponum
     */
    public String getPonum() {
        return ponum;
    }

    /**
     * @param ponum the ponum to set
     */
    public void setPonum(String ponum) {
        this.ponum = ponum;
    }

    /**
     * @return the trucknme
     */
    public String getTrucknme() {
        return trucknme;
    }

    /**
     * @param trucknme the trucknme to set
     */
    public void setTrucknme(String trucknme) {
        this.trucknme = trucknme;
    }

    /**
     * @return the drvrnme
     */
    public String getDrvrnme() {
        return drvrnme;
    }

    /**
     * @param drvrnme the drvrnme to set
     */
    public void setDrvrnme(String drvrnme) {
        this.drvrnme = drvrnme;
    }

    /**
     * @return the stdesc
     */
    public String getStdesc() {
        return stdesc;
    }

    /**
     * @param stdesc the stdesc to set
     */
    public void setStdesc(String stdesc) {
        this.stdesc = stdesc;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @param plate the plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
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
     * @return the items2
     */
    public ArrayList<InventoryModel> getItems2() {
        return items2;
    }

    /**
     * @param items2 the items2 to set
     */
    public void setItems2(ArrayList<InventoryModel> items2) {
        this.items2 = items2;
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
    
}
