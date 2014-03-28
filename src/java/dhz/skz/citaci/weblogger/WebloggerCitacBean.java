/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.citaci.weblogger;

import dhz.skz.aqdb.facades.PodatakFacade;
import dhz.skz.citaci.CitacIzvora;
import dhz.skz.citaci.FtpKlijent;
import dhz.skz.citaci.LikzDB;
import dhz.skz.citaci.weblogger.validatori.Validator;
import dhz.skz.citaci.weblogger.validatori.ValidatorFactory;
import dhz.skz.likz.aqdb.entity.IzvorPodataka;
import dhz.skz.likz.aqdb.entity.Podatak;
import dhz.skz.likz.aqdb.entity.Postaja;
import dhz.skz.likz.aqdb.entity.ProgramMjerenja;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import org.apache.commons.net.ftp.FTPFile;
import wlcitac.NizPodataka;
import wlcitac.SatniAgregator;
import wlcitac.WlDatotekaParser;
import wlcitac.WlFileFilter;
import wlcitac.exceptions.AgregatorException;
import wlcitac.exceptions.FtpKlijentException;
import wlcitac.exceptions.IzvorPodatakaException;

/**
 *
 * @author kraljevic
 */
@Stateless
@LocalBean
public class WebloggerCitacBean implements CitacIzvora {

    private static final Logger log = Logger.getLogger(WebloggerCitacBean.class.getName());
    private final TimeZone timeZone = TimeZone.getTimeZone("UTC");
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm z");

    private Map<ProgramMjerenja, NizPodataka> nizoviPodataka;
    private Map<Postaja, Map<ProgramMjerenja, NizPodataka>> nizoviPoPostajama;
    private Map<Postaja, Podatak> zadnjiPodatakPoPostaji;

    @EJB
    private FtpKlijent ftp;

    @EJB
    private PodatakFacade podatakDAO;

    @Inject
    @LikzDB
    private EntityManager em;

    @EJB
    private ValidatorFactory validatorFac;

    public WebloggerCitacBean() {
        sdf.setTimeZone(timeZone);
    }

    @Override
    public Map<ProgramMjerenja, NizPodataka> procitaj(IzvorPodataka izvor, Map<ProgramMjerenja, Podatak> pocetak) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public Map<ProgramMjerenja, NizPodataka> procitaj(IzvorPodataka izvor) {
        try {
            init(izvor);
            for (Postaja p : nizoviPoPostajama.keySet()) {
                ftp.connect();
                Date zadnji = getZadnjeVrijemeNaPostaji(p);
                Map<ProgramMjerenja, NizPodataka> nizovi = nizoviPoPostajama.get(p);

                WlDatotekaParser citac = new WlDatotekaParser(timeZone);
                citac.setZadnjiPodatak(zadnji);
                citac.setNizKanala(nizovi);

                WlFileFilter fns = new WlFileFilter(p.getNazivPostaje(), zadnji, timeZone);

                for (FTPFile file : ftp.getFileList(fns)) {
                    log.log(Level.INFO, "Datoteka :{0}", file.getName());
                    try (InputStream ifs = ftp.getFileStream(file)) {
                        citac.parse(ifs);
                    } catch (Exception ex) {
                        log.log(Level.SEVERE, null, ex);
                    } finally {
                        ftp.zavrsi();
                    }
                }
            }
            obradiNizove();
        } catch (IzvorPodatakaException | URISyntaxException | AgregatorException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (IOException | FtpKlijentException | NamingException ex) {
            log.log(Level.SEVERE, null, ex);
        } finally {
            ftp.disconnect();
        }
        return null;
    }

    private void init(IzvorPodataka izvor) throws IzvorPodatakaException, URISyntaxException, NamingException {
        log.log(Level.INFO, "init poceo");
        URI izvorUri = new URI(izvor.getUri());
        ftp.setUri(izvorUri);
        em.refresh(izvor);

        nizoviPoPostajama = new HashMap<>();
        nizoviPodataka = new HashMap<>();
        for (ProgramMjerenja pm : izvor.getProgramMjerenjaCollection()) {
            ProgramMjerenja kljuc = pm;

            if (!nizoviPoPostajama.containsKey(pm.getPostajaId())) {
                nizoviPoPostajama.put(pm.getPostajaId(), new HashMap<ProgramMjerenja, NizPodataka>());
            }
            Map<ProgramMjerenja, NizPodataka> set = nizoviPoPostajama.get(pm.getPostajaId());
            NizPodataka np = new NizPodataka(kljuc);
            NavigableMap<Date, Validator> val = validatorFac.getValidatori(pm);
            np.setValidatori(val);
            nizoviPodataka.put(pm, np);
            set.put(kljuc, np);
        }
        this.initZadnjiPodatak();
        log.log(Level.INFO, "init zavrsio");

    }

    private void obradiNizove() throws AgregatorException {
        for (ProgramMjerenja p : nizoviPodataka.keySet()) {
            NizPodataka niz = nizoviPodataka.get(p);
            if (!niz.getPodaci().isEmpty()) {
                SatniAgregator a = new SatniAgregator();
                a.setNeagregiraniNiz(niz);
                a.agregiraj();
                NizPodataka agr = a.getAgregiraniNiz();
                nizoviPodataka.put(p, agr);
            }
        }
    }

    public void initZadnjiPodatak() {
        zadnjiPodatakPoPostaji = new HashMap<>();
        for (Entry<ProgramMjerenja, Podatak> e : podatakDAO.getZadnjiPodatakPoProgramu().entrySet()) {
            Postaja p = e.getKey().getPostajaId();
            Podatak pod = e.getValue();
            if (zadnjiPodatakPoPostaji.containsKey(p)) {
                if (pod.getVrijeme().after(zadnjiPodatakPoPostaji.get(p).getVrijeme())) {
                    zadnjiPodatakPoPostaji.put(p, pod);
                }
            }
        }
    }

    private Date getZadnjeVrijemeNaPostaji(Postaja p) {
        Podatak pod = zadnjiPodatakPoPostaji.get(p);
        Date zadnji = new Date(0);
        if (pod != null) {
            zadnji = zadnjiPodatakPoPostaji.get(p).getVrijeme();
        }
        return zadnji;
    }
}
