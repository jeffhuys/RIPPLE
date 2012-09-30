package chatServer;

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
    
    public static void main(String[] args) {
        
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
