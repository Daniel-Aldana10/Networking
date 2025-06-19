package edu.networking.exercise1;
import java.net.*;
public class URLReader {

    public static void main(String[] args) {
        try{
            URL google = new URL("https://github.com/Daniel-Aldana10/");
            System.out.println(google.getProtocol());
            System.out.println(google.getAuthority());
            System.out.println(google.getHost());
            System.out.println(google.getPort());
            System.out.println(google.getQuery());
            System.out.println(google.getFile());
            System.out.println(google.getRef());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
}
