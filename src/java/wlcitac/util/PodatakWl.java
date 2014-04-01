/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wlcitac.util;

import dhz.skz.likz.aqdb.entity.ProgramMjerenja;
import java.util.Date;


/**
 *
 * @author kraljevic
 */
public class PodatakWl {
    private Date vrijeme;
    private ProgramMjerenja programMjerenja;
    private Status status;
    private Float vrijednost;
    private short obuhvat;
    
    
    public void dodajStatus(Flag s){
        status.dodajFlag(s);
    }
        
    public boolean isValid() {
        return status.isValid();
    }

    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setProgramMjerenja(ProgramMjerenja kljuc) {
        this.programMjerenja = kljuc;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setObuhvat(short obuhvat) {
        this.obuhvat = obuhvat;
    }

    public void setVrijednost(float iznos) {
        this.vrijednost = iznos;
    }

    public ProgramMjerenja getProgramMjerenja() {
        return programMjerenja;
    }

    public Float getVrijednost() {
        return vrijednost;
    }

    public short getObuhvat() {
        return obuhvat;
    }
    

    public Date getVrijeme() {
        return this.vrijeme;
    }

}
