package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.VisionMultiPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.video.KalmanFilter;

import java.util.List;
import com.ThermalEquilibrium.homeostasis.Filters.FilterAlgorithms;



/* Copyright (c) 2023 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */



/*
 * This OpMode illustrates the basics of AprilTag recognition and pose estimation, using
 * two webcams.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@TeleOp(name = "Concept: AprilTag Switchable Cameras", group = "Concept")
@Disabled
public class ConceptAprilTagSwitchableCameras extends LinearOpMode implements Localizer{

    /*
     * Variables used for switching cameras.
     */
    private WebcamName webcam1, webcam2;
    private boolean oldLeftBumper;
    private boolean oldRightBumper;

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag1;
    private AprilTagProcessor aprilTag2;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    private VisionPortal visionPortal1;
    private VisionPortal visionPortal2;

    //private Multi

    @Override
    public void runOpMode() {

        initAprilTag();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //checks which camera to use
                doCameraSwitching();

                //reports which camera is in use
                telemetryCameraSwitching();

                //reports data from AprilTags
                telemetryAprilTag();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end runOpMode()

    /**
     * Initialize the AprilTag processor.
     */
    private void initAprilTag() {
        double fx;
        double fy;
        double cx;
        double cy;
        // Create the AprilTag processor by using a builder.
        if (visionPortal1.getActiveCamera().equals(webcam1)){
            fx = 1.43024281e+03;
            fy = 1.42898689e+03;
            cx = 9.60603975e+02;
            cy = 5.60364362e+02;
        }
        else{
            fx = 1.46031356e+03;
            fy = 1.45004035e+03;
            cx = 9.93407252e+02;
            cy = 5.34287088e+02;
        }
        aprilTag1 = new AprilTagProcessor.Builder()
                .setLensIntrinsics(1.43024281e+03,1.42898689e+03,9.60603975e+02,5.60364362e+02)
                .build();

        aprilTag2 = new AprilTagProcessor.Builder()
                .setLensIntrinsics(1.46031356e+03,1.45004035e+03,9.93407252e+02,5.34287088e+02)
                .build();

        webcam1 = hardwareMap.get(WebcamName.class, "Webcam 1");
        webcam2 = hardwareMap.get(WebcamName.class, "Webcam 2");
        CameraName switchableCamera = ClassFactory.getInstance()
                .getCameraManager().nameForSwitchableCamera(webcam1, webcam2);


        // Create the vision portal by using a builder.
        visionPortal1 = new VisionPortal.Builder()
                .setCamera(switchableCamera)
                .addProcessor(aprilTag1)
                .build();

        visionPortal2 = new VisionPortal.Builder()
                .setCamera(switchableCamera)
                .addProcessor(aprilTag2)
                .build();
    }   // end method initAprilTag()

    /**
     * Set the active camera according to input from the gamepad.
     */
    //TODO: switch camera based which camera is closer to the april tag. find angle of robot from imu and figure out which april tag is closer to which camera
    private void doCameraSwitching() {
        //if camera1 is closer to AprilTag
        if (1<2) {
            visionPortal = visionPortal1;
        }
        //if camera2 is closer to AprilTag
        else if (2<1) {
            visionPortal = visionPortal2;
            visionPortal.setActiveCamera(webcam2);
        }

    }   // end method doCameraSwitching()

    /**
     * Add telemetry about camera switching.
     */
    private void telemetryCameraSwitching() {
        if (visionPortal.getActiveCamera().equals(webcam1)) {
            telemetry.addData("activeCamera", "Webcam 1");
            //telemetry.addData("Press RightBumper", "to switch to Webcam 2");
        } else {
            telemetry.addData("activeCamera", "Webcam 2");
            //telemetry.addData("Press LeftBumper", "to switch to Webcam 1");
        }

    }   // end method telemetryCameraSwitching()

    /**
     * Add telemetry about AprilTag detections.
     * reads in data from aprilTag and outputs to telemetry
     */
    private void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections;
        if (visionPortal.getActiveCamera().equals(webcam1)){
            currentDetections = aprilTag1.getDetections();
        }
        else{
            currentDetections = aprilTag2.getDetections();
        }

        telemetry.addData("# AprilTags Detected", currentDetections.size());


        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                Double range = detection.ftcPose.range;
                Double bearing = detection.ftcPose.bearing;
                Double yaw = detection.ftcPose.yaw;
                Double x = detection.ftcPose.x;
                Double y = detection.ftcPose.y;

            }
            else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");




    }   // end method telemetryAprilTag()

    //TODO: Use kalman filter to combine drive encoder estimates with aprilTag estimates

    /**
     * use rotation matrix to find x and y coordinates from aprilTag readings
     * @param range
     * @param bearing
     * @param yaw
     * @param x
     * @param y
     */
    private void findGlobalPosition(Double range, Double bearing, Double yaw, Double x, Double y) {
        //create 2x2 rotation matrix for camera's x and y to align with aprilTags x and y
        Mat rotationMatrix = new Mat(2, 2,CvType.CV_64F, new Scalar(0));
        rotationMatrix.put(0, 0, Math.cos(yaw));
        rotationMatrix.put(0, 1, Math.sin(yaw));
        rotationMatrix.put(1, 0, -Math.sin(yaw));
        rotationMatrix.put(1, 1, Math.cos(yaw));

        //matrix with x and y with respect to camera's perspective
        Mat position = new Mat(2, 1,CvType.CV_64F, new Scalar(0));
        rotationMatrix.put(0, 0, x);
        rotationMatrix.put(1, 0, y);

        //matrix multiplication
        Core.gemm(rotationMatrix, position,1, new Mat(), 0, new Mat(), 0);

        //fuse imu position estimates with aprilTag's
        KalmanFilter kalmanFilter = new KalmanFilter(1,1,1);
        kalmanFilter.estimate();

//        Double[][] rotationMatrix = new Double[2][2];
//        rotationMatrix[0][0] = Math.cos(yaw);
//        rotationMatrix[0][1] = Math.sin(yaw);
//        rotationMatrix[1][0] = -Math.sin(yaw);
//        rotationMatrix[1][1] = Math.cos(yaw);
//
//        Double[][] position = new Double[1][1];
//
//        position = rotationMatrix*position;
    }

    //TODO: Use the road runner localizer to find last known position to determine which camera to use
    private void whichCamera(){
        //set limits for heading
        //create mecanum drive
        //get heading
        //figure out which camera to use
    }


    @Override
    public Twist2dDual<Time> update() {
        return null;
    }
}   // end class
