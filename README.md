# 💬 Chatroom SOAP - Java Swing

Une simple **application de messagerie multiclients** développée en **Java** 
Elle utilise les **Web Services SOAP (JAX-WS)** côté serveur, et une une **interface graphique Swing** côté client.

--- 

Chaque utilisateur peut :

    . Se connecter avec un pseudo

    . Voir les autres utilisateurs connectés

    . Envoyer et recevoir des messages horodatés

    . Recevoir des notifications système (connexion/déconnexion)

    . Vider son écran de discussion à tout moment

Le tout repose sur une architecture client/serveur distribuée via SOAP.

---

## Architecture

    - Backend : **Web Service SOAP** (`RoomService`)
    - Frontend : **Interface Swing Java** (`Gui.java`)
    - Communication par **appel distant SOAP** via `javax.xml.ws`

---

## Pour lancer l'application

### 1. Démarrer le serveur SOAP :
```
Run Server.java
```
Le service sera disponible sur :
http://localhost:8080/ws/roomService?wsdl

### 2. Démarrer les clients GUI :
``` Run Gui.java ``` 
Plusieurs fois : pour avoir plusieurs instances / utilisateurs différents

### 3. Technologies :

    - Java 8+ (Le JDK 1.8 est préféré)
    - JAX-WS (SOAP)
    - Swing
    - Maven 

---
