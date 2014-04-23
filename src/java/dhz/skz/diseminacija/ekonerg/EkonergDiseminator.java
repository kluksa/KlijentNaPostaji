/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.diseminacija.ekonerg;

import dhz.skz.diseminacija.DiseminatorPodataka;
import dhz.skz.aqdb.entity.Komponenta;
import dhz.skz.aqdb.entity.Podatak;
import dhz.skz.aqdb.entity.PrimateljiPodataka;
import dhz.skz.aqdb.entity.ProgramMjerenja;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author kraljevic
 */
@Stateless
@LocalBean
public class EkonergDiseminator implements DiseminatorPodataka{

    @Override
    public void salji(PrimateljiPodataka primatelj) {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "automat");
        connectionProps.put("password", "pasvord@auto");
        String conStr = "jdbc:" + primatelj.getUrl();
//        
//        try (Connection con = DriverManager.getConnection(conStr);
//            for ( ProgramMjerenja pm : primatelj.getProgramMjerenjaCollection()) {
////                Podatak p = getZadnjiPodatakZa
//                
//                
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(EkonergDiseminator.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void salji(PrimateljiPodataka primatelj, Map<ProgramMjerenja, Podatak> zadnjiPodatak) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
    
}
    