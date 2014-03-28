/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.citaci.weblogger;

import dhz.skz.citaci.FtpKlijent;
import dhz.skz.likz.aqdb.entity.IzvorPodataka;
import dhz.skz.likz.aqdb.entity.Postaja;
import dhz.skz.likz.aqdb.entity.ProgramMjerenja;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.apache.commons.net.ftp.FTPFile;
import wlcitac.NizPodataka;
import wlcitac.WlDatotekaParser;
import wlcitac.WlFileFilter;
import wlcitac.exceptions.FtpKlijentException;

/**
 *
 * @author kraljevic
 */
@Stateless
@LocalBean
public class WlCitacPostajeBean {

    private static final Logger log = Logger.getLogger(WlCitacPostajeBean.class.getName());
    private final TimeZone timeZone = TimeZone.getTimeZone("UTC");

    @EJB
    private FtpKlijent ftp;

    public void procitajPostaju(IzvorPodataka izvor, Postaja p, Date zadnji, 
            Map<ProgramMjerenja, NizPodataka> nizovi) throws FtpKlijentException, IOException, URISyntaxException {
        URI izvorUri = new URI(izvor.getUri());
        ftp.setUri(izvorUri);
        ftp.connect();
        try {

            WlDatotekaParser citac = new WlDatotekaParser(timeZone);
            citac.setZadnjiPodatak(zadnji);
            citac.setNizKanala(nizovi);

            WlFileFilter fns = new WlFileFilter(p.getNazivPostaje(), zadnji, timeZone);

            for (FTPFile file : ftp.getFileList(fns)) {
                log.log(Level.INFO, "Datoteka :{0}", file.getName());
                try (InputStream ifs = ftp.getFileStream(file)) {
                    citac.parse(ifs);
// obradi
                } catch (Exception ex) {
                    log.log(Level.SEVERE, null, ex);
                } finally {
                    ftp.zavrsi();
                }
            }
        } finally {
            ftp.disconnect();
        }
    }
}
