package tests;

import rsg.processing.BattleRunner;
import rsg.processing.KeyCode;
import rsg.processing.WindowInput;

public class AcceptanceTestHelper
{
	//17 up, 1 left , 12 up, 4 right, talk to wizard
	public static void testMap1()
	{
		moveHelper(17,KeyCode.KeyUp);
		WindowInput.processKeyboardInput(KeyCode.KeyLeft);
		moveHelper(12,KeyCode.KeyUp);
		moveHelper(4,KeyCode.KeyRight);
	}
	public static void testRSG2Help1()
	{
		//Getting the mug
		moveHelper(17 , KeyCode.KeyUp);
		moveHelper(1 , KeyCode.KeyLeft);
		moveHelper(9 , KeyCode.KeyUp);
		moveHelper(3 , KeyCode.KeyRight);
		moveHelper(1 , KeyCode.KeyUp);
	}
	public static void testRSG2Help2()
	{
		//Getting the diamond
		moveHelper(3 , KeyCode.KeyLeft);
		moveHelper(9 , KeyCode.KeyDown);
		moveHelper(1 , KeyCode.KeyRight);
		moveHelper(10 , KeyCode.KeyDown);
		moveHelper(15 , KeyCode.KeyLeft);
		moveHelper(20 , KeyCode.KeyUp);
		moveHelper(2 , KeyCode.KeyLeft);
	}
	public static void testRSG2Help3()
	{
		//getting the floppy
		moveHelper(2 , KeyCode.KeyRight);
		moveHelper(20 , KeyCode.KeyDown);
		moveHelper(15 , KeyCode.KeyRight);
		moveHelper(10 , KeyCode.KeyUp);
		moveHelper(7 , KeyCode.KeyRight);
		moveHelper(15 , KeyCode.KeyUp);
		
	}
	public static void testRSG2Help4()
	{
		//Getting the two-sided tape
		moveHelper(15 , KeyCode.KeyDown);
		moveHelper(7 , KeyCode.KeyLeft);
		moveHelper(8 , KeyCode.KeyDown);
		moveHelper(1 , KeyCode.KeyRight);
	}
	public static void testRSG2Help5()
	{
		//White-out
		moveHelper(14 , KeyCode.KeyRight);
		moveHelper(7 , KeyCode.KeyUp);
	}
	public static void testRSG2Help6()
	{
		//getting the flashdrive
		moveHelper(11 , KeyCode.KeyDown);
		moveHelper(45, KeyCode.KeyLeft);
		moveHelper(1 , KeyCode.KeyDown);
		moveHelper(4, KeyCode.KeyLeft);
		moveHelper(1 , KeyCode.KeyDown);
	}
	public static void testRSG2Help7()
	{
		//getting the catFolder and fighting the nasty tree
		moveHelper(9 , KeyCode.KeyDown);
		moveHelper(47, KeyCode.KeyRight);
		moveHelper(10,  KeyCode.KeyDown);
		moveHelper(2 , KeyCode.KeyRight);
		@SuppressWarnings("unused")
		BattleRunner temp = new BattleRunner(45, 0); // this method injects the fixed values so they are not random
		moveHelper(1 , KeyCode.KeyUp);
	}
	public static void testRSG2Help8()
	{
		moveHelper(1,KeyCode.KeyUp);
	}
	public static void testRSG2Help9()
	{
		//talking to braico
		moveHelper(3,KeyCode.KeyRight);
		moveHelper(18,KeyCode.KeyUp);
		moveHelper(1,KeyCode.KeyLeft);
		moveHelper(1,KeyCode.KeyUp);
	}
	
	//12 left, 1 up, grab diamond
	public static void testMap2()
	{
		moveHelper(12,KeyCode.KeyLeft);
		WindowInput.processKeyboardInput(KeyCode.KeyUp);
		WindowInput.processKeyboardInput(KeyCode.KeySpace);
	}
	
