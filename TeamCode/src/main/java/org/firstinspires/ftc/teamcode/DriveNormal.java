package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class DriveNormal extends LinearOpMode {

    public static double INTAKE_POWER = 0.5;
    public static double TRANSFER_UP = 0.3;
    public static double TRANSFER_DOWN = 1;
//    public static double CLAW_LEFT_OPEN = 0;
//    public static double CLAW_RIGHT_OPEN = 0;
//    public static double CLAW_LEFT_CLOSED = 0;
//    public static double CLAW_RIGHT_CLOSED = 0;
    public static double LINKAGE_INTAKE = 0.93;
    public static double LINKAGE_STOW = 0.8;
//    public static double LINKAGE_TRANSFER = 0.5;
    public static double INTAKE_DROP_POSITION_DOWN = 0.72;
    public static double INTAKE_DROP_POSITION_UP = 0.4;



    private DcMotorEx FR, FL, BL, BR;
    private double FRP, FLP, BLP, BRP;


    private DcMotorEx Intake;

    private Servo Transfer;

//    private Servo clawLeft, clawRight;

    private Servo Linkage;
    private Servo IntakeDropLeft, IntakeDropRight;


    private boolean isTransferUp = false;
    private boolean isIntakeUp = false;
//    private boolean isClawClosed = false;


    private int direction = 1;
    private boolean wasRightTriggerPressedLast = false;
    private boolean wasAPressedLast = false;
    private boolean wasBPressedLast = false;



    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        BR = hardwareMap.get(DcMotorEx.class, "BR");
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");

        Intake = hardwareMap.get(DcMotorEx.class, "Intake");
        Transfer = hardwareMap.get(Servo.class, "Transfer");

//        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
//        clawRight = hardwareMap.get(Servo.class, "clawRight");

        Linkage = hardwareMap.get(Servo.class, "Linkage");

        IntakeDropLeft = hardwareMap.get(Servo.class, "IntakeDropLeft");
        IntakeDropRight = hardwareMap.get(Servo.class, "IntakeDropRight");


        FR.setDirection(DcMotorEx.Direction.FORWARD);
        BR.setDirection(DcMotorEx.Direction.FORWARD);
        double speedDivide = 1;
        BL.setDirection(DcMotorEx.Direction.REVERSE);//switched from BR TO BL
        FL.setDirection(DcMotorEx.Direction.REVERSE);//switched from FR TO FL

        Intake.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

//        Intake.setPower(INTAKE_POWER);



        IntakeDropLeft.setPosition(INTAKE_DROP_POSITION_DOWN);
        IntakeDropRight.setPosition(1-INTAKE_DROP_POSITION_DOWN);

        Linkage.setPosition(LINKAGE_INTAKE);
        sleep(1000);
        Transfer.setPosition(TRANSFER_DOWN);



        while(opModeIsActive()) {
            double y = 0.8*(Math.pow(-gamepad1.left_stick_y,2))*Math.signum(-gamepad1.left_stick_y); //y value is inverted
            double x = -0.8*(Math.pow(gamepad1.left_stick_x, 2))*Math.signum(gamepad1.left_stick_x);
            double rx = gamepad1.right_stick_x;

//            if (gamepad1.left_trigger > 0.1){
//                speedDivide = 4;
//            } else {
//                speedDivide = 2;
//                //speedDivide = 1;
//            }

            if (gamepad1.a && !wasAPressedLast) {
                isTransferUp = !isTransferUp;
                if (isTransferUp) {
                    Linkage.setPosition(LINKAGE_STOW);
                    Transfer.setPosition(TRANSFER_UP);

                    IntakeDropLeft.setPosition(INTAKE_DROP_POSITION_UP);
                    IntakeDropRight.setPosition(1 - INTAKE_DROP_POSITION_UP);

                } else {
                    Transfer.setPosition(TRANSFER_DOWN);
                    sleep(500);
                    Linkage.setPosition(LINKAGE_INTAKE);
                    sleep(500);
                    IntakeDropLeft.setPosition(INTAKE_DROP_POSITION_DOWN);
                    IntakeDropRight.setPosition(1 - INTAKE_DROP_POSITION_DOWN);
                }
            }

            wasAPressedLast = gamepad1.a;
            
            if (gamepad1.right_trigger > 0.1) {
                Intake.setPower(-INTAKE_POWER);
            } else if (gamepad1.left_trigger > 0.1) {
                Intake.setPower(INTAKE_POWER);
            } else {
                Intake.setPower(0);
            }

            FLP = (y + x + rx)/speedDivide;
            FRP = (y - x - rx)/speedDivide;
            BLP = (y - x + rx)/speedDivide;
            BRP = (y + x - rx)/speedDivide;

            double denominator = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(rx),1);
            FLP /= denominator;
            FRP /= denominator;
            BLP /= denominator;
            BRP /= denominator;

            // jank switch here
            FL.setPower(FLP);
            FR.setPower(BRP);
            BL.setPower(BLP);
            BR.setPower(FRP);

            BL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            FL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            BR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            FR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        }
    }

}