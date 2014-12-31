package rsg.objects;

import org.eclipse.swt.graphics.Image;

public class Projectile
{
	private static Direction direction;
	private static Image image;
	private static int currX;
	private static int currY;
	
	public static int getX()
	{
		return currX;
	}
	
	public static int getY()
	{
		return currY;
	}
	
	public static Direction getDirection()
	{
		return direction;
	}
	
	public static Image getImage()
	{
		return image;
	}
	
	public static void setPosition(int x, int y)
	{
		currX = x;
		currY = y;
	}

	public static void create(Image newImage)
	{
		Projectile.image = newImage;
	}

	public static void set(Direction newDirection, int adjustedX, int adjustedY)
	{
		Projectile.direction = newDirection;
		Projectile.currX = adjustedX;
		Projectile.currY = adjustedY;
	}
}
