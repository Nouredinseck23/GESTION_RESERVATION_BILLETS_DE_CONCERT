package com.monprojet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GestionReservation extends JFrame {
    private ArrayList<Evenement> evenements = new ArrayList<>();
    private ArrayList<Utilisateur> utilisateurs = new ArrayList<>(); // Liste des utilisateurs
    private JTextArea textArea;
    private Utilisateur currentUser; // Utilisateur courant

    public GestionReservation() {
        setTitle("Réservation de Billet de Concert");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Création de la zone de texte pour afficher les événements et réservations
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Ajout du panneau pour le menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1)); // Vertical layout
        add(menuPanel, BorderLayout.EAST); // Ajout à la droite pour une meilleure visibilité

        // Création des boutons du menu
        JButton displayEventsButton = new JButton("Afficher les Événements");
        JButton reserveButton = new JButton("Réserver un Billet");
        JButton viewReservationsButton = new JButton("Afficher mes Réservations");
        JButton adminButton = new JButton("Gérer les Événements (Admin)");
        JButton quitButton = new JButton("Quitter");

        // Ajout des boutons au panneau du menu
        menuPanel.add(displayEventsButton);
        menuPanel.add(reserveButton);
        menuPanel.add(viewReservationsButton);
        menuPanel.add(adminButton);
        menuPanel.add(quitButton);

        // Ajout des actions aux boutons
        displayEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherEvenements();
            }
        });

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserverBillets();
            }
        });

        viewReservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherMesReservations();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authentifierAdmin();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Quitte l'application
            }
        });
    }

    private void authentifierAdmin() {
        String password = JOptionPane.showInputDialog(this, "Entrez le mot de passe Admin :");
        if ("admin123".equals(password)) {
            JOptionPane.showMessageDialog(this, "Connexion réussie en tant qu'Admin !");
            ouvrirOptionsAdmin();
        } else {
            JOptionPane.showMessageDialog(this, "Mot de passe incorrect !");
        }
    }

    private void ouvrirOptionsAdmin() {
        JFrame adminFrame = new JFrame("Options Admin");
        adminFrame.setSize(400, 300);
        adminFrame.setLayout(new GridLayout(0, 1));

        JButton addEventButton = new JButton("Ajouter un Événement");
        JButton modifyEventButton = new JButton("Modifier un Événement");
        JButton deleteEventButton = new JButton("Supprimer un Événement");

        adminFrame.add(addEventButton);
        adminFrame.add(modifyEventButton);
        adminFrame.add(deleteEventButton);

        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEvenement();
            }
        });

        modifyEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierEvenement();
            }
        });

        deleteEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerEvenement();
            }
        });

        adminFrame.setVisible(true);
    }

    private void ajouterEvenement() {
        String artiste = JOptionPane.showInputDialog(this, "Entrez le nom de l'artiste :");
        String date = JOptionPane.showInputDialog(this, "Entrez la date (JJ/MM/AAAA) :");
        String lieu = JOptionPane.showInputDialog(this, "Entrez le lieu :");
        int nbBillets = Integer.parseInt(JOptionPane.showInputDialog(this, "Entrez le nombre de billets disponibles :"));

        evenements.add(new Evenement(artiste, date, lieu, nbBillets));
        JOptionPane.showMessageDialog(this, "Événement ajouté avec succès !");
    }

    private void modifierEvenement() {
        if (evenements.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun événement disponible pour modification.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sélectionnez un événement à modifier :\n");
        for (int i = 0; i < evenements.size(); i++) {
            sb.append((i + 1)).append(". ").append(evenements.get(i)).append("\n");
        }

        int choix = Integer.parseInt(JOptionPane.showInputDialog(this, sb.toString())) - 1;

        if (choix >= 0 && choix < evenements.size()) {
            Evenement evenement = evenements.get(choix);
            String nouvelleDate = JOptionPane.showInputDialog(this, "Nouvelle date :", evenement.getDate());
            String nouveauLieu = JOptionPane.showInputDialog(this, "Nouveau lieu :", evenement.getLieu());
            int nouveauxBillets = Integer.parseInt(JOptionPane.showInputDialog(this, "Nouveau nombre de billets :", evenement.getNbBilletsDisponibles()));

            evenement.modifierEvenement(nouvelleDate, nouveauLieu, nouveauxBillets);
            JOptionPane.showMessageDialog(this, "Événement modifié avec succès !");
        } else {
            JOptionPane.showMessageDialog(this, "Événement non valide.");
        }
    }

    private void supprimerEvenement() {
        if (evenements.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun événement disponible pour suppression.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sélectionnez un événement à supprimer :\n");
        for (int i = 0; i < evenements.size(); i++) {
            sb.append((i + 1)).append(". ").append(evenements.get(i)).append("\n");
        }

        int choix = Integer.parseInt(JOptionPane.showInputDialog(this, sb.toString())) - 1;

        if (choix >= 0 && choix < evenements.size()) {
            evenements.remove(choix);
            JOptionPane.showMessageDialog(this, "Événement supprimé avec succès !");
        } else {
            JOptionPane.showMessageDialog(this, "Événement non valide.");
        }
    }

    private void afficherEvenements() {
        if (evenements.isEmpty()) {
            textArea.setText("Aucun événement disponible.");
            return;
        }

        StringBuilder sb = new StringBuilder("Événements disponibles :\n");
        for (Evenement evenement : evenements) {
            sb.append(evenement).append("\n");
        }

        textArea.setText(sb.toString());
    }

    private void afficherMesReservations() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Veuillez d'abord réserver un billet.");
            return;
        }

        ArrayList<Reservation> userReservations = currentUser.getReservations();
        if (userReservations.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune réservation trouvée.");
            return;
        }

        StringBuilder sb = new StringBuilder("Mes Réservations :\n");
        for (Reservation reservation : userReservations) {
            sb.append(reservation).append("\n");
        }

        textArea.setText(sb.toString());
    }

    private void reserverBillets() {
        String nomUtilisateur = JOptionPane.showInputDialog(this, "Entrez votre nom :");

        // Chercher si l'utilisateur existe déjà
        currentUser = trouverUtilisateur(nomUtilisateur);
        if (currentUser == null) {
            currentUser = new Utilisateur(nomUtilisateur);
            utilisateurs.add(currentUser); // Ajouter l'utilisateur à la liste
        }

        if (evenements.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun événement disponible pour réservation.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sélectionnez un événement pour réserver des billets :\n");
        for (int i = 0; i < evenements.size(); i++) {
            sb.append((i + 1)).append(". ").append(evenements.get(i)).append("\n");
        }

        int choix = Integer.parseInt(JOptionPane.showInputDialog(this, sb.toString())) - 1;

        if (choix >= 0 && choix < evenements.size()) {
            Evenement evenement = evenements.get(choix);
            try {
                int nombreBillets = Integer.parseInt(JOptionPane.showInputDialog(this, "Combien de billets souhaitez-vous réserver ?"));
                evenement.reserverBillets(nombreBillets);
                currentUser.reserverBillet(new Reservation(evenement, nombreBillets)); // Ajout de la réservation à l'utilisateur
                JOptionPane.showMessageDialog(this, "Réservation réussie !");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Événement non valide.");
        }
    }

    private Utilisateur trouverUtilisateur(String nom) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getNom().equals(nom)) {
                return utilisateur; // Retourne l'utilisateur s'il existe
            }
        }
        return null; // Aucun utilisateur trouvé
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionReservation gestionReservation = new GestionReservation();
            gestionReservation.setVisible(true);
        });
    }
}
