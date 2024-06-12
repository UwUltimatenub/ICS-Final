import java.util.ArrayList;

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

        // Calculate the increments for x and y coordinates
        deltaX = (endPoint.x - startPoint.x) / (double)numPoints;
        deltaY = (endPoint.y - startPoint.y) / (double)numPoints;

        // Generate points along the linear path
        for (int i = 0; i <= numPoints; i++) {
            double x = startPoint.x + i * deltaX;
            double y = startPoint.y + i * deltaY;
            motionPoints.add(new Point((int)x, (int)y));
        }

        return motionPoints;
    }
}

