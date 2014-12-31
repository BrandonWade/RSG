package rsg.processing;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;

import acceptanceTests.EventLoop;
import rsg.objects.Direction;
import rsg.objects.GameCharacter;
import rsg.objects.GameItem;
import rsg.objects.GameObject;
import rsg.objects.GamePlayer;
import rsg.objects.GameVehicle;
import rsg.objects.Map;
import rsg.objects.OurDisplay;
import rsg.objects.Projectile;
import rsg.persistence.Services;
import rsg.persistence.World;
import rsg.persistence.WorldInfo;
import rsg.presentation.GuiWindow;
import rsg.processing.KeyCode;

public class GameRunner
{
	private static String characterName = "New Player";
	private static String playerStart = "MC04";
	
	public static void main(String[] args)
	{		
		try
		{
			initializeGame();
		}
		catch(MapCreationException e)
		{
			System.out.println("MapCreation Exception: " + e.getMessage());
		}
		
		 //Logic for handling window close
		if (EventLoop.isEnabled())
		{
			while (!GuiWindow.isDisposed())										
			{
				if (!OurDisplay.readAndDispatch())
				{
					OurDisplay.forceSleep();
				}
			}
			GuiWindow.destroy();
		}
		
		Services.closeDataAccess();
		System.out.println("Game Over");
	}
	
