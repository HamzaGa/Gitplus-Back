/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Samsung
 */
@Entity
@Table(name = "executable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Executable.findAll", query = "SELECT e FROM Executable e")
    , @NamedQuery(name = "Executable.findByChemin", query = "SELECT e FROM Executable e WHERE e.chemin = :chemin")
    , @NamedQuery(name = "Executable.findByLien", query = "SELECT e FROM Executable e WHERE e.lien = :lien")
    , @NamedQuery(name = "Executable.findByExtension", query = "SELECT e FROM Executable e WHERE e.extension = :extension")})
public class Executable implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "executable")
    private List<Bd> bdList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "chemin")
    private String chemin;
    @Basic(optional = false)
    @Column(name = "lien")
    private String lien;
    @Basic(optional = false)
    @Column(name = "extension")
    private String extension;
    @JoinColumn(name = "utilisateur", referencedColumnName = "pseudo")
    @ManyToOne(optional = false)
    private Utilisateur utilisateur;

    public Executable() {
    }

    public Executable(String chemin) {
        this.chemin = chemin;
    }

    public Executable(String chemin, String lien, String extension) {
        this.chemin = chemin;
        this.lien = lien;
        this.extension = extension;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chemin != null ? chemin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Executable)) {
            return false;
        }
        Executable other = (Executable) object;
        if ((this.chemin == null && other.chemin != null) || (this.chemin != null && !this.chemin.equals(other.chemin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "helloworld.Executable[ chemin=" + chemin + " ]";
    }

    @XmlTransient
    public List<Bd> getBdList() {
        return bdList;
    }

    public void setBdList(List<Bd> bdList) {
        this.bdList = bdList;
    }
    
}
