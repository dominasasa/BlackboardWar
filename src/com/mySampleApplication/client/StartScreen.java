package com.mySampleApplication.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.mySampleApplication.server.GameServerImpl;

import java.awt.*;

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
    private Label ratio;
    private Label p2Nick;
    private Label p2Ratio;
    private CssColor p2Color;

    private TextBox nickName;
    private Grid welcomeBox;
    private Grid p1Grid;
    private Grid p2Grid;

    private Grid midMenuGrid;
    private Label gameId;
    private Label timeLeft;
    private Label gameId_var;
    private Label timeLeft_var;
    private PushButton resetGame;

    private TextBox SessionId;


    public void StartScreen(){
        setSessionID(new TextBox());
        setWelcomeBox(new Grid(2, 2));

        getWelcomeBox().setStyleName("welcome-box");

        this.NewGamePopUpBox();
        RootPanel.get("slot2").add(this.getWelcomeBox());



    }

    public void welcomePopUpBox(Player result) {
        makeMidMenu();

        setBtnYellow(new RadioButton("colorGroup"));
        getBtnYellow().setValue(true);
        setBtnRed(new RadioButton("colorGroup"));
        setBtnGreen(new RadioButton("colorGroup"));
        setBtnPurple(new RadioButton("colorGroup"));
        setGo(new Button("GO!"));
        setGrabChalk(new Label("GRAB CHALK!"));
        setNickname(new Label("CHOOSE YOUR NICKNAME:"));
        setNickName(new TextBox());
        getWelcomeBox().clear();
        getWelcomeBox().resizeRows(3);



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


        getGrabChalk().setStyleName("text_welcome");
        getNickname().setStyleName("text_welcome");
        getNickName().setStyleName("text_welcome_input");

        getWelcomeBox().setWidget(0, 0, getNickname());
        getWelcomeBox().setWidget(0, 1, getNickName());
        getWelcomeBox().setWidget(1, 0, getGrabChalk());
        getWelcomeBox().setWidget(2, 0, getColorBtnGroup());
        getWelcomeBox().setWidget(2, 1, getGo());


        // Moved from main class

        getGo().addClickHandler((ClickEvent event) -> {
            if (this.getNickName().getText().length() > 0) {
                this.setColor();
                this.spawnBoard(this.getSessionID().getText(), result);

            } else {
                this.getNickName().setText("NICK!");
                this.getNickName().addClickHandler((ClickEvent event3) ->
                        this.getNickName().setText(""));
            }
        });

        /*getNickName().addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (getNickName().getText().length() > 0) {
                    setColor();
                    spawnBoard(getSessionID().getText(), result);

                } else {
                    getNickName().setText("NICK!");
                    getNickName().addClickHandler((ClickEvent event3) ->
                            getNickName().setText(""));
                }
            }
        });*/

    }

    
    // Creates new game board and adds to RootPanel
    public void spawnBoard(String sessionID, Player player) {
        Board board = new Board(600,600, this.getP1Color(), this.getP1Ratio(), sessionID, player);
        board.setColor(this.getP1Color());

        RootPanel.get("slot2").remove(0);
        RootPanel.get("slot2").add(board.getBoard());

        // run() updates state of both brushes
        /*Timer run = new Timer() {
            @Override
            public void run() {
                board.run();
            }
        };
        run.scheduleRepeating(10);*/
    }

    private void NewGamePopUpBox() {
        setNewGame(new Button("CREATE\nGAME"));
        setNickname(new Label("ENTER GAME ID TO JOIN:"));
        setJoinGame(new Button("JOIN\nGAME"));
        getSessionID().setStyleName("text_welcome_input");

        getJoinGame().setStyleName("button-std");
        getNewGame().setStyleName("button-std");
        getWelcomeBox().setWidget(0, 0, getNickname());
        getWelcomeBox().setWidget(0, 1, getSessionID());
        getWelcomeBox().setWidget(1, 0, getNewGame());
        getWelcomeBox().setWidget(1, 1, getJoinGame());


        // Session creation
        GameSessionCallback sessionCallback = new GameSessionCallback(this);

        getSessionID().addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    if(getSessionID().getText().isEmpty()) {
                        getSessionID().setText("Not valid ID!");
                    } else {
                        GameServer.App.getInstance().createSession(getSessionID().getText(), sessionCallback);

                    }
                }
            }
        });

        // New game button
        getNewGame().addClickHandler((ClickEvent event2) -> {
            // Check is sessionId is valid
            if(this.getSessionID().getText().isEmpty()) {
                this.getSessionID().setText("Not valid ID!");
            } else {
                GameServer.App.getInstance().createSession(this.getSessionID().getText(), sessionCallback);

            }
        });

        getJoinGame().addClickHandler((ClickEvent event3) -> {
            // Check is sessionId is valid
            if(this.getSessionID().getText().isEmpty()) {
                this.getSessionID().setText("Not valid ID!");
            } else {
                GameServer.App.getInstance().createSession(this.getSessionID().getText(), sessionCallback);
                //this.welcomePopUpBox();
            }
        });

    }

    private void makeP1() {
        setP1Grid(new Grid(2, 2));
        setP1Nick(new Label(this.getNickName().getText().toUpperCase()));
        setName(new Label("PLAYER NAME: "));
        setRatio(new Label("RATIO: "));

        getP1Grid().setWidget(0, 0, getName());
        getP1Grid().setWidget(0, 1, getP1Nick());
        getP1Grid().setWidget(1, 0, getRatio());
        getP1Grid().setWidget(1, 1, getP1Ratio());

        RootPanel.get("tab_p1_text").add(this.getP1Grid());
    }
    private void makeP2() {
        setP2Grid(new Grid(2, 2));


        //getP2Grid().setWidget(0, 0, getName());
        //getP2Grid().setWidget(0, 1, getP1Nick());
        //getP2Grid().setWidget(1, 0, getRatio());
        //getP2Grid().setWidget(1, 1, getP1Ratio());

        Document.get().getElementById("tab_p2").getStyle().setBackgroundColor(Green.value());
        this.setP2Color(Green);

        RootPanel.get("tab_p2_text").add(this.getP2Grid());
    }
    private void makeMidMenu(){
        setGameId(new Label("GAME ID"));
        setGameId_var(new Label(getSessionID().getText()));
        setTimeLeft(new Label("TIME LEFT"));
        setTimeLeft_var(new Label("40s"));
        setMidMenuGrid(new Grid(2,2));
        setResetGame(new PushButton(new Image("https://boqnfa.bn1304.livefilestore.com/y3mTp9e-o5NxYA6lPGMRUXtzOlTy55C83bgT0nuRFnzthFS1LBjeAqGyeJOdIMh3kBSGkfFhyhNKe3k_p9lcJR6CSfPVEb_y57j-mP6n5wJfVnvGCb56rehn6ZM6KESwcHnUOyetQDSjoisXsdgDz7pYRHSQre0ri0hCZE5TPaqsJE/reset.png?psid=1")));

        getMidMenuGrid().setWidget(0, 0, getGameId());
        getMidMenuGrid().setWidget(0, 1, getGameId_var());
        getMidMenuGrid().setWidget(1, 0, getTimeLeft());
        getMidMenuGrid().setWidget(1, 1, getTimeLeft_var());
        /*getResetGame().addKeyDownHandler((ClickEvent e) -> (
                this.RESET_KURWA_FUNCTION();
        ));*/
        RootPanel.get("tab_mid_menu_info").add(this.getMidMenuGrid());
        RootPanel.get("tab_mid_menu_btnReset").add(this.getResetGame());

    }

    private void setColor() {

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
        makeP2();


        /*this.getJoinGame().addClickHandler((ClickEvent event2) ->
            RootPanel.get("slot2").remove(this.getWelcomeBox())
        );
        this.getNewGame().addClickHandler((ClickEvent event2) -> {
            RootPanel.get("slot2").remove(this.getWelcomeBox());*/

            /*
            Creates new board and adds to RootPanel
             */
           // Board board = new Board(600, 600, this.getP1Color());
            //RootPanel.get("slot2").add(board.getBoard());
    }

    //-------------------------------------------------------------------------------------------------//

    public TextBox getSessionID() {
        return SessionId;
    }

    public void setSessionID(TextBox SessionId) {
        this.SessionId = SessionId;
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

    public String getP1Color() {
        return p1Color.value();
    }

    public void setP1Color(CssColor col) {
        this.p1Color = col;
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
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

    public String getP2Color() {
        return p2Color.value();
    }

    public void setP2Color(CssColor col) {
        this.p2Color = col;
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

    public Grid getMidMenuGrid() {
        return midMenuGrid;
    }

    public void setMidMenuGrid(Grid midMenuGrid) {
        this.midMenuGrid = midMenuGrid;
    }

    public Label getGameId() {
        return gameId;
    }

    public void setGameId(Label gameId) {
        this.gameId = gameId;
    }

    public Label getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Label timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Label getGameId_var() {
        return gameId_var;
    }

    public void setGameId_var(Label gameId_var) {
        this.gameId_var = gameId_var;
    }

    public Label getTimeLeft_var() {
        return timeLeft_var;
    }

    public void setTimeLeft_var(Label timeLeft_var) {
        this.timeLeft_var = timeLeft_var;
    }

    public PushButton getResetGame() {
        return resetGame;
    }

    public void setResetGame(PushButton resetGame) {
        this.resetGame = resetGame;
    }

    public TextBox getNickName() {
        return nickName;
    }

    public void setNickName(TextBox nickName) {
        this.nickName = nickName;
    }

    public Grid getP2Grid() { return p2Grid; }

    public void setP2Grid(Grid p2Grid) { this.p2Grid = p2Grid; }
}

