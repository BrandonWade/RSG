package tests.objects;

import org.eclipse.swt.graphics.Image;

import rsg.objects.GameItem;
import rsg.objects.OurDisplay;
import rsg.persistence.Services;
import tests.persistence.DataAccessStub;
import junit.framework.TestCase;

public class TestGameItem extends TestCase
{
	private GameItem item;
	
	public TestGameItem(String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		item = new GameItem("A", "Test", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/male04.png"), true, 20, "Test message.");
	}
	
	public void tearDown()
	{
		Services.closeDataAccess();
	}
	
	public void testInitialValue()
	{
		System.out.println("Begin initial values testing...");
		
		assertEquals(20, item.getValue());
		
		//test just get to make sure it returns the same value
		assertEquals(20, item.setValue(20));
		
		System.out.println("Successfully finished initial value testing.\n");
	}
	
	public void testResetNegativeValue()
	{
		System.out.println("Begin reset negative value testing...");
		
		//negative value shouldn't work
		assertEquals(20, item.setValue(-10));
		
		System.out.println("Successfully finished reset negative value testing.\n");
	}
	
	public void testResetTypicalValue()
	{
		System.out.println("Begin reset typical value tests...");
		
		//test set with get
		item.setValue(30);
		assertEquals(30, item.getValue());
		
		//really big value
		assertEquals(250, item.setValue(250));
		
		System.out.println("Successfully finished reset typical value tests.\n");
	}
	
	public void testZeroValue()
	{
		System.out.println("Begin zero value tests...");
		
		//zero value
		assertEquals(0, item.setValue(0));
		
		System.out.println("Successfully finished zero value tests.\n");
	}
	
	public void testBoundaryValue()
	{	
		System.out.println("Begin boundary value testing...");
		
		//boundary value
		assertEquals(1, item.setValue(1));
		
		System.out.println("Successfully finished boundary value testing.\n");
	}
}