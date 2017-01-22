package com.mySampleApplication.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by abcd on 2017-01-17.
 */


/**
 * Class implementing game board
 * <p>
 * Constructor takes width, height, playerColor, ratiostream, sessionID, active player
 */
public class Board {
    public final Canvas canvas;
    public final Context2d context;

    public final Player player;
    public CssColor color;
    private int areaUpdates = 0;

    public String sessionID;

    public SendPlayerCallback sendPlayerCallback;
    public GetPlayerCallback getPlayerCallback;

    public Board(int width, int height, String playerColor, Label Ratiostream, String sessionID, Player player) {

        // DEBUG
        Logger log = Logger.getLogger("hw");

        this.player = player;
        player.brush = new Brush(0, 0, playerColor);

        this.sessionID = sessionID;

        // Set sessionId in player brush
        player.sessionID = this.sessionID;

        canvas = Canvas.createIfSupported();
        context = canvas.getContext2d();
        color = CssColor.make(playerColor);

        /* setSize() takes String as parameters, not int */
        canvas.setSize(Integer.toString(width), Integer.toString(height));

        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);

        //Callbacks
        sendPlayerCallback = new SendPlayerCallback();
        getPlayerCallback = new GetPlayerCallback();

        canvas.addMouseDownHandler(event -> {
            player.brush.down = true;
            player.brush.x = event.getX();
            player.brush.y = event.getY();

            //DEBUG
            log.log(Level.INFO, "Mouse down - x: " + player.brush.x + ", y: " + player.brush.y);
        });

        /*
        Update player position
         */
        canvas.addMouseMoveHandler(event -> {
            player.brush.prev_x = player.brush.x;
            player.brush.prev_y = player.brush.y;
            if (player.brush.down && player.brush.can_draw) {
                player.brush.x = event.getX();
                player.brush.y = event.getY();

                // Update current player position
                GameServer.App.getInstance().sendPlayer(player, sendPlayerCallback);
                //covered(Ratiostream);

                //DEBUG
                log.log(Level.INFO, "drawing");
            }

            //DEBUG
            log.log(Level.INFO, "Mouse move - x: " + player.brush.x + ", y: " + player.brush.y);
        });

        canvas.addMouseUpHandler(event -> player.brush.down = false);

        /*
        Pressing Enter clears canvas
         */
        canvas.addKeyPressHandler(event -> {
            if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                context.clearRect(0, 0, width, height);
            }
        });
    }

    private void covered(Label la) {

        if (areaUpdates++ % 10 != 0) {
            int pixels = 0;
            ImageData imageData = context.getImageData(0, 0, 600, 600);

            CanvasPixelArray canvasPixelArray = imageData.getData();
            for (int i = 3; i < canvasPixelArray.getLength(); i += 4) {
                if (canvasPixelArray.get(i) > 50) pixels++;
            }
            pixels = pixels / 3600;
            la.setText(NumberFormat.getFormat("###").format(pixels) + "%");
        }
    }

    /*
    Returns board canvas
     */
    Canvas getBoard() {
        return canvas;
    }

    void draw(Brush brush) {
//        if (brush.can_draw && brush.down) {
            Board.this.context.beginPath();
            Board.this.context.moveTo(brush.prev_x, brush.prev_y);
            Board.this.context.setLineCap("round");
            Board.this.context.setFillStyle(brush.color);
            Board.this.context.setLineWidth(5);
            Board.this.context.lineTo(brush.x, brush.y);
            Board.this.context.setStrokeStyle(brush.color);
            Board.this.context.stroke();
//        }
    }

    void run() {
        GameServer.App.getInstance().getPlayer(sessionID, getPlayerCallback);
    }

    public void setColor(String col) {
        this.color = CssColor.make(col);
    }


    /* Callback retrieving both players positions from server
    * */

    class GetPlayerCallback implements AsyncCallback<Player[]> {

        @Override
        public void onFailure(Throwable caught) {
            Window.alert("can't get brush!");
        }

        @Override
        public void onSuccess(Player[] result) {
            Board.this.draw(result[0].brush);
            Board.this.draw(result[1].brush);
        }
    }


    class SendPlayerCallback implements AsyncCallback<Void> {
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("can't send brush!");
        }

        @Override
        public void onSuccess(Void result) {
        }
    }

}

