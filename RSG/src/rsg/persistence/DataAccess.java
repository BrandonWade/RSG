package rsg.persistence;

import java.util.ArrayList;

import rsg.objects.GameCharacter;
import rsg.objects.GameItem;
import rsg.objects.GameObject;
import rsg.objects.Goal;

public interface DataAccess
{	
	public void open(String dbName);
	public void close();
	public ArrayList<GameObject> getGameScenery();
	public String[] getGameMapInfo();
	public ArrayList<GameItem> getGameItems();
	public ArrayList<GameCharacter> getGameCharacters();
	public ArrayList<Goal> getGameGoals();
}