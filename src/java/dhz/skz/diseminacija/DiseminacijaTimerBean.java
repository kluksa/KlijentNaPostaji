/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.diseminacija;

import dhz.skz.aqdb.facades.PodatakFacade;
import dhz.skz.aqdb.entity.PrimateljiPodataka;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author kraljevic
 */
@Singleton
public class DiseminacijaTimerBean implements DiseminacijaTimerBeanRemote {
    
    private static final Logger log = Logger.getLogger(DiseminacijaTimerBean.class.getName());
    
    @EJB
    private PodatakFacade dao;
    
    @Override
//    @Schedule(minute = "27", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    public void pokreniDiseminaciju() {
                try {
            InitialContext ctx = new InitialContext();
            String str = "java:module/";
            
            for (PrimateljiPodataka pr : dao.getAktivniPrimatelji()) {
                String naziv = str + pr.getTip().trim();
                log.log(Level.INFO, "JNDI: {0}", naziv);
                try {
                    DiseminatorPodataka diseminator = (DiseminatorPodataka) ctx.lookup(naziv);
                    diseminator.salji(pr);
                } catch (NamingException ex) {
                    log.log(Level.SEVERE, null, ex);
                }
            }
        } catch (NamingException ex) {
            log.log(Level.SEVERE, null, ex);
        }

    }
}
