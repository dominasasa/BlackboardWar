package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

/**
 * Created by abcd on 2017-01-21.
 */
@RemoteServiceRelativePath("GameServer")
public interface GameServer extends RemoteService {

    Player createSession(String ID);

    void sendPlayer(Player player);
    Player[] getPlayer(String sessionID);

    /**
     * Utility/Convenience class.
     * Use GameServer.App.getInstance() to access static instance of GameServerAsync
     *
     */

    public static class App {
        private static final GameServerAsync ourInstance = (GameServerAsync) GWT.create(GameServer.class);

        public static GameServerAsync getInstance() {
            return ourInstance;
        }
    }
}
