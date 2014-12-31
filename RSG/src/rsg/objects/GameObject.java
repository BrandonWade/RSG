package rsg.objects;

import org.eclipse.swt.graphics.Image;

public abstract class GameObject
{
	private String code;
	private String name;
	private Image image;
	private boolean traversable;
	private String interactMessage;
	private boolean sailable;
	private boolean rideable;

	public GameObject(String code, String name, Image image, boolean traversable, String interactMessage)
	{
		this.code = code; //represents sequence of characters that this object will be represented by in text file/db
		this.name = name;
		this.image = image;
		this.traversable = traversable;
		this.interactMessage = interactMessage;
		this.sailable = false;
		this.rideable = false;
	}
	
	public void setCode(String newCode)
	{
		code = newCode;
	}
	
	public void setSailable(boolean toSet)
	{
		sailable = toSet;
	}
	
	public void setRideable(boolean toSet)
	{
		rideable = toSet;
	}

	public String getCode()
	{
		return code;
	}

	public void setName(String newName)
	{
		if (newName !=  "" && newName != null)
		{
			name = newName;
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setImage(Image newImage)
	{
		image =  newImage;
	}
	
	public void setTraversable(boolean newTraversable)
	{
		traversable = newTraversable;
	}

	public boolean isTraversable()
	{
		boolean retVal = false;
		
		if(sailable)
		{
			retVal = GamePlayer.playerHasBoat();
		}
		else
		{
			retVal = traversable;
		}
		
		return retVal;
	}
	
	public boolean isSailable()
	{
		return sailable;
	}
	
	public boolean isRideable()
	{
		return rideable;
	}
	
	public String getInteractionMessage()
	{
		return interactMessage;
	}
}