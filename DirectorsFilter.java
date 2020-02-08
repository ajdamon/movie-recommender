
/**
 * Author: Alex Damon
 * Date: 04/22/18
 */

import java.util.Arrays;
import java.util.HashSet;
public class DirectorsFilter implements Filter {
    private String[] directors;
    
    public DirectorsFilter(String dir) {
        directors = dir.split(",");
    }
    
    @Override
    public boolean satisfies(String id) {
        String dirsOfMovie = MovieDatabase.getDirector(id);
        for(String d : directors) {
            if(dirsOfMovie.contains(d)) {
                return true;
            }
        }
        return false;
    }
}
