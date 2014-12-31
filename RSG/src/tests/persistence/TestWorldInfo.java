package tests.persistence;

import java.util.ArrayList;

import rsg.objects.GameObject;
import rsg.objects.Goal;
import rsg.persistence.Services;
import rsg.persistence.WorldInfo;
import junit.framework.TestCase;

public class TestWorldInfo extends TestCase
{
	private GameObject retVal;
	private Goal gVal;
	private ArrayList<GameObject> list;
	private ArrayList<Goal> gList;
	
	public TestWorldInfo (String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		WorldInfo.initializeWorld();
		list = WorldInfo.getObjects();
		gList = WorldInfo.getGoals();
	}
	
	public void tearDown()
	{
		Services.closeDataAccess();
	}
	
	public void testSearch()
	{
		System.out.println("Starting World test searching....");
		
		retVal = WorldInfo.search("GR01");
		assertEquals("GR01", retVal.getCode());
		
		retVal = WorldInfo.search("SD04");
		assertEquals("SD04", retVal.getCode());
		
		retVal = WorldInfo.search("TR08");
		assertEquals("TR08", retVal.getCode());
		
		//Should return null on search for non-existent code
		retVal = WorldInfo.search("F");
		assertNull(retVal);
	
		//Should return null on search for non-existent code
		retVal = WorldInfo.search("0");
		assertNull(retVal);
		
		retVal = WorldInfo.search("MC04");
		assertEquals("MC04", retVal.getCode());
		
		System.out.println("Successfully finished world search testing.\n");
	}
	
	public void testWorldCreation()
	{
		System.out.println("Starting world creation testing...");
		
		assertNotNull(list);
		assertNotSame(list.size(), 0);
		
		assertNotNull(gList);
		assertNotSame(gList.size(), 0);
		
		System.out.println("Successfully finished world creation testing.\n");
	}
	
	public void testSearchGoals()
	{
		System.out.println("Starting goal search testing...");
		
		gVal = WorldInfo.searchGoals("c");
		assertNull(gVal);
		
		gVal = WorldInfo.searchGoals("MG01");
		assertNotNull(gVal);
		assertEquals(gVal.getNum(), 0);
		assertNotSame(gVal.getWrongItemMsg(), "c");
		assertEquals(gVal.getWrongItemMsg(), "What's this?");
		assertEquals(gVal.getDescription(), "If you are going to pull an all-nighter, you might want some coffee first.");
		
		gVal = WorldInfo.searchGoals("a");
		assertNull(gVal);
		
		gVal = WorldInfo.searchGoals("0");
		assertNull(gVal);
		
		gVal = WorldInfo.searchGoals("GR01");
		assertNull(gVal);
		
		System.out.println("Successfully finished goal search testing.\n");
	}
}