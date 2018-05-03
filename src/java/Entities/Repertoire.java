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
@Table(name = "repertoire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Repertoire.findAll", query = "SELECT r FROM Repertoire r")
    , @NamedQuery(name = "Repertoire.findByChemin", query = "SELECT r FROM Repertoire r WHERE r.chemin = :chemin")
    , @NamedQuery(name = "Repertoire.findByNom", query = "SELECT r FROM Repertoire r WHERE r.nom = :nom")})
public class Repertoire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "chemin")
    private String chemin;
    @Basic(optional = false)
    @Column(name = "nom")
    private String nom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repertoire")
    private List<Fichier> fichierList;
    @JoinColumn(name = "utilisateur", referencedColumnName = "pseudo")
    @ManyToOne(optional = false)
    private Utilisateur utilisateur;

    public Repertoire() {
    }

    public Repertoire(String chemin) {
        this.chemin = chemin;
    }

    public Repertoire(String chemin, String nom) {
        this.chemin = chemin;
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlTransient
    public List<Fichier> getFichierList() {
        return fichierList;
    }

    public void setFichierList(List<Fichier> fichierList) {
        this.fichierList = fichierList;
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
        if (!(object instanceof Repertoire)) {
            return false;
        }
        Repertoire other = (Repertoire) object;
        if ((this.chemin == null && other.chemin != null) || (this.chemin != null && !this.chemin.equals(other.chemin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "helloworld.Repertoire[ chemin=" + chemin + " ]";
    }
    
}
