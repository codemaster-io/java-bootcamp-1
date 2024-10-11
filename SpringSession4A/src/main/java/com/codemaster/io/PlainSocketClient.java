package com.codemaster.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class PlainSocketClient {

    public static void main(String[] args) {
        String serverAddress = "localhost"; // or the IP address of your server
        int port = 8080; // The same port the server is listening on

        try (Socket socket = new Socket(serverAddress, port)) {
            // Send an HTTP POST request
            String request = "POST / HTTP/1.1\r\n" +
                    "Host: " + serverAddress + "\r\n" +
                    "Content-Type: application/x-www-form-urlencoded\r\n" +
                    "Content-Length: 10\r\n" +
                    "\r\n" +
                    "data=value"; // Sample body data


            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(request.getBytes());
            outputStream.flush();

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
                // Break on the first empty line indicating the end of the headers
//                if (line.isEmpty()) {
//                    break;
//                }
            }

            System.out.println("Server Response:\n" + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
