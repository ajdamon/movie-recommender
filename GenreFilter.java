
/**
 * Author: Alex Damon
 * Date: 04/22/18
 */

public class GenreFilter implements Filter {
    private String genre;

    public GenreFilter(String genre) {
        this.genre = genre;
    }
    
    @Override
    public boolean satisfies(String id) {
        if(MovieDatabase.getGenres(id).contains(genre))
            return true;
        return false;
    }
}
