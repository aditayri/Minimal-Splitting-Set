import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;


public class Graph<T>{

	private List<Edge<T>> allEdges;
	private Map<Long,Vertex<T>> allVertex;
	boolean isDirected = false;

	public Graph(boolean isDirected){
		allEdges = new ArrayList<Edge<T>>();
		allVertex = new HashMap<Long,Vertex<T>>();
		this.isDirected = isDirected;
	}

	public void addEdge(long id1, long id2){
		addEdge(id1,id2,0);
	}

	public boolean hasEdge(Vertex<Integer> v1 ,Vertex<T> v2)
	{
		for (Edge<Integer> edge : v1.getEdges()) 
		{
			if(edge.getVertex2().id==v2.id)
				return true;
		}
	/*	if(v1.getEdges().contains(v2))
		{
			return true;
		}*/
		return false;
	}

	//This works only for directed graph because for undirected graph we can end up
	//adding edges two times to allEdges
	public void addVertex(Vertex<T> vertex){
		if(allVertex.containsKey(vertex.getId())){
			return;
		}
		allVertex.put(vertex.getId(), vertex);
		for(Edge<T> edge : vertex.getEdges()){
			allEdges.add(edge);
		}
	}

	public Vertex<T> addSingleVertex(long id){
		if(allVertex.containsKey(id)){
			return allVertex.get(id);
		}
		Vertex<T> v = new Vertex<T>(id);
		allVertex.put(id, v);
		return v;
	}

	public Vertex<T> getVertex(long id){
		return allVertex.get(id);
	}

	public void addEdge(long id1,long id2, int weight){
		Vertex<T> vertex1 = null;
		if(allVertex.containsKey(id1)){
			vertex1 = allVertex.get(id1);
		}else{
			vertex1 = new Vertex<T>(id1);
			allVertex.put(id1, vertex1);
		}
		Vertex<T> vertex2 = null;
		if(allVertex.containsKey(id2)){
			vertex2 = allVertex.get(id2);
		}else{
			vertex2 = new Vertex<T>(id2);
			allVertex.put(id2, vertex2);
		}

		Edge<T> edge = new Edge<T>(vertex1,vertex2,isDirected,weight);
		allEdges.add(edge);
		vertex1.addAdjacentVertex(edge, vertex2);
		if(!isDirected){
			vertex2.addAdjacentVertex(edge, vertex1);
		}

	}
	public void addEdge(long id1,long id2, int weight,int sizeOfS){
		Vertex<T> vertex1 = null;
		if(allVertex.containsKey(id1)){
			vertex1 = allVertex.get(id1);
		}else{
			vertex1 = new Vertex<T>(id1);
			allVertex.put(id1, vertex1);
		}
		Vertex<T> vertex2 = null;
		if(allVertex.containsKey(id2)){
			vertex2 = allVertex.get(id2);
		}else{
			vertex2 = new Vertex<T>(id2);
			allVertex.put(id2, vertex2);
		}

		Edge<T> edge = new Edge<T>(vertex1,vertex2,isDirected,weight,sizeOfS);
		allEdges.add(edge);
		vertex1.addAdjacentVertex(edge, vertex2);
		if(!isDirected){
			vertex2.addAdjacentVertex(edge, vertex1);
		}

	}

	public List<Edge<T>> getAllEdges(){
		return allEdges;
	}

