package org.firstinspires.ftc.teamcode.CommandFramework;

import java.util.function.Supplier;

public class Intent {
    protected Command command;
    protected Supplier<Boolean> condition;

    public Intent(Command command, Supplier<Boolean> condition) {
        this.command = command;
        this.condition = condition;
    }

    public Command getCommand() {
        return command;
    }

    public boolean isReady() {
        return condition.get();
    }





}
