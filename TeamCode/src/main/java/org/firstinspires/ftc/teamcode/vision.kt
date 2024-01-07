////package org.firstinspires.ftc.teamcode
//
//import org.opencv.core.Core
//import org.opencv.core.CvType
//import org.opencv.core.Mat
//import org.opencv.core.MatOfPoint
//import org.opencv.core.Point
//import org.opencv.core.Scalar
//import org.opencv.imgproc.Imgproc
//
//fun detectBallPosition(imagePath: String): String {
//    // Load the native OpenCV library
//    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
//
//    // Read the image
//    val image = Imgproc.imread(imagePath)
//    val hsvImage = Mat()
//    val mask1 = Mat()
//    val mask2 = Mat()
//
//    // Convert to HSV
//    Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV)
//
//    // Define the range of red color in HSV
//    val lowerRed1 = Scalar(0.0, 120.0, 70.0)
//    val upperRed1 = Scalar(10.0, 255.0, 255.0)
//    val lowerRed2 = Scalar(170.0, 120.0, 70.0)
//    val upperRed2 = Scalar(180.0, 255.0, 255.0)
//
//    // Threshold the HSV image to get only red colors
//    Core.inRange(hsvImage, lowerRed1, upperRed1, mask1)
//    Core.inRange(hsvImage, lowerRed2, upperRed2, mask2)
//
//    // Combine the two masks
//    val redMask = Mat()
//    Core.addWeighted(mask1, 1.0, mask2, 1.0, 0.0, redMask)
//
//    // Find contours
//    val contours = ArrayList<MatOfPoint>()
//    val hierarchy = Mat()
//    Imgproc.findContours(redMask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)
//
//    // Assume the largest contour is the ball
//    val ballContour = contours.maxByOrNull { Imgproc.contourArea(it) } ?: return "No ball detected"
//    // Calculate the center of the contour
//    val moments = Imgproc.moments(ballContour)
//    val centerX = (moments.m10 / moments.m00).toInt()
//    val centerY = (moments.m01 / moments.m00).toInt()
//    val ballCenter = Point(centerX.toDouble(), centerY.toDouble())
//
//    // Determine the position of the ball relative to the image thirds
//    val third = image.width() / 3
//    val ballThirdPosition = when {
//        centerX < third -> "left"
//        centerX > 2 * third -> "right"
//        else -> "center"
//    }
//
//    // Draw the thirds and the ball on the image
//    val divisionImage = image.clone()
//    Imgproc.line(divisionImage, Point(third.toDouble(), 0.0), Point(third.toDouble(), divisionImage.height().toDouble()), Scalar(255.0, 255.0, 255.0), 2)
//    Imgproc.line(divisionImage, Point((2 * third).toDouble(), 0.0), Point((2 * third).toDouble(), divisionImage.height().toDouble()), Scalar(255.0, 255.0, 255.0), 2)
//    Imgproc.circle(divisionImage, ballCenter, 10, Scalar(0.0, 255.0, 0.0), -1)
//    Imgproc.putText(divisionImage, "Ball", ballCenter, Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, Scalar(0.0, 255.0, 0.0), 2)
//
//    // Save or display the image as needed
//    // For example, to save the image:
//    // Imgproc.imwrite("output_path", divisionImage)
//
//    return ballThirdPosition
//}