package org.example.server;

import javax.xml.ws.Endpoint;

public class Server {

    public static void main(String[] z) {
        Endpoint.publish("http://localhost:8080/ws/roomService", new RoomServiceImpl());
        System.out.println("Service is running");
    }

}
