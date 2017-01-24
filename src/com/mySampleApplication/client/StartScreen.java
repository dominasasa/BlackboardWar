package com.mySampleApplication.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

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
    private Label proc;
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
    private boolean stopScheduler = true;

    private TextBox SessionId;

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
        setWelcomeBox(new Grid(3, 2));

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


        // Moved from main class

        go.addClickHandler((ClickEvent event) -> {
            if (this.getNickName().getValue().length() > 0) {

                // Save player name - needs refactor
                this.setP1Nick(new Label(this.getNickName().getText().toUpperCase()));
                this.setColor();
                this.NewGamePopUpBox();
            } else {
                this.getNickName().setText("NICK!");
                this.getNickName().addClickHandler((ClickEvent event3) ->
                        this.getNickName().setText(""));
            }
        });
    }


    // Creates new game board and adds to RootPanel
    public void spawnBoard(String sessionID, Player player) {
        Board board = new Board(600, 600, this.getP1Ratio(), sessionID, player);
        board.setColor(this.getP1Color());

        RootPanel.get("slot2").remove(this.getWelcomeBox());
        RootPanel.get("slot2").add(board.getBoard());

        // run() updates state of both brushes
        Timer run = new Timer() {
            @Override
            public void run() {
                board.run();
            }
        };
        run.scheduleRepeating(6);
    }

    private void NewGamePopUpBox() {

        getWelcomeBox().clear();

        setSessionID(new TextBox());
        setNewGame(new Button("CREATE\nGAME"));
        getNickname().setText("ENTER GAME ID TO JOIN:");
        setJoinGame(new Button("JOIN\nGAME"));

        getSessionID().setStyleName("text_welcome_input");

        getJoinGame().setStyleName("button-std");
        getNewGame().setStyleName("button-std");

        getWelcomeBox().resizeRows(2);
        getWelcomeBox().setWidget(0, 0, getNickname());
        getWelcomeBox().setWidget(0, 1, getSessionID());
        getWelcomeBox().setWidget(1, 0, getNewGame());
        getWelcomeBox().setWidget(1, 1, getJoinGame());

        // Session creation
        GameSessionCallback sessionCallback = new GameSessionCallback(this);

        // New game button
        getNewGame().addClickHandler((ClickEvent event2) -> {
            // Check is sessionId is valid
            if (this.getSessionID().getText().isEmpty()) {
                this.getSessionID().setText("Not valid ID!");
            } else {
                GameServer.App.getInstance().createSession(this.getSessionID().getText(), getP1Nick().getText(), this.getP1Color(), sessionCallback);
                this.makeMidMenu();
            }
        });
        getJoinGame().addClickHandler((ClickEvent event2) -> {
            // Check is sessionId is valid
            if (this.getSessionID().getText().isEmpty()) {
                this.getSessionID().setText("Not valid ID!");
            } else {
                GameServer.App.getInstance().createSession(this.getSessionID().getText(), getP1Nick().getText(), this.getP1Color(), sessionCallback);
                this.makeMidMenu();
            }
        });


    }

    private void makeP1() {
        this.setP1Grid(new Grid(2, 2));
        this.setName(new Label("PLAYER 1: "));
        this.setRatio(new Label("RATIO: "));


        this.getP1Grid().setWidget(0, 0, getName());
        this.getP1Grid().setWidget(0, 1, this.getP1Nick());
        this.getP1Grid().setWidget(1, 0, getRatio());
        this.getP1Grid().setWidget(1, 1, this.getP1Ratio());


    }

    private void makeP2() {
        setP2Grid(new Grid(1, 2));
        Label name = new Label("PLAYER 2: ");


        getP2Grid().setWidget(0, 0, name);
        GetPlayerCallback gpc = new GetPlayerCallback();
        Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
            @Override
            public boolean execute() {

                GameServer.App.getInstance().getPlayer(getSessionID().getText(), gpc);
                return stopScheduler;
            }
        }, 500);


    }

    private void makeMidMenu() {
        setGameId(new Label("GAME ID"));
        setGameId_var(new Label(getSessionID().getText()));
        setMidMenuGrid(new Grid(1, 2));
        setResetGame(new PushButton(new Image("https://boqnfa.bn1304.livefilestore.com/y3mTp9e-o5NxYA6lPGMRUXtzOlTy55C83bgT0nuRFnzthFS1LBjeAqGyeJOdIMh3kBSGkfFhyhNKe3k_p9lcJR6CSfPVEb_y57j-mP6n5wJfVnvGCb56rehn6ZM6KESwcHnUOyetQDSjoisXsdgDz7pYRHSQre0ri0hCZE5TPaqsJE/reset.png?psid=1")));

        getMidMenuGrid().setWidget(0, 0, getGameId());
        getMidMenuGrid().setWidget(0, 1, getGameId_var());
        /*getResetGame().addKeyDownHandler((ClickEvent e) -> (
                this.RESET_KURWA_FUNCTION();
        ));*/
        RootPanel.get("tab_mid_menu_info").add(this.getMidMenuGrid());
        RootPanel.get("tab_mid_menu_btnReset").add(this.getResetGame());

        this.makeP2();

    }

    public void setColor() {
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

        RootPanel.get("tab_p1_text").add(this.getP1Grid());
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

    class GetPlayerCallback implements AsyncCallback<Player[]> {

        @Override
        public void onFailure(Throwable caught) {
            //Window.alert("can't get brush!");
        }

        @Override
        public void onSuccess(Player[] result) {
            if (!result[1].name.equals(null)) {
                if (StartScreen.this.getP1Nick().getText().equals(result[0].name)) {
                    StartScreen.this.setP2Nick(new Label(result[1].name));
                    StartScreen.this.setP2Color(CssColor.make(result[1].brush.getColor()));
                } else {
                    StartScreen.this.setP2Nick(new Label(result[0].name));
                    StartScreen.this.setP2Color(CssColor.make(result[0].brush.getColor()));

                }
                Document.get().getElementById("tab_p2").getStyle().setBackgroundColor(StartScreen.this.getP2Color());
                getP2Grid().setWidget(0, 1, StartScreen.this.getP2Nick());
                RootPanel.get("tab_p2_text").add(StartScreen.this.getP2Grid());
                stopScheduler = false;
            }
        }
    }

    public TextBox getSessionID() {
        return SessionId;
    }

    public void setSessionID(TextBox sessionId) {
        this.SessionId = sessionId;
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

    public TextBox getNickName() {
        return nickName;
    }

    public void setNickName(TextBox nickName) {
        this.nickName = nickName;
    }

    public Grid getP2Grid() {
        return p2Grid;
    }

    public void setP2Grid(Grid p2Grid) {
        this.p2Grid = p2Grid;
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

    public Label getGameId_var() {
        return gameId_var;
    }

    public void setGameId_var(Label gameId_var) {
        this.gameId_var = gameId_var;
    }

    public PushButton getResetGame() {
        return resetGame;
    }

    public void setResetGame(PushButton resetGame) {
        this.resetGame = resetGame;
    }
}

