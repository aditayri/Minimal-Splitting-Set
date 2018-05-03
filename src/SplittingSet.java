/***
 * @author Adi Tayri
 * @date Mar 2018*/
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;


public class SplittingSet
{
	private Set<Vertex<Integer>> MinimalSplittingSet=null;
	
	public SplittingSet(Graph<Integer> g , DataStructure DS)//constructor create the minimal splitting set
	{
		//g =initGraph(DS, num_of_rules);
		
		//find super graph
		SuperGraph sg = new SuperGraph(g);
				
		//find all sources of super graph
		List<Set<Vertex<Integer>>> allSources = sg.findAllRoots();//each set is a source
		//System.out.println("all sources: " + allSources);
				
		//init priority queue with all sources the smallest is first
		PriorityQueue<Set<Vertex<Integer>>> pq=new PriorityQueue<>(Comparator.comparing(Set::size));
		for (Set<Vertex<Integer>> set : allSources) 
			pq.add(set);	
		
		DefaultHashMap<Integer, Boolean> foundTree = new DefaultHashMap<>(false);//if we found tree of rule
		Map<Integer, Set<Vertex<Integer>>> trees = new HashMap<>();
		do
		{
			Set<Vertex<Integer>> S= pq.poll();
			//System.out.println("we pulled from queue " + S);
			int ruleNumber = isSplittingSet(S,DS);
			if(ruleNumber==-1)//we found a splitting set
			{
				this.MinimalSplittingSet = S;
				return ;
			}
			//System.out.println("rule num: " +ruleNumber);
			Set<Vertex<Integer>> S2 = new HashSet<>();
			if(!foundTree.get(ruleNumber))
			{
				//System.out.println("Not found tree for rule " + ruleNumber);
				S2 = treeOfRule(g,sg , ruleNumber, DS);//tree(r) 
				//System.out.println("found tree from function : " + S2);
				trees.put(ruleNumber, S2);
				//System.out.println("put in trees hashmap");
				uniteS(S2, S);
				//System.out.println("unite with S : " + S2);
				foundTree.put(ruleNumber, true);
				//System.out.println("add to queue " + S2);
				pq.add(S2);
			}
			else//already found tree for this rule
			{
				//System.out.println("already have tree in hash map");
				S2=trees.get(ruleNumber);
				//System.out.println("get from hashmap " + S2);
				uniteS(S2, S);
				pq.add(S2);
					
				//System.out.println("after unite with S : " + S2);
			}
			
		}while(true);
	}
	
	public Set<Vertex<Integer>> getMSS()
	{
		return this.MinimalSplittingSet;
	}
	public int getSize()
	{
		return this.MinimalSplittingSet.size();
	}
	


	
	private  Set<Vertex<Integer>> treeOfRule(Graph<Integer> g ,SuperGraph sg , int ruleNumber ,DataStructure DS)//tree(r) U S
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
		
		return toReturn;
		
	}
	
	private void uniteS(Set<Vertex<Integer>> dest,Set<Vertex<Integer>> src)
	{
		for(Vertex<Integer> ver : src)
		{
			if(!dest.contains(ver))
				dest.add(ver);
		}
	}
	
	/**Check if the list of sets is a splitting set, if not- returns the rule number*/
	private int isSplittingSet(Set<Vertex<Integer>> SS, DataStructure DS)
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
	
	
	
	
	
	
	
	
	

}












