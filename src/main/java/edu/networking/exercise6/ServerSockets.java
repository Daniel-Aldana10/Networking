package edu.networking.exercise6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ServerSockets {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Esperando conexiones...");

            while (true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Cliente conectado");

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    OutputStream out = clientSocket.getOutputStream();


                    String requestLine = in.readLine();
                    System.out.println("Petición: " + requestLine);

                    int contentLength = 0;
                    String header;
                    while ((header = in.readLine()) != null && !header.isEmpty()) {
                        if (header.toLowerCase().startsWith("content-length:")) {
                            contentLength = Integer.parseInt(header.split(":")[1].trim());
                        }
                    }


                    char[] bodyChars = new char[contentLength];
                    in.read(bodyChars);
                    String urlReceived = new String(bodyChars).trim();
                    System.out.println("URL solicitada: " + urlReceived);


                    URL url = new URL(urlReceived);
                    URLConnection connection = url.openConnection();
                    String contentType = connection.getContentType();

                    boolean esTexto = contentType != null && contentType.startsWith("text/");

                    byte[] contentBytes;

                    if (esTexto) {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        reader.close();
                        contentBytes = sb.toString().getBytes(StandardCharsets.UTF_8);
                    } else {

                        InputStream inputStream = connection.getInputStream();
                        contentBytes = inputStream.readAllBytes();
                        inputStream.close();
                    }


                    String httpResponse = """
                        HTTP/1.1 200 OK\r
                        Content-Type: %s\r
                        Content-Length: %d\r
                        Access-Control-Allow-Origin: *\r
                        \r
                        """.formatted(contentType, contentBytes.length);

                    out.write(httpResponse.getBytes(StandardCharsets.UTF_8));
                    out.write(contentBytes);
                    out.flush();
                } catch (Exception e) {
                    System.err.println("Error al procesar solicitud: " + e.getMessage());

                    if (clientSocket != null) {
                        try {
                            OutputStream out = clientSocket.getOutputStream();
                            String errorMessage = "<p>Error: " + e.getMessage() + "</p>";
                            String httpResponse = """
                                HTTP/1.1 500 Internal Server Error\r
                                Content-Type: text/html\r
                                Content-Length: %d\r
                                Access-Control-Allow-Origin: *\r
                                \r
                                """.formatted(errorMessage.getBytes().length);

                            out.write(httpResponse.getBytes(StandardCharsets.UTF_8));
                            out.write(errorMessage.getBytes(StandardCharsets.UTF_8));
                            out.flush();
                        } catch (IOException ex) {
                            System.err.println("No se pudo enviar el error al cliente: " + ex.getMessage());
                        }
                    }
                } finally {
                    try {
                        if (clientSocket != null) clientSocket.close();
                    } catch (IOException e) {
                        System.err.println("Error cerrando el socket: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error al iniciar servidor: " + e.getMessage());
        }
    }
}
