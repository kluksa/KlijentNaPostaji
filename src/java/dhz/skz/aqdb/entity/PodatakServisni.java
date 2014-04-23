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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kraljevic
 */
@Entity
@Table(name = "podatak_servisni", catalog = "aq_t2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PodatakServisni.findAll", query = "SELECT p FROM PodatakServisni p"),
    @NamedQuery(name = "PodatakServisni.findById", query = "SELECT p FROM PodatakServisni p WHERE p.id = :id"),
    @NamedQuery(name = "PodatakServisni.findByVrijeme", query = "SELECT p FROM PodatakServisni p WHERE p.vrijeme = :vrijeme"),
    @NamedQuery(name = "PodatakServisni.findByVrijednost", query = "SELECT p FROM PodatakServisni p WHERE p.vrijednost = :vrijednost")})
public class PodatakServisni implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date vrijeme;
    private Integer vrijednost;
    @JoinColumn(name = "servisna_komponenta_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private ServisnaKomponenta servisnaKomponentaId;
    @JoinColumn(name = "postaja_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Postaja postajaId;
    private static final Logger log = Logger.getLogger(PodatakServisni.class.getName());

    public PodatakServisni() {
    }

    public PodatakServisni(Integer id) {
        this.id = id;
    }

    public PodatakServisni(Integer id, Date vrijeme) {
        this.id = id;
        this.vrijeme = vrijeme;
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

    public Integer getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(Integer vrijednost) {
        this.vrijednost = vrijednost;
    }

    public ServisnaKomponenta getServisnaKomponentaId() {
        return servisnaKomponentaId;
    }

    public void setServisnaKomponentaId(ServisnaKomponenta servisnaKomponentaId) {
        this.servisnaKomponentaId = servisnaKomponentaId;
    }

    public Postaja getPostajaId() {
        return postajaId;
    }

    public void setPostajaId(Postaja postajaId) {
        this.postajaId = postajaId;
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
        if (!(object instanceof PodatakServisni)) {
            return false;
        }
        PodatakServisni other = (PodatakServisni) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhz.skz.likz.aqdb.entity.PodatakServisni[ id=" + id + " ]";
    }

}
