package com.Pieman492.PieCannon;

import com.Pieman492.PieCannon.commands.*;
import com.Pieman492.PieCannon.core.ConnectionHandler;

public class Piecannon {

    public static void main(String[] args) {

        ConnectionHandler connectionHandler = new ConnectionHandler();
        new DeleteFlag(connectionHandler.getClient());
        new InfoCommand(connectionHandler.getClient(), "ping", "Pong!");
        new InfoCommand(connectionHandler.getClient(), "help", "God helps those who help themselves.");
        new Annoy(connectionHandler.getClient());
        new FightGroovy(connectionHandler.getClient());
        new Shutdown(connectionHandler.getClient());

        connectionHandler.getClient().onDisconnect().block();
    }
}
