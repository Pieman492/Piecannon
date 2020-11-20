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
    private final int pingDelayLowerBound = 2;
    private final int pingDelayUpperBound = 5;

    public Annoy(GatewayDiscordClient client){
        setCommandPrefix();
        establishCommandStarter(client);
        establishCommandStopper(client);
        establishCommandAgent(client);
    }

    protected void setCommandPrefix() {
        this.commandPrefix = this.getCOMMAND_SYMBOL() + "annoy";
        System.out.println(commandPrefix);
    }

    protected void establishCommandStarter(GatewayDiscordClient client) {
        System.out.println("Annoy establish starter method reached!");
        client.getEventDispatcher().on(MessageCreateEvent.class)
            .filter(messageEvent -> messageEvent.getMessage().getContent().toLowerCase().startsWith(commandPrefix)) // Starts with !annoy
            .filter(messageEvent -> BotHelper.getENDS_WITH_USER_PING().matcher(messageEvent.getMessage().getContent()).find()) // Ends with a user ping
            .filter(messageEvent -> {
                return messageEvent.getMessage().getAuthor().map(user -> !user.isBot()).orElse(false);
            }) // User isn't a bot and exists
            .map(messageEvent -> {
                String targetSnowflake = messageEvent.getMessage().getContent();
                targetSnowflake = targetSnowflake.substring(targetSnowflake.lastIndexOf('!')+1, targetSnowflake.lastIndexOf('>'));
                setCommandTargetId(targetSnowflake);

                messageEvent.getMessage().getChannel()
                .subscribe(this::setCommandChannel)
                .dispose();

                return true;
            })
            .subscribe(this::setCommandActive);
    }

    protected void establishCommandStopper(GatewayDiscordClient client) {
        System.out.println("Annoy establish stopper method reached!");
        client.getEventDispatcher().on(MessageCreateEvent.class)
            .filter(messageEvent -> messageEvent.getMessage().getContent().startsWith(commandPrefix + " stop")) // Starts with !annoy
            .filter(messageEvent -> messageEvent.getMessage().getAuthor().map(user -> !user.isBot()).orElse(false)) // User isn't a bot and exists
            .map(messageEvent -> false)
            .subscribe(this::setCommandActive);
    }

    protected void establishCommandAgent(GatewayDiscordClient client) {
        System.out.println("Annoy establish agent method reached!");
        Flux.interval(BotHelper.randomizedDelay(pingDelayUpperBound,pingDelayLowerBound))
        .filter(delay ->  commandActive)
        .flatMap(channel -> commandChannel.createMessage("<@!" + commandTargetId + ">"))
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
