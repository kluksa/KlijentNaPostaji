/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.diseminacija.ekonerg;

import dhz.skz.diseminacija.DiseminatorPodataka;
import dhz.skz.aqdb.entity.Komponenta;
import dhz.skz.aqdb.entity.Podatak;
import dhz.skz.aqdb.entity.PrimateljProgramKljuceviMap;
import dhz.skz.aqdb.entity.PrimateljiPodataka;
import dhz.skz.aqdb.entity.ProgramMjerenja;
import dhz.skz.aqdb.facades.PodatakFacade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author kraljevic
 */
@Stateless
@LocalBean
public class EkonergDiseminator implements DiseminatorPodataka {

    private static final Logger log = Logger.getLogger(EkonergDiseminator.class.getName());
    private PrimateljiPodataka primatelj;
    private Connection con;
    @EJB
    private PodatakFacade dao;

    @Override
    public void salji(PrimateljiPodataka primatelj) {
        this.primatelj = primatelj;

        Date vrijeme = new Date(new Date().getTime() - 7600000L);
        try {
            otvoriKonekciju();
            for ( PrimateljProgramKljuceviMap pm : primatelj.getPrimateljProgramKljuceviMapCollection()) {
                Podatak p = dao.getPodatakNakonVremena(pm.getProgramMjerenja(), vrijeme);
                spremi(pm, p);
            }
            zatvoriKonekciju();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "", ex);
        }
    }

    @Override
    public void salji(PrimateljiPodataka primatelj, Map<ProgramMjerenja, Podatak> zadnjiPodatak) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    private void otvoriKonekciju() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "automat");
        connectionProps.put("password", "pasvord@auto");
        String conStr = "jdbc:" + primatelj.getUrl();
        log.log(Level.INFO, conStr);
        con = DriverManager.getConnection(conStr, connectionProps);
    }

    private void spremi(PrimateljProgramKljuceviMap pm, Podatak p) {
        log.log(Level.INFO, "Spremam {0}:::{1}::{2}::{3}::{4}", new Object[]{p.getVrijeme(), 
        p.getProgramMjerenjaId().getId(), pm.getPKljuc(), pm.getKKljuc(), p.getVrijednost()});
    }

    private void zatvoriKonekciju() {
        try {
            con.close();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }


}
