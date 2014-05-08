/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klijentnapostaji.webservice;


import klijentnapostaji.citac.exceptions.PrihvatWSException;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author kraljevic
 */
public class PrihvatServisLocalImpl {
    private static final Logger log = Logger.getLogger(PrihvatServisLocalImpl.class.getName());
    private final PrihvatSirovihPodataka pss;

    public PrihvatServisLocalImpl() {
        pss = new PrihvatSirovihPodataka_Service().getPrihvatSirovihPodatakaPort();
    }

    public PrihvatServisLocalImpl(PrihvatSirovihPodataka pss) {
        this.pss = pss;
    }
    
    public Date getVrijemeZadnjeg(String str, String str0, String str1) {
        Long vrijemeZadnjeg = pss.getUnixTimeZadnjeg(str, str1, str1);
        return new Date(vrijemeZadnjeg);
    }

    public void prebaciOmotnicu(CsvOmotnica create) throws PrihvatWSException{
        try {
            pss.prebaciOmotnicu(create);
        } catch (CsvPrihvatException_Exception ex) {
            throw new PrihvatWSException(ex);
        }
    }
    
}
