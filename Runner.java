/*
- Data
- Update collaborations file
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Runner {
    /**
     * Main method. Creates a new BookRecommender and ArrayList of NamedUsers tracking the previous users. 
     * If there is a "save.tmp" file, these values are loaded in from it; otherwise, they start out empty. 
     * The user can then enter their name. If their name is in the list of previous users, their data is 
     * loaded in; otherwise, they have the option to create an account or continue as a guest (as a guest, 
     * they have the option to start from scratch or "shadow" another player to start out with their 
     * preferences). They then have the option to rate books, get a recommendation, or add new books (if 
     * they are a NamedUser). They also have the option to end the program which, if they are a NamedUser, 
     * will resave the file "save.tmp" with their data added to it. A more readable text version is also
     * saved to a .txt file.
     * @param args an String array, not used in this program
     */
    public static void main(String[] args)
    {
        BookRecommender recommender=new BookRecommender();
        ArrayList<NamedUser> previousUsers=new ArrayList<NamedUser>();
        
        try(ObjectInputStream input=new ObjectInputStream(new FileInputStream("save.tmp")))
        {
            recommender=((BookRecommender)input.readObject());
            int num=input.readInt();
            for(int i=0; i<num; i++)
            {
                previousUsers.add((NamedUser)input.readObject());
            }
        }
        catch(Exception e)
        {
            System.out.println("Failed to read save file. Check to make sure 'save.tmp' is in the same directory as this program.");
            System.out.println("Continuing with a blank recommender from scratch, with no books previously loaded in or rated...\n");
        }
        finally
        {
            Scanner scanner=new Scanner(System.in);
            System.out.print("\nEnter your name: ");
            String name=scanner.nextLine().trim();
            User thisUser=null;

            for(NamedUser user:previousUsers)
            {
                if(user.getName().equals(name))
                {
                    thisUser=user;
                }
            }
            if(thisUser==null)
            {
                String input="";
                while(!input.equals("1") && !input.equals("2"))
                {
                    System.out.print("\nIt looks like you've never used this program before.\n  (1) Create an account\n  (2) Continue as guest: ");
                    input=scanner.nextLine().trim();
                    if(input.equals("1"))
                    {
                        thisUser=new NamedUser(name);
                    }
                    else if(input.equals("2"))
                    {
                        thisUser=new GuestUser();
                        System.out.print("\nWould you like to:\n  (1) Shadow a previous user and start out with their data\n  (2) Start from scratch: ");
                        input=scanner.nextLine();
                        if(input.equals("1"))
                        {
                            for(int i=0; i<previousUsers.size(); i++)
                            {
                                System.out.print("\n "+(i+1)+". "+previousUsers.get(i));
                            }
                            int input2=-1;
                            while(input2<0 || input2>=previousUsers.size())
                            {
                                System.out.print("\nEnter the number corresponding to the user you would like to shadow: ");
                                input2=scanner.nextInt()-1;
                            }
                            ((GuestUser)thisUser).shadow(previousUsers.get(input2));
                        }
                    }
                }
            }
            else
            {
                System.out.println("\nLoading in user data...");
                System.out.println("Welcome back, "+name);
            }

            ArrayList<Book> booksAsked=new ArrayList<Book>();
            booksAsked.addAll(thisUser.getBooksRead());

            int choice=0;
            boolean ratedOneExistingBook=thisUser.getBooksRead().size()>0;
            while(choice!=4)
            {
                System.out.print("\n"+thisUser.getMenuText());
                choice=thisUser.convertMenuInd(scanner.nextLine().trim());
                if(choice==1)//Rate Books
                {
                    String input=" ";
                    while(input.length()>0 && booksAsked.size()<recommender.getBooks().size())
                    {
                        Book book=recommender.getRandomBook();
                        if(!booksAsked.contains(book))
                        {
                            input=" ";
                            while(!input.equals("1") && !input.equals("2") && !input.equals("3") && input.length()>0)
                            {
                                System.out.print("\n\nDo you like the book "+book.getTitle()+"?\n  (1) yes\n  (2) no\n  (3) haven't read\n  (press ENTER to return to menu): ");
                                input=scanner.nextLine().toLowerCase().trim();
                                if(input.equals("1") || input.equals("2") || input.equals("3"))
                                {
                                    booksAsked.add(book);
                                    ratedOneExistingBook=true;
                                }
                                if(input.equals("1"))
                                {
                                    thisUser.readBook(book, true, recommender);
                                }
                                else if(input.equals("2"))
                                {
                                    thisUser.readBook(book, false, recommender);
                                }
                            }
                        }
                    }
                    if(booksAsked.size()>=recommender.getBooks().size())
                    {
                        System.out.println("\n  There are no books remaining to rate. Please select another option.");
                    }

                }
                else if(choice==2)//Add Books
                {
                    System.out.print("\nEnter title of book to add: ");
                    String title=scanner.nextLine();
                    if(recommender.containsTitle(title))
                    {
                        System.out.println("This book has already been entered.");
                    }
                    else
                    {
                        System.out.print("\nEnter the author of "+title+": ");
                        String author=scanner.nextLine();
                        for(int i=0; i<Genre.values().length; i++)
                        {
                            System.out.println("  ("+(i+1)+") "+Genre.values()[i]);
                        }
                        System.out.print("Enter the numbers corresponding to each genre this book falls under, with spaces in between each: ");
                        Scanner inputScan=new Scanner(scanner.nextLine().trim());
                        ArrayList<Genre> genres=new ArrayList<Genre>();
                        while(inputScan.hasNextInt())
                        {
                            int num=inputScan.nextInt()-1;
                            if(num>0 && num<Genre.values().length)//ignores int if it doesn't fall within bounds of Genre.values() array
                            {
                                genres.add(Genre.values()[num]);
                            }
                        }
                        String input="";
                        while(!input.equals("1") && !input.equals("2"))
                        {
                            System.out.print("\nDo you like the book "+title+"?\n  (1) yes\n  (2) no: ");
                            input=scanner.nextLine().toLowerCase().trim();
                        }
                        if(input.equals("1"))
                        {
                            thisUser.readBook(new Book(title, author, genres), true, recommender);
                        }
                        else if(input.equals("2"))
                        {
                            thisUser.readBook(new Book(title, author, genres), false, recommender);
                        }
                        booksAsked.add(thisUser.getBooksRead().get(thisUser.getBooksRead().size()-1));
                    }
                }
                else if(choice==3)//Get Recommendation
                {
                    if(ratedOneExistingBook)
                    {
                        ArrayList<Book> booksLiked=new ArrayList<Book>();
                        for(Book book:thisUser.getBooksRead())
                        {
                            if(thisUser.likedBook(book))
                            {
                                booksLiked.add(book);
                            }
                        }
                        ArrayList<Book> recommendation=recommender.recommend(booksLiked);
                        recommendation.removeAll(thisUser.getBooksRead());
                        System.out.println("\nRecommendation:");
                        for(int i=0; i<10; i++)
                        {
                            if(i<recommendation.size())
                            {
                                System.out.println("  "+(i+1)+". "+recommendation.get(i));
                            }
                        }
                    }
                    else
                    {
                        System.out.println("\n  You must rate at least one book before getting a recommendation.");
                    }
                }
                else if(choice!=4)
                {
                    System.out.println("\n  Please enter one of the following digits to make your selection.");
                }
            }
            scanner.close();
            if(thisUser instanceof NamedUser)
            {
                System.out.println("\nSaving progress...");
                try
                {
                    ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream("save.tmp"));
                    output.writeObject(recommender);
                    if(!previousUsers.contains(thisUser))
                    {
                        previousUsers.add((NamedUser)thisUser);
                    }
                    output.writeInt(previousUsers.size());
                    for(NamedUser user:previousUsers)
                    {
                        output.writeObject(user);
                    }
                    output.close();
                    BufferedWriter bWrite=new BufferedWriter(new FileWriter("save.txt"));
                    for(Book b:recommender.getBooks())
                    {
                        bWrite.write(b+"\n");
                    }
                    bWrite.write("\n"+recommender.getGraph()+"\n\n");
                    for(User u:previousUsers)
                    {
                        bWrite.write(u+"\n");
                    }
                    bWrite.close();
                    System.out.println("Progress saved.");
                }
                catch(Exception e)
                {
                    System.out.println(e);
                    System.out.println("Error. Failed to write save data to file.");
                }
            }
        }
    }
}
