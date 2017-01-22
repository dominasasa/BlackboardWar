package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Created by abcd on 2017-01-21.
 */
public interface GameServerAsync {



    void createSession(String ID, AsyncCallback<Player> async);

    void sendPlayer(Player player, AsyncCallback<Void> async);

    void getPlayer(String sessionID,AsyncCallback<Player[]> async);
}


