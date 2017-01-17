package com.mySampleApplication.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.*;

/**
 * Created by odomi on 16.01.2017.
 */
public class StartScreen {

    private static final CssColor Yellow = CssColor.make("#FFF43A");
    private static final CssColor Red = CssColor.make("#FF4C47");
    private static final CssColor Green = CssColor.make("#33E35A");
    private static final CssColor Purple = CssColor.make("#9E63FF");


    private RadioButton btnYellow;
    private RadioButton btnRed;
    private RadioButton btnGreen;
    private RadioButton btnPurple;
    private Button go;
    private Button newGame;
    private Button JoinGame;
    private FlowPanel colorBtnGroup;

    private Label nickname;
    private Label grabChalk;

    private Label p1Nick;
    private Label p1Ratio;
    private CssColor p1Color;
    private Label name;
    private Label proc;
    private Label ratio;
    private Label p2Nick;
    private Label p2Ratio;
    private CssColor p2Color;

    private TextBox nickName;
    private Grid welcomeBox;
    private Grid p1Grid;

    public void welcomePopUpBox() {
        setBtnYellow(new RadioButton("colorGroup"));
        getBtnYellow().setValue(true);
        setBtnRed(new RadioButton("colorGroup"));
        setBtnGreen(new RadioButton("colorGroup"));
        setBtnPurple(new RadioButton("colorGroup"));
        setGo(new Button("GO!"));
        setGrabChalk(new Label("GRAB CHALK!"));
        setNickname(new Label("CHOOSE YOUR NICKNAME:"));

        setNickName(new TextBox());
        setWelcomeBox(new Grid(3,2));

        getNickName().addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    if(getNickName().getValue().length() > 0) {
                        setColor();
                    }else {
                        getNickName().setText("NICK!");
                        getNickName().addClickHandler((ClickEvent event3) ->
                                getNickName().setText(""));
                    }
                }
            }
        });
        setColorBtnGroup(new FlowPanel());
        getColorBtnGroup().add(getBtnYellow());
        getColorBtnGroup().add(getBtnGreen());
        getColorBtnGroup().add(getBtnPurple());
        getColorBtnGroup().add(getBtnRed());


        getGo().setStyleName("button-std");
        getBtnYellow().setStyleName("btn-small");
        getBtnGreen().setStyleName("btn-small");
        getBtnPurple().setStyleName("btn-small");
        getBtnRed().setStyleName("btn-small");

        getWelcomeBox().setStyleName("welcome-box");
        getGrabChalk().setStyleName("text_welcome");
        getNickname().setStyleName("text_welcome");
        getNickName().setStyleName("text_welcome_input");

        getWelcomeBox().setWidget(0, 0, getNickname());
        getWelcomeBox().setWidget(0, 1, getNickName());
        getWelcomeBox().setWidget(1, 0, getGrabChalk());
        getWelcomeBox().setWidget(2, 0, getColorBtnGroup());
        getWelcomeBox().setWidget(2, 1, getGo());
    }

    private void NewGamePopUpBox(){
        getWelcomeBox().clear();
        getNickName().setText("");
        setNewGame(new Button("CREATE\nGAME"));
        getNickname().setText("ENTER GAME ID TO JOIN:");
        setJoinGame(new Button("JOIN\nGAME"));

        getJoinGame().setStyleName("button-std");
        getJoinGame().setEnabled(false);
        getNewGame().setStyleName("button-std");
        getWelcomeBox().resizeRows(2);
        getWelcomeBox().setWidget(0, 0, getNickname());
        getWelcomeBox().setWidget(0, 1, getNickName());
        getWelcomeBox().setWidget(1, 0, getNewGame());
        getWelcomeBox().setWidget(1, 1, getJoinGame());
    }

    private void makeP1(){
        setP1Grid(new Grid(2,2));
        setP1Nick(new Label("B"));
        setName(new Label("PLAYER NAME: "));
        setP1Ratio(new Label("0"));
        setProc(new Label("%"));
        setRatio(new Label("RATIO: "));


        getP1Nick().setText(getNickName().getText().toUpperCase());

        getP1Grid().setWidget(0, 0, getName());
        getP1Grid().setWidget(0, 1, getP1Nick());
        getP1Grid().setWidget(1, 0, getRatio());
        getP1Grid().setWidget(1, 1, getP1Ratio());
        //p1Grid.setWidget(1, 1, proc);

    }

    public void setColor(){
        if (this.getBtnYellow().getValue().equals(true)) {
            Document.get().getElementById("tab_p1").getStyle().setBackgroundColor(Yellow.value());
            this.setP1Color(Yellow);
        }
        if (this.getBtnRed().getValue().equals(true)) {
            Document.get().getElementById("tab_p1").getStyle().setBackgroundColor(Red.value());
            this.setP1Color(Red);
        }
        if (this.getBtnGreen().getValue().equals(true)) {
            Document.get().getElementById("tab_p1").getStyle().setBackgroundColor(Green.value());
            this.setP1Color(Green);
        }
        if (this.getBtnPurple().getValue().equals(true)) {
            Document.get().getElementById("tab_p1").getStyle().setBackgroundColor(Purple.value());
            this.setP1Color(Purple);
        }
        this.makeP1();
        this.NewGamePopUpBox();

        RootPanel.get("tab_p1_text").add(this.getP1Grid());
        this.getJoinGame().addClickHandler((ClickEvent event2) ->

                RootPanel.get("slot2").remove(this.getWelcomeBox())
        );
        this.getNewGame().addClickHandler((ClickEvent event2) ->
                RootPanel.get("slot2").remove(this.getWelcomeBox())
        );
    }

    public TextBox getNickName() {
        return nickName;
    }

    public void setNickName(TextBox nickName) {
        this.nickName = nickName;
    }

    public RadioButton getBtnGreen() {
        return btnGreen;
    }

    public void setBtnGreen(RadioButton btnGreen) {
        this.btnGreen = btnGreen;
    }

    public RadioButton getBtnPurple() {
        return btnPurple;
    }

    public void setBtnPurple(RadioButton btnPurple) {
        this.btnPurple = btnPurple;
    }

    public Button getGo() {
        return go;
    }

    public void setGo(Button go) {
        this.go = go;
    }

    public Button getNewGame() {
        return newGame;
    }

    public void setNewGame(Button newGame) {
        this.newGame = newGame;
    }

    public FlowPanel getColorBtnGroup() {
        return colorBtnGroup;
    }

    public void setColorBtnGroup(FlowPanel colorBtnGroup) {
        this.colorBtnGroup = colorBtnGroup;
    }

    public Label getNickname() {
        return nickname;
    }

    public void setNickname(Label nickname) {
        this.nickname = nickname;
    }

    public Label getGrabChalk() {
        return grabChalk;
    }

    public void setGrabChalk(Label grabChalk) {
        this.grabChalk = grabChalk;
    }

    public Label getP1Nick() {
        return p1Nick;
    }

    public void setP1Nick(Label p1Nick) {
        this.p1Nick = p1Nick;
    }

    public Label getP1Ratio() {
        return p1Ratio;
    }

    public void setP1Ratio(Label p1Ratio) {
        this.p1Ratio = p1Ratio;
    }

    public String getP1Color() { return p1Color.value(); }

    public void setP1Color(CssColor col) { this.p1Color = col; }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public Label getProc() {
        return proc;
    }

    public void setProc(Label proc) {
        this.proc = proc;
    }

    public Label getRatio() {
        return ratio;
    }

    public void setRatio(Label ratio) {
        this.ratio = ratio;
    }

    public Label getP2Nick() {
        return p2Nick;
    }

    public void setP2Nick(Label p2Nick) {
        this.p2Nick = p2Nick;
    }

    public Label getP2Ratio() {
        return p2Ratio;
    }

    public void setP2Ratio(Label p2Ratio) {
        this.p2Ratio = p2Ratio;
    }

    public String getP2Color() { return p2Color.value(); }

    public void setP2Color(CssColor col) { this.p2Color = col; }

    public Grid getWelcomeBox() {
        return welcomeBox;
    }

    public void setWelcomeBox(Grid welcomeBox) {
        this.welcomeBox = welcomeBox;
    }

    public Grid getP1Grid() {
        return p1Grid;
    }

    public void setP1Grid(Grid p1Grid) {
        this.p1Grid = p1Grid;
    }

    public RadioButton getBtnYellow() {
        return btnYellow;
    }

    public void setBtnYellow(RadioButton btnYellow) {
        this.btnYellow = btnYellow;
    }

    public RadioButton getBtnRed() {
        return btnRed;
    }

    public void setBtnRed(RadioButton btnRed) {
        this.btnRed = btnRed;
    }

    public Button getJoinGame() {
        return JoinGame;
    }

    public void setJoinGame(Button joinGame) {
        JoinGame = joinGame;
    }
}

