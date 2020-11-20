package com.Pieman492.PieCannon.core;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;

public class ConnectionHandler {
    private GatewayDiscordClient client;
    public ConnectionHandler() {
        // Establish connection
        client = DiscordClientBuilder.create(BotHelper.grabToken())
                .build()
                .login()
                .block();

        // Outputs message to console when connection is established
        client.getEventDispatcher().on(ReadyEvent.class)
            .subscribe(event -> {
                User self = event.getSelf();
                System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
            });

        /* I'm not totally sure but I believe this puts
         * the bot in some kind of stasis when
         * it goes offline so it can connect again.
         */
        client.onDisconnect().block();

    }

    public GatewayDiscordClient getClient() {
        return client;
    }

}
