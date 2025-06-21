package edu.networking.exercise8;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
public class Chat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Local port for RMI service: ");
            int localPort = Integer.parseInt(sc.nextLine());
            Registry localRegistry = LocateRegistry.createRegistry(localPort);
            System.out.print("Your name ");
            String user = sc.nextLine();
            ChatServerImpl service = new ChatServerImpl(user);
            localRegistry.rebind("ChatServer", service);
            System.out.println("Service 'ChatServer' post in port " + localPort);
            System.out.print("IP of remote service: ");
            String remoteIP = sc.nextLine();
            System.out.print("port remote RMI: ");
            int remotePort = Integer.parseInt(sc.nextLine());
            Registry remoteRegistry = LocateRegistry.getRegistry(remoteIP, remotePort);
            ChatServer remoteService = (ChatServer) remoteRegistry.lookup("ChatServer");
            System.out.println("Connect chat in " + remoteIP + ':' + remotePort);
            System.out.println("Write messages (or 'exit' for close):");
            while (true) {
                String msg = sc.nextLine();
                if ("exit".equalsIgnoreCase(msg)) break;
                try {
                    remoteService.sendMessage(user, msg);
                } catch (RemoteException e) {
                    System.err.println("error sending: " + e.getMessage());
                }
            }
            System.out.println("chat ended ...");
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}