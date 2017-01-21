package com.mySampleApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.client.GameServer;

import java.util.HashSet;

/**
 * Created by abcd on 2017-01-21.
 */
public class GameServerImpl extends RemoteServiceServlet implements GameServer {
    HashSet<GameSession> sessions = new HashSet<>();


    @Override
    public Boolean createSession(String ID) {
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

    GameSession(String ID) {
        this.ID = ID;
    }

}