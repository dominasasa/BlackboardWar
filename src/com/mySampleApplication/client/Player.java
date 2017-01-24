package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Class implementing Player
 *
 */
public class Player implements IsSerializable {
    public enum Order {
        FIRST, SECOND
    }

    /**
     * Player's brush.
     */
    public Brush brush;

    /**
     * Player's number ( 1 or 2).
     */
    public Order order;

    /**
     * ID of the session.
     */
    public String sessionID;

    /**
     * Name of the player.
     */
    public String name;

    /**
     * Empty constructor for serialization
     */
    public Player() {}

    /**
     * Player constructor
     *<p>
     *     Creates new player.
     *</p>
     * @param sessionID session ID of the player
     * @param name name of the player
     * @param color color of hte brush
     * @param order number of the player
     */
    public Player(String sessionID, String name, String color, Order order) {
        this.brush = new Brush(0,0, color);
        this.name = name;
        this.order = order;
        this.sessionID = sessionID;
    }

}
