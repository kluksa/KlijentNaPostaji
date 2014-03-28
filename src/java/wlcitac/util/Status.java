/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wlcitac.util;

import wlcitac.util.Flag;

/**
 *
 * @author kraljevic
 */
public class Status {
    private int status;

    public int getStatus() {
        return status;
    }
    
    public void dodajStatus(Status s) {
        this.status |= s.getStatus();
    }
    
    public void iskljuciStatus(Status s) {
        this.status &= ~s.getStatus();
    }


    public void dodajFlag(Flag s) {
        this.status |= 1 << s.ordinal();
    }


    public void iskljuciFlag(Flag s)  {
        this.status &= ~1 << (s.ordinal()-1);
    }

    public boolean isValid() {
        return status < Flag.NEVALJAN;
    }
    
    public Status() {
        status = 0;
    }
}
