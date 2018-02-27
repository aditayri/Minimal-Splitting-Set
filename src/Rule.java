
import java.util.LinkedList;

public class Rule 
{
	public LinkedList<Integer> body;
	public LinkedList<Integer> head;
	
	public Rule()
	{
		body = new LinkedList<>();
		head = new LinkedList<>();
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
