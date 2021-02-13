package com.Pieman492.PieCannon.commands;

import discord4j.core.GatewayDiscordClient;

public abstract class Command {

    protected String commandPrefix = "";
    protected final char COMMAND_SYMBOL = '!'; // Hardcoded, fix this later;
    protected char getCOMMAND_SYMBOL() {
        return COMMAND_SYMBOL;
    }

    protected abstract void setCommandPrefix();
    protected abstract void establishCommandAgent(GatewayDiscordClient client);

    @Override
    public String toString() {
        return this.commandPrefix;
    }
}
