/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.citaci.weblogger.validatori;

import javax.ejb.Stateless;
import dhz.skz.citaci.weblogger.util.Flag;
import dhz.skz.citaci.weblogger.exceptions.NevaljanStatusException;
import dhz.skz.citaci.weblogger.util.Status;

/**
 *
 * @author kraljevic
 */
@Stateless
public class SynspecValidator extends ValidatorImpl {
    private final Float ldl = 0.f;
    private final Float opseg = 1000.f;

    @Override
    public Status getStatus(Float iznos, String statusStr, float temperatura) throws NevaljanStatusException {
        Status s = new Status();
        if (temperatura != -999.f && (temperatura < tempMin || temperatura > tempMax)) {
            s.dodajFlag(Flag.TEMPERATURA);
        }
        if (iznos < ldl) {
            s.dodajFlag(Flag.LDL);
        }
        if (iznos > opseg) {
            s.dodajFlag(Flag.VAN_PODRUCJA);
        }
        if (iznos == -999.f) {
            s.dodajFlag(Flag.NEDOSTAJE);
        } else if (!statusStr.isEmpty()) {
            try {
                int stInt = Integer.parseInt(statusStr,16);
                if (stInt > 255) {
                    s.dodajFlag(Flag.ERR);
                }
                if ((stInt & 1) == 1) {
                    s.dodajFlag(Flag.MAN);
                }
                if ((stInt & 2) == 2) {
                    s.dodajFlag(Flag.ZS);
                }
                if ((stInt & 4) == 4) {
                    s.dodajFlag(Flag.ZS);
                }
            } catch (NumberFormatException ex) {
                throw new NevaljanStatusException(ex);
            }
        }
        return s;
    }
}
