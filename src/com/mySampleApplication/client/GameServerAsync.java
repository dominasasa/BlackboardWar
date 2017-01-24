package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Asynchronous interface to be called from the client-side code.
 */
public interface GameServerAsync {

    void createSession(String ID, String name, String color, AsyncCallback<Player> async);

    void sendPlayer(Player player, AsyncCallback<Void> async);

    void getPlayer(String sessionID, AsyncCallback<Player[]> async);
}


