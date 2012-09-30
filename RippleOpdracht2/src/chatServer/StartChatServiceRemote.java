package chatServer;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author bartspiering
 */
public class StartChatServiceRemote {
    
    public static void main(String[] args) throws SQLiteException {     
        // Disable logging of SQLite4java
        
        System.out.println("Started.");
        try {
            LocateRegistry.createRegistry(1099);
            ChatServiceRemoteInterface service = new ChatServiceRemoteImpl();
            Naming.rebind("ChatService", service);
        } catch (RemoteException ex) {
            Logger.getLogger(StartChatServiceRemote.class.getName())
                                             .log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(StartChatServiceRemote.class.getName())
                                             .log(Level.SEVERE, null, ex);
        }

    }
}
