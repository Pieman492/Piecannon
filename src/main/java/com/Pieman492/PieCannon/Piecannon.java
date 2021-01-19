package com.Pieman492.PieCannon;

import com.Pieman492.PieCannon.commands.*;
import com.Pieman492.PieCannon.core.ConnectionHandler;

public class Piecannon {

    public static void main(String[] args) {

        ConnectionHandler connectionHandler = new ConnectionHandler();
        new DeleteFlag(connectionHandler.getClient());
        new Ping(connectionHandler.getClient());
        new Annoy(connectionHandler.getClient());
        new FightGroovy(connectionHandler.getClient());

        connectionHandler.getClient().onDisconnect().block();

    }
}
