package org.firstinspires.ftc.teamcode.Robot.Subsystems.ScoringMechanism;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystem;

public class MainScoringMechanism extends Subsystem {

    public VerticalExtension verticalExtension = new VerticalExtension();
    public Arm arm = new Arm();


    MechanismStates state = MechanismStates.BEGIN;

    @Override
    public void initAuto(HardwareMap hwMap) {
        verticalExtension.initAuto(hwMap);
        arm.initAuto(hwMap);
    }

    @Override
    public void initTeleop(HardwareMap hwMap) {
        verticalExtension.initTeleop(hwMap);
        arm.initTeleop(hwMap);
    }

    @Override
    public void periodic() {
        updateMechanisms();
    }

    @Override
    public void shutdown() {

    }

    private void updateMechanisms() {
        verticalExtension.periodic();
        arm.periodic();
    }

    private void updateLogic() {
        // TODO: logic for moving states
    }

    public enum MechanismStates {
        BEGIN,// everything in 18 inches, for match start
        CollectingTeleop,
        CollectingAuto,
        BringingTurretAndExtensionIn, // after in taking cone, turn the turret around and begin moving the horizontal Slides in
        TransferCone, // drop the cone onto th platform
        ReadyToDeposit, // cone is on platform, if necessary, horizontal extension is out of the way
        ReadyToDepositAuto, // same as the previous state but the horizontal slides are going all the way out instead of just partially out
        HIGH,
        LOW,
    }

    public MechanismStates getState() {
        return state;
    }


}