/***BEFORE**/
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.Set;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.PriorityQueue;
//
//
//public class SplittingSet extends Graph<Integer> 
//{
//	DataStructure DS;
//	int num_of_rules;
//	
//	public SplittingSet() 
//	{
//		super(true);
//	}
//	
//	public static void main(String[] args) throws IOException 
//	{
//		// TODO Auto-generated method stub
//		SplittingSet s = new SplittingSet();
//		//System.out.print(s.DP_CALLS(args[0]));
//		
//		
//		
//		
//		
//		String path=args[0];
//		s.readfile(path);	
//		//Start the algorithm
//		Set<Vertex<Integer>> MinimalSplittingSet = new HashSet<>();
//		MinimalSplittingSet = s.MSS();
//		//System.out.println("We found minimal splitting set: " + MinimalSplittingSet);	
//		System.out.print(MinimalSplittingSet.size());
//	}
//	
//	/**minimal splitting set algorithm*/
//	public Set<Vertex<Integer>> MSS()
//	{	
//		//init the graph
//		Graph<Integer> g =initGraph(DS, num_of_rules);
//				
//		//find super graph
//		SuperGraph sg = new SuperGraph(g);
//				
//		//find all sources of super graph
//		List<Set<Vertex<Integer>>> allSources = sg.findAllRoots();//each set is a source
//		//System.out.println("all sources: " + allSources);
//				
//		//init priority queue with all sources the smallest is first
//		PriorityQueue<Set<Vertex<Integer>>> pq=new PriorityQueue<>(Comparator.comparing(Set::size));
//		for (Set<Vertex<Integer>> set : allSources) 
//			pq.add(set);	
//		
//		DefaultHashMap<Integer, Boolean> foundTree = new DefaultHashMap<>(false);//if we found tree of rule
//		Map<Integer, Set<Vertex<Integer>>> trees = new HashMap<>();
//		do
//		{
//			Set<Vertex<Integer>> S= pq.poll();
//			//System.out.println("we pulled from queue " + S);
//			int ruleNumber = isSplittingSet(S);
//			if(ruleNumber==-1)//we found a splitting set
//			{
//				return S;
//			}
//			//System.out.println("rule num: " +ruleNumber);
//			Set<Vertex<Integer>> S2 = new HashSet<>();
//			if(!foundTree.get(ruleNumber))
//			{
//				//System.out.println("Not found tree for rule " + ruleNumber);
//				S2 = treeOfRule(g,sg , ruleNumber);//tree(r) 
//				//System.out.println("found tree from function : " + S2);
//				trees.put(ruleNumber, S2);
//				//System.out.println("put in trees hashmap");
//				uniteS(S2, S);
//				//System.out.println("unite with S : " + S2);
//				foundTree.put(ruleNumber, true);
//				//System.out.println("add to queue " + S2);
//				pq.add(S2);
//			}
//			else//already found tree for this rule
//			{
//				//System.out.println("already have tree in hash map");
//				S2=trees.get(ruleNumber);
//				//System.out.println("get from hashmap " + S2);
//				uniteS(S2, S);
//				pq.add(S2);
//					
//				//System.out.println("after unite with S : " + S2);
//			}
//			
//		}while(true);
//		
//	}
//	public  Set<Vertex<Integer>> treeOfRule(Graph<Integer> g ,SuperGraph sg , int ruleNumber)//tree(r) U S
//	{
//		List<Set<Vertex<Integer>>> treeOfR = new ArrayList<>();
//		for(int var : DS.T[ruleNumber].body)
//		{
//			//System.out.println("in body");
//			Vertex<Integer> v = g.getVertex(var);
//			//System.out.println("vertex is : " + v );
//			sg.treeOfVertex(treeOfR, sg.ver_in_sg(v));
//		}
//		for(int var : DS.T[ruleNumber].head)
//		{
//			//System.out.println("in head");
//			Vertex<Integer> v = g.getVertex(var);
//		//	System.out.println("vertex is : " + v );
//			sg.treeOfVertex(treeOfR, sg.ver_in_sg(v));
//		}
//		
//		Set<Vertex<Integer>> toReturn = new HashSet<>();
//		for (Set<Vertex<Integer>> set : treeOfR)
//		{
//			for(Vertex<Integer> ver : set)
//			{
//				toReturn.add(ver);
//			}
//		}
//		
//		return toReturn;
//		
//	}
//	
//	public void uniteS(Set<Vertex<Integer>> dest,Set<Vertex<Integer>> src)
//	{
//		for(Vertex<Integer> ver : src)
//		{
//			if(!dest.contains(ver))
//				dest.add(ver);
//		}
//	}
//	
//	/**Check if the list of sets is a splitting set, if not- returns the rule number*/
//	public int isSplittingSet(Set<Vertex<Integer>> SS)
//	{
//		for (Rule r :DS.T)
//			for (int var : r.head) 
//				if(varInSS(var, SS)&&!ruleInSS(r, SS))
//					return r.rule_number;				
//		return -1;
//	}
//	
//	
//	/**Check if all variable in rule r are in the Splitting Set*/
//	private boolean ruleInSS(Rule r , Set<Vertex<Integer>> SS)
//	{
//		for (int hvar :r.head)//head of the rule 
//			if(!varInSS(hvar,SS))
//				return false;
//		
//		for (int  bvar :r.body)//body of the rule
//			if(!varInSS(bvar,SS))
//				return false;
//		
//		return true;
//	}
//	
//	/**Check if variable is in the Splitting Set*/
//	private boolean varInSS(int var ,Set<Vertex<Integer>> SS)
//	{
//		for (Vertex<Integer> vertex : SS) 
//			if(vertex.id==var)
//				return true;
//		
//		return false;
//	}
//	
//	
//	public void readfile(String path)
//	{
//		Scanner sc;
//		int var ;
//		int index = 0;
//		int numOfRules=0;
//
//		try 
//		{
//			sc = new Scanner(new File(path));//read file
//			if(sc.hasNext())
//				numOfRules = sc.nextInt();
//			num_of_rules = numOfRules;
//			DS = new DataStructure(numOfRules);
//			while (sc.hasNext()) 
//			{
//				var = sc.nextInt();
//				if(var!=0)
//					DS.addRule(index, var);
//				else
//					index++;
//			}
//			sc.close();
//			
//			//System.out.println("File was read successfully");
//		}catch (FileNotFoundException ex)
//		{
//			// TODO Auto-generated catch block
//			//ex.printStackTrace();
//			
//			System.err.println("Error on reading the file");
//			
//		}
//
//	}
//
//	public int DP_CALLS(String path) 
//	{
//		Scanner sc;
//		String var ;
//		int index = 0;
//		int numOfRules=0;
//		ArrayList<Clause> Clauses = new ArrayList<Clause>();
//
//		try 
//		{
//			sc = new Scanner(new File(path));//read file
//			if(sc.hasNext())
//				numOfRules = sc.nextInt();
//			num_of_rules = numOfRules;
//			DS = new DataStructure(numOfRules);
//			Clause clause = new Clause();
//			while (sc.hasNext()) 
//			{
//				var = sc.next();
//				if(!var.equals("0"))
//				{
//					clause.addLiteral(var);
//				}
//				else
//				{
//					Clauses.add(clause);
//					clause= new Clause();
//				}
//			}
//			sc.close();
//			
//			//System.out.println("File was read successfully");
//		}catch (FileNotFoundException ex)
//		{
//			// TODO Auto-generated catch block
//			//ex.printStackTrace();
//			
//			System.err.println("Error on reading the file");
//			
//		}
////		if(DS.DLL(Clauses))
////		{
////			System.out.println("Result: The formula is satisfaible.");
////		}
////		else
////		{
////			System.out.println("Result: The formula is  not satisfiable.");
////		}
//		DS.DLL(Clauses);
//		return DS.dpCalls;
//	}
//	
//	
//	
//	
//	
//	
//
//}