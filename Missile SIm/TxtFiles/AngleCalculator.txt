public class AngleCalculator {

    /**
     * This method calculates the angle between the center point (centerX, centerY)
     * and another point (objectX, objectY) in a 2D plane. The angle is calculated
     * using the arctangent function (atan2) which returns the angle in radians.
     *
     * @param centerX the x-coordinate of the center point
     * @param centerY the y-coordinate of the center point
     * @param objectX the x-coordinate of the object point
     * @param objectY the y-coordinate of the object point
     * @return the angle in radians between the two points, measured from the positive x-axis
     */
    public static double calculateAngle(int centerX, int centerY, int objectX, int objectY) {
        // Calculate the angle using the arctangent function, adjusting for the center point
        double angle = Math.atan2(objectY - centerY, objectX - centerX);

        // Return the calculated angle
        return angle;
    }
}
