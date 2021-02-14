package com.Pieman492.PieCannon.control;

import discord4j.core.GatewayDiscordClient;

public class Shutdown extends ControlCommand {
    GatewayDiscordClient client;

    public Shutdown(GatewayDiscordClient client) {
        this.client = client;
    }

    @Override
    public void execute() {
        this.client.logout();
    }

}
