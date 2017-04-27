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
public class WHSModel {
    
    private String whsmod;
    
    public WHSModel(String whsmod){
        this.whsmod = whsmod;
    }

    /**
     * @return the whsmod
     */
    public String getWhsmod() {
        return whsmod;
    }

    /**
     * @param whsmod the whsmod to set
     */
    public void setWhsmod(String whsmod) {
        this.whsmod = whsmod;
    }
    
}
