package chatServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServiceRemoteInterface extends Remote{
    public String sendMessage(String message) throws RemoteException;
    public Message getMessage() throws RemoteException;
}