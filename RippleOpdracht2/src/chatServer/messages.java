/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatServer;

import java.io.Serializable;

/**
 *
 * @author Acer
 */
public class messages implements Serializable{
    String message;
    String user;
    public messages(){
        message = "Standard message";
        user = "Standard user";
    }
    public messages(String mess, String usr){
        message = mess;
        user = usr;
    }
}
