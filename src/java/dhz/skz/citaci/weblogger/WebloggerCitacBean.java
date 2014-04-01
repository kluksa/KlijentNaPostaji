/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.citaci.weblogger;

import dhz.skz.aqdb.facades.PodatakFacade;
import dhz.skz.citaci.CitacIzvora;
import dhz.skz.citaci.FtpKlijent;
import dhz.skz.citaci.weblogger.validatori.ValidatorFactory;
import dhz.skz.likz.aqdb.entity.IzvorPodataka;
import dhz.skz.likz.aqdb.entity.Podatak;
import dhz.skz.likz.aqdb.entity.Postaja;
import dhz.skz.likz.aqdb.entity.ProgramMjerenja;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.net.ftp.FTPFile;
import wlcitac.NizPodataka;
import wlcitac.SatniAgregator;
import wlcitac.WlDatotekaParser;
import wlcitac.WlFileFilter;
import wlcitac.exceptions.FtpKlijentException;

/**
 *
 * @author kraljevic
 */
@Stateless
@LocalBean
public class WebloggerCitacBean implements CitacIzvora {

    private static final Logger log = Logger.getLogger(WebloggerCitacBean.class.getName());

    private Map<ProgramMjerenja, NizPodataka> nizoviPodataka;
    private Map<Postaja, Map<ProgramMjerenja, NizPodataka>> nizoviPoPostajama;
    private Map<Postaja, Podatak> zadnjiPodatakPoPostaji;
    private final TimeZone timeZone = TimeZone.getTimeZone("UTC");


    @EJB
    private PodatakFacade dao;
    
    @EJB
    private FtpKlijent ftp;

    @PersistenceContext(unitName = "CitacModulPU")
    private EntityManager em;

    @EJB
    private ValidatorFactory validatorFac;
    
    public WebloggerCitacBean() {
        
    }

    @Override
    public Map<ProgramMjerenja, NizPodataka> procitaj(IzvorPodataka izvor, Map<ProgramMjerenja, Podatak> pocetak) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public Map<ProgramMjerenja, NizPodataka> procitaj(IzvorPodataka izvor) {
            em.refresh(izvor);
            Collection<Postaja> postajeZaIzvor = dao.getPostajeZaIzvor(izvor);
            for (Postaja p : postajeZaIzvor) {
                log.log(Level.INFO,"Citam: {0}", p.getNazivPostaje());
                pokupiPodatkeSaPostaje(izvor, p);
            }
        
        return null;
    }

    private Map<ProgramMjerenja, NizPodataka> getMapaNizova(Postaja p, IzvorPodataka izvor, Date zadnji ) throws URISyntaxException{

        Map<ProgramMjerenja, NizPodataka> tmp = new HashMap<>();
        Collection<ProgramMjerenja> programNaPostajiZaIzvor = dao.getProgramNaPostajiZaIzvor(p, izvor, zadnji);
        for ( ProgramMjerenja pm : programNaPostajiZaIzvor) {
            NizPodataka np = new NizPodataka();
            np.setKljuc(pm);
            np.setValidatori(validatorFac.getValidatori(pm));
            tmp.put(pm, np);
        }
        return tmp;
    }
    
    private void pokupiPodatkeSaPostaje(IzvorPodataka izvor, Postaja p)  {
        Date zadnji = getZadnjiPodatak(izvor, p);
        
        
        try {
            Map<ProgramMjerenja, NizPodataka> tmp = getMapaNizova(p, izvor, zadnji);
            ftp.connect(new URI(izvor.getUri()));
            
            WlDatotekaParser citac = new WlDatotekaParser(timeZone);
            citac.setZadnjiPodatak(zadnji);
            citac.setNizKanala(tmp);

            WlFileFilter fns = new WlFileFilter(p.getNazivPostaje(), zadnji, timeZone);
            for (FTPFile file : ftp.getFileList(fns)) {
                log.log(Level.INFO, "Datoteka :{0}", file.getName());
                try (InputStream ifs = ftp.getFileStream(file)) {
                    citac.parse(ifs);
                } catch (Exception ex) { // kakva god da se iznimka dogodi, nastavljamo
                    log.log(Level.SEVERE, null, ex);
                } finally {
                    ftp.zavrsi();
                }
            }
            obradiISpremiNizove(tmp);
        } catch (FtpKlijentException | URISyntaxException ex) {
            log.log(Level.SEVERE, null, ex);
        } finally {
            ftp.disconnect();
        }
    }

