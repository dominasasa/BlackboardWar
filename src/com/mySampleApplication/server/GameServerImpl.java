package com.mySampleApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.client.GameServer;
import com.mySampleApplication.client.Player;

import java.util.HashMap;


/**
 * Created by abcd on 2017-01-21.
 */
public class GameServerImpl extends RemoteServiceServlet implements GameServer {
    HashMap<String, GameSession> sessions = new HashMap<>();

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

    @Override
    public Player[] getPlayer(String sessionID) {
        return sessions.get(sessionID).players;
    }


    class GameSession {
        String ID;
        Player[] players;
        
        int playerCount;

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