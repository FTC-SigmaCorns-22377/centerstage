Many thanks to our wonderful mentor Carl Ryden for his guidance with this repo.

The following articles detail the purpose and theory and camera calibration:
- ["AprilTag Camera Calibration," by FTC Docs](https://ftc-docs.firstinspires.org/en/latest/apriltag/vision_portal/apriltag_camera_calibration/apriltag-camera-calibration.html)
- ["Camera Calibration for _FIRST_ Tech Challenge," by FTC Docs](https://ftc-docs.firstinspires.org/en/latest/programming_resources/vision/camera_calibration/camera-calibration.html)
- ["Camera Calibration," by OpenCV Docs](https://docs.opencv.org/4.x/dc/dbb/tutorial_py_calibration.html)

"That's great and all, but how to calibrate my camera in practice?" you ask.
1. Print out 'Chessboard.png' and press it onto a movable hard surface (e.g. book, clipboard).
2. Take various pictures of the chessboard at different angles with your camera and specified resolution.
3. Upload the pictures to an appropriately titled folder (e.g. 'CarlCam720p').
4. Appropriately modify line 18 of 'Calibrate.py' and run the program.
5. (Optional) Appropriately modify lines 5 to 7 and 10 to 11 of 'FieldofView.py' and run it.
6. Substitute all necessary values (likely cam_matrix and dist_coeffs only) into your program.

Note that even if your codebase is in Java or Kotlin (it should be!), you can still calibrate your
camera with Python, because camera calibration is completely independent from the rest of the code.
As mentioned above, all you need out of camera calibration are values (cam_matrix and dist_coeffs),
which can then be integrated into your Java or Kotlin codebase.
Happy calibrating!