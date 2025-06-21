package edu.networking.exercise8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    private String name;
    public ChatServerImpl(String name) throws RemoteException {
        super();
        this.name = name;
    }

    @Override
    public void sendMessage(String user, String message) throws RemoteException{
        System.out.println( user + " : " + message);
    }
}
