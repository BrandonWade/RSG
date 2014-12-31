package rsg.persistence;

import java.util.ArrayList;

import rsg.objects.Direction;
import rsg.objects.GameObject;
import rsg.objects.Map;
import rsg.processing.MapCreationException;
import rsg.processing.MapCreator;

public class World 
{
	private static Map[][] maps;
	private static DataAccess gameMapDB;
	private static int x = -1;
	private static int y = -1;
	private static int oldX = -1;
	private static int oldY = -1;
	private static boolean getSetUsed = false;
	
	private World(int mapsWidth, int mapsHeight)
	{
		maps = new Map[mapsHeight][mapsWidth];
	}
	
	public static void initalizeSuperMap(String mapPath) throws MapCreationException
	{
		initalizeSuperMap(MapCreator.createMap(mapPath));
	}
	
	public static void initalizeSuperMap(Map map) throws MapCreationException
	{
		new World(1,1);
		x = 0;
		y = 0;
		oldX = 0;
		oldY = 0;
		maps[x][y] = map;
		getSetUsed = true;
	}
	
	public static void initalizeSuperMap() throws MapCreationException
	{
		int mapSize = 0;
		String[] tokens;
		String curr = "";
		String[] mapData;
		
		gameMapDB = Services.getDataAccess();
		
		mapData = gameMapDB.getGameMapInfo();
		
		mapSize = (int) Math.ceil(Math.sqrt(mapData.length));
		new World(mapSize, mapSize);
		
		for (int i = 0; i < mapData.length; i++)
		{
			curr = mapData[i];
			tokens = curr.trim().split(",");
			
			if (tokens[1].equals(""))
			{
				maps[Integer.parseInt(tokens[2])][Integer.parseInt(tokens[3])] = null;
			}
			else
			{
				maps[Integer.parseInt(tokens[3])][Integer.parseInt(tokens[2])] = MapCreator.createMap(tokens[1]);
			}
		}
	}
	
	public static Map getSetSuperMap(int xVal, int yVal)
	{
		Map result = null;
		
		if (!getSetUsed)
		{
			oldX = x;
			oldY = y;
			x = convertValue(xVal);
			y = convertValue(yVal);
			result =  maps[x][y];
			getSetUsed = true;
		}
		
		return result;
	}
	
	public static Map getSuperMap(int xVal, int yVal)
	{
		if(xVal < 0 || xVal > maps.length - 1)
		{
			xVal = convertValue(xVal);	
		}
		
		if(yVal < 0 || yVal > maps.length -1)
		{
			yVal = convertValue(yVal);				
		}
		
		return maps[xVal][yVal];
	}
	
	public static int getCurrentMapX()
	{
		return x;
	}
	
	public static int getCurrentMapY()
	{
		return y;
	}
	
	public static int getOldMapX()
	{
		return oldX;
	}
	
	public static int getOldMapY()
	{
		return oldY;
	}
	
	public static Map[][] getWorldMap()
	{
		return maps;
	}
	
	private static int convertValue(int value) 
	{
		value = value % (maps.length);
		if (value < 0)
		{
			value = value + 3;
		}
		
		return value;
	}

	public static boolean addLocation(Map map, int x, int y)
	{
		boolean result = false;
		
		if (maps[x][y] == null)
		{
			maps[x][y] = map;
			result = true;
		}
		
		return result;
	}
	
	private static boolean checkMapSet()
	{
		return x != -1 && y != -1;
	}

	public static Map loadNewSuperMap(Direction playerDirection)
	{
		return getMapMath(playerDirection, true);
	}
	
	public static int getMapXLength()
	{
		if(maps != null)
		{
			return maps.length;
		}
		
		return -1;
	}
	
	public static int getMapYLength()
	{
		if(maps != null)
		{
			return maps[0].length;
		}
		
		return -1;
	}
	
	private static Map getMapMath(Direction playerDirection, boolean setValues)
	{

		int loadX = x;
		int loadY = y;
		Map result = null;

		if (!checkMapSet())
		{
			return null;
		}
		
		if (playerDirection == Direction.North)
		{
			if (y == 0)
			{
				loadY = maps[0].length - 1;
			}
			else
			{
				loadY = y - 1;
			}
		}
		else if (playerDirection == Direction.South)
		{
			if (y == maps[0].length - 1)
			{
				loadY = 0;
			}
			else
			{
				loadY++;
			}
		}
		else if (playerDirection == Direction.East)
		{
			if(x == maps.length - 1)
			{
				loadX = 0; 
			}
			else
			{
				loadX++;
			}
		}
		else if (playerDirection == Direction.West)
		{
			if (x == 0)
			{
				loadX = maps.length - 1;
			}
			else
			{
				loadX--;
			}
		}
		
		if (x != loadX || y != loadY)
		{
			if (setValues)
			{
				oldX = x;
				oldY = y;
				x = loadX;
				y = loadY;
			}
			result = maps[loadX][loadY];
		}
		
		return result;
	}

	public static Map getSuperMap(Direction playerDirection)
	{
		return getMapMath(playerDirection, false);
	}

	public static void set(int xVal, int yVal)
	{
		oldX = x;
		oldY = y;
		x = xVal;
		y = yVal;
	}
	
	public static Map getCurrentMap()
	{
		Map result = null;
		
		if (maps != null)
		{
			result = maps[x][y];
		}
		
		return result;
	}

	public static ArrayList<GameObject> getTileImages(int xVal, int yVal)
	{
		return maps[x][y].getTileImages(xVal, yVal);
	}
	
	public static String getInteractionText(int xVal, int yVal)
	{
		return maps[x][y].getInteractionMessage(xVal, yVal);	
	}

	public static boolean removeItem(int xVal, int yVal)
	{
		return maps[x][y].removeItem(xVal, yVal);
	}

	public static boolean isTileTraversable(int xVal, int yVal)
	{
		return maps[x][y].isTileTraversable(xVal, yVal);
	}

	public static void destroy()
	{
		for (int i = 0; i < maps.length; i++)
		{
			for (int j = 0; j < maps.length; j++)
			{
				maps[i][j] = null;
			}
		}
		
		x = -1;
		y = -1;
		oldX = -1;
		oldY = -1;
		getSetUsed = false;
		maps = null;
	}

	public static boolean isNextMapTileTraversable(Direction playerDirection, int xVal, int yVal)
	{
		return getMapMath(playerDirection, false).isTileTraversable(xVal, yVal);
	}

	public static void setMap(Map map, int x, int y) 
	{
		maps[x][y] = map;
	}
}