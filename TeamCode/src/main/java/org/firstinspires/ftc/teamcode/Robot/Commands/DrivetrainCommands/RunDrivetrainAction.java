package org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Drivetrain;

public class RunDrivetrainAction extends Command {

    protected final Action action;
    private boolean done;

    public RunDrivetrainAction(Action action) {
        this.action = action;
    }
    @Override
    public void init() {
    }

    @Override
    public void periodic() {
        TelemetryPacket packet = new TelemetryPacket();
        done = !action.run(packet);

    }

    @Override
    public boolean completed() {
        return done;
    }

    @Override
    public void shutdown() {

    }
}
