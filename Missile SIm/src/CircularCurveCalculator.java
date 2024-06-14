import java.util.ArrayList;

public class CircularCurveCalculator {


    private int numPoints;
    public Point center, startPoint, endPoint;

    public CircularCurveCalculator(Point startPoint, Point endPoint, int numPoints) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.numPoints = numPoints;
    }

    public ArrayList<Point> generateCurve() {
        ArrayList<Point> curvePoints = new ArrayList<>();

        // Calculate the center point of the circle passing through start and end points
        center = calculateCenter(startPoint, endPoint);

        // Calculate the radius of the circle
        double radius = calculateRadius(startPoint, endPoint, center);

        // Calculate the start and end angles of the circular arc
        double startAngle = Math.atan2(startPoint.y - center.y, startPoint.x - center.x);
        double endAngle = Math.atan2(endPoint.y - center.y, endPoint.x - center.x);

        // Adjust angles to ensure the shortest arc is used
        if (startAngle > endAngle) {
            startAngle -= 2 * Math.PI;
        }

        // Calculate the angle increment between points
        double angleIncrement = (endAngle - startAngle) / numPoints;

        // Generate points along the circular path
        for (int i = 0; i < numPoints; i++) {
            double angle = startAngle + i * angleIncrement;
            double x = center.x + radius * Math.cos(angle);
            double y = center.y + radius * Math.sin(angle);
            curvePoints.add(new Point((int)x, (int)y));
        }

        return curvePoints;
    }

    // Helper method to calculate the center point of the circle
    private Point calculateCenter(Point start, Point end) {
        double centerX = (start.x + end.x) / 2;
        double centerY = (start.y + end.y) / 2;
        return new Point((int)centerX, (int)centerY);
    }

    // Helper method to calculate the radius of the circle
    private double calculateRadius(Point start, Point end, Point center) {
        // Use the distance formula to calculate the distance between the center and start or end point
        double radius = Math.sqrt(Math.pow(center.x - start.x, 2) + Math.pow(center.y - start.y, 2));
        return radius;
    }
}
