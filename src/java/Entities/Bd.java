/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Samsung
 */
@Entity
@Table(name = "bd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bd.findAll", query = "SELECT b FROM Bd b")
    , @NamedQuery(name = "Bd.findByNom", query = "SELECT b FROM Bd b WHERE b.nom = :nom")
    , @NamedQuery(name = "Bd.findByChemin", query = "SELECT b FROM Bd b WHERE b.chemin = :chemin")})
public class Bd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chemin")
    private String chemin;
    @JoinColumn(name = "executable", referencedColumnName = "chemin")
    @ManyToOne(optional = false)
    private Executable executable;

    public Bd() {
    }

    public Bd(String nom) {
        this.nom = nom;
    }

    public Bd(String nom, String chemin) {
        this.nom = nom;
        this.chemin = chemin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public Executable getExecutable() {
        return executable;
    }

    public void setExecutable(Executable executable) {
        this.executable = executable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nom != null ? nom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bd)) {
            return false;
        }
        Bd other = (Bd) object;
        if ((this.nom == null && other.nom != null) || (this.nom != null && !this.nom.equals(other.nom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Bd[ nom=" + nom + " ]";
    }
    
}
