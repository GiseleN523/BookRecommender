public class NamedUser extends User
{
    private String name;
    /**
     * Constructor for the class. Sets name instance variable to a given String.
     * @param name, the String to set name to
     */
    public NamedUser(String name)
    {
        super();
        this.name=name;
    }
    /**
     * Accessor method for the instance variable name.
     * @return the instance variable name, a String
     */
    public String getName()
    {
        return name;
    }
    /**
     * This class' implementation of readBook(). Adds given Book to booksRead variable and whether they
     * liked it to corresponding index in booksLiked variable. In addition, if this Book is not already in
     * the given BookRecommender's books ArrayList variable, it will be added to both that and to that
     * BookRecommender's WeightedGraph variable. The weights of the edges between this and the other items'
     * in that graph will then be updated based on the user's previous preferences and their preference for this
     * Book. If the user liked both this and another Book, or disliked both this and another Book, the weight of
     * the edge between those is incremented, and if the user liked one but not the other, that edge is decremented.
     * @param newBook, the Book to be read by this user
     * @param liked, a boolean representing whether the user liked it or not
     * @param recommender, the BookRecommender being used (not needed in GuestUser implementation)
     */
    public void readBook(Book newBook, boolean liked, BookRecommender recommender)
    {
        getBooksRead().add(newBook);
        getBooksLiked().add(liked);
        if(!recommender.getBooks().contains(newBook))
        {
            recommender.getGraph().addVertex();
            recommender.getBooks().add(newBook);
        }
        for(Book book:recommender.getBooks())
        {
            if(getBooksRead().contains(book) && !book.equals(newBook))
            {
                if((liked && getBooksLiked().get(getBooksRead().indexOf(book))) || (!liked && !getBooksLiked().get(getBooksRead().indexOf(book))))
                {
                    recommender.getGraph().incrementEdge(recommender.getBooks().indexOf(book), recommender.getBooks().indexOf(newBook));
                }
                else
                {
                    recommender.getGraph().decrementEdge(recommender.getBooks().indexOf(book), recommender.getBooks().indexOf(newBook));
                }
            }
        }
    }
    /**
     * Gets the menu text that gives the user their options as a GuestUser.
     * @return a String containing the menu text for this User
     */
    public String getMenuText()
    {
        return "\n  (1) Rate books\n  (2) Add books\n  (3) Get recommendation\n  (4) Close and Save: ";
    }
    /**
     * Takes a String representing input from the user where they choose a menu option and
     * converts it to an int that is common to all subclasses of User (adjusted to reflect the 
     * number of options one type of User might have compared to another).
     * @param indStr, a String containing the user's input (should be "1", "2", "3", or "4")
     * @return an int after their input has been converted to a number that is common to all Users
     */
    public int convertMenuInd(String ind)
    {
        try
        {
            return Integer.parseInt(ind);
        }
        catch(Exception e)
        {
            return 0;
        }
    }
    /**
     * toString method that overrides the one inherited from the User class. Returns result of 
     * call to super() version of the method along with the value of the name instance variable.
     */
    public String toString()
    {
        return name+": \n"+super.toString();
    }
}
