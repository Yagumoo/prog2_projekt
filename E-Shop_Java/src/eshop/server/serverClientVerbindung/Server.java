package eshop.server.serverClientVerbindung;

import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.server.domain.E_Shop;

import java.net.*;
import java.io.*;

public class Server {

    private ServerSocket serverSocket;
    private E_Shop eShop = new E_Shop();

    public Server(int port) {
        try {
            // erstellt ServerSocket mit dem angegeben Port, muss der gleiche Port sein wie der auf dem der Client versucht zu verbinden
            serverSocket = new ServerSocket(port);
            System.out.println("Sever hört auf Port: " + port);
        } catch (IOException e) {
            System.err.println("Sever ist NICHT geschlossen: " + port);
        }

        try {
            // Endlosschleife um auf Client Verbindungsanfragen zu warten
            while (true) {
                // akzeptiert immer wieder die Verbindung eines neuen Clients
                Socket newClientSocket = serverSocket.accept();

                // erstellt neuen Thread für den Client
                ClientRequestProcessor clientHandler = new ClientRequestProcessor(newClientSocket, eShop);
                // startet den Thread
                clientHandler.start();

                System.out.println("Connected to a Client");
            }
        } catch (IOException e) {
            System.err.println("Connection failed");
        }

    }

    public static void main(String[] args) {
        // Server erstellen
        Server server = new Server(1028);
    }
}