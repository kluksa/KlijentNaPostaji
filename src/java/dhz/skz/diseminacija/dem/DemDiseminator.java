/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.diseminacija.dem;

import dhz.skz.aqdb.facades.PodatakFacade;
import dhz.skz.diseminacija.DiseminatorPodataka;
import dhz.skz.diseminacija.datatransfer.DataTransfer;
import dhz.skz.diseminacija.datatransfer.DataTransferFactory;
import dhz.skz.diseminacija.datatransfer.exceptions.ProtocolNotSupported;
import dhz.skz.aqdb.entity.Komponenta;
import dhz.skz.aqdb.entity.NivoValidacije;
import dhz.skz.aqdb.entity.Podatak;
import dhz.skz.aqdb.entity.PrimateljiPodataka;
import dhz.skz.aqdb.entity.ProgramMjerenja;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TimeZone;
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
public class DemDiseminator implements DiseminatorPodataka {

    @EJB
    private PodatakFacade dao;
    
    @Override
    public void salji(PrimateljiPodataka primatelj, Map<ProgramMjerenja, Podatak> zadnjiPodatak) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void salji(PrimateljiPodataka primatelj) {
        Map<Komponenta, Collection<ProgramMjerenja>> programPoKomponentama = 
                getProgramPoKomponentama(primatelj.getProgramMjerenjaCollection());
        
        DEMTransformation demT = new DEMTransformation(primatelj);
        NivoValidacije nv = new NivoValidacije((short)0);
        Date zadnji = getZadnji();
        
        for (Komponenta k : programPoKomponentama.keySet()) {
            try {
                DataTransfer dto = DataTransferFactory.getTransferObj(primatelj);
                Collection<Podatak> podaci = dao.getPodaciZaKomponentu(zadnji, zadnji, k,nv, (short)0);
                demT.setKomponenta(k);
                demT.setProgram(programPoKomponentama.get(k));
                demT.setPocetak(zadnji);
                demT.setKraj(zadnji);
                demT.setPodaci(podaci);
                demT.odradi(dto);
                
            } catch (ProtocolNotSupported ex) {
                Logger.getLogger(DemDiseminator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Map<Komponenta, Collection<ProgramMjerenja>> getProgramPoKomponentama(Collection<ProgramMjerenja> program) {
        Map<Komponenta, Collection<ProgramMjerenja>> kpm = new HashMap<>();
        for (ProgramMjerenja pm : program) {
            Komponenta k = pm.getKomponentaId();
            if (!kpm.containsKey(k)) {
                kpm.put(k, new HashSet<ProgramMjerenja>());
            }
            kpm.get(k).add(pm);
        }
        return kpm;
    }

    private Date getZadnji() {
        Calendar trenutni_termin = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        trenutni_termin.setTime(new Date());
        trenutni_termin.set(Calendar.MINUTE, 0);
        trenutni_termin.set(Calendar.SECOND, 0);
        trenutni_termin.set(Calendar.MILLISECOND, 0);
        trenutni_termin.getTime();
        return trenutni_termin.getTime();
    }
}
