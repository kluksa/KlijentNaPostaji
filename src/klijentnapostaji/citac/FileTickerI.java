/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klijentnapostaji.citac;

import java.util.Date;

/**
 *
 * @author kraljevic
 */
public interface FileTickerI extends Runnable {
    public void setPrihvatService();
    public void setFileName(String fname);
    public void setFileRotate(boolean rotate);
    public void procitajDatoteke();
    public void posaljiOmotnicu();
    public void setVrijemeZadnjegPodatka( Date zadnji );
}
