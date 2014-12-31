package rsg.objects;

import org.eclipse.swt.graphics.Image;

public class GameWeapon extends GameItem
{
	public GameWeapon(String code, String name, Image image,boolean interactive, int value, String interactMessage) 
	{
		super(code, name, image, interactive, value, interactMessage);
	}
}
