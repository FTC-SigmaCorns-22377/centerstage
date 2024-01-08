package org.firstinspires.ftc.teamcode.Robot.Commands.MiscCommands;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;

import java.util.function.Supplier;

public class RunCommand extends Command {
	Supplier<Command> callback;


	public RunCommand(Supplier<Command> callback) {
		this.callback = callback;
	}

	@Override
	public void init() {
		// Run the callback to get which command to run conditionally, and set whatever command was originally supposed to run after this command after the conditional command
		Command conditionalCommand = callback.get();
		Command current = conditionalCommand;
		while (current.getNext() != null)
			current = current.getNext();

		current.setNext(this.nextCommand);
		setNext(conditionalCommand);
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
