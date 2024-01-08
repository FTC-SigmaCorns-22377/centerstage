package org.firstinspires.ftc.teamcode.Robot.Subsystems;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TimeTrajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CommandFramework.Subsystem;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.ArrayList;
import java.util.List;

public class Drivetrain extends Subsystem {
	protected HardwareMap hwMap;
	public MecanumDrive drive;
	protected List<Action> runningActions = new ArrayList<>();


	@Override
	public void initAuto(HardwareMap hwMap) {
		this.hwMap = hwMap;
		drive = new MecanumDrive(hwMap, new Pose2d(0, 0, 0));
	}

	@Override
	public void periodic() {
		TelemetryPacket packet = new TelemetryPacket();
		List<Action> newActions = new ArrayList<>();
		for (Action action : runningActions) {
			action.preview(packet.fieldOverlay());
			if (action.run(packet)) {
				newActions.add(action);
			}
		}
		runningActions = newActions;
	}



	@Override
	public void shutdown() {
		drive.leftFront.setPower(0);
		drive.leftBack.setPower(0);
		drive.rightFront.setPower(0);
		drive.rightBack.setPower(0);
	}



	public void runAction(Action action) {
		runningActions.add(
				action
		);
	}


}
