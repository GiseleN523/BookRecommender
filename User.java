import java.util.ArrayList;
import java.io.Serializable;

public abstract class User implements Serializable
{
    private ArrayList<Book> booksRead;
    private ArrayList<Boolean> booksLiked;
    /**
     * Constructor for the User class. Sets booksRead and booksLiked instance variables
     * to new, empty ArrayLists.
     */
    public User()
    {
        booksRead=new ArrayList<Book>();
        booksLiked=new ArrayList<Boolean>();
    }
    /**
     * Accessor method for the booksRead instance variable.
     * @return the booksRead instance variable, an ArrayList<Book>
     */
    public ArrayList<Book> getBooksRead()
    {
        return booksRead;
    }
    /**
     * Accessor method for the booksLiked instance variable
     * @return the booksLiked instance variable, an ArrayList<Boolean>
     */
    public ArrayList<Boolean> getBooksLiked()
    {
        return booksLiked;
    }
    /**
     * Returns the index of the Book in the ArrayList booksRead that has the given value for its
     * title variable. If none exists, returns -1.
     * @param title, a String representing a Book title
     * @return an int, the index in booksRead of the Book with this title
     */
    public int indOfTitle(String title)
    {
        for(int i=0; i<booksRead.size(); i++)
        {
            if(booksRead.get(i).getTitle().equals(title))
            {
                return i;
            }
        }
        return -1;
    }
    /**
     * Searches through booksRead ArrayList and uses data in corresponding index 
     * of booksLiked to determine whether this user liked the Book in booksRead 
     * with the given title. Returns false if no Book with that title is found.
     * @param title, a String representing a Book title
     * @return a boolean, whether this user liked the Book with this title or not
     */
    public boolean likedBook(String title)
    {
        for(int i=0; i<booksRead.size(); i++)
        {
            if(booksRead.get(i).getTitle().equals(title))
            {
                return booksLiked.get(i);
            }
        }
        return false;
    }
    /**
     * Returns whether this user liked the given Book. If they have not read it (it isn't
     * in the booksRead variable), returns false.
     * @param book, a Book to determine the user's preference for
     * @return a boolean, whether this user liked the given Book or not
     */
    public boolean likedBook(Book book)
    {
        if(booksRead.contains(book))
        {
            return booksLiked.get(booksRead.indexOf(book));
        }
        return false;
    }
    /**
     * Abstract method to be implemented in the subclasses that gets the menu text that gives the user their 
     * options based on the subclass they are part of.
     * @return a String containing the menu text for this User
     */
    public abstract String getMenuText();
    /**
     * Abstract method to be implemented in the subclasses that takes a String representing input from the 
     * user where they choose a menu option and converts it to an int that is common to all subclasses of User 
     * (adjusted to reflect the number of options one type of User might have compared to another).
     * @param indStr, a String containing the user's input
     * @return an int after their input has been converted to a number that is common to all Users
     */
    public abstract int convertMenuInd(String ind);
    /**
     * Abstract method to be implemented in the subclasses that reacts to this user having read a Book
     * and giving their preference for it.
     * @param newBook, the Book to be read by this User
     * @param liked, a boolean representing whether the user liked it or not
     * @param recommender, the BookRecommender being used
     */
    public abstract void readBook(Book newBook, boolean liked, BookRecommender recommender);
    /**
     * This class' toString() method. Returns formatted version of booksRead ArrayList variable along with 
     * corresponding values from booksLiked variable.
     * @return a formatted String representation of the instance's data
     */
    public String toString()
    {
        String str="";
        for(int i=0; i<booksRead.size(); i++)
        {
            str+="  "+booksRead.get(i)+", Liked:"+booksLiked.get(i)+"\n";
        }
        return str;
    }
}
