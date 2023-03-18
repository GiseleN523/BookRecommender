import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BookRecommender implements Serializable
{
    private WeightedGraph graph;
    private ArrayList<Book> books;
    /**
     * Constructor for the BookRecommender class. Sets both instance variables to default values.
     */
    public BookRecommender()
    {
        graph=new WeightedGraph();
        books=new ArrayList<Book>();
    }
    /**
     * Accessor method for graph instance variable.
     * @return the WeightedGraph instance variable graph
     */
    public WeightedGraph getGraph()
    {
        return graph;
    }
    /**
     * Accessor method for books instance variable.
     * @return the ArrayList<Book> instance variable books
     */
    public ArrayList<Book> getBooks()
    {
        return books;
    }
    /**
     * Determines whether this instance already contains a Book with the given title (NOT Case-Sensitive).
     * @param title, a String, the title to search for in books ArrayList
     * @return a boolean indicating whether a book with the given title was found in the instance variable books.
     */
    public boolean containsTitle(String title)
    {
        for(Book book:books)
        {
            if(book.getTitle().toLowerCase().equals(title.toLowerCase().trim()))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * Returns a random Book from the ArrayList instance variable books.
     * @return a Book randomly chosen from books
     */
    public Book getRandomBook()
    {
        return books.get((int)(books.size()*Math.random()));
    }
    /**
     * Ranks all books user hasn't read in an ArrayList, with the first one being the most highly recommended. 
     * Does this by going through list of books user liked and compiling a map with cumulative weights for the books,
     * which are calculated by adding up the weights of each edge between that book they liked and the rest of the books.
     * The books in this map are then transferred in order to an ArrayList that is returned.
     * 
     * @param userBooksLiked, an ArrayList<Book> containing the Books the user said they liked
     * @return an ordered ArrayList<Book> with the books recommended for this user (may contain books they have already read)
     */
    public ArrayList<Book> recommend(ArrayList<Book> userBooksLiked)
    {
        HashMap<Book, Integer> map=new HashMap<Book, Integer>();
        for(Book userBook:userBooksLiked)
        {
            int ind=books.indexOf(userBook);
            for(int i=0; i<graph.getEdgesFor(ind).size(); i++)
            {
                int weight=graph.getEdgesFor(ind).get(i);
                if(map.containsKey(books.get(i)))
                {
                    map.put(books.get(i), map.get(books.get(i))+weight);
                }
                else
                {
                    map.put(books.get(i), weight);
                }
            }
        }
        ArrayList<Book> recommendation=new ArrayList<Book>();
        while(!map.isEmpty())
        {
            Book maxWeightKey=null;
            for(Book key:map.keySet())
            {
                if(maxWeightKey==null || map.get(key)>map.get(maxWeightKey))
                {
                    maxWeightKey=key;
                }
            }
            recommendation.add(maxWeightKey);
            map.remove(maxWeightKey);
        }
        return recommendation;
    }
}