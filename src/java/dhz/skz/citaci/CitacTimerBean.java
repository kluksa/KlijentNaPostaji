/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.citaci;

import dhz.skz.aqdb.facades.PodatakFacade;
import dhz.skz.likz.aqdb.entity.IzvorPodataka;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author kraljevic
 */
@Singleton
public class CitacTimerBean {

    private static final Logger log = Logger.getLogger(CitacTimerBean.class.getName());

    @EJB
    private PodatakFacade dao;

    @Schedule(minute = "36", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    public void pokreniCitace() {
        try {
            InitialContext ctx = new InitialContext();
            String str = "java:module/";
            for (IzvorPodataka ip : dao.getAktivniIzvori()) {
                log.log(Level.INFO, "");
                String naziv = str + ip.getBean().trim();
                log.log(Level.INFO, "JNDI: {0}", naziv);
                try {
                    CitacIzvora citac = (CitacIzvora) ctx.lookup(naziv);
                    citac.procitaj(ip);
                } catch (NamingException ex) {
                    log.log(Level.SEVERE, null, ex);
                }
            }
        } catch (NamingException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }
}
