package tests.processing;

import rsg.objects.GameItem;
import rsg.objects.Story;
import rsg.persistence.Services;
import rsg.persistence.WorldInfo;
import rsg.processing.StoryManager;
import tests.persistence.DataAccessStub;
import junit.framework.TestCase;

public class TestStoryManager extends TestCase {
	
	public TestStoryManager (String arg0) 
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		WorldInfo.initializeWorld();
	}

	public GameItem getTestGameItem()
	{
		GameItem testItem = new GameItem("TEST","test",null,false,0,"interact msg test");
		
		return testItem;
	}
	
	public void testExistence()
	{	
		assertNotNull(StoryManager.getStory());
		
		Story currStory = StoryManager.getStory();
		
		assertNotNull(currStory.getCurrGoalCode());
		
		assertEquals(currStory.size(), 3);
		
		assertNotNull(currStory.getCurrGoalDesc());
	}
	
	public void testGoalFlags()
	{	
		assertFalse(StoryManager.checkGoalFlag());
		
		assertFalse(StoryManager.checkWrongItemFlag());
		
		assertTrue(StoryManager.checkItemPickupAllowed(getTestGameItem()));
		
		StoryManager.markWrongItemFlag();
		assertTrue(StoryManager.checkWrongItemFlag());
		
		StoryManager.clearWrongItemFlag();
		assertFalse(StoryManager.checkWrongItemFlag());
	}
	
	public void testGoalsCompleted()
	{
		assertFalse(StoryManager.checkGoalCompleted(getTestGameItem()));
	}
}
