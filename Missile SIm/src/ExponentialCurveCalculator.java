import java.util.ArrayList;

public class ExponentialCurveCalculator {
    private Point startPoint;
    private Point endPoint;
    private int numPoints;

    public ExponentialCurveCalculator(Point startPoint, Point endPoint, int numPoints) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.numPoints = numPoints;
    }

    public ArrayList<Point> generateCurve() {
        ArrayList<Point> curvePoints = new ArrayList<>();

        // Validate inputs
        if (startPoint == null || endPoint == null || numPoints <= 1) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        double startX = startPoint.getX();
        double startY = startPoint.getY();
        double endX = endPoint.getX();
        double endY = endPoint.getY();

        // Handle edge cases
        if (startX <= 0 || startY <= 0 || endX <= 0 || endY <= 0) {
            throw new IllegalArgumentException("Points must have positive coordinates.");
        }

        double a = Math.pow(endY / startY, 1.0 / (endX - startX));
        double b = startY / Math.pow(a, startX);

        double step = (endX - startX) / (numPoints - 1);

        for (int i = 0; i < numPoints; i++) {
            double x = startX + i * step;
            double y = b * Math.pow(a, x);
            curvePoints.add(new Point((int) x, (int) y));
        }

        return curvePoints;
    }
}
