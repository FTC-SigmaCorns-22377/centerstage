package org.firstinspires.ftc.teamcode.robot.subsystems

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.framework.CommandScheduler

class BaseRobot(hwMap: HardwareMap, opMode: OpMode, gamepad1: Gamepad, gamepad2: Gamepad) {
    val gamepad1: Input
    val gamepad2: Input
    val modules = ArrayList<LynxModule>()
    val scheduler: CommandScheduler

    init {
        scheduler = CommandScheduler(hwMap)
        this.gamepad1 = Input(gamepad1, scheduler)
        this.gamepad2 = Input(gamepad2, scheduler)
        if (opMode == OpMode.Auto) {
            scheduler.initAuto()
        } else if (opMode == OpMode.Teleop) {
            scheduler.initTeleop()
        }
        for (module in hwMap.getAll(LynxModule::class.java)) {
            module.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL
            modules.add(module)
        }
    }

    fun update() {
        for (module in modules) {
            module.clearBulkCache()
        }
        updateInput()
        scheduler.run()
    }

    fun shutdown() {
        scheduler.shutdown()
    }

    private fun updateInput() {
        gamepad1.periodic()
        gamepad2.periodic()
    }

    enum class OpMode {
        Auto, Teleop
    }
}