	public static void initializeGame() throws MapCreationException
	{	
		GameAudio gameMusic = new GameAudio();
		
		Services.createDataAccess("OBJS");
		WorldInfo.initializeWorld();
		
		GamePlayer.createPlayer(playerStart, characterName, WorldInfo.search(playerStart).getImage(), 40, 250, 20, 500, 19, 4);
		GuiWindow.openWelcome();
		GuiWindow.createGUI();
		World.initalizeSuperMap();
		GuiWindow.updateStats();
		GuiWindow.updateGameOutput("Welcome to the world of RSG. Your COMP3350 assignment is due today! I hope you can get it handed in on time...");
		
		GuiWindow.drawMap(World.getSetSuperMap(4, 1));
		
		new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					for (int i = 0; i < World.getCurrentMap().getTile(GamePlayer.getX(), GamePlayer.getY()).getList().size(); i++)
					{
						GuiWindow.drawImage(World.getCurrentMap().getTile(GamePlayer.getX(), GamePlayer.getY()).getList().get(i).getImage(), GamePlayer.getX(), GamePlayer.getY());
					}
					
					GuiWindow.drawImage(GamePlayer.getImage(), GamePlayer.getX(), GamePlayer.getY());
					
					try
					{
						Thread.sleep(25);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		new Thread(new Runnable()
		{
			public void run()
			{
				Direction oldDirection = GamePlayer.getPlayerDirection();
				Image northFrame1 = WorldInfo.search("MC02").getImage();
				Image northFrame2 = WorldInfo.search("MC06").getImage();
				Image southFrame1 = WorldInfo.search("MC04").getImage();
				Image southFrame2 = WorldInfo.search("MC08").getImage();
				
				while (true)
				{
					if (oldDirection == GamePlayer.getPlayerDirection())
					{
						switch (GamePlayer.getPlayerDirection())
						{
							case North:
								GamePlayer.setImage(northFrame1);
								
								try
								{
									Thread.sleep(500);
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								}
								
								GamePlayer.setImage(northFrame2);
								
								try
								{
									Thread.sleep(500);
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								}
								break;
							case South:
								GamePlayer.setImage(southFrame1);
								
								try
								{
									Thread.sleep(500);
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								}
								
								GamePlayer.setImage(southFrame2);
								
								try
								{
									Thread.sleep(500);
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								}
								break;
							case West:
								break;
							case East:
								break;
							default:
								break;
						}
					}
					
					oldDirection = GamePlayer.getPlayerDirection();
				}
			}
		}).start();
		
		GuiWindow.drawMiniMap();
		
		GameVehicle defaultVehicle = GamePlayer.getDefaultVehicle();
		GuiWindow.addToInventory(defaultVehicle);
		
		gameMusic.startMusic();
	}
	
	public static void processCharacterMove(KeyCode keyCode)
	{
		int oldX = -1;
		int oldY = -1;
		int newX = -1;
		int newY = -1;
		boolean reDraw;
		Direction oldDirection = GamePlayer.getPlayerDirection();
		ArrayList<GameObject> imageList = null;
		
		oldX = GamePlayer.getX();
		oldY = GamePlayer.getY();
		imageList = World.getTileImages(oldX, oldY);
		reDraw = checkPrevTileMovement(keyCode);
		
		if (reDraw)
		{
			newX = GamePlayer.getX();
			newY = GamePlayer.getY();
			drawOrdered(imageList, oldX, oldY, false);
			drawOrdered(World.getTileImages(newX, newY), newX, newY, true);
		}
		else
		{
			checkNewMapLoaded(oldY, oldX, oldDirection);
		}
	}
	
	private static void checkNewMapLoaded(int oldY, int oldX, Direction oldDirection)
	{
		int currX = -1;
		int currY = -1;
		boolean newMap;
		Direction playerDirection = null;
		ArrayList<GameObject> arrayList = null;
		
		currX = GamePlayer.getX();
		currY = GamePlayer.getY();
		newMap = isNewMap(oldX, currX, oldY, currY);
		playerDirection = GamePlayer.getPlayerDirection();
		
		if (newMap)
		{
			if (newMapValidTransition())
			{
				GuiWindow.removeBullet();
				GuiWindow.drawMap(World.loadNewSuperMap(playerDirection));
				arrayList = World.getTileImages(currX, currY);
				drawOrdered(arrayList, currX, currY, true);	
			}
			//Since the new map has an invalid square, don't move to it. THIS SHOULD NEVER HAPPEN, BUT IS HERE JUST IN CASE
			//TO BE HANDLED PROPERLY
			else
			{
				GamePlayer.setX(oldX);
				GamePlayer.setY(oldY);
				if(playerDirection != oldDirection)
				{
					arrayList = World.getTileImages(oldX, oldY);
					drawOrdered(arrayList, oldX, oldY, true);	
				}
			}
		}
		//Player is stuck on an object, make them face it.
		else
		{
			arrayList = World.getTileImages(GamePlayer.getX(), GamePlayer.getY());
			drawOrdered(arrayList, GamePlayer.getX(), GamePlayer.getY(), true);	
		}
	}

	public static boolean isNewMap(int oldX, int currX, int oldY, int currY)
	{
		boolean newYMap = ((oldY == 0 && currY == (Map.getRows() - 1)) || (oldY == (Map.getRows() - 1) && currY == 0)) && currX == oldX;
		boolean newXMap = ((oldX == 0 && currX == (Map.getCols() - 1)) || (oldX == (Map.getCols() - 1) && currX == 0)) && currY == oldY;
		
		return newYMap || newXMap;
	}

	public static boolean newMapValidTransition()
	{
		boolean result = false;
		final int MAX = Map.getCols() -1; //Columns because rows MUST be the same (square maps).
		final int MIN = 0;
		int xVal = GamePlayer.getX();
		int yVal = GamePlayer.getY();
		Direction playerDirection = GamePlayer.getPlayerDirection();
		
		if (playerDirection == Direction.North)
		{
			result = World.isNextMapTileTraversable(playerDirection, xVal, MAX);
		}
		if (playerDirection == Direction.South)
		{
			result = World.isNextMapTileTraversable(playerDirection, xVal, MIN);
		}
		if (playerDirection == Direction.East)
		{
			result = World.isNextMapTileTraversable(playerDirection, MIN, yVal);
		}
		if (playerDirection == Direction.West)
		{
			result = World.isNextMapTileTraversable(playerDirection, MAX, yVal);
		}
		
		return result;
	}

	private static void drawOrdered(ArrayList<GameObject> imageList, int oldX, int oldY, boolean drawCharacter)
	{
		int i;
		GameObject curr = null;
		boolean playerDrawn = false;
		boolean sailableTile = false;
		
		for (i = 0; i < imageList.size(); i++)
		{
			curr = imageList.get(i);
			
			if(curr.isSailable())
				sailableTile = true;
			
			if (curr.getCode().equalsIgnoreCase("P") && drawCharacter)
			{
				if(sailableTile)
				{
					//GuiWindow.drawImage(GamePlayer.getPlayerBoat().getImage(), oldX, oldY);
				}
				//GuiWindow.drawImage(GamePlayer.getImage(), oldX, oldY);
				playerDrawn = true;
			}
			
			else
			{
				GuiWindow.drawImage(imageList.get(i).getImage(), oldX, oldY);
			}
		}
		
		if (playerDrawn == false && drawCharacter)
		{
			if(sailableTile)
			{
				//GuiWindow.drawImage(GamePlayer.getPlayerBoat().getImage(), oldX, oldY);
			}
			//GuiWindow.drawImage(GamePlayer.getImage(), oldX, oldY);
		}
	}

	public static void processInteraction()
	{
		int interactX = -1;
		int interactY = -1;
		int playerX = GamePlayer.getX();
		int playerY = GamePlayer.getY();
		String interactMessage = "";
		ArrayList<GameObject> tileList = null;
		Direction playerDirection = GamePlayer.getPlayerDirection();
		
		switch (playerDirection)
		{
			case North:
				if (playerY > 0)
				{
					interactX = playerX;
					interactY = playerY - 1;
				}
				break;
			case East:
				if (playerX < (Map.getRows() - 1))
				{
					interactX = playerX + 1;
					interactY = playerY;
				}
				break;
			case South:
				if (playerY < (Map.getCols() - 1))
				{
					interactX = playerX;
					interactY = playerY + 1;
				}
				break;
			case West:
				if (playerX > 0)
				{
					interactX = playerX - 1;
					interactY = playerY;
				}
				break;
		}
		
		if(interactX != -1 && interactY != -1)
		{
			tileList = World.getTileImages(interactX, interactY);
			interactMessage = World.getInteractionText(interactX, interactY);
			
			if (tileList != null)
			{
				if (tileList.get(tileList.size() - 1) instanceof GameItem)
				{
					interactObject(tileList, interactX, interactY);
				}
				
				if (tileList.get(tileList.size() - 1) instanceof GameCharacter)
				{
					interactMessage = interactPlayer(tileList, interactX, interactY);
				}
			}
			determineOutput(interactMessage);
		}
		else if (interactX == -1 || (interactX == Map.getCols() - 1) || interactY == -1 || (interactY == Map.getRows() - 1))
		{
			determineOutput("");
		}
	}
	
	private static String interactPlayer(ArrayList<GameObject> tileList, int interactX, int interactY)
	{
		String interactMessage = "";
		GameCharacter foundCharacter;

		foundCharacter = (GameCharacter) tileList.get(tileList.size() - 1);
		interactMessage = BattleRunner.runBattle(foundCharacter, interactX, interactY);
		
		GuiWindow.updateStats();
		
		return interactMessage;
	}

	private static void interactObject(ArrayList<GameObject> tileList, int interactX, int interactY)
	{
		boolean interactAllowed;
		interactAllowed = pickUpGameItem(tileList);
		
		if (interactAllowed)
		{
			World.removeItem(interactX, interactY);
			for (int i = 0; i < tileList.size(); i++)
			{
				//GuiWindow.drawImage(tileList.get(i).getImage(), interactX, interactY);
			}
		}
	}

	private static void determineOutput(String itemMsg)
	{
		if (StoryManager.checkGoalFlag())
		{
			StoryManager.outputCompleted();
		}
		else if (StoryManager.checkWrongItemFlag())
		{
			GuiWindow.updateGameOutput(StoryManager.getWrongItemMsg());
			StoryManager.clearWrongItemFlag();
		}
		else
		{
			GuiWindow.updateGameOutput(itemMsg);
		}
	}

	private static boolean pickUpGameItem(ArrayList<GameObject> tileList)
	{
		boolean success = false;
		
		GameObject tileItem = tileList.get(tileList.size() - 1);
		
		if (tileItem instanceof GameItem)
		{
			if (StoryManager.checkItemPickupAllowed((GameItem)tileItem))
			{
				GamePlayer.pickUpGameItem((GameItem)tileList.get(tileList.size() - 1));
				tileList.remove(tileList.size() - 1);
				GuiWindow.addToInventory(tileItem);
				success = true;
			}
			else
			{
				GuiWindow.updateItemNotAllowedOutput((GameItem)tileItem);
			}
			StoryManager.checkGoalCompleted((GameItem)tileItem);	
		}
		
		return success;
	}

	//Returns true if we should draw the previous square the character was in
	public static boolean checkPrevTileMovement(KeyCode keyCode)
	{
		int newX = GamePlayer.getX();
		int newY = GamePlayer.getY();
		Direction oldDirection = GamePlayer.getPlayerDirection();
		boolean result = false;
		
		//Always update the players direction
		GamePlayer.setPlayerDirection(keyCode);
		
		if (keyCode == KeyCode.KeyLeft)									
		{
			if (newX - 1 >= 0)														
			{
				if (World.isTileTraversable(newX - 1, newY))
				{
					newX = newX - 1;
					result = true;
				}
			}
			else
			{
				newX = Map.getCols() - 1;
			}
		}
		else if (keyCode == KeyCode.KeyUp)								
		{
			if (newY - 1 >= 0)														
			{
				if (World.isTileTraversable(newX, newY - 1))
				{
					newY = newY - 1;
					result = true;
				}
			}
			else
			{
				newY = Map.getRows() - 1;
			}
		}
		else if (keyCode == KeyCode.KeyRight)							
		{
			if ((newX + 1) < Map.getCols())										
			{
				if (World.isTileTraversable(newX + 1, newY))
				{
					newX = newX + 1;
					result = true;
				}
			}
			else
			{
				newX = 0;
			}
		}
		else if (keyCode == KeyCode.KeyDown)	
		{
			if ((newY + 1) < Map.getRows())										
			{
				if (World.isTileTraversable(newX, newY + 1))
				{
					newY = newY + 1;
					result = true;
				}
			}
			
			else
			{
				newY = 0;
			}
		}
		//If we need to redraw the player Since they've moved.
		if  (GamePlayer.getX() != newX || GamePlayer.getY() != newY || GamePlayer.getPlayerDirection() != oldDirection)
		{
			GamePlayer.setX(newX);
			GamePlayer.setY(newY);
		}
		
		return result;
	}
	
	public static void processWeaponInteraction()
	{
		if (GamePlayer.hasWeapon())
		{
			GuiWindow.removeWeapon();
			GamePlayer.setWeapon(null);
		}
	}
	
	public static void increasePlayerHealth(int changeAmt)
	{
		GamePlayer.increaseHealth(changeAmt);
		GuiWindow.updateStats();
	}

	public static boolean drawNewBulletPos()
	{
		boolean result = true;
		int x = Projectile.getX();
		int y = Projectile.getY();
		Direction direction = Projectile.getDirection();
		int newX = getAdjustedX(direction, x);
		int newY = getAdjustedY(direction, y);
		ArrayList<GameObject> imageList = null;
		
		//Retrieve the square in front of the bullet in the direction that they are facing. If this isn't further than an edge case,
		//Draw it.
		if ((newX >= 0 && newX <= 23) && (newY >= 0 && newY <= 23))
		{
			//Redraw current bullet tile, draw new bullet.
			imageList = World.getTileImages(x, y);
			for (int i = 0; i < imageList.size(); i++)
			{
				//GuiWindow.drawImage(imageList.get(i).getImage(), x, y);
			}
			//GuiWindow.drawImage(Projectile.getImage(), newX, newY);
			Projectile.setPosition(newX, newY);
			
			if (x == GamePlayer.getX() && y == GamePlayer.getY())
			{
				//Redraw the player.
				//GuiWindow.drawImage(GamePlayer.getImage(), x, y);
			}
		}
		
		else
		{
			//Bullet has left the screen.
			imageList = World.getTileImages(x, y);
			for (int i = 0; i < imageList.size(); i++)
			{
				//GuiWindow.drawImage(imageList.get(i).getImage(), x, y);
			}
			result = false;
		}
		return result;
	}
	
	private static int getAdjustedX(Direction direction, int x)
	{
		int newX = -1;
		
		switch(direction)
		{
			case North:
				newX = x;
				break;
			case East:
				newX = x + 1;
				break;
			case South:
				newX = x;
				break;
			case West:
				newX = x - 1;
				break;
			default:
				break;
		}
		return newX;
	}
	
	private static int getAdjustedY(Direction direction, int y)
	{
		int newY = -1;
		
		switch(direction)
		{
			case North:
				newY = y - 1;
				break;
			case East:
				newY = y;
				break;
			case South:
				newY = y + 1;
				break;
			case West:
				newY = y;
				break;
			default:
				break;
		}
		return newY;
	}

	public static void fireWeapon()
	{
		Direction direction = GamePlayer.getPlayerDirection();
		int bulletX = GamePlayer.getX();
		int bulletY = GamePlayer.getY();


		Projectile.set(direction, bulletX, bulletY);
		//Check if the player can actually shoot this thing.
		if (GuiWindow.isWeaponShootable() && validBulletFire(direction, bulletX, bulletY))
		{
			GuiWindow.fireWeapon();	
		}
	}

	private static boolean validBulletFire(Direction direction, int bulletX, int bulletY) 
	{
		bulletX = getAdjustedX(direction, bulletX);
		bulletY = getAdjustedY(direction, bulletY);
		
		return (bulletX != 24 && bulletX != -1) && (bulletY != 24 && bulletY != -1);
	}
}