package tests.persistence;

import java.util.ArrayList;

import rsg.objects.GameItem;
import rsg.objects.GameObject;
import rsg.persistence.DataAccess;
import rsg.persistence.Services;


import junit.framework.TestCase;

public class TestDataAccess extends TestCase
{
	private DataAccess db;
	private ArrayList<GameObject> scenery;
	private ArrayList<GameItem> gameItems;
	private String[] maps = new String[9];
	
	public TestDataAccess(String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{
		db = (DataAccess) Services.createDataAccess(new DataAccessStub("OBJS"));
		scenery = db.getGameScenery();
		gameItems = db.getGameItems();
		maps = db.getGameMapInfo();
	}
	
	public void tearDown()
	{
		Services.closeDataAccess();
	}
	
	public void testTablesNotNull()
	{
		System.out.println("Starting data access stub testing...");
		assertNotNull(maps);
		assertNotNull(gameItems);
		assertNotNull(scenery);
		
		System.out.println("Successfully finished data access stub testing.\n");
	}
	
	public void testSizes()
	{
		assertEquals(scenery.size(), 24);
		assertEquals(gameItems.size(), 9);
		assertEquals(maps.length, 9);
	}
}