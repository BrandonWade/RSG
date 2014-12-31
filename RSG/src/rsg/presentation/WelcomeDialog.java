package rsg.presentation;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import acceptanceTests.EventLoop;
import acceptanceTests.Register;
import rsg.objects.GamePlayer;
import rsg.objects.OurDisplay;
import rsg.persistence.Services;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class WelcomeDialog extends Dialog
{
	protected Object result;
	protected Shell shell = new Shell(getParent(), getStyle());
	private Label welcome_1 = new Label(shell, SWT.NONE);
	private Text text;
	private Button btnOK;
	private Button btnQuit;
	private Label lblError = new Label(shell, SWT.NONE);
	private static String playerName;

	public WelcomeDialog(Shell parent, int style)
	{
		super(parent, style);
		Register.newWindow(this);
		setText("SWT Dialog");
		playerName = "";
	}

	public Object open()
	{
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		
		if (EventLoop.isEnabled())
		{
			while (!shell.isDisposed())
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
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		shell.setSize(350, 300);
		shell.setText(getText());
			
		welcome_1.setImage(SWTResourceManager.getImage(WelcomeDialog.class, "/assets/art/display/welcome_1.png"));
		welcome_1.setBounds(80, 47, 180, 50);
	    shell.setLocation(mainMonitor.getBounds().x + (mainMonitor.getBounds().width - shell.getBounds().width) / 2, mainMonitor.getBounds().y + (mainMonitor.getBounds().height - shell.getBounds().height) / 2);
		
	    Label lblWhatCanWe = new Label(shell, SWT.NONE);
		lblWhatCanWe.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		lblWhatCanWe.setAlignment(SWT.CENTER);
		lblWhatCanWe.setFont(SWTResourceManager.getFont("Courier", 12, SWT.BOLD));
		lblWhatCanWe.setBounds(35, 120, 271, 24);
		lblWhatCanWe.setText("What can we call you?");
		
		text = new Text(shell, SWT.NONE);
		text.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent arg0)
			{
				if (arg0.keyCode == 13)
				{
					if (checkInput(text.getText()))
					{
						legalInput();
					}
					else
					{
						errorInput();
					}
				}
			}
		});
		
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		text.setTextLimit(30);
		text.setFont(SWTResourceManager.getFont("Courier", 12, SWT.BOLD));
		text.setBounds(88, 154, 131, 20);
		
		lblError.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		lblError.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblError.setAlignment(SWT.CENTER);
		lblError.setFont(SWTResourceManager.getFont("Courier", 10, SWT.NORMAL));
		lblError.setBounds(10, 185, 330, 31);
		lblError.setText("Error: Name must be between 1 and 10 characters.");
		lblError.setVisible(false);
		
		btnOK = new Button(shell, SWT.NONE);
		btnOK.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		btnOK.addSelectionListener(new SelectionAdapter() 
		{
			public void widgetSelected(SelectionEvent e)
			{
				
				if (checkInput(text.getText()))
				{
					legalInput();
				}
				else
				{
					errorInput();
				}
			}
		});
		
		btnOK.setImage(SWTResourceManager.getImage(WelcomeDialog.class, "/assets/art/display/ok_button.png"));
		btnOK.setBounds(231, 152, 29, 25);
		
		btnQuit = new Button(shell, SWT.NONE);
		btnQuit.addSelectionListener(new SelectionAdapter() 
		{
			public void widgetSelected(SelectionEvent e) 
			{
				shell.dispose();
				//shut everything down
				Services.closeDataAccess();
			}
		});
		
		btnQuit.setImage(SWTResourceManager.getImage(WelcomeDialog.class, "/assets/art/display/quit_button.png"));
		btnQuit.setDragDetect(false);
		btnQuit.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		btnQuit.setBounds(138, 222, 58, 25);
	}
	
	public boolean checkInput(String newInput)
	{
		boolean result = false;
		
		//remove white space
		newInput = newInput.trim();
		
		if (newInput.length() > 0 && newInput.length() < 11)
		{
			result = true;
			playerName = newInput;
		}
		
		return result;
	}
	
	private void errorInput()
	{
		lblError.setVisible(true);
	}
	
	private void legalInput()
	{
		lblError.setVisible(false);
		GamePlayer.setName(playerName);
		shell.dispose();
	}
}