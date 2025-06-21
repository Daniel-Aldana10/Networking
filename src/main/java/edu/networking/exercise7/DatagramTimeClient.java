package edu.networking.exercise7;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class DatagramTimeClient {

    public static void main(String[] args) {
        byte[] buf = new byte[256];
        String lastMessage = "time not available";
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(2000);
            InetAddress address = InetAddress.getByName("127.0.0.1");
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
                try {
                    socket.send(packet);
                    packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    lastMessage = new String(packet.getData(), 0, packet.getLength());

                }catch (IOException ex){
                    System.out.println("There was no response: " + lastMessage);
                }
                System.out.println(lastMessage);
                Thread.sleep(5000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}