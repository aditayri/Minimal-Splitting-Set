import java.util.ArrayList;
import java.util.HashMap;


public class DataStructure extends DavisPutnamHelper
{
	
	 public Rule[] T ;//collection of rules
	// public Hashtable<Integer, LinkedList<Integer>> varHT ;//in which rules a variable appears
	 static HashMap<String, Boolean> literalMap;// We will store the value of literals in this structure as we go along
	 public int dpCalls;
	 
	 public DataStructure(int numOfRules)
	 {
		 T = new Rule[numOfRules];
		 for (int i = 0; i < T.length; i++) 
		 {
			T[i]= new Rule(i);
		 }
		// varHT= new Hashtable<>();
	    literalMap = new HashMap<String,Boolean>();
	    dpCalls=0;
	 }
	 
	 public void addRule(int index , int variable)
	 {
		 if(variable<0)
		 {
			 variable*=-1;
	    	 T[index].addToBody(variable);
		 }
		 else//variable can't be 0
		 {
			 T[index].addToHead(variable);
		 }
		// addToHashTable(variable, index);
		 
	 }
	 
	 
	 
	public boolean DLL(ArrayList<Clause> Clauses)
	{
		this.dpCalls++;
	    if(Clauses.size() == 0) 
		{
		//	System.out.println("T = {EMPTY}");
				return true;
		}
		//Unitary Propagation
		while(true)
		{	
			String literalToRemove =searchSingleLiteral(Clauses, literalMap);
			if(!literalToRemove.equals("NotFoundYet"))
			{
				//printClauses(Clauses);
				//System.out.println("Performing unitary propagation with: "+literalToRemove);
				//this.counter++;//count how many times we put value in a variable
//				printClauses(Clauses);
//				System.out.println("Performing unitary propagation with: "+literalToRemove);
				removeClauses(literalToRemove,Clauses);
				cutClauses(literalToRemove,Clauses);
				//printClauses(Clauses);
				if(Clauses.size() == 0) 
				{
				//	System.out.println("All clauses removed. Returning true.");
					return true;
				}
				if(hasFalsehood(Clauses)) 
				{
					//System.out.println("Falsehood detected. Returning false.");
					return false;
				}
				else if(hasEmptyClause(Clauses))
				{
					//System.out.println("Empty clause detected. Returning false.");
					return false;
				}
			}
			else
			{
			//	System.out.println("No single literals.");
			//	System.out.println("Cannot perform unitary propagation.");
				break;
			}
		}
		ArrayList<Clause> copy1 = new ArrayList<Clause>();
		ArrayList<Clause> copy2 = new ArrayList<Clause>();
		for(Clause c: Clauses)
		{
			Clause c2 = new Clause();
			for(String s: c.literals)
			{
				c2.addLiteral(s);
			}
			copy1.add(c2);
		}
		for(Clause c: Clauses)
		{
			Clause c2 = new Clause();
			for(String s: c.literals)
			{
				c2.addLiteral(s);
			}
			copy2.add(c2);
		}
		Clause clause1 = new Clause();
		Clause clause2 = new Clause();
		String l1 = pickLiteral(Clauses);//most of time pick a negative literal because the order of a clause (first body then head)
		String l2 = "";
		
		if(l1.startsWith("-"))
		{
			l2 = l1.substring(1);
			clause1.addLiteral(l1);
			clause2.addLiteral(l2);
			copy1.add(clause1);
			copy2.add(clause2);
		}
		else
		{
			l2 = "-"+l1;
			clause1.addLiteral(l2);
			clause2.addLiteral(l1);
			copy1.add(clause1);
			copy2.add(clause2);
		}
		
			
		//Moment of the truth
		//System.out.println("Adding clause: ["+l1+"]");
		if(DLL(copy1) == true)
		{
			return true;
		}
		else
		{
		//	System.out.println("Trying opposite clause: ["+l2+"]");
			return DLL(copy2);
		}
	}
	 	/*public boolean DLL(ArrayList<Clause> Clauses)
		{
			//Unitary Propagation
			while(true)
			{	
				String literalToRemove = searchSingleLiteral(Clauses,literalMap);
				if(!literalToRemove.equals("NotFoundYet"))
				{
					printClauses(Clauses);
					System.out.println("Performing unitary propagation with: "+literalToRemove);
					removeClauses(literalToRemove,Clauses);
					cutClauses(literalToRemove,Clauses);
					printClauses(Clauses);
					if(Clauses.size() == 0) 
					{
						System.out.println("All clauses removed. Returning true.");
						return true;
					}
					if(hasFalsehood(Clauses)) 
					{
						System.out.println("Falsehood detected. Returning false.");
						return false;
					}
					else if(hasEmptyClause(Clauses))
					{
						System.out.println("Empty clause detected. Returning false.");
						return false;
					}
				}
				else
				{
					System.out.println("No single literals.");
					System.out.println("Cannot perform unitary propagation.");
					break;
				}
			}
			ArrayList<Clause> copy1 = new ArrayList<Clause>();
			ArrayList<Clause> copy2 = new ArrayList<Clause>();
			for(Clause c: Clauses)
			{
				Clause c2 = new Clause();
				for(String s: c.literals)
				{
					c2.addLiteral(s);
				}
				copy1.add(c2);
			}
			for(Clause c: Clauses)
			{
				Clause c2 = new Clause();
				for(String s: c.literals)
				{
					c2.addLiteral(s);
				}
				copy2.add(c2);
			}
			Clause clause1 = new Clause();
			Clause clause2 = new Clause();
			String l1 = pickLiteral(Clauses);
			String l2 = "";
			
			if(l1.startsWith("-")) l2 = l1.substring(1);
			else l2 = "-"+l1;
			clause1.addLiteral(l1);
			clause2.addLiteral(l2);
			copy1.add(clause1);
			copy2.add(clause2);
			//Moment of the truth
			System.out.println("Adding clause: ["+l1+"]");
			if(DLL(copy1) == true)
			{
				return true;
			}
			else
			{
				System.out.println("Trying opposite clause: ["+l2+"]");
				return DLL(copy2);
			}
	}*/
	    
	 
	 
	
	 
	/* private void addToHashTable(int var , int ruleIndex) 
	    {
	    	LinkedList<Integer> ls;
	    	if(varHT.containsKey(var))//key exist
	    	{
	    		ls = varHT.get(var);
	    		if(VariableExistInLinkedList(ruleIndex,ls))
	    		{
	    			return;
	    		}
	    		ls.add(ruleIndex);
	    		varHT.put(var,ls); 		
	    	}
	    	else//key does not exist
	    	{
	    		ls = new LinkedList<>();
	    		ls.add(ruleIndex);
	    		varHT.put(var,ls); 	
	    	}
	    	
	    }
	 private boolean VariableExistInLinkedList(int var,LinkedList<Integer> l)
	 { 
		 for (int i = 0; i < l.size(); i++)
		 {
			 if(l.get(i)==var)
			 {
				 return true;
			 }
	     } 
		 return false;
	  }
	 
	 public void printHashTable() 
	    {
	    	 Set<Integer> keys = varHT.keySet();
	    	 for(int key: keys)
	    	 {
	    		 System.out.println("Value of " + key +" is: ");
	    		System.out.println( varHT.get(key).toString());
	    	 }
	    }
	 
	 
	*/
	 
}
