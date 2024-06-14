import java.util.ArrayList;
/**
 * The ParabolicCalculator class is responsible for calculating the points along a parabolic curve defined by three given points.
 * It uses matrix determinants to solve for the coefficients of the quadratic equation that defines the parabola.
 * 
 * Methods:
 * - calculateParabolaPoints(Point p1, Point p2, Point p3): Calculates the points along the parabola passing through the three given points.
 * - determinant(double[][] matrix): Computes the determinant of a 3x3 matrix.
 * 
 * The calculateParabolaPoints method:
 * - Takes three points (p1, p2, p3) as input.
 * - Constructs and solves three equations to find the coefficients (a, b, c) of the quadratic equation.
 * - Iterates over a range of x-values to compute the corresponding y-values on the parabola.
 * - Stores the computed points in an ArrayList and returns it.
 * 
 * The determinant method:
 * - Takes a 3x3 matrix as input.
 * - Computes and returns the determinant of the matrix.
 * */
public class ParabolicCalculator {
    private static final int GRID_SIZE = 800;
    public static double a, b, c;


    public static ArrayList<CalculatedPoints> calculateParabolaPoints(Point p1,Point p2,Point p3) {


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
        ArrayList<CalculatedPoints> CalculatedPoints = new ArrayList<>();
        int z = 0;

        for (int x = 0; x < GRID_SIZE; x+= 2) {
            int y = (int)(a * x * x + b * x + c); 
            if (y >= 0 && y <= GRID_SIZE) {
                CalculatedPoints.add(new CalculatedPoints(x, y,z));
                z+=1;
            }
        }

        return CalculatedPoints;
    }

    private static double determinant(double[][] matrix) {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
             - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
             + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
    }
    
}
