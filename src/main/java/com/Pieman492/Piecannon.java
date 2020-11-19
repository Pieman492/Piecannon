package com.Pieman492;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

public class Piecannon {

    public static void main (String[] args) {

        // Establish connection
        GatewayDiscordClient client = DiscordClientBuilder.create(BotHelper.grabToken())
                .build()
                .login()
                .block();

        // Outputs message to console when connection is established
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                });

        // Funny command that took way too long to understand
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!ping"))
                .flatMap(Message::getChannel)
                .delayElements(BotHelper.randomizedDelay(10,5)) // Randomized delay of 5-10 seconds
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();
        client.onDisconnect().block();

    }
}
