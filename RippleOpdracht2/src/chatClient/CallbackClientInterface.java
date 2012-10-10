package chatClient;

import chatServer.Message;
import java.rmi.*;

public interface CallbackClientInterface extends java.rmi.Remote {
    public String notifyMe(Message message) throws java.rmi.RemoteException;
    public String notifyMe(String message) throws java.rmi.RemoteException;
}