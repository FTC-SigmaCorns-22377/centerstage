package org.firstinspires.ftc.teamcode.Robot.Commands.MiscCommands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;

public class DelayedCommand extends Command {

	public boolean hasRunInit = false;
	Command targetCommand;
	double timeUntilStartS;
	ElapsedTime timer = new ElapsedTime();

	public DelayedCommand(double timeUntilStartS, Command targetCommand) {
		this.timeUntilStartS = timeUntilStartS;
		this.targetCommand = targetCommand;
	}

	@Override
	public void init() {
		timer.reset();
	}

	@Override
	public void periodic() {
		if (timer.seconds() > timeUntilStartS) {
			if (!hasRunInit) {
				targetCommand.init();
				hasRunInit = true;
			} else {
				targetCommand.periodic();
			}
		}
	}

	@Override
	public boolean completed() {
		return targetCommand.completed();
	}

	@Override
	public void shutdown() {
		targetCommand.shutdown();
	}
}
