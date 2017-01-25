package com.mySampleApplication.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

/**
 * Created by odomi on 16.01.2017.
 */

/**
 * class creating
 * - left menu with players and Session Id
 * - both pop up boxes one to get nick name and color second to get session id
 * - Board
 * and appends it to appropriate places
 */
class StartScreen {
    /**
     * Colors used in app
     */
    private static final CssColor Yellow = CssColor.make("#FFF43A");
    private static final CssColor Red = CssColor.make("#FF4C47");
    private static final CssColor Green = CssColor.make("#33E35A");
    private static final CssColor Purple = CssColor.make("#9E63FF");

    /**
     * Button group to choose Player's brush color
     */
    private FlowPanel colorBtnGroup;
    private RadioButton btnYellow;
    private RadioButton btnRed;
    private RadioButton btnGreen;
    private RadioButton btnPurple;

    /**
     * buttons used in popup boxes
     */
    private Button go;
    private Button newGame;
    private Button JoinGame;

    /**
     * Info to user in popup boxes
     */
    private Label nickname;
    private Label grabChalk;
    private Label name;
    private Label ratio;

    /**
     * Elements of both popup boxes: input TextBoxes and grid
     */

    private TextBox nickName;
    private Grid welcomeBox;
    private TextBox SessionId;

    /**
     * Player's data
     */
    private Label p1Nick;
    private Label p1Ratio;
    private CssColor p1Color;


    /**
     * Opponent's data
     */
    private Label p2Nick;
    private CssColor p2Color;

    /**
     * Grid for Player's info boxes in the left menu
     */
    private Grid p1Grid;
    private Grid p2Grid;

    private Grid midMenuGrid;
    private Label gameId;
    private Label gameId_var;
    /**
     * handler to turn off scheduler when opponent info arrived
     */
    private boolean stopScheduler = true;


    /**
     * WelcomePopUpBox
     * Creates and shows First PopUp Box
     * Creates 3x2 Grid and fills it with:
     * - input Textbox to get nickname of player
     * - some info-labels
     * - Go button to:
     * -- call function saving color choosed by user,
     * -- call function creating next box
     * -- proceed to next step
     * - RadioButtons group showing available colors
     */
    void welcomePopUpBox() {
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


    /**
     * Creates new game board and adds to RootPanel
     * creates timer to periodically check opponent position
     **/
    void spawnBoard(String sessionID, Player player) {
        Board board = new Board(600,600, this.getP1Ratio(), sessionID, player);
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
    /**
     * NewGamePopUpBox
     * Creates and shows Second PopUp Box
     * Creates 2x2 Grid and fills it with:
     * - input Textbox to get session ID
     * - some info-labels
     * - New Game button and Join Game button to:
     * -- create session
     * -- create player in session remembering brush color, nick, and session Id
     */
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
            if(this.getSessionID().getText().isEmpty()) {
                this.getSessionID().setText("Not valid ID!");
            } else {
                GameServer.App.getInstance().createSession(this.getSessionID().getText(), getP1Nick().getText(), this.getP1Color(), sessionCallback);
                this.makeMidMenu();
            }
        });
        getJoinGame().addClickHandler((ClickEvent event2) -> {
            // Check is sessionId is valid
            if(this.getSessionID().getText().isEmpty()) {
                this.getSessionID().setText("Not valid ID!");
            } else {
                GameServer.App.getInstance().createSession(this.getSessionID().getText(), getP1Nick().getText(), this.getP1Color(), sessionCallback);
                this.makeMidMenu();
            }
        });




    }

    /**
     * Creates Panel for Player, who calls the function and appends itself to RootPanel
     * It is everytime on the top
     * Shows Player's Nickname and % of the total area covered by Player color
     *
     */

    private void makeP1() {
        this.setP1Grid(new Grid(2, 2));
        this.setName(new Label("PLAYER 1: "));
        this.setRatio(new Label("RATIO: "));


        this.getP1Grid().setWidget(0, 0, getName());
        this.getP1Grid().setWidget(0, 1, this.getP1Nick());
        this.getP1Grid().setWidget(1, 0, getRatio());
        this.getP1Grid().setWidget(1, 1, this.getP1Ratio());

        RootPanel.get("tab_p1_text").add(this.getP1Grid());
    }
    /**
     * Creates Panel for Opponent, op player who calls the function and appends itself to RootPanel
     * It is everytime on the bottom
     * Shows Player's Nickname
     * Calls for opponent's data every half second until it is able to get it
     **/
    private void makeP2() {
        setP2Grid(new Grid(1, 2));
        Label name = new Label("PLAYER 2: ");


        getP2Grid().setWidget(0, 0, name);
        GetPlayerCallback gpc = new GetPlayerCallback();
        Scheduler.get().scheduleFixedPeriod(() -> {
            GameServer.App.getInstance().getPlayer(getSessionID().getText(), gpc);
            return stopScheduler;
        }, 500);


    }

    /**
     * Creates menu between players' info boxes
     * shows Session Id
     */
    private void makeMidMenu(){
        setGameId(new Label("GAME ID"));
        setGameId_var(new Label(getSessionID().getText()));
        setMidMenuGrid(new Grid(1,2));
        getMidMenuGrid().setWidget(0, 0, getGameId());
        getMidMenuGrid().setWidget(0, 1, getGameId_var());

        RootPanel.get("tab_mid_menu_info").add(this.getMidMenuGrid());

        this.makeP2();

    }
    /**
     * setColor function checks which radio button had been checked when user clicked "GO" button
     * saves Player's color
     * sets choosen color as background to player's info box in menu
     * calls constructor of player's info box
     */
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

    }

    /**
     * To asynchronously call server for opponent's nickname and brush color
     * sets received data
     */
    class GetPlayerCallback implements AsyncCallback<Player[]> {

        @Override
        public void onFailure(Throwable caught){
        }

        @Override
        public void onSuccess(Player[] result) {
            if(!result[1].name.equals(null)) {
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
}

