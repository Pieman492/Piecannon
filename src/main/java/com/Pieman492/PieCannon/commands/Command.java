package com.Pieman492.PieCannon.commands;

import discord4j.core.GatewayDiscordClient;

public abstract class Command {

    protected String commandPrefix;
    public final char COMMAND_SYMBOL = '!'; // Hardcoded, fix this later;
    public char getCOMMAND_SYMBOL() {
        return COMMAND_SYMBOL;
    }

    protected abstract void setCommandPrefix();
    protected abstract void establishCommandAgent(GatewayDiscordClient client);
}
