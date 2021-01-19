package com.Pieman492.PieCannon.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

public class InfoCommand extends Command{
    String call;
    String response;

    public InfoCommand(GatewayDiscordClient client, String call, String response){
        this.call = call;
        this.response = response;
        setCommandPrefix();
        establishCommandAgent(client);
    }

    @Override
    protected void setCommandPrefix() {
        this.commandPrefix = COMMAND_SYMBOL + this.call;
    }

    @Override
    protected void establishCommandAgent(GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase(this.commandPrefix))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(this.response))
                .subscribe();

    }
}
