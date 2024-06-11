import java.util.ArrayList;

public class CircularCurveCalculator {
    private Point startPoint;
    private Point endPoint;
    private int numPoints;

    public CircularCurveCalculator(Point startPoint, Point endPoint, int numPoints) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.numPoints = numPoints;
    }

    public ArrayList<Point> generateCurve() {
        ArrayList<Point> curvePoints = new ArrayList<>();

        // Calculate the center point of the circle passing through start and end points
        Point center = calculateCenter(startPoint, endPoint);

        // Calculate the radius of the circle
        double radius = calculateRadius(startPoint, endPoint, center);

        // Calculate the start and end angles of the circular arc
        double startAngle = Math.atan2(startPoint.getY() - center.getY(), startPoint.getX() - center.getX());
        double endAngle = Math.atan2(endPoint.getY() - center.getY(), endPoint.getX() - center.getX());

        // Adjust angles to ensure the shortest arc is used
        if (startAngle > endAngle) {
            startAngle -= 2 * Math.PI;
        }

        // Calculate the angle increment between points
        double angleIncrement = (endAngle - startAngle) / numPoints;

        // Generate points along the circular path
        for (int i = 0; i < numPoints; i++) {
            double angle = startAngle + i * angleIncrement;
            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY() + radius * Math.sin(angle);
            curvePoints.add(new Point((int)x, (int)y));
        }

        return curvePoints;
    }

    // Helper method to calculate the center point of the circle
    private Point calculateCenter(Point start, Point end) {
        double centerX = (start.getX() + end.getX()) / 2;
        double centerY = (start.getY() + end.getY()) / 2;
        return new Point((int)centerX, (int)centerY);
    }

    // Helper method to calculate the radius of the circle
    private double calculateRadius(Point start, Point end, Point center) {
        // Use the distance formula to calculate the distance between the center and start or end point
        double radius = Math.sqrt(Math.pow(center.getX() - start.getX(), 2) + Math.pow(center.getY() - start.getY(), 2));
        return radius;
    }
}
