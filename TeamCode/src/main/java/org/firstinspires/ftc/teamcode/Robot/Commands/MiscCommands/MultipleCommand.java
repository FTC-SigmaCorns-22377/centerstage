package org.firstinspires.ftc.teamcode.Robot.Commands.MiscCommands;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Run multiple commands at once, will complete once ALL are complete,
 */
public class MultipleCommand extends Command {

	protected ArrayList<Command> commands = new ArrayList<>();

	public MultipleCommand(Command... commands) {
		Collections.addAll(this.commands, commands);

	}

	@Override
	public void init() {
		for (Command command : commands) {
			command.init();
		}
	}

	@Override
	public void periodic() {
		for (Command command : commands) {
			command.periodic();
		}

		// move each command to next command if the command has completed
		for (int i = 0; i < commands.size(); i++) {
			if (commands.get(i).completed() && commands.get(i).getNext() != null) {
				commands.get(i).shutdown();

				commands.set(i, commands.get(i).getNext());
				commands.get(i).init();
				commands.get(i).periodic(); // maybe just don't
			}
		}

//		cleanup();
		System.out.println("Number of commands in the multiple command: " + commands.size());
	}

	/**
	 * checks if ALL of the actions are done
	 *
	 * @return false if any one action is false, returns true if all are true
	 */
	@Override
	public boolean completed() {

		for (Command command : commands) {
			if (!command.completed()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void shutdown() {
		for (Command command : commands) {
			command.shutdown();
		}
	}

	public void cleanup() {
		ArrayList<Command> commandsToAdd = new ArrayList<>();
		ArrayList<Command> commandsToRemove = new ArrayList<>();

		for (Command command : commands) {
			if (command.completed()) {
				if (command.getNext() != null) {
					if (!command.getNext().completed()) {
						System.out.println("added command " + command.getNext());
						commandsToAdd.add(command.getNext());
						commandsToRemove.add(command);
					}
				}
			}
		}

		for (Command command : commandsToRemove) {
			commands.remove(command);
		}

		commands.addAll(commandsToAdd);
	}
}
