package rsg.objects;

import rsg.presentation.GuiWindow;

public class Goal {

	private String initDesc;
	private String compDesc;
	private boolean completed;
	private String itemCode;
	private int goalNum;
	private String wrongMsg;
	
	public Goal (int goalNum, String initDesc, String itemCode, String compDesc, String wrongMsg)
	{
		this.goalNum = goalNum;
		this.initDesc = initDesc;
		this.itemCode = itemCode;
		this.compDesc = compDesc;
		this.wrongMsg = wrongMsg;
		completed = false;
	}
	
	public int getNum()
	{
		return goalNum;
	}
	
	public String getWrongItemMsg()
	{
		return wrongMsg;
	}
	
	public boolean isCompleted()
	{
		return completed;
	}
	
	public String getDescription()
	{
		return initDesc;
	}
	
	public String getCode()
	{
		return itemCode;
	}
	
	public boolean compareItems(GameItem toCompare)
	{
		boolean result = false;
		
		String compareCode = toCompare.getCode();
		
		if (compareCode.equals(itemCode))
		{
			result = true;
			GuiWindow.updateGameOutput(compDesc);
		}
		
		return result;
	}
}