package rsg.processing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import rsg.objects.GameObject;
import rsg.objects.Map;
import rsg.objects.Tile;
import rsg.persistence.WorldInfo;

public class MapCreator
{
	private static Tile[][] createMapTiles(String mapLocation) throws MapCreationException
	{
		Tile newTile = null;
		Tile[][] newMap = null;
		GameObject result = null;
		int i = 0;
		int j = 0;
		int k = 0;
		int lineCounter = 0;
		int rows = -1;
		int cols = -1;
		String[] mapTileTokens = null;
		String line = "";
		String[] imageCodes;
		BufferedReader fileIn = null;

		try
		{
			fileIn = new BufferedReader(new FileReader(mapLocation));
			
			//First line is dimensions
			line = fileIn.readLine();
			
			if (line == null)
			{
				fileIn.close();
				throw new MapCreationException("Map had no meta-data");
			}
			
			lineCounter++;
			mapTileTokens = line.split(",");

			if (mapTileTokens.length != 2)
			{
				fileIn.close();
				throw new MapCreationException("Rows and columns are not properly stated before reading in map data.");				
			}

			rows = Integer.parseInt(mapTileTokens[0].trim());
			cols = Integer.parseInt(mapTileTokens[1].trim());
			
			if (rows <= 0 || cols <= 0)
			{
				fileIn.close();
				throw new MapCreationException("Number of rows and columns must be greater than 0.");
			}
			
			newMap = new Tile[rows][cols];
			
			line = fileIn.readLine();
			lineCounter++;
			
			for (i = 0; line != null; i++)
			{
				if (i >= rows)
				{
					fileIn.close();
					throw new MapCreationException("More rows in file than stated in the first line of the file. Error" +
							" on line " + lineCounter);
				}
				
				//mapLines now contains all the images for this certain tile
				mapTileTokens = line.split("\\|");
				
				if (mapTileTokens.length != cols)
				{
					fileIn.close();
					throw new MapCreationException("More columns in file than stated in the first line of the file." +
							" Error " +	"on line " + lineCounter + " with " + cols + " columns read in.");
				}
				
				//Loop through every tile (i is row, this j is the column). Parse the tile and load into image list.
				for (j = 0; j < mapTileTokens.length; j++)
				{
					newTile = new Tile();
					mapTileTokens[j] = mapTileTokens[j].trim();
					
					imageCodes = mapTileTokens[j].split(",");
					
					//Loop through the list of strings we read for the singular tile.
					for (k = 0; k < imageCodes.length; k++)
					{
						result = WorldInfo.search(imageCodes[k].trim());
						if (result != null)
						{
							newTile.addGameObject(result);
						}
						
						else
						{
							fileIn.close();
							throw new MapCreationException("\nThe map code you are looking for does not exist in the World. Looking " +
									"at: "	+ mapTileTokens[j] + " in map row: " + (lineCounter -1) + " column: " + j + ". File: " + mapLocation);
						}
					}
					newMap[j][i] = newTile;
				}
				line = fileIn.readLine();
				lineCounter++;
			}
			
			if (lineCounter != rows + 2)
			{
				fileIn.close();
				throw new MapCreationException("Not enough rows read from map via relative to map meta data!");
			}
			fileIn.close();
		} 
		catch (IOException ioe)
		{
			System.out.println("Map location does not exist or missing map metadata (missing rows, columns) at head of file!\n");
			ioe.printStackTrace();
		}
		
		return newMap;
	}
	
	public static Map createMap(String location) throws MapCreationException
	{
		return new Map(createMapTiles(location), location);
	}
}