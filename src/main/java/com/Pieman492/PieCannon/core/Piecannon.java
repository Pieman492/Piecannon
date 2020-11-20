package com.Pieman492.PieCannon.core;

import com.Pieman492.PieCannon.commands.*;

public class Piecannon {

    public static void main(String[] args) {

        ConnectionHandler connectionHandler = new ConnectionHandler();
        new Annoy(connectionHandler.getClient());
        new Ping(connectionHandler.getClient());

    }
}
