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
public class DRItemsModel {
    
    private int dritemid;
    private int drordrid;
    private int siditem;
    private int drqty;
    
    public DRItemsModel(){
        
    }
    
    /**
     * @return the dritemid
     */
    public int getDritemid() {
        return dritemid;
    }

    /**
     * @param dritemid the dritemid to set
     */
    public void setDritemid(int dritemid) {
        this.dritemid = dritemid;
    }

    /**
     * @return the siditem
     */
    public int getSiditem() {
        return siditem;
    }

    /**
     * @param siditem the siditem to set
     */
    public void setSiditem(int siditem) {
        this.siditem = siditem;
    }

    /**
     * @return the drqty
     */
    public int getDrqty() {
        return drqty;
    }

    /**
     * @param drqty the drqty to set
     */
    public void setDrqty(int drqty) {
        this.drqty = drqty;
    }

    /**
     * @return the drordrid
     */
    public int getDrordrid() {
        return drordrid;
    }

    /**
     * @param drordrid the drordrid to set
     */
    public void setDrordrid(int drordrid) {
        this.drordrid = drordrid;
    }
    
}
