/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wlcitac;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

/**
 *
 * @author kraljevic
 */
public final class WlFileFilter implements FTPFileFilter {

    private static final Logger log = Logger.getLogger(WlFileFilter.class.getName());
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    private Date pocetak;
    private String nazivPostaje;
    private TimeZone timeZone;

    public WlFileFilter(String nazivPostaje, Date pocetak, TimeZone timeZone) {
        setPocetak(pocetak);
        setNazivPostaje(nazivPostaje);
        setTimeZone(timeZone);
    }

    public void setPocetak(Date pocetak) {
        this.pocetak = pocetak;
    }

    public void setNazivPostaje(String nazivPostaje) {
        this.nazivPostaje = Pattern.quote(nazivPostaje.toLowerCase());
       
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        formatter.setTimeZone(timeZone);
    }

    @Override
    public boolean accept(FTPFile file) {
        if ( file.isDirectory()) return false;
        
        String strL = file.getName().toLowerCase();
        String ptStr = "^(" + nazivPostaje + ")-(\\d{8})(.?)\\.csv";
        Pattern pt = Pattern.compile(ptStr);
        Matcher m = pt.matcher(strL);
        if (m.matches()) {
            try {
                Date sada = formatter.parse(m.group(2));
                if (pocetak.getTime() - sada.getTime() < 24 * 3600 * 1000) {
                    return true;
                }
            } catch (ParseException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
