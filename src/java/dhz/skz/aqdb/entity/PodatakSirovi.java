/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.aqdb.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kraljevic
 */
@Entity
@Table(name = "podatak_sirovi", catalog = "aq_t2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PodatakSirovi.findAll", query = "SELECT p FROM PodatakSirovi p"),
    @NamedQuery(name = "PodatakSirovi.findById", query = "SELECT p FROM PodatakSirovi p WHERE p.id = :id"),
    @NamedQuery(name = "PodatakSirovi.findByVrijeme", query = "SELECT p FROM PodatakSirovi p WHERE p.vrijeme = :vrijeme"),
    @NamedQuery(name = "PodatakSirovi.findByVrijednost", query = "SELECT p FROM PodatakSirovi p WHERE p.vrijednost = :vrijednost"),
    @NamedQuery(name = "PodatakSirovi.findByStatus", query = "SELECT p FROM PodatakSirovi p WHERE p.status = :status"),
    @NamedQuery(name = "PodatakSirovi.findByGreska", query = "SELECT p FROM PodatakSirovi p WHERE p.greska = :greska")})
public class PodatakSirovi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijeme;
    @Basic(optional = false)
    @Column(nullable = false)
    private double vrijednost;
    private Integer status;
    private Integer greska;
    @JoinColumn(name = "uredjaj_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Uredjaj uredjajId;
    @JoinColumn(name = "komponenta_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Komponenta komponentaId;
    private static final Logger log = Logger.getLogger(PodatakSirovi.class.getName());

    public PodatakSirovi() {
    }

    public PodatakSirovi(Integer id) {
        this.id = id;
    }

    public PodatakSirovi(Integer id, Date vrijeme, double vrijednost) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.vrijednost = vrijednost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
    }

    public double getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(double vrijednost) {
        this.vrijednost = vrijednost;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGreska() {
        return greska;
    }

    public void setGreska(Integer greska) {
        this.greska = greska;
    }

    public Uredjaj getUredjajId() {
        return uredjajId;
    }

    public void setUredjajId(Uredjaj uredjajId) {
        this.uredjajId = uredjajId;
    }

    public Komponenta getKomponentaId() {
        return komponentaId;
    }

    public void setKomponentaId(Komponenta komponentaId) {
        this.komponentaId = komponentaId;
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
        if (!(object instanceof PodatakSirovi)) {
            return false;
        }
        PodatakSirovi other = (PodatakSirovi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhz.skz.likz.aqdb.entity.PodatakSirovi[ id=" + id + " ]";
    }

}
