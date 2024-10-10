package com.monprojet;

import java.util.ArrayList;

public class Utilisateur {
    private String nom;
    private ArrayList<Reservation> reservations;

    public Utilisateur(String nom) {
        this.nom = nom;
        this.reservations = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void reserverBillet(Reservation reservation) {
        reservations.add(reservation); // Ajoute la réservation à l'utilisateur
    }
}
