/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wlcitac.util;

/**
 *
 * @author kraljevic
 */
public enum Flag {
    ALARM,
    UPOZORENJE,
    VAN_PODRUCJA,
    LDL,
    NEDOSTAJE,
    ZS,
    CAL,
    MAN,
    ERR,
    TEMPERATURA,
    OBUHVAT,
    NEMA_PODATKA;
    public static final int NEVALJAN = 1 << Flag.NEDOSTAJE.ordinal();
}
