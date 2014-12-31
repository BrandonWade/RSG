package rsg.objects;

import org.eclipse.swt.widgets.Display;

public class OurDisplay
{
	private static Display display = null;
	
	private OurDisplay()
	{
		display = new Display();
	}
	
	public static void createDisplay()
	{
		if (display == null)
		{
			new OurDisplay();
		}
		
		else
		{
			System.out.println("Trying to create the display when the display has already been instansiated.");
		}
	}
	
	public static Display getDisplay()
	{
		if (display == null)
		{
			new OurDisplay();
		}
		
		return display;
	}

	public static boolean readAndDispatch()
	{
		return display.readAndDispatch();
	}

	public static void forceSleep() 
	{
		display.sleep();
	}

	public static void dispose()
	{
		display.dispose();
	}
}