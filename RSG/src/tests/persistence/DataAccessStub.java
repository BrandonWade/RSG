package tests.persistence;

import java.util.ArrayList;


import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import rsg.objects.GameCharacter;
import rsg.objects.GameItem;
import rsg.objects.GameObject;
import rsg.objects.GameScenery;
import rsg.objects.Goal;
import rsg.objects.OurDisplay;
import rsg.persistence.DataAccess;

public class DataAccessStub implements DataAccess
{
	private String DBName;
	private String DBType;
	private ArrayList<GameObject> gameObjs;
	private ArrayList<GameItem> gameItems;
	private ArrayList<GameCharacter> gameCharacters;
	private ArrayList<Goal> gameGoals;
	private String[] gameMaps;
	
	public DataAccessStub (String dbName)
	{
		this.DBName = dbName;
		DBType = "STUB";
	}
	
	public void open(String dbName)
	{
		Display display = OurDisplay.getDisplay();
		gameObjs = new ArrayList<GameObject>();
		gameItems = new ArrayList<GameItem>();
		gameCharacters = new ArrayList<GameCharacter>();
		gameMaps = new String[9];
		gameGoals = new ArrayList<Goal>();
		
		gameObjs.add(new GameScenery("GR01","Grass1", new Image(display, "res/assets/art/grid/grass01.png"), true, "Ordinary green grass.", new Image(display, "res/assets/art/minimap/grass.png"),  false));
		gameObjs.add(new GameScenery("RK01", "Rock1" , new Image(display, "res/assets/art/grid/rock01.png"),  false, "It's just a rock.", new Image(display, "res/assets/art/minimap/rock.png"),  false));
		gameObjs.add(new GameScenery("TR01", "Tree1" , new Image(display, "res/assets/art/grid/tree01.png"), false, "A leafy tree.", new Image(display, "res/assets/art/minimap/tree.png"),  false));
		gameObjs.add(new GameScenery("TR02", "Tree2" , new Image(display, "res/assets/art/grid/tree02.png"), false, "A leafy tree.", new Image(display, "res/assets/art/minimap/tree.png"),  false));
		gameObjs.add(new GameScenery("TR03", "Tree3" , new Image(display, "res/assets/art/grid/tree03.png"), false, "A leafy tree.", new Image(display, "res/assets/art/minimap/tree.png"),  false));
		gameObjs.add(new GameScenery("SD01", "Sand1" , new Image(display, "res/assets/art/grid/sand01.png"), true, "Plain old sand.", new Image(display, "res/assets/art/minimap/sand.png"),  false));
		gameObjs.add(new GameScenery("SD02", "GrassSand1" , new Image(display,  "res/assets/art/grid/sand02.png"), true, "There' grass growing in this sand.", new Image(display, "res/assets/art/minimap/sand.png"),  false));
		gameObjs.add(new GameScenery("SD03", "GrassSand2" , new Image(display,  "res/assets/art/grid/sand03.png"), true, "There's grass growing in this sand.", new Image(display, "res/assets/art/minimap/sand.png"),  false)); 
		gameObjs.add(new GameScenery("SD04", "GrassSand3" , new Image(display, "res/assets/art/grid/sand04.png"), true, "There's grass growing in this sand.", new Image(display, "res/assets/art/minimap/sand.png"),  false)); 
		gameObjs.add(new GameScenery("BH01", "Beach1" , new Image(display, "res/assets/art/grid/beach01.png"), false, "The beach shoreline.", new Image(display, "res/assets/art/minimap/sand.png"),  false));
		gameObjs.add(new GameScenery("BH02", "Beach2" , new Image(display, "res/assets/art/grid/beach02.png"), false, "The beach shoreline.", new Image(display, "res/assets/art/minimap/sand.png"),  false)); 
		gameObjs.add(new GameScenery("GR02", "Grass2" , new Image(display, "res/assets/art/grid/grass03.png"), true, "Nice green grass.", new Image(display, "res/assets/art/minimap/grass.png"),  false));
		gameObjs.add(new GameScenery("TR04", "Tree4" , new Image(display, "res/assets/art/grid/tree06.png"), false, "A big tree.", new Image(display, "res/assets/art/minimap/tree.png"),  false));
		gameObjs.add(new GameScenery("TR05", "Tree5" , new Image(display, "res/assets/art/grid/tree05.png"), false, "There's a bird in the tree!", new Image(display, "res/assets/art/minimap/tree.png"),  false));
		gameObjs.add(new GameScenery("BK01", "Book1" , new Image(display, "res/assets/art/grid/bookstore01.png"), false, "I can buy my books here.", new Image(display, "res/assets/art/minimap/grass.png"),  false));
		gameObjs.add(new GameScenery("TR06", "Tree6" , new Image(display, "res/assets/art/grid/tree04.png"), false, "An old tree.", new Image(display, "res/assets/art/minimap/tree.png"),  false));
		gameObjs.add(new GameScenery("TR07", "Tree7" , new Image(display, "res/assets/art/grid/tree07.png"), false, "An evergreen.", new Image(display, "res/assets/art/minimap/tree.png"),  false));
		gameObjs.add(new GameScenery("GR03", "Engineering1" , new Image(display, "res/assets/art/grid/engineering01.png"), false, "The Engineering building.", new Image(display, "res/assets/art/minimap/grass.png"),  false));
		gameObjs.add(new GameScenery("TIER", "Tier1" , new Image(display, "res/assets/art/grid/tier01.png"), false, "It looks like a castle.", new Image(display, "res/assets/art/minimap/grass.png"),  false));
		gameObjs.add(new GameScenery("TR08", "Tree8" , new Image(display, "res/assets/art/grid/tree08.png"), false, "A large evergreen.", new Image(display, "res/assets/art/minimap/tree.png"),  false));
		gameObjs.add(new GameScenery("MC04", "PlayerD" , new Image(display, "res/assets/art/grid/male04.png"), false, "", null,  false));
		gameObjs.add(new GameScenery("MC01", "PlayerL" , new Image(display, "res/assets/art/grid/male01.png"), false, "", null,  false));
		gameObjs.add(new GameScenery("MC03", "PlayerR" , new Image(display, "res/assets/art/grid/male03.png"), false, "", null,  false));
		gameObjs.add(new GameScenery("MC02", "PlayerU" , new Image(display, "res/assets/art/grid/male02.png"), false, "", null,  false));

		gameItems.add(new GameItem("DM01" , "Diamond" , new Image(display, "res/assets/art/grid/diamond01.png"),false , 0, "Its a mezmerizing diamond"));
		gameItems.add(new GameItem("MU01" , "1UPMUSHROOM" , new Image(display, "res/assets/art/grid/mushroom01.png"),false , 0, "This is a 1-Up mushroom"));
		gameItems.add(new GameItem("PK01" , "Pikachu" , new Image(display, "res/assets/art/grid/pikachu01.png"),false, 0, "Peek-a-pee"));
		gameItems.add(new GameItem("PZ01" , "Pizza" , new Image(display, "res/assets/art/grid/pizza01.png"),false, 0, "Yum! Pizza"));
		gameItems.add(new GameItem("MG01" , "Mug" , new Image(display, "res/assets/art/grid/mug01.png"),false, 0, "This holds coffee"));
		gameItems.add(new GameItem("FL01" , "Floppy" , new Image(display, "res/assets/art/grid/floppy01.png"),false, 0, "A floppy ninja star"));
		gameItems.add(new GameItem("CH01" , "Computer Chip" , new Image(display, "res/assets/art/grid/chip01.png") ,false, 0, "A COMPUTER CHIP"));
		gameItems.add(new GameItem("MN01" , "Money" , new Image(display, "res/assets/art/grid/money01.png"),false , 0, "This is worth $5"));
		gameItems.add(new GameItem("BC01" , "Bicycle" , new Image(display, "res/assets/art/grid/bike01.png"),false , 0, "Ooh, so shiny!"));
		
		gameCharacters.add(new GameCharacter("STD1","Male Chem Student", new Image(display,"res/assets/art/grid/chemstudentmale01.png"),20,20,5,"He knows chemistry!"));
		
		gameMaps[0] = "1,res/WorldTestMaps/map0x0.txt,0,0";
		gameMaps[1] = "2,res/WorldTestMaps/map0x1.txt,0,1";
		gameMaps[2] = "3,res/WorldTestMaps/map0x2.txt,0,2";
		gameMaps[3] = "4,res/WorldTestMaps/map1X0.txt,1,0";
		gameMaps[4] = "5,res/WorldTestMaps/map1x1.txt,1,1";
		gameMaps[5] = "6,res/WorldTestMaps/map1x2.txt,1,2";
		gameMaps[6] = "7,res/WorldTestMaps/map2x0.txt,2,0";
		gameMaps[7] = "8,res/WorldTestMaps/map2x1.txt,2,1";
		gameMaps[8] = "9,res/WorldTestMaps/map2x2.txt,2,2";
		
		gameGoals.add(new Goal(0,"If you are going to pull an all-nighter, you might want some coffee first.","MG01","Oh, what a lovely cup of coffee! If I drink it, I start feeling better.","What's this?"));
		gameGoals.add(new Goal(1,"Diamonds are a nerds best friend.. Wait.. That sounds wrong..","DM01","Now I can sell this so I can buy a Mac!!","This looks pretty, but I can''t pick it up yet."));
		gameGoals.add(new Goal(2,"I think I saw a floppy disc recently.. or maybe that was in 1994..","FL01","Wow! Now I can store 720KB of stuff!!","I'm not brave enough to pick this item up yet."));
	}
	
	public String getName()
	{
		return DBName;
	}
	
	public void close()
	{
		System.out.println("Closed" + DBType +" database" + DBName);
	}
	
	public ArrayList<GameObject> getGameObjs()
	{
		return gameObjs;
	}
	
	public ArrayList<GameCharacter> getGameCharacters()
	{
		return gameCharacters;
	}
	
	public ArrayList<GameObject> getGameScenery()
	{
		return gameObjs;
	}

	public String[] getGameMapInfo() 
	{
		return gameMaps;
	}
	
	public ArrayList<GameItem> getGameItems()
	{
		return gameItems;
	}
	
	public ArrayList<Goal> getGameGoals()
	{
		return gameGoals;
	}
}