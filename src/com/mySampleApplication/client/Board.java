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
 * Class implementing game board
 * <p>
 * Creates canvas on which player draws. Contains methods, which enable retrieving data of the user
 * and callbacks used to communicate with the server.
 */
public class Board {
    public final Canvas canvas;
    public final Context2d context;

    public final Player player;
    public CssColor color;
    public String sessionID;
    public SendPlayerCallback sendPlayerCallback;
    public GetPlayerCallback getPlayerCallback;
    private int areaUpdates = 0;

    /**
     * Class constructor
     * <p>
     * Creates new canvas and brush of the current player. Adds handlers of mouse events and gets their
     * coordinates.
     *
     * @param width       width of the canvas
     * @param height      height of the canvas
     * @param Ratiostream percentage of covered board
     * @param sessionID   ID of the current session
     * @param player      current player
     */

    public Board(int width, int height, Label Ratiostream, String sessionID, Player player) {

        // DEBUG
        Logger log = Logger.getLogger("hw");

        this.player = player;
        this.sessionID = sessionID;

        canvas = Canvas.createIfSupported();
        context = canvas.getContext2d();

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

                // Draw player locally
                this.draw(player.brush);

                // Update current player position
                GameServer.App.getInstance().sendPlayer(player, sendPlayerCallback);
                covered(Ratiostream);

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

    /**
     * Calculates the percentage of covered board.
     */
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

    /**
     * Returns board canvas
     */
    Canvas getBoard() {
        return canvas;
    }

    /**
     * Draws strokes on the canvas
     *
     * @param brush - brush used by the player
     */
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

    /**
     * Sets the color of the current player.
     *
     * @param col - color chosen by the player
     */
    public void setColor(String col) {
        this.color = CssColor.make(col);
    }


    /**
     * Callback retrieving both players' positions from the server
     */

    class GetPlayerCallback implements AsyncCallback<Player[]> {

        @Override
        public void onFailure(Throwable caught) {
            Window.alert("can't get brush!");
        }

        @Override
        public void onSuccess(Player[] result) {
            if (Board.this.player.order == Player.Order.FIRST) {
                Board.this.draw(result[1].brush);
            } else {
                Board.this.draw(result[0].brush);
            }
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

