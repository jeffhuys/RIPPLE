package chatServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServiceRemoteInterface extends Remote{
    public String sendMessage(String message) throws RemoteException;
    public Message getLastMessage() throws RemoteException;
    public Message getMessage(int messageID) throws RemoteException;
    public boolean login(String username, String password) throws RemoteException;

    public int messagesLength() throws RemoteException;
    
}