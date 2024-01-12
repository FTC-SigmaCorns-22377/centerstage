package org.firstinspires.ftc.teamcode.Robot.Subsystems.ScoringMechanism;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystem;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Dashboard;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import org.firstinspires.ftc.teamcode.Math.AsymmetricProfile.MotionConstraint;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Dashboard;
import org.firstinspires.ftc.teamcode.Utils.ProfiledPID;

public class VerticalExtension extends Subsystem {
    MainScoringMechanism.MechanismStates state = MainScoringMechanism.MechanismStates.BEGIN;

    DcMotorEx vertical1;
    DcMotorEx vertical2;

    PIDCoefficients coefficients = new PIDCoefficients(0.015,0,0);
    MotionConstraint upConstraint = new MotionConstraint(3500,3500,2000);
    MotionConstraint downConstraint = new MotionConstraint(3500,10000,3000);

    ProfiledPID controller = new ProfiledPID(upConstraint,downConstraint,coefficients);
    public final static double HIGH_POSITION = 880;
    public final static double MID_POSITION = 540;

    public final static double IN_POSITION = 0;


    protected double slideTargetPosition = 0;


    public void commonInit(HardwareMap hwMap) {
        vertical1 = hwMap.get(DcMotorEx.class, "vertical1");
        vertical2 = hwMap.get(DcMotorEx.class, "vertical2");
        // TODO, set direction
        vertical2.setDirection(DcMotorSimple.Direction.REVERSE);
        vertical1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vertical2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    @Override
    public void initAuto(HardwareMap hwMap) {
        commonInit(hwMap);
        vertical1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vertical2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    @Override
    public void initTeleop(HardwareMap hwMap) {
        commonInit(hwMap);
        vertical1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vertical2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void periodic() {

        updatePID();

    }

    @Override
    public void shutdown() {

    }

    protected void updatePID() {
        double measuredPosition = getSlidePosition();
        double power = controller.calculate(slideTargetPosition,measuredPosition);
        vertical1.setPower(power);
        vertical2.setPower(power);
        Dashboard.packet.put("measured slide position",measuredPosition);
        Dashboard.packet.put("target slide position",slideTargetPosition);
        Dashboard.packet.put("slide power",power);
    }

    /**
     * get position of the linear slides
     * @return average encoder position of the slides
     */
    public double getSlidePosition() {
        return (vertical1.getCurrentPosition() + vertical2.getCurrentPosition()) / 2.0;
    }

    public double getSlideTargetPosition() {
        return slideTargetPosition;
    }

    public void updateTargetPosition(double targetpos) {
        this.slideTargetPosition = targetpos;
    }

    public boolean isMovementFinished() {
        return controller.isDone();
    }


}