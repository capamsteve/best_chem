/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodels;

/**
 *
 * @author Steven
 */
public class SOViewModel {
//    hello.get("idsalesorder");
//    hello.get("sodate");
//    hello.get("status");
//    hello.get("sodeliverydate");
//    hello.get("customerpo");
    
    private int idso;
    private String sodate;
    private String status;
    private String sodrdate;
    private String Customerpo;

    /**
     * @return the idso
     */
    public int getIdso() {
        return idso;
    }

    /**
     * @param idso the idso to set
     */
    public void setIdso(int idso) {
        this.idso = idso;
    }

    /**
     * @return the sodate
     */
    public String getSodate() {
        return sodate;
    }

    /**
     * @param sodate the sodate to set
     */
    public void setSodate(String sodate) {
        this.sodate = sodate;
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

    /**
     * @return the sodrdate
     */
    public String getSodrdate() {
        return sodrdate;
    }

    /**
     * @param sodrdate the sodrdate to set
     */
    public void setSodrdate(String sodrdate) {
        this.sodrdate = sodrdate;
    }

    /**
     * @return the Customerpo
     */
    public String getCustomerpo() {
        return Customerpo;
    }

    /**
     * @param Customerpo the Customerpo to set
     */
    public void setCustomerpo(String Customerpo) {
        this.Customerpo = Customerpo;
    }
    
}
