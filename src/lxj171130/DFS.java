/**
 * @author Leejia James
 * @author Nirbhay Sibal
 *
 * Depth-first search (DFS)
 * Finding topological order of DAG using DFS
 *
 * Ver 1.0: 2018/10/26
 */

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
	boolean notDAG;
	
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
    
    /**
     * Recursive algorithm to visit the nodes of a graph
     * @param Graph g
     * @return DFS
     */
    public static DFS depthFirstSearch(Graph g) {
    	DFS dfstemp = new DFS(g);
    	dfstemp.topNum = g.size();
    	dfstemp.finishList = new LinkedList<>();
		for(Vertex u: g) {
		    dfstemp.get(u).color = Color.WHITE;
		    dfstemp.get(u).parent = null;
		    dfstemp.get(u).top = 0;
		}
		dfstemp.time = 0;
		
    	for(Vertex u: g) {
    		if(dfstemp.get(u).color == Color.WHITE) {
				dfstemp.dfsVisit(u);
    		}
    	}
    	return dfstemp;
    }

    /**
     * Utility method for depthFirstSearch()
     * @param Vertex u
     */
    private void dfsVisit(Vertex u) { 
    	// u is visited by DFS
    	// Precondition: u is white
		get(u).color = Color.GREY;
		get(u).dis = ++time;
		
	    for(Edge e: g.incident(u)) {
			Vertex v = e.otherEnd(u);
			if(get(v).color == Color.WHITE) {
			    get(v).parent = u;
			    dfsVisit(v);
			}
			else if(get(v).color == Color.GREY) {
				// cycle detected
				notDAG = true;
			}
	    }
		get(u).top = topNum--;
		finishList.addFirst(u);
		get(u).color = Color.BLACK;
		get(u).fin = ++time;
	}

	/**
	 * Member function to find topological order
	 * @return List of vertices in topological order if graph is DAG, 
	 * 			null otherwise
	 */
    public List<Vertex> topologicalOrder1() {
    	DFS d1 = depthFirstSearch(g);
    	if(d1.notDAG) {
    		return null;
    	}
    	else {
    		return d1.finishList;
    	}
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
    
    /**
     * Finding topological order of a DAG using DFS
     * @param Graph g
     * @return List of vertices in topological order, null if g is not a DAG
     */
    public static List<Vertex> topologicalOrder1(Graph g) {
		DFS d = new DFS(g);
		return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
	return null;
    }

    public static void main(String[] args) throws Exception {
	//String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
	String string = "7 6   1 2 2   1 3 3   2 4 5   3 4 4   5 1 7   7 6 1 0";
	Scanner in;
	// If there is a command line argument, use it as file from which
	// input is read, otherwise use input from string.
	in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
	
	// Read graph from input
        //Graph g = Graph.readGraph(in);
        Graph g = Graph.readDirectedGraph(in);
	g.printGraph(false);

	DFS d = new DFS(g);
	int numcc = d.connectedComponents();
	System.out.println("Number of components: " + numcc + "\nu\tcno");
	for(Vertex u: g) {
	    System.out.println(u + "\t" + d.cno(u));
	}
	System.out.println();
	
	List<Vertex> finishList = topologicalOrder1(g);	
	System.out.println("Topological order of DAG using DFS");
	if(finishList == null) {
		System.out.println("--Cycles detected, Not a DAG--");
	}
	else {
		for(Vertex u: finishList) {
		    System.out.print(u + " ");
		}
	}
	System.out.println();
	
    }
}
