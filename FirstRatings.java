
/**
 * Author: Alex Damon
 * Date: 04/22/18
 */

import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;
public class FirstRatings {
    /**
     * This method processes every record from the CSV file whose name is filename, 
     * a file of movie information, and returns an ArrayList of type Movie with all of the
     * movie data from the file.
     */
    public ArrayList<Movie> loadMovies(String filename) {
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Movie> movieData = new ArrayList<Movie>();
        try {
            for(CSVRecord csvr : parser.getRecords()) {
                //Making a temp movie variable to make a Movie Object with data in csvr, this will be added to movieData
                Movie temp = new Movie(csvr.get("id"), csvr.get("title"), csvr.get("year"), csvr.get("genre"), 
                    csvr.get("director"), csvr.get("country"), csvr.get("poster"), Integer.parseInt(csvr.get("minutes").trim()));
                //Adding temp to movieData
                movieData.add(temp); 
            }
        } catch(Exception ioe) {
            System.out.println("IOException caught");
        }
        
        //Returning an ArrayList of type Movie with data processed from file [filename]
        return movieData;
    }
    
    /**
     * Call the method loadMovies on the file ratedmovies_short.csv and store the result in an ArrayList local variable . 
     * Print the number of movies, and print each movie. You should see there are five movies in this file, which are all shown 
     * above. After this works you should comment out the printing of the movies. If you run your program on the file
     * ratedmoviesfull.csv, you should see there are 3143 movies in the file.
     * Add code to determine how many movies include the Comedy genre. In the file ratedmovies_short.csv, there is only one.
     * Add code to determine how many movies are greater than 150 minutes in length. In the file ratedmovies_short.csv, there are two.
     * Add code to determine the maximum number of movies by any director, and who the directors are that directed that many movies. Remember
     * that some movies may have more than one director. In the file ratedmovies_short.csv the maximum number of movies by any director is 
     * one, and there are five directors that directed one such movie.
     */
    public void testLoadMovies() {
        ArrayList<Movie> mData = loadMovies("data/ratedmoviesfull.csv");

        System.out.println("Finished Processing \"ratedmovies_short.csv\".\n" + mData.size() + " movies found in the file. They are:\n" );
        for(Movie m : mData) {
            System.out.println(m);
        }
        int comedyMovies = 0, g150l = 0;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int largestNumMovies = 0;
        for(Movie m : mData) {
            if(m.getGenres().contains("Comedy")) {
                comedyMovies ++;
            }
            if(m.getMinutes() > 150) {
                g150l ++;
            }
            String[] directors = (m.getDirector().trim() + ",").split(",");
            for(String dir : directors) {
                if(map.containsKey(dir)) {
                    map.put(dir, map.get(dir) + 1);
                } else {
                    map.put(dir, 1);
                }
            }
        }
        System.out.println(comedyMovies + " comedy movies found");
        System.out.println(g150l + " movies are > 150 minutes in length");
        HashSet<String> directorsWithMaxMovies = new HashSet<String>();
        for(String key : map.keySet()) {
            if(map.get(key) > largestNumMovies) {
                largestNumMovies = map.get(key);
                directorsWithMaxMovies.clear();
                directorsWithMaxMovies.add(key);
            } else if (map.get(key) == largestNumMovies) {
                directorsWithMaxMovies.add(key);
            }
        }
        System.out.println("Max Number of Movies By Any Director Is: " + largestNumMovies); 
        System.out.println(directorsWithMaxMovies.size() + " directors have directed " + largestNumMovies + ". They are: ");
        for(String dir : directorsWithMaxMovies) {
            System.out.println(dir);
        }
        
        
        System.out.println("---------------------------------------------------");
        mData = loadMovies("data/ratedmoviesfull.csv");
        System.out.println("Finished Processing \"ratedmoviesfull.csv\".\n" + mData.size() + " movies found in the file.");
    }
    
