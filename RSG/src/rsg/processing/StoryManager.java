package rsg.processing;

import rsg.objects.GameItem;
import rsg.objects.Goal;
import rsg.objects.Story;
import rsg.persistence.WorldInfo;
import rsg.presentation.GuiWindow;

public abstract class StoryManager
{	
	private static Story story = new Story(WorldInfo.getGoals());
	private static boolean goalFlag = false;
	private static boolean wrongItemFlag = false;
	private static String wrongItemMsg = "";
	
	public static boolean checkGoalCompleted(GameItem newItem)
	{	
		boolean goalComplete;
		
		goalComplete = story.checkGoalItem(newItem);
		
		if (goalComplete)
		{
			story.setNextGoal();
			goalFlag = true;
		}
		
		return goalFlag;
	}
	
	public static Story getStory()
	{
		return story;
	}
	
	public static String getCurrentGoalDesc()
	{
		return story.getCurrGoalDesc();
	}
	
	public static boolean checkGoalFlag()
	{
		return goalFlag;
	}
	
	public static void outputCompleted()
	{
		goalFlag = false;
		
		GuiWindow.updateGoalOutput(getCurrentGoalDesc());
		
		if (story.getCurrGoalDesc() == Story.endText)
		{
			GuiWindow.drawImage(WorldInfo.gameWinScreen, 0, 0);
			WindowInput.freezeInput();
			WindowInput.unfreezeOnKey(KeyCode.KeySpace);
			GuiWindow.removeLastItem();
		}
	}
	
	public static String getWrongItemMsg()
	{
		return wrongItemMsg;
	}
	
	public static boolean checkItemPickupAllowed(GameItem currItem)
	{
		boolean allowed = true;
		String currCode = currItem.getCode();
			
		if (isGoalCode(currCode))
		{
			if (currCode.equals(story.getCurrGoalCode()))
			{
				wrongItemFlag = false;
				allowed = true;
			}
			else
			{
				Goal currGoal = WorldInfo.searchGoals(currCode);
				wrongItemMsg = currGoal.getWrongItemMsg();
				wrongItemFlag = true;
				allowed = false;
			}
		}
		else
		{
			allowed = true;
		}
			
		return allowed;
	}
	
	private static boolean isGoalCode(String incomingCode)
	{
		boolean match = false;
		
		for (int i = 0; i < story.size(); i++)
		{
			if (story.getGoalCode(i).equals(incomingCode))
			{
				match = true;
			}
		}
		
		return match;
	}

	public static boolean checkWrongItemFlag()
	{
		return wrongItemFlag;
	}
	
	public static void markWrongItemFlag()
	{
		wrongItemFlag = true;
	}
	
	public static void clearWrongItemFlag()
	{
		wrongItemFlag = false;
	}
}