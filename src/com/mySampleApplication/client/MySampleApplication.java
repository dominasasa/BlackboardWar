package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class MySampleApplication implements EntryPoint {

    public void onModuleLoad() {
        StartScreen MainPage = new StartScreen();
        MainPage.welcomePopUpBox();

        MainPage.getGo().addClickHandler((ClickEvent event) -> {
            if(MainPage.getBtnYellow().getValue().equals(true))
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor("#FFF43A");
            if(MainPage.getBtnRed().getValue().equals(true))
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor("#9E63FF");
            if(MainPage.getBtnGreen().getValue().equals(true))
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor("#33E35A");
            if(MainPage.getBtnBlue().getValue().equals(true))
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor("#33E35A");
            MainPage.makeP1();
            MainPage.NewGamePopUpBox();
            RootPanel.get("tab_p1_text").add(MainPage.getP1Grid());
        });
        RootPanel.get("slot2").add(MainPage.getWelcomeBox());
    }
}
