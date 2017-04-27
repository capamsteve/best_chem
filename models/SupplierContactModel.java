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
public class SupplierContactModel {
    
    private int cid;
    private String supcname;
    private String contact;
    private String supemail;
    private int supid;
    
    public SupplierContactModel(){
        
    }
    
    public SupplierContactModel(String name, String contact, String email){
        this.supcname = name;
        this.contact = contact;
        this.supemail = email;
    }

    /**
     * @return the cid
     */
    public int getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(int cid) {
        this.cid = cid;
    }

    /**
     * @return the supcname
     */
    public String getSupcname() {
        return supcname;
    }

    /**
     * @param supcname the supcname to set
     */
    public void setSupcname(String supcname) {
        this.supcname = supcname;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the supemail
     */
    public String getSupemail() {
        return supemail;
    }

    /**
     * @param supemail the supemail to set
     */
    public void setSupemail(String supemail) {
        this.supemail = supemail;
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
    
}
