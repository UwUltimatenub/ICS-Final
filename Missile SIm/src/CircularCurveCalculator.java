import java.util.ArrayList;
/**
 * The CircularCurveCalculator class calculates the points along a circular curve 
 * given a start point, an end point, and the number of points to generate along the curve. 
 * It calculates the center and radius of the circle. Using this information, it adds all the 
 * points to an ArrayList called curvePoints.
 * 
 * - generateCurve(): Generates and returns an ArrayList of points along the circular curve.
 *   - It calculates the center of the circle from the start and end points.
 *   - It calculates the radius of the circle from the start point, end point, and center.
 *   - It computes the start and end angles in radians.
 *   - It calculates the angle increment between each point along the curve.
 *   - It iterates through the number of points, calculating each point's coordinates and adding it to the ArrayList.
 */
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

        center = calculateCenter(startPoint, endPoint);

        double radius = calculateRadius(startPoint, endPoint, center);

       
        double startAngle = Math.atan2(startPoint.y - center.y, startPoint.x - center.x);
        double endAngle = Math.atan2(endPoint.y - center.y, endPoint.x - center.x);

        if (startAngle > endAngle) {
            startAngle -= 2 * Math.PI;
        }

       
        double angleIncrement = (endAngle - startAngle) / numPoints;

        for (int i = 0; i < numPoints; i++) {
            double angle = startAngle + i * angleIncrement;
            double x = center.x + radius * Math.cos(angle);
            double y = center.y + radius * Math.sin(angle);
            curvePoints.add(new Point((int)x, (int)y));
        }

        return curvePoints;
    }

    private Point calculateCenter(Point start, Point end) {
        double centerX = (start.x + end.x) / 2;
        double centerY = (start.y + end.y) / 2;
        return new Point((int)centerX, (int)centerY);
    }

    private double calculateRadius(Point start, Point end, Point center) {

        double radius = Math.sqrt(Math.pow(center.x - start.x, 2) + Math.pow(center.y - start.y, 2));
        return radius;
    }
}
