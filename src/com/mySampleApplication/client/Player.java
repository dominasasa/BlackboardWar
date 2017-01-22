package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by abcd on 2017-01-22.
 */
public class Player implements IsSerializable {
    public enum Order {
        FIRST, SECOND
    }

    public Brush brush;
    public Order order;
    public String sessionID;

    public Player() {}

    public Player(Order order) {
        this.order = order;
    }

}
