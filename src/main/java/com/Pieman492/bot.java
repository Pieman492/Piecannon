package com.Pieman492;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;

public class bot {
    public static void main (String[] args) {
        GatewayDiscordClient client = DiscordClientBuilder.create("Nzc2NjAyMTg2MjAwNzc2NzA1.X63RJQ.BMiyD_YQWp6cDd3Ii_UoJYPmvHM")
                .build()
                .login()
                .block();
        client.onDisconnect().block();
    }
}
