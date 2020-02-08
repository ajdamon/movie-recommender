
/**
 * Author: Alex Damon
 * Date: 04/22/18
 */

import java.util.*;

public interface Rater {
    public void addRating(String item, double rating);
    public boolean hasRating(String item);
    public String getID();
    public String toString();
    public double getRating(String item);
    public int numRatings();
    public ArrayList<String> getItemsRated();
}
