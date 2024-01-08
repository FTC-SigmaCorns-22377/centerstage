package org.firstinspires.ftc.teamcode.Robot.Commands.MiscCommands;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;

public class RaceAction extends MultipleCommand {
	public RaceAction(Command... commands) {
		super(commands);
	}

	@Override
	public boolean completed() {

		for (Command command : commands) {
			if (command.completed()) {
				for (Command c : commands) {
					c.shutdown();
				}
				return true;
			}
		}

		return false;
	}

}
