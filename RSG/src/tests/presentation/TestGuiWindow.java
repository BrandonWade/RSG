package tests.presentation;

import rsg.objects.GamePlayer;
import rsg.objects.GamePotion;
import rsg.objects.GameWeapon;
import rsg.persistence.Services;
import rsg.persistence.WorldInfo;
import rsg.presentation.GuiWindow;
import tests.persistence.DataAccessStub;
import junit.framework.TestCase;

public class TestGuiWindow extends TestCase
{	
	public TestGuiWindow(String arg0) 
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		WorldInfo.initializeWorld();
		GamePlayer.createPlayer("P", "Name", WorldInfo.search("MC04").getImage(), 20, 20, 0, 5, 0, 0);
		GuiWindow.createGUI();
	}
	
	public void testProperName()
	{
		System.out.println("Starting test proper name...");
		
		GamePlayer.setName("Gregorio");
		GuiWindow.updateName();
		assertNotNull(GuiWindow.getPlayerName());
		assertEquals(GuiWindow.getPlayerName(), "Gregorio");

		System.out.println("Successfully finished test proper name.\n");
	}
	
	public void testWeaponUse()
	{		
		System.out.println("Starting test weapon removal...");
		GameWeapon wep = new GameWeapon("Sword", "BigSword", WorldInfo.search("GR01").getImage(), true, 0, "Sword");
		
		assertFalse(GuiWindow.removeWeapon());
		GuiWindow.updateWeapon(wep);
		assertTrue(GuiWindow.removeWeapon());
		
		System.out.println("Successfully finished weapon removal.\n");
	}
	
	
	public void testInventoryAdd()
	{		
		System.out.println("Starting test inventory add...");
		GameWeapon wep = new GameWeapon("Sword", "BigSword", WorldInfo.search("GR01").getImage(), true, 0, "Sword");
		GamePotion pot = new GamePotion("Potion", "potion", WorldInfo.search("GR01").getImage(), true, 20, "Potion");

		assertTrue(GuiWindow.addToInventory(wep));
		assertTrue(GuiWindow.addToInventory(pot));
		assertFalse(GuiWindow.addToInventory(null));
		
		System.out.println("Successfully finished inventory add.\n");
	}
	
	public void testHealthChange()
	{
		System.out.println("Starting guiwindow health change testing...");
		
		GamePlayer.createPlayer("MC04", "player", WorldInfo.search("MC04").getImage(), 20, 20, 0, 0, 12, 12);
		
		GuiWindow.updateHealth();
		assertEquals("20 / 20", GuiWindow.getHealth());
		GamePlayer.decreaseHealth(10);
		GuiWindow.updateHealth();
		assertEquals("10 / 20", GuiWindow.getHealth());
		GamePlayer.decreaseHealth(10);
		GuiWindow.updateHealth();
		assertEquals("0 / 20", GuiWindow.getHealth());
		
		System.out.println("Successfully finished health change testing...");
	}
	
	public void testExistance()
	{	
		System.out.println("Starting guiwindow existence testing...");
		assertEquals(false, GuiWindow.isDisposed());
		
		try 
		{
			GuiWindow.destroy();
		}
		catch (NullPointerException e)
		{
			System.out.println(e.getMessage());
			fail();
		}
		
		assertEquals(true, GuiWindow.isDisposed());
		System.out.println("Successfully finished guiwindow existence testing.\n");
	}
}