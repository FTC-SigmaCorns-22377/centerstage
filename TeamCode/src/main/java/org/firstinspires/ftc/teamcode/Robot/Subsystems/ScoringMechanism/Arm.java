package org.firstinspires.ftc.teamcode.Robot.Subsystems.ScoringMechanism;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CommandFramework.Subsystem;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Dashboard;

public class Arm extends Subsystem {
    MainScoringMechanism.MechanismStates state = MainScoringMechanism.MechanismStates.BEGIN;

    Servo armLeft;
    Servo armRight;
    Servo wrist;
    Servo clawLeft;
    Servo clawRight;

    //TODO: Find the actual values needed for beginning state
    double armLeftPosition = 0.34;
    double armRightPosition = 0.34;
    double wristPosition = 0.4;
    double clawLeftPosition = 0.51889;
    double clawRightPosition = 0.51889;

    double currentFreeStateValue = 0;

    @Override
    public void initAuto(HardwareMap hwMap) {
        armLeft = hwMap.get(Servo.class, "armLeft");
        armLeft.setPosition(armLeftPosition);

        armRight = hwMap.get(Servo.class, "armRight");
        //armRight.setDirection(Servo.Direction.REVERSE);
        armRight.setPosition(armRightPosition);

        wrist = hwMap.get(Servo.class, "wrist");
        wrist.setPosition(wristPosition);

        clawLeft = hwMap.get(Servo.class, "clawLeft");
        clawLeft.setPosition(clawLeftPosition);

        clawRight = hwMap.get(Servo.class, "clawRight");
        clawRight.setPosition(clawRightPosition);
    }

    @Override
    public void periodic() {

    }

    @Override
    public void shutdown() {

    }

    //TODO: Find the actual values needed for each state
    public void setArm(ArmStates armStates) {
        switch (armStates) {
            case TRANSFER:
                armLeft.setPosition(0.19);
                armRight.setPosition(0.19);
                break;
            case LOW_SCORING:
                armLeft.setPosition(0.19);
                armRight.setPosition(0.19);
                break;
            case HIGH_SCORING:
                armLeft.setPosition(0.19);
                armRight.setPosition(0.19);
                break;
        }
    }

    //this might not be needed
//    public void setArmDirect(double position) {
//        currentFreeStateValue = position;
//        setArm(ArmStates.FREE_STATE);
//    }


    public void setArmPositionSync(double position) {
        // TODO: Adjust the min and max here to appropriate soft stops
        position = Range.clip(position,-1,1);
        armLeft.setPosition(position);
//		armRight.setPosition(1 - position); uncomment once second servo is connected
    }

    // TODO: Maybe don't average? if we only use 1 servo lol
    public double getArmPosition() {
        return armLeft.getPosition();
    }

    //TODO: Find the actual values needed for each state
    public void setWrist(WristStates wristStates) {
        switch (wristStates) {
            case DEG0:
                wrist.setPosition(0.4);
                break;
            case DEG60:
                wrist.setPosition(0.6);
                break;
            case DEG120:
                wrist.setPosition(0.8);
                break;
            case DEG180:
                wrist.setPosition(1);
                break;
            case DEG240:
                wrist.setPosition(0.2);
                break;
        }
    }

    /**
     * Makes sure wrist position is in range and updates the wrist position to dashboard
     *
     * @param position the desired position of the wrist, ranging from -1 to 1
     */
    public void setWristPositionSync(double position) {
        position = Range.clip(position,-1,1);
        wrist.setPosition(position);
        Dashboard.packet.put("RECIEVES POS", position);
    }


    /**
     * Returns the position of the wrist.
     *
     * @return the position of the wrist
     */
    public double getWristPosition() {
        return wrist.getPosition();
    }

    public void setClawGrabbing(ClawStates clawState) {
        // TODO: Maybe add a different state for normally dropping cone from claw and dropping the cone in the outtake?
        switch (clawState) {
            case OPEN:
                // TODO: tune this values
                clawLeft.setPosition(0.5);
                clawRight.setPosition(0.5);
                break;
            case CLOSED:
                // TODO: tune this values
                clawLeft.setPosition(0);
                clawRight.setPosition(0);
        }
    }

    public void setClawPositionSync(double position) {
        position = Range.clip(position,-1,1);
        clawLeft.setPosition(position);
        clawRight.setPosition(position); //position porbably needs to be updated
    }

    public double getClawPosition() {
        return clawLeft.getPosition();
    }

    public enum ArmStates {
        TRANSFER,
        LOW_SCORING,
        HIGH_SCORING
    }

    public enum WristStates {
        DEG0,
        DEG60,
        DEG120,
        DEG180,
        DEG240
    }

    public enum ClawStates {
        OPEN,
        CLOSED
    }

    public void setCurrentFreeStateValue(double currentFreeStateValue) {
        this.currentFreeStateValue = currentFreeStateValue;
    }
}