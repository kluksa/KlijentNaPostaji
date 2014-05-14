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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class Konfiguracija2 {
    
    private static final Logger log = Logger.getLogger(Konfiguracija.class.getName());

    private final XMLConfiguration config;
    private Map<String, HierarchicalConfiguration> izvori;
    private SubnodeConfiguration postaja;
    private PrihvatServisLocalImpl servis;
    
    

    public Konfiguracija2(String konfigPath) throws ConfigurationException {
        config = new XMLConfiguration(konfigPath);
        izvori = new HashMap<>();
        for (HierarchicalConfiguration izvor : config.configurationsAt("izvori.izvor")) {
            izvori.put(izvor.getString("[@id]"), izvor);
        }
        postaja = config.configurationAt("postaja");
    }

    
           
    public Collection<CsvFileTicker> getFileTickeri() throws ConfigurationException{
        Collection<CsvFileTicker> tikeri = new ArrayList<>();
        for (HierarchicalConfiguration datoteka : config.configurationsAt("datoteke.datoteka")){
            CsvFileTicker tik = new CsvFileTicker();
            tik.setServis(servis);
            tik.setFileListGen(getFileListGenerator(datoteka.configurationAt("putanja")));
            config(tik, datoteka);
            tikeri.add(tik);
        }
        return tikeri;
    }
    
    
    public void config(PrihvatServisLocalImpl servis){
        SubnodeConfiguration s = config.configurationAt("webservis");
        this.servis = servis;
        servis.setUrl(s.getString(""));
     //   servis.setUrl(s.getString(""));
    }
    
    private void config(CsvOmotnicaBuilder csvOB, HierarchicalConfiguration datoteka){
        String oznaka = datoteka.getString("[@oznaka]");
        String izvorid = datoteka.getString("[@izvorid]");
        String vrstaIzvora = izvori.get(izvorid).getString("vrsta");
        csvOB.setIzvor(vrstaIzvora);
        csvOB.setPostaja(postaja.getString("oznaka_dhmz"));
        csvOB.setDatoteka(oznaka);
    }

    private void config(CsvFileTicker tik, HierarchicalConfiguration datoteka) throws ConfigurationException {
        CsvOmotnicaBuilder cob = new CsvOmotnicaBuilder();
        config(cob, datoteka);

        tik.setCsvOBuilder(cob);
        tik.setDateFormat(getDateFormat(datoteka.configurationAt("vrijeme")));
        tik.setFileListGen(null);
        tik.setSeparator(datoteka.getString("separator").charAt(0));
        tik.setStupciSaVremenom(getStupciSaVremenom(datoteka.configurationAt("vrijeme")));
    }
    
    private Integer[] getStupciSaVremenom(SubnodeConfiguration vrijeme) throws ConfigurationException {
        List<HierarchicalConfiguration> stupci = vrijeme.configurationsAt("stupac");
        Integer[] polje = new Integer[stupci.size()];
        for (int i=0; i<stupci.size(); i++ ) {
            Integer st = stupci.get(i).getInt("");
            polje[i] = st;
        }
        if (stupci.isEmpty()) {
            throw new ConfigurationException("Not supported yet.");
        }
        return polje;
    }

    private FileListGenerator getFileListGenerator(SubnodeConfiguration putanja)  {

//        String imeKlase = "klijentnapostaji.citac.filelistgeneratori." + vrstaIzvora.trim() + "FileListGenerator";
//        Class klasa = Class.forName(imeKlase);
//        FileListGenerator flg = (FileListGenerator) klasa.newInstance();
        String suf = putanja.getString("sufiks");
        String pref = putanja.getString("prefiks");
        String put = putanja.getString("direktorij");
        List<Object> list = putanja.getList("infiks");
        
        //flg.setBaseName(dirname + "/" + bname);
        return null;
    }


    private DateFormat getDateFormat(SubnodeConfiguration vrijeme) {
        String format = vrijeme.getString("format");
        String zona = vrijeme.getString("zona");
        if (zona == null) {
            zona = "UTC";
        }
        DateFormat df = new SimpleDateFormat(format);
        TimeZone timeZone = TimeZone.getTimeZone(zona);
        df.setTimeZone(timeZone);
        return df;
    }

}
