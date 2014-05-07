/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klijentnapostaji.citac;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kraljevic
 */
public interface CitacDatoteke {
    public void setDatoteka(File datoteka);
    public void setVrijemeZadnjegPodatka(Date zadnji);
    public void procitaj();
    public String[] getHeaderi();
    public List<String[]> getLinije();
}
