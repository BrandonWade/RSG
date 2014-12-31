package tests.integration;

import rsg.objects.Map;
import rsg.persistence.Services;
import rsg.persistence.World;
import rsg.persistence.WorldInfo;
import rsg.processing.MapCreationException;
import junit.framework.TestCase;

public class TestMapTraversability extends TestCase 
{
	final int ZERO = 0; //border value
	final int MAX = 23; //border value
	boolean created = false;
	
	public TestMapTraversability (String arg0) 
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess("OBJS");

		WorldInfo.initializeWorld();
		if (!created)
		{
			created = true;
			try 
			{
				World.initalizeSuperMap();
			} 
			catch (MapCreationException mce) 
			{
				mce.printStackTrace();
				fail("Unable to load superworld");
			}
		}
	}
	
	public void tearDown()
	{
		Services.closeDataAccess();
	}
	
	//When you move from map A -> B that the square in B that you move to from A is traversable.
	public void testNoWrapCollision()
	{
		System.out.println("Starting traversablity intergration test (test no wrap collision)");
		Map currMap = null;
		
		for (int superMapX = 0; superMapX < World.getMapXLength(); superMapX++)
		{
			for (int superMapY = 0; superMapY < World.getMapYLength(); superMapY++)
			{
				currMap = World.getSuperMap(superMapX, superMapY);
				
				//These methods set and test the specific border constants that must be tested 
				compareVerticalBounds(currMap, World.getSuperMap(superMapX, superMapY - 1), ZERO, MAX);
				compareVerticalBounds(currMap, World.getSuperMap(superMapX, superMapY + 1), MAX, ZERO);
				compareHorizontalBounds(currMap, World.getSuperMap(superMapX - 1, superMapY), ZERO, MAX);
				compareHorizontalBounds(currMap, World.getSuperMap(superMapX + 1, superMapY), MAX, ZERO);
			}
		}
		System.out.println("Successfully finished test no wrap collision");
	}

	private void compareVerticalBounds(Map currMap, Map testMap, int firstBound, int secondBound)
	{
		for (int i = 0; i <= MAX; i++)
		{
			if(currMap.isTileTraversable(i, firstBound) != testMap.isTileTraversable(i, secondBound))
			{
				fail("Tiles are not simular traversablity.\n Failed on maps " + currMap.getMapLocation() + 
						" and " + testMap.getMapLocation() + " on rows: " + secondBound + ", " + firstBound + 
						" and column " + i + ".");
			}
		}	
	}

	private void compareHorizontalBounds(Map currMap, Map testMap, int firstBound, int secondBound)
	{
		for (int i = 0; i <= MAX; i++)
		{
			if(currMap.isTileTraversable(firstBound, i) != testMap.isTileTraversable(secondBound, i))
			{
				fail("Tiles are not simular traversablity.\nFailed on map " +currMap.getMapLocation() + 
						" line " + i + " column " + firstBound + " and on map " + testMap.getMapLocation() + 
						" on rows: " + secondBound + " column " + i + ".");
			}
		}
	}
}
