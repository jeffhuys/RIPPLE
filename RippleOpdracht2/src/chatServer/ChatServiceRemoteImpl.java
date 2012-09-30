package chatServer;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServiceRemoteImpl extends UnicastRemoteObject implements ChatServiceRemoteInterface {

    private static ArrayList<Message> messages = new ArrayList<>();
    //private static ArrayList<User> users = new ArrayList<>();

    public ChatServiceRemoteImpl() throws RemoteException {
        //Message bericht = new Message("Test", "user");
        //messages.add(bericht);
    }

    public String sendMessage(String message) throws RemoteException {
        Message bericht = new Message(message, "user");
        messages.add(bericht);
        return message;
    }

    public Message getLastMessage() throws RemoteException {
        System.out.println(messages.get(messages.size() - 1).message);
        return messages.get(messages.size() - 1);
    }

    public Message getMessage(int messageID) throws RemoteException {
        if (messageID > messages.size()) {
            return null;
        } else {
            return messages.get(messageID);
        }
    }

    public boolean login(String username, String password) throws RemoteException {
        String usernameFromDB = "";
        String passwordFromDB = "";
        
        try {
            SQLiteConnection db = new SQLiteConnection(new File("src/users.db"));
            db.open(true);
            Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);
            // Check if user is registered
            SQLiteStatement st = db.prepare("SELECT * FROM Users WHERE username = '" + username + "'");
            try {
                //st.bind(1, minimumQuantity);
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

    public int messagesLength() throws RemoteException {
        return messages.size();
    }
}
