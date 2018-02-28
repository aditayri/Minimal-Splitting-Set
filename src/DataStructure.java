import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;


public class DataStructure 
{
	
	 public Rule[] T ;//collection of rules
	 public Hashtable<Integer, LinkedList<Integer>> varHT ;//in which rules a variable appears
	 
	 
	 public DataStructure(int numOfRules)
	 {
		 T = new Rule[numOfRules];
		 for (int i = 0; i < T.length; i++) 
		 {
			T[i]= new Rule(i);
		 }
		 varHT= new Hashtable<>();
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
		 addToHashTable(variable, index);
		 
	 }
	 
	 private void addToHashTable(int var , int ruleIndex) 
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
	 
	 
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

}