    private void obradiISpremiNizove(Map<ProgramMjerenja, NizPodataka> ulaz) {
        for (ProgramMjerenja p : ulaz.keySet()) {

            NizPodataka niz = ulaz.get(p);
            if (!niz.getPodaci().isEmpty()) {
                try {
                    SatniAgregator a = new SatniAgregator();
                    a.setNeagregiraniNiz(niz);
                    a.agregiraj();
                    NizPodataka agr = a.getAgregiraniNiz();
                    log.log(Level.INFO, "Pospremam Postaja {0}, komponenta {1}", new Object[]{p.getPostajaId().getNazivPostaje(), p.getKomponentaId().getFormula()});
//                    dao.pospremiNiz(agr);
//                    izlazniNiz.put(p, agr);
                    niz.getPodaci().clear();
                    niz.getValidatori().clear();
                } catch (Exception ex) {
                    log.log(Level.SEVERE, "Postaja {0}, komponenta {1}", new Object[]{p.getPostajaId().getNazivPostaje(), p.getKomponentaId().getFormula()});
                    throw ex;
                }
            }
        }
//        return izlazniNiz;
    }

    private Date getZadnjiPodatak(IzvorPodataka izvor, Postaja p) {
        Date zadnji = dao.getVrijemeZadnjegNaPostajiZaIzvor(p, izvor);
        if ( zadnji == null ) {
            zadnji = new Date(0L);
        }
        return zadnji;
    }
    
    //    private void init(IzvorPodataka izvor) throws IzvorPodatakaException, URISyntaxException, NamingException {
//        log.log(Level.INFO, "init poceo");
//        em.refresh(izvor);
//
//        nizoviPoPostajama = new HashMap<>();
//        nizoviPodataka = new HashMap<>();
//        for (ProgramMjerenja pm : izvor.getProgramMjerenjaCollection()) {
//            ProgramMjerenja kljuc = pm;
//
//            if (!nizoviPoPostajama.containsKey(pm.getPostajaId())) {
//                nizoviPoPostajama.put(pm.getPostajaId(), new HashMap<ProgramMjerenja, NizPodataka>());
//            }
//            Map<ProgramMjerenja, NizPodataka> set = nizoviPoPostajama.get(pm.getPostajaId());
//            NizPodataka np = new NizPodataka(kljuc);
////            NavigableMap<Date, Validator> val = validatorFac.getValidatori(pm);
////            np.setValidatori(val);
////            nizoviPodataka.put(pm, np);
//            set.put(kljuc, np);
//        }
////        this.initZadnjiPodatak();
//        log.log(Level.INFO, "init zavrsio");
//
//    }
//
//    private void obradiNizove() throws AgregatorException {
//        for (ProgramMjerenja p : nizoviPodataka.keySet()) {
//            NizPodataka niz = nizoviPodataka.get(p);
//            if (!niz.getPodaci().isEmpty()) {
//                SatniAgregator a = new SatniAgregator();
//                a.setNeagregiraniNiz(niz);
//                a.agregiraj();
//                NizPodataka agr = a.getAgregiraniNiz();
//                nizoviPodataka.put(p, agr);
//            }
//        }
//    }
//
//    public void initZadnjiPodatak() {
//        zadnjiPodatakPoPostaji = new HashMap<>();
//        for (Entry<ProgramMjerenja, Podatak> e : podatakDAO.getZadnjiPodatakPoProgramu().entrySet()) {
//            Postaja p = e.getKey().getPostajaId();
//            Podatak pod = e.getValue();
//            if (zadnjiPodatakPoPostaji.containsKey(p)) {
//                if (pod.getVrijeme().after(zadnjiPodatakPoPostaji.get(p).getVrijeme())) {
//                    zadnjiPodatakPoPostaji.put(p, pod);
//                }
//            }
//        }
//    }
//
//    private Date getZadnjeVrijemeNaPostaji(Postaja p) {
//        Podatak pod = zadnjiPodatakPoPostaji.get(p);
//        Date zadnji = new Date(0L);
//        if (pod != null) {
//            zadnji = zadnjiPodatakPoPostaji.get(p).getVrijeme();
//        }
//        return zadnji;
//    }

}
