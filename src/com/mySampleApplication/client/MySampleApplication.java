package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MySampleApplication implements EntryPoint {
    /**
     * Function creantes new StartScreen object
     * calls function to create welcome popup box
     * creates label storing player's ratio
     * appends welcome popup Box to root panel
     */
    public void onModuleLoad() {

        StartScreen MainPage = new StartScreen();
        MainPage.welcomePopUpBox();
        MainPage.setP1Ratio(new Label("0"));
        RootPanel.get("slot2").add(MainPage.getWelcomeBox());


    }
}
