package com.Pieman492.PieCannon.control;

import java.util.ArrayList;

public class Controller {

    ArrayList<ControlCommand> commandList = new ArrayList<ControlCommand>();

    public Controller() {


    }
    enum COMMANDS {

        SHUTDOWN,
        SPOUT
    }

}
