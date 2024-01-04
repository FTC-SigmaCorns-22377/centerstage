package org.firstinspires.ftc.teamcode.framework

import com.qualcomm.robotcore.hardware.HardwareMap
import java.util.Collections

class CommandScheduler(private var hwMap: HardwareMap, vararg initSubsystems: Subsystem) {
    private var subsystems = ArrayList<Subsystem>()
    private var activeCommands = ArrayList<Command>()
    private var interruptedCommands = ArrayList<Command>()
    private var intents = ArrayList<Intent>()

    init {
        subsystems.addAll(initSubsystems)
    }

    fun initAuto() {
        for (subsystem in subsystems) subsystem.initAuto(hwMap)
    }

    fun initTeleop() {
        for (subsystem in subsystems) subsystem.initTeleop(hwMap)
    }

    fun shutdown() {
        for (subsystem in subsystems) subsystem.shutdown()
    }

    fun addIntent(intent: Intent) {
        intents.add(intent)
    }

    fun run() {
        for (subsystem in subsystems) {
            subsystem.startTimer()
            subsystem.periodic()
            subsystem.stopTimer()
        }

        // detect intents
        for (intent in intents) {
            if (!intent.isReady) {
                // skip this intent
                continue
            }
            if (intent.command.canInterruptOthers) {
                // interrupt all active commands
                for (command in activeCommands) {
                    command.shutdown()
                    interruptedCommands.add(command)
                }
                activeCommands.clear()
            }
        }
        val commands = activeCommands.iterator()
        val nextCommands = ArrayList<Command>()
        while (commands.hasNext()) {
            val command = commands.next()
            command.periodic()
            if (command.completed()) {
                command.shutdown()
                if (command.next != null) {
                    nextCommands.add(command.next!!)
                } else if (command.canInterruptOthers) {
                    // restart all interrupted commands
                    for (interruptedCommand in interruptedCommands) {
                        interruptedCommand.init()
                        activeCommands.add(interruptedCommand)
                    }
                }
                commands.remove()
            }
        }
        for (nextCommand in nextCommands) forceCommand(nextCommand)
    }

    fun forceCommand(command: Command) {
        val nextCommandDependencies = command.dependencies
        val currentCommands = activeCommands.iterator()
        while (currentCommands.hasNext()) {
            val currentCommand = currentCommands.next()
            for (subsystem in currentCommand.dependencies) if (nextCommandDependencies.contains(
                    subsystem
                )
            ) {
                currentCommand.shutdown()
                currentCommands.remove()
                break
            }
        }
        activeCommands.add(command)
        command.init()
    }

    val isEmpty: Boolean
        get() = activeCommands.isEmpty()
}