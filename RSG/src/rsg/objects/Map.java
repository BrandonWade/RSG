package rsg.objects;

import java.util.ArrayList;

public class Map
{
	public final static int GAME_TILE_SIZE = 20;
	public final static int MINIMAP_TILE_SIZE = 1;
	private static int fixedRows;
	private static int fixedCols;
	private Tile[][] grid;
	private String mapLocation;

	public Map(Tile[][] map, String location)
	{
		this.grid = map;
		fixedRows = map.length;
		fixedCols = map[0].length;
		mapLocation = location;
	}

	public ArrayList<GameObject> getTileImages(int x, int y)
	{
		return grid[x][y].getList();
	}

	public Tile getTile(int xVal, int yVal)
	{
		return grid[xVal][yVal];
	}
	
	public static int getRows()
	{
		return fixedRows;
	}
	
	public static int getCols()
	{
		return fixedCols;
	}
	
	public boolean isTileTraversable(int xVal, int yVal)
	{
		return grid[xVal][yVal].isTraversable();
	}
	
	public String getMapLocation()
	{
		return mapLocation;
	}

	public boolean removeItem(int xVal, int yVal)
	{
		return grid[xVal][yVal].removeLast();
	}

	public String getInteractionMessage(int interactX, int interactY)
	{
		return grid[interactX][interactY].getInteractionMessage();
	}
}