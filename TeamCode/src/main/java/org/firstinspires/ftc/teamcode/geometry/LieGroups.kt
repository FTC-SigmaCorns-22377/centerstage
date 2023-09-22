package org.firstinspires.ftc.teamcode.geometry

import org.apache.commons.math3.geometry.euclidean.threed.Rotation
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.RealMatrix
import org.apache.commons.math3.util.FastMath
import java.lang.RuntimeException
import kotlin.math.abs

operator fun Rotation.times(other: Rotation): Rotation = applyTo(other)
operator fun Rotation.times(other: Vector3D): Vector3D = applyTo(other)
fun Rotation.inv() = Rotation(q0, -q1, -q2, -q3, false)
operator fun Vector3D.plus(other: Vector3D): Vector3D = add(other)
operator fun Vector3D.minus(other: Vector3D): Vector3D = subtract(other)

data class SE3(val orientation: Rotation, val position: Vector3D) {
    val matrix: RealMatrix by lazy {
        Array2DRowRealMatrix(4, 4).apply {
            setColumn(3, doubleArrayOf(*position.toArray(), 1.0))
            setSubMatrix(orientation.matrix, 0, 0)
        }
    }

    constructor(matrix: RealMatrix): this(
        Rotation(
            matrix.getSubMatrix(0, 2, 0, 2).data,
            FastMath.ulp(8.0)
        ),
        Vector3D(matrix.getColumn(3).sliceArray(0..<3))
    ) {
        if (matrix.getRow(3)
                .subtract(listOf(0.0, 0.0, 0.0, 1.0))
                .maxOf { abs(it) } > FastMath.ulp(8.0))
            throw RuntimeException("The matrix ${matrix.data.contentDeepToString()} is not an SE3 transformation!")
    }

    operator fun times(other: SE3) = SE3(
        orientation * other.orientation,
        orientation * other.position + position
    )
    operator fun times(other: Vector3D) = orientation * other + position
    operator fun unaryMinus() = orientation.inv().let {
        SE3(it, (it * position).negate())
    }
}