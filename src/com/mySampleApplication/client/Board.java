package com.mySampleApplication.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Label;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by abcd on 2017-01-17.
 */


/**
 * Class implementing game board
 * <p>
 * Constructor takes width, height, playerColor
 */
public class Board {
    final Canvas canvas;
    final Context2d context;

    final Brush mouse;
    private CssColor color ; //= CssColor.make("black");
    private int areaUpdates = 0;

    public Board(int width, int height, String playerColor, Label Ratiostream) {

        // DEBUG
        Logger log = Logger.getLogger("hw") ;

        mouse = new Brush(0,0, CssColor.make(playerColor));

        canvas = Canvas.createIfSupported();
        context = canvas.getContext2d();
        color = CssColor.make(playerColor);

        /*
        setSize() takes String as parameters, not int
         */
        canvas.setSize(Integer.toString(width), Integer.toString(height));

        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);


        canvas.addMouseDownHandler(event -> {
            mouse.down = true;
            mouse.x = event.getX();
            mouse.y = event.getY();

            //DEBUG
            log.log(Level.INFO, "Mouse down - x: " + mouse.x + ", y: " + mouse.y);
        });

        /*
        Actual painting
         */
        canvas.addMouseMoveHandler(event -> {
            mouse.prev_x = mouse.x;
            mouse.prev_y = mouse.y;
            if(mouse.down && mouse.can_draw) {
                mouse.x = event.getX();
                mouse.y = event.getY();
                context.beginPath();
                context.moveTo(mouse.prev_x, mouse.prev_y);
                context.setLineCap("round");
                context.setFillStyle(color);
                context.setLineWidth(5);
                context.lineTo(event.getX(), event.getY());
                context.setStrokeStyle(color);
                context.stroke();

                covered(Ratiostream);

                //DEBUG
                log.log(Level.INFO, "drawing");
            }

            //DEBUG
            log.log(Level.INFO, "Mouse move - x: " + mouse.x + ", y: " + mouse.y);
        });

        canvas.addMouseUpHandler(event -> mouse.down = false);

        /*
        Pressing Enter clears canvas
         */
        canvas.addKeyPressHandler(event -> {
            if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                context.clearRect(0, 0, width, height);
            }
        });
    }
    private void covered( Label la) {

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

    public void setColor(String col) { this.color = CssColor.make(col); }

}

/**
 * Brush class
 * <p>
 * Stores current and previous(used for painting smoother lines) position of cursor.
 *
 *
 */

class Brush  {
    public int x, y, prev_x, prev_y;
    public boolean down, can_draw;
    public CssColor color;

    Brush(int x, int y, CssColor color) {
        this.x = x;
        this.y = y;
        this.prev_x = x;
        this.prev_y = y;
        this.color = color;

        down = false;
        can_draw = true;
    }

    /**
     *Serializes Brush to String
     */
    public String serialize() {
        return this.x + "," +
               this.y + "," +
               this.prev_x + "," +
               this.prev_y + "," +
               this.color + ",";

    }
}