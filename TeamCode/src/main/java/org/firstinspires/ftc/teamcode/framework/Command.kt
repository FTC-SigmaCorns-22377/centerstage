package org.firstinspires.ftc.teamcode.framework

import java.util.Collections
import java.util.function.Supplier

abstract class Command(vararg subsystems: Subsystem) {
    var next: Command? = null
    var dependencies = ArrayList<Subsystem>()
        protected set
    var canInterruptOthers = false

    init {
        dependencies.addAll(subsystems)
    }

    fun addNext(command: Command?): Command {
        var commandNode: Command? = this
        while (commandNode!!.next != null) commandNode = commandNode.next
        commandNode.next = command
        return this
    }

    abstract fun init()
    abstract fun periodic()
    abstract fun completed(): Boolean
    abstract fun shutdown()
    fun interruptOthers(): Command {
        canInterruptOthers = true
        return this
    }

    fun `when`(condition: Supplier<Boolean>): Intent {
        return Intent(this, condition)
    }
}