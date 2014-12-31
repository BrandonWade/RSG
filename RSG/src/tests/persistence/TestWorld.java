package tests.persistence;

import rsg.objects.Direction;
import rsg.objects.Map;
import rsg.persistence.Services;
import rsg.persistence.World;
import rsg.persistence.WorldInfo;
import rsg.processing.MapCreationException;
import junit.framework.TestCase;

public class TestWorld extends TestCase
{
	private Map map = null;
	private final int MAX = 3;

	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		WorldInfo.initializeWorld();
		
		try 
		{
			World.initalizeSuperMap();
		} 
		catch (MapCreationException e)
		{
			e.printStackTrace();
			fail("SuperMap was not able to be created.");
		}
	}
	
	public void tearDown()
	{
		Services.closeDataAccess();
	}
	
	public void testMapsLoaded()
	{
		System.out.println("\nStarting test maps loaded...");
		
		for (int i = 0; i < World.getMapXLength(); i++)
		{
			for (int j = 0; j < World.getMapYLength(); j++)
			{
				assertNotNull(World.getSuperMap(i, j));
			}
		}
		
		System.out.println("\nSuccessfully finished test maps loaded");
	}
	
	public void testGetSuperMapValue()
	{
		System.out.println("\nStarting test get super map value...");
		
		map = World.getSuperMap(-1, 0);
		assertEquals(World.getSuperMap(2, 0).getMapLocation(), map.getMapLocation());
		
		map = World.getSuperMap(6, 3);
		assertEquals(World.getSuperMap(0, 0).getMapLocation(), map.getMapLocation());
		
		map = World.getSuperMap(0, 0);
		assertEquals(World.getSuperMap(0, 0).getMapLocation(), map.getMapLocation());
		
		map = World.getSuperMap(-6, 4);
		assertEquals(World.getSuperMap(0, 1).getMapLocation(), map.getMapLocation());
		
		System.out.println("\nSuccessfully finished test get super map value.");
	}
	
	public void testLoadNewSuperMap()
	{
		System.out.println("\nStarting test load new supermap...");
		World.set(1,1);
		
		//regular
		map = World.loadNewSuperMap(Direction.North);
		assertEquals(World.getSuperMap(1, 0).getMapLocation(), map.getMapLocation());

		World.set(1,1);
		map = World.loadNewSuperMap(Direction.South);
		assertEquals(World.getSuperMap(1, 2).getMapLocation(), map.getMapLocation());

		World.set(1,1);
		map = World.loadNewSuperMap(Direction.East);
		assertEquals(World.getSuperMap(2, 1).getMapLocation(), map.getMapLocation());

		World.set(1,1);
		map = World.loadNewSuperMap(Direction.West);
		assertEquals(World.getSuperMap(0, 1).getMapLocation(), map.getMapLocation());

		World.set(0, 0);
		map = World.loadNewSuperMap(Direction.North);
		assertEquals(World.getSuperMap(0, 2).getMapLocation(), map.getMapLocation());

		World.set(0, 0);
		map = World.loadNewSuperMap(Direction.South);
		assertEquals(World.getSuperMap(0, 1).getMapLocation(), map.getMapLocation());

		World.set(0, 0);
		map = World.loadNewSuperMap(Direction.East);
		assertEquals(World.getSuperMap(1, 0).getMapLocation(), map.getMapLocation());

		World.set(0, 0);
		map = World.loadNewSuperMap(Direction.West);
		assertEquals(World.getSuperMap(2, 0).getMapLocation(), map.getMapLocation());
		
		System.out.println("\nSuccessfully finished load new super map");
	}
	
	public void testGetSuperMapDirection()
	{
		System.out.println("\nStarting test get super map direction...");
		World.set(1,1);
		
		//regular
		map = World.getSuperMap(Direction.North);
		assertEquals(World.getSuperMap(1, 0).getMapLocation(), map.getMapLocation());

		map = World.getSuperMap(Direction.South);
		assertEquals(World.getSuperMap(1, 2).getMapLocation(), map.getMapLocation());

		map = World.getSuperMap(Direction.East);
		assertEquals(World.getSuperMap(2, 1).getMapLocation(), map.getMapLocation());

		map = World.getSuperMap(Direction.West);
		assertEquals(World.getSuperMap(0, 1).getMapLocation(), map.getMapLocation());

		World.set(0, 0);
		map = World.getSuperMap(Direction.North);
		assertEquals(World.getSuperMap(0, 2).getMapLocation(), map.getMapLocation());

		map = World.getSuperMap(Direction.South);
		assertEquals(World.getSuperMap(0, 1).getMapLocation(), map.getMapLocation());

		map = World.getSuperMap(Direction.East);
		assertEquals(World.getSuperMap(1, 0).getMapLocation(), map.getMapLocation());

		map = World.getSuperMap(Direction.West);
		assertEquals(World.getSuperMap(2, 0).getMapLocation(), map.getMapLocation());
		
		System.out.println("\nSuccessfuly finished get super map direction.");
	}
	
	public void testGetSetSuperMap()
	{
		System.out.println("\nStarting test get set super map...");
		
		map = World.getSetSuperMap(2, 2);
		assertEquals(World.getSuperMap(2, 2).getMapLocation(), World.getCurrentMap().getMapLocation());
		assertNull(World.getSetSuperMap(3,3));
		
		System.out.println("\nSuccessfully finished test get set super map.");
	}
	
	public void testMapSizing()
	{
		System.out.println("\nStarting test map sizing");
		
		assertEquals(MAX, World.getMapXLength());
		assertEquals(MAX, World.getMapYLength());
		
		System.out.println("\nSuccessfuly finished test map sizing.");
	}
	
	public void testMapFilled()
	{
		System.out.println("\nStarting test map filled...");
		
		map = null;
		
		for (int i = 0; i < World.getMapXLength(); i++)
		{
			for (int j = 0; j < World.getMapYLength(); j++)
			{
				assertFalse(World.addLocation(map, i, j));
			}
		}
		
		System.out.println("Successfully finished test map filled.");
	}
}