/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wlcitac;

import dhz.skz.likz.aqdb.entity.ProgramMjerenja;
import java.util.Objects;


/**
 *
 * @author kraljevic
 */
public class PodatakKljuc {
    String p, k, u, n;
    
    public PodatakKljuc(ProgramMjerenja pm) {
        p = pm.getIzvorProgramKljuceviMap().getPKljuc();
        k = pm.getIzvorProgramKljuceviMap().getKKljuc();
        u = pm.getIzvorProgramKljuceviMap().getUKljuc();
        n = pm.getIzvorProgramKljuceviMap().getNKljuc();
    }

    public String getP() {
        return p;
    }

    public String getK() {
        return k;
    }

    public String getU() {
        return u;
    }

    public String getN() {
        return n;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.p);
        hash = 67 * hash + Objects.hashCode(this.k);
        hash = 67 * hash + Objects.hashCode(this.u);
        hash = 67 * hash + Objects.hashCode(this.n);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PodatakKljuc other = (PodatakKljuc) obj;
        if (!Objects.equals(this.p, other.p)) {
            return false;
        }
        if (!Objects.equals(this.k, other.k)) {
            return false;
        }
        if (!Objects.equals(this.u, other.u)) {
            return false;
        }
        if (!Objects.equals(this.n, other.n)) {
            return false;
        }
        return true;
    }
    

    
}
