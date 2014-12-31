package tests.objects;

import junit.framework.TestCase;

import org.eclipse.swt.graphics.Image;

import rsg.objects.OurDisplay;
import rsg.objects.Player;
import rsg.persistence.Services;
import tests.persistence.DataAccessStub;

public class TestGameCharacter extends TestCase
{
	private Player character;
	
	public TestGameCharacter(String arg0)
	{
		super(arg0);
	}

	public void setUp()
	{		
		Services.createDataAccess(new DataAccessStub("STUB"));
		character = new Player("A", "Test", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/male04.png"), 30, 50, 3, 100,0,0);
	}
	
	public void tearDown()
	{
		Services.closeDataAccess();
	}
	
	public void reSetUp()
	{		
		character = new Player("A", "Test", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/male04.png"), 30, 50, 3, 100,0,0);
	}
	
	public void testChangeHealth()
	{
		System.out.println("Starting health change testing...");
		//test getter method
		assertEquals(30, character.getHealth());
		
		//value over maxHealth
		reSetUp();
		assertEquals(50, character.setHealth(120));
		
		//boundary just over maxHealth
		reSetUp();
		assertEquals(50, character.setHealth(51));
		
		//maxHealth value
		reSetUp();
		assertEquals(50, character.setHealth(50));
		
		//average value
		reSetUp();
		assertEquals(20, character.setHealth(20));
		
		//boundary value
		assertEquals(1, character.setHealth(1));
		
		//negative value
		assertEquals(0, character.setHealth(-50));
		
		//boundary negative value
		assertEquals(0, character.setHealth(-1));
		
		//zero value
		assertEquals(0, character.setHealth(0));
		
		//set maxHealth
		assertEquals(50, character.setMaxHealth());
		
		System.out.println("Successfully completed health change testing.\n");
	}
	
	public void testIncreaseHealth()
	{
		System.out.println("Starting health increase testing...");
		
		//normal increase
		assertEquals(40, character.increaseHealth(10));
		
		//boundary increase
		reSetUp();
		assertEquals(50, character.increaseHealth(20));
		
		//over maximum increase
		reSetUp();
		assertEquals(50, character.increaseHealth(30));
		
		//zero increase
		reSetUp();
		assertEquals(30, character.increaseHealth(0));
		
		//very small increase
		reSetUp();
		assertEquals(31, character.increaseHealth(1));
		
		//negative "increase" - shouldn't work
		reSetUp();
		assertEquals(30, character.increaseHealth(-10));
		
		System.out.println("Successfully finished health increase testing.\n");
	}
	
	public void testDecreaseHealth()
	{
		System.out.println("Begin health decrease testing...");
		
		//normal decrease
		assertEquals(10, character.decreaseHealth(20));
		
		//boundary decrease
		assertEquals(0, character.decreaseHealth(30));
		
		//negative value - shouldn't work
		character.setHealth(30);
		assertEquals(30, character.decreaseHealth(-10));
		
		//zero decrease
		character.setHealth(30);
		assertEquals(30, character.decreaseHealth(0));
		
		//over minimum decrease
		character.setHealth(30);
		assertEquals(0, character.decreaseHealth(30));
		
		//very small decrease
		character.setHealth(30);
		assertEquals(29, character.decreaseHealth(1));
		
		System.out.println("Successfully finished health decrease testing.\n");
	}
	
	public void testDifferentLocationVals()
	{
		System.out.println("Begin different location value testing...");
		
		//different values
		character.setX(10);
		character.setY(20);
		
		assertEquals(10, character.getX());
		assertEquals(20, character.getY());
		
		System.out.println("Successfully finished different location value testing.\n");
	}

	public void testNegativeLocation()
	{
		System.out.println("Begin negative location values testing...");
		
		//negative values
		character.setX(-10);
		character.setY(-10);
		
		assertEquals(-10, character.getX());
		assertEquals(-10, character.getY());
		
		System.out.println("Successfully finished negative location value testing.\n");
	}
	
	public void testTypicalLocation()
	{	
		System.out.println("Begin typical location testing...");
		
		//regular values
		character.setX(20);
		character.setY(20);
		
		assertEquals(20, character.getX());
		assertEquals(20, character.getY());
		
		System.out.println("Successfully finished typical location testing.\n");
	}
	
	public void testBoundaryLocation()
	{
		System.out.println("Begin boundary location testing...");
		
		//boundary values
		character.setX(1);
		character.setY(1);
		
		assertEquals(1, character.getX());
		assertEquals(1, character.getY());
		
		System.out.println("Successfully finished boundary location testing.\n");
	}
}