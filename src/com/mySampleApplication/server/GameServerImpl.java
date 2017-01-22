package com.mySampleApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.client.Brush;
import com.mySampleApplication.client.GameServer;

import java.util.HashMap;


/**
 * Created by abcd on 2017-01-21.
 */
public class GameServerImpl extends RemoteServiceServlet implements GameServer {
    HashMap<String, GameSession> sessions = new HashMap<>();

    @Override
    public Boolean createSession(String ID) {
        System.out.println("Got ID: " + ID);
        if(sessions.containsKey(ID)) {
            System.out.println("ID: " + ID + "is already used!");
            return new Boolean(false);
        } else {
            System.out.println("Creating session with ID: " + ID);
            sessions.put(ID, new GameSession(ID));
            return new Boolean(true);
        }
    }

    @Override
    public void sendBrush(Brush brush) {

    }

    @Override
    public Brush getBrush() {
        return null;
    }

    @Override
    public void sendBrushPosition(String brush) {

    }

    @Override
    public String getBrushPosition() {
        return null;
    }
}

class GameSession {
    String ID;
    Brush player1, player2;

    GameSession(String ID) {
        this.ID = ID;
    }

    public void setPlayer1(Brush player) {
        this.player1 = player;
    }

}