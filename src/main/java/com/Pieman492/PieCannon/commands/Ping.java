package com.Pieman492.PieCannon.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

public class Ping extends Command {

    public Ping(GatewayDiscordClient client) {
        setCommandPrefix();
        establishCommandAgent(client);
    }

    protected void setCommandPrefix() {
        this.commandPrefix = this.getCOMMAND_SYMBOL() + "ping";
    }

    // Call-response test command, useful for diagnostics and the first command implemented!
    protected void establishCommandAgent(GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase(commandPrefix))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();
    }
}

