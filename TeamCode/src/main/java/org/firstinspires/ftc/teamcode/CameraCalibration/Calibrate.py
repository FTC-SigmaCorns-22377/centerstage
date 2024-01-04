import cv2
import glob
import numpy as np
import os

board_dims = (6, 9) # dimensions of the chessboard

criteria = (cv2.TERM_CRITERIA_EPS + cv2.TERM_CRITERIA_MAX_ITER, 30, 0.001) # termination criteria

object_points = [] # will store the 3D points of each image
image_points = [] # will store the 2D points of each image

# Defining the coordinates for the 3D "object points"
object_coords = np.zeros((1, board_dims[0] * board_dims[1], 3), np.float32)
object_coords[0, :, :2] = np.mgrid[0:board_dims[0], 0:board_dims[1]].T.reshape(-1, 2)

# Extracting the path of the images
images = glob.glob('C:/Users/<USER>/<PATH e.g. OneDrive/Pictures/Camera Roll/CarlCam720pHD>/*')

for file_name in images:
    image = cv2.imread(file_name)
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    # Finding the corners and whether all four have been found in the image (= ret)
    ret, corners = cv2.findChessboardCorners(gray, board_dims, cv2.CALIB_CB_ADAPTIVE_THRESH +
                                             cv2.CALIB_CB_FAST_CHECK + cv2.CALIB_CB_NORMALIZE_IMAGE)

    if ret == True:
        object_points.append(object_coords)

        # Refining the image coordinates
        corners2 = cv2.cornerSubPix(gray, corners, (11, 11), (-1, -1), criteria)
        image_points.append(corners2)

        # Displaying the corners
        cv2.drawChessboardCorners(image, board_dims, corners2, ret)
        cv2.imshow('image', image)
        cv2.waitKey(0)

cv2.destroyAllWindows()

# height, width = image.shape[:2]

"""
Calibrating by passing the known 3D points (object_points)
and corresponding 2D points of the detected corners (image_points)
"""
ret, cam_matrix, dist_coeffs, rot_vecs, trans_vecs = cv2.calibrateCamera(object_points, image_points,
                                                                         gray.shape[::-1], None, None)

print("Camera Matrix:")
print(cam_matrix)
print("\nDistortion Coefficients:")
print(dist_coeffs)
print("\nRotation Vectors:")
print(rot_vecs)
print("\nTranslation Vectors:")
print(trans_vecs)