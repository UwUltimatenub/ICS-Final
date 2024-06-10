import java.util.ArrayList;

public class VertexFinder {
    
    public static Object findLowestY(ArrayList<CalculatedPoints> CalculatedPoints) {
        if (CalculatedPoints.isEmpty()) {
            return null;
        }
        
        CalculatedPoints lowestYObject = CalculatedPoints.get(0);
        for (CalculatedPoints obj : CalculatedPoints) {
            if (obj.getY() < lowestYObject.getY()) {
                lowestYObject = obj;
            }
        }
        
        return lowestYObject;
    }
}