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
@Table(name = "fichier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fichier.findAll", query = "SELECT f FROM Fichier f")
    , @NamedQuery(name = "Fichier.findByExtension", query = "SELECT f FROM Fichier f WHERE f.extension = :extension")
    , @NamedQuery(name = "Fichier.findByNom", query = "SELECT f FROM Fichier f WHERE f.nom = :nom")})
public class Fichier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "extension")
    private String extension;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "nom")
    private String nom;
    @JoinColumn(name = "repertoire", referencedColumnName = "chemin")
    @ManyToOne(optional = false)
    private Repertoire repertoire;

    public Fichier() {
    }

    public Fichier(String nom) {
        this.nom = nom;
    }

    public Fichier(String nom, String extension) {
        this.nom = nom;
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
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
        if (!(object instanceof Fichier)) {
            return false;
        }
        Fichier other = (Fichier) object;
        if ((this.nom == null && other.nom != null) || (this.nom != null && !this.nom.equals(other.nom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "helloworld.Fichier[ nom=" + nom + " ]";
    }
    
}
