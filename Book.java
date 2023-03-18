import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable
{
    private String title;
    private String author;
    private ArrayList<Genre> genres;
    /**
     * Constructor for Book class; assigns all variables to given argument values.
     * @param title, a String to assign the instance variable title to
     * @param author, a String to assign the instance variable author to
     * @param genres, an ArrayList>Genre> to assign the instance variable genres to
     */
    public Book(String title, String author, ArrayList<Genre> genres)
    {
        this.title=title;
        this.author=author;
        this.genres=genres;
    }
    /**
     * Accessor method that returns the instance variable title.
     * @return the String instance variable title
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * toString method that returns a String representation of all three instance variables.
     */
    public String toString()
    {
        return title+", by "+author+" "+genres;
    }
}
