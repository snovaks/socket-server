package com.snovaks.server;

import com.snovaks.data.PhoneBookData;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Getter
public class Server {

    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "2301";

    @Setter
    private boolean serverRunning;
    private final String host;
    private final int port;
    private final InetSocketAddress inetSocketAddress;
    private ServerSocket serverSocket;

    private ServerService serverService;

    private Server(String host, String port, String fileName) {
        serverService = ServerService.createNewServerService(this);
        serverService.initializePhoneBookData(fileName);
        this.host = host;
        this.port = Integer.parseInt(port);
        inetSocketAddress = createInetSocketAddress(this.host, this.port);
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(inetSocketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serviceConnections();
    }

    public static Server createDefaultServer() {
        Server server = new Server(DEFAULT_HOST, DEFAULT_PORT, null);
        return server;
    }

    public static Server createCustomServer(String host, String port) {
        Server server = new Server(host, port, null);
        return server;
    }

    public static Server createCustomServerWithData(String host, String port, String fileName) {
        Server server = new Server(host, port, fileName);
        return server;
    }

    private InetSocketAddress createInetSocketAddress(String host, int port) {
        return new InetSocketAddress(host, port);
    }

    private void serviceConnections() {
        serverRunning = true;

        while (serverRunning) {
            try {
                Socket connection = serverSocket.accept();
                System.out.println("Connection established");
                serverService.handleConnection(connection);
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}