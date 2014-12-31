package rsg.processing;

import rsg.objects.GamePlayer;
import rsg.persistence.World;
import rsg.presentation.GuiWindow;

public class WindowInput
{
	private static boolean acceptInput = true;
	private static KeyCode unlockKey = null;
	
	public static void processKeyboardInput(KeyCode keyCode)
	{
		if (!acceptInput && keyCode != null && keyCode == unlockKey)
		{
			unfreezeInput();
			unlockKey = null;
			GuiWindow.drawMap(World.getCurrentMap());
			//GuiWindow.drawImage(GamePlayer.getImage(), GamePlayer.getX(), GamePlayer.getY());
		}
		
		if (acceptInput)
		{
			if (validMovementKey(keyCode))
			{
				GameRunner.processCharacterMove(keyCode);
			}
			else if (validInteractKey(keyCode))
			{
				GameRunner.processInteraction();
			}
			else if (validWeaponKey(keyCode))
			{
				GameRunner.processWeaponInteraction();
			}
			else if(keyCode == KeyCode.KeyShift && GuiWindow.isWeaponShootable())
			{
				GameRunner.fireWeapon();
			}
		}
	}
	
	public static boolean validMovementKey(KeyCode keyCode)
	{
		if (keyCode == KeyCode.KeyLeft || keyCode == KeyCode.KeyRight || keyCode == KeyCode.KeyUp || keyCode == KeyCode.KeyDown)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean validInteractKey(KeyCode keyCode)
	{
		if (keyCode == KeyCode.KeySpace)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean validWeaponKey(KeyCode keyCode)
	{
		if (keyCode == KeyCode.KeyEsc)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean inputAccepted()
	{
		return acceptInput;
	}
	
	public static void freezeInput()
	{
		acceptInput = false;
	}
	
	public static void unfreezeInput()
	{
		acceptInput = true;
	}
	
	public static void unfreezeOnKey(KeyCode newUnlockKey)
	{
		unlockKey = newUnlockKey;
	}
}