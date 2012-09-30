package chatServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatServiceRemoteImpl extends UnicastRemoteObject implements ChatServiceRemoteInterface {

    private static ArrayList<Message> berichten = new ArrayList<>();

    public ChatServiceRemoteImpl() throws RemoteException {
        Message bericht = new Message("Test", "user");
        berichten.add(bericht);
    }

    public String sendMessage(String message) throws RemoteException {
        Message bericht = new Message(message, "user");
        berichten.add(bericht);
        return message;
    }

    public Message getLastMessage() throws RemoteException {
        System.out.println(berichten.get(berichten.size() - 1).message);
        return berichten.get(berichten.size() - 1);
    }

    public Message getMessage(int messageID) throws RemoteException {
        if (messageID > berichten.size()) {
            return null;
        } else {
            return berichten.get(messageID);
        }
    }

    public boolean login(String username, String password) throws RemoteException {
        if (username.equals("jeffhuys") && password.equals("testpass")) {
            return true;
        } else {
            return false;
        }
    }

    public int messagesLength() throws RemoteException {
        return berichten.size();
    }
}
