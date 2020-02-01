package com.snovaks.server;

import com.snovaks.data.PhoneBookDataService;
import com.snovaks.protocol.NuclearProtocol;
import com.snovaks.protocol.NuclearProtocolTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Pattern;

public class ServerService {

    private Server server;

    private BufferedReader in;
    private PrintWriter out;

    private static final Pattern REQUEST_PATTERN = Pattern.compile(" +", Pattern.CASE_INSENSITIVE);

    private PhoneBookDataService phoneBookDataService;

    private ServerService(Server server) {
        this.server = server;
        phoneBookDataService = new PhoneBookDataService();
    }

    public static ServerService createNewServerService(Server server) {
        return new ServerService(server);
    }

    public void initializePhoneBookData(String fileName) {
        phoneBookDataService.loadDataFromTheFile(fileName);
    }

    public void handleConnection(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            for (String line; (line = in.readLine()) != null ;) {
                String[] request = REQUEST_PATTERN.split(line, 3);
                handleRequest(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(String[] request) {
        NuclearProtocol nuclearProtocolMethod = NuclearProtocolTranslator.translateFromString(request[0]);

        switch (nuclearProtocolMethod) {
            case BYE:
                handleBye();
                break;
            case GET:
                handleGet(request);
                break;
            case ADD:
                handleAdd();
                break;
            case REPLACE:
                handleReplace();
                break;
            case BAD_REQUEST:
                handleBadRequest();
                break;
        }
    }

    private void handleBye() {
        writeResponse(NuclearProtocol.BYE, "nara elo");
        turnOffServer();
    }

    private void handleGet(String[] request) {
        if (request.length != 2) {
            writeResponse(NuclearProtocol.BAD_REQUEST, null);
        } else {
            String phoneNumber = phoneBookDataService.getPhoneNumber(request[1]);
            if (phoneNumber == null) {
                writeResponse(NuclearProtocol.NOT_FOUND, null);
            }
            writeResponse(NuclearProtocol.OK, phoneNumber);
        }
    }

    private void handleAdd() {
    }

    private void handleReplace() {
    }

    private void handleBadRequest() {
    }

    private void writeResponse(NuclearProtocol nuclearProtocol, String message) {
        int methodCode = NuclearProtocolTranslator.getCode(nuclearProtocol);
        String methodName = NuclearProtocolTranslator.getProtocolMethodName(methodCode);
        out.println(methodCode + " " + methodName);
        if (message != null) {
            out.println(message);
        }
    }

    private void turnOffServer() {
        server.setServerRunning(false);
    }

}