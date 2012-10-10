package chatClient;

import chatServer.ChatServiceRemoteInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bartspiering
 */
public class ChatClient {
    private static String username;
    

    public static ChatServiceRemoteInterface remoteService;
    public static CallbackClientInterface callbackobj;
    public static Sender s;
    
    public boolean connect(String username, String password) {
        boolean login = false;
        try {
            remoteService = (ChatServiceRemoteInterface) Naming.lookup("rmi://127.0.0.1/ChatService");
            callbackobj = new CallbackClientImpl();
            remoteService.registerForCallback(callbackobj);
            //Random random = new Random();
            //String test = remoteService.sendMessage("Rnd: " + random.nextInt());
            login = remoteService.login(username, password);
            //System.out.println(test);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return login;
    }

    public void chat() throws RemoteException {
        System.out.println("Login succeeded.");
        System.out.println("Getting last 5 messages...\n");
        int messages = remoteService.messagesLength();
        //System.out.println("DEBUG: There are " + messages + " messages on the server.");
        for (int i = messages - ((messages >= 5) ? 5 : messages); i < messages; i++) {
            System.out.println(remoteService.getMessage(i).getUser() + ": " + remoteService.getMessage(i).getMessage());
        }
        
        //System.out.println("Starting thread...");
        s = new Sender();
        Thread t = new Thread(s);
        s.setUsername(username);
        t.start();
        
        //remoteService.register("etstatast", "peopaPROPA");


    }

    public static void main(String[] args) throws RemoteException {
        ChatClient chatClient = new ChatClient();
        
        Scanner input = new Scanner(System.in);
        System.out.print("Username: ");
        username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();
        
        if (chatClient.connect(username, password)) {
            chatClient.chat();
        }
        //System.out.println("You started the wrong file, mate. Run GUI.java!");
    }

    public void printMessage() throws RemoteException {
        System.out.println(remoteService.getLastMessage().getMessage());
    }
}

class Sender implements Runnable {

    private String username = "";
    @Override
    public void run() {
        String messageToSend;
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Your message: ");
            messageToSend = input.nextLine();
            try {
                ChatClient.remoteService.sendMessage(messageToSend, username);
            } catch (RemoteException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
