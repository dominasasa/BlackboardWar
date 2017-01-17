package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.RootPanel;

import static com.mySampleApplication.client.StartScreen.*;

public class MySampleApplication implements EntryPoint {

    public void onModuleLoad() {

        StartScreen MainPage = new StartScreen();
        MainPage.welcomePopUpBox();

        MainPage.getGo().addClickHandler((ClickEvent event) -> {
            if(MainPage.getBtnYellow().getValue().equals(true)){
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor(Yellow.value());
                MainPage.setP1Color(Yellow);
            }
            if(MainPage.getBtnRed().getValue().equals(true)) {
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor(Red.value());
                MainPage.setP1Color(Red);
            }
            if(MainPage.getBtnGreen().getValue().equals(true)) {
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor(Green.value());
                MainPage.setP1Color(Green);
            }
            if(MainPage.getBtnPurple().getValue().equals(true)) {
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor(Purple.value());
                MainPage.setP1Color(Purple);
            }
            MainPage.makeP1();
            MainPage.NewGamePopUpBox();

            RootPanel.get("tab_p1_text").add(MainPage.getP1Grid());
            MainPage.getJoinGame().addClickHandler((ClickEvent event2) ->

                    RootPanel.get("slot2").remove(MainPage.getWelcomeBox())
            );
            MainPage.getNewGame().addClickHandler((ClickEvent event2) ->
                    RootPanel.get("slot2").remove(MainPage.getWelcomeBox())
            );
        });
        RootPanel.get("slot2").add(MainPage.getWelcomeBox());
    }
}
