package com.mySampleApplication.client;

import com.google.gwt.user.client.ui.*;

/**
 * Created by odomi on 16.01.2017.
 */
public class StartScreen {

    private RadioButton btnYellow;
    private RadioButton btnRed;
    private RadioButton btnGreen;
    private RadioButton btnBlue;
    private Button go;
    private Button newGame;
    private Button JoinGame;
    private FlowPanel colorBtnGroup;

    private Label nickname;
    private Label grabChalk;

    private Label p1Nick;
    private Label p1Ratio;
    private Label name;
    private Label proc;
    private Label ratio;
    private Label p2Nick;
    private Label p2Ratio;

    private TextBox nickName;
    private Grid welcomeBox;
    private Grid p1Grid;

    public void welcomePopUpBox() {
        setBtnYellow(new RadioButton("colorGroup"));
        getBtnYellow().setValue(true);
        setBtnRed(new RadioButton("colorGroup"));
        setBtnGreen(new RadioButton("colorGroup"));
        setBtnBlue(new RadioButton("colorGroup"));
        setGo(new Button("GO!"));
        setGrabChalk(new Label("GRAB CHALK!"));
        setNickname(new Label("CHOOSE YOUR NICKNAME:"));

        setNickName(new TextBox());
        setWelcomeBox(new Grid(3,2));

        setColorBtnGroup(new FlowPanel());
        getColorBtnGroup().add(getBtnYellow());
        getColorBtnGroup().add(getBtnRed());
        getColorBtnGroup().add(getBtnGreen());
        getColorBtnGroup().add(getBtnBlue());

        getGo().setStyleName("button-std");
        getBtnYellow().setStyleName("btn-small");
        getBtnRed().setStyleName("btn-small");
        getBtnGreen().setStyleName("btn-small");
        getBtnBlue().setStyleName("btn-small");

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

    public void NewGamePopUpBox(){
        getWelcomeBox().clear();
        getNickName().setText("");
        setNewGame(new Button("CREATE\nGAME"));
        getNickname().setText("ENTER GAME ID TO JOIN:");
        setJoinGame(new Button("JOIN\nGAME"));

        getJoinGame().setStyleName("button-std");
        getNewGame().setStyleName("button-std");
        getWelcomeBox().resizeRows(2);
        getWelcomeBox().setWidget(0, 0, getNickname());
        getWelcomeBox().setWidget(0, 1, getNickName());
        getWelcomeBox().setWidget(1, 0, getNewGame());
        getWelcomeBox().setWidget(1, 1, getJoinGame());
    }

    public void makeP1(){
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

    public RadioButton getBtnBlue() {
        return btnBlue;
    }

    public void setBtnBlue(RadioButton btnBlue) {
        this.btnBlue = btnBlue;
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

