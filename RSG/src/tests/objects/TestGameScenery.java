package tests.objects;

import junit.framework.TestCase;

import org.eclipse.swt.graphics.Image;

import rsg.objects.GameScenery;
import rsg.objects.OurDisplay;
import rsg.persistence.Services;
import tests.persistence.DataAccessStub;

public class TestGameScenery extends TestCase
{
	private GameScenery scenery;
	
	public TestGameScenery(String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		scenery = new GameScenery("A", "Test", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/male04.png"), false, "Test scenery.", null,  false);
	}
	
	public void tearDown()
	{
		Services.closeDataAccess();
	}
	
	public void testTraversable()
	{
		System.out.println("Starting game scenery traversable testing...");
		assertEquals(false, scenery.isTraversable());
		
		scenery.setTraversable(true);
		
		assertEquals(true, scenery.isTraversable());
		
		System.out.println("Successfully finished game scenery traversable testing.\n");
	}
}