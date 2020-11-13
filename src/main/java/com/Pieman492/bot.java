package com.Pieman492;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;

public class bot {
    public static void main (String[] args) {
        GatewayDiscordClient client = DiscordClientBuilder.create(APIToken.token)
                .build()
                .login()
                .block();
        client.onDisconnect().block();
    }
}
