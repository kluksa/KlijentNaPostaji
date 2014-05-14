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
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import klijentnapostaji.citac.CsvFileTicker;
import klijentnapostaji.citac.CsvOmotnicaBuilder;
import klijentnapostaji.citac.filelistgeneratori.FileListGenerator;
import klijentnapostaji.webservice.PrihvatServisLocalImpl;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author kraljevic
 */
public class Konfiguracija {

    private static final Logger log = Logger.getLogger(Konfiguracija.class.getName());

    private final XMLConfiguration config;

    public Konfiguracija(String konfigPath) throws ConfigurationException {
        config = new XMLConfiguration(konfigPath);
    }

    public Collection<CsvFileTicker> getFileTickeri() throws ConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Collection<CsvFileTicker> tikeri = new ArrayList<>();
        PrihvatServisLocalImpl servis = getWebServis();

        String oznakaPostaje = config.getString("postaja");
        for (HierarchicalConfiguration izvor : config.configurationsAt("izvor")) {
            String vrstaIzvora = izvor.getString("[@vrsta]");
            String oznaka = izvor.getString("[@oznaka]");

            for (HierarchicalConfiguration datoteka : izvor.configurationsAt("datoteka")) {

                String dirname = datoteka.getString("[@dirname]");

                String name = datoteka.getString("[@name]");

                String bname = datoteka.getString("[@basename]");

                Boolean rotacija = datoteka.getBoolean("[@rotacija]");

                String oznakaDatoteke;
                if (rotacija) {
                    oznakaDatoteke = bname;
                } else {
                    if (name == null) {
                        throw new ConfigurationException();
                    }
                    oznakaDatoteke = name;
                }

                CsvOmotnicaBuilder csvOB = new CsvOmotnicaBuilder(oznakaDatoteke, oznaka, oznakaPostaje);
                csvOB = CsvOmotnicaBuilder.getOmotnicaBuilderFromConfig(datoteka);
                
                FileListGenerator flg = getFileListGenerator(vrstaIzvora, dirname, bname);

                String separator = datoteka.getString("[@separator]");
                if (separator == null) {
                    separator = ",";
                }

                DateFormat df = getDateFormat(datoteka);
                Integer[] stupciSaVremenom = getStupciSaVremenom(datoteka);

                tikeri.add(new CsvFileTicker(servis, flg, csvOB,
                        stupciSaVremenom, df, separator.charAt(0)));
            }
        }
        return tikeri;
    }

    private DateFormat getDateFormat(HierarchicalConfiguration datoteka) {
        String format = datoteka.getString("vrijeme[@format]");
        String zona = datoteka.getString("vrijeme[@vremenska_zona]");
        if (zona == null) {
            zona = "UTC";
        }
        DateFormat df = new SimpleDateFormat(format);
        TimeZone timeZone = TimeZone.getTimeZone(zona);
        df.setTimeZone(timeZone);
        return df;
    }

    private Integer[] getStupciSaVremenom(HierarchicalConfiguration datoteka) throws ConfigurationException {
        List<Integer> lista = new ArrayList<>();
        for (HierarchicalConfiguration vrijeme : datoteka.configurationsAt("vrijeme")) {
            for (HierarchicalConfiguration stupac : vrijeme.configurationsAt("stupac")) {
                Integer st = stupac.getInt("");
                lista.add(st);
            }
        }
        if (lista.isEmpty()) {
            throw new ConfigurationException("Not supported yet.");
        }
        return lista.toArray(new Integer[lista.size()]);
    }

    private FileListGenerator getFileListGenerator(String vrstaIzvora, String dirname, String bname) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        String imeKlase = "klijentnapostaji.citac.filelistgeneratori." + vrstaIzvora.trim() + "FileListGenerator";
        Class klasa = Class.forName(imeKlase);
        FileListGenerator flg = (FileListGenerator) klasa.newInstance();
        flg.setBaseName(dirname + "/" + bname);
        return flg;
    }

    private PrihvatServisLocalImpl getWebServis() {
        PrihvatServisLocalImpl servis;
        if (config.containsKey("webservis")) {
            SubnodeConfiguration ws = config.configurationAt("webservis");
            String ss = ws.getString("");
            servis = new PrihvatServisLocalImpl();
        } else {
            servis = new PrihvatServisLocalImpl();
        }
        return servis;
    }

}
