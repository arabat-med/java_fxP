package application;

import application.Person;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty prenom;
    private final SimpleStringProperty codeMassar;

    public Person(int id, String nom, String prenom, String codeMassar) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.codeMassar = new SimpleStringProperty(codeMassar);
        this.id = new SimpleIntegerProperty(id);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getCodeMassar() {
        return codeMassar.get();
    }

    public void setCodeMassar(String codeMassar) {
        this.codeMassar.set(codeMassar);
    }

    public int getId() {
        return id.get();
    }

}