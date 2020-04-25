package model;

import controller.WelcomePage;
import packets.LoginPacket;
import serializers.WriteJSON;

import java.io.IOException;

public class SignIn extends Thread {

    private String signedin = "error";
    String nickname;
    String password;

    /**
     * SignIn instance; verify the information and proceed to sign in
     * @param nickname
     * @param password
     */
    public SignIn(String nickname, String password){
        this.nickname = nickname;
        this.password = password;
    }

    public void run(){
        LoginPacket packet = new LoginPacket(nickname, password);
        try {
            String response = WelcomePage.getHm().sendPost("", WriteJSON.writePacket(packet));
            setSignedin(response);
        } catch (IOException e) { //if takes too much time or fail to connect
            setSignedin("Sign In Packet Error");
        }
        System.out.println(getSignedin());
    }

    /**
     * @return signedin : this string contain the information to display in case of error
     */
    public String getSignedin() {
        return signedin;
    }

    public void setSignedin(String signedin) {
        this.signedin = signedin;

    }
}