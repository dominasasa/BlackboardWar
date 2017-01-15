package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    /**
     * This is the entry point method.
     */
    RadioButton btnYellow;
    RadioButton btnRed;
    RadioButton btnGreen;
    RadioButton btnBlue;
    Button go;
    Button newGame;
    FlowPanel colorBtnGroup;

    Label nickname;
    Label grabChalk;

    Label p1Nick;
    Label p1Ratio;
    Label name;
    Label proc;
    Label ratio;
    Label p2Nick;
    Label p2Ratio;


    TextBox nickName;
    Grid welcomeBox;
    Grid p1Grid;

    public void welcomePopUpBox() {
        btnYellow = new RadioButton("colorGroup");
        btnYellow.setValue(true);
        btnRed = new RadioButton("colorGroup");
        btnGreen = new RadioButton("colorGroup");
        btnBlue = new RadioButton("colorGroup");
        go = new Button("GO!");
        grabChalk = new Label("GRAB CHALK!");
        nickname = new Label("CHOOSE YOUR NICKNAME:");

        nickName = new TextBox();
        welcomeBox = new Grid(3,2);

        colorBtnGroup = new FlowPanel();
        colorBtnGroup.add(btnYellow);
        colorBtnGroup.add(btnRed);
        colorBtnGroup.add(btnGreen);
        colorBtnGroup.add(btnBlue);

        go.setStyleName("button-std");
        btnYellow.setStyleName("btn-small");
        btnRed.setStyleName("btn-small");
        btnGreen.setStyleName("btn-small");
        btnBlue.setStyleName("btn-small");

        welcomeBox.setStyleName("welcome-box");
        grabChalk.setStyleName("text_welcome");
        nickname.setStyleName("text_welcome");
        nickName.setStyleName("text_welcome_input");

        welcomeBox.setWidget(0, 0, nickname);
        welcomeBox.setWidget(0, 1, nickName);
        welcomeBox.setWidget(1, 0, grabChalk);
        welcomeBox.setWidget(2, 0, colorBtnGroup);
        welcomeBox.setWidget(2, 1, go);
    }

    public void NewGamePopUpBox(){
        welcomeBox.clear();
        nickName.setText("");
        newGame = new Button("CREATE\nGAME");
        nickname.setText("ENTER GAME ID TO JOIN:");
        go.setText("JOIN\nGAME");

        go.setStyleName("button-std");
        newGame.setStyleName("button-std");
        welcomeBox.resizeRows(2);
        welcomeBox.setWidget(0, 0, nickname);
        welcomeBox.setWidget(0, 1, nickName);
        welcomeBox.setWidget(1, 0, newGame);
        welcomeBox.setWidget(1, 1, go);

    }

    public void makeP1(){
        p1Grid = new Grid(2,2);
        p1Nick = new Label("B");
        name = new Label("PLAYER NAME: ");
        p1Ratio = new Label("0");
        proc = new Label("%");
        ratio = new Label("RATIO: ");

        p1Nick.setText(nickName.getText().toUpperCase());

        p1Grid.setWidget(0, 0, name);
        p1Grid.setWidget(0, 1, p1Nick);
        p1Grid.setWidget(1, 0, ratio);
        p1Grid.setWidget(1, 1, p1Ratio);
        //p1Grid.setWidget(1, 1, proc);

    }

    public void onModuleLoad() {

        welcomePopUpBox();
        final Button button = new Button("Click me");
        final Label label = new Label();

        //final Button newGame = new Button("NEW GAME");
        //final Button joinGame = new Button("JOIN GAME");


//        final Label enterGameId = new Label("ENTER GAME ID TO JOIN");

        //final TextBox gameId = new TextBox();


        button.addClickHandler(event -> {
            if (label.getText().equals("")) {
                MySampleApplicationService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));
            } else {
                label.setText("");
            }
        });

        go.addClickHandler(event -> {
            if(btnYellow.getValue().equals(true))
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor("#FFF43A");
            if(btnRed.getValue().equals(true))
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor("#9E63FF");
            if(btnGreen.getValue().equals(true))
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor("#33E35A");
            if(btnBlue.getValue().equals(true))
                Document.get().getElementById("tab_p1").getStyle().setBackgroundColor("#33E35A");
            makeP1();
            NewGamePopUpBox();
            RootPanel.get("tab_p1_text").add(p1Grid);
        });

        // Assume that the host HTML has elements defined whose
        // IDs are "slot1", "slot2".  In a real app, you probably would not want
        // to hard-code IDs.  Instead, you could, for example, search for all
        // elements with a particular CSS class and replace them with widgets.
        //
        RootPanel.get("slot2").add(welcomeBox);





        //RootPanel.get("slot2").add(label);
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
