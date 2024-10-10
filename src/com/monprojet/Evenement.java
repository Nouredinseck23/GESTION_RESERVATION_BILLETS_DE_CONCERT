package com.monprojet;

public class Evenement {
    private String artiste;
    private String date;
    private String lieu;
    private int nbBilletsDisponibles;

    public Evenement(String artiste, String date, String lieu, int nbBilletsDisponibles) {
        this.artiste = artiste;
        this.date = date;
        this.lieu = lieu;
        this.nbBilletsDisponibles = nbBilletsDisponibles;
    }

    public void reserverBillets(int nombre) throws Exception {
        if (nombre > nbBilletsDisponibles) {
            throw new Exception("Pas assez de billets disponibles.");
        }
        nbBilletsDisponibles -= nombre;
    }

    public void modifierEvenement(String nouvelleDate, String nouveauLieu, int nouveauxBillets) {
        this.date = nouvelleDate;
        this.lieu = nouveauLieu;
        this.nbBilletsDisponibles = nouveauxBillets;
    }

    @Override
    public String toString() {
        return artiste + " - " + date + " à " + lieu + " (" + nbBilletsDisponibles + " billets disponibles)";
    }

    // Getters
    public String getArtiste() {
        return artiste;
    }

    public String getDate() {
        return date;
    }

    public String getLieu() {
        return lieu;
    }

    public int getNbBilletsDisponibles() {
        return nbBilletsDisponibles;
    }
}
