package org.firstinspires.ftc.teamcode.CommandFramework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Supplier;

public abstract class Command {
	protected Command nextCommand = null;
	protected ArrayList<Subsystem> dependencies = new ArrayList<>();
	protected boolean canInterruptOthers = false;


	public Command(Subsystem... subsystems) {
		Collections.addAll(dependencies, subsystems);
	}

	public Command getNext() {
		return nextCommand;
	}

	public void setNext(Command command) {
		nextCommand = command;
	}

	public Command addNext(Command command) {
		Command commandNode = this;
		while (commandNode.getNext() != null)
			commandNode = commandNode.getNext();

		commandNode.setNext(command);

		return this;
	}

	public ArrayList<Subsystem> getDependencies() {
		return dependencies;
	}

	public abstract void init();

	public abstract void periodic();

	public abstract boolean completed();

	public abstract void shutdown();

	public Command interruptOthers() {
		canInterruptOthers = true;
		return this;
	}


	public Intent when(Supplier<Boolean> condition) {
	    return new Intent(this, condition);
	}
}
