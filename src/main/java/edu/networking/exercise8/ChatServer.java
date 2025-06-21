package edu.networking.exercise8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
    public void sendMessage(String user, String message) throws RemoteException;
}