	public Collection<Vertex<T>> getAllVertex(){
		return allVertex.values();
	}
	public void setDataForVertex(long id, T data){
		if(allVertex.containsKey(id)){
			Vertex<T> vertex = allVertex.get(id);
			vertex.setData(data);
		}
	}
	public void removeEdge(Edge<T> edge) {
		 List<Edge<T>> newEdgeList= new ArrayList<Edge<T>>();
		 for(Edge<T> e: getAllEdges()) {
			 if(!e.equals(edge)) {
				 newEdgeList.add(e);
			 }
		 }
		 this.allEdges=newEdgeList;
	}
	/**
	 * Remove the vertex v from the graph.
	 * 
	 * @param v The vertex that will be removed.
	 */
	public Graph<Integer> removeVertex(ArrayList<Vertex<Integer>> vertexsListToRemove) {
		Graph<Integer> newGraph= new Graph<Integer>(isDirected); 
		try {
//			System.out.println(" test:::------------------------------------------------------");
			for (Edge<T> e: getAllEdges()) {
				if(!vertexsListToRemove.contains(e.getVertex1()) && !vertexsListToRemove.contains(e.getVertex2())) {
					//getAllEdges().remove(e);
//					System.out.println("v1:" +e.getVertex1().getId()+" v2: "+e.getVertex2().getId());
					newGraph.addEdge(e.getVertex1().getId(), e.getVertex2().getId(),1);
//					System.out.println("end test ------------------------------------------");
				}
			}

		}catch (Exception e2) {
			// TODO: handle exception
			System.err.println("Remove Error: "+e2);
		}


		return newGraph;
	}

	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for(Edge<T> edge : getAllEdges()){
			buffer.append(edge.getVertex1() + " -> " + edge.getVertex2() + " w: " + edge.getWeight() +" s size:"+ edge.getSSize());
			buffer.append("\n");
		}
		return buffer.toString();
	}




	/**	find cut from two set of vertexes id's collection
	 * **/
	public static Collection<Integer> cutLinkList(Collection<Integer> A,Collection<Integer> B){
		Collection<Integer> A_B= new java.util.LinkedList<Integer>();
		for(Integer a:A) {
			if(B.contains(a)) {
				A_B.add(a);
			}
		}
		return A_B;
	}
	/**
	 * we find source of the graph-only the first source is relevant**/
	/*public static LinkedList sourceOfGraph(Graph<Integer>  graph)
	{
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		List<Set<Vertex<Integer>>> result = scc.scc(graph);
		LinkedList s = new LinkedList();
		if(result.isEmpty())
		{
			return s;
		}
		for (Vertex<Integer> vertex : result.get(0)) // go over first set of vertex
		{
			s.addAtTail((int)vertex.getId());	//add to source arrayList 
		}
		
//    System.out.println("------------------------------------------------------------------------------------------------------------------");
//    System.out.println("Here are the size of all the connected component in the graph");
//    //print the result
//    result.forEach(set -> {
//    	System.out.println("sizeof :"+ set.size());
//       // set.forEach(v -> System.out.print(v.getId() + " "));
//        System.out.println();
//    });
//    System.out.println("------------------------------------------------------------------------------------------------------------------");
		return s;
	}*/

	/**
	 * convert clauses data structure in to graph body-->head
	 * body  has edge to head
	 * this is a directed**/
	public static Graph<Integer> initGraph(DataStructure DS ,int numOfRules) 
	{


		Graph<Integer> graph = new Graph<>(true);
		LinkedList<Integer> bodyList;
		LinkedList<Integer> headList;

		for (int i = 0; i < numOfRules; i++) 
		{
			if(DS.T[i]!=null)
			{
				bodyList =DS.T[i].body;
				headList=DS.T[i].head;
				if(bodyList.isEmpty())
				{
					for (int j = 0; j < headList.size(); j++) 
					{
						graph.addSingleVertex(headList.get(j));
					}
				}
				else
				{
					for (int j = 0; j < bodyList.size(); j++) 
					{
						graph.addSingleVertex(bodyList.get(j));
						for (int k = 0; k < headList.size(); k++)
						{
							graph.addEdge(bodyList.get(j),headList.get(k),1);
						}
					}
				}
				
				
			}


		}
//		System.out.println("This is the Graph:");
//		System.out.println(graph);


		return graph;	
	} 
	
	
	
	public static LinkedList<Integer> sourceOfGraph(Graph<Integer>  graph)
	{
		SCC scc = new SCC();
		List<Set<Vertex<Integer>>> result = scc.scc(graph);
		LinkedList<Integer> s = new LinkedList<Integer>();
		if(result.isEmpty())
		{
			return s;
		}
		for (Vertex<Integer> vertex : result.get(0)) // go over first set of vertex
		{
			s.add((int)vertex.getId());	//add to source arrayList 
		}
		
//    System.out.println("------------------------------------------------------------------------------------------------------------------");
//    System.out.println("Here are the size of all the connected component in the graph");
//    //print the result
//    result.forEach(set -> {
//    	System.out.println("sizeof :"+ set.size());
//       // set.forEach(v -> System.out.print(v.getId() + " "));
//        System.out.println();
//    });
//    System.out.println("------------------------------------------------------------------------------------------------------------------");
		return s;
	}
	
	
	
	
	
	
	
	
	/**input original graph and set of vertexes and return (create) a smaller graph from those set of vertexes */

	/*public static Graph<Integer> copyGraph(LinkedList setOfVertex,Graph<Integer> oldGraph) {
		Graph<Integer> newGraph = new Graph<>(true);
		Node n = setOfVertex.head;
		while(n!=null)
		{
			if(oldGraph.getAllVertex().contains(oldGraph.getVertex((int)n.var))){
				newGraph.addSingleVertex(n.var);
			}
			for(Edge<Integer> e : oldGraph.getAllEdges()) {
				
				if( n.var==e.getVertex1().getId() && setOfVertex.contains((int)e.getVertex2().getId())) {
					newGraph.addEdge(n.var, e.getVertex2().getId(),e.getWeight());
					//System.out.println("v1: "+v.getId()+" v2: "+e.getVertex2().getId());
				}

			}
			n=n.next;
		}

		//System.out.println(newGraph.getAllEdges().toString());

		return newGraph;
	} 
*/






}
