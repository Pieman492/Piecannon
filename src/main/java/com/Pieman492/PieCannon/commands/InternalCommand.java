package com.Pieman492.PieCannon.commands;

public abstract class InternalCommand extends Command {
    protected final void setCommandPrefix() {
        commandPrefix = null;
    }
}
