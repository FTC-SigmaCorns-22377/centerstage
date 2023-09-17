package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@TeleOp // mark this class as an op mode
class TankDrive : LinearOpMode() {
    override fun runOpMode() { // execution starts here

        // this copies telemetry to both the standard robot controller telemetry and FtcDashboard
        val telemetry = MultipleTelemetry(super.telemetry, FtcDashboard.getInstance().telemetry)

        // these are specified in the robot's config file
        val motorNames = listOf("FrontLeft", "BackLeft", "BackRight", "FrontRight")

        // initialize a list of each motor with its name
        val motors = motorNames.map { hardwareMap.get(DcMotor::class.java, it) }

        // get lists of the left and right motors
        val leftMotors = motors.subList(0, 2)
        val rightMotors = motors.subList(2, 4)

        // reverse the left motors so positive power is forward
        for (motor in leftMotors)
            motor.direction = DcMotorSimple.Direction.REVERSE

        // tell the driver we are done with initialization and they are good to start
        telemetry.addData("Status", "Initialized")
        telemetry.update()

        // wait for start to be pressed
        waitForStart()

        while (opModeIsActive()) { // will run until the driver hits stop

            // get joystick positions
            val forward = -gamepad1.left_stick_y.toDouble() // up is negative on the controllers
            val turn = gamepad1.right_stick_x.toDouble()

            // calculate motor powers
            val leftPower = forward + turn
            val rightPower = forward - turn

            // set motors to their respective powers
            for (motor in leftMotors) motor.power = leftPower
            for (motor in rightMotors) motor.power = rightPower

            // give the driver some feedback so they know everything is fine
            telemetry.addData("Status", "Running")
            telemetry.update()
        }
    }
}
