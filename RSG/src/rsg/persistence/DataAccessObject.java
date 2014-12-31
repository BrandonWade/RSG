package rsg.persistence;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import rsg.objects.GameCharacter;
import rsg.objects.GameItem;
import rsg.objects.GameObject;
import rsg.objects.GamePotion;
import rsg.objects.GameScenery;
import rsg.objects.GameVehicle;
import rsg.objects.GameWeapon;
import rsg.objects.Goal;
import rsg.objects.OurDisplay;
import rsg.objects.Projectile;

public class DataAccessObject implements DataAccess
{
	private String DBName;
	private String DBType;
	
	private Statement st1, st2;
	private Connection c1;
	private ResultSet rs1, rs2;
	
	private String cmdString;
	private static String EOF = "  ";
	
	public DataAccessObject (String dbName)
	{
		this.DBName = dbName;
	}
	
	public void open(String dbName)
	{
		String url;
		
		try
		{
			DBType = "HSQL";
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:database/" + dbName; // stored on disk mode
			c1 = DriverManager.getConnection(url, "SA", "");
			st1 = c1.createStatement();
			st2 = c1.createStatement();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
	}
	
	public void close()
	{
		try
		{	// commit all changes to the database
			cmdString = "shutdown compact";
			rs1 = st1.executeQuery(cmdString);
			rs2 = st2.executeQuery(cmdString);
			c1.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		System.out.println("Closed " +DBType +" database " +DBName);
	}

	public String processSQLError(Exception e)
	{
		String result;
		result = "*** SQL Error: " + e.getMessage();
		//e.printStackTrace();
		return result;
	}
	
	public ArrayList<GameObject> getGameScenery()
	{
		GameScenery newScenery;
		Image newImage;
		ArrayList<GameObject> scenery;
		String name;
		String interactMessage;
		boolean traversable;
		String codeString = EOF;
		Display display = OurDisplay.getDisplay();
		Image mapTile;
		boolean sailable;
		
		name = EOF;

		scenery = new ArrayList<GameObject>();
		try
		{
			cmdString = "Select * from Scenery";
			rs1 = st1.executeQuery(cmdString);
			//ResultSetMetaData md2 = rs2.getMetaData();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		try
		{
			while (rs1.next())
			{
				codeString = rs1.getString("Gamecode");
				name = rs1.getString("Name");
				newImage = new Image(display, rs1.getString("Filepath"));
				traversable = rs1.getBoolean("Traversable");
				interactMessage = rs1.getString("InteractMessage");
				sailable = rs1.getBoolean("Sailable");
				
				mapTile = new Image(display, rs1.getString("MapIconPath"));
				newScenery = new GameScenery(codeString, name, newImage, traversable, interactMessage, mapTile, sailable);
				
				//Hacky fix for creating the projectile.
				if(codeString.equals("ZZZZ"))
				{
					Projectile.create(newImage);
				}
				else
				{
					scenery.add(newScenery);
				}
			}
			rs1.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		return scenery;
	}
	
	public String[] getGameMapInfo()
	{
		String[] gameMap = new String[25];
		String holder = null; 
		int count = 0;
		
		try
		{
			cmdString = "Select * from maps";
			rs2 = st2.executeQuery(cmdString);
			//ResultSetMetaData md2 = rs2.getMetaData();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		try
		{
			while (rs2.next())
			{
				holder = Integer.toString(rs2.getInt("MapPos"));
				holder += ",";
				holder += rs2.getString("filepath");
				holder += ",";
				holder += Integer.toString(rs2.getInt("PosX"));
				holder += ",";
				holder += Integer.toString(rs2.getInt("PosY"));
				gameMap[count] = holder;
				count++;
			}
			rs2.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}

		return gameMap;
	}
	
	public ArrayList<GameItem> getGameItems()
	{
		GameItem newItem;
		Image newImage;
		ArrayList<GameItem> gameItems;
		String name;
		String interactMessage;
		boolean interactable;
		int value;
		String codeString = EOF;
		Display display = OurDisplay.getDisplay();
		
		name = EOF;
		gameItems = new ArrayList<GameItem>();
		
		try
		{
			cmdString = "Select * from Items";
			rs1 = st1.executeQuery(cmdString);
			//ResultSetMetaData md2 = rs2.getMetaData();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		try
		{
			while (rs1.next())
			{
				codeString = rs1.getString("Gamecode");
				name = rs1.getString("Name");
				newImage = new Image(display, rs1.getString("Filepath"));
				interactable = rs1.getBoolean("Interactive");
				value = rs1.getInt("value");
				interactMessage = rs1.getString("InteractMessage");
				newItem = new GameItem(codeString, name, newImage, interactable, value, interactMessage);
				gameItems.add(newItem);
			}
			rs1.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		//Get game potions
		try
		{
			cmdString = "Select * from POTIONS";
			rs1 = st1.executeQuery(cmdString);
			//ResultSetMetaData md2 = rs2.getMetaData();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		try
		{
			while (rs1.next())
			{
				codeString = rs1.getString("Gamecode");
				name = rs1.getString("Name");
				newImage = new Image(display, rs1.getString("Filepath"));
				interactable = rs1.getBoolean("Interactive");
				value = rs1.getInt("value");
				interactMessage = rs1.getString("InteractMessage");
				GamePotion newPotion = new GamePotion(codeString, name, newImage, interactable, value, interactMessage);
				gameItems.add(newPotion);
			}
			rs1.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		//Add weapons
		try
		{
			cmdString = "Select * from WEAPONS";
			rs1 = st1.executeQuery(cmdString);
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		try
		{
			while (rs1.next())
			{
				codeString = rs1.getString("Gamecode");
				name = rs1.getString("Name");
				newImage = new Image(display, rs1.getString("Filepath"));
				interactable = rs1.getBoolean("Interactive");
				value = rs1.getInt("value");
				interactMessage = rs1.getString("InteractMessage");
				GameWeapon newWeapon = new GameWeapon(codeString, name, newImage, interactable, value, interactMessage);
				gameItems.add(newWeapon);
			}
			rs1.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		//Add vehicles
		try
		{
			cmdString = "Select * from VEHICLES";
			rs1 = st1.executeQuery(cmdString);
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		try
		{
			while (rs1.next())
			{
				codeString = rs1.getString("Gamecode");
				name = rs1.getString("Name");
				newImage = new Image(display, rs1.getString("Filepath"));
				interactable = rs1.getBoolean("Interactive");
				value = rs1.getInt("value");
				interactMessage = rs1.getString("InteractMessage");
				GameVehicle newVehicle = new GameVehicle(codeString, name, newImage, interactable, value, interactMessage);
				gameItems.add(newVehicle);
			}
			rs1.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		return gameItems;
	}
	
	public ArrayList<GameCharacter> getGameCharacters()
	{
		GameCharacter newCharacter;
		Image newImage;
		ArrayList<GameCharacter> gameCharacters;
		String name;
		String interactMessage;
		int health;
		int maxHealth;
		int value;
		String codeString = EOF;
		Display display = OurDisplay.getDisplay();
		
		name = EOF;
		gameCharacters = new ArrayList<GameCharacter>();
		
		try
		{
			cmdString = "Select * from Characters";
			rs1 = st1.executeQuery(cmdString);
			//ResultSetMetaData md2 = rs2.getMetaData();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		try
		{
			while (rs1.next())
			{
				codeString = rs1.getString("Gamecode");
				name = rs1.getString("Name");
				newImage = new Image(display, rs1.getString("Filepath"));
				health = rs1.getInt("Health");
				maxHealth = rs1.getInt("Maxhealth");
				value = rs1.getInt("value");
				interactMessage = rs1.getString("InteractMessage");  
				newCharacter = new GameCharacter(codeString, name, newImage, health, maxHealth, value, interactMessage);
				gameCharacters.add(newCharacter);
			}
			rs1.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		return gameCharacters;
	}
	
	public ArrayList<Goal> getGameGoals()
	{
		Goal newGoal;
		ArrayList<Goal> goals;
		String initialDesc;
		int goalNum;
		String completedDesc;
		String wrongMsg;
		String codeString = EOF;

		goals = new ArrayList<Goal>();
		
		try
		{
			cmdString = "Select * from Goals";
			rs1 = st1.executeQuery(cmdString);
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		try
		{
			while (rs1.next())
			{
				goalNum = rs1.getInt("Goalnum");
				initialDesc = rs1.getString("Description");
				codeString = rs1.getString("Gamecode");
				completedDesc = rs1.getString("Completed");
				wrongMsg = rs1.getString("Wrong");
				
				newGoal = new Goal(goalNum,initialDesc,codeString,completedDesc,wrongMsg);
				goals.add(newGoal);
			}
			rs1.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		
		return goals;
	}
}