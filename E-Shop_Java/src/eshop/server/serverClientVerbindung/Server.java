package eshop.server.serverClientVerbindung;
/**
 * Author Maximilian Stenzel
 * Author Simon Brockhoff
 * Author Marvin Klattenhoff
 * */
import eshop.client.clientServerVerbindung.Eshopclientsite;
import eshop.server.domain.E_Shop;

import java.net.*;
import java.io.*;
/**
 * Repräsentiert den Server für das E-Shop-System.
 *
 * Die Klasse `Server` verwaltet die Netzwerkverbindungen zu den Clients und leitet Anfragen von Clients an die entsprechenden
 * Verarbeitungsmechanismen weiter. Der Server läuft auf einem bestimmten Port und akzeptiert Verbindungen von Clients, wobei
 * für jede eingehende Verbindung ein neuer Thread gestartet wird, um die Anfragen des Clients zu bearbeiten. Die Anfragen
 * werden durch die Klasse {@link ClientRequestProcessor} verarbeitet.
 *
 * <p>
 * Der Server startet auf einem angegebenen Port und wartet in einer Endlosschleife auf eingehende Verbindungen. Bei einer
 * neuen Verbindung wird ein neuer {@link ClientRequestProcessor}-Thread erstellt und gestartet, um die Kommunikation mit dem
 * Client zu übernehmen.
 * </p>
 *
 * <p>
 * In der `main`-Methode wird eine Instanz des Servers erstellt und auf Port 1028 gestartet. Dieser Port muss sowohl im Server
 * als auch im Client übereinstimmen.
 * </p>
 */
public class Server {

    private ServerSocket serverSocket;
    private E_Shop eShop = new E_Shop();
    /**
     * Erzeugt einen neuen Server, der auf dem angegebenen Port Verbindungen akzeptiert.
     *
     * Der Konstruktor initialisiert den {@link ServerSocket} mit dem angegebenen Port und startet die Endlosschleife, um auf
     * eingehende Verbindungen von Clients zu warten. Für jede Verbindung wird ein neuer {@link ClientRequestProcessor}-Thread
     * erstellt und gestartet, der für die Bearbeitung der Anfragen des Clients zuständig ist.
     *
     * @param port Der Port, auf dem der Server Verbindungen akzeptieren soll. Dieser Port muss identisch mit dem Port sein,
     *             den der Client zum Verbinden verwendet.
     */
    public Server(int port) {
        try {
            // erstellt ServerSocket mit dem angegeben Port, muss der gleiche Port sein wie der auf dem der Client versucht zu verbinden
            serverSocket = new ServerSocket(port);
            System.out.println("Server hört auf Port: " + port);
        } catch (IOException e) {
            System.err.println("Server ist NICHT geschlossen: " + port);
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

    /**
     * Startet den Server auf Port 1028.
     *
     * Die `main`-Methode erstellt eine Instanz des Servers und startet ihn auf Port 1028. Dieser Port muss in der Client-Anwendung
     * ebenfalls konfiguriert sein, um eine erfolgreiche Verbindung herzustellen.
     *
     * @param args Kommandozeilenargumente (werden in dieser Implementierung nicht verwendet).
     */
    public static void main(String[] args) {
        // Server erstellen
        Server server = new Server(1028);
    }
}