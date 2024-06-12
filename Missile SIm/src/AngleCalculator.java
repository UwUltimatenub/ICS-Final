import java.awt.geom.Point2D;

public class AngleCalculator {


    // Function to calculate the angle
    public static double calculateAngle(int centerX, int centerY, int objectX, int objectY) {
        double angle = (Math.atan2(objectY - centerY, objectX - centerX));

        return angle;
    }
}