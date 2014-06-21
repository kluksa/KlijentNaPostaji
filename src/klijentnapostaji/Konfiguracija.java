/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import klijentnapostaji.citac.CsvFileTicker;
import klijentnapostaji.citac.CsvOmotnicaBuilder;
import klijentnapostaji.citac.filelistgeneratori.FileListGenerator;
import klijentnapostaji.webservice.PrihvatServisLocalImpl;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author kraljevic
 */
public class Konfiguracija {

    private static final Logger log = Logger.getLogger(Konfiguracija.class.getName());

    private final XMLConfiguration config;
    private final Map<String, HierarchicalConfiguration> izvori;
    private final HierarchicalConfiguration postaja;
    private PrihvatServisLocalImpl servis;

    public Konfiguracija(String konfigPath) throws ConfigurationException {
        config = new XMLConfiguration(konfigPath);
        izvori = new HashMap<>();
        for (HierarchicalConfiguration izvor : config.configurationsAt("izvori.izvor")) {
            izvori.put(izvor.getString("[@id]"), izvor);
        }
        postaja = config.configurationAt("postaja");
    }

    public Collection<CsvFileTicker> getFileTickeri() throws ConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Collection<CsvFileTicker> tikeri = new ArrayList<>();
        for (HierarchicalConfiguration datoteka : config.configurationsAt("datoteke.datoteka")) {
            CsvFileTicker tik = new CsvFileTicker();
            tik.setServis(servis);
            config(tik, datoteka);
            tikeri.add(tik);
        }
        return tikeri;
    }

    public void config(PrihvatServisLocalImpl servis) throws MalformedURLException {
        HierarchicalConfiguration s = config.configurationAt("webservis");
        if ( s.containsKey("wsdl-url")){
            String urlStr = s.getString("wsdl-url");
            servis.setWsdlUrl(new URL(urlStr));
        }

        if (s.containsKey("qname")) {
            String uri = s.getString("qname.namespace-uri");
            String lokalniPort = s.getString("qname.lokalni-port");

            QName qname;
            if (s.containsKey("qname.prefix")) {
                qname = new QName(uri, lokalniPort, s.getString("qname.prefix"));
            } else {
                qname = new QName(uri, lokalniPort);
            }
            servis.setQname(qname);
        }
        servis.init();
    }

    private void config(CsvOmotnicaBuilder csvOB, HierarchicalConfiguration datoteka) {
        String oznaka = datoteka.getString("[@oznaka]");
        String vrstaIzvora = izvori.get(datoteka.getString("[@izvorid]")).getString("vrsta");
        csvOB.setIzvor(vrstaIzvora);
        csvOB.setPostaja(postaja.getString("oznaka_dhmz"));
        csvOB.setDatoteka(oznaka);
    }

    private void config(CsvFileTicker tik, HierarchicalConfiguration datoteka) throws ConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        CsvOmotnicaBuilder cob = new CsvOmotnicaBuilder();
        config(cob, datoteka);
        tik.setCsvOBuilder(cob);
        tik.setSchedulerStr(datoteka.getString("cron-string"));
        tik.setDateFormat(getDateFormat(datoteka.configurationAt("vrijeme")));
        tik.setFileListGen(getFileListGenerator(datoteka.configurationAt("putanja")));
        tik.setSeparator(datoteka.getString("separator").charAt(0));
        tik.setStupciSaVremenom(getStupciSaVremenom(datoteka.configurationAt("vrijeme")));
    }

    private Integer[] getStupciSaVremenom(HierarchicalConfiguration vrijeme) throws ConfigurationException {
        List<HierarchicalConfiguration> stupci = vrijeme.configurationsAt("stupac");
        Integer[] polje = new Integer[stupci.size()];
        for (int i = 0; i < stupci.size(); i++) {
            Integer st = stupci.get(i).getInt("");
            polje[i] = st;
        }
        if (stupci.isEmpty()) {
            throw new ConfigurationException("Not supported yet.");
        }
        return polje;
    }

    private FileListGenerator getFileListGenerator(HierarchicalConfiguration putanja) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        String imeKlase = putanja.getString("generator-klasa").trim();
        Class klasa = Class.forName(imeKlase);

        FileListGenerator flg = (FileListGenerator) klasa.newInstance();
        String dir = putanja.getString("direktorij");
        String[] stringArray = putanja.getStringArray("element");
        flg.setPath(dir);
        flg.setElementiImena(stringArray);
        return flg;
    }

    private DateFormat getDateFormat(HierarchicalConfiguration vrijeme) {
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
