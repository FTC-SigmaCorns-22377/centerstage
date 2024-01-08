package org.firstinspires.ftc.teamcode.Robot.Commands.MiscCommands;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;

import java.util.function.Supplier;

public class RunCommandLegacy extends Command {
	Supplier<Command> callback;

	public RunCommandLegacy(Supplier<Command> callback) {
		this.callback = callback;
	}

	@Override
	public void init() {
		setNext(callback.get());
	}

	@Override
	public void periodic() {

	}

	@Override
	public boolean completed() {
		return true;
	}

	@Override
	public void shutdown() {

	}
}