/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transmittal;

/**
 *
 * @author Steven
 */
class BMPMVal {
    
    private String WH;
    private int val;
    
    public BMPMVal(int val, String wh){
        this.val = val;
        this.WH = wh;
    }

    /**
     * @return the WH
     */
    public String getWH() {
        return WH;
    }

    /**
     * @param WH the WH to set
     */
    public void setWH(String WH) {
        this.WH = WH;
    }

    /**
     * @return the val
     */
    public int getVal() {
        return val;
    }

    /**
     * @param val the val to set
     */
    public void setVal(int val) {
        this.val = val;
    }
    
}
