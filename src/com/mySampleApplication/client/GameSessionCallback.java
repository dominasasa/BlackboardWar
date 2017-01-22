package com.mySampleApplication.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by abcd on 2017-01-22.
 */
public class GameSessionCallback implements AsyncCallback<Player> {

    StartScreen startScreen;

    @Override
    public void onFailure(Throwable caught) {
        Window.alert("Can't create session!");
    }

    @Override
    public void onSuccess(Player result) {
        // Spawn board if session name is available
        if(result != null) {
            startScreen.spawnBoard(startScreen.getSessionID().getText(), result);
        } else {
            startScreen.getSessionID().setText("Used ID!");
        }
    }

    GameSessionCallback(StartScreen startScreen) {
        this.startScreen = startScreen;
    }
}
