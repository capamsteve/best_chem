/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

/**
 *
 * @author Steven
 */
public class DRItemViewModel {
    
    private int idsoitem;
    private String sku;
    private String skudesc;
    private int ordrqty;
    private int deliveryqty;
    private int qtyremaining;
    private String uom;

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
     * @return the ordrqty
     */
    public int getOrdrqty() {
        return ordrqty;
    }

    /**
     * @param ordrqty the ordrqty to set
     */
    public void setOrdrqty(int ordrqty) {
        this.ordrqty = ordrqty;
    }

    /**
     * @return the deliveryqty
     */
    public int getDeliveryqty() {
        return deliveryqty;
    }

    /**
     * @param deliveryqty the deliveryqty to set
     */
    public void setDeliveryqty(int deliveryqty) {
        this.deliveryqty = deliveryqty;
    }

    /**
     * @return the qtyremaining
     */
    public int getQtyremaining() {
        return qtyremaining;
    }

    /**
     * @param qtyremaining the qtyremaining to set
     */
    public void setQtyremaining(int qtyremaining) {
        this.qtyremaining = qtyremaining;
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
     * @return the idsoitem
     */
    public int getIdsoitem() {
        return idsoitem;
    }

    /**
     * @param idsoitem the idsoitem to set
     */
    public void setIdsoitem(int idsoitem) {
        this.idsoitem = idsoitem;
    }
    
}
