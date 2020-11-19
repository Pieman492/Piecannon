
package com.Pieman492;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Flux;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class bot {

    public static void main(String[] args) {

        // This is the bot's API Token.
        String token = "Nzc2NjAyMTg2MjAwNzc2NzA1.X63RJQ.IbExEdVu9rdVcwI39pFdNBa9bPk";

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
                .filter(message -> message.getContent().equals("!DearBuckets"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("<@410240888657149970> come play video games"))
                .subscribe();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!ping"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();
/*
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().toLowerCase().startsWith("!annoy"))
                .filter(message -> message.getContent().toLowerCase().matches("<@\\d{18}>\\Z"))
                .flatMap(message -> {
                    return Flux.just(message.getContent(), "Test", "String");
                })
                .subscribe(
*/

        client.onDisconnect().block();
    }
}


