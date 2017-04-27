/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Steven
 */
public class CustomerModel {
    private String idcustomer;
    private String company;
    private String tin;
    private String businessstyle;
    private String address;
    private int postal_code;
    private String paymentterm;
    private double discount;
    private String uom;
    private boolean auto_create;
    private double VAT;
    private String vendor_code;
    
    
    public CustomerModel(ResultSet customer){
        try {
            while(customer.next()){
                idcustomer = customer.getInt(1) + "";
                company = customer.getString(2);
                tin = customer.getString(3);
                address = customer.getString(4);
                
                System.out.println(idcustomer);
            }
        } catch (SQLException ex) {
            System.out.println("Customer Model ResultSet Exception!");
        }
    }
    
    public CustomerModel(){
        
    }
    
    public CustomerModel(String idcustomer, String company, String tin, String address){
        this.idcustomer = idcustomer;
        this.company = company;
        this.tin = tin;
        this.address = address;
    }

    public String getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(String idcustomer) {
        this.idcustomer = idcustomer;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the businessstyle
     */
    public String getBusinessstyle() {
        return businessstyle;
    }

    /**
     * @param businessstyle the businessstyle to set
     */
    public void setBusinessstyle(String businessstyle) {
        this.businessstyle = businessstyle;
    }

    /**
     * @return the postal_code
     */
    public int getPostal_code() {
        return postal_code;
    }

    /**
     * @param postal_code the postal_code to set
     */
    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * @return the paymentterm
     */
    public String getPaymentterm() {
        return paymentterm;
    }

    /**
     * @param paymentterm the paymentterm to set
     */
    public void setPaymentterm(String paymentterm) {
        this.paymentterm = paymentterm;
    }

    /**
     * @return the discount
     */
    public Double getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(Double discount) {
        this.discount = discount;
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
     * @return the auto_create
     */
    public boolean isAuto_create() {
        return auto_create;
    }

    /**
     * @param auto_create the auto_create to set
     */
    public void setAuto_create(boolean auto_create) {
        this.auto_create = auto_create;
    }

    /**
     * @return the VAT
     */
    public Double getVAT() {
        return VAT;
    }

    /**
     * @param VAT the VAT to set
     */
    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    /**
     * @return the vendor_code
     */
    public String getVendor_code() {
        return vendor_code;
    }

    /**
     * @param vendor_code the vendor_code to set
     */
    public void setVendor_code(String vendor_code) {
        this.vendor_code = vendor_code;
    }
    
}
