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
public class DRViewModel {
    
    private Integer drnum;
    private String drdate;
    private String prnt;
    private String pgi;
    private String status;
    private int siitem_id;
    
    public DRViewModel(int drnum){
        this.drnum = drnum;
    }
    
    /**
     * @return the drnum
     */
    public int getDrnum() {
        return drnum;
    }
    
    public Integer getId() {
        return drnum;
    }

    /**
     * @param drnum the drnum to set
     */
    public void setDrnum(int drnum) {
        this.drnum = drnum;
    }

    /**
     * @return the drdate
     */
    public String getDrdate() {
        return drdate;
    }

    /**
     * @param drdate the drdate to set
     */
    public void setDrdate(String drdate) {
        this.drdate = drdate;
    }

    /**
     * @return the prnt
     */
    public String getPrnt() {
        return prnt;
    }

    /**
     * @param prnt the prnt to set
     */
    public void setPrnt(String prnt) {
        this.prnt = prnt;
    }

    /**
     * @return the pgi
     */
    public String getPgi() {
        return pgi;
    }

    /**
     * @param pgi the pgi to set
     */
    public void setPgi(String pgi) {
        this.pgi = pgi;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DRViewModel)) {
            return false;
        }
        DRViewModel other = (DRViewModel) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    /**
     * @return the siitem_id
     */
    public int getSiitem_id() {
        return siitem_id;
    }

    /**
     * @param siitem_id the siitem_id to set
     */
    public void setSiitem_id(int siitem_id) {
        this.siitem_id = siitem_id;
    }
    
}
