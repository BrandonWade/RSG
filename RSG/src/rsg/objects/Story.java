package rsg.objects;

import java.util.ArrayList;

public class Story
{
	private ArrayList<Goal> goalList;
	private int currGoalNum;
	private int storySize;
	
	public static final String endText = "All goals completed!";
	
	public Story (ArrayList<Goal> goalList)
	{
		this.goalList = goalList;
		currGoalNum = 0;
		storySize = goalList.size();
	}
	
	public boolean checkGoalItem (GameItem newItem)
	{
		boolean result = false;
		
		if (currGoalNum < goalList.size())
		{
			Goal currGoal = goalList.get(currGoalNum);
			result = currGoal.compareItems(newItem);
		}
	
		return result;
	}
	
	public String getGoalCode (int num)
	{
		return goalList.get(num).getCode();
	}

	public void setNextGoal()
	{
		if (storySize > currGoalNum)
		{
			currGoalNum++;
		}
	}
	
	public String getCurrGoalDesc()
	{
		String desc = endText;
		
		if (currGoalNum < storySize)
		{
			Goal currGoal = goalList.get(currGoalNum);
			desc = currGoal.getDescription();
		}
		
		return desc;
	}
	
	public String getCurrGoalCode()
	{
		String goalCode = "0000";
		
		if (storySize > currGoalNum)
		{
			goalCode =  goalList.get(currGoalNum).getCode();
		}
		
		return goalCode;
	}
	
	public int size()
	{
		return goalList.size();
	}
}
