package rsg.objects;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;

import rsg.processing.KeyCode;


public class GamePlayer
{
	private static Player player = null;
	
	private GamePlayer(String code, String characterName, Image image, int health, int maxHealth, int lives, int value, int startX, int startY)
	{
		player = new Player(code, characterName, image, health, maxHealth, lives, value, startX, startY);
	}
	
	public static boolean createPlayer(Player newPlayer)
	{
		boolean result = false;
		
		if (player == null)
		{
			result = true;
			player = newPlayer;
		}
		
		return result;
	}

	public static void createPlayer(String code, String characterName, Image image, int health, int maxHealth, int lives, int value, int startX, int startY)
	{		
		new GamePlayer(code, characterName, image, health, maxHealth, lives, value, startX, startY);
	}
	
	public static int getPlayerAttack()
	{
		return player.getAttack();
	}

	public static Image getImage()
	{
		return player.getImage();
	}

	public static int getX()
	{
		return player.getX();
	}

	public static int getY()
	{
		return player.getY();
	}

	public static Direction getPlayerDirection()
	{
		return player.getPlayerDirection();
	}

	public static void setX(int newX)
	{
		player.setX(newX);
	}
	
	public static void setY(int newY)
	{
		player.setY(newY);
	}

	public static boolean pickUpGameItem(GameItem gameItem)
	{
		return player.pickUpGameItem(gameItem);
	}

	public static int getInventorySize()
	{
		return player.getInventorySize();
	}

	public static ArrayList<GameItem> getInventory()
	{
		return player.getInventory();
	}

	public static void setPlayerDirection(KeyCode keyCode)
	{
		player.setPlayerDirection(keyCode);		
	}

	public static int increaseHealth(int changeAmt)
	{
		return player.increaseHealth(changeAmt);
	}
	
	public static int decreaseHealth(int changeAmt)
	{
		return player.decreaseHealth(changeAmt);
	}

	public static int getHealth()
	{
		return player.getHealth();
	}

	public static String getName()
	{
		return player.getName();
	}

	public static void setName(String newName)
	{
		player.setName(newName);
	}

	public static void resetInventory()
	{
		player.createInventory();
	}
	
	public static void removeLastItem()
	{
		player.removeLastItem();
	}
	
	public static void setWeapon(GameWeapon newWeapon)
	{
		player.setWeapon(newWeapon);
	}
	
	public static int getWeaponValue()
	{
		int value = 0;
		
		if (player.hasWeapon())
		{
			value = player.getWeaponValue();
		}
		
		return value;
	}
	
	public static boolean hasWeapon()
	{
		return player.hasWeapon();
	}
	
	public static void delete()
	{
		player = null;
	}

	public static void setPosition(int xVal, int yVal)
	{
		setX(xVal);
		setY(yVal);
	}

	public static int getMaxHealth()
	{
		return player.getMaxHealth();
	}
	
	public static boolean playerHasBoat()
	{
		return player.playerHasBoat();
	}
	
	public static GameObject getPlayerBoat()
	{
		return player.getPlayerBoat();
	}
	
	public static void setPlayerVehicle(GameVehicle newVehicle)
	{
		player.setPlayerVehicle(newVehicle);
	}
	
	public static GameVehicle getDefaultVehicle()
	{
		return player.getPlayerVehicle();
	}
}
