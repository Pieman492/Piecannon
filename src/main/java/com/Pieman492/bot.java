package com.Pieman492;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

public class bot {

    // Object oriented code. Bad! I started auto piloting, this isn't reactive!

    /*
    static boolean isBotCommand (String message) {
        if (message.startsWith("ι")) {
            return true;
        } else {
            return false;
        }
    }
    static Message generateResponse(String inputCommand) {
        Message outputMessage = new Message();
        switch (inputCommand.substring(1).toLowerCase()) {
            case "ping":

        }
    }
    */
    public static void main (String[] args) {
        // Command prefix
        // final char commandFlag = 'ι';

        // Establish connection
        GatewayDiscordClient client = DiscordClientBuilder.create(APIToken.token)
                .build()
                .login()
                .block();

        // Outputs message to console when connection is established
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                });

        // Funny command that took way too long to understand
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!ping"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();

        client.onDisconnect().block();
    }
}
