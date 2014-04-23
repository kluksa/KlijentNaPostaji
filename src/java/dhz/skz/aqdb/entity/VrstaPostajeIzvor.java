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
@Table(name = "vrsta_postaje_izvor", catalog = "aq_t2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VrstaPostajeIzvor.findAll", query = "SELECT v FROM VrstaPostajeIzvor v"),
    @NamedQuery(name = "VrstaPostajeIzvor.findById", query = "SELECT v FROM VrstaPostajeIzvor v WHERE v.id = :id"),
    @NamedQuery(name = "VrstaPostajeIzvor.findByOznaka", query = "SELECT v FROM VrstaPostajeIzvor v WHERE v.oznaka = :oznaka"),
    @NamedQuery(name = "VrstaPostajeIzvor.findByNaziv", query = "SELECT v FROM VrstaPostajeIzvor v WHERE v.naziv = :naziv")})
public class VrstaPostajeIzvor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    private char oznaka;
    @Basic(optional = false)
    @Column(nullable = false, length = 20)
    private String naziv;
    @OneToMany(mappedBy = "vrstaPostajeIzvorId")
    private Collection<Postaja> postajaCollection;
    private static final Logger log = Logger.getLogger(VrstaPostajeIzvor.class.getName());

    public VrstaPostajeIzvor() {
    }

    public VrstaPostajeIzvor(Integer id) {
        this.id = id;
    }

    public VrstaPostajeIzvor(Integer id, char oznaka, String naziv) {
        this.id = id;
        this.oznaka = oznaka;
        this.naziv = naziv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public char getOznaka() {
        return oznaka;
    }

    public void setOznaka(char oznaka) {
        this.oznaka = oznaka;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public Collection<Postaja> getPostajaCollection() {
        return postajaCollection;
    }

    public void setPostajaCollection(Collection<Postaja> postajaCollection) {
        this.postajaCollection = postajaCollection;
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
        if (!(object instanceof VrstaPostajeIzvor)) {
            return false;
        }
        VrstaPostajeIzvor other = (VrstaPostajeIzvor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhz.skz.likz.aqdb.entity.VrstaPostajeIzvor[ id=" + id + " ]";
    }

}
