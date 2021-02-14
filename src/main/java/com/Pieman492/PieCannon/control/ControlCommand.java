package com.Pieman492.PieCannon.control;

public abstract class ControlCommand {
    private Controller.COMMANDS identifier;
    public abstract void execute();
}
