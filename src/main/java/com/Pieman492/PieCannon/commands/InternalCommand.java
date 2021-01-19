package com.Pieman492.PieCannon.commands;

public abstract class InternalCommand extends Command {
    protected void setCommandPrefix() {
        commandPrefix = null;
    }
}
