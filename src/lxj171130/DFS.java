/** Starter code for SP8
 *  @author 
 */

// change to your netid
package lxj171130;

import rbk.Graph.Vertex;
import rbk.Graph;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;
import rbk.Graph.Timer;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	enum Color 
	{ 
	    WHITE, BLACK, GREY; 
	}
	int time;
	int topNum;
	LinkedList<Vertex> finishList;
	
    public static class DFSVertex implements Factory {
	int cno;
	Color color;
	Vertex parent;
	int top;
	int dis;
	int fin;
	public DFSVertex(Vertex u) {
        color = Color.WHITE;
        parent = null;
        top = 0;
	}
	public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
	super(g, new DFSVertex(null));
    }

    public static DFS depthFirstSearch(Graph g) {
	return null;
    }

    // Member function to find topological order
    public List<Vertex> topologicalOrder1() throws Exception {
    	topNum = g.size();
    	finishList = new LinkedList<>();
		for(Vertex u: g) {
		    get(u).color = Color.WHITE;
		    get(u).parent = null;
		    get(u).top = 0;
		}
		time = 0;
		
    	for(Vertex u: g) {
    		if(get(u).color == Color.WHITE) {
    			topologicalOrder1_Visit(u);
    		}
    	}
	return finishList;
    }

    private void topologicalOrder1_Visit(Vertex u) throws Exception {
		get(u).color = Color.GREY;
		get(u).dis = ++time;
		
	    for(Edge e: g.incident(u)) {
			Vertex v = e.otherEnd(u);
			if(get(v).color == Color.WHITE) {
			    get(v).parent = u;
			    topologicalOrder1_Visit(v);
			}
			else if(get(v).color == Color.GREY) {
				throw new Exception("Cycles detected, Not a DAG");
			}
	    }
		get(u).top = topNum--;
		finishList.addFirst(u);
		get(u).color = Color.BLACK;
		get(u).fin = ++time;		
	}

	// Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
	return 0;
    }

    // After running the onnected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
	return get(u).cno;
    }
    
    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
	DFS d = new DFS(g);
	try {
		return d.topologicalOrder1();
	} catch (Exception e) {
		return null;
	}
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
	return null;
    }

    public static void main(String[] args) throws Exception {
	//String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
	String string = "7 6   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   6 7 1 1";
	Scanner in;
	// If there is a command line argument, use it as file from which
	// input is read, otherwise use input from string.
	in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
	
	// Read graph from input
       // Graph g = Graph.readGraph(in);
        Graph g = Graph.readDirectedGraph(in);
	g.printGraph(false);

	DFS d = new DFS(g);
	int numcc = d.connectedComponents();
	System.out.println("Number of components: " + numcc + "\nu\tcno");
	for(Vertex u: g) {
	    System.out.println(u + "\t" + d.cno(u));
	}
	
	List<Vertex> finishList = topologicalOrder1(g);
	System.out.println("Topological ordering");
	if(finishList == null) {
		System.out.println("Not a DAG");
	}
	else {
		for(Vertex u: finishList) {
		    System.out.print(u + " ");
		}
	}
	
    }
}
