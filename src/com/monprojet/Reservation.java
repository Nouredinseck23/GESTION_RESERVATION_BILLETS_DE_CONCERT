package com.monprojet;

public class Reservation {
    private Evenement evenement;
    private int nombreBillets;

    public Reservation(Evenement evenement, int nombreBillets) {
        this.evenement = evenement;
        this.nombreBillets = nombreBillets;
    }

    @Override
    public String toString() {
        return "Événement: " + evenement + ", Nombre de billets: " + nombreBillets;
    }
}
