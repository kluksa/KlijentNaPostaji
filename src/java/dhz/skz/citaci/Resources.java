/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.citaci;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



/**
 *
 * @author kraljevic
 */
@RequestScoped
public class Resources {
    @SuppressWarnings("unused")
    @Produces
    @PersistenceContext(unitName = "CitacModulPU")
    @LikzDB
    private EntityManager em;           
    
}
