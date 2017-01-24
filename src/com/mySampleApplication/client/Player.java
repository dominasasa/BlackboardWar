package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by abcd on 2017-01-22.
 */

/**
 * Class implementing Player
 *
 */
public class Player implements IsSerializable {
    public enum Order {
        FIRST, SECOND
    }

    public Brush brush;
    public Order order;
    public String sessionID;
    public String name;

    // Empty constructor for serialization
    public Player() {}

    /**
     * Player constructor
     *
     * @param sessionID
     * @param name
     * @param color
     * @param order
     */
    public Player(String sessionID, String name, String color, Order order) {
        this.brush = new Brush(0,0, color);
        this.name = name;
        this.order = order;
        this.sessionID = sessionID;
    }

}
