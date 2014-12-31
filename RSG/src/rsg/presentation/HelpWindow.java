package rsg.presentation;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import rsg.objects.OurDisplay;

import acceptanceTests.EventLoop;
import acceptanceTests.Register;
import org.eclipse.swt.widgets.Text;

public class HelpWindow extends Dialog
{
	protected Object result;
	protected Shell shlHelp;
	protected Button btnOK;
	private Text txtTheObjectOf;

	public HelpWindow(Shell parent, int style)
	{
		super(parent, style);
		Register.newWindow(this);
		setText("SWT Dialog");
	}

	public Object open()
	{
		createContents();
		shlHelp.open();
		shlHelp.layout();
		Display display = getParent().getDisplay();
		
		if (EventLoop.isEnabled())
		{
			while (!shlHelp.isDisposed())
			{
				if (!display.readAndDispatch())
				{
					display.sleep();
				}
			}
		}
		
		return result;
	}

	private void createContents()
	{
		Monitor mainMonitor = OurDisplay.getDisplay().getPrimaryMonitor();
		
		shlHelp = new Shell(getParent(), getStyle());
		shlHelp.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		shlHelp.setSize(400, 500);
	    shlHelp.setLocation(mainMonitor.getBounds().x + (mainMonitor.getBounds().width - shlHelp.getBounds().width) / 2, mainMonitor.getBounds().y + (mainMonitor.getBounds().height - shlHelp.getBounds().height) / 2);
		shlHelp.setText("Help");
		
		btnOK = new Button(shlHelp, SWT.NONE);
		btnOK.addMouseListener(new MouseAdapter()
		{
			public void mouseUp(MouseEvent arg0)
			{
				GuiWindow.closeHelp();
				shlHelp.dispose();
			}
		});
		
		btnOK.setImage(SWTResourceManager.getImage(HelpWindow.class, "/assets/art/display/ok_button.png"));
		btnOK.setBounds(173, 443, 29, 25);
		
		txtTheObjectOf = new Text(shlHelp, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		txtTheObjectOf.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		txtTheObjectOf.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
		txtTheObjectOf.setText("CONTROLS\r\n\r\nArrow Keys: Move character\r\nMouse click: Use inventory item\r\nSpacebar: Interact with objects and characters\r\nEsc: Unequip weapon\r\n\r\nThe QUIT button on the main screen will close the game. Since this is an old school style game, if you quit the game, you will lose your current progress.\r\n\r\nGOALS\r\n\r\nGoals are assigned to you and displayed in the Player Info bar. You must complete all of the goals in the order they are assigned. When you have completed a goal, a new goal message will appear in the GOALS box. You will win the game when you have completed all of the goals.\r\n\r\nITEMS\r\n\r\nItems can be picked up by interacting with them. When an item has been picked up, it will be added to your inventory. There are two types of special items: Weapons and Potions. These items can be used by clicking on them from the inventory.\r\n\r\nWEAPONS\r\n\r\nWhen a weapon has been picked up, you can equip it by clicking on it from your inventory. Each weapon has a special bonus value that increases the damage you do in battle. This value is shown in brackets when the weapon is equipped. You can drop a weapon with the ESC key. You must drop your current weapon before equipping a new one.\r\n\r\nPOTIONS\r\n\r\nPotions increase player health. Click on a potion in your inventory to use it. Potions can be reused as many times as necessary. Health cannot be increased over the maximum 100 points. If your health is reduced to 0, it's game over.\r\n\r\nCHARACTERS\r\n\r\nYou can fight characters in the game by interacting with them. On each interaction, you will do damage to your opponent and they will do damage to you. The battle is over when either you or your opponent's health is reduced to 0.\r\n");
		txtTheObjectOf.setBounds(10, 89, 380, 344);
		
		Label lblTheObjectOf = new Label(shlHelp, SWT.WRAP | SWT.CENTER);
		lblTheObjectOf.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		lblTheObjectOf.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.BOLD));
		lblTheObjectOf.setBounds(10, 49, 380, 34);
		lblTheObjectOf.setText("The object of the game is to complete all goals so you can hand in your COMP3350 assignment on time.");
		
		Label lblNewLabel = new Label(shlHelp, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(HelpWindow.class, "/assets/art/display/help_button.png"));
		lblNewLabel.setBounds(158, 10, 58, 25);
	}
}