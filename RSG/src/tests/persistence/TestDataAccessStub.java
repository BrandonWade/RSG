package tests.persistence;

import java.util.ArrayList;

import rsg.objects.GameObject;

import junit.framework.TestCase;

public class TestDataAccessStub extends TestCase
{
	private DataAccessStub db;
	private ArrayList<GameObject> list;
	
	public TestDataAccessStub(String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{
		db = new DataAccessStub("Test DB");
		db.open("Test DB");
	}
	
	public void testList()
	{
		System.out.println("Starting data access stub testing...");
		list = db.getGameObjs();
		
		assertNotNull(list);
		
		System.out.println("Successfully finished data access stub testing.\n");
	}
	
	public void testName()
	{
		System.out.println("Begin name testing...");
		
		assertEquals("Test DB", db.getName());
		
		System.out.println("Successfully finished name testing.\n");
	}
}
