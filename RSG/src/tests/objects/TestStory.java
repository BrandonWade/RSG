package tests.objects;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;

import junit.framework.TestCase;
import rsg.objects.GameItem;
import rsg.objects.GamePlayer;
import rsg.objects.OurDisplay;
import rsg.objects.Story;
import rsg.objects.Goal;
import rsg.persistence.Services;
import rsg.persistence.WorldInfo;
import rsg.presentation.GuiWindow;
import tests.persistence.DataAccessStub;

public class TestStory extends TestCase
{
	Story story;
	Goal testGoal0;
	Goal testGoal1;
	Goal testGoal2;
	Goal testGoal3;
	Goal testGoal4;
	GameItem testItem0;
	GameItem testItem1;
	GameItem testItem2;
	GameItem testItem3;
	GameItem testItem4;
	
	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		WorldInfo.initializeWorld();
		GamePlayer.createPlayer("MC01", "GamePlayer", WorldInfo.search("MC01").getImage(), 20, 20, 20, 20,0,0);
		GuiWindow.createGUI();
		
		testItem0 = new GameItem("Code 0", "Item 0", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/male04.png"), true, 0, "0");
		testItem1 = new GameItem("Code 1", "Item 1", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/male04.png"), true, 0, "1");
		testItem2 = new GameItem("Code 2", "Item 2", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/male04.png"), true, 0, "2");
		testItem3 = new GameItem("Code 3", "Item 3", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/male04.png"), true, 0, "3");
		testItem4 = new GameItem("Code 4", "Item 4", new Image(OurDisplay.getDisplay(), "res/assets/art/grid/male04.png"), true, 0, "4");

		testGoal0 = new Goal(0, "Init 0", testItem0.getCode(), "Comp 0", "Wrong 0");
		testGoal1 = new Goal(0, "Init 1", testItem1.getCode(), "Comp 1", "Wrong 1");
		testGoal2 = new Goal(0, "Init 2", testItem2.getCode(), "Comp 2", "Wrong 2");
		testGoal3 = new Goal(0, "Init 3", testItem3.getCode(), "Comp 3", "Wrong 3");
		testGoal4 = new Goal(0, "Init 4", testItem4.getCode(), "Comp 4", "Wrong 4");
	}
	
	public void tearDown()
	{
		GamePlayer.delete();
		Services.closeDataAccess();
	}
	
	public void resetUp()
	{
		ArrayList<Goal> goalList = new ArrayList<Goal>();
		goalList.add(testGoal0);
		goalList.add(testGoal1);
		goalList.add(testGoal2);
		goalList.add(testGoal3);
		goalList.add(testGoal4);
		
		story = new Story(goalList);
	}
	
	public void testCheckGoalItem()
	{
		System.out.println("Starting checkGoalItem testing...");
		
		resetUp();
		
		assertTrue(story.checkGoalItem(testItem0));
		assertFalse(story.checkGoalItem(testItem1));
		assertFalse(story.checkGoalItem(testItem2));
		assertFalse(story.checkGoalItem(testItem3));
		assertFalse(story.checkGoalItem(testItem4));
		
		System.out.println("Successfully finished checkGoalItem testing.\n");
	}
	
	public void testGoalListSize()
	{
		System.out.println("Starting GoalListSize testing...");
		
		ArrayList<Goal> goalList = new ArrayList<Goal>();
		story = new Story(goalList);
		
		assertEquals(0,story.size());
		
		goalList.add(testGoal0);
		story = new Story(goalList);
		assertEquals(1,story.size());
		
		goalList.add(testGoal1);
		story = new Story(goalList);
		assertEquals(2,story.size());
		
		System.out.println("Successfully finished GoalListSize testing.\n");
	}
	
	public void testGoalCompletion()
	{
		System.out.println("Starting GoalCompletion testing...");
		
		resetUp();
		
		assertEquals(testGoal0.getCode(), story.getCurrGoalCode());
		assertEquals(testGoal0.getDescription(), story.getCurrGoalDesc());
		story.setNextGoal();
		assertEquals(testGoal1.getCode(), story.getCurrGoalCode());
		assertEquals(testGoal1.getDescription(), story.getCurrGoalDesc());
		story.setNextGoal();
		assertEquals(testGoal2.getCode(), story.getCurrGoalCode());
		assertEquals(testGoal2.getDescription(), story.getCurrGoalDesc());
		story.setNextGoal();
		assertEquals(testGoal3.getCode(), story.getCurrGoalCode());
		assertEquals(testGoal3.getDescription(), story.getCurrGoalDesc());
		story.setNextGoal();
		assertEquals(testGoal4.getCode(), story.getCurrGoalCode());
		assertEquals(testGoal4.getDescription(), story.getCurrGoalDesc());
		story.setNextGoal();
		assertEquals(story.getCurrGoalDesc(), Story.endText);
		
		System.out.println("Successfully finished GoalCompletion testing.\n");
		
	}
}
