import java.util.ArrayList;
import java.util.Random;

public class RandomPoint {
    
    /**
     * This method finds a random position from a list of CalculatedPoints objects
     * that meet specific criteria. The criteria are:
     * - The 'z' value of the object must be greater than half the size of the input list.
     * - The 'y' value of the object must be less than 550.
     *
     * @param CalculatedPoints the list of CalculatedPoints objects to filter and choose from.
     * @return a randomly selected CalculatedPoints object that meets the criteria, or null if none are found.
     */
    public static Object findRandomPos(ArrayList<CalculatedPoints> CalculatedPoints) {
        // Check if the input list is empty, and return null if it is
        if (CalculatedPoints.isEmpty()) {
            return null;
        }
        
        // Create a list to store objects that meet the criteria
        ArrayList<CalculatedPoints> randomPosList = new ArrayList<>();
        
        // Iterate through the input list and add objects that meet the criteria to the new list
        for (CalculatedPoints obj : CalculatedPoints) {
            if (obj.z > CalculatedPoints.size()/2 && obj.y < 550) {
                randomPosList.add(obj);
            }
        }
        
        // Create an instance of Random to generate a random index
        Random rand = new Random();
        
        // Return a randomly selected object from the list of objects that meet the criteria
        return randomPosList.get(rand.nextInt(randomPosList.size()));
    }
}
