package com.Pieman492.PieCannon.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Flux;
import java.time.Duration;


public final class FightGroovy extends RepeatingCommand {

    private Member groovy;
    private MessageChannel commandChannel;
    private final Duration deafenDelay = Duration.ofSeconds(1);

    public FightGroovy(GatewayDiscordClient client){

        setCommandPrefix();
        establishCommandStarter(client);
        establishCommandStopper(client);
        establishCommandAgent(client);
    }

    @Override
    protected void setCommandPrefix() {
        this.commandPrefix = this.getCOMMAND_SYMBOL() + "fightgroovy";
    }

    @Override
    protected void establishCommandStarter(GatewayDiscordClient client) {

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                // User exists and isn't a bot
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                // Check is message matches format and contains the start flag
                .filter(message -> message.getContent().toLowerCase().startsWith(this.commandPrefix + " start"))
                .flatMap(message -> {
                    message.getChannel()
                    .subscribe(this::setCommandChannel)
                    .dispose();

                    message.getGuild()
                    .flatMap(guild -> guild.getMemberById(Snowflake.of(234395307759108106L)))
                    .subscribe(this::setGroovy)
                    .dispose();

                    setCommandActive(true);

                    return commandChannel.createMessage("Yeah I've always had it out for that Groovy guy. Lets take him out!");
                })
                .subscribe();
    }

    @Override
    protected void establishCommandStopper(GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getContent().toLowerCase().startsWith(this.commandPrefix + " ceasefire"))
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false)) // User isn't a bot and exists
                .flatMap(message -> {
                    this.setCommandActive(false);
                    return commandChannel.createMessage("Fine, I'll lay off. For now.");
                })
                .subscribe();
    }

    @Override
    protected void establishCommandAgent(GatewayDiscordClient client) {
        Flux.interval(getDeafenDelay())
        .filter(pulse -> this.commandActive)
        .flatMap(pulse -> groovy.edit(edit -> edit.setDeafen(false)))
        .subscribe();
    }

    public void setCommandChannel(MessageChannel commandChannel) {
        this.commandChannel = commandChannel;
    }

    private void setCommandActive(boolean flag) {
        this.commandActive = flag;
    }

    private void setGroovy(Member member) {
        this.groovy = member;
    }

    private Duration getDeafenDelay() {
        return deafenDelay;
    }

}
