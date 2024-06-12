import java.util.ArrayList;
import java.util.Random;

public class RandomPoint {
    
    public static Object findRandomPos(ArrayList<CalculatedPoints> CalculatedPoints) {
        if (CalculatedPoints.isEmpty()) {
            return null;
        }
        ArrayList<CalculatedPoints> randomPosList = new ArrayList<>();
        for (CalculatedPoints obj : CalculatedPoints) {
            if (obj.z > CalculatedPoints.size()/2 && obj.y < 550) {
                randomPosList.add(obj);
            }
        }
        Random rand = new Random();
        return randomPosList.get(rand.nextInt(randomPosList.size()));
    }
}