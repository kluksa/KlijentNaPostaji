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
import klijentnapostaji.webservice.CsvPrihvatException_Exception;
import klijentnapostaji.webservice.PrihvatSirovihPodataka;

/**
 *
 * @author kraljevic
 */
public class FileTicker implements Runnable {

    private static final Logger log = Logger.getLogger(FileTicker.class.getName());

    private PrihvatSirovihPodataka servis;
    private FileListGenerator fileListGen;
    private CsvOmotnicaBuilder csvOBuilder;

    public void setServis(PrihvatSirovihPodataka servis) {
        this.servis = servis;
    }

    public void setFileListGen(FileListGenerator fileListGen) {
        this.fileListGen = fileListGen;
    }

    public void setCsvOBuilder(CsvOmotnicaBuilder csvOBuilder) {
        this.csvOBuilder = csvOBuilder;
    }
    
    

    @Override
    public void run() {
        final Date start = new Date();
        String str = "Ticker pokrenut";
        log.info(str);
        XMLGregorianCalendar vrijemeZadnjeg = servis.getVrijemeZadnjeg(str, str, str);
        Date zadnji;
        for (File datoteka : fileListGen.getFileList(str, start)) {
            try {
                procitaj(datoteka);
                
                String[] headeri = getHeaderi(datoteka);
                List<String[]> linije = getLinije(datoteka);
                CsvOmotnica create = csvOBuilder.create(headeri, linije);
                servis.prebaciOmotnicu(create);
            } catch (CsvPrihvatException_Exception ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
        
        
        final Date end = new Date();
        str = "Ticker zavrsio za " + (end.getTime() - start.getTime()) + "ms";
        log.info(str);
    }


    private void procitaj(File datoteka) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String[] getHeaderi(File datoteka) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<String[]> getLinije(File datoteka) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
