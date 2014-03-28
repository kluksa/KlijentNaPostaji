/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wlcitac.util;

import dhz.skz.likz.aqdb.entity.Podatak;


/**
 *
 * @author kraljevic
 */
public class PodatakWl extends Podatak {
    
    public void dodajStatus(Flag s){
        int st = getStatus() | (1 << s.ordinal());
        setStatus(st);
    }

    public void dodajStatus(int s){
        int st = getStatus() | s;
        setStatus(st);
    }
    
    public void postaviStatus(Status s){
        setStatus(s.getStatus());
    }
    
    public boolean isValid() {
        return getStatus() < Flag.NEVALJAN;
    }

}
