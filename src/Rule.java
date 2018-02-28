
import java.util.LinkedList;

public class Rule 
{
	public LinkedList<Integer> body;
	public LinkedList<Integer> head;
	public int rule_number;
	
	public Rule(int ruleNum)
	{
		body = new LinkedList<>();
		head = new LinkedList<>();
		this.rule_number = ruleNum;
	}
	
	protected void addToBody(int num)
	{
		body.add(num);
	}
	protected void addToHead(int num)
	{
		head.add(num);
	}
}
