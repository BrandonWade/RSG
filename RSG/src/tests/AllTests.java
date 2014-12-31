package tests;

import rsg.objects.OurDisplay;
import tests.objects.TestGameCharacter;
import tests.objects.TestGameItem;
import tests.objects.TestGameObject;
import tests.objects.TestGameScenery;
import tests.objects.TestGoal;
import tests.objects.TestStory;
import tests.objects.TestTile;
import tests.persistence.TestDataAccess;
import tests.persistence.TestDataAccessStub;
import tests.persistence.TestWorld;
import tests.persistence.TestWorldInfo;
import tests.presentation.TestGuiWindow;
import tests.presentation.TestWelcomeDialog;
import tests.processing.TestBattleRunner;
import tests.processing.TestCharacterMove;
import tests.processing.TestGameRunner;
import tests.processing.TestInteraction;
import tests.processing.TestMapCreator;
import tests.processing.TestStoryManager;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
	private static TestSuite suite;

	public static Test suite()
	{
		suite = new TestSuite(AllTests.class.getName());
		OurDisplay.createDisplay();
		//$JUnit-BEGIN$
		testObjects();
		testPersistence();
		testProcessing();
		testPresentation();
		//$JUnit-END$
		
		return suite;
	}

	private static void testObjects()
	{
		suite.addTestSuite(TestGameCharacter.class);
		suite.addTestSuite(TestGameObject.class);
		suite.addTestSuite(TestGameScenery.class);
		suite.addTestSuite(TestGameItem.class);
		suite.addTestSuite(TestTile.class);
		suite.addTestSuite(TestStory.class);
		suite.addTestSuite(TestGoal.class);
	}
	
	private static void testPersistence()
	{		
		suite.addTestSuite(TestDataAccess.class);
		suite.addTestSuite(TestDataAccessStub.class);
		suite.addTestSuite(TestWorldInfo.class);
		suite.addTestSuite(TestWorld.class);
	}
	
	private static void testPresentation()
	{
		suite.addTestSuite(TestGuiWindow.class);
		suite.addTestSuite(TestWelcomeDialog.class);
	}
	
	private static void testProcessing()
	{
		suite.addTestSuite(TestGameRunner.class);
		suite.addTestSuite(TestBattleRunner.class);
		suite.addTestSuite(TestMapCreator.class);
		suite.addTestSuite(TestInteraction.class);
		suite.addTestSuite(TestCharacterMove.class);
		suite.addTestSuite(TestStoryManager.class);
	}
}