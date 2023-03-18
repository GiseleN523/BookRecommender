import java.util.ArrayList;
import java.io.Serializable;

public class WeightedGraph implements Serializable
{
    private ArrayList<ArrayList<Integer>> array;
    /**
     * Constructor for the WeightedGraph class. Sets the instance variable array to a new ArrayList<ArrayList<Integer>>.
     */
    public WeightedGraph()
    {
        array=new ArrayList<ArrayList<Integer>>();
    }
    /**
     * Adds a new vertex with weight 0 to the graph.
     */
    public void addVertex()
    {
        ArrayList<Integer> newList=new ArrayList<Integer>();
        for(int i=0; i<array.size(); i++)
        {
            newList.add(0);
            array.get(i).add(0);
        }
        array.add(newList);
        array.get(array.size()-1).add(0);
    }
    /**
     * Returns the weights of the edges connected to the vertex at the given index in the graph.
     * @param ind, an int representing the index in the graph of the vertex to get the edges for
     * @return an ArrayList<Integer> with the weights of the edges connected to this vertex
     */
    public ArrayList<Integer> getEdgesFor(int ind)
    {
        return array.get(ind);
    }
    /**
     * Increments the weight of the edge between the vertices at the two given indices in the graph.
     * @param vertex1Ind, an int representing the index in the graph of the first vertex
     * @param vertex2Ind, an int representing the index in the graph of the second vertex
     */
    public void incrementEdge(int vertex1Ind, int vertex2Ind)
    {
        if(vertex1Ind>-1 && vertex1Ind<array.size() && vertex2Ind>-1 && vertex2Ind<array.size())
        {
            array.get(vertex1Ind).set(vertex2Ind, array.get(vertex1Ind).get(vertex2Ind)+1);
            array.get(vertex2Ind).set(vertex1Ind, array.get(vertex2Ind).get(vertex1Ind)+1);
        }
    }
    /**
     * Decrements the weight of the edge between the vertices at the two given indices in the graph.
     * @param vertex1Ind, an int representing the index in the graph of the first vertex
     * @param vertex2Ind, an int representing the index in the graph of the second vertex
     */
    public void decrementEdge(int vertex1Ind, int vertex2Ind)
    {
        if(vertex1Ind>-1 && vertex1Ind<array.size() && vertex2Ind>-1 && vertex2Ind<array.size())
        {
            array.get(vertex1Ind).set(vertex2Ind, array.get(vertex1Ind).get(vertex2Ind)-1);
            array.get(vertex2Ind).set(vertex1Ind, array.get(vertex2Ind).get(vertex1Ind)-1);
        }
    }
    /**
     * Sets the weight of the edge between the verties at the two given indices in the graph.
     * @param vertex1Ind, an int representing the index in the graph of the first vertex
     * @param vertex2Ind, an int representing the index in the graph of the second vertex
     * @param weight, an int representing the weight to set the edge between these vertices to
     */
    public void setEdge(int vertex1Ind, int vertex2Ind, int weight)
    {
        if(vertex1Ind>-1 && vertex1Ind<array.size() && vertex2Ind>-1 && vertex2Ind<array.size())
        {
            array.get(vertex1Ind).set(vertex2Ind, weight);
            array.get(vertex2Ind).set(vertex1Ind, weight);
        }
    }
    /**
     * Retrieves the weight of the edge between two given vertices.
     * @param vertex1Ind, an int representing the index in the graph of the first vertex
     * @param vertex2Ind, an int representing the index in the graph of the second vertex
     * @return an int representing the weight of the edge between these two vertices
     */
    public int getEdgeWeight(int vertex1Ind, int vertex2Ind)
    {
        if(vertex1Ind>-1 && vertex1Ind<array.size() && vertex2Ind>-1 && vertex2Ind<array.size())
            return array.get(vertex1Ind).get(vertex2Ind);
        return 0;
    }
    /**
     * Retrieves the size of the graph (the number of vertices in it)
     * @return an int representing the number of vertices this graph has
     */
    public int getSize()
    {
        return array.size();
    }
    /**
     * toString method that creates and returns a formatted String representation 
     * of this graph and the weights in it.
     */
    public String toString()
    {
        String str="";
        for(int r=0; r<array.size(); r++)
        {
            for(int c=0; c<array.get(r).size(); c++)
            {
                str+=String.format("%3s,", array.get(r).get(c));
            }
            str+="\n";
        }
        return str;
    }
}