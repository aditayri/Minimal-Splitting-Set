

import java.util.ArrayList;

public class Clause
{
	ArrayList<String> literals;
	Clause()
	{
		this.literals = new ArrayList<String>();
	}
	void addLiteral(String literal)
	{
		literals.add(literal);
	}
	String printClause()
	{
		String clause = "[";
		boolean first = true;
		for(String l : literals)
		{
			if(first)
			{
				clause += l;
				first = false;
			}
			else
			{
				clause += " || "+l;
			}
		}
		return clause+"]";
	}
}
