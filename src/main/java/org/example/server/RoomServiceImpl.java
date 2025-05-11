package org.example.server;

import org.example.server.bean.Utilisateur;
import org.example.server.bean.Message;
import org.example.server.bean.UserList;



import javax.jws.WebService;
import java.util.List;
import java.util.ArrayList;


@WebService(endpointInterface = "org.example.server.RoomService")
public class RoomServiceImpl implements RoomService {

    private final ArrayList<Utilisateur> userList;

    public RoomServiceImpl() {
        this.userList = new ArrayList<Utilisateur>();
    }

    @Override
    public boolean subscribe(String pseudo) {
        Utilisateur u = this.getUser(pseudo);
        synchronized (userList) {
            if (u != null) {
                return false;
            }
            userList.add(new Utilisateur(pseudo));

            // Afficher du côté du serveur qu'un nouvel utilisateur vient de se connecter
            System.out.println(pseudo + " vient de se connecter.");

            // Notifier les autres utilisateurs dans le chatroom
            Message m = new Message("Système", pseudo + " s'est connecté(e).");
            for (Utilisateur other : userList) {
                if (!other.getPseudo().equals(pseudo)) {
                    other.getListMsg().add(m);
                }
            }

            return true;

        }
    }

    @Override
    public boolean unsubscribe(String pseudo) {
        Utilisateur u = this.getUser(pseudo);
        synchronized (userList) {
            if (u == null) {
                return false;
            }
            userList.remove(u);

            System.out.println(pseudo + " s'est déconnecté(e).");

            Message m = new Message("Système", pseudo + " a quitté la discussion.");
            for (Utilisateur other : userList) {
                other.getListMsg().add(m);
            }

            return true;

        }
    }

    @Override
    public String getMessageUser(String pseudo) {
        Utilisateur u = this.getUser(pseudo);
        synchronized (userList) {
            if (u == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (Message m : u.getListMsg()) {
                sb.append(m.toString()).append("\n");
            }
            u.getListMsg().clear();
            return sb.toString();
        }
        //return null;
    }

    @Override
    public void postMsg(String pseudo, String Message) {
        //Utilisateur u = this.getUser(pseudo);
        Message m = new Message(pseudo, Message);
        synchronized (userList) {
            for (Utilisateur u : userList) {
                u.getListMsg().add(m);
            }//
        }

    }

    @Override
    public UserList getConnectedUsers() {
        List<String> pseudos = new ArrayList<>();
        synchronized (userList) {
            for (Utilisateur u : userList) {
                pseudos.add(u.getPseudo());
            }
        }
        return new UserList(pseudos);
    }


    private Utilisateur getUser(String pseudo) {
        for (Utilisateur u : userList) {
            if (u.getPseudo().equals(pseudo)) {
                return u;
            }
        }
        return null;
    }

}
