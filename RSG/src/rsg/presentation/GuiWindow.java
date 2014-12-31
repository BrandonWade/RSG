package rsg.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import acceptanceTests.Register;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import rsg.objects.GameItem;
import rsg.objects.GameObject;
import rsg.objects.GamePlayer;
import rsg.objects.GamePotion;
import rsg.objects.GameScenery;
import rsg.objects.GameVehicle;
import rsg.objects.GameWeapon;
import rsg.objects.Map;
import rsg.objects.OurDisplay;
import rsg.objects.Projectile;
import rsg.persistence.World;
import rsg.processing.GameRunner;
import rsg.processing.KeyCode;
import rsg.processing.StoryManager;
import rsg.processing.WindowInput;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class GuiWindow implements ActionListener
{
	private final static Shell shell = new Shell(SWT.NO_TRIM | SWT.MIN | SWT.MAX);
	private final static Canvas gridCanvas = new Canvas(shell, SWT.NONE);
	private final static Canvas miniMapCanvas = new Canvas(shell, SWT.BORDER);
	private final static GC gridGC = new GC(gridCanvas);
	private final static GC miniMapGC = new GC(miniMapCanvas);
	private static int inventoryCount = 0;
	private static boolean weaponEquipped = false;
	private static boolean specialWeapon = false;
	private static boolean helpOpen = false;
	private static boolean windowOpen = false;
	private static boolean bulletOnScreen = false;
	private static HelpWindow helpWindow = new HelpWindow(shell, 1);
	private static WelcomeDialog welcome = new WelcomeDialog(shell, 1);
	private static Text game_output;
	private static Text health_box;
	private static Text name_box;
	private static Text goal_box;
	private static Table weaponTable;
	private static Table table;
	private static Button btnQuit;
	private static Button btnHelp;
	private static Timer timer;
	
	private GuiWindow()
	{
		Register.newWindow(this);
		
	    Monitor mainMonitor = OurDisplay.getDisplay().getPrimaryMonitor();
	    
	    shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
	    shell.setSize(745, 573);
	    shell.setLocation(mainMonitor.getBounds().x + (mainMonitor.getBounds().width - shell.getBounds().width) / 2, mainMonitor.getBounds().y + (mainMonitor.getBounds().height - shell.getBounds().height) / 2);
	    
	    gridCanvas.setSize(480, 480);
	    gridCanvas.setLocation(260, 5);
	    
	    Canvas player_stats = new Canvas(shell, SWT.NONE);
	    player_stats.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	    player_stats.setBounds(5, 5, 250, 563);

	    btnQuit = new Button(player_stats, SWT.NONE);
	    btnQuit.setLocation(150, 530);
	    btnQuit.setSize(58, 25);
	    btnQuit.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
	    	    
	    btnQuit.setDragDetect(false);
	    btnQuit.setImage(SWTResourceManager.getImage(GuiWindow.class, "/assets/art/display/quit_button.png"));
	    	    	    
	    Label title_label = new Label(player_stats, SWT.NONE);
	    title_label.setLocation(70, 10);
	    title_label.setSize(100, 30);
	    title_label.setImage(SWTResourceManager.getImage(GuiWindow.class, "/assets/art/display/player_title.png"));
    
	    btnHelp = new Button(player_stats, SWT.NONE);
	    btnHelp.addSelectionListener(new SelectionAdapter() 
	    {
	    	public void widgetSelected(SelectionEvent e)
	    	{
	    		if (!helpOpen)
	    		{
	    			helpOpen = true;
	    			helpWindow.open();
	    		}
	    	}
	    });
	    
	    btnHelp.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
	    btnHelp.setImage(SWTResourceManager.getImage(GuiWindow.class, "/assets/art/display/help_button.png"));
	    btnHelp.setBounds(40, 530, 58, 25);
	    	    	    
	    health_box = new Text(player_stats, SWT.READ_ONLY);
	    health_box.addFocusListener(new FocusAdapter()
	    {
	    	public void focusGained(FocusEvent e)
	    	{
	    		gridCanvas.setFocus();
	    	}
	    });
	    
	    health_box.setFont(SWTResourceManager.getFont("Courier New", 11, SWT.NORMAL));
	    health_box.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
	    health_box.setBounds(80, 90, 160, 18);
	    	    	    
	    name_box = new Text(player_stats, SWT.READ_ONLY);
	    name_box.addSelectionListener(new SelectionAdapter() 
	    {
	    	public void widgetSelected(SelectionEvent e)
	    	{
	    		gridCanvas.setFocus();
	    	}
	    });
	    
	    name_box.setText(GamePlayer.getName());
	    name_box.setFont(SWTResourceManager.getFont("Courier New", 11, SWT.NORMAL));
	    name_box.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
	    name_box.setBounds(80, 60, 160, 18);
	    
	    Label lblName = new Label(player_stats, SWT.NONE);
	    lblName.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	    lblName.setFont(SWTResourceManager.getFont("Courier", 12, SWT.BOLD));
	    lblName.setBounds(10, 60, 59, 18);
	    lblName.setText("NAME");
	    
	    Label lblHealth = new Label(player_stats, SWT.NONE);
	    lblHealth.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	    lblHealth.setFont(SWTResourceManager.getFont("Courier", 12, SWT.BOLD));
	    lblHealth.setBounds(10, 90, 68, 14);
	    lblHealth.setText("HEALTH");
	    
	    Label lblItems = new Label(player_stats, SWT.NONE);
	    lblItems.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	    lblItems.setFont(SWTResourceManager.getFont("Courier", 12, SWT.BOLD));
	    lblItems.setBounds(10, 120, 58, 14);
	    lblItems.setText("ITEMS");
	    
	    table = new Table(player_stats, SWT.FULL_SELECTION);
	    table.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseDown(MouseEvent e)
	    	{
	    		gridCanvas.forceFocus();
	    	}
	    });
	    
	    table.addSelectionListener(new SelectionAdapter() 
	    {
	    	public void widgetSelected(SelectionEvent e)
	    	{
	    		if (inventoryCount > 0 && WindowInput.inputAccepted())
	    		{
	    			try
	    			{
	    				TableItem[] curr = table.getSelection();
	    				TableItem newItem = curr[0];
	    				GameItem selectedItem = (GameItem)newItem.getData();
	    				determineItemAction(selectedItem);
	    			}
	    			catch (ArrayIndexOutOfBoundsException e1)
	    			{
	    				GuiWindow.updateGameOutput("You must click on an item to use it.");
	    			}
	    		}
	    		gridCanvas.forceFocus();
	    	}
	    });

	    table.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
	    table.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
	    table.setBounds(80, 124, 160, 90);
	    
	    Label lblWeapon = new Label(player_stats, SWT.NONE);
	    lblWeapon.setText("WEAPON");
	    lblWeapon.setFont(SWTResourceManager.getFont("Courier", 12, SWT.BOLD));
	    lblWeapon.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	    lblWeapon.setBounds(10, 225, 67, 14);
	    
	    weaponTable = new Table(player_stats, SWT.FULL_SELECTION);
	    weaponTable.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseDown(MouseEvent e)
	    	{
	    		gridCanvas.forceFocus();
	    	}
	    });
	    
	    weaponTable.addSelectionListener(new SelectionAdapter() 
	    {
	    	public void widgetSelected(SelectionEvent e)
	    	{
	    		gridCanvas.forceFocus();
	    	}
	    });
	    
	    weaponTable.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
	    weaponTable.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
	    weaponTable.setBounds(80, 225, 160, 22);
	    
	    Label lblGoal = new Label(player_stats, SWT.NONE);
	    lblGoal.setText("CURRENT GOAL");
	    lblGoal.setFont(SWTResourceManager.getFont("Courier", 12, SWT.BOLD));
	    lblGoal.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	    lblGoal.setBounds(10, 255, 151, 14);
	    
	    goal_box = new Text(player_stats, SWT.READ_ONLY | SWT.WRAP);
	    goal_box.setTextLimit(100);
	    goal_box.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
	    goal_box.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
	    goal_box.setBounds(10, 275, 230, 80);
	    
	    Label lblMiniMap = new Label(player_stats, SWT.NONE);
	    lblMiniMap.setText("MINIMAP");
	    lblMiniMap.setFont(SWTResourceManager.getFont("Courier", 12, SWT.BOLD));
	    lblMiniMap.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	    lblMiniMap.setBounds(90, 365, 80, 14);
	    
	    miniMapCanvas.setBounds(70, 390, 124, 124);
 	    	    
	    btnQuit.addSelectionListener(new SelectionAdapter() 
	    {
	    	public void widgetSelected(SelectionEvent e)
			{
				destroy();
			}
	    });
	    
	    shell.addListener(SWT.Deiconify, new Listener()
	    {
			public void handleEvent(Event e)
			{
				gridCanvas.redraw();
				miniMapCanvas.redraw();
			}
	    });
	    /*
	    gridCanvas.addPaintListener(new PaintListener()
	    {
	    	public void paintControl(PaintEvent e)
	    	{
	    		drawMap(World.getCurrentMap());
	    		drawImage(GamePlayer.getImage(), GamePlayer.getX(), GamePlayer.getY());
	        }
	    });
	    */
	    miniMapCanvas.addPaintListener(new PaintListener()
	    {
	    	public void paintControl(PaintEvent e)
	    	{
	    		drawMiniMap();
	        }
	    });
	    
	    Canvas output_bar = new Canvas(shell, SWT.NONE);
	    output_bar.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	    output_bar.setBounds(260, 490, 480, 78);

	    Label speech_bubble = new Label(output_bar, SWT.NONE);
	    speech_bubble.setImage(SWTResourceManager.getImage(GuiWindow.class, "/assets/art/display/speech.png"));
	    speech_bubble.setBounds(10, 8, 30, 30);
	    
	    game_output = new Text(output_bar, SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
	    game_output.addSelectionListener(new SelectionAdapter() 
	    {
	    	public void widgetSelected(SelectionEvent e)
	    	{
	    		gridCanvas.setFocus();
	    	}
	    });
	    
	    game_output.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
	    game_output.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
	    game_output.setBounds(46, 8, 424, 60);
	    shell.open();
	    gridCanvas.forceFocus();
	    
	    //KeyListener event to check when any key is pressed when the window has focus
	    gridCanvas.addKeyListener(new KeyAdapter()							
	    {	
			//The method to process the actual event
			public void keyPressed(KeyEvent e)							
			{
				KeyCode pressedKey = null;
				
				switch(e.keyCode)
				{
					case SWT.ARROW_LEFT: 
						pressedKey = KeyCode.KeyLeft;
						break;
					case SWT.ARROW_RIGHT:
						pressedKey = KeyCode.KeyRight;
						break;
					case SWT.ARROW_UP:
						pressedKey = KeyCode.KeyUp;
						break;
					case SWT.ARROW_DOWN:
						pressedKey = KeyCode.KeyDown;
						break;
					case SWT.SPACE:
						pressedKey = KeyCode.KeySpace;
						break;
					case SWT.ESC:
						pressedKey = KeyCode.KeyEsc;
						break;
					case SWT.SHIFT:
						pressedKey = KeyCode.KeyShift;
						break;
				}
				
				if(pressedKey != null)
				{
					WindowInput.processKeyboardInput(pressedKey);
				}
			}
		});
	    
	    //Set up timer to drive animation events.
		timer = new Timer(100, this);
		timer.setInitialDelay(100);
		timer.start();
	}
	
	public static void createGUI()
	{
		if (!windowOpen)
		{
			new GuiWindow();
			windowOpen = true;
		}
	}
	
	public static void destroy()
	{
		OurDisplay.dispose();
		gridCanvas.dispose();
		gridGC.dispose();
		shell.dispose();
		System.exit(0);
	}
	
	public static void closeHelp()
	{
		helpOpen = false;
	}

	public static void updateStats()
	{	
		updateHealth();
		updateName();
		updateGoalOutput(StoryManager.getCurrentGoalDesc());
	}
	
	public static void updateHealth()
	{
		health_box.setText(GamePlayer.getHealth() + " / " + GamePlayer.getMaxHealth());
	}
	
	public static String getHealth()
	{
		return health_box.getText();
	}
	
	public static void updateName()
	{
		name_box.setText(GamePlayer.getName());
	}
	
	public static boolean updateWeapon(GameWeapon newWeapon)
	{
		TableItem newItem;
	
		if (weaponEquipped)
		{	
			removeWeapon();
		}
		
		weaponEquipped = true;
		newItem = new TableItem(weaponTable, SWT.NONE);
		newItem.setText(newWeapon.getName() + "(+" + newWeapon.getValue() + ")");
		newItem.setImage(newWeapon.getImage());
		newItem.setData(newWeapon);
		
		if(newWeapon.getCode().equals("GUN1"))
		{
			specialWeapon = true;
		}
		
		return true;
	}
	
	public static boolean removeWeapon()
	{
		if (weaponEquipped)
		{
			weaponEquipped = false;
			specialWeapon = false;
			weaponTable.remove(0);
			return true;
		}
		return false;
	}
	
	public static boolean addToInventory(GameObject tileItem)
	{
		TableItem newItem;
		GameItem toAdd;
		
		if (tileItem instanceof GameItem)
		{
			toAdd = (GameItem)tileItem;
			newItem = new TableItem(table, SWT.NONE);
		
			newItem.setText(toAdd.getName());
			newItem.setImage(toAdd.getImage());
			newItem.setData(toAdd);
			inventoryCount++;
			return true;
		}	
		return false;
	}

	public static void determineItemAction(GameItem currItem)
	{
		int itemValue;
		
		if (currItem instanceof GamePotion)
		{
			GamePotion newPotion = (GamePotion)currItem;
			itemValue = newPotion.getValue();
			GameRunner.increasePlayerHealth(itemValue);
			updateHealth();
			table.remove(table.getSelectionIndices());
		}
		else if (currItem instanceof GameWeapon)
		{
			GameWeapon newWeapon = (GameWeapon)currItem;
			GamePlayer.setWeapon(newWeapon);
			updateWeapon(newWeapon);
		}
		else if (currItem instanceof GameVehicle)
		{
			GameVehicle newVehicle = (GameVehicle)currItem;
			GamePlayer.setPlayerVehicle(newVehicle);
			//delay
		}
		
		updateGameOutput(currItem.getInteractionMessage());
	}
	
	public static void removeLastItem()
	{
		int pos = table.getItems().length;
		
		GamePlayer.removeLastItem();
		table.remove(pos-1);
	}
	
	public static void updateGameOutput(String newOutput)
	{
		game_output.setText(newOutput);
	}

	public static String getGameOutput()
	{
		return game_output.getText();
	}
	
	public static String getPlayerName()
	{
		return name_box.getText();
	}
	
	public static void updateGoalOutput(String newGoal)
	{
		goal_box.setText(newGoal);
	}
	
	public static void updateItemNotAllowedOutput(GameItem notAllowed)
	{
		game_output.setText("Hmm... I don't know what this is");
	}
	
	public static void openWelcome()
	{
		welcome.open();
	}
	
	public static void drawMiniMap()
	{
		OurDisplay.getDisplay().syncExec(new Runnable()
		{
			public void run()
			{
				Map gameMap = null;
				Image mapIcon;
				
				for (int i = 0; i < World.getMapXLength(); i++)
				{
					for (int j = 0; j < World.getMapXLength(); j++)
					{
						gameMap = World.getSuperMap(i, j);
						
						for (int k = 0; k < Map.getRows(); k++)
						{
							for (int l = 0; l < Map.getCols(); l++)
							{
								if (gameMap.getTileImages(k, l).get(gameMap.getTileImages(k, l).size() - 1) instanceof GameScenery)
								{
									mapIcon = ((GameScenery)(gameMap.getTileImages(k, l).get(gameMap.getTileImages(k, l).size() - 1))).getMapTile();
								}
								else
								{
									mapIcon = ((GameScenery)(gameMap.getTileImages(k, l).get(gameMap.getTileImages(k, l).size() - 2))).getMapTile();
								}
								miniMapGC.drawImage(mapIcon, ((i * Map.getRows()) + k) * Map.MINIMAP_TILE_SIZE, ((j * Map.getCols()) + l) * Map.MINIMAP_TILE_SIZE);
							}
						}
					}
				}
				
				drawMiniMapIndicator();
			}
		});
	}
	
	private static void drawMiniMapIndicator()
	{
		miniMapGC.setForeground(OurDisplay.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
		miniMapGC.drawRectangle(World.getCurrentMapX() * Map.MINIMAP_TILE_SIZE * Map.getRows(), World.getCurrentMapY() * Map.MINIMAP_TILE_SIZE * Map.getCols(), Map.MINIMAP_TILE_SIZE * (Map.getRows() - 1), Map.MINIMAP_TILE_SIZE * (Map.getCols() - 1));
	}
	
	public static void removeMiniMapIndicator()
	{		
		//Redraw top boundary
		for (int i = 0; i < Map.getCols(); i++)
		{
			drawValidMiniMapIcon(i, 0);
		}
		
		//Redraw bottom boundary
		for (int i = 0; i < Map.getCols(); i++)
		{
			drawValidMiniMapIcon(i, Map.getCols() - 1);		
		}
		
		//Redraw left boundary
		for (int i = 1; i < Map.getRows() - 1; i++)
		{
			drawValidMiniMapIcon(0, i);
		}
		
		//Redraw right boundary
		for (int i = 1; i < Map.getRows() - 1; i++)
		{
			drawValidMiniMapIcon(Map.getCols() - 1, i);
		}
		
		drawMiniMapIndicator();
	}
	
	private static void drawValidMiniMapIcon(final int drawX, final int drawY)
	{
		int offsetX = World.getOldMapX();
		int offsetY = World.getOldMapY();
		Map gameMap = World.getSuperMap(offsetX, offsetY);
		Image mapIcon = null;
		
		if (gameMap.getTileImages(drawX, drawY).get(gameMap.getTileImages(drawX, drawY).size() - 1) instanceof GameScenery)
		{
			mapIcon = ((GameScenery)(gameMap.getTileImages(drawX, drawY).get(gameMap.getTileImages(drawX, drawY).size() - 1))).getMapTile();
		}
		else
		{
			mapIcon = ((GameScenery)(gameMap.getTileImages(drawX, drawY).get(gameMap.getTileImages(drawX, drawY).size() - 2))).getMapTile();
		}
		
		miniMapGC.drawImage(mapIcon, (offsetX * Map.getRows()) + (drawX * Map.MINIMAP_TILE_SIZE), (offsetY * Map.getCols()) + (drawY * Map.MINIMAP_TILE_SIZE));
	}
	
	public static void drawMap(final Map gameMap)
	{
		OurDisplay.getDisplay().syncExec(new Runnable()
		{
			public void run()
			{
				ArrayList<GameObject> imageList = null;
				
				for (int i = 0; i < Map.getRows(); i++)
				{
					for (int j = 0; j < Map.getCols(); j++)
					{
						imageList = gameMap.getTileImages(i, j);
						for(int k = 0; k < imageList.size(); k++)
						{
							gridGC.drawImage(imageList.get(k).getImage(), i * Map.GAME_TILE_SIZE, j * Map.GAME_TILE_SIZE);
						}
					}
				}
				
				removeMiniMapIndicator();
			}
		});
	}

	public static void drawImage(final Image image, final int xVal, final int yVal)
	{
		OurDisplay.getDisplay().asyncExec(new Runnable()
		{
			public void run()
			{
				gridGC.drawImage(image, xVal * Map.GAME_TILE_SIZE, yVal * Map.GAME_TILE_SIZE);
			}
		});
	}
	
	public static boolean isDisposed()
	{
		return shell.isDisposed();
	}
	
	public static Shell getShell()
	{
		return shell;
	}
	
	public static GC getGridGC()
	{
		return gridGC;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (bulletOnScreen)
        {
        	//Update position of bullet. Draw it all
			bulletOnScreen = GameRunner.drawNewBulletPos();
        }
		else
		{
			//Clear old settings for the projectile.
			Projectile.set(null, -1, -1);
		}
    }

	public static void fireWeapon()
	{
		bulletOnScreen = true;
	}

	public static boolean isSpecialWeapon() 
	{
		return specialWeapon;
	}
	
	public static boolean isBulletAlready() 
	{
		return bulletOnScreen;
	}

	public static boolean isWeaponShootable()
	{
		return !isBulletAlready() && isSpecialWeapon();
	}

	public static void removeBullet()
	{
		bulletOnScreen = false;
	}
}