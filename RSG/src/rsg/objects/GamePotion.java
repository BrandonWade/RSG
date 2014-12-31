package rsg.objects;

import org.eclipse.swt.graphics.Image;

public class GamePotion extends GameItem
{	
	public GamePotion(String code, String name, Image image, boolean interactive, int value, String interactMessage)
	{
		super(code, name, image, interactive, value, interactMessage);
	}
}