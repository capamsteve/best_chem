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
public class SalesOrderModel {
    
    private Integer soid;
    private String customerpo;
    private int custid;
    private Date sodate;
    private Date deliverydate;
    private String status;
    
    private ArrayList<SOItemModel> soItems;
    
    public SalesOrderModel(){
        this.soItems = new ArrayList();
    }

    /**
     * @return the soid
     */
    public int getSoid() {
        return soid;
    }

    /**
     * @param soid the soid to set
     */
    public void setSoid(int soid) {
        this.soid = soid;
    }

    /**
     * @return the customerpo
     */
    public String getCustomerpo() {
        return customerpo;
    }

    /**
     * @param customerpo the customerpo to set
     */
    public void setCustomerpo(String customerpo) {
        this.customerpo = customerpo;
    }

    /**
     * @return the custid
     */
    public int getCustid() {
        return custid;
    }

    /**
     * @param custid the custid to set
     */
    public void setCustid(int custid) {
        this.custid = custid;
    }

    /**
     * @return the sodate
     */
    public Date getSodate() {
        return sodate;
    }

    /**
     * @param sodate the sodate to set
     */
    public void setSodate(Date sodate) {
        this.sodate = sodate;
    }

    /**
     * @return the deliverydate
     */
    public Date getDeliverydate() {
        return deliverydate;
    }

    /**
     * @param deliverydate the deliverydate to set
     */
    public void setDeliverydate(Date deliverydate) {
        this.deliverydate = deliverydate;
    }

    /**
     * @return the soItems
     */
    public ArrayList<SOItemModel> getSoItems() {
        return soItems;
    }

    /**
     * @param soItems the soItems to set
     */
    public void setSoItems(ArrayList<SOItemModel> soItems) {
        this.soItems = soItems;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
