package tests.objects;

import org.eclipse.swt.graphics.Image;

import rsg.objects.GameItem;
import rsg.objects.GameObject;
import rsg.objects.GameScenery;
import rsg.objects.OurDisplay;
import rsg.objects.Tile;
import junit.framework.TestCase;

public class TestTile extends TestCase
{
	private Tile tile;
	
	GameObject obj1;
	GameObject obj2;
	GameObject obj3;
	GameObject obj4;
	GameObject obj5;
	GameObject obj6;
	GameObject obj7;
	
	public TestTile(String arg0) 
	{
		super(arg0);
	}
	
	public void setUp()
	{
		tile = new Tile();
		
		obj1 = new GameScenery("A", "Test1", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/sand01.png"), true, "Test item 1", null,  false);
		obj2 = new GameScenery("B", "Test2", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/sand01.png"), true, "Test item 2", null,  false);
		obj3 = new GameScenery("C", "Test3", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/sand01.png"), false, "Test item 3", null,  false);
		obj4 = new GameScenery("D", "Test4", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/sand01.png"), false, "Test item 4", null,  false);
		obj5 = new GameItem("E", "Test5", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/sand01.png"), true, 5, "Test item 5");
		obj6 = new GameItem("F", "Test6", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/sand01.png"), true, 6, "Test item 6");
		obj7 = new GameItem("G", "Test7", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/sand01.png"), true, 7, "Test item 7");
	}
	
	public void reSetup()
	{
		tile = new Tile();
	}
	
	public void testGetList()
	{
		System.out.println("Starting Tile getList testing...");
		
		reSetup();
		assertNotNull(tile.getList());
		assertEquals(0, tile.getList().size());
		
		//Normal Cases
		tile.addGameObject(obj1);
		assertEquals(1, tile.getList().size());
		
		tile.addGameObject(obj2);
		assertEquals(2, tile.getList().size());
		
		tile.addGameObject(obj3);
		assertEquals(3, tile.getList().size());
		
		tile.addGameObject(obj4);
		assertEquals(4, tile.getList().size());
		
		tile.removeLast();
		assertEquals(3, tile.getList().size());
		
		tile.removeLast();
		assertEquals(2, tile.getList().size());
		
		tile.removeLast();
		assertEquals(1, tile.getList().size());
		
		tile.removeLast();
		assertEquals(0, tile.getList().size());
		
		//empty list
		tile.removeLast();
		assertEquals(0, tile.getList().size());
		
		System.out.println("Successfully finishsed tile list testing.\n");
	}
	
	public void testIsTraversable()
	{
		System.out.println("Starting Tile isTraversable testing...");
		
		reSetup();
		assertNotNull(tile.getList());
		assertEquals(0, tile.getList().size());
		
		//empty tile
		assertTrue(tile.isTraversable());
		
		//Normal Cases
		tile.addGameObject(obj1);
		assertTrue(tile.isTraversable());
		
		tile.addGameObject(obj2);
		assertTrue(tile.isTraversable());
		
		tile.addGameObject(obj3);
		assertFalse(tile.isTraversable());
		
		tile.addGameObject(obj4);
		assertFalse(tile.isTraversable());
		
		tile.removeLast();
		assertFalse(tile.isTraversable());
		
		tile.removeLast();
		assertTrue(tile.isTraversable());
		
		tile.removeLast();
		assertTrue(tile.isTraversable());
		
		tile.removeLast();
		assertTrue(tile.isTraversable());
		
		System.out.println("Successfully finishsed isTraversable testing.\n");
	}
	
	public void testGetInteractionMessage()
	{
		System.out.println("Starting Tile InteractionMessage testing...");
		
		reSetup();
		assertNotNull(tile.getList());
		assertEquals(0, tile.getList().size());
		
		//empty tile
		assertTrue(tile.isTraversable());
		
		//Normal Cases
		tile.addGameObject(obj1);
		assertEquals(obj1.getInteractionMessage(), tile.getInteractionMessage());
		
		tile.addGameObject(obj2);
		assertEquals(obj2.getInteractionMessage(), tile.getInteractionMessage());
		
		tile.addGameObject(obj3);
		assertEquals(obj3.getInteractionMessage(), tile.getInteractionMessage());
		
		tile.addGameObject(obj4);
		assertEquals(obj4.getInteractionMessage(), tile.getInteractionMessage());
		
		tile.removeLast();
		assertEquals(obj3.getInteractionMessage(), tile.getInteractionMessage());
		
		tile.removeLast();
		assertEquals(obj2.getInteractionMessage(), tile.getInteractionMessage());
		
		tile.removeLast();
		assertEquals(obj1.getInteractionMessage(), tile.getInteractionMessage());
		
		tile.removeLast();
		assertEquals(null, tile.getInteractionMessage());
		
		System.out.println("Successfully finishsed InteractionMessage testing.\n");
	}
	
	
	public void testList()
	{
		System.out.println("Starting Tile list testing...");
		reSetup();
		assertNotNull(tile.getList());
		System.out.println("Successfully finishsed tile list testing.\n");
	}
}