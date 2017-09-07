package com.bouami.danecreteil2017.Models;

/**
 * Created by mbouami on 16/08/2017.
 */

public class AnimateurModel extends Animateur {

    public String id;
    public String genre;
    public String nom;
    public String prenom;
    public String tel;
    public String email;
    public String photo;

    public AnimateurModel() {

    }

    public AnimateurModel(String id,String genre, String nom, String prenom, String tel, String email, String photo) {
        this.id = id;
        this.genre = genre;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.photo = photo;
    }

    public AnimateurModel(String genre, String nom, String prenom, String tel, String email, String photo) {
        this.genre = genre;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.photo = photo;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
