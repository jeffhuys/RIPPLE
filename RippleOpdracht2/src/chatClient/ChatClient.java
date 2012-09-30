package chatClient;

import chatServer.ChatServiceRemoteInterface;
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

    ChatServiceRemoteInterface remoteService;

    public void connect() {
        try {
            remoteService = (ChatServiceRemoteInterface) Naming.lookup("rmi://127.0.0.1/ChatService");
            String test = remoteService.sendMessage("Dit is een bericht");
            //System.out.println(test);
        } catch (NotBoundException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws RemoteException {
        ChatClient chatClient = new ChatClient();
        chatClient.connect();
        chatClient.printMessage();
        //while (true) {
        //}
    }

    public void printMessage() throws RemoteException {
        System.out.println(remoteService.getMessage());
    }
}
