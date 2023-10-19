package org.firstinspires.ftc.teamcode.framework

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.ElapsedTime

abstract class Subsystem {
    var deltaTime = 0.0
    var timer = ElapsedTime()
    abstract fun initAuto(hwMap: HardwareMap?)
    fun initTeleop(hwMap: HardwareMap?) {
        initAuto(hwMap)
    }

    abstract fun periodic()
    abstract fun shutdown()
    fun startTimer() {
        timer.reset()
    }

    fun stopTimer() {
        deltaTime = timer.milliseconds()
        timer.reset()
    }
}