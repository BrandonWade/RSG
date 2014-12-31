package rsg.objects;

import java.util.ArrayList;

public class Tile
{
	private ArrayList<GameObject> imageList;

	public Tile()
	{
		this.imageList = new ArrayList<GameObject>();
	}

	public boolean isTraversable()
	{
		boolean traversable = true;

		for (int i = 0; i < imageList.size() && traversable; i++)
		{
			if (!imageList.get(i).isTraversable())
			{
				traversable = false;
			}
		}
		
		return traversable;
	}

	public ArrayList<GameObject> getList()
	{
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		
		for (int i = 0; i < imageList.size(); i++)
		{
			result.add(imageList.get(i));
		}
		
		return result;
	}
	
	public String getInteractionMessage()
	{
		String toReturn;
		
		if(imageList.size() < 1)
			toReturn = null;
		else
			toReturn = imageList.get(imageList.size()-1).getInteractionMessage();
		
		return toReturn;
	}
	
	public void addGameObject(GameObject newObj)
	{
		imageList.add(newObj);
	}
	
	public boolean removeLast()
	{
		if (imageList.size() > 0)
		{
			imageList.remove(imageList.size() -1);
			return true;
		}
		return false;
	}
}