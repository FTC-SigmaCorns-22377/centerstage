package org.firstinspires.ftc.teamcode.framework

import java.util.function.Supplier

class Intent(var command: Command, private var condition: Supplier<Boolean>) {
    val isReady: Boolean
        get() = condition.get()
}