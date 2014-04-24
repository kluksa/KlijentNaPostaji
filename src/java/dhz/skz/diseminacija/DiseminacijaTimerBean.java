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
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author kraljevic
 */
@Singleton
public class DiseminacijaTimerBean implements DiseminacijaTimerBeanRemote{
    
    private static final Logger log = Logger.getLogger(DiseminacijaTimerBean.class.getName());
    
    @EJB
    private PodatakFacade dao;
    
//    @Schedule(minute = "49", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    @Override
    public void pokreniDiseminaciju() {
        log.log(Level.INFO, "DISEMINACIJA");
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
