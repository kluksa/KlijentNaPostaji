/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.aqdb.facades;


import dhz.skz.likz.aqdb.entity.IzvorPodataka;
import dhz.skz.likz.aqdb.entity.IzvorPodataka_;
import dhz.skz.likz.aqdb.entity.Podatak;
import dhz.skz.likz.aqdb.entity.ProgramMjerenja;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author kraljevic
 */
@Stateless
public class PodatakFacade extends AbstractFacade<Podatak> {
    @PersistenceContext(unitName = "CitacModulPU")
    private EntityManager em;
    private static final Logger log = Logger.getLogger(PodatakFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PodatakFacade() {
        super(Podatak.class);
    }

    public Iterable<IzvorPodataka> getAktivniIzvori() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<IzvorPodataka> cq = cb.createQuery(IzvorPodataka.class);
        Root<IzvorPodataka> from = cq.from(IzvorPodataka.class);
        cq.where(cb.equal(from.get(IzvorPodataka_.aktivan), true));
        cq.select(from);
        return  em.createQuery(cq).getResultList();
    }

    public Map<ProgramMjerenja, Podatak> getZadnjiPodatakPoProgramu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
