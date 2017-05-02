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
public class ReturnsModel {
    
    private int idreturns;
    private String sku;
    private String skudesc;
    private String retuom;
    private String retwhs;
    private int soh;
    private String mov;

    /**
     * @return the idreturns
     */
    public int getIdreturns() {
        return idreturns;
    }

    /**
     * @param idreturns the idreturns to set
     */
    public void setIdreturns(int idreturns) {
        this.idreturns = idreturns;
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
     * @return the retuom
     */
    public String getRetuom() {
        return retuom;
    }

    /**
     * @param retuom the retuom to set
     */
    public void setRetuom(String retuom) {
        this.retuom = retuom;
    }

    /**
     * @return the retwhs
     */
    public String getRetwhs() {
        return retwhs;
    }

    /**
     * @param retwhs the retwhs to set
     */
    public void setRetwhs(String retwhs) {
        this.retwhs = retwhs;
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
     * @return the mov
     */
    public String getMov() {
        return mov;
    }

    /**
     * @param mov the mov to set
     */
    public void setMov(String mov) {
        this.mov = mov;
    }
    
}
