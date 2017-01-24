package com.mySampleApplication.client;

/**
 * Created by abcd on 2017-01-22.
 */


import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Brush class
 * <p>
 * Stores current and previous(used for painting smoother lines) position of cursor.
 *
 *
 */

public class Brush implements IsSerializable {
    public int x, y, prev_x, prev_y;
    public boolean down, can_draw;

    public String color;

    // Empty constructor for serialization
    public Brush() {
        this.x = 0;
        this.y = 0;
        this.prev_x = 0;
        this.prev_y = 0;
        this.color = "black";

        down = false;
        can_draw = false;

    }

    public Brush(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.prev_x = x;
        this.prev_y = y;
        this.color = color;

        down = false;
        can_draw = true;
    }

    public String getColor() {
        return color;
    }

}