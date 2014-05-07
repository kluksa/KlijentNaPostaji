/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji.citac;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.XMLGregorianCalendar;
import klijentnapostaji.webservice.CsvOmotnica;
import klijentnapostaji.webservice.PrihvatSirovihPodataka;

/**
 *
 * @author kraljevic
 */
public class FileTicker implements Runnable {

    private static final Logger log = Logger.getLogger(FileTicker.class.getName());

    private CitacDatoteke citac;
    private PrihvatSirovihPodataka servis;
    private ServisWrapper servisW;
    
    

    @Override
    public void run() {
        final Date start = new Date();
        String str = "Ticker pokrenut";
        log.info(str);
        XMLGregorianCalendar vrijemeZadnjeg = servis.getVrijemeZadnjeg(str, str, str);
        Date zadnji;
        for (File datoteka : getDatoteke(zadnji)) {
            citac.setDatoteka(datoteka);
            String[] headeri = citac.getHeaderi();
            List<String[]> linije = citac.getLinije();
            
        }
        
        
        try {
            dbm.nadjiVrijemeDatoteke(datoteka);
            log.log(Level.INFO, "AAA {0}", datoteka.getZadnjeVrijeme().toString());
            FileInputStream fis = new FileInputStream(datoteka.getFilename());
            parser.parse(fis);
            datoteka.setParametri(parser.getParametri());
            dbm.spremiVrijemeDatoteke(datoteka);
            log.log(Level.INFO, "OOO {0}", datoteka.getZadnjeVrijeme().toString());
            dbm.spremiMjerenja(datoteka);
            dbm.close();
            fis.close();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        final Date end = new Date();
        str = "Ticker zavrsio za " + (end.getTime() - start.getTime()) + "ms";
        log.info(str);
    }

    public FileParser getParser() {
        return parser;
    }

    public void setParser(FileParser parser) {
        this.parser = parser;
    }

    private Iterable<File> getDatoteke(Date zadnji) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
