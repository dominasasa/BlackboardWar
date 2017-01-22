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
        RootPanel.get("slot2").add(MainPage.getWelcomeBox());


    }
}
