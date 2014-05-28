/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji.citac;

import klijentnapostaji.webservice.PrihvatServisLocalImpl;
import klijentnapostaji.citac.filelistgeneratori.FileListGenerator;
import klijentnapostaji.citac.exceptions.ParserException;
import klijentnapostaji.citac.exceptions.PrihvatWSException;
import com.csvreader.CsvReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import klijentnapostaji.webservice.CsvOmotnica;

/**
 *
 * @author kraljevic
 */
public class CsvFileTicker implements Runnable {

    private static final Logger log = Logger.getLogger(CsvFileTicker.class.getName());

    private PrihvatServisLocalImpl servis;
    private FileListGenerator fileListGen;
    private CsvOmotnicaBuilder csvOBuilder;
    private Integer[] stupciSaVremenom;
    private DateFormat dateFormat;
    private char separator;
    private Charset chareset = Charset.forName("UTF-8");

    private String[] headeri;
    private List<String[]> linije;
    private List<Long> vremena;
    private int brojStupaca;
    private Date trenutnoVrijeme;

    public CsvFileTicker() {
    }

    public CsvFileTicker(PrihvatServisLocalImpl servis, FileListGenerator fileListGen, CsvOmotnicaBuilder csvOBuilder, Integer[] stupciSaVremenom, DateFormat dateFormat, char separator) {
        this.servis = servis;
        this.fileListGen = fileListGen;
        this.csvOBuilder = csvOBuilder;
        this.stupciSaVremenom = stupciSaVremenom;
        this.dateFormat = dateFormat;
        this.separator = separator;
    }

    public void setChareset(Charset chareset) {
        this.chareset = chareset;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public void setServis(PrihvatServisLocalImpl servis) {
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
        Date zadnji = servis.getVrijemeZadnjeg(csvOBuilder.getIzvor(), csvOBuilder.getPostaja(), csvOBuilder.getDatoteka());
        for (File datoteka : fileListGen.getFileList(zadnji)) {
            try {
                procitaj(datoteka, zadnji);
                CsvOmotnica create = csvOBuilder.create(headeri, linije, vremena);
                servis.prebaciOmotnicu(create);
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            } catch (ParserException ex) {
                log.log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                log.log(Level.SEVERE, null, ex);
            } catch (PrihvatWSException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }

        final Date end = new Date();
        str = "Ticker zavrsio za " + (end.getTime() - start.getTime()) + "ms";
        log.info(str);
    }

    private void procitaj(File datoteka, Date zadnji) throws ParserException, IOException, ParseException {
        log.log(Level.FINE, "Datoteka: {0}, zadnji: {1}", new Object[]{datoteka.getAbsoluteFile(), zadnji});

        CsvReader csv = new CsvReader(datoteka.getCanonicalPath(), separator, chareset);
        try {
            csv.readHeaders();
            brojStupaca = csv.getHeaderCount();
            headeri = csv.getHeaders();
            linije = new ArrayList<>();
            vremena = new ArrayList<>();

            while (csv.readRecord()) {
                int nc = csv.getColumnCount();
                if (brojStupaca != nc) {
                    log.log(Level.SEVERE, "Promijenio se broj stupaca kod zapisa {0}", csv.getRawRecord());
                    throw new ParserException();
                }
                trenutnoVrijeme = getVrijeme(csv);
                if (trenutnoVrijeme.after(zadnji)) {
                    vremena.add(trenutnoVrijeme.getTime());
                    linije.add(csv.getValues());
                }
            }
        } finally {
            csv.close();
        }
    }

    public void setStupciSaVremenom(Integer[] stupciSaVremenom) {
        this.stupciSaVremenom = stupciSaVremenom;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    private Date getVrijeme(CsvReader csv) throws IOException, ParseException {
        String vrijemeStr = "";
        for (Integer i : stupciSaVremenom) {
            vrijemeStr += csv.get(i) + " ";
        }
        return dateFormat.parse(vrijemeStr);
    }

}
