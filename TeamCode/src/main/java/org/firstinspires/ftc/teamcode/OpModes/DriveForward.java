package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandFramework.BaseAuto;
import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.RunDrivetrainAction;

@Autonomous
public class DriveForward extends BaseAuto {

    @Override
    public Command setupAuto(CommandScheduler scheduler) {
        return new RunDrivetrainAction(
                robot.drivetrain.drive.actionBuilder(
                        new Pose2d(0,0,0))
                        .splineTo(new Vector2d(-50, 20), 0)
                        .build()
        );
    }
}
