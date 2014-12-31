package tests.processing;

import rsg.objects.GamePlayer;
import rsg.objects.Map;
import rsg.persistence.Services;
import rsg.persistence.World;
import rsg.persistence.WorldInfo;
import rsg.processing.GameRunner;
import rsg.processing.KeyCode;
import rsg.processing.MapCreationException;
import tests.persistence.DataAccessStub;
import junit.framework.TestCase;

public class TestCharacterMove extends TestCase
{
	public TestCharacterMove(String arg0) 
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		WorldInfo.initializeWorld();
		GamePlayer.createPlayer("MC04", "TEST", WorldInfo.search("MC04").getImage(), 20, 20, 3, 0, 0, 0);
		try 
		{
			World.initalizeSuperMap();
		} 
		catch (MapCreationException e)
		{
			fail("Map should be createable.");
		}
	}
	
	public void tearDown()
	{
		GamePlayer.delete();
	}
	
	public void testCheckPrevTileMovement()
	{
		System.out.println("Starting test checkprevious tile movement...");
		
		World.set(0, 0);
		
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyDown));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyDown));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyDown));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyDown));
		assertEquals(0, GamePlayer.getX());
		assertEquals(4, GamePlayer.getY());
		//rock
		assertFalse(GameRunner.checkPrevTileMovement(KeyCode.KeyLeft));
		//Move...
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyDown));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyLeft));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyLeft));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyUp));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyRight));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyRight));
		//rock
		assertFalse(GameRunner.checkPrevTileMovement(KeyCode.KeyRight));
		//Move...
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyUp));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyRight));
		assertTrue(GameRunner.checkPrevTileMovement(KeyCode.KeyRight));
		assertEquals(2, GamePlayer.getX());
		assertEquals(3, GamePlayer.getY());

		System.out.println("Sucessfully finished checkprevious tile movement.\n");
	}
	
	//Tests that if a player walks onto a new map that the square they are trying to move to on the new map is not traversable.
	public void testNewMapNonValidTransition()
	{
		System.out.println("Starting test new map non valid transitions...");
		
		World.set(1,0);
		GamePlayer.setPosition(22, 23);
		
		//Test down
		GamePlayer.setPlayerDirection(KeyCode.KeyDown);
		GamePlayer.setPosition(21,23);
		assertFalse(GameRunner.newMapValidTransition());
		GamePlayer.setPosition(20,23);
		assertFalse(GameRunner.newMapValidTransition());
		
		//test up
		World.set(1, 2);
		GamePlayer.setPlayerDirection(KeyCode.KeyUp);
		GamePlayer.setPosition(21,0);
		assertFalse(GameRunner.newMapValidTransition());
		GamePlayer.setPosition(20,0);
		assertFalse(GameRunner.newMapValidTransition());
		
		//Test right
		World.set(0, 1);
		GamePlayer.setPlayerDirection(KeyCode.KeyRight);
		GamePlayer.setPosition(23,0);
		assertFalse(GameRunner.newMapValidTransition());
		GamePlayer.setPosition(23,7);
		assertFalse(GameRunner.newMapValidTransition());
		
		//test Left
		World.set(2, 1);
		GamePlayer.setPlayerDirection(KeyCode.KeyLeft);
		GamePlayer.setPosition(0, 0);
		assertFalse(GameRunner.newMapValidTransition());
		GamePlayer.setPosition(0, 7);
		assertFalse(GameRunner.newMapValidTransition());

		System.out.println("Successfully finished test new map non valid transition.\n");
	}
	
	//Tests that if a player walks onto a new map that the square they are trying to move to on the new map is traversable.
	public void testNewMapValidTransition()
	{
		System.out.println("Starting test new map valid transitions...");
	
		//Test down
		World.set(1,0);
		GamePlayer.setPosition(22, 23);
		GamePlayer.setPlayerDirection(KeyCode.KeyDown);
		assertTrue(GameRunner.newMapValidTransition());
		
		//test up
		World.set(1, 2);
		GamePlayer.setPosition(22, 0);
		GamePlayer.setPlayerDirection(KeyCode.KeyUp);
		assertTrue(GameRunner.newMapValidTransition());
		
		//Test right
		World.set(0, 1);
		GamePlayer.setPosition(23, 1);
		GamePlayer.setPlayerDirection(KeyCode.KeyRight);
		assertTrue(GameRunner.newMapValidTransition());
		
		//test Left
		World.set(2, 1);
		GamePlayer.setPosition(0, 1);
		GamePlayer.setPlayerDirection(KeyCode.KeyLeft);
		assertTrue(GameRunner.newMapValidTransition());
		
		System.out.println("Successfully finished test new map valid transition.\n");
	}
	
	public void testIsNewMap()
	{
		System.out.println("Starting test is new map...");
		final int MAX = Map.getCols() - 1;
		final int MIN = 0;
		
		assertTrue(GameRunner.isNewMap(MIN, MAX, 5, 5));
		assertTrue(GameRunner.isNewMap(MAX, MIN, 5, 5));
		assertTrue(GameRunner.isNewMap(MIN, MAX, 5, 5));
		assertTrue(GameRunner.isNewMap(MIN, MIN, MAX, MIN));
		assertTrue(GameRunner.isNewMap(3, 3, MIN, MAX));
		assertFalse(GameRunner.isNewMap(12, 13, 14, 15));
		assertFalse(GameRunner.isNewMap(22 ,7 ,2 , MIN));
		assertFalse(GameRunner.isNewMap(MIN, MIN, MIN, MIN));
		assertFalse(GameRunner.isNewMap(MAX, MAX, MAX, MAX));
		
		//Test that if a map appears to be loaded, but somehow the player has moved in two directions (Mot allowed).
		assertFalse(GameRunner.isNewMap(MIN, MAX, 12, 13));
		assertFalse(GameRunner.isNewMap(23, 1, MAX, MIN));
		assertFalse(GameRunner.isNewMap(MIN, MAX, MAX, MIN));
		assertFalse(GameRunner.isNewMap(MAX, MIN, MAX, MIN));
		
		System.out.println("Successfully finished test is new map.\n");
	}
}