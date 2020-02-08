
/**
 * Author: Alex Damon
 * Date: 04/22/18
 */

import java.util.*;
public class FourthRatings {        
    private double getAverageByID(String id, int minimalRaters) {
        double total = 0.0;
        int count = 0;
        for(Rater r : RaterDatabase.getRaters()) {
            Double rating = r.getRating(id);
            if(rating != -1) {
                total += rating;
                count ++;
            }
        }
        return (count >= minimalRaters) ? total/count : 0.0;
    }
               
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        for(String id : movies) {
            double avg = getAverageByID(id, minimalRaters);
            if(avg == 0.0) continue;
            avgRatings.add(new Rating(id, avg));
        }
        return avgRatings;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for(String id : movies) {
            double avg = getAverageByID(id, minimalRaters);
            if(avg == 0.0) continue;
            avgRatings.add(new Rating(id, avg));
        }
        return avgRatings;
    }
    
    private double dotProduct(Rater me, Rater r) {
        double sim = 0;
        for(String item : r.getItemsRated()) {
            if(me.hasRating(item)) {               
                sim += (me.getRating(item) - 5) * (r.getRating(item) - 5);
            }
        }
        return sim;
    }
    
    private ArrayList<Rating> getSimilarities(String id) {
        Rater me = RaterDatabase.getRater(id);
        ArrayList<Rating> toRet = new ArrayList<Rating>();
        for(Rater r : RaterDatabase.getRaters()) {            
            if(r.equals(me)) {
                continue;
            }        
            double dp = dotProduct(me, r);           
            if(dp < 0) {
                continue;
            }             
            toRet.add(new Rating(r.getID(), dp));
        }        
        Collections.sort(toRet, Collections.reverseOrder());
        return toRet;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> toRet = new ArrayList<Rating>();        
        ArrayList<Rating> raters = getSimilarities(id);       
        for(String movie : MovieDatabase.filterBy(new TrueFilter())) {
            double totalWeightedRating = 0.0;
            int numTopRaters = 0;
            for(int i = 0 ; i < numSimilarRaters ; i ++) {
                Rating raterSimilarityRating = raters.get(i); 
                Rater rater = RaterDatabase.getRater(raterSimilarityRating.getItem());                
                if(RaterDatabase.getRater(id).hasRating(movie)) continue;
                if(rater.hasRating(movie)) {
                    totalWeightedRating += (rater.getRating(movie) ) * raterSimilarityRating.getValue();
                    numTopRaters ++;
                }
            }
            if(numTopRaters >= minimalRaters) {
                toRet.add(new Rating(movie, totalWeightedRating/numTopRaters));
            }
        }
        Collections.sort(toRet, Collections.reverseOrder());
        return toRet;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter f) {
        ArrayList<Rating> toRet = new ArrayList<Rating>();       
        ArrayList<Rating> raters = getSimilarities(id);       
        for(String movie : MovieDatabase.filterBy(f)) {
            double totalWeightedRating = 0.0;
            int numTopRaters = 0;
            for(int i = 0 ; i < numSimilarRaters ; i ++) {
                Rating raterSimilarityRating = raters.get(i); 
                Rater rater = RaterDatabase.getRater(raterSimilarityRating.getItem());                 
                if(RaterDatabase.getRater(id).hasRating(movie)) continue;
                if(rater.hasRating(movie)) {
                    totalWeightedRating += (rater.getRating(movie) ) * raterSimilarityRating.getValue();
                    numTopRaters ++;
                }
            }
            if(numTopRaters >= minimalRaters) {
                toRet.add(new Rating(movie, totalWeightedRating/numTopRaters));
            }
        }
        Collections.sort(toRet, Collections.reverseOrder());
        return toRet;
    }

    public FourthRatings() {
        this("ratings.csv");
    }    
    
    public FourthRatings(String ratingsFile) {
        RaterDatabase.initialize(ratingsFile);
    }

}
