/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.citaci;

import dhz.skz.likz.aqdb.entity.IzvorPodataka;
import dhz.skz.likz.aqdb.entity.Podatak;
import dhz.skz.likz.aqdb.entity.ProgramMjerenja;
import java.util.Map;
import wlcitac.NizPodataka;

/**
 *
 * @author kraljevic
 */
public interface CitacIzvora {
    public Map<ProgramMjerenja, NizPodataka> procitaj(IzvorPodataka izvor);
    public Map<ProgramMjerenja, NizPodataka> procitaj(IzvorPodataka izvor, 
                                            Map<ProgramMjerenja, Podatak> zadnjiPodatak);

}
