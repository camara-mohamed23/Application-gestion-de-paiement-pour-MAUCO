/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

/**
 *
 * @author camaramohamed
 */
public class Employé {
        private int id;
        private String nom;
        private String prénom;  
        private String statut;  
        private String adresse;  
        private String temps_travail;
        private String type_contrat;  
        private String poste_occupé; 
        private String salaire;  
        private String telephone;  
        
        
// le constructeur de la classe
    public Employé(int id, String nom, String prénom, String statut, String adresse, String temps_travail, String type_contrat, String poste_occupé, String salaire, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prénom = prénom;
        this.statut = statut;
        this.adresse = adresse;
        this.temps_travail = temps_travail;
        this.type_contrat = type_contrat;
        this.poste_occupé = poste_occupé;
        this.salaire = salaire;
        this.telephone = telephone;
    }
    
    // les getters de la classe

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrénom() {
        return prénom;
    }

    public String getStatut() {
        return statut;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTemps_travail() {
        return temps_travail;
    }

    public String getType_contrat() {
        return type_contrat;
    }

    public String getPoste_occupé() {
        return poste_occupé;
    }

    public String getSalaire() {
        return salaire;
    }

    public String getTelephone() {
        return telephone;
    }
    
    // les setters de classes

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrénom(String prénom) {
        this.prénom = prénom;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTemps_travail(String temps_travail) {
        this.temps_travail = temps_travail;
    }

    public void setType_contrat(String type_contrat) {
        this.type_contrat = type_contrat;
    }

    public void setPoste_occupé(String poste_occupé) {
        this.poste_occupé = poste_occupé;
    }

    public void setSalaire(String salaire) {
        this.salaire = salaire;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
        
    
}

