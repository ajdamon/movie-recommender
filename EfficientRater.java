
/**
 * Author: Alex Damon
 * Date: 04/22/18
 */

import java.util.*;

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String, Rating> myRatings; //Maps movie IDs to Ratings.

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String, Rating>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        return (myRatings.containsKey(item)) ? true : false;
    }

    public String getID() {
        return myID;
    }
    
    public String toString() {
        return myID + ": " + myRatings.toString();
    }

    public double getRating(String item) {
        return (myRatings.get(item) != null) ? myRatings.get(item).getValue() : -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>(myRatings.keySet());
        return list;
    }
}
