import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
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
		s.MSS();
		
		
		

	}
	
	/**minimal splitting set*/
	public void MSS()
	{
		Graph<Integer> g =initGraph(DS, num_of_rules);
		SuperGraph sg = new SuperGraph(g);
		List<Set<Vertex<Integer>>> allSources = sg.findAllRoots();//each set is a source
		System.out.println(allSources);
    
		PriorityQueue<Set<Vertex<Integer>>> pq=new PriorityQueue<>(Comparator.comparing(Set::size));
		for (Set<Vertex<Integer>> set : allSources) 
		{
			pq.add(set);
			
		}
		/*while (pq.size() != 0)
        {
            System.out.println(pq.remove());
        }*/
		List<Set<Vertex<Integer>>> SplittingSet = new ArrayList<>();
		do
		{
			SplittingSet.add(pq.poll());
			int ruleNumber =isSplittingSet(SplittingSet);
			if(ruleNumber==-1)//we found a splitting set
			{
				break;
			}
			calc(SplittingSet, ruleNumber);
			
		}while(true);
			
		
	}
	public void calc(List<Set<Vertex<Integer>>> SS, int ruleNumber)//s' = s U tree(r)
	{
		
	}
	
	/**Check if the list of sets is a splitting set, if not- returns the rule number*/
	public int isSplittingSet(List<Set<Vertex<Integer>>> SS)
	{
		for (Rule r :DS.T)
			for (int var : r.head) 
				if(varInSS(var, SS)&&!ruleInSS(r, SS))
					return r.rule_number;				
		return -1;
	}
	
	
	/**Check if all variable in rule r are in the Splitting Set*/
	private boolean ruleInSS(Rule r , List<Set<Vertex<Integer>>> SS)
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
	private boolean varInSS(int var , List<Set<Vertex<Integer>>> SS)
	{
		for (Set<Vertex<Integer>> set : SS) 
			for (Vertex<Integer> vertex : set) 
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