	//11 right, 1 down , space
	public static void testMap3()
	{
		moveHelper(11,KeyCode.KeyRight);
		WindowInput.processKeyboardInput(KeyCode.KeyDown);
		WindowInput.processKeyboardInput(KeyCode.KeyDown);
		WindowInput.processKeyboardInput(KeyCode.KeySpace);
	}
	public static void testMap4Help1()
	{
		moveHelper(17,KeyCode.KeyUp);
		moveHelper(1 , KeyCode.KeyRight);
		moveHelper(1,KeyCode.KeyUp);
	}
	public static void testMap4Help1a()
	{
		moveHelper(3 , KeyCode.KeyRight);
		moveHelper(1,KeyCode.KeyUp);
	}
	public static void testMap4Help1b()
	{
		moveHelper(2 , KeyCode.KeyRight);
		moveHelper(3,KeyCode.KeyUp);
		moveHelper(1 , KeyCode.KeyLeft);
		moveHelper(1 , KeyCode.KeyUp);
	}
	public static void testMap4Help1c()
	{
		moveHelper(6 , KeyCode.KeyLeft);
		moveHelper(3 , KeyCode.KeyDown);
		moveHelper(1 , KeyCode.KeyRight);
	}
	public static void testMap4Help2()
	{
		moveHelper(17,KeyCode.KeyDown);
	}
	
	public static void testMap4Help3()
	{
		moveHelper(30,KeyCode.KeyLeft);
	}
	public static void testMap4Help3a()
	{
		moveHelper(12, KeyCode.KeyRight);
		moveHelper(3 , KeyCode.KeyUp);
	}
	public static void testMap4Help3b()
	{
		moveHelper(1, KeyCode.KeyRight);
	}
	public static void testMap4Help3c()
	{
		moveHelper(4 , KeyCode.KeyUp);
		moveHelper(1, KeyCode.KeyRight);
	}
	public static void testMap4Help3d(){
		moveHelper(13 , KeyCode.KeyLeft);
		moveHelper(7 , KeyCode.KeyDown);
	}
	
	
	public static void testMap4Help4()
	{
		moveHelper(23,KeyCode.KeyUp);
		moveHelper(6 , KeyCode.KeyRight);
	}
	public static void testMap4Help4a()
	{
		moveHelper(2 , KeyCode.KeyLeft);
		moveHelper(1 , KeyCode.KeyDown);
	}
	public static void testMap4Help4b()
	{
		moveHelper(1,KeyCode.KeyUp);
		moveHelper(4 , KeyCode.KeyLeft);
	}
	public static void testMap4Help4c()
	{
		moveHelper(42,KeyCode.KeyUp);
	}
	
	public static void testMap4Help5()
	{
		moveHelper(42,KeyCode.KeyLeft);
	}
	
	public static void testMap4Help6()
	{
		moveHelper(23,KeyCode.KeyDown);
	}
	public static void testMap4Help6a()
	{
		moveHelper(10, KeyCode.KeyUp);
		moveHelper(3 , KeyCode.KeyLeft);
	}
	public static void testMap4Help6b()
	{
		moveHelper(3 , KeyCode.KeyRight);
		moveHelper(13, KeyCode.KeyDown);
	}
	public static void testMap4Help6c()
	{
		moveHelper(3 , KeyCode.KeyUp);
	}
	
	
	
	public static void testMap4Help7()
	{
		moveHelper(23,KeyCode.KeyRight);
	}
	
	public static void testMap4Help8()
	{
		moveHelper(48,KeyCode.KeyUp);
	}
	
	public static void testMap4Help9()
	{
		moveHelper(3, KeyCode.KeyDown);
		moveHelper(20,KeyCode.KeyRight);
	}
	public static void testMap4Help10()
	{

		moveHelper(23, KeyCode.KeyDown);
		moveHelper(29, KeyCode.KeyRight);
	}
	public static void testMap5()
	{
		moveHelper(3,KeyCode.KeyLeft);
		moveHelper(14,KeyCode.KeyDown);
		WindowInput.processKeyboardInput(KeyCode.KeyRight);
		moveHelper(14,KeyCode.KeyDown);
	}
	
	public static void testMap6()
	{
		moveHelper(30, KeyCode.KeyDown);
		moveHelper(25, KeyCode.KeyRight);
		moveHelper(25, KeyCode.KeyLeft);
		moveHelper(30, KeyCode.KeyUp);
	}
	
	private static void moveHelper(int movements, KeyCode keyboardInput)
	{
		try
		{
			for (int i = 0; i<movements; i++)
			{
				WindowInput.processKeyboardInput(keyboardInput);
				Thread.sleep(30);
			}
			
			WindowInput.processKeyboardInput(KeyCode.KeySpace);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}