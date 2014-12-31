package rsg.objects;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;

import rsg.persistence.WorldInfo;
import rsg.processing.KeyCode;

public class Player extends GameCharacter
{	
	private final int BASE_ATTACK = 5;
	
	private ArrayList<GameItem> inventory;
	private int playerX;
	private int playerY;
	private GameWeapon playerWeapon;
	private Direction playerDirection;
	private GameObject playerBoat;
	private GameVehicle playerVehicle;
	private static String playerLeft;
	private static String playerBack;			
	private static String playerRight;
	private static String playerFront;

	private final String FOOT_CODE = "MC";
	
	public Player(String code, String name, Image image, int health, int maxHealth, int lives, int value, int startX, int startY)
	{
		super(code, name, image, health, maxHealth, value, "It's Me");
		this.playerDirection = Direction.South;
		this.inventory = new ArrayList<GameItem>();
		playerX = startX;
		playerY = startY;
		playerWeapon = null;
		playerBoat = null;
		
		//default player starts on foot
		playerVehicle = (GameVehicle)WorldInfo.search("MC00");
		playerLeft = FOOT_CODE + "01";
		playerBack = FOOT_CODE + "02";
		playerRight = FOOT_CODE + "03";
		playerFront = FOOT_CODE + "04";
	}
	
	public int getX()
	{
		return playerX;
	}

	public int getY()
	{
		return playerY;
	}
	
	public Direction getPlayerDirection()
	{

		return playerDirection;
	}
	
	public int getAttack()
	{
		int attack = (int)(Math.random() * BASE_ATTACK);
		
		if (hasWeapon())
		{
			attack += getWeaponValue();
		}
		
		return attack;
	}
	
	public ArrayList<GameItem> getInventory()
	{
		ArrayList<GameItem> result = new ArrayList<GameItem>();
		
		for(int i = 0; i < inventory.size(); i++)
		{
			result.add(inventory.get(i));
		}
		
		return result;
	}
	
	public boolean playerHasBoat()
	{
		boolean result = false;
		
		for(int i = 0; i < inventory.size() && result == false; i++)
		{
			if(inventory.get(i).getCode().startsWith("BOAT"))
			{
				result = true;
				playerBoat = inventory.get(i);
			}
		}
		
		return result;
	}
	
	public GameObject getPlayerBoat()
	{
		if(playerBoat == null)
		{
			playerHasBoat();
		}
		
		return playerBoat;
	}
	
	public void setX(int newX)
	{		
		playerX = newX;
	}

	public void setY(int newY)
	{
		playerY = newY;
	}
	
	public void setWeapon(GameItem newWeapon)
	{
		if (newWeapon instanceof GameWeapon)
		{
			playerWeapon = (GameWeapon)newWeapon;
		}
		else if (newWeapon == null)
		{
			playerWeapon = null;
		}
		else
		{
			System.out.println("That's not a weapon");
		}
	}
	
	public void setPlayerDirection(KeyCode keyCode)
	{
		switch(keyCode)
		{
			case KeyLeft: 
				setPlayerDirection(Direction.West); 
				super.setImage(WorldInfo.search(playerLeft).getImage());
				break;
			case KeyUp: 
				setPlayerDirection(Direction.North); 
				super.setImage(WorldInfo.search(playerBack).getImage());
				break;
			case KeyRight: 
				setPlayerDirection(Direction.East); 
				super.setImage(WorldInfo.search(playerRight).getImage());
				break;
			case KeyDown: 
				setPlayerDirection(Direction.South); 
				super.setImage(WorldInfo.search(playerFront).getImage());
				break;
			default:
				break;
		}		
	}
	
	private void setPlayerDirection(Direction toSet)
	{
		playerDirection = toSet;
	}
	
	public boolean pickUpGameItem(GameItem newItem)
	{
		boolean result = false;
		
		if(inventory.add(newItem))
		{
			result = true;
		}
		
		return result;
	}
	
	public void removeLastItem()
	{
		int pos = inventory.size()-1;
		
		inventory.remove(pos);
	}
	
	public boolean hasWeapon()
	{
		if (playerWeapon == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public int getWeaponValue()
	{
		int value = 0;
		
		if (playerWeapon != null)
		{
			value = playerWeapon.getValue();
		}
		
		return value;
	}

	public int getInventorySize()
	{
		return inventory.size();
	}

	public void createInventory()
	{
		inventory = new ArrayList<GameItem>();
	}
	
	private void setPlayerImages(String masterCode)
	{
		setPlayerLeft( (masterCode + "01") );
		setPlayerBack( (masterCode + "02") );
		setPlayerRight( (masterCode + "03") );
		setPlayerFront( (masterCode + "04") );
	}

	private void setPlayerLeft(String newLeft)
	{
		playerLeft = newLeft;
	}
	
	private void setPlayerRight(String newRight)
	{
		playerRight = newRight;
	}
	
	private void setPlayerFront(String newFront)
	{
		playerFront = newFront;
	}
	
	private void setPlayerBack(String newBack)
	{
		playerBack = newBack;
	}
	
	public void setPlayerVehicle(GameVehicle newVehicle)
	{
		playerVehicle = newVehicle;
		
		setPlayerImages(newVehicle.getVehicleCode());
	}
	
	public GameVehicle getPlayerVehicle()
	{
		return playerVehicle;
	}
}