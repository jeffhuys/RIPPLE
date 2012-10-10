package chatServer;

import chatClient.CallbackClientInterface;
import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServiceRemoteImpl extends UnicastRemoteObject implements ChatServiceRemoteInterface {

    private static ArrayList<Message> messages = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    private Vector clientList;

    public ChatServiceRemoteImpl() throws RemoteException {
        super();
        clientList = new Vector();
    }

    @Override
    public String sendMessage(String message, String user) throws RemoteException {
        Message bericht = new Message(message, user);
        messages.add(bericht);
        doCallbacks(bericht);
        return message;
    }

    @Override
    public Message getLastMessage() throws RemoteException {
        System.out.println(messages.get(messages.size() - 1).message);
        return messages.get(messages.size() - 1);
    }

    @Override
    public Message getMessage(int messageID) throws RemoteException {
        if (messageID > messages.size()) {
            return null;
        } else {
            return messages.get(messageID);
        }
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        String usernameFromDB = "";
        String passwordFromDB = "";

        try {
            SQLiteConnection db = new SQLiteConnection(new File("src/users.db"));
            db.open(true);
            Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);
            // Check if user is registered
            SQLiteStatement st = db.prepare("SELECT * FROM Users WHERE username = ? AND password = ?");
            try {
                //st.bind(1, minimumQuantity);
                st.bind(1, username);
                st.bind(2, password);
                st.step();
                usernameFromDB = st.columnString(0);
                passwordFromDB = st.columnString(1);
            } finally {
                st.dispose();
            }
            db.dispose();
        } catch (SQLiteException ex) {
            Logger.getLogger(ChatServiceRemoteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (username.equals(usernameFromDB) && password.equals(passwordFromDB)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int messagesLength() throws RemoteException {
        return messages.size();
    }

    @Override
    public void register(String username, String password) throws RemoteException {
        try {
            // Registers a new user
            // WARNING WARNING WARNING
            // TODO: SQL INJECTION IS POSSIBLE WITH THIS METHOD!
            //       FIX THIS ASAP!
            SQLiteConnection db = new SQLiteConnection(new File("src/users.db"));
            db.open(true);
            Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);
            // Check if user is registered
            SQLiteStatement st = db.prepare("insert into Users (username,password) values ('" + username + "','" + password + "');");
            st.step();
            db.dispose();
        } catch (SQLiteException ex) {
            Logger.getLogger(ChatServiceRemoteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void pushMessage(Message message) throws RemoteException {
        System.out.println("New message: " + message.message);
    }
    @Override
    public synchronized void registerForCallback(CallbackClientInterface callbackClientObject) throws java.rmi.RemoteException {
        if (!(clientList.contains(callbackClientObject))) {
            clientList.addElement(callbackClientObject);
            // register :) 
            Message bericht = new Message("Hi new client", "system");
            System.out.println("Hi new client ");
            doCallbacks();
        }
    }

    @Override
    public synchronized void unregisterForCallback(CallbackClientInterface callbackClientObject) throws java.rmi.RemoteException {
        if (clientList.removeElement(callbackClientObject)) {
            System.out.println("Doei client");
        } else {
            System.out.println("Uhoh ik ken je niet, niet gelukt");
        }
    }
   private synchronized void doCallbacks() throws java.rmi.RemoteException {
        System.out.println("Callback started");
        for (int i = 0; i < clientList.size(); i++) {
            System.out.println("Sturen naar " + i + " van de " +clientList.size()+ "verbonden clients\n");
            CallbackClientInterface nextClient = (CallbackClientInterface) clientList.elementAt(i);
            nextClient.notifyMe("init");
        }
        System.out.println("completed callback");
    }
    private synchronized void doCallbacks(Message message) throws java.rmi.RemoteException {
        System.out.println("Callback started");
        for (int i = 0; i < clientList.size(); i++) {
            System.out.println("Sturen naar " + i + " van de " +clientList.size()+ "verbonden clients\n");
            CallbackClientInterface nextClient = (CallbackClientInterface) clientList.elementAt(i);
            nextClient.notifyMe(message);
        }
        System.out.println("completed callback");
    }
}
