package chatServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatServiceRemoteImpl extends UnicastRemoteObject implements ChatServiceRemoteInterface {
    private static ArrayList<messages> berichten = new ArrayList<>();
    public ChatServiceRemoteImpl() throws RemoteException {   
        messages bericht = new messages("Test", "user");
        berichten.add(bericht);
    }

    public String sendMessage(String message) throws RemoteException {
        messages bericht = new messages(message, "user");
        berichten.add(bericht);
        return message;
    }
    
    public messages getMessage() throws RemoteException {
        return berichten.get(berichten.size()-1);
    }
}
