package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MySampleApplication implements EntryPoint {

    public void onModuleLoad() {

        StartScreen MainPage = new StartScreen();
        MainPage.welcomePopUpBox();
        MainPage.setP1Ratio(new Label("0"));
        Board board = new Board(600,600, MainPage.getP1Color(), MainPage.getP1Ratio());

        MainPage.getGo().addClickHandler((ClickEvent event) -> {
            if (MainPage.getNickName().getValue().length() > 0) {
                MainPage.setColor();
                board.setColor(MainPage.getP1Color());

                MainPage.getNewGame().addClickHandler((ClickEvent event2) -> {
                    RootPanel.get("slot2").remove(MainPage.getWelcomeBox());
                    RootPanel.get("slot2").add(board.getBoard());
                });

            }else {
                MainPage.getNickName().setText("NICK!");
                MainPage.getNickName().addClickHandler((ClickEvent event3) ->
                        MainPage.getNickName().setText(""));
            }
        });
        RootPanel.get("slot2").add(MainPage.getWelcomeBox());


    }
}
