package com.codemaster.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class PlainSocketServer {

    public static void main(String[] args) {

        int port = 8080; // Specify the port to listen on
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                // Accept incoming client connections
                Socket socket = serverSocket.accept();
                writeClientInfo(socket);
                // Handle the client request in a new thread
                new Thread(() -> handleRequest(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket socket) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream outputStream = socket.getOutputStream();

            String header = readHTTPHeader(reader);
            if (header == null) return;

            String body = readHTTPBody(reader, header);

            System.out.println("\n\nIncoming HTTP Request: \n");
            System.out.println(header);
            System.out.println(body);

            String response = writeHTTPResponse(outputStream);
            System.out.println("\n\nOutgoing HTTP Response: \n\n" + response);



        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String writeHTTPResponse(OutputStream outputStream) {
        try {
            String sessionId = UUID.randomUUID().toString();
            // Send a response back to the client
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/plain\r\n" +
//                    "Content-Length: 22\r\n" +
                    "Set-Cookie: JSESSIONID=" + sessionId + "; HttpOnly\r\n" +  // Add the session cookie
                    "\r\n" +
                    "Received your request!";
            outputStream.write(response.getBytes());
            outputStream.flush();
            return response;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static void writeClientInfo(Socket socket) {
        String clientIP = socket.getInetAddress().getHostAddress();
        int clientPort = socket.getPort();
        System.out.println("ClientInfo: " + clientIP + ":" + clientPort);
    }

    public static String readHTTPHeader(BufferedReader reader) {
        StringBuilder headers = new StringBuilder();

        try {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                headers.append(line).append("\r\n");
            }

            if(headers.toString().isEmpty()) return null;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return headers.toString();
    }

    public static String readHTTPBody(BufferedReader reader, String header) {
        StringBuilder body = new StringBuilder();

        try {

            // Read the body (if any)
            if (header.startsWith("POST")) {
                // The body length is specified by the "Content-Length" header
                String contentLengthHeader = header.lines()
                        .filter(line -> line.toLowerCase().startsWith("content-length"))
                        .findFirst().orElse("");

                int contentLength = 0;

                if (!contentLengthHeader.isEmpty()) {
                    contentLength = Integer.parseInt(contentLengthHeader.split(":")[1].trim());
                }

                // Read the specified number of bytes from the input stream
                char[] bodyBuffer = new char[contentLength];
                reader.read(bodyBuffer, 0, contentLength);
                body.append(bodyBuffer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return body.toString();
    }
}