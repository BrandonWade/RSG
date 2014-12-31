package tests.integration;


import java.util.ArrayList;

import rsg.objects.GameItem;
import rsg.objects.GameObject;
import rsg.objects.GamePlayer;
import rsg.objects.Map;
import rsg.persistence.Services;
import rsg.persistence.World;
import rsg.persistence.WorldInfo;
import rsg.presentation.GuiWindow;
import rsg.processing.GameRunner;
import rsg.processing.KeyCode;
import rsg.processing.MapCreationException;

import junit.framework.TestCase;

public class TestGameRunner extends TestCase
{	
	public TestGameRunner (String arg0) 
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess("OBJS");
		WorldInfo.initializeWorld();
		World.set(1, 1);
		try
		{
			World.initalizeSuperMap();
		} 
		catch (MapCreationException e)
		{
			fail("Map creation should not have failed for this task.");
		}
		GamePlayer.createPlayer("MC01", "GamePlayer", WorldInfo.search("MC01").getImage(), 20, 20, 20, 20,0,0);
		GuiWindow.createGUI();
	}

	public void tearDown()
	{
		GamePlayer.delete();
		Services.closeDataAccess();
		World.destroy();
	}
	
	public void testMovementRegular()
	{
		System.out.println("Started player movement intergration test...");
		
		GamePlayer.setX(12);
		GamePlayer.setY(12);
		
		//move up 
		GameRunner.checkPrevTileMovement(KeyCode.KeyUp);
		assertEquals(12, GamePlayer.getX());
		assertEquals(11, GamePlayer.getY());
		
		
		//move left 
		GameRunner.checkPrevTileMovement(KeyCode.KeyLeft);
		assertEquals(GamePlayer.getX(), 11);
		assertEquals(GamePlayer.getY(), 11);
		
		
		//move right 
		GameRunner.checkPrevTileMovement(KeyCode.KeyRight);
		assertEquals(GamePlayer.getX(), 12);
		assertEquals(GamePlayer.getY(), 11);
	
		//move down
		GameRunner.checkPrevTileMovement(KeyCode.KeyDown);
		assertEquals(GamePlayer.getX(), 12);
		assertEquals(GamePlayer.getY(), 12);
		
		System.out.println("Successfully finished player movement testing.\n");
		
	}
	
	public void testMovementMulti()
	{
		System.out.println("Started intergration test multiple movements...");
		
		GamePlayer.setX(12);
		GamePlayer.setY(12);
	
		//move up 
		GameRunner.checkPrevTileMovement(KeyCode.KeyUp);
		assertEquals(GamePlayer.getX(), 12);
		assertEquals(GamePlayer.getY(), 11);
		
		//move left 
		GameRunner.checkPrevTileMovement(KeyCode.KeyLeft);
		assertEquals(GamePlayer.getX(), 11);
		assertEquals(GamePlayer.getY(), 11);
		
		//move left 
		GameRunner.checkPrevTileMovement(KeyCode.KeyLeft);
		assertEquals(GamePlayer.getX(), 10);
		assertEquals(GamePlayer.getY(), 11);
		
		//move down
		GameRunner.checkPrevTileMovement(KeyCode.KeyDown);
		assertEquals(GamePlayer.getX(), 10);
		assertEquals(GamePlayer.getY(), 12);
		
		//move right 
		GameRunner.checkPrevTileMovement(KeyCode.KeyRight);
		assertEquals(GamePlayer.getX(), 11);
		assertEquals(GamePlayer.getY(), 12);
		
		System.out.println("Successfully finished multiple movements testing.\n");
	}
	
	public void testMovementObstacle() 
	{
		System.out.println("Started movement obstacle intergration test...");
		
		int xVal = 13;
		int yVal = 6;

		//move right into obstacle
		GamePlayer.setX(xVal - 1);
		GamePlayer.setY(yVal);
		GameRunner.checkPrevTileMovement(KeyCode.KeyRight);
		assertEquals(GamePlayer.getX(), xVal - 1);
		assertEquals(GamePlayer.getY(), yVal);
		
		//move left into obstacle
		GamePlayer.setX(xVal + 1);
		GamePlayer.setY(yVal);
		GameRunner.checkPrevTileMovement(KeyCode.KeyLeft);
		assertEquals(GamePlayer.getX(), xVal + 1);
		assertEquals(GamePlayer.getY(), yVal);
		
		//move up into obstacle
		GamePlayer.setX(xVal);
		GamePlayer.setY(yVal + 1);
		GameRunner.checkPrevTileMovement(KeyCode.KeyUp);
		assertEquals(GamePlayer.getX(), xVal);
		assertEquals(GamePlayer.getY(), yVal + 1);
		
		//move down into obstacle
		GamePlayer.setX(xVal);
		GamePlayer.setY(yVal - 1);
		GameRunner.checkPrevTileMovement(KeyCode.KeyDown);
		assertEquals(GamePlayer.getX(), xVal);
		assertEquals(GamePlayer.getY(), yVal - 1);
		
		System.out.println("Successfully finished movement obstacle testing.\n");
	}
	
	public void testMovementWrapRegular()
	{
		System.out.println("Started movement wrap intergration test...");
		
		GamePlayer.setX(0);
		GamePlayer.setY(0);
	
		//move up 
		GameRunner.checkPrevTileMovement(KeyCode.KeyUp);
		assertEquals(GamePlayer.getX(), 0);
		assertEquals(GamePlayer.getY(), Map.getRows() - 1);
		
		//move left 
		GameRunner.checkPrevTileMovement(KeyCode.KeyLeft);
		assertEquals(GamePlayer.getX(), Map.getCols() - 1);
		assertEquals(GamePlayer.getY(), Map.getRows() - 1);
		
		//move down
		GameRunner.checkPrevTileMovement(KeyCode.KeyDown);
		assertEquals(GamePlayer.getX(), Map.getCols() - 1);
		assertEquals(GamePlayer.getY(), 0);
		
		//move right 
		GameRunner.checkPrevTileMovement(KeyCode.KeyRight);
		assertEquals(GamePlayer.getX(), 0);
		assertEquals(GamePlayer.getY(), 0);
		
		System.out.println("Successfully finished movement wrap testing.\n");
	}
	
	public void testNoItemPickup()
	{
		System.out.println("Started no item pickup intergration test...");
		
		Map map;
		ArrayList<GameObject> tileList;
		
		map = World.getCurrentMap();
		
		GamePlayer.setX(13);
		GamePlayer.setY(5);
		GamePlayer.setPlayerDirection(KeyCode.KeyDown);
		
		//test initial value
		assertEquals(GamePlayer.getInventory().size(), 0);
		
		//test no pickup
		tileList = map.getTile(12, 12).getList();
		assertEquals(tileList.size(), 1);
		assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("GR02"));
		GameRunner.processInteraction();
		assertEquals(GamePlayer.getInventory().size(), 0);
		
		System.out.println("Successfully finished no item pickup testing.\n");
	}
	
	public void testSimpleItemPickup()
	{
		System.out.println("Started simple item pickup intergration test...");
		
		Map map;
		ArrayList<GameObject> tileList;
		
		World.set(2, 1);
		map = World.getCurrentMap();
		ArrayList<GameItem> testInventory;
		
		GamePlayer.setX(5);
		GamePlayer.setY(11);
		GamePlayer.setPlayerDirection(KeyCode.KeyUp);
		
		//test initial value
		assertEquals(GamePlayer.getInventory().size(), 0);
		
		//test simple pickup
		tileList = map.getTile(5, 10).getList();
		assertEquals(2, tileList.size());
		assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("BC01"));
		GameRunner.processInteraction();
		assertEquals(GamePlayer.getInventory().size(), 1);
		
		tileList = map.getTile(5, 10).getList();
		assertEquals(tileList.size(), 1);
		assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("GR02"));
		
		//test inventory contents
		testInventory = GamePlayer.getInventory();
		assertEquals(testInventory.get(0), WorldInfo.search("BC01"));
		
		System.out.println("Successfully finished simple item pickup testing.\n");
	}
	
	public void testMultiItemPickup()
	{
		System.out.println("Started Item Pickup intergration test...");
		
		Map map;
		ArrayList<GameItem> testInventory;
		ArrayList<GameObject> tileList;
		World.set(2, 1);
		map = World.getCurrentMap();
		GamePlayer.resetInventory();
		
		//test initial value
		assertEquals(GamePlayer.getInventory().size(), 0);
		
		GamePlayer.setX(5);
		GamePlayer.setY(11);
		GamePlayer.setPlayerDirection(KeyCode.KeyUp);
		
		//test initial value
		assertEquals(GamePlayer.getInventory().size(), 0);
		
		//test simple pickup
		tileList = map.getTile(5, 10).getList();
		assertEquals(2, tileList.size());
		assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("BC01"));
		GameRunner.processInteraction();
		assertEquals(GamePlayer.getInventory().size(), 1);
		tileList = map.getTile(5, 10).getList();
		assertEquals(tileList.size(), 1);
		assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("GR02"));
		
		//Pick up second item
		World.set(0, 2);
		map = World.getCurrentMap();
		GamePlayer.setX(5);
		GamePlayer.setY(8);
		GamePlayer.setPlayerDirection(KeyCode.KeyDown);
		tileList = map.getTile(5, 9).getList();
		assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("MN01"));
		GameRunner.processInteraction();
		assertEquals(GamePlayer.getInventory().size(), 2);
		
		//Third Item
		World.set(2, 0);
		map = World.getCurrentMap();
		GamePlayer.setX(22);
		GamePlayer.setY(2);
		tileList = map.getTile(22, 3).getList();
		assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("PK01"));
		GameRunner.processInteraction();
		assertEquals(GamePlayer.getInventory().size(), 3);
		
		//FourthItem
		World.set(0, 0);
		map = World.getCurrentMap();
		GamePlayer.setX(11);
		GamePlayer.setY(5);
		tileList = map.getTile(11, 6).getList();
		assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("SWD1"));
		GameRunner.processInteraction();
		assertEquals(GamePlayer.getInventory().size(), 4);
		
		//test inventory contents
		testInventory = GamePlayer.getInventory();
		assertEquals(testInventory.get(0), WorldInfo.search("BC01"));
		assertEquals(testInventory.get(1), WorldInfo.search("MN01"));
		assertEquals(testInventory.get(2), WorldInfo.search("PK01"));
		assertEquals(testInventory.get(3), WorldInfo.search("SWD1"));
		
		System.out.println("Successfully finished multi item pickup testing.\n");
	}
}