package com.Pieman492.PieCannon.old;

import com.Pieman492.PieCannon.commands.Command;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

public class Help extends Command {

    public Help(GatewayDiscordClient client){
        setCommandPrefix();
        establishCommandAgent(client);
    }

    @Override
    protected void setCommandPrefix() {
        this.commandPrefix = COMMAND_SYMBOL + "help";
    }

    @Override
    protected void establishCommandAgent(GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
        .map(MessageCreateEvent::getMessage)
        .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
        .filter(message -> message.getContent().equalsIgnoreCase(this.commandPrefix))
        .flatMap(Message::getChannel)
        .flatMap(channel -> channel.createMessage("God helps those who help themselves."))
        .subscribe();

    }
}
