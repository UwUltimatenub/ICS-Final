import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class BezierCircleCalculator {
    public ArrayList<Point> calculateCirclePositions(Point start, Point end, int numPoints) {
        // Calculate control points for Bezier curve
        Point control1 = new Point(start.x + (end.x - start.x) / 3, start.y);
        Point control2 = new Point(start.x + 2 * (end.x - start.x) / 3, end.y);

        ArrayList<Point> positions = new ArrayList<>();

        for (int i = 0; i <= numPoints; i++) {
            float t = (float) i / numPoints;

            // Calculate Bezier curve point
            int x = (int) ((1 - t) * (1 - t) * (1 - t) * start.x + 3 * (1 - t) * (1 - t) * t * control1.x + 3 * (1 - t) * t * t * control2.x + t * t * t * end.x);
            int y = (int) ((1 - t) * (1 - t) * (1 - t) * start.y + 3 * (1 - t) * (1 - t) * t * control1.y + 3 * (1 - t) * t * t * control2.y + t * t * t * end.y);

            // Add position to the list
            positions.add(new Point(x, y));
        }

        return positions;
    }
}
