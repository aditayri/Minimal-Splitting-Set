import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
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
		List<Set<Vertex<Integer>>> allSources = sg.findAllRoots();
		System.out.println(allSources);
       /* Comparator<String> comparator = new StringLengthComparator();
		PriorityQueue<String> pq=new PriorityQueue<String>();
		pq.add("a");
		pq.add("ab");
		pq.add("a");
		while (pq.size() != 0)
        {
            System.out.println(pq.remove());
        }
        */
		SortedSet<Set<Vertex<Integer>>> sor = new TreeSet<>(Comparator.comparing(Set::size));
		PriorityQueue<Set<Vertex<Integer>>> pq=new PriorityQueue<>(Comparator.comparing(Set::size));
		for (Set<Vertex<Integer>> set : allSources) 
		{
			pq.add(set);
			
		}
		while (pq.size() != 0)
        {
            System.out.println(pq.remove());
        }
		//System.out.println(sor);		
		
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

	
	public class StringLengthComparator implements Comparator<String>
	{
	    @Override
	    public int compare(String x, String y)
	    {
	        // Assume neither string is null. Real code should
	        // probably be more robust
	        // You could also just return x.length() - y.length(),
	        // which would be more efficient.
	        if (x.length() < y.length())
	        {
	            return -1;
	        }
	        if (x.length() > y.length())
	        {
	            return 1;
	        }
	        return 0;
	    }
	}
	
	

}
