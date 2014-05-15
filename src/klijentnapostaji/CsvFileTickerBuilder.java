/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;
import java.util.logging.Logger;
import klijentnapostaji.citac.CsvFileTicker;
import klijentnapostaji.citac.CsvOmotnicaBuilder;
import klijentnapostaji.citac.filelistgeneratori.FileListGenerator;
import klijentnapostaji.citac.filelistgeneratori.MLULoggerFileListGenerator;
import klijentnapostaji.webservice.PrihvatServisLocalImpl;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author kraljevic
 */
public class CsvFileTickerBuilder {

    private static final Logger log = Logger.getLogger(CsvFileTickerBuilder.class.getName());

    public CsvFileTicker create() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(timeZone);

        MLULoggerFileListGenerator mfg = new MLULoggerFileListGenerator();

        CsvOmotnicaBuilder ob = new CsvOmotnicaBuilder();
        ob.setDatoteka(mfg.getFileList(null)[0].getName());
        ob.setIzvor("MLULogger");
        ob.setPostaja("Slavonski brod 2");

        CsvFileTicker f = new CsvFileTicker();
        f.setServis(new PrihvatServisLocalImpl());

        f.setStupciSaVremenom(new Integer[]{0});
        f.setDateFormat(sdf);

        f.setFileListGen(mfg);
        f.setCsvOBuilder(ob);
        return f;
    }

    public CsvFileTicker create(PrihvatServisLocalImpl servis, FileListGenerator fileListGen, CsvOmotnicaBuilder csvOBuilder, Integer[] stupciSaVremenom, DateFormat dateFormat, char separator) {
        return new CsvFileTicker(servis, fileListGen, csvOBuilder, stupciSaVremenom, dateFormat, separator);
    }

}
