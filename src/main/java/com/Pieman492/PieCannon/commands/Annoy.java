package com.Pieman492.PieCannon.commands;

import com.Pieman492.PieCannon.core.BotHelper;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public final class Annoy extends RepeatingCommand {

    private User target;
    private MessageChannel commandChannel;
    private boolean commandActive = false;

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
            .filter(messageEvent -> messageEvent.getMessage().getContent().toLowerCase().startsWith(commandPrefix)) // Starts with !annoy
            .filter(messageEvent -> {
                return BotHelper.getENDS_WITH_USER_PING().matcher(messageEvent.getMessage().getContent()).matches(); // Ends with a user ping
            })
            .filter(messageEvent -> messageEvent.getMessage().getAuthor().map(User::isBot).orElse(false)) // User isn't a bot and exists
            .map(messageEvent -> {
                String targetSnowflake = messageEvent.getMessage().getContent();
                targetSnowflake = targetSnowflake.substring(targetSnowflake.lastIndexOf('@')+1, targetSnowflake.lastIndexOf('>'));
                client.getUserById(Snowflake.of(Long.parseLong(targetSnowflake)))
                .subscribe(this::setCommandTarget)
                .dispose();

                messageEvent.getMessage().getChannel()
                .subscribe(this::setCommandChannel);

                return true;
            }).subscribe(this::setCommandActive);
    }

    protected void establishCommandStopper(GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
            .filter(messageEvent -> messageEvent.getMessage().getContent().startsWith(commandPrefix)) // Starts with !annoy
            .filter(messageEvent -> messageEvent.getMessage().getContent().endsWith("stop")) // Ends with stop
            .filter(messageEvent -> messageEvent.getMessage().getAuthor().map(User::isBot).orElse(false)) // User isn't a bot and exists
            .map(messageEvent -> false)
            .subscribe(this::setCommandActive);
    }

    protected void establishCommandAgent(GatewayDiscordClient client) {
        Mono.just("")
        .delayElement(BotHelper.randomizedDelay(2,5));
        commandChannel.createMessage("<@" + target.getId().asString() + ">")
        .filter(message -> commandActive)
        .repeat();
    }

    private void setCommandActive(boolean flag) {
        commandActive = flag;
    }

    private void setCommandTarget(User user) {
        target = user;
    }

    private void setCommandChannel(MessageChannel channel) {
        commandChannel = channel;
    }

    @Override
    public String toString() {
        return "!annoy";
    }
}
