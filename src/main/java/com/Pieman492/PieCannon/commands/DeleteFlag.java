package com.Pieman492.PieCannon.commands;

import com.Pieman492.PieCannon.core.BotHelper;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.object.entity.Message;

public class DeleteFlag extends InternalCommand {

    public DeleteFlag(GatewayDiscordClient client) {
        establishCommandAgent(client);
    }

    protected void establishCommandAgent(GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
        .map(MessageCreateEvent::getMessage)
        .filter(message -> BotHelper.CONTAINS_DELETION_FLAG.matcher(message.getContent()).find())
        .flatMap(Message::delete)
        .subscribe();

        client.getEventDispatcher().on(MessageUpdateEvent.class)
        .flatMap(MessageUpdateEvent::getMessage)
        .filter(message -> BotHelper.CONTAINS_DELETION_FLAG.matcher(message.getContent()).find())
        .flatMap(Message::delete)
        .subscribe();
    }

}
