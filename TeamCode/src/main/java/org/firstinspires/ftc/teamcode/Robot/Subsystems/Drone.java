package org.firstinspires.ftc.teamcode.Robot.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CommandFramework.Subsystem;


public class Drone extends Subsystem {



        Servo arm;
        Servo wrist;
        Servo claw;

        //TODO: Find the actual values needed for beginning state
        double armPosition = 0.34;
        double wristPosition = 0.4;
        double clawPosition = 0.51889;

        double currentFreeStateValue = 0;

    public Drone() {

    }

        @Override
        public void initAuto(HardwareMap hwMap) {
            arm = hwMap.get(Servo.class, "arm");
            arm.setPosition(armPosition);

            wrist = hwMap.get(Servo.class, "wrist");
            wrist.setPosition(wristPosition);

            claw = hwMap.get(Servo.class, "claw");
            claw.setPosition(clawPosition);
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
                    arm.setPosition(0.19);
                    break;
                case LOW_SCORING:
                    arm.setPosition(0.19);
                    break;
                case HIGH_SCORING:
                    arm.setPosition(0.19);
                    break;
            }
        }

        public void setArmPositionSync(double position) {
            // TODO: Adjust the min and max here to appropriate soft stops
            position = Range.clip(position, -1, 1);
            arm.setPosition(position);
        }

        // TODO: Maybe don't average? if we only use 1 servo lol
        public double getArmPosition() {
            return arm.getPosition();
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

        public void setWristPositionSync(double position) {
            position = Range.clip(position, -1, 1);
            wrist.setPosition(position);
            Dashboard.packet.put("RECIEVES POS", position);
        }

        public double getWristPosition() {
            return wrist.getPosition();
        }

        public void setClawGrabbing(ClawStates clawState) {
            switch (clawState) {
                case OPEN:
                    // TODO: tune this values
                    claw.setPosition(0.5);
                    break;
                case CLOSED:
                    // TODO: tune this values
                    claw.setPosition(0);
                    break;
            }
        }

        public void setClawPositionSync(double position) {
            position = Range.clip(position, -1, 1);
            claw.setPosition(position);
        }

        public double getClawPosition() {
            return claw.getPosition();
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


