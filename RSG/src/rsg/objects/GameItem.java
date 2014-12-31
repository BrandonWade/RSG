package rsg.objects;

import org.eclipse.swt.graphics.Image;

public class GameItem extends GameObject
{
	private int value;

	public GameItem(String code, String name, Image image, boolean interactive, int value, String interactMessage)
	{
		super(code, name, image, false, interactMessage);
		this.value = value;
	}

	public int setValue (int newValue)
	{
		if (newValue >= 0)
		{
			value = newValue;
		}
		
		return value;
	}
	
	public int getValue()
	{
		return value;
	}
}