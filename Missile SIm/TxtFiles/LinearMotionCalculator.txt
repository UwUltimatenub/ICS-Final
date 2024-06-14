import java.util.ArrayList;
/**
 * This class takes the startpoint and endpoint of the linear line and take the number 
 *  of points it will print to reach the end. It calculates the slope, and adds all the points to an arraylist called motionPoints
 */
public class LinearMotionCalculator {
    private Point startPoint, endPoint;
    private int numPoints;
    public double deltaY, deltaX;

    public LinearMotionCalculator(Point startPoint, Point endPoint, int numPoints) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.numPoints = numPoints;
    }

    public ArrayList<Point> generateMotion() {
        ArrayList<Point> motionPoints = new ArrayList<>();
        deltaX = (endPoint.x - startPoint.x) / (double)numPoints;
        deltaY = (endPoint.y - startPoint.y) / (double)numPoints;

        for (int i = 0; i <= numPoints; i++) {
            double x = startPoint.x + i * deltaX;
            double y = startPoint.y + i * deltaY;
            motionPoints.add(new Point((int)x, (int)y));
        }

        return motionPoints;
    }
}

