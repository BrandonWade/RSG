package rsg.persistence;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;

import rsg.objects.GameCharacter;
import rsg.objects.GameItem;
import rsg.objects.GameObject;
import rsg.objects.Goal;
import rsg.objects.OurDisplay;

public class WorldInfo 
{
	final static String GAMEOVERSCREENPATH = "res/assets/art/display/lose_screen.png";
	final static String GAMEWINSCREENPATH = "res/assets/art/display/win_screen.png";
	public final static Image gameOverScreen = new Image(OurDisplay.getDisplay(), GAMEOVERSCREENPATH);
	public final static Image gameWinScreen = new Image(OurDisplay.getDisplay(), GAMEWINSCREENPATH);
	
	private static DataAccess gameObjsDB;
	private static ArrayList<GameObject> gameScenery;
	private static ArrayList<GameItem> gameItems;
	private static ArrayList<GameCharacter> gameCharacters;
	private static ArrayList<Goal> gameGoals;
	
	public static void initializeWorld()
	{
		Services.getDataAccess();
		gameObjsDB = (DataAccess) Services.getDataAccess();
		gameScenery = gameObjsDB.getGameScenery();		
		gameItems = gameObjsDB.getGameItems();
		gameCharacters = gameObjsDB.getGameCharacters();
		gameGoals = gameObjsDB.getGameGoals();
	}
	
	public static ArrayList<GameObject> getObjects()
	{
		return gameScenery;
	}
	
	public static ArrayList<Goal> getGoals()
	{
		return gameGoals;
	}
	
	public static Goal searchGoals(String code)
	{
		int count = 0;
		Goal curr = null;
		Goal result = null;
		boolean found = false;

		while (!found && count < gameGoals.size())
		{
			curr = gameGoals.get(count);
			if (curr.getCode().equalsIgnoreCase(code))
			{
				result = curr;
				found = true;
			}
			count++;
		}
		return result;
	}
	
	public static GameObject search(String code)
	{
		int count = 0;
		GameObject curr = null;
		GameObject result = null;
		boolean found = false;

		while (!found && count < gameScenery.size())
		{
			curr = gameScenery.get(count);
			if (curr.getCode().equalsIgnoreCase(code))
			{
				result = curr;
				found = true;
			}
			count++;
		}
		
		count = 0;
		while (!found && count < gameItems.size())
		{
			curr = gameItems.get(count);
			if (curr.getCode().equalsIgnoreCase(code))
			{
				result = curr;
				found = true;
			}
			count++;
		}
		
		count = 0;
		while (!found && count < gameCharacters.size())
		{
			curr = gameCharacters.get(count);
			if (curr.getCode().equalsIgnoreCase(code))
			{
				result = curr;
				found = true;
			}
			count++;
		}		
		return result;
	}
}