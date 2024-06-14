import java.util.ArrayList;
/**VertexFinder findes the vertex by finding the lowest YValue in the Arraylist
 * Calculated Points which contains the path of the flying missiles.
 */
public class VertexFinder {
    
    public static Object findLowestY(ArrayList<CalculatedPoints> CalculatedPoints) {
        if (CalculatedPoints.isEmpty()) {
            return null;
        }
        
        CalculatedPoints lowestYObject = CalculatedPoints.get(0);
        for (CalculatedPoints obj : CalculatedPoints) {
            if (obj.y < lowestYObject.y) {
                lowestYObject = obj;
            }
        }
        
        return lowestYObject;
    }
}