package com.Pieman492.PieCannon.cli;

import discord4j.core.GatewayDiscordClient;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class CLI implements Runnable {

    GatewayDiscordClient client;

    public CLI(GatewayDiscordClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        boolean running = true;
        Scanner consoleInput = new Scanner(System.in);
        while(running) {
            primeConsole();
            LinkedList<String> command = new LinkedList<String>(Arrays.asList(consoleInput.nextLine().split("\\s+")));

        }
    }

    private void primeConsole() {
        System.out.print("\n<Piecannon> ");
    }

    enum COMMANDS {
        SHUTDOWN,
        SPOUT
    }
}
