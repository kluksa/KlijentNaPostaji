/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.aqdb.entity;


import java.io.Serializable;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kraljevic
 */
@Entity
@Table(name = "primatelj_program_kljucevi_map", catalog = "aq_t2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrimateljProgramKljuceviMap.findAll", query = "SELECT p FROM PrimateljProgramKljuceviMap p"),
    @NamedQuery(name = "PrimateljProgramKljuceviMap.findByProgramMjerenjaId", query = "SELECT p FROM PrimateljProgramKljuceviMap p WHERE p.programMjerenjaId = :programMjerenjaId"),
    @NamedQuery(name = "PrimateljProgramKljuceviMap.findByPKljuc", query = "SELECT p FROM PrimateljProgramKljuceviMap p WHERE p.pKljuc = :pKljuc"),
    @NamedQuery(name = "PrimateljProgramKljuceviMap.findByKKljuc", query = "SELECT p FROM PrimateljProgramKljuceviMap p WHERE p.kKljuc = :kKljuc"),
    @NamedQuery(name = "PrimateljProgramKljuceviMap.findByUKljuc", query = "SELECT p FROM PrimateljProgramKljuceviMap p WHERE p.uKljuc = :uKljuc"),
    @NamedQuery(name = "PrimateljProgramKljuceviMap.findByNKljuc", query = "SELECT p FROM PrimateljProgramKljuceviMap p WHERE p.nKljuc = :nKljuc")})
public class PrimateljProgramKljuceviMap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "program_mjerenja_id", nullable = false)
    private Integer programMjerenjaId;
    @Column(name = "p_kljuc", length = 45)
    private String pKljuc;
    @Column(name = "k_kljuc", length = 45)
    private String kKljuc;
    @Column(name = "u_kljuc", length = 45)
    private String uKljuc;
    @Column(name = "n_kljuc", length = 45)
    private String nKljuc;
    @JoinColumn(name = "program_mjerenja_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ProgramMjerenja programMjerenja;
    private static final Logger log = Logger.getLogger(PrimateljProgramKljuceviMap.class.getName());

    public PrimateljProgramKljuceviMap() {
    }

    public PrimateljProgramKljuceviMap(Integer programMjerenjaId) {
        this.programMjerenjaId = programMjerenjaId;
    }

    public Integer getProgramMjerenjaId() {
        return programMjerenjaId;
    }

    public void setProgramMjerenjaId(Integer programMjerenjaId) {
        this.programMjerenjaId = programMjerenjaId;
    }

    public String getPKljuc() {
        return pKljuc;
    }

    public void setPKljuc(String pKljuc) {
        this.pKljuc = pKljuc;
    }

    public String getKKljuc() {
        return kKljuc;
    }

    public void setKKljuc(String kKljuc) {
        this.kKljuc = kKljuc;
    }

    public String getUKljuc() {
        return uKljuc;
    }

    public void setUKljuc(String uKljuc) {
        this.uKljuc = uKljuc;
    }

    public String getNKljuc() {
        return nKljuc;
    }

    public void setNKljuc(String nKljuc) {
        this.nKljuc = nKljuc;
    }

    public ProgramMjerenja getProgramMjerenja() {
        return programMjerenja;
    }

    public void setProgramMjerenja(ProgramMjerenja programMjerenja) {
        this.programMjerenja = programMjerenja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programMjerenjaId != null ? programMjerenjaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrimateljProgramKljuceviMap)) {
            return false;
        }
        PrimateljProgramKljuceviMap other = (PrimateljProgramKljuceviMap) object;
        if ((this.programMjerenjaId == null && other.programMjerenjaId != null) || (this.programMjerenjaId != null && !this.programMjerenjaId.equals(other.programMjerenjaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhz.skz.likz.aqdb.entity.PrimateljProgramKljuceviMap[ programMjerenjaId=" + programMjerenjaId + " ]";
    }

}
