package org.firstinspires.ftc.teamcode.robot.subsystems

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap

import org.firstinspires.ftc.teamcode.framework.Command
import org.firstinspires.ftc.teamcode.framework.CommandScheduler
import org.firstinspires.ftc.teamcode.framework.Subsystem

class Input(private val gamepad: Gamepad, private val scheduler: CommandScheduler) : Subsystem() {
    class ButtonWrapper(private val buttonStateProvider: () -> Boolean, private val scheduler: CommandScheduler) {
        private var value: Boolean = buttonStateProvider.invoke() // Initializing with the current state
        private var lastValue: Boolean = value
        private var command: Command? = null

        // Sets the command that will be scheduled when the button is pressed.
        fun setOnPress(command: Command) {
            this.command = command
        }

        // To be called every update cycle
        fun update() {
            lastValue = value
            value = buttonStateProvider.invoke()
            if (!lastValue && value) {
                command?.let { scheduler.forceCommand(it) }
            }
        }
    }

    class JoystickWrapper(private val joystickStateProvider: () -> Float) {
        private var value: Float = joystickStateProvider.invoke() // Initializing with the current state
    }

    val cross = ButtonWrapper({ gamepad.cross }, scheduler)
    val circle = ButtonWrapper({ gamepad.circle }, scheduler)
    val square = ButtonWrapper({ gamepad.square }, scheduler)
    val triangle = ButtonWrapper({ gamepad.triangle }, scheduler)
    val leftStickButton = ButtonWrapper({ gamepad.left_stick_button }, scheduler)
    val rightStickButton = ButtonWrapper({ gamepad.right_stick_button }, scheduler)
    val dpadUp = ButtonWrapper({ gamepad.dpad_up }, scheduler)
    val dpadDown = ButtonWrapper({ gamepad.dpad_down }, scheduler)
    val dpadLeft = ButtonWrapper({ gamepad.dpad_left }, scheduler)
    val dpadRight = ButtonWrapper({ gamepad.dpad_right }, scheduler)
    val shareButton = ButtonWrapper({ gamepad.share }, scheduler)
    val options = ButtonWrapper({ gamepad.options }, scheduler)
    val psButton = ButtonWrapper({ gamepad.ps }, scheduler)
    val leftBumper = ButtonWrapper({ gamepad.left_bumper }, scheduler)
    val rightBumper = ButtonWrapper({ gamepad.right_bumper }, scheduler)
    val leftTriggerPress = ButtonWrapper({ gamepad.left_trigger > 0.5 }, scheduler)
    val rightTriggerPress = ButtonWrapper({ gamepad.right_trigger > 0.5 }, scheduler)

    val leftStickX = JoystickWrapper { gamepad.left_stick_x }
    val leftStickY = JoystickWrapper { gamepad.left_stick_y }
    val rightStickX = JoystickWrapper { gamepad.right_stick_x }
    val rightStickY = JoystickWrapper { gamepad.right_stick_y }
    val leftTrigger = JoystickWrapper { gamepad.left_trigger }
    val rightTrigger = JoystickWrapper { gamepad.right_trigger }






    val buttons: List<ButtonWrapper> = listOf(
        cross,
        circle,
        square,
        triangle,
        leftStickButton,
        rightStickButton,
        dpadUp,
        dpadDown,
        dpadLeft,
        dpadRight,
        shareButton,
        options,
        psButton,
        leftBumper,
        rightBumper,
        leftTriggerPress,
        rightTriggerPress
    )

    override fun initAuto(hwMap: HardwareMap?) {}

    override fun periodic() {
        buttons.forEach { it.update() }
    }

    override fun shutdown() {}


}