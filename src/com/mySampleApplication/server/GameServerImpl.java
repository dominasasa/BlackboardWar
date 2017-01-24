package com.mySampleApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.client.GameServer;
import com.mySampleApplication.client.Player;

import java.util.HashMap;


/**
 * Implementation of application server.
 */
public class GameServerImpl extends RemoteServiceServlet implements GameServer {
    HashMap<String, GameSession> sessions = new HashMap<>();

    /**
     * Creates session fo current player.
     *<p>
     *    For new player,constructor creates new session with given ID. If the entered ID is already used and
     *    the number of players is smaller than 2, then constructor creates new Player and adds it
     *    to already existing session. If the number of players in given session is greater or equal to 2,
     *    constructor returns null.
     *</p>
     * @param ID session ID entered by the player
     * @param name player's name
     * @param color color chosen by the player
     * @return On success: returns Player object, representing current player.
     *         On failure returns null.
     */
    @Override
    public Player createSession(String ID, String name, String color) {
        System.out.println("Got ID: " + ID);
        if (sessions.containsKey(ID)) {
            if (sessions.get(ID).playerCount < 2) {
                System.out.println("Adding to session with ID: " + ID);
                Player player = new Player(ID, name, color, Player.Order.SECOND);
                sessions.get(ID).setPlayer2(player);
                sessions.get(ID).playerCount++;
                return player;
            } else {
                System.out.println("ID: " + ID + "is already used!");
                return null;
            }

        } else {
            System.out.println("Creating session with ID: " + ID);
            GameSession gameSession = new GameSession(ID);
            Player player = new Player(ID, name, color, Player.Order.FIRST);
            gameSession.setPlayer1(player);
            sessions.put(ID, gameSession);
            sessions.get(ID).playerCount++;
            return player;
        }
    }

    /**
     * Sends current player to the application server.
     * @param player current player
     */
    @Override
    public void sendPlayer(Player player) {
        if(player.order == Player.Order.FIRST) {
            sessions.get(player.sessionID).setPlayer1(player);
            System.out.println("Player 1: " + player.brush.x + ", " + player.brush.y);
        } else {
            sessions.get(player.sessionID).setPlayer2(player);
            System.out.println("Player 2: " + player.brush.x + ", " + player.brush.y);
        }
    }

    /**
     * Returns players of the given session.
     * @param sessionID
     * @return Player array
     */
    @Override
    public Player[] getPlayer(String sessionID) {
        return sessions.get(sessionID).players;
    }


    /**
     * Class representing session of the current game.
     */
    class GameSession {
        /**
         * ID of the session.
         */
        String ID;

        /**
         * Array of players of the current game.
         */
        Player[] players;

        /**
         * Number of players.
         */
        int playerCount;

        /**
         * Creates new GameSession
         * @param ID ID of the session to be created.
         */
        GameSession(String ID) {
            this.ID = ID;
            this.playerCount = 0;
            this.players = new Player[2];
        }

        public void setPlayer1(Player player) {
            this.players[0] = player;
        }

        public void setPlayer2(Player player) {
            this.players[1] = player;
        }

        public Player getPlayer1() {
            return this.players[0];
        }

        public Player getPlayer2() {
            return this.players[1];
        }

    }
}