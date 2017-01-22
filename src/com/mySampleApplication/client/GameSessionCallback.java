package com.mySampleApplication.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Created by abcd on 2017-01-22.
 */
public class GameSessionCallback implements AsyncCallback<Boolean> {

    StartScreen startScreen;

    @Override
    public void onFailure(Throwable caught) {
        Window.alert("Can't create session!");
    }

    @Override
    public void onSuccess(Boolean result) {
        // If session name is available
        if(result) {
            startScreen.spawnBoard();
        } else {
            startScreen.getSessionID().setText("Used ID!");
        }
    }

    GameSessionCallback(StartScreen startScreen) {
        this.startScreen = startScreen;
    }
}
