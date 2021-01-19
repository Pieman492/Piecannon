package com.Pieman492.PieCannon.commands;

import discord4j.core.GatewayDiscordClient;

public abstract class RepeatingCommand extends Command {
    protected boolean commandActive = false;
    protected abstract void establishCommandStarter(GatewayDiscordClient client);
    protected abstract void establishCommandStopper(GatewayDiscordClient client);
    protected abstract void establishCommandAgent(GatewayDiscordClient client);
}
