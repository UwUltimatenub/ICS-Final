/**This Class makes an object that stores 3 variables. They include the x, position, 
 * y position and the time/z position in the array 
 */
public class CalculatedPoints {
    public int x;
    public int y;
    public int z;

    public CalculatedPoints(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y +","+z+ ")";
    }

}
