/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.aqdb.facades;

import dhz.skz.citaci.weblogger.util.NizZeroSpanPodataka;
import dhz.skz.aqdb.entity.IzvorPodataka;
import dhz.skz.aqdb.entity.Postaja;
import dhz.skz.aqdb.entity.ProgramMjerenja;
import dhz.skz.aqdb.entity.ProgramMjerenja_;
import dhz.skz.aqdb.entity.ProgramUredjajLink;
import dhz.skz.aqdb.entity.ProgramUredjajLink_;
import dhz.skz.aqdb.entity.Uredjaj;
import dhz.skz.aqdb.entity.Uredjaj_;
import dhz.skz.aqdb.entity.ZeroSpan;
import dhz.skz.aqdb.entity.ZeroSpan_;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author kraljevic
 */
@Stateless
public class ZeroSpanFacade extends AbstractFacade<ZeroSpan>{
    private static final Logger log = Logger.getLogger(ZeroSpanFacade.class.getName());

    @PersistenceContext(unitName = "CitacModulPU")
    private EntityManager em;
    
        @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZeroSpanFacade() {
        super(ZeroSpan.class);
    }

    public Date getVrijemeZadnjegZeroSpanNaPostajiZaIzvor(Postaja p, IzvorPodataka izvor) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Date> cq = cb.createQuery(Date.class);
        Root<ZeroSpan> zsT = cq.from(ZeroSpan.class);
        
        Join<ZeroSpan, Uredjaj>  uredjajT= zsT.join(ZeroSpan_.uredjajId);
        Join<Uredjaj, ProgramUredjajLink> uredjajProgramT = uredjajT.join(Uredjaj_.programUredjajLinkCollection);
        Join<ProgramUredjajLink, ProgramMjerenja> programT = uredjajProgramT.join(ProgramUredjajLink_.programMjerenjaId);
        Join<ProgramMjerenja, IzvorPodataka> izvorT = programT.join(ProgramMjerenja_.izvorPodatakaId);
        Join<ProgramMjerenja, Postaja> postajaT = programT.join(ProgramMjerenja_.postajaId);
        
        Expression<Date> vrijemeE = zsT.get(ZeroSpan_.vrijeme);
        
        cq.where(
                cb.and(
                        cb.equal(postajaT, p),
                        cb.equal(izvorT, izvor)
                )
        );
        cq.select(cb.greatest(vrijemeE));
        return em.createQuery(cq).getSingleResult();
   }

    public void pospremiNiz(NizZeroSpanPodataka niz) {
        ProgramMjerenja kljuc = niz.getKljuc();
        if ( niz.getPodaci().isEmpty()) return;
        
        log.log(Level.INFO, "Postaja {0}, komponenta {1}, prvi {2}, zadnj {3}, ukupno {4}",
                new Object[]{kljuc.getPostajaId().getNazivPostaje(),
                    kljuc.getKomponentaId().getFormula(),
                    niz.getPodaci().firstKey(),
                    niz.getPodaci().lastKey(),
                    niz.getPodaci().size()});
        for (Date d : niz.getPodaci().keySet()){
            ZeroSpan zs = niz.getPodaci().get(d);
            em.persist(zs);
        }
        em.flush();
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

}
