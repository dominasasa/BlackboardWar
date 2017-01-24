package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Brush class
 * <p>
 * Stores current and previous(used for painting smoother lines) positions of the cursor.
 *
 */

public class Brush implements IsSerializable {
    /**
     * Coordinates of current and previous position of the cursor.
     */
    public int x, y, prev_x, prev_y;

    /**
     * Boolean flags indicating drawing.
     */
    public boolean down, can_draw;

    /**
     * Colour of the brush.
     */
    public String color;

    /**
     *  Empty constructor for serialization
     */
    public Brush() {
        this.x = 0;
        this.y = 0;
        this.prev_x = 0;
        this.prev_y = 0;
        this.color = "black";

        down = false;
        can_draw = false;
    }

    /**
     * Brush constructor.
     *
     * @param x horizontal coordinate of the brush
     * @param y vertical coordinate of the brush
     * @param color color of the brush
     */
    public Brush(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.prev_x = x;
        this.prev_y = y;
        this.color = color;

        down = false;
        can_draw = true;
    }

    /**
     * Get brush color
     * @return
     */
    public String getColor() {
        return color;
    }

}