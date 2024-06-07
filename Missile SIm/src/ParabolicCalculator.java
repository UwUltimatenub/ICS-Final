import java.util.ArrayList;

public class ParabolicCalculator {
    private static final int GRID_SIZE = 1200;

    public static ArrayList<Point> calculateParabolaPoints(int x1, int y1, int x2, int y2, int x3, int y3) {
        ArrayList<Point> points = new ArrayList<>();

        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        Point p3 = new Point(x3, y3);

        double a, b, c;

        double[][] matrix = {
            {p1.x * p1.x, p1.x, 1},
            {p2.x * p2.x, p2.x, 1},
            {p3.x * p3.x, p3.x, 1}
        };

        double det = determinant(matrix);

        double[][] matrixA = {
            {p1.y, p1.x, 1},
            {p2.y, p2.x, 1},
            {p3.y, p3.x, 1}
        };

        double[][] matrixB = {
            {p1.x * p1.x, p1.y, 1},
            {p2.x * p2.x, p2.y, 1},
            {p3.x * p3.x, p3.y, 1}
        };

        double[][] matrixC = {
            {p1.x * p1.x, p1.x, p1.y},
            {p2.x * p2.x, p2.x, p2.y},
            {p3.x * p3.x, p3.x, p3.y}
        };

        double detA = determinant(matrixA);
        double detB = determinant(matrixB);
        double detC = determinant(matrixC);

        a = detA / det;
        b = detB / det;
        c = detC / det;

        for (int x = 0; x < GRID_SIZE; x++) {
            int y = (int) (a * x * x + b * x + c);
            if (y >= 0 && y <= GRID_SIZE) {
                points.add(new Point(x, y));
            }
        }

        return points;
    }

    private static double determinant(double[][] matrix) {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
             - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
             + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
    }
}
