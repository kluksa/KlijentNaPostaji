/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.aqdb.facades;

import dhz.skz.likz.aqdb.entity.IzvorPodataka;
import dhz.skz.likz.aqdb.entity.IzvorPodataka_;
import dhz.skz.likz.aqdb.entity.NivoValidacije;
import dhz.skz.likz.aqdb.entity.Podatak;
import dhz.skz.likz.aqdb.entity.Podatak_;
import dhz.skz.likz.aqdb.entity.Postaja;
import dhz.skz.likz.aqdb.entity.Postaja_;
import dhz.skz.likz.aqdb.entity.ProgramMjerenja;
import dhz.skz.likz.aqdb.entity.ProgramMjerenja_;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import wlcitac.NizPodataka;
import wlcitac.util.PodatakWl;

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
        return em.createQuery(cq).getResultList();
    }

    public Map<ProgramMjerenja, Podatak> getZadnjiPodatakPoProgramu() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Podatak> from = cq.from(Podatak.class);

        Expression<NivoValidacije> nivoValidacijeE = from.get(Podatak_.nivoValidacijeId);
        Expression<Date> vrijeme = from.get(Podatak_.vrijeme);
        Expression<ProgramMjerenja> program = from.get(Podatak_.programMjerenjaId);

        cq.where(cb.equal(nivoValidacijeE, new NivoValidacije((short) 0)));
        cq.groupBy(program);
        cq.multiselect(cb.greatest(vrijeme), from);

        Map<ProgramMjerenja, Podatak> pm = new HashMap<>();
        for (Tuple t : em.createQuery(cq).getResultList()) {
            Date vr = t.get(0, Date.class);
            Podatak po = t.get(1, Podatak.class);
            pm.put(po.getProgramMjerenjaId(), po);
        }
        return pm;
    }
    
    
    
    public Date getVrijemeZadnjegNaPostajiZaIzvor(Postaja p, IzvorPodataka i) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Date> cq = cb.createQuery(Date.class);
        Root<Podatak> from = cq.from(Podatak.class);
        Join<Podatak, ProgramMjerenja> podatakProgram = from.join(Podatak_.programMjerenjaId);

        Expression<NivoValidacije> nivoValidacijeE = from.get(Podatak_.nivoValidacijeId);
        Expression<Postaja> postaja = podatakProgram.get(ProgramMjerenja_.postajaId);
        Expression<IzvorPodataka> izvor = podatakProgram.get(ProgramMjerenja_.izvorPodatakaId);
        Expression<Date> vrijeme = from.get(Podatak_.vrijeme);
                
        cq.where(
                cb.and(
                        cb.equal(nivoValidacijeE, new NivoValidacije((short) 0)),
                        cb.equal(postaja, p),
                        cb.equal(izvor, i)
                )
        );
        cq.select(cb.greatest(vrijeme));
        return em.createQuery(cq).getSingleResult();
    }
    
    public Collection<ProgramMjerenja> getProgramNaPostajiZaIzvor(Postaja p, IzvorPodataka i, Date zadnji) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProgramMjerenja> cq = cb.createQuery(ProgramMjerenja.class);
        Root<ProgramMjerenja> program = cq.from(ProgramMjerenja.class);
        Expression<Postaja> postaja = program.get(ProgramMjerenja_.postajaId);
        Expression<IzvorPodataka> izvor = program.get(ProgramMjerenja_.izvorPodatakaId);
        Expression<Date> kraj = program.get(ProgramMjerenja_.zavrsetakMjerenja);
        
        cq.select(program).where(
            cb.and(
                cb.equal(postaja, p),
                cb.equal(izvor, i),
                cb.or(
                    cb.isNull(kraj),
                    cb.greaterThan(kraj, zadnji)
                )
            )
        );
        return em.createQuery(cq).getResultList();
    }
    
    public Collection<Postaja> getPostajeZaIzvor(IzvorPodataka i) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Postaja> cq = cb.createQuery(Postaja.class);
        Root<IzvorPodataka> izvor = cq.from(IzvorPodataka.class);
        CollectionJoin<IzvorPodataka, ProgramMjerenja> izvorProgram = izvor.join(IzvorPodataka_.programMjerenjaCollection);
        Join<ProgramMjerenja, Postaja> programPostaja = izvorProgram.join(ProgramMjerenja_.postajaId);
        Expression<Postaja> postaja = izvorProgram.get(ProgramMjerenja_.postajaId);
        Expression<String> naziv = programPostaja.get(Postaja_.nazivPostaje);
        cq.where(cb.equal(izvor, i)).select(postaja).distinct(true).orderBy(cb.asc(naziv));
        return em.createQuery(cq).getResultList();
    }
    
    public void pospremiNiz(NizPodataka niz) {
        ProgramMjerenja kljuc = niz.getKljuc();
        if ( niz.getPodaci().isEmpty()) return;
        
        log.log(Level.INFO, "Postaja {0}, komponenta {1}, prvi {2}, zadnj {3}, ukupno {4}",
                new Object[]{kljuc.getPostajaId().getNazivPostaje(),
                    kljuc.getKomponentaId().getFormula(),
                    niz.getPodaci().firstKey(),
                    niz.getPodaci().lastKey(),
                    niz.getPodaci().size()});
        NivoValidacije nv = new NivoValidacije((short)0);
        for (Date d : niz.getPodaci().keySet()){
            PodatakWl wlp = niz.getPodaci().get(d);
            Podatak p = new Podatak();
            p.setVrijeme(d);
            p.setVrijednost(wlp.getVrijednost());
            p.setObuhvat(wlp.getObuhvat());
            p.setProgramMjerenjaId(kljuc);
            p.setNivoValidacijeId(nv);
            p.setStatus(wlp.getStatus());
            em.persist(p);
        }
        em.flush();
    }
}
