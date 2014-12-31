package tests.processing;

import java.util.ArrayList;

import rsg.objects.GameCharacter;
import rsg.objects.GameObject;
import rsg.objects.GamePlayer;
import rsg.persistence.Services;
import rsg.persistence.World;
import rsg.persistence.WorldInfo;
import rsg.processing.BattleRunner;
import rsg.processing.MapCreationException;
import rsg.processing.MapCreator;
import rsg.processing.WindowInput;
import tests.persistence.DataAccessStub;
import junit.framework.TestCase;

public class TestBattleRunner extends TestCase
{
	private GameCharacter currEnemy;
	private final static String MAP1_TXT = "res/BattleRunnerTestMaps/testMap1.txt";
	private ArrayList<GameObject> tileList;

	public TestBattleRunner(String arg0) 
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(new DataAccessStub("STUB"));
		WorldInfo.initializeWorld();

		GamePlayer.createPlayer("MC01", "GamePlayer", WorldInfo.search("MC01").getImage(), 20, 20, 20, 20, 0, 0);

		try 
		{
			World.initalizeSuperMap(MapCreator.createMap(MAP1_TXT));
			tileList = World.getTileImages(0, 0);
			currEnemy = (GameCharacter) tileList.get(tileList.size() - 1);

			GamePlayer.setX(0);
			GamePlayer.setY(1);
		} 
		catch (MapCreationException e)
		{
			System.out.println("Map Creation Exception: " + e.getMessage());
			fail("map creation exception");
		}
	}

	public void testNoDamageBattle()
	{
		GamePlayer.createPlayer("MC01", "GamePlayer", WorldInfo.search("MC01").getImage(), 20, 20, 20, 20, 0, 0);
		GameCharacter enemy = new GameCharacter("STD1", "Male Chem Student",WorldInfo.search("MC01").getImage(), 20, 20, 0, "test");
		new BattleRunner(0,0);
		assertEquals(enemy.getHealth(), 20);
		assertEquals(GamePlayer.getHealth(), 20);
		BattleRunner.runBattle(enemy, 0, 0);
		assertEquals(GamePlayer.getHealth(), 20);
		assertEquals(enemy.getHealth(), 20);
	}

	public void testDamageBattle()
	{
		GamePlayer.createPlayer("MC01", "GamePlayer", WorldInfo.search("MC01").getImage(), 20, 20, 20, 20, 0, 0);
		GameCharacter enemy = new GameCharacter("STD1", "Male Chem Student", WorldInfo.search("MC01").getImage(), 20, 20, 0, "test");
		new BattleRunner(5,5);
		assertEquals(enemy.getHealth(), 20);
		assertEquals(GamePlayer.getHealth(), 20);
		BattleRunner.runBattle(enemy, 0, 0);
		assertEquals(GamePlayer.getHealth(), 15);
		assertEquals(enemy.getHealth(), 15);

		BattleRunner.runBattle(enemy, 0, 0);
		assertEquals(GamePlayer.getHealth(), 10);
		assertEquals(enemy.getHealth(), 10);
	}

	public void testEnemyKilled()
	{
		GamePlayer.createPlayer("MC01", "GamePlayer", WorldInfo.search("MC01").getImage(), 20, 20, 20, 20, 0, 0);
		new BattleRunner(20,0);
		assertEquals(currEnemy.getHealth(), 20);
		assertTrue(tileList.get(tileList.size() - 1) instanceof GameCharacter);
		BattleRunner.runBattle(currEnemy, 0, 0);
		assertEquals(currEnemy.getHealth(), 0);
		tileList = World.getTileImages(0, 0);
		assertFalse(tileList.get(tileList.size() - 1) instanceof GameCharacter);
	}

	public void testPlayerKilled() 
	{
		GamePlayer.delete();
		new BattleRunner(0,20);
		GamePlayer.createPlayer("MC01", "GamePlayer", WorldInfo.search("MC01").getImage(), 20, 20, 20, 20, 0, 0);
		GameCharacter enemy = new GameCharacter("STD1", "Male Chem Student", WorldInfo.search("MC01").getImage(), 20, 20, 0, "test");
		assertTrue(WindowInput.inputAccepted());
		assertEquals(GamePlayer.getHealth(), 20);
		BattleRunner.runBattle(enemy, 0, 0);
		assertEquals(GamePlayer.getHealth(), 0);
		assertFalse(WindowInput.inputAccepted());
	}
}