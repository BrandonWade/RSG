package tests.processing;


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
import rsg.processing.MapCreator;
import tests.persistence.DataAccessStub;

import junit.framework.TestCase;

public class TestGameRunner extends TestCase
{
	private final static String MAP1_TXT = "res/GameRunnerTestMaps/testMap1.txt";
	private final static String MAP2_TXT = "res/GameRunnerTestMaps/testMap2.txt";
	private final static String MAP3_TXT = "res/GameRunnerTestMaps/testMap5.txt";
	
	public TestGameRunner (String arg0) 
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		WorldInfo.initializeWorld();
		World.set(0, 0);
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
		System.out.println("Started player movement testing...");
		
		GamePlayer.setX(1);
		GamePlayer.setY(1);
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP1_TXT));
			
			
			//move up 
			GameRunner.checkPrevTileMovement(KeyCode.KeyUp);
			assertEquals(1, GamePlayer.getX());
			assertEquals(0, GamePlayer.getY());
			
			GamePlayer.setX(1);
			GamePlayer.setY(1);
			
			//move left 
			GameRunner.checkPrevTileMovement(KeyCode.KeyLeft);
			assertEquals(GamePlayer.getX(), 0);
			assertEquals(GamePlayer.getY(), 1);
			
			GamePlayer.setX(1);
			GamePlayer.setY(1);
			
			//move right 
			GameRunner.checkPrevTileMovement(KeyCode.KeyRight);
			assertEquals(GamePlayer.getX(), 2);
			assertEquals(GamePlayer.getY(), 1);
			
			GamePlayer.setX(1);
			GamePlayer.setY(1);
			
			//move down
			GameRunner.checkPrevTileMovement(KeyCode.KeyDown);
			assertEquals(GamePlayer.getX(), 1);
			assertEquals(GamePlayer.getY(), 2);
			
			System.out.println("Successfully finished player movement testing.\n");
		}
		catch(MapCreationException e)
		{
			System.out.println("Map Creation Exception: " + e.getMessage());
			fail("Map Creation failure");
		}
	}
	
	public void testMovementMulti()
	{
		System.out.println("Started testing multiple movements...");
		
		GamePlayer.setX(12);
		GamePlayer.setY(12);
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP1_TXT));
			
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
		catch(MapCreationException e)
		{
			System.out.println("Map Creation Exception: " + e.getMessage());
			fail("Map Creation failure");
		}
	}
	
	public void testMovementObstacle() 
	{
		System.out.println("Started movement obstacle testing...");
		
		int xVal = 2;
		int yVal = 5;
		
		GamePlayer.setX(xVal);
		GamePlayer.setY(yVal);
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP2_TXT));
			
			//move up into obstacle
			GameRunner.checkPrevTileMovement(KeyCode.KeyUp);
			assertEquals(GamePlayer.getX(), xVal);
			assertEquals(GamePlayer.getY(), yVal);
			
			//move left into obstacle
			GameRunner.checkPrevTileMovement(KeyCode.KeyLeft);
			assertEquals(GamePlayer.getX(), xVal);
			assertEquals(GamePlayer.getY(), yVal);
			
			//move right into obstacle
			GameRunner.checkPrevTileMovement(KeyCode.KeyRight);
			assertEquals(GamePlayer.getX(), xVal);
			assertEquals(GamePlayer.getY(), yVal);
			
			//move down into obstacle
			GameRunner.checkPrevTileMovement(KeyCode.KeyDown);
			assertEquals(GamePlayer.getX(), xVal);
			assertEquals(GamePlayer.getY(), yVal);
			
			System.out.println("Successfully finished movement obstacle testing.\n");
		}
		catch(MapCreationException e)
		{
			System.out.println("Map Creation Exception: " + e.getMessage());
			fail("Map Creation failure");
		}
	}
	
	public void testMovementWrapRegular()
	{
		System.out.println("Started movement wrap testing...");
		
		GamePlayer.setX(0);
		GamePlayer.setY(0);
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP1_TXT));
			
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
		catch(MapCreationException e)
		{
			System.out.println("Map Creation Exception: " + e.getMessage());
			fail("map creation exception");
		}
	}
	
	public void testNoItemPickup()
	{
		System.out.println("Started no item pickup testing...");
		
		Map map;
		ArrayList<GameObject> tileList;
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP3_TXT));
			map = World.getCurrentMap();
			
			GamePlayer.setX(0);
			GamePlayer.setY(1);
			GamePlayer.setPlayerDirection(KeyCode.KeyDown);
			
			//test initial value
			assertEquals(GamePlayer.getInventory().size(), 0);
			
			//test simple pickup
			tileList = map.getTile(0, 2).getList();
			assertEquals(tileList.size(), 1);
			assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("GR01"));
			GameRunner.processInteraction();
			assertEquals(GamePlayer.getInventory().size(), 0);
			
			tileList = map.getTile(0, 2).getList();
			assertEquals(tileList.size(), 1);
			assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("GR01"));
			
			System.out.println("Successfully finished no item pickup testing.\n");
		}
		catch(MapCreationException e)
		{
			System.out.println("Map Creation Exception: " + e.getMessage());
			fail("Map Creation failure");
		}
	}
	
	public void testSimpleItemPickup()
	{
		System.out.println("Started simple item pickup testing...");
		
		Map map;
		ArrayList<GameObject> tileList;
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP3_TXT));
			map = World.getCurrentMap();
			ArrayList<GameItem> testInventory;
			
			GamePlayer.setX(0);
			GamePlayer.setY(1);
			GamePlayer.setPlayerDirection(KeyCode.KeyUp);
			
			//test initial value
			assertEquals(GamePlayer.getInventory().size(), 0);
			
			//test simple pickup
			tileList = map.getTile(0, 0).getList();
			assertEquals(tileList.size(), 2);
			assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("BC01"));
			GameRunner.processInteraction();
			assertEquals(GamePlayer.getInventorySize(), 1);
			
			tileList = map.getTile(0, 0).getList();
			assertEquals(tileList.size(), 1);
			assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("GR01"));
			
			//test inventory contents
			testInventory = GamePlayer.getInventory();
			assertEquals(testInventory.get(0), WorldInfo.search("BC01"));
			
			System.out.println("Successfully finished simple item pickup testing.\n");
		}
		catch(MapCreationException e)
		{
			System.out.println("Map Creation Exception: " + e.getMessage());
			fail("Map Creation failure");
		}
	}
	
	public void testMultiItemPickup()
	{
		System.out.println("Started Item Pickup Testing...");
		
		Map map;
		ArrayList<GameItem> testInventory;
		ArrayList<GameObject> tileList;
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP3_TXT));
			map = World.getCurrentMap();
			
			GamePlayer.setX(1);
			GamePlayer.setY(1);
			GamePlayer.setPlayerDirection(KeyCode.KeyUp);
			GamePlayer.resetInventory();
			
			//test initial value
			assertEquals(GamePlayer.getInventory().size(), 0);
			
			//test multiple pickup
			tileList = map.getTile(1, 0).getList();
			assertEquals(tileList.size(), 5);
			assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("MN01"));
			GameRunner.processInteraction();
			assertEquals(GamePlayer.getInventory().size(), 1);
			
			tileList = map.getTile(1, 0).getList();
			assertEquals(tileList.size(), 4);
			assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("CH01"));
			GameRunner.processInteraction();
			assertEquals(GamePlayer.getInventory().size(), 2);
			
			tileList = map.getTile(1, 0).getList();
			assertEquals(tileList.size(), 3);
			assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("MU01"));
			GameRunner.processInteraction();
			System.out.println(GamePlayer.getInventory().size());
			assertEquals(GamePlayer.getInventory().size(), 3);
			
			tileList = map.getTile(1, 0).getList();
			assertEquals(tileList.size(), 2);
			assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("BC01"));
			GameRunner.processInteraction();
			assertEquals(GamePlayer.getInventory().size(), 4);
			
			tileList = map.getTile(1, 0).getList();
			assertEquals(tileList.size(), 1);
			assertEquals(tileList.get(tileList.size() - 1), WorldInfo.search("GR01"));
			
			//test inventory contents
			testInventory = GamePlayer.getInventory();
			assertEquals(testInventory.get(0), WorldInfo.search("MN01"));
			testInventory = GamePlayer.getInventory();
			assertEquals(testInventory.get(1), WorldInfo.search("CH01"));
			testInventory = GamePlayer.getInventory();
			assertEquals(testInventory.get(2), WorldInfo.search("MU01"));
			testInventory = GamePlayer.getInventory();
			assertEquals(testInventory.get(3), WorldInfo.search("BC01"));
			
			System.out.println("Successfully finished multi item pickup testing.\n");
		}
		catch(MapCreationException e)
		{
			System.out.println("Map Creation Exception: " + e.getMessage());
			fail("Map Creation failure");
		}
	}
}