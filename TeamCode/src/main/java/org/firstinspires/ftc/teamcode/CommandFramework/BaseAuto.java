package org.firstinspires.ftc.teamcode.CommandFramework;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Subsystems.Robot;
import org.firstinspires.ftc.teamcode.Robot.Commands.MiscCommands.Delay;
import org.firstinspires.ftc.teamcode.Robot.Commands.MiscCommands.DelayedCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.MiscCommands.MultipleCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.MiscCommands.RaceAction;
import org.firstinspires.ftc.teamcode.Utils.Team;
import org.firstinspires.ftc.teamcode.Utils.Side;

public abstract class BaseAuto extends LinearOpMode {

    protected Robot robot;

    protected String randomization = "LEFT"; // CALL

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap, Robot.OpMode.Auto, gamepad1, gamepad2, getTeam(), getSide());
        setRobotPosition();

        waitForStart();
        robot.getScheduler().forceCommand(setupAuto(robot.getScheduler()));

        while (opModeIsActive() && !isStopRequested()) {
            robot.update();
        }
        robot.shutdown();

    }

    public abstract Command setupAuto(CommandScheduler scheduler);

    public DelayedCommand delayCommand(double time, Command command) {
        return new DelayedCommand(time, command);
    }

    public MultipleCommand multiCommand(Command... commands) {
        return new MultipleCommand(commands);
    }

    public Delay wait(double seconds) {
        return new Delay(seconds);
    }

    public RaceAction raceCommand(Command... commands) {
        return new RaceAction(commands);
    }

    public void setRobotPosition() {

    }

    public Team getTeam() {
        return Team.NOT_ASSIGNED;
    }

    public Side getSide() {
        return Side.NOT_ASSIGNED;
    }
}
