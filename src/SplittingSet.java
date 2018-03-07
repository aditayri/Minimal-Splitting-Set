import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;


public class SplittingSet extends Graph<Integer> 
{
	DataStructure DS;
	int num_of_rules;
	
	public SplittingSet() 
	{
		super(true);
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		SplittingSet s = new SplittingSet();
		s.readfile();
	//	s.DS.printHashTable();
		
		//init the graph
		Graph<Integer> g =initGraph(s.DS, s.num_of_rules);
		
		//find super graph
		SuperGraph sg = new SuperGraph(g);
		
		//find all sources of super graph
		List<Set<Vertex<Integer>>> allSources = sg.findAllRoots();//each set is a source
		System.out.println("all sources: " + allSources);
		
		//init priority queue with all sources the smallest is first
		PriorityQueue<Set<Vertex<Integer>>> pq=new PriorityQueue<>(Comparator.comparing(Set::size));
		for (Set<Vertex<Integer>> set : allSources) 
			pq.add(set);		
		
		
		//Start the algorithm
		s.MSS(g ,sg ,pq);
		
	}
	
	/**minimal splitting set algorithm*/
	public void MSS(Graph<Integer> g , SuperGraph sg , PriorityQueue<Set<Vertex<Integer>>> pq)
	{	
		DefaultHashMap<Set<Vertex<Integer>>, Boolean> foundTree = new DefaultHashMap<>(false);//if we found tree of rule

		do
		{
			Set<Vertex<Integer>> S= pq.poll();
		//	System.out.println("we pulled from queue " + S);
			int ruleNumber = isSplittingSet(S);
			if(ruleNumber==-1)//we found a splitting set
			{
				System.out.println("We found splitting set: " + S);
				break;
			}
			//System.out.println("rule num: " +ruleNumber);
			Set<Vertex<Integer>> S2 = new HashSet<>();
			if(!foundTree.get(S))
			{
			//	System.out.println("Not found tree for S");
				S2 = treeOfRule(g,sg , S, ruleNumber);//tree(r) U S
				foundTree.put(S, true);
				//System.out.println("add to queue " + S2);
				pq.add(S2);
			}
			else
			{
				//System.out.println("already found tree for S");
			}
			
		}while(true);
		
	}
	public  Set<Vertex<Integer>> treeOfRule(Graph<Integer> g ,SuperGraph sg ,Set<Vertex<Integer>> S, int ruleNumber)//tree(r) U S
	{
		List<Set<Vertex<Integer>>> treeOfR = new ArrayList<>();
		for(int var : DS.T[ruleNumber].body)
		{
			//System.out.println("in body");
			Vertex<Integer> v = g.getVertex(var);
			//System.out.println("vertex is : " + v );
			sg.treeOfVertex(treeOfR, sg.ver_in_sg(v));
		}
		for(int var : DS.T[ruleNumber].head)
		{
			//System.out.println("in head");
			Vertex<Integer> v = g.getVertex(var);
		//	System.out.println("vertex is : " + v );
			sg.treeOfVertex(treeOfR, sg.ver_in_sg(v));
		}
		
		Set<Vertex<Integer>> toReturn = new HashSet<>();
		for (Set<Vertex<Integer>> set : treeOfR)
		{
			for(Vertex<Integer> ver : set)
			{
				toReturn.add(ver);
			}
		}
		for(Vertex<Integer> ver : S)
		{
			if(!toReturn.contains(ver))
				toReturn.add(ver);
		}
		return toReturn;
		
	}
	
	/**Check if the list of sets is a splitting set, if not- returns the rule number*/
	public int isSplittingSet(Set<Vertex<Integer>> SS)
	{
		for (Rule r :DS.T)
			for (int var : r.head) 
				if(varInSS(var, SS)&&!ruleInSS(r, SS))
					return r.rule_number;				
		return -1;
	}
	
	
	/**Check if all variable in rule r are in the Splitting Set*/
	private boolean ruleInSS(Rule r , Set<Vertex<Integer>> SS)
	{
		for (int hvar :r.head)//head of the rule 
			if(!varInSS(hvar,SS))
				return false;
		
		for (int  bvar :r.body)//body of the rule
			if(!varInSS(bvar,SS))
				return false;
		
		return true;
	}
	
	/**Check if variable is in the Splitting Set*/
	private boolean varInSS(int var ,Set<Vertex<Integer>> SS)
	{
		for (Vertex<Integer> vertex : SS) 
			if(vertex.id==var)
				return true;
		
		return false;
	}
	
	
	public void readfile()
	{
		Scanner sc;
		int var ;
		int index = 0;
		int numOfRules;

		try 
		{

			String Path = ".\\CnfFile.txt" ;
			sc = new Scanner(new File(Path));//read file
			numOfRules = sc.nextInt();
			num_of_rules = numOfRules;
			DS = new DataStructure(numOfRules);
			while (sc.hasNextLine()) 
			{
				var = sc.nextInt();
				if(var!=0)
					DS.addRule(index, var);
				else
					index++;
			}
			System.out.println("File was read successfully");
		}catch (FileNotFoundException ex)
		{
			// TODO Auto-generated catch block
			//ex.printStackTrace();
			System.out.println("Error on reading the file");


		}

	}

	
	

}
