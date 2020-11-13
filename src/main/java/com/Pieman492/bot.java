package com.Pieman492;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class bot {

    public static void main (String[] args) {

        // Object oriented pull of AI token from file. Currently using a dev file.
        String token = "";
        try {
            FileInputStream tokenInput = new FileInputStream("src/main/resources/APIToken");
            Scanner tokenScanner = new Scanner(tokenInput);
            token = tokenScanner.nextLine();
            tokenInput.close();
            tokenScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Establish connection
        GatewayDiscordClient client = DiscordClientBuilder.create(token)
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
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();

        client.onDisconnect().block();
    }
}
