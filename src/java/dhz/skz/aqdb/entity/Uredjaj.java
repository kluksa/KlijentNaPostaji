/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.aqdb.entity;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kraljevic
 */
@Entity
@Table(catalog = "aq_t2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uredjaj.findAll", query = "SELECT u FROM Uredjaj u"),
    @NamedQuery(name = "Uredjaj.findById", query = "SELECT u FROM Uredjaj u WHERE u.id = :id"),
    @NamedQuery(name = "Uredjaj.findBySerijskaOznaka", query = "SELECT u FROM Uredjaj u WHERE u.serijskaOznaka = :serijskaOznaka"),
    @NamedQuery(name = "Uredjaj.findByGodinaProizvodnje", query = "SELECT u FROM Uredjaj u WHERE u.godinaProizvodnje = :godinaProizvodnje"),
    @NamedQuery(name = "Uredjaj.findByDatumIsporuke", query = "SELECT u FROM Uredjaj u WHERE u.datumIsporuke = :datumIsporuke"),
    @NamedQuery(name = "Uredjaj.findByDatumOtpisa", query = "SELECT u FROM Uredjaj u WHERE u.datumOtpisa = :datumOtpisa")})
public class Uredjaj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "serijska_oznaka", nullable = false, length = 45)
    private String serijskaOznaka;
    @Column(name = "godina_proizvodnje")
    private Integer godinaProizvodnje;
    @Column(name = "datum_isporuke")
    @Temporal(TemporalType.DATE)
    private Date datumIsporuke;
    @Column(name = "datum_otpisa")
    @Temporal(TemporalType.DATE)
    private Date datumOtpisa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uredjajId")
    private Collection<PodatakSirovi> podatakSiroviCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uredjajId")
    private Collection<Kvarovi> kvaroviCollection;
    @JoinColumn(name = "model_uredjaja_id", referencedColumnName = "id")
    @ManyToOne
    private ModelUredjaja modelUredjajaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uredjajId")
    private Collection<ZeroSpan> zeroSpanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uredjajId")
    private Collection<Umjeravanje> umjeravanjeCollection;
    @OneToMany(mappedBy = "uredjajId")
    private Collection<ProgramUredjajLink> programUredjajLinkCollection;
    private static final Logger log = Logger.getLogger(Uredjaj.class.getName());

    public Uredjaj() {
    }

    public Uredjaj(Integer id) {
        this.id = id;
    }

    public Uredjaj(Integer id, String serijskaOznaka) {
        this.id = id;
        this.serijskaOznaka = serijskaOznaka;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerijskaOznaka() {
        return serijskaOznaka;
    }

    public void setSerijskaOznaka(String serijskaOznaka) {
        this.serijskaOznaka = serijskaOznaka;
    }

    public Integer getGodinaProizvodnje() {
        return godinaProizvodnje;
    }

    public void setGodinaProizvodnje(Integer godinaProizvodnje) {
        this.godinaProizvodnje = godinaProizvodnje;
    }

    public Date getDatumIsporuke() {
        return datumIsporuke;
    }

    public void setDatumIsporuke(Date datumIsporuke) {
        this.datumIsporuke = datumIsporuke;
    }

    public Date getDatumOtpisa() {
        return datumOtpisa;
    }

    public void setDatumOtpisa(Date datumOtpisa) {
        this.datumOtpisa = datumOtpisa;
    }

    @XmlTransient
    public Collection<PodatakSirovi> getPodatakSiroviCollection() {
        return podatakSiroviCollection;
    }

    public void setPodatakSiroviCollection(Collection<PodatakSirovi> podatakSiroviCollection) {
        this.podatakSiroviCollection = podatakSiroviCollection;
    }

    @XmlTransient
    public Collection<Kvarovi> getKvaroviCollection() {
        return kvaroviCollection;
    }

    public void setKvaroviCollection(Collection<Kvarovi> kvaroviCollection) {
        this.kvaroviCollection = kvaroviCollection;
    }

    public ModelUredjaja getModelUredjajaId() {
        return modelUredjajaId;
    }

    public void setModelUredjajaId(ModelUredjaja modelUredjajaId) {
        this.modelUredjajaId = modelUredjajaId;
    }

    @XmlTransient
    public Collection<ZeroSpan> getZeroSpanCollection() {
        return zeroSpanCollection;
    }

    public void setZeroSpanCollection(Collection<ZeroSpan> zeroSpanCollection) {
        this.zeroSpanCollection = zeroSpanCollection;
    }

    @XmlTransient
    public Collection<Umjeravanje> getUmjeravanjeCollection() {
        return umjeravanjeCollection;
    }

    public void setUmjeravanjeCollection(Collection<Umjeravanje> umjeravanjeCollection) {
        this.umjeravanjeCollection = umjeravanjeCollection;
    }

    @XmlTransient
    public Collection<ProgramUredjajLink> getProgramUredjajLinkCollection() {
        return programUredjajLinkCollection;
    }

    public void setProgramUredjajLinkCollection(Collection<ProgramUredjajLink> programUredjajLinkCollection) {
        this.programUredjajLinkCollection = programUredjajLinkCollection;
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
        if (!(object instanceof Uredjaj)) {
            return false;
        }
        Uredjaj other = (Uredjaj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhz.skz.likz.aqdb.entity.Uredjaj[ id=" + id + " ]";
    }

}
