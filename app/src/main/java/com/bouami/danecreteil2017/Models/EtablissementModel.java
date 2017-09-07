package com.bouami.danecreteil2017.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mbouami on 16/08/2017.
 */

public class EtablissementModel {
    public String rne;
    public String type;
    public String nom;
    public String fax;
    public String tel;
    public String email;
    public String cp;
    public String ville;
    public String adresse;
    public Map<String, Boolean> animateurs = new HashMap<>();
    public Map<String, Boolean> personnel = new HashMap<>();


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("rne", rne);
        result.put("type", type);
        result.put("nom", nom);
        result.put("fax", fax);
        result.put("tel", tel);
        result.put("email", email);
        result.put("cp", cp);
        result.put("ville", ville);
        result.put("adresse", adresse);
        result.put("animateurs", animateurs);
        result.put("personnel", personnel);
        return result;
    }

    public EtablissementModel() {

    }

    public EtablissementModel(String rne, String type, String nom, String fax, String tel, String email, String cp, String ville, String adresse) {
        this.rne = rne;
        this.type = type;
        this.nom = nom;
        this.fax = fax;
        this.tel = tel;
        this.email = email;
        this.cp = cp;
        this.ville = ville;
        this.adresse = adresse;
    }


    public EtablissementModel(String rne, String type, String nom, String fax, String tel, String email, String cp, String ville, String adresse, Map<String, Boolean> animateurs, Map<String, Boolean> personnel) {
        this.rne = rne;
        this.type = type;
        this.nom = nom;
        this.fax = fax;
        this.tel = tel;
        this.email = email;
        this.cp = cp;
        this.ville = ville;
        this.adresse = adresse;
        this.animateurs = animateurs;
        this.personnel = personnel;
    }

    public String getRne() {
        return rne;
    }

    public void setRne(String rne) {
        this.rne = rne;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Map<String, Boolean> getAnimateurs() {
        return animateurs;
    }

    public void setAnimateurs(Map<String, Boolean> animateurs) {
        this.animateurs = animateurs;
    }

    public Map<String, Boolean> getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Map<String, Boolean> personnel) {
        this.personnel = personnel;
    }
}
