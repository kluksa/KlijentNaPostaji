/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.citaci.weblogger.validatori;

import dhz.skz.likz.aqdb.entity.ModelUredjaja;
import dhz.skz.likz.aqdb.entity.ProgramMjerenja;
import dhz.skz.likz.aqdb.entity.ProgramUredjajLink;
import dhz.skz.likz.aqdb.entity.Validatori;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author kraljevic
 */
@Singleton
public class ValidatorFactory  {

    private ModelUredjaja model;

    private final Map<String, Validator> validatorPool = new HashMap<>();

    public NavigableMap<Date, Validator> getValidatori(ProgramMjerenja pm)  {
        NavigableMap<Date, Validator> validatori = new TreeMap<>();
        for (Iterator<ProgramUredjajLink> it = pm.getProgramUredjajLinkCollection().iterator(); it.hasNext();) {
            ProgramUredjajLink pul = it.next();
            try {
                Validator val = getValidator(pul.getUredjajId().getModelUredjajaId());
                validatori.put(pul.getVrijemePostavljanja(), val);
            } catch ( InstantiationException | IllegalAccessException | IOException | ClassNotFoundException ex) {
                Logger.getLogger(ValidatorFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return validatori;
    }

    public Validator getValidator(ModelUredjaja model) throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        Validator validator;
        Validatori v = model.getValidatorId();

        if (validatorPool.containsKey(v.getNaziv())) {
            validator = validatorPool.get(v.getNaziv());
        } else {
            validator = getValidatorObject(v);
//            validator = ((Class<Validator>) defineClass(v.getNaziv(), v.getKlasa(), 0, v.getKlasa().length)).newInstance();
            validatorPool.put(v.getNaziv(), validator);
        }
        return validator;
    }

    private Validator getValidatorObject(Validatori model) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(model.getKlasa());
        ObjectInput in = null;
        Validator v;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            v = (Validator) o;
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {}
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {}
        }
        return v;
    }
}
