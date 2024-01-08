package org.firstinspires.ftc.teamcode.Utils;

public class MathUtils {
	public static double applyDeadBand(double input, double deadBand) {
		if (Math.abs(input) < deadBand)
			input = 0;

		return input;
	}
}
