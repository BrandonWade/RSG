package tests.objects;

import junit.framework.TestCase;

import org.eclipse.swt.graphics.Image;

import rsg.objects.GameObject;
import rsg.objects.GameScenery;
import rsg.objects.OurDisplay;
import rsg.persistence.Services;
import tests.persistence.DataAccessStub;

public class TestGameObject extends TestCase
{	
	private GameObject obj;
	
	public TestGameObject(String arg0)
	{
		super(arg0);
	}

	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		obj = new GameScenery("A", "Test", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/sand01.png"), true, "Test item.", null,  false);
	}
	
	public void tearDown()
	{
		Services.closeDataAccess();
	}
	
	public void testInitialName()
	{
		System.out.println("Begin initial name testing...");
		
		assertEquals("Test", obj.getName());
		
		System.out.println("Successfully finished initial name testing.\n");
	}
	
	public void testChangingName()
	{
		System.out.println("Begin change name testing...");
		
		obj.setName("Test2");
		assertEquals("Test2", obj.getName());
		
		obj.setName("T");
		assertEquals("T", obj.getName());
		
		System.out.println("Successfully finished change name testing.\n");
	}
	
	public void testUnchangedName()
	{
		System.out.println("Begin unchanged name testing...");
		
		//neither of these should change the original name
		obj.setName("");
		assertEquals("Test", obj.getName());
		
		obj.setName(null);
		assertEquals("Test", obj.getName());
		
		System.out.println("Successfully finished unchanged name testing.\n");
	}
	
	public void testInitialTraversable()
	{
		System.out.println("Begin initial traversable testing...");
		
		assertEquals(true, obj.isTraversable());
		
		System.out.println("Successfully finished initial traversable testing.\n");
	}
	
	public void testChangingTraversable()
	{
		System.out.println("Begin changing traversbale testing...");
		
		obj.setTraversable(false);
		assertEquals(false, obj.isTraversable());
		
		System.out.println("Successully finished changing traversable testing.\n");
	}
	
	public void testInitialCode()
	{
		System.out.println("Begin initial code testing...");
		
		assertEquals("A", obj.getCode());
		
		System.out.println("Successfully finished initial code testing.\n");
	}
	
	public void testChangingCode()
	{
		System.out.println("Begin changing code testing...");
		
		obj.setCode("B");
		assertEquals("B", obj.getCode());
		
		System.out.println("Successfully finished changing code testing.\n");
	}
	
	public void testImageExistance()
	{
		System.out.println("Begin initial image existance testing...");
		
		assertNotNull(obj.getImage());
		
		System.out.println("Successfully finished image existance testing.\n");
	}
}
