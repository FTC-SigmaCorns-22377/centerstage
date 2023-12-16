import math
import numpy as np

# Setting camera matrix
cam_matrix = np.array([[1.39572057e+03, 0.00000000e+00, 6.38432818e+02],
                       [0.00000000e+00, 1.38988423e+03, 3.66723324e+02],
                       [0.00000000e+00, 0.00000000e+00, 1.00000000e+00]])

# Setting resolution
width = 1280
height = 720

# Extracting focal lengths
fx = cam_matrix[0, 0]
fy = cam_matrix[1, 1]

# Calculating fields of view
horizontal_fov = math.degrees(2 * math.atan(width / (2 * fx)))
vertical_fov = math.degrees(2 * math.atan(height / (2 * fy)))

print("Horizontal Field of View: ", horizontal_fov)
print("\nVertical Field of View: ", vertical_fov)