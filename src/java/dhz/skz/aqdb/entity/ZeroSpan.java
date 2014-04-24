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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kraljevic
 */
@Entity
@Table(name = "zero_span", catalog = "aq_t2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ZeroSpan.findAll", query = "SELECT z FROM ZeroSpan z"),
    @NamedQuery(name = "ZeroSpan.findById", query = "SELECT z FROM ZeroSpan z WHERE z.id = :id"),
    @NamedQuery(name = "ZeroSpan.findByVrijeme", query = "SELECT z FROM ZeroSpan z WHERE z.vrijeme = :vrijeme"),
    @NamedQuery(name = "ZeroSpan.findByVrsta", query = "SELECT z FROM ZeroSpan z WHERE z.vrsta = :vrsta"),
    @NamedQuery(name = "ZeroSpan.findByVrijednost", query = "SELECT z FROM ZeroSpan z WHERE z.vrijednost = :vrijednost"),
    @NamedQuery(name = "ZeroSpan.findByMinimum", query = "SELECT z FROM ZeroSpan z WHERE z.minimum = :minimum"),
    @NamedQuery(name = "ZeroSpan.findByMaximum", query = "SELECT z FROM ZeroSpan z WHERE z.maximum = :maximum"),
    @NamedQuery(name = "ZeroSpan.findByStdev", query = "SELECT z FROM ZeroSpan z WHERE z.stdev = :stdev"),
    @NamedQuery(name = "ZeroSpan.findByReferentnaVrijednost", query = "SELECT z FROM ZeroSpan z WHERE z.referentnaVrijednost = :referentnaVrijednost")})
public class ZeroSpan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijeme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    private String vrsta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Float vrijednost;
    private Float minimum;
    private Float maximum;
    private Float stdev;
    @Column(name = "referentna_vrijednost")
    private Float referentnaVrijednost;
    @JoinColumn(name = "uredjaj_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Uredjaj uredjajId;
    @JoinColumn(name = "komponenta_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Komponenta komponentaId;
    private static final Logger log = Logger.getLogger(ZeroSpan.class.getName());

    public ZeroSpan() {
    }

    public ZeroSpan(Integer id) {
        this.id = id;
    }

    public ZeroSpan(Integer id, Date vrijeme, String vrsta) {
        this.id = id;
        this.vrijeme = vrijeme;
        this.vrsta = vrsta;
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

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public Float getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(Float vrijednost) {
        this.vrijednost = vrijednost;
    }

    public Float getMinimum() {
        return minimum;
    }

    public void setMinimum(Float minimum) {
        this.minimum = minimum;
    }

    public Float getMaximum() {
        return maximum;
    }

    public void setMaximum(Float maximum) {
        this.maximum = maximum;
    }

    public Float getStdev() {
        return stdev;
    }

    public void setStdev(Float stdev) {
        this.stdev = stdev;
    }

    public Float getReferentnaVrijednost() {
        return referentnaVrijednost;
    }

    public void setReferentnaVrijednost(Float referentnaVrijednost) {
        this.referentnaVrijednost = referentnaVrijednost;
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
        if (!(object instanceof ZeroSpan)) {
            return false;
        }
        ZeroSpan other = (ZeroSpan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhz.skz.aqdb.entity.ZeroSpan[ id=" + id + " ]";
    }

}
