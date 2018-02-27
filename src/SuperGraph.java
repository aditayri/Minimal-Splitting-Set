
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SuperGraph {
	
	//private Graph<Integer> graph;
	private Graph<Integer> super_graph;
	public SuperGraph(Graph<Integer> graph) {
		super_graph = new Graph<>(true);
		if(graph==null) {
			System.out.println("graph is empty!!!!");
			return;
		}
		SCC scc = new SCC();
		List<Set<Vertex<Integer>>> CClist = scc.scc(graph);
		///check if cclist ==null
		int id =1;
		for(Set<Vertex<Integer>> set: CClist) 
		{
			super_graph.addSingleVertex(id);
			super_graph.getVertex(id).setCCList(set);
			id++;
		}
		int x,y;
		for(Edge<Integer> edge : graph.getAllEdges()) {
			x=findIdInVertexCC(super_graph,(int) edge.getVertex1().getId());
			y=findIdInVertexCC(super_graph,(int) edge.getVertex2().getId());
//			if(findIdInVertexCC(graph,(int) edge.getVertex1().getId())!=findIdInVertexCC(graph,(int)edge.getVertex2().getId())&&findIdInVertexCC(graph,(int) edge.getVertex1().getId())!=-1) {
//				super_graph.addEdge(id1, id2);
//			}
			if( -1==y || x==-1) {
				System.out.println("Cannot find id of vertrx");
			}else if(x!=y) {
				super_graph.addEdge(x, y);
			}
			
			
		}
		
	}	
//	@Override
//	public String toString() {
//		
//		
//		
//	}
	
	
	
    /// find vertex of original graph in super graph, return id of where in superGraph it was found (id of superGraph vertex) -1 if not found  
	public int findIdInVertexCC(Graph<Integer> graph,int id) {
		if(graph==null)
			return -1;
		
		for(Vertex<Integer> v : graph.getAllVertex()) {
			for(Vertex<Integer> vertexInCC: v.getCCList() ) {
				if(vertexInCC.getId()==id) {
					return (int) v.getId();
				}
				
			}
			
		}	
		return -1;
	}
	
//	public void setGraph(Graph<Integer> graph) {
//		this.graph=graph;
//	}
	public Graph<Integer> getSuperGraph() {
		return this.super_graph;
	}
//	public Graph<Integer> getGraph() {
//		return this.graph;
//	}
	
	public void printGraph() {
		System.out.println(super_graph);
		System.out.println();
		for(Vertex<Integer> v : super_graph.getAllVertex()) {
			System.out.print("Vertex: "+v.getId());
			System.out.print(": Vertex of cc ");
			for(Vertex<Integer> vertexInCC: v.getCCList() ) {
				System.out.print(vertexInCC.getId()+" ");
				
			}
			System.out.println();
		}	
	}
	
	public List<Set<Vertex<Integer>>> findAllRoots()
	{
		List<Set<Vertex<Integer>>> a = new ArrayList<>();
		
		for(Vertex<Integer> v : super_graph.getAllVertex()) 
		{
			//System.out.println("check if " + v +" is root");
			if(isRoot(v))
			{
				a.add(v.getCCList());
			}
			
		}	
		return a;
	}
	private boolean isRoot(Vertex<Integer> v)
	{
		for(Vertex<Integer> ver : super_graph.getAllVertex()) 
		{
			//System.out.println("check if has " + ver + "-->" + v);
			if(super_graph.hasEdge(ver, v))
			{
			//	System.out.println("yes");
				return false;
			}
		}	
		return true;
	}
	
	/*public static void main(String args[]){
        Graph<Integer> g = new Graph<>(true);
        g.addEdge(1, 2);
        g.addEdge(2, 1);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
     //   g.addSingleVertex(5);
       // System.out.println(g);
       
       // System.out.println(g.hasEdge(1, 2);
      
        SuperGraph sg = new SuperGraph(g);
       
       
        System.out.println(sg.findAllRoots());
    }*/
	
	
	
	
	
	
	
	
	
}
