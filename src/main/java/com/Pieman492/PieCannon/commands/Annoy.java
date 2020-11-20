package com.Pieman492.PieCannon.commands;

import com.Pieman492.PieCannon.core.BotHelper;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Flux;

public final class Annoy extends RepeatingCommand {

    private String commandTargetId;
    private MessageChannel commandChannel;
    private boolean commandActive = false;
    private final int pingDelayLowerBound = 15;
    private final int pingDelayUpperBound = 30;

    public Annoy(GatewayDiscordClient client){

        setCommandPrefix();
        establishCommandStarter(client);
        establishCommandStopper(client);
        establishCommandAgent(client);
    }

    protected void setCommandPrefix() {

        this.commandPrefix = this.getCOMMAND_SYMBOL() + "annoy";
    }

    protected void establishCommandStarter(GatewayDiscordClient client) {

        client.getEventDispatcher().on(MessageCreateEvent.class)
            .map(MessageCreateEvent::getMessage)
            // Starts with !annoy
            .filter(message -> message.getContent().toLowerCase().startsWith(commandPrefix))
            // Ends with a user ping
            .filter(message -> BotHelper.ENDS_WITH_USER_PING.matcher(message.getContent()).find())
            // User both isn't a bot and exists
            .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
            // Doing the following is hacky and a violation of everything
            // conventional about functional and reactive programming.
            // Too bad!
            .flatMap(message -> {
                String targetSnowflake = message.getContent();
                targetSnowflake = targetSnowflake.substring(targetSnowflake.lastIndexOf('!')+1, targetSnowflake.lastIndexOf('>'));
                setCommandTargetId(targetSnowflake);

                message.getChannel()
                .subscribe(this::setCommandChannel)
                .dispose();

                setCommandActive(true);
                return message.delete();
            })
            .subscribe();
    }

    protected void establishCommandStopper(GatewayDiscordClient client) {

        client.getEventDispatcher().on(MessageCreateEvent.class)
            .map(MessageCreateEvent::getMessage)
            .filter(message -> message.getContent().startsWith(commandPrefix + " stop")) // Starts with !annoy
            .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false)) // User isn't a bot and exists
            .flatMap(message -> {
                setCommandActive(false);
                return message.delete();
            })
            .subscribe();
    }

    protected void establishCommandAgent(GatewayDiscordClient client) {

        Flux.interval(BotHelper.randomizedDelay(pingDelayUpperBound,pingDelayLowerBound))
        .filter(delay -> commandActive)
        .flatMap(channel -> commandChannel.createMessage("[DeletionFlag] <@!" + commandTargetId + ">"))
        .subscribe();

    }

    private void setCommandActive(boolean flag) {

        commandActive = flag;
    }

    private void setCommandTargetId(String userId) {

        commandTargetId = userId;
    }

    private void setCommandChannel(MessageChannel channel) {

        commandChannel = channel;
    }

    @Override
    public String toString() {
        return "!annoy";
    }
}