    private int indexOf(ArrayList<Rater> arr, String raterId) {
        for(int i  = 0; i < arr.size(); i ++) {
            if(arr.get(i).getID().equals(raterId))
                return i;
        }
        return -1;
    }
    
    /**
     * In the FirstRatings class, write a method named loadRaters that has one parameter named filename. This method should process every record from the
     * CSV file whose name is filename, a file of raters and their ratings, and return an ArrayList of type Rater with all the rater data from the file.
     */
    public ArrayList<Rater> loadRaters(String filename) {
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Rater> raterData = new ArrayList<Rater>();
        try {
            for(CSVRecord csvr : parser.getRecords()) {
                //Making a temp movie variable to make a Movie Object with data in csvr, this will be added to movieData
                String id = csvr.get("rater_id");
                int index = indexOf(raterData, id);
                if(index == -1) {
                    Rater temp = new EfficientRater(id);
                    temp.addRating(csvr.get("movie_id"), Double.parseDouble(csvr.get("rating")));
                    raterData.add(temp);
                } else {
                    raterData.get(index).addRating(csvr.get("movie_id"), Double.parseDouble(csvr.get("rating")));
                }
            }
        } catch(Exception ioe) {
            System.out.println("IOException caught");
        }
        
        //Returning an ArrayList of type Movie with data processed from file [filename]
        return raterData;
    }
    
    /**
     * Call the method loadRaters on the file ratings_short.csv and store the result in a local ArrayList variable. 
     * Print the total number of raters. Then for each rater, print the rater�s ID and the number of ratings they did on one 
     * line, followed by each rating (both the movie ID and the rating given) on a separate line. If you run your program on the
     * file ratings_short.csv you will see there are five raters. After it looks like it works, you may want to comment out the printing 
     * of each rater and their ratings. If you run your program on the file ratings.csv, you should get 1048 raters.

     * Add code to find the number of ratings for a particular rater you specify in your code. For example, if you run this code on the rater
     * whose rater_id is 2 for the file ratings_short.csv, you will see they have three ratings.

     * Add code to find the maximum number of ratings by any rater. Determine how many raters have this maximum number of ratings and who those raters are.
     * If you run this code on the file ratings_short.csv, you will see rater 2 has three ratings, the maximum number of ratings of all the raters, and that
     * there is only one rater with three ratings.

     * Add code to find the number of ratings a particular movie has. If you run this code on the file ratings_short.csv for the movie �1798709�, 
     * you will see it was rated by four raters.

     * Add code to determine how many different movies have been rated by all these raters. If you run this code on the file ratings_short.csv, you will see there were four movies rated.
     */
    public void testLoadRaters() {
        ArrayList<Rater> raterData = loadRaters("data/ratings.csv");
        System.out.println(raterData.size() + " raters found. Their IDs and no. of movies rated are as follows:");
        int max = 0, countRatingsFor1798709 = 0;
        HashSet<Rater> maxRaters = new HashSet<Rater>();
        HashSet<String> moviesRated = new HashSet<String>();
        for(Rater r : raterData) {
            for(String s : r.getItemsRated()) moviesRated.add(s);
            System.out.println(r.getID() + ": " + r.numRatings() +"\n\t"); 
            //System.out.println(r.getItemsRated());
            if(r.hasRating("1798709")) countRatingsFor1798709 ++;
            if(r.numRatings() > max) {
                max = r.numRatings();
                maxRaters.clear();
                maxRaters.add(r);
            } else if (r.numRatings() == max) {
                maxRaters.add(r);
            }
        }
        
        System.out.println("Rater with id 2 has " + raterData.get(indexOf(raterData, "2")).numRatings() + " ratings.");
        System.out.println("Max number of ratings given by one user is " + max +". " + maxRaters.size() + "people gave max ratings");
        for(Rater r : maxRaters) System.out.println(r);
        System.out.println("1798709 has been rated by " + countRatingsFor1798709 + " people.");
        System.out.println(moviesRated.size() + " different movies were rated.");
    }
    
}
