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
