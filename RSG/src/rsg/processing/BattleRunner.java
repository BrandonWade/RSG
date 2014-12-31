package rsg.processing;

import java.util.ArrayList;

import rsg.objects.GameCharacter;
import rsg.objects.GameObject;
import rsg.objects.GamePlayer;
import rsg.persistence.World;
import rsg.persistence.WorldInfo;
import rsg.presentation.GuiWindow;

public class BattleRunner
{
	private static boolean setAttacks = false;
	private static boolean created = false;
	private static int playerAttk;
	private static int enemyAttk;
	private final static String[] VERBS = new String[] {"killed", "slaughtered", "massacred", "poked the eye out of","sneezed on","destroyed"};
	private static int verbCount = 0;
	
	private BattleRunner()
	{
		playerAttk = -1;
		enemyAttk = -1;
		created = true;
	}
	
	public BattleRunner(int playerdmg, int enemydmg)
	{
		playerAttk = playerdmg;
		enemyAttk = enemydmg;
		created = true;
		setAttacks = true;
	}
	
	public static String runBattle(GameCharacter currEnemy, int enemyX, int enemyY)
	{
		if(!created)
		{
			new BattleRunner();
		}
		
		ArrayList<GameObject> tileList;
		String interactMessage = "You attacked " + currEnemy.getName() + ", " + currEnemy.getInteractionMessage();
		
		if(setAttacks)
		{
			GamePlayer.decreaseHealth(enemyAttk);
			currEnemy.decreaseHealth(playerAttk);
		}
		else
		{
			enemyAttk = currEnemy.getAttack();
			playerAttk = GamePlayer.getPlayerAttack();
			GamePlayer.decreaseHealth(enemyAttk);
			currEnemy.decreaseHealth(playerAttk);
		}
		
		if(currEnemy.getHealth() <= 0)
		{
			interactMessage = "You defeated " + currEnemy.getName();
			World.removeItem(enemyX, enemyY);
			tileList = World.getTileImages(enemyX, enemyY);
			for (int i = 0; i < tileList.size(); i++)
			{
				//GuiWindow.drawImage(tileList.get(i).getImage(), enemyX, enemyY);
			}
		}
		
		if(GamePlayer.getHealth() <= 0)
		{
			WindowInput.freezeInput();
			GuiWindow.drawImage(WorldInfo.gameOverScreen, 0, 0);
			interactMessage = "You died bro...";
		}
		else
		{
			interactMessage += " You dealt " + playerAttk + " damage, and took " + enemyAttk + " damage.";
			if(currEnemy.getHealth() <= 0)
			{
				interactMessage += " You have " + VERBS[verbCount%VERBS.length] + " " + currEnemy.getName() + ".";
				verbCount++;
			}			
			else
			{				
				interactMessage += " Enemy has " + currEnemy.getHealth() + "/" + currEnemy.getMaxHealth();
			}
		}
		
		return interactMessage;
	}
}