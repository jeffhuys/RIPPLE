package chatClient;

import chatServer.ChatServiceRemoteInterface;
import chatServer.Message;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bartspiering
 */
public class ChatClient {

    public ChatServiceRemoteInterface remoteService;

    public boolean connect() {
        boolean login = false;
        try {
            remoteService = (ChatServiceRemoteInterface) Naming.lookup("rmi://127.0.0.1/ChatService");
            String test = remoteService.sendMessage("Dit is een bericht2");
            login = remoteService.login("jeffhuys", "testpass");
            //System.out.println(test);
        } catch (NotBoundException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return login;
    }

    public void chat() throws RemoteException {
        System.out.println("Login succeeded.");
        System.out.println("Getting last 5 messages...");
        int messages = remoteService.messagesLength();
        
    
    }

    public static void main(String[] args) throws RemoteException {
        ChatClient chatClient = new ChatClient();
        boolean login = chatClient.connect();
        if (login) {
            chatClient.chat();
        } else {
            System.exit(1);
        }
    }

    public void printMessage() throws RemoteException {
        System.out.println(remoteService.getMessage().GetMessage());
    }
}
