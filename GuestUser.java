public class GuestUser extends User
{
    /**
     * Indicates that the user does not want to start from scratch, but wants to "shadow" another user
     * by starting out with the book preferences they chose.
     * Copies all the books the given User has read into this instance and takes the preferences they chose for them
     * @param previousUser, the User object that this user has chosen to start out with the values from
     */
    public void shadow(User previousUser)
    {
        for(int i=0; i<previousUser.getBooksRead().size(); i++)
        {
            readBook(previousUser.getBooksRead().get(i), previousUser.getBooksLiked().get(i), null);
        }
    }
    /**
     * This class' implementation of readBook(). Adds given Book to booksRead variable and whether they
     * liked it to corresponding index in booksLiked variable.
     * @param newBook, the Book to be read by this user
     * @param liked, a boolean representing whether the user liked it or not
     * @param recommender, the BookRecommender being used (not needed in GuestUser implementation)
     */
    public void readBook(Book newBook, boolean liked, BookRecommender recommender)
    {
        getBooksRead().add(newBook);
        getBooksLiked().add(liked);
    }
    /**
     * Gets the menu text that gives the user their options as a GuestUser.
     * @return a String containing the menu text for this User
     */
    public String getMenuText()
    {
        return "\n  (1) Rate books\n  (2) Get recommendation\n  (3) Close: ";
    }
    /**
     * Takes a String representing input from the user where they choose a menu option and
     * converts it to an int that is common to all subclasses of User (adjusted to reflect the 
     * number of options one type of User might have compared to another).
     * @param indStr, a String containing the user's input (should be "1", "2", or "3")
     * @return an int after their input has been converted to a number that is common to all Users
     */
    public int convertMenuInd(String indStr)
    {
        if(indStr.equals("1"))
        {
            return 1;
        }
        if(indStr.equals("2") || indStr.equals("3"))
        {
            return Integer.parseInt(indStr)+1;
        }
        return 0;

    }
}
