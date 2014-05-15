/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji.citac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import klijentnapostaji.webservice.CsvOmotnica;
import klijentnapostaji.webservice.StringArray;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 *
 * @author kraljevic
 */
public class CsvOmotnicaBuilder {

    private String datoteka;
    private String izvor;
    private String postaja;

    public static CsvOmotnicaBuilder getOmotnicaBuilderFromConfig(HierarchicalConfiguration conf) {
        return null;
    }

    public CsvOmotnicaBuilder() {
    }

    public CsvOmotnicaBuilder(String datoteka, String izvor, String postaja) {
        this.datoteka = datoteka;
        this.izvor = izvor;
        this.postaja = postaja;
    }

    public CsvOmotnica create(String[] headers, List<String[]> linije) {

        CsvOmotnica o = new CsvOmotnica();
        o.setDatoteka(datoteka);
        o.setIzvor(izvor);
        o.setPostaja(postaja);
        o.getHeaderi().addAll(Arrays.asList(headers));
        for (String[] ss : linije) {
            StringArray sa = new StringArray();
            sa.getItem().addAll(Arrays.asList(ss));
            o.getLinije().add(sa);
        }
        return o;
    }

    public String getDatoteka() {
        return datoteka;
    }

    public void setDatoteka(String datoteka) {
        this.datoteka = datoteka;
    }

    public String getIzvor() {
        return izvor;
    }

    public void setIzvor(String izvor) {
        this.izvor = izvor;
    }

    public String getPostaja() {
        return postaja;
    }

    public void setPostaja(String postaja) {
        this.postaja = postaja;
    }
}
