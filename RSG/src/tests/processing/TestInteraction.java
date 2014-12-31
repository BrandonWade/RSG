package tests.processing;

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
import rsg.processing.WindowInput;
import tests.persistence.DataAccessStub;
import junit.framework.TestCase;

public class TestInteraction extends TestCase
{
	private final String MAP3 = "res/InteractionTestMaps/map3.txt";
	private final String MAP2 = "res/InteractionTestMaps/map2.txt";
	private final String MAP1 = "res/InteractionTestMaps/map1.txt";
	
	public TestInteraction(String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{		
		Services.createDataAccess(new DataAccessStub("STUB"));
		WorldInfo.initializeWorld();
		GamePlayer.createPlayer("MC01", "GamePlayer", WorldInfo.search("MC01").getImage(), 20, 20, 20, 20, 0, 0);
		GuiWindow.createGUI();
		
		try 
		{
			World.initalizeSuperMap();
		} 
		catch (MapCreationException e)
		{
			e.printStackTrace();
			fail("Map creation should not have failed for this task.");
		}
	}
	
	public void tearDown()
	{
		World.destroy();
		Services.closeDataAccess();
		GamePlayer.delete();
	}
	
	public void testValidKey()
	{
		System.out.println("Started valid key press testing...");
		
		//testing interaction keys
		assertFalse(WindowInput.validInteractKey(KeyCode.KeyUp));
		assertFalse(WindowInput.validInteractKey(KeyCode.KeyLeft));
		assertTrue(WindowInput.validInteractKey(KeyCode.KeySpace));
		assertFalse(WindowInput.validInteractKey(KeyCode.KeyDown));
		assertFalse(WindowInput.validInteractKey(KeyCode.KeyRight));
		assertFalse(WindowInput.validInteractKey(KeyCode.KeyEsc));
		assertFalse(WindowInput.validInteractKey(null));
		
		//testing movement keys
		assertTrue(WindowInput.validMovementKey(KeyCode.KeyUp));
		assertTrue(WindowInput.validMovementKey(KeyCode.KeyLeft));
		assertFalse(WindowInput.validMovementKey(KeyCode.KeySpace));
		assertTrue(WindowInput.validMovementKey(KeyCode.KeyDown));
		assertTrue(WindowInput.validMovementKey(KeyCode.KeyRight));
		assertFalse(WindowInput.validMovementKey(KeyCode.KeyEsc));
		assertFalse(WindowInput.validMovementKey(null));
		
		//testing weapon keys
		assertFalse(WindowInput.validWeaponKey(KeyCode.KeyUp));
		assertFalse(WindowInput.validWeaponKey(KeyCode.KeyLeft));
		assertFalse(WindowInput.validWeaponKey(KeyCode.KeySpace));
		assertFalse(WindowInput.validWeaponKey(KeyCode.KeyDown));
		assertFalse(WindowInput.validWeaponKey(KeyCode.KeyRight));
		assertTrue(WindowInput.validWeaponKey(KeyCode.KeyEsc));
		assertFalse(WindowInput.validWeaponKey(null));
		
		System.out.println("Successfully finished vaild key press testing.\n");
	}
	
	public void testInputAcceptance()
	{
		//unfreeze input
		WindowInput.unfreezeInput();
		assertTrue(WindowInput.inputAccepted());
		
		//freeze input
		WindowInput.freezeInput();
		assertFalse(WindowInput.inputAccepted());
		
		//unfreeze input
		WindowInput.unfreezeInput();
		assertTrue(WindowInput.inputAccepted());
	}

	public void testUnfreezeOnKey()
	{
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP1));
			
