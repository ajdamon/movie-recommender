
/**
 * Author: Alex Damon
 * Date: 04/22/18
 */

import java.util.ArrayList;
import java.util.Random;
public class RecommendationRunner implements Recommender {  
    @Override
    public ArrayList<String> getItemsToRate() {
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<String> items = new ArrayList<String>();
        Random myRandom = new Random();
        for(int i = 0; i < 15; i ++) {
        int index = myRandom.nextInt(movies.size());
        String item = movies.get(index);
        if(items.contains(item)) {
        index = myRandom.nextInt(movies.size());
        item = movies.get(index);
        items.add(item);
	} else {
	    items.add(item);
	}
	}
	return items;
    }
    
    @Override
    public void printRecommendationsFor(String webRaterID) {
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> movies = fr.getSimilarRatingsByFilter(webRaterID, 50, 2, new TrueFilter());
        if(movies.size() == 0) {
            System.out.println("<h2>No Results Found</h2>");
            System.exit(1);
        }
        System.out.println("<h2><i>Recommended Films For You</i></h2>");
        System.out.println("<table>");
        System.out.println("<tr><th></th><th><u>Film</u></th><th><u>Genre(s)</u></th><th><u>Director(s)</u></th><th><u>Year</u></th></tr>");
        for(int i = 0; i < 10; i ++) {   
        System.out.println("<tr>");
        System.out.println("<td><img src=" + MovieDatabase.getPoster(movies.get(i).getItem()) + " width=52 height=52 </td>");
        System.out.println("<td><b>" + MovieDatabase.getTitle(movies.get(i).getItem()) + " </b> </td>");
        System.out.println("<td>" + MovieDatabase.getCountry(movies.get(i).getItem()) + "</td>");
        System.out.println("<td>" + MovieDatabase.getGenres(movies.get(i).getItem()) + "</td>");
        System.out.println("<td>" + MovieDatabase.getYear(movies.get(i).getItem()) + "</td>");
        System.out.println("</tr>");
        }
        System.out.println("</table>");
    }
    
}
