/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji.webservice;

import java.net.URL;
import java.util.Date;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import klijentnapostaji.citac.exceptions.PrihvatWSException;

/**
 *
 * @author kraljevic
 */
public class PrihvatServisLocalImpl {

    private static final Logger log = Logger.getLogger(PrihvatServisLocalImpl.class.getName());
    private PrihvatPodataka pss;
    private QName qname;
    private URL wsdlurl;

    public void init() {
        PrihvatPodataka_Service s;
        if (wsdlurl != null) {
            if (qname == null) {
                s = new PrihvatPodataka_Service(wsdlurl);
            } else {
                s = new PrihvatPodataka_Service(wsdlurl, qname);
            }
        } else {
            s = new PrihvatPodataka_Service();
        }
        pss = s.getPrihvatPodatakaPort();
    }

    public Date getVrijemeZadnjeg(String str, String str0, String str1) {
        Long vrijemeZadnjeg = pss.getUnixTimeZadnjeg(str, str0, str1);
        return new Date(vrijemeZadnjeg);
    }

    public void prebaciOmotnicu(CsvOmotnica create) throws PrihvatWSException {
        try {
            pss.prebaciOmotnicu(create);
        } catch (CsvPrihvatException_Exception ex) {
            throw new PrihvatWSException(ex);
        }
    }
    
    public String test(String str) throws PrihvatWSException{
        try {
            return pss.testWS(str);
        } catch (CsvPrihvatException_Exception ex) {
            throw new PrihvatWSException(ex);
        }
    }

    public void setWsdlUrl(URL string) {
        this.wsdlurl = string;
    }

    public void setQname(QName qname) {
        this.qname = qname;
    }

    private static Long getUnixTimeZadnjeg(java.lang.String izvor, java.lang.String postaja, java.lang.String datoteka) {
        klijentnapostaji.webservice.PrihvatPodataka_Service service = new klijentnapostaji.webservice.PrihvatPodataka_Service();
        klijentnapostaji.webservice.PrihvatPodataka port = service.getPrihvatPodatakaPort();
        return port.getUnixTimeZadnjeg(izvor, postaja, datoteka);
    }
}
