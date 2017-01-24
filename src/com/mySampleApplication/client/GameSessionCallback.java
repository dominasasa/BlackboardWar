package com.mySampleApplication.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Class implementing an interface AsyncCallback<T> to receive a response from RPC.
 */
public class GameSessionCallback implements AsyncCallback<Player> {

    /**
     * Main window of the application.
     */
    StartScreen startScreen;

    /**
     * Called when asynchronous call fails to complete normally.
     *
     * @param caught
     */
    @Override
    public void onFailure(Throwable caught) {
        Window.alert("Can't create session!");
    }

    /**
     * Called when asynchronous call completes successfully.
     *
     * @param result
     */
    @Override
    public void onSuccess(Player result) {
        /**
         * Spawn board if session name is available.
         */
        if (result != null) {
            startScreen.spawnBoard(startScreen.getSessionID().getText(), result);
        } else {
            startScreen.getSessionID().setText("Used ID!");
        }
    }

    GameSessionCallback(StartScreen startScreen) {
        this.startScreen = startScreen;
    }
}
