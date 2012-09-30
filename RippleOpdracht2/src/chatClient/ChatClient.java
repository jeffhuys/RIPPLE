package chatClient;

import chatServer.ChatServiceRemoteInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bartspiering
 */
public class ChatClient {

    public ChatServiceRemoteInterface remoteService;

    public boolean connect(String username, String password) {
        boolean login = false;
        try {
            remoteService = (ChatServiceRemoteInterface) Naming.lookup("rmi://127.0.0.1/ChatService");
            Random random = new Random();
            String test = remoteService.sendMessage("Rnd: " + random.nextInt());
            login = remoteService.login(username, password);
            //System.out.println(test);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return login;
    }

    public void chat() throws RemoteException {
        System.out.println("Login succeeded.");
        System.out.println("Getting last 5 messages...");
        int messages = remoteService.messagesLength();
        System.out.println("DEBUG: There are " + messages + " messages on the server.");
        
        int x = 1;
        for(int i = messages - 5; i < messages; i++) {
            if(i >= 0) {
                System.out.println("Message " + (x) + ": " + remoteService.getMessage(i).GetMessage());
                x++;
            }
        }
        
    
    }

    public static void main(String[] args) throws RemoteException {
        //ChatClient chatClient = new ChatClient();
        System.out.println("You started the wrong file, mate. Run GUI.java!");
    }

    public void printMessage() throws RemoteException {
        System.out.println(remoteService.getLastMessage().GetMessage());
    }
}
