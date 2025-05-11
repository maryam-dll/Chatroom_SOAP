package org.example.client;

import java.awt.*;
import java.net.MalformedURLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.example.server.bean.UserList;
import java.util.List;

public class Gui extends JFrame {

    private JPanel contentPane;
    private JTextField zoneTexte;
    private JTextArea zoneMsg;
    private JTextArea zoneUsers;
    private String pseudo;
    private Client service;
    private Thread updateMsg;
    private Timer userUpdateTimer;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Gui frame = new Gui();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Gui() {
        ihm();
        try {
            service = new Client();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        requestPseudo();

        // Thread de récupération des messages
        updateMsg = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                updateZoneMessage();
            }
        });
        updateMsg.start();

        // Timer Swing pour mettre à jour la liste des utilisateurs toutes les 2 secondes
        userUpdateTimer = new Timer(2000, e -> updateConnectedUsers());
        userUpdateTimer.start();

        // Gérer la fermeture propre de la fenêtre
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (pseudo != null && !pseudo.trim().isEmpty()) {
                    service.desinscription(pseudo);
                    System.out.println(pseudo + " a quitté la chatroom.");
                }
                // Laisser l'application se fermer normalement
            }
        });

    }

    private void ihm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Zone de messages
        zoneMsg = new JTextArea();
        zoneMsg.setLineWrap(true);
        zoneMsg.setWrapStyleWord(true);
        zoneMsg.setEditable(false);
        contentPane.add(new JScrollPane(zoneMsg), BorderLayout.CENTER);

        // Zone de saisie en bas
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        zoneTexte = new JTextField();
        panel.add(zoneTexte);
        zoneTexte.setColumns(28);

        JButton btnSend = new JButton("Envoyer");
        panel.add(btnSend);
        btnSend.addActionListener(e -> {
            String msg = zoneTexte.getText().trim();
            if (msg.isEmpty()) msg = "";
            service.getRoom().postMsg(pseudo, msg);
            zoneTexte.setText("");
        });

        // Effacer le chat
        JButton btnClear = new JButton("Effacer");
        panel.add(btnClear);
        btnClear.addActionListener(e -> zoneMsg.setText(""));

        // Liste des utilisateurs connectés
        zoneUsers = new JTextArea();
        zoneUsers.setEditable(false);
        zoneUsers.setPreferredSize(new Dimension(150, 0));
        zoneUsers.setBorder(BorderFactory.createTitledBorder("Connectés"));
        contentPane.add(zoneUsers, BorderLayout.EAST);
    }


    private void requestPseudo() {
        pseudo = JOptionPane.showInputDialog("Veuillez renseigner le pseudo");
        setTitle("Chatroom - " + pseudo);
        if (pseudo == null || pseudo.trim().isEmpty()) {
            System.exit(0);
        }
        pseudo = pseudo.trim();
        System.out.println(pseudo + " s'est connecté(e).");
        service.inscription(pseudo);
    }

    protected void updateZoneMessage() {
        String messages = service.getRoom().getMessageUser(pseudo);
        if (!messages.trim().isEmpty()) {
            this.zoneMsg.append(messages);
        }
    }

    private void updateConnectedUsers() {
        UserList listWrapper = service.getRoom().getConnectedUsers();
        List<String> users = listWrapper.getUsers();

        StringBuilder list = new StringBuilder();
        for (String u : users) {
            list.append("- ").append(u).append("\n");
        }

        zoneUsers.setText(list.toString());
    }

}