			assertTrue(WindowInput.inputAccepted());
			WindowInput.freezeInput();
			assertFalse(WindowInput.inputAccepted());
			WindowInput.unfreezeOnKey(KeyCode.KeySpace);
			WindowInput.processKeyboardInput(KeyCode.KeyDown);
			assertFalse(WindowInput.inputAccepted());
			WindowInput.processKeyboardInput(KeyCode.KeyEsc);
			assertFalse(WindowInput.inputAccepted());
			WindowInput.processKeyboardInput(KeyCode.KeySpace);
			assertTrue(WindowInput.inputAccepted());
		
		}
		catch (MapCreationException mce)
		{
			System.out.println("Map Creation Exception: " + mce.getMessage());
			fail("Map Creation failure");
		}
	}
	
	public void testBasicInteraction()
	{
		System.out.println("Started basic interaction testing...");
		
		Map map;
		String guiOutput = "";
		
		GamePlayer.setX(1);
		GamePlayer.setY(1);
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP1));
			map = World.getCurrentMap();
			
			//Face North -> interact with tier
			GamePlayer.setPlayerDirection(KeyCode.KeyUp);
			GameRunner.processInteraction();
			guiOutput = GuiWindow.getGameOutput();
			assertEquals(guiOutput, map.getInteractionMessage(1, 0));
			
			//Face South -> interact with rock
			GamePlayer.setPlayerDirection(KeyCode.KeyDown);
			GameRunner.processInteraction();
			guiOutput = GuiWindow.getGameOutput();
			
			assertEquals(guiOutput, map.getInteractionMessage(1, 2));
			
			//Face East -> interact with evergreen
			GamePlayer.setPlayerDirection(KeyCode.KeyRight);
			GameRunner.processInteraction();
			guiOutput = GuiWindow.getGameOutput();
			assertEquals(guiOutput, map.getInteractionMessage(2, 1));
			
			//Face West -> interact with large evergreen
			GamePlayer.setPlayerDirection(KeyCode.KeyLeft);
			GameRunner.processInteraction();
			guiOutput = GuiWindow.getGameOutput();
			assertEquals(guiOutput, map.getInteractionMessage(0, 1));
		}
		catch (MapCreationException mce)
		{
			System.out.println("Map Creation Exception: " + mce.getMessage());
			fail("Map Creation failure");
		}
		
		System.out.println("Successfully finished basic interaction testing.\n");
	}
	
	public void testBoundaryInteraction()
	{
		System.out.println("Started boundary interaction testing...");
		
		Map map;
		String guiOutput = "";
		
		GamePlayer.setX(0);
		GamePlayer.setY(0);
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP2));
			map = World.getCurrentMap();
			
			//Face North -> interact with nothing
			GuiWindow.updateGameOutput("");
			GamePlayer.setPlayerDirection(KeyCode.KeyUp);
			GameRunner.processInteraction();
			guiOutput = GuiWindow.getGameOutput();
			assertEquals(guiOutput, "");
			
			//Face South -> interact with tier
			GuiWindow.updateGameOutput("");
			GamePlayer.setPlayerDirection(KeyCode.KeyDown);
			GameRunner.processInteraction();
			guiOutput = GuiWindow.getGameOutput();
			assertEquals(guiOutput, map.getInteractionMessage(0, 1));
			
			//Face East -> interact with large evergreen
			GuiWindow.updateGameOutput("");
			GamePlayer.setPlayerDirection(KeyCode.KeyRight);
			GameRunner.processInteraction();
			guiOutput = GuiWindow.getGameOutput();
			assertEquals(guiOutput, map.getInteractionMessage(1, 0));
			
			//Face West -> interact with nothing
			GuiWindow.updateGameOutput("");
			GamePlayer.setPlayerDirection(KeyCode.KeyLeft);
			GameRunner.processInteraction();
			guiOutput = GuiWindow.getGameOutput();
			assertEquals(guiOutput, "");
		}
		catch (MapCreationException mce)
		{
			System.out.println("Map Creation Exception: " + mce.getMessage());
			fail("Map Creation failure");
		}
		
		System.out.println("Successfully finished boundary interaction testing.\n");
	}

	public void testStackInteraction()
	{
		System.out.println("Started stack interaction testing...");
		
		Map map;
		String guiOutput = "";
		
		GamePlayer.setX(1);
		GamePlayer.setY(1);
		
		try
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP3));
			map = World.getCurrentMap();
			
			//Test a stack of items
			GuiWindow.updateGameOutput("");
			GamePlayer.setPlayerDirection(KeyCode.KeyDown);
			GameRunner.processInteraction();
			guiOutput = GuiWindow.getGameOutput();
			assertEquals(guiOutput, map.getInteractionMessage(1, 2));
		}
		catch (MapCreationException mce)
		{
			System.out.println("Map Creation Exception: " + mce.getMessage());
			fail("Map Creation failure");
		}
		
		System.out.println("Successfully finished stack interaction testing.\n");
	}
}