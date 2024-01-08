package org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Drivetrain;

public class RunDrivetrainAction extends Command {

    protected final Drivetrain drivetrain;
    protected final Action action;

    public RunDrivetrainAction(Drivetrain drivetrain, Action action) {
        this.drivetrain = drivetrain;
        this.action = action;
    }
    @Override
    public void init() {
        drivetrain.runAction(action);

    }

    @Override
    public void periodic() {

    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public void shutdown() {

    }
}
