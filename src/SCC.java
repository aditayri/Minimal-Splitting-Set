

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Date 10/01/2014
 * @author Tushar Roy
 *
 * Given a directed graph, find all strongly connected components in this graph.
 * We are going to use Kosaraju's algorithm to find strongly connected component.
 *
 * Algorithm
 * Create a order of vertices by finish time in decreasing order.
 * Reverse the graph
 * Do a DFS on reverse graph by finish time of vertex and created strongly connected
 * components.
 *
 * Runtime complexity - O(V + E)
 * Space complexity - O(V)
 *
 * References
 * https://en.wikipedia.org/wiki/Strongly_connected_component
 * http://www.geeksforgeeks.org/strongly-connected-components/
 */
public class SCC {

    public List<Set<Vertex<Integer>>> scc(Graph<Integer> graph)
    {

        //it holds vertices by finish time in reverse order.
        Deque<Vertex<Integer>> stack = new ArrayDeque<>();
        //holds visited vertices for DFS.
        Set<Vertex<Integer>> visited = new HashSet<>();

        //populate stack with vertices with vertex finishing last at the top.
        for (Vertex<Integer> vertex : graph.getAllVertex()) {
            if (visited.contains(vertex)) {
                continue;
            }
            DFSUtil(vertex, visited, stack);
        }

        //reverse the graph.
        Graph<Integer> reverseGraph = reverseGraph(graph);

        //Do a DFS based off vertex finish time in decreasing order on reverse graph..
        visited.clear();
        List<Set<Vertex<Integer>>> result = new ArrayList<>();
        while (!stack.isEmpty()) {
        //	System.out.println("stack "+stack);
            Vertex<Integer> vertex = reverseGraph.getVertex(stack.poll().getId());
            if(visited.contains(vertex)){
                continue;
            }
            Set<Vertex<Integer>> set = new HashSet<>();
            DFSUtilForReverseGraph(vertex, visited, set);
            result.add(set);
        }

        return result;
    }

    private Graph<Integer> reverseGraph(Graph<Integer> graph) {
    	Graph<Integer> reverseGraph = new Graph<>(true);
    	//        for (Edge<Integer> edge : graph.getAllEdges()) {
    	//            reverseGraph.addEdge(edge.getVertex2().getId(), edge.getVertex1()
    	//                    .getId(), edge.getWeight());
    	//        }

    	for(Vertex<Integer> v: graph.getAllVertex()) 
    	{
    		reverseGraph.addSingleVertex(v.getId());
    		for (Edge<Integer> edge : graph.getAllEdges()) {
    			if(edge.getVertex1().equals(v)) {
    				reverseGraph.addEdge(edge.getVertex2().getId(), edge.getVertex1()
    						.getId(), edge.getWeight());
    			}
    		}

    	}
    	return reverseGraph;
    }

    private void DFSUtil(Vertex<Integer> vertex,
            Set<Vertex<Integer>> visited, Deque<Vertex<Integer>> stack) {
        visited.add(vertex);
        for (Vertex<Integer> v : vertex.getAdjacentVertexes()) {
            if (visited.contains(v)) {
                continue;
            }
            DFSUtil(v, visited, stack);
        }
        stack.offerFirst(vertex);
    }

    private void DFSUtilForReverseGraph(Vertex<Integer> vertex,
                                        Set<Vertex<Integer>> visited, Set<Vertex<Integer>> set) {
        visited.add(vertex);
        set.add(vertex);
//        if(vertex==null) {
//        	System.out.println("adi is null");
//        }
        for (Vertex<Integer> v : vertex.getAdjacentVertexes()) {
            if ( visited.contains(v)) {
                continue;
            }
            DFSUtilForReverseGraph(v, visited, set);
        }
    }

    public static void main(String args[]){
        Graph<Integer> g = new Graph<>(true);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);
        g.addSingleVertex(5);
        g.addSingleVertex(20);

        SCC scc = new SCC();
        List<Set<Vertex<Integer>>> result = scc.scc(g);

        //print the result
        result.forEach(set -> {
            set.forEach(v -> System.out.print(v.getId() + " "));
            System.out.println();
        });
    }
}