/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package best_chem;

import models.UserModel;

/**
 *
 * @author Steven
 */
public abstract class AbstractController {
    
    private UserModel globalUser;

    /**
     * @return the globalUser
     */
    public UserModel getGlobalUser() {
        return globalUser;
    }

    /**
     * @param globalUser the globalUser to set
     */
    public void setGlobalUser(UserModel globalUser) {
        this.globalUser = globalUser;
    }
    
    public abstract void initData(UserModel user);
    
    
    
}
