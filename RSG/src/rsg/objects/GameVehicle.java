package rsg.objects;

import org.eclipse.swt.graphics.Image;

public class GameVehicle extends GameItem{

	public GameVehicle(String code, String name, Image image, boolean interactive, int value, String interactMessage)
	{
		super(code, name, image, interactive, value, interactMessage);
	}
	
	public String getVehicleCode()
	{
		// Take first two letters from the vehicle code
		String substring = getCode().substring(0,2);
		
		return substring;
	}
	
	public int getVehicleDelay()
	{
		return super.getValue();
	}
	
}
