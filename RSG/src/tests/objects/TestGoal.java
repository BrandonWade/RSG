package tests.objects;

import rsg.objects.Goal;
import junit.framework.TestCase;

public class TestGoal extends TestCase {

	private Goal goal;
	
	public TestGoal(String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{
		goal = new Goal(0, "Initial description","CODE","Completed message","Wrong item pickup message");
	}
	
	public void testAttributes()
	{
		System.out.println("Starting attribute testing...");
		
		assertEquals(goal.getNum(), 0);
		
		assertEquals(goal.getCode(), "CODE");
		
		assertEquals(goal.getDescription(), "Initial description");
		
		System.out.println("Successfully finished attribute testing.\n");
	}
}
