package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.RootPanel;

public class MySampleApplication implements EntryPoint {

    public void onModuleLoad() {

        StartScreen MainPage = new StartScreen();
        MainPage.welcomePopUpBox();

        MainPage.getGo().addClickHandler((ClickEvent event) -> {
            if(MainPage.getNickName().getValue().length() > 0) {
                MainPage.setColor();
            }else {
                MainPage.getNickName().setText("NICK!");
                MainPage.getNickName().addClickHandler((ClickEvent event3) ->
                        MainPage.getNickName().setText(""));
            }
        });
        RootPanel.get("slot2").add(MainPage.getWelcomeBox());
    }
}
