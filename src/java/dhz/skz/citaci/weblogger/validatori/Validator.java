/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.citaci.weblogger.validatori;

import wlcitac.exceptions.NevaljanStatusException;
import wlcitac.util.Status;

/**
 *
 * @author kraljevic
 */
public interface Validator {

    Integer getBrojMjerenjaUSatu();
    
    void setBrojMjerenjaUSatu(Integer n);

    Status getStatus(Float iznos, String statusStr, float temperatura) throws NevaljanStatusException;
    
}
