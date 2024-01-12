package org.firstinspires.ftc.teamcode.Robot.Subsystems;


import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.ArrayList;
import java.util.List;
class MotionConstraint {
    private int maxVelocity;
    private int maxAcceleration;
    private int maxJerk;

    public MotionConstraint(int maxVelocity, int maxAcceleration, int maxJerk) {
        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;
        this.maxJerk = maxJerk;
        state = hang.MechanismStates;
        Dashboard.MechanismStates state = MechanismStates;

    }
public static class hang  {
    public static final Dashboard.MechanismStates state = null;

    static {

        ///state = new MechanismStates[];
    }

    public static Dashboard.MechanismStates MechanismStates;

    private static class MechanismStates {
    }
}
    protected static Dashboard.MechanismStates MechanismStates;
    private enum MechanismStates {
        BEGIN,
        /* Add other states as needed */
    }
    public class ProfiledPID {
        public ProfiledPID(MotionConstraint upConstraint, MotionConstraint downConstraint, PIDCoefficients coefficients) {

        }

        public double calculate(double targetPosition, double measuredPosition) {
            return targetPosition;
        }

        public boolean isDone() {
            return false;
        }
        // Implementation details
    }

    protected double targetPosition = 0.0;
    protected Dashboard.MechanismStates state;
    protected HardwareMap hwMap;
    public MecanumDrive drive;
    protected List<Action> runningActions = new ArrayList<>();

    DcMotorEx leftMotor;
    DcMotorEx rightMotor;

    PIDCoefficients coefficients = new PIDCoefficients(0.01,0,0);
    MotionConstraint upConstraint = new MotionConstraint(6000,5000,2000);
    MotionConstraint downConstraint = new MotionConstraint(6000,5000,2000);
    ProfiledPID controller = new ProfiledPID(upConstraint,downConstraint,coefficients);

    
    public void initAuto(HardwareMap hwMap) {
        commonInit(hwMap);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void commonInit(HardwareMap hwMap) {

    }


    public void periodic() {
        updatePID();


        List<Action> newActions;
        newActions = null;
        runningActions = newActions;
    }


    public void initTeleop(HardwareMap hwMap) {
            commonInit(hwMap);
            leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

    public void shutdown() {

    }

    protected void updatePID(){
        double measuredPosition=getSlidePosition();
        double power=controller.calculate(targetPosition,measuredPosition);
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }
    public void setTargetPosition(double targetPosition) {
            this.targetPosition = targetPosition;
            }
    public void runAction(Action action) {
        runningActions.add(
                action
        );
    }

    public double getSlidePosition() {
            return (leftMotor.getCurrentPosition() + rightMotor.getCurrentPosition()) / 2.0;
            }

    public double getSlideTargetPosition() {
            return targetPosition;
            }
    public boolean isMovementFinished() {
            return controller.isDone();
            }
}

