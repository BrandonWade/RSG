package tests.presentation;

import org.eclipse.swt.widgets.Shell;

import rsg.presentation.WelcomeDialog;
import junit.framework.TestCase;

public class TestWelcomeDialog extends TestCase
{	
	private WelcomeDialog testDialog;
	private Shell shell;
	
	public TestWelcomeDialog(String arg0) 
	{
		super(arg0);
	}
	
	public void setUp()
	{
		shell = new Shell();
		testDialog = new WelcomeDialog(shell,0);
	}
	
	public void testInput()
	{
		//test normal input
		assertTrue(testDialog.checkInput("Normal"));
		
		//test single character
		assertTrue(testDialog.checkInput("N"));
		
		//test blank input
		assertFalse(testDialog.checkInput(""));
		
		//test white space only input
		assertFalse(testDialog.checkInput("   "));
		
		//test input that is too long
		assertFalse(testDialog.checkInput("This is really long!!!"));
	}
}