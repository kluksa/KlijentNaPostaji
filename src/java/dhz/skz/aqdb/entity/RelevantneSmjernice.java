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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kraljevic
 */
@Entity
@Table(name = "relevantne_smjernice", catalog = "aq_t2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelevantneSmjernice.findAll", query = "SELECT r FROM RelevantneSmjernice r"),
    @NamedQuery(name = "RelevantneSmjernice.findById", query = "SELECT r FROM RelevantneSmjernice r WHERE r.id = :id"),
    @NamedQuery(name = "RelevantneSmjernice.findByNaziv", query = "SELECT r FROM RelevantneSmjernice r WHERE r.naziv = :naziv")})
public class RelevantneSmjernice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(length = 45)
    private String naziv;
    @ManyToMany(mappedBy = "relevantneSmjerniceCollection")
    private Collection<Komponenta> komponentaCollection;
    private static final Logger log = Logger.getLogger(RelevantneSmjernice.class.getName());

    public RelevantneSmjernice() {
    }

    public RelevantneSmjernice(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public Collection<Komponenta> getKomponentaCollection() {
        return komponentaCollection;
    }

    public void setKomponentaCollection(Collection<Komponenta> komponentaCollection) {
        this.komponentaCollection = komponentaCollection;
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
        if (!(object instanceof RelevantneSmjernice)) {
            return false;
        }
        RelevantneSmjernice other = (RelevantneSmjernice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhz.skz.likz.aqdb.entity.RelevantneSmjernice[ id=" + id + " ]";
    }

}
