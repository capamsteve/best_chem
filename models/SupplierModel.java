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
public class SupplierModel {
    
    private int supid;
    private String supname;
    private String suptin;
    private String supaddress;
    private String supbustyp;
    private String suppymttrm;
    
    private ArrayList<SupplierContactModel> scm;
    
    public SupplierModel(){
        scm = new ArrayList();
    }

    /**
     * @return the supid
     */
    public int getSupid() {
        return supid;
    }

    /**
     * @param supid the supid to set
     */
    public void setSupid(int supid) {
        this.supid = supid;
    }

    /**
     * @return the supname
     */
    public String getSupname() {
        return supname;
    }

    /**
     * @param supname the supname to set
     */
    public void setSupname(String supname) {
        this.supname = supname;
    }

    /**
     * @return the suptin
     */
    public String getSuptin() {
        return suptin;
    }

    /**
     * @param suptin the suptin to set
     */
    public void setSuptin(String suptin) {
        this.suptin = suptin;
    }

    /**
     * @return the supaddress
     */
    public String getSupaddress() {
        return supaddress;
    }

    /**
     * @param supaddress the supaddress to set
     */
    public void setSupaddress(String supaddress) {
        this.supaddress = supaddress;
    }

    /**
     * @return the supbustyp
     */
    public String getSupbustyp() {
        return supbustyp;
    }

    /**
     * @param supbustyp the supbustyp to set
     */
    public void setSupbustyp(String supbustyp) {
        this.supbustyp = supbustyp;
    }

    /**
     * @return the suppymttrm
     */
    public String getSuppymttrm() {
        return suppymttrm;
    }

    /**
     * @param suppymttrm the suppymttrm to set
     */
    public void setSuppymttrm(String suppymttrm) {
        this.suppymttrm = suppymttrm;
    }

    /**
     * @return the scm
     */
    public ArrayList<SupplierContactModel> getScm() {
        return scm;
    }

    /**
     * @param scm the scm to set
     */
    public void setScm(ArrayList<SupplierContactModel> scm) {
        this.scm = scm;
    }
    
}
