import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class CircleDrawer {
    public void drawCircleWithBezier(Point start, Point end, long durationInMilliseconds, JPanel panel) {
        // Calculate control points for Bezier curve
        Point control1 = new Point(start.x + (end.x - start.x) / 3, start.y);
        Point control2 = new Point(start.x + 2 * (end.x - start.x) / 3, end.y);

        long startTime = System.currentTimeMillis();

        while (true) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= durationInMilliseconds) {
                // End animation when duration is reached
                break;
            }

            float t = (float) elapsedTime / durationInMilliseconds;

            // Calculate Bezier curve point
            int x = (int) ((1 - t) * (1 - t) * (1 - t) * start.x + 3 * (1 - t) * (1 - t) * t * control1.x + 3 * (1 - t) * t * t * control2.x + t * t * t * end.x);
            int y = (int) ((1 - t) * (1 - t) * (1 - t) * start.y + 3 * (1 - t) * (1 - t) * t * control1.y + 3 * (1 - t) * t * t * control2.y + t * t * t * end.y);

            // Draw circle at calculated position
            Graphics g = panel.getGraphics();
            g.setColor(Color.RED);
            g.fillOval(x, y, 10, 10);

            try {
                Thread.sleep(10); // Adjust this value for smoother animation
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
