package rsg.objects;

import org.eclipse.swt.graphics.Image;

public class GameScenery extends GameObject
{
	private Image mapTile;
	
	public GameScenery(String code, String name, Image image, boolean traversable, String interactMessage, Image mapTile, boolean sailable)
	{
		super(code, name, image, traversable, interactMessage);
		this.mapTile = mapTile;
		
		if(sailable)
			super.setSailable(true);
	}
	
	public Image getMapTile()
	{
		return mapTile;
	}
	
	public boolean isTraversable()
	{
		return super.isTraversable();
	}
}