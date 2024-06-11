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

        double startX = startPoint.getX();
        double startY = startPoint.getY();
        double endX = endPoint.getX();
        double endY = endPoint.getY();

        double a = Math.pow(endY / startY, 1.0 / (endX - startX));
        double b = startY / Math.pow(a, startX);

        double step = (endX - startX) / (numPoints - 1);

        for (int i = 0; i < numPoints; i++) {
            double x = startX + i * step;
            double y = b * Math.pow(a, x);
            curvePoints.add(new Point((int)x, (int)y));
        }

        return curvePoints;
    }
}