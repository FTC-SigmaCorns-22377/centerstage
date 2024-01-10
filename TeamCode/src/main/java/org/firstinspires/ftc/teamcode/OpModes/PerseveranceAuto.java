package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandFramework.BaseAuto;
import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.RunDrivetrainAction;

import java.util.ArrayList;
import java.util.List;

@Autonomous
public class PerseveranceAuto extends BaseAuto {

    // Robot Parameters -- TUNE
    final Double robotLength = 17.008;
    final Double robotWidth = 14.358;
    final Double distToPixelSlot = (8.5 + 10 + 9.75) / 3;
    final Double distToRollerTip = 18.5;
    final Double distToBackdropBase = 1.5;

    // Calculated Field Parameters
    Double startY = 71.25 - 0.5 * robotLength;
    final Double intakeX = -69.75 + distToRollerTip;
    final Double outtakeX = 60 - distToBackdropBase - 0.5 * robotLength;

    @Override
    public Command setupAuto(CommandScheduler scheduler) {
        switch (getTeam()) {
            case BLUE:
                Vector2d centerStack = new Vector2d(intakeX, 23.75);

                List<Vector2d> backdropFlats = new ArrayList<Vector2d>();
                for (int i = 5; i >= -5; --i) {
                    backdropFlats.add(new Vector2d(outtakeX, 35.625 + 1.5 * i));
                }

                List<Vector2d> backdropSlants = new ArrayList<Vector2d>();
                for (int i = 5; i >= -6; --i) {
                    backdropSlants.add(new Vector2d(outtakeX, 36.375 + 1.5 * i));
                }

                switch (getSide()) {
                    case FRONT:
                        switch (randomization) {
                            case "LEFT": // 23.81 w/o Outtakes
                                Pose2d startPose = new Pose2d(23.25 - 0.5 * robotWidth, startY, -0.5 * Math.PI);
                                Pose2d spikeMark = new Pose2d(22.75 + distToPixelSlot, 35.75, Math.PI);
                                Vector2d intakeJunction = new Vector2d(-29.688, 11.875); // TUNE
                                Vector2d outtakeJunction = new Vector2d(23.75, 11.875); // TUNE
                                return new RunDrivetrainAction(robot.drivetrain,
                                        robot.drivetrain.drive.actionBuilder(startPose)
                                                .splineToLinearHeading(spikeMark, -0.5 * Math.PI) // SCORED PURPLE PIXEL
                                                // SET ARM HEIGHT = ROW 0, WRIST THETA = 0
                                                .setTangent(0)
                                                .splineToConstantHeading(backdropFlats.get(1), 0)
                                                // OPEN LEFT CLAW // SCORED YELLOW PIXEL

                                                .setTangent(Math.PI)
                                                .splineToConstantHeading(outtakeJunction, Math.PI)
                                                .lineToX(intakeX)
                                                // INTAKE TWO WHITE PIXELS
                                                // SET ARM HEIGHT = ROW 1, WRIST THETA = 0
                                                .lineToX(outtakeJunction.x)
                                                .splineToConstantHeading(backdropFlats.get(5), 0)
                                                // OPEN BOTH CLAWS // SCORED TWO WHITE PIXELS

                                                .setTangent(Math.PI)
                                                .splineToConstantHeading(outtakeJunction, Math.PI)
                                                .lineToX(intakeX)
                                                // INTAKE TWO WHITE PIXELS
                                                // SET ARM HEIGHT = ROW 0.5, WRIST THETA = 120
                                                .lineToX(outtakeJunction.x)
                                                .splineToConstantHeading(backdropSlants.get(8), 0)
                                                // OPEN BOTH CLAWS // SCORED TWO WHITE PIXELS

                                                .setTangent(Math.PI)
                                                .splineToConstantHeading(outtakeJunction, Math.PI)
                                                .lineToX(intakeJunction.x)
                                                .splineToConstantHeading(centerStack, Math.PI)
                                                // INTAKE TWO WHITE PIXELS
                                                // SET ARM HEIGHT = ROW 0.5, WRIST THETA = 60
                                                .setTangent(0)
                                                .splineToConstantHeading(intakeJunction, 0)
                                                .lineToX(outtakeJunction.x)
                                                .splineToConstantHeading(backdropSlants.get(3), 0)
                                                // OPEN BOTH CLAWS // SCORED TWO WHITE PIXELS

                                                .build()
                                );
                            case "CENTER": // 22.31s w/o Outtakes
                                startPose = new Pose2d(11.875, startY, -0.5 * Math.PI);
                                spikeMark = new Pose2d(11.875, 24.75 + distToPixelSlot, -0.5 * Math.PI);
                                intakeJunction = new Vector2d(-30.0, 35.625); // TUNE
                                outtakeJunction = new Vector2d(31.25, 35.625); // TUNE
                                return new RunDrivetrainAction(robot.drivetrain,
                                        robot.drivetrain.drive.actionBuilder(startPose)
                                                .lineToY(spikeMark.position.y) // SCORED PURPLE PIXEL
                                                // SET ARM HEIGHT = ROW 0, WRIST THETA = 0
                                                .setTangent(0.5 * Math.PI)
                                                .splineToLinearHeading(new Pose2d(backdropFlats.get(3), Math.PI), 0)
                                                // OPEN RIGHT CLAW // SCORED YELLOW PIXEL

                                                .setTangent(Math.PI)
                                                .splineToConstantHeading(outtakeJunction, Math.PI)
                                                .lineToX(intakeX)
                                                // INTAKE TWO WHITE PIXELS
                                                // SET ARM HEIGHT = ROW 0, WRIST THETA = 0
                                                .lineToX(outtakeJunction.x)
                                                .splineToConstantHeading(backdropFlats.get(9), 0)
                                                // OPEN BOTH CLAWS // SCORED TWO WHITE PIXELS

                                                .setTangent(Math.PI)
                                                .splineToConstantHeading(outtakeJunction, Math.PI)
                                                .lineToX(intakeX)
                                                // INTAKE TWO WHITE PIXELS
                                                // SET ARM HEIGHT = ROW 0.5, WRIST THETA = 60
                                                .lineToX(outtakeJunction.x)
                                                .splineToConstantHeading(backdropSlants.get(3), 0)
                                                // OPEN BOTH CLAWS // SCORED TWO WHITE PIXELS

                                                .setTangent(Math.PI)
                                                .splineToConstantHeading(outtakeJunction, Math.PI)
                                                .lineToX(intakeJunction.x)
                                                .splineToConstantHeading(centerStack, Math.PI)
                                                .setTangent(0)
                                                .splineToConstantHeading(intakeJunction, 0)
                                                .lineToX(outtakeJunction.x)
                                                .splineToConstantHeading(backdropSlants.get(0), 0)

                                                .build()
                                );
                            case "RIGHT": // 24.33s w/o Outtakes
                                startPose = new Pose2d(16.5, startY, -0.5 * Math.PI);
                                spikeMark = new Pose2d(1 + distToPixelSlot, 30.25, Math.PI);
                                intakeJunction = new Vector2d(-29.688, 11.875); // TUNE
                                outtakeJunction = new Vector2d(23.75, 11.875); // TUNE
                                return new RunDrivetrainAction(robot.drivetrain,
                                        robot.drivetrain.drive.actionBuilder(startPose)
                                                .lineToYLinearHeading(spikeMark.position.y, -0.5 * Math.PI) // SCORED PURPLE PIXEL
                                                // SET ARM HEIGHT = ROW 0, WRIST THETA = 0
                                                .lineToX(backdropFlats.get(9).x)
                                                // OPEN RIGHT CLAW // SCORED YELLOW PIXEL

                                                .setTangent(Math.PI)
                                                .splineToConstantHeading(outtakeJunction, Math.PI)
                                                .lineToX(intakeX)
                                                // INTAKE TWO WHITE PIXELS
                                                // SET ARM HEIGHT = ROW 1, WRIST THETA = 0
                                                .lineToX(outtakeJunction.x)
                                                .splineToConstantHeading(backdropFlats.get(5), 0)
                                                // OPEN BOTH CLAWS // SCORED TWO WHITE PIXELS

                                                .setTangent(Math.PI)
                                                .splineToConstantHeading(outtakeJunction, Math.PI)
                                                .lineToX(intakeX)
                                                // INTAKE TWO WHITE PIXELS
                                                // SET ARM HEIGHT = ROW 0.5, WRIST THETA = 60
                                                .lineToX(outtakeJunction.x)
                                                .splineToConstantHeading(backdropSlants.get(3), 0)
                                                // OPEN BOTH CLAWS // SCORED TWO WHITE PIXELS

                                                .setTangent(Math.PI)
                                                .splineToConstantHeading(outtakeJunction, Math.PI)
                                                .lineToX(intakeJunction.x)
                                                .splineToConstantHeading(centerStack, Math.PI)
                                                // INTAKE TWO WHITE PIXELS
                                                // SET ARM HEIGHT = ROW 0.5, WRIST THETA = 120
                                                .setTangent(0)
                                                .splineToConstantHeading(intakeJunction, 0)
                                                .lineToX(outtakeJunction.x)
                                                .splineToConstantHeading(backdropSlants.get(8), 0)
                                                // OPEN BOTH CLAWS // SCORED TWO WHITE PIXELS

                                                .build()
                                );
                        }
                }
            case RED:
                startY *= -1;
        }
        return null;
    }
}
