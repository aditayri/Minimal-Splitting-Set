/***
 * @author Adi Tayri
 * @date Apr 2018*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;



public class Main extends Graph<Integer>
{
	
	public Main() {
		super(true);
		// TODO Auto-generated constructor stub
	}

	DataStructure DS ;
	int num_of_rules;

	public static void main(String[] args) throws IOException 
	{
		Main m = new Main();
		//String path =args[0];
		String path =".//CnfFile.txt";
//		m.readfile(path);
//		Graph<Integer> g =initGraph(m.DS, m.num_of_rules);
//		SplittingSet s = new SplittingSet(g, m.DS);
//		System.out.print(s.getSize());
		
		
		/***run time checking*/
		long startTime,endTime,totalTime;//in mili sec
		
		startTime = System.currentTimeMillis();
		 m.readfile(path);
		 Graph<Integer> g =initGraph(m.DS, m.num_of_rules);
		 SplittingSet s = new SplittingSet(g, m.DS);
		 endTime   = System.currentTimeMillis();
		 totalTime = endTime - startTime;
		 System.out.print(totalTime);
		
	}
	
	public void readfile(String path)
	{
		Scanner sc;
		int var ;
		int index = 0;
		int numOfRules=0;

		try 
		{

			//String path = ".//CnfFile.txt" ;
			sc = new Scanner(new File(path));//read file
			if(sc.hasNext())
				numOfRules = sc.nextInt();
			this.num_of_rules=numOfRules;
			DS = new DataStructure(numOfRules);
			while (sc.hasNext()) 
			{
				var = sc.nextInt();
				if(var!=0)
					DS.addRule(index, var);
				else
					index++;
			}
			//System.out.println("File was read successfully");
		}catch (FileNotFoundException ex)
		{
			//ex.printStackTrace();
			//System.out.println("Error on reading the file");
		}

	}
	
	
}
