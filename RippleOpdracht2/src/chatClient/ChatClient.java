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

    public static ChatServiceRemoteInterface remoteService;

    public void connect() {
        try {
            remoteService = (ChatServiceRemoteInterface) Naming.lookup("rmi://127.0.0.1/ChatService");
            String test = remoteService.sendMessage("Dit is een bericht2");
            System.out.println(test);
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

    }

    public void printMessage() throws RemoteException {
        Message m = remoteService.getMessage();
        System.out.println(m.GetMessage());
    }
}

class Reading extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Reading.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                System.out.println("Reading.");
                System.out.println(ChatClient.remoteService.getMessage());
                Thread.sleep(1000);
            } catch (RemoteException | InterruptedException ex) {
                Logger.getLogger(Reading.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
