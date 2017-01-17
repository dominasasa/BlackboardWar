package com.mySampleApplication.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.*;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by abcd on 2017-01-17.
 */


/**
 * Class implementing game board
 *
 * Constructor takes width, height, playerColor
 */
public class Board {
    final Canvas canvas ;
    final Context2d context;

    final Brush mouse = new Brush(0,0);
    final CssColor color ; //= CssColor.make("black");

    public Board(int width, int height, String playerColor) {

        // DEBUG
        Logger log = Logger.getLogger("hwdp") ;

        canvas = Canvas.createIfSupported();
        context = canvas.getContext2d();
        color = CssColor.make(playerColor);

        /*
        setSize() takes String as parameters, not int
         */
        canvas.setSize(Integer.toString(width), Integer.toString(height));

        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);


        canvas.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                mouse.down = true;
                mouse.x = event.getX();
                mouse.y = event.getY();

                //DEBUG
                log.log(Level.INFO, "Mouse down - x: " + mouse.x + ", y: " + mouse.y);
            }
        });

        /*
        Actual painting
         */
        canvas.addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                mouse.prev_x = mouse.x;
                mouse.prev_y = mouse.y;
                if(mouse.down && mouse.can_draw) {
                    mouse.x = event.getX();
                    mouse.y = event.getY();
                    context.beginPath();
                    context.setStrokeStyle(color);
                    context.moveTo(mouse.prev_x, mouse.prev_y);
                    context.lineTo(mouse.x, mouse.y);
                    context.closePath();
                    context.stroke();

                    //DEBUG
                    log.log(Level.INFO, "drawing");
                }

                //DEBUG
                log.log(Level.INFO, "Mouse move - x: " + mouse.x + ", y: " + mouse.y);
            }
        });

        canvas.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                mouse.down = false;
            }
        });

        /*
        Pressing Enter clears canvas
         */
        canvas.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    context.clearRect(0,0, width,height);
                }
            }
        });
    }

    /*
    Returns board canvas
     */
    Canvas getBoard() {
        return canvas;
    }

}

/**
 * Brush class
 *
 * Stores current and previous(used for painting smoother lines) position of cursor.
 *
 *
 */

class Brush {
    public int x, y, prev_x, prev_y;
    public boolean down, can_draw;

    Brush(int x, int y) {
        this.x = x;
        this.y = y;
        this.prev_x = x;
        this.prev_y = y;
        down = false;
        can_draw = true;
    }
}