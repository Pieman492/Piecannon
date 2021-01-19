package com.Pieman492.PieCannon.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;

public final class Shutdown extends Command {

    public Shutdown(GatewayDiscordClient client) {
        setCommandPrefix();
        establishCommandAgent(client);
    }

    @Override
    protected void setCommandPrefix() {
        this.commandPrefix = COMMAND_SYMBOL + "shutdown SorryPiebot";
    }

    @Override
    protected void establishCommandAgent(GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
        .map(MessageCreateEvent::getMessage)
        .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
        .filter(message -> message.getContent().equalsIgnoreCase(this.commandPrefix))
        .flatMap(message -> {
                message.delete().subscribe().dispose();
                return client.logout();
        })
        .subscribe();
    }
}
