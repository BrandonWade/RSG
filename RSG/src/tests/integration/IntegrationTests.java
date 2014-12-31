package tests.integration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class IntegrationTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Integration tests");
        suite.addTestSuite(TestWorld.class);
        suite.addTestSuite(TestCharacterMove.class);
        suite.addTestSuite(TestMapTraversability.class);
        suite.addTestSuite(TestGameRunner.class);
        return suite;
    }
}
