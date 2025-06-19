package edu.networking.exercise2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpServer{
    public static String getHTML(String url) {
        try {
            URL target = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(target.openStream()));
            BufferedWriter out = new BufferedWriter(new FileWriter("resultado.html"));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.write(inputLine);
                out.newLine();
            }
            in.close();
            out.close();
            return "Contenido guardado correctamente en <a href='/resultado.html' target='_blank'>resultado.html</a>";
        } catch (Exception e) {
            return "Error al leer la URL: " + e.getMessage();
        }
    }
}
