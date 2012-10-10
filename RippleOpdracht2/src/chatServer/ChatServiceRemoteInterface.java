package chatServer;

import chatClient.CallbackClientInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServiceRemoteInterface extends Remote{
    public String sendMessage(String message, String user) throws RemoteException;
    public Message getLastMessage() throws RemoteException;
    public Message getMessage(int messageID) throws RemoteException;
    public boolean login(String username, String password) throws RemoteException;
    public void register(String username, String password) throws RemoteException;
    
    public void pushMessage(Message message) throws RemoteException;
// This remote method allows an object client to 
// register for callback
// @param callbackClientObject is a reference to the
//        object of the client; to be used by the server
//        to make its callbacks.

  public void registerForCallback(
    CallbackClientInterface callbackClientObject
    ) throws java.rmi.RemoteException;

// This remote method allows an object client to 
// cancel its registration for callback

  public void unregisterForCallback(
    CallbackClientInterface callbackClientObject)
    throws java.rmi.RemoteException;


    public int messagesLength() throws RemoteException;
    
}