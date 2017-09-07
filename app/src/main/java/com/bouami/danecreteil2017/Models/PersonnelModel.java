package com.bouami.danecreteil2017.Models;

/**
 * Created by mbouami on 16/08/2017.
 */

public class PersonnelModel {

    public String id;
    public String genre;
    public String nom;
    public String prenom;
    public String statut;

    public PersonnelModel() {

    }

    public PersonnelModel(String id, String genre, String nom, String prenom, String statut) {
        this.id = id;
        this.genre = genre;
        this.nom = nom;
        this.prenom = prenom;
        this.statut = statut;
    }

    public PersonnelModel(String genre, String nom, String prenom, String statut) {
        this.genre = genre;
        this.nom = nom;
        this.prenom = prenom;
        this.statut = statut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
