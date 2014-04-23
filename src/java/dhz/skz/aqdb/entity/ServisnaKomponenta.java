/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.aqdb.entity;


import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kraljevic
 */
@Entity
@Table(name = "servisna_komponenta", catalog = "aq_t2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServisnaKomponenta.findAll", query = "SELECT s FROM ServisnaKomponenta s"),
    @NamedQuery(name = "ServisnaKomponenta.findById", query = "SELECT s FROM ServisnaKomponenta s WHERE s.id = :id"),
    @NamedQuery(name = "ServisnaKomponenta.findByOznaka", query = "SELECT s FROM ServisnaKomponenta s WHERE s.oznaka = :oznaka"),
    @NamedQuery(name = "ServisnaKomponenta.findByFlipflop", query = "SELECT s FROM ServisnaKomponenta s WHERE s.flipflop = :flipflop"),
    @NamedQuery(name = "ServisnaKomponenta.findByFaktor", query = "SELECT s FROM ServisnaKomponenta s WHERE s.faktor = :faktor")})
public class ServisnaKomponenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(length = 45)
    private String oznaka;
    private Boolean flipflop;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 12)
    private Float faktor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servisnaKomponentaId")
    private Collection<PodatakServisni> podatakServisniCollection;
    private static final Logger log = Logger.getLogger(ServisnaKomponenta.class.getName());

    public ServisnaKomponenta() {
    }

    public ServisnaKomponenta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public Boolean getFlipflop() {
        return flipflop;
    }

    public void setFlipflop(Boolean flipflop) {
        this.flipflop = flipflop;
    }

    public Float getFaktor() {
        return faktor;
    }

    public void setFaktor(Float faktor) {
        this.faktor = faktor;
    }

    @XmlTransient
    public Collection<PodatakServisni> getPodatakServisniCollection() {
        return podatakServisniCollection;
    }

    public void setPodatakServisniCollection(Collection<PodatakServisni> podatakServisniCollection) {
        this.podatakServisniCollection = podatakServisniCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServisnaKomponenta)) {
            return false;
        }
        ServisnaKomponenta other = (ServisnaKomponenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhz.skz.likz.aqdb.entity.ServisnaKomponenta[ id=" + id + " ]";
    }

}
