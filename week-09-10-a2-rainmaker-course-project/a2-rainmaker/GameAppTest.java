import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class GameAppTest extends TestCase {

    private GameApp gameApp;

    @Before
    public void setUp() throws Exception {
        this.gameApp = new GameApp();
    }

    /**
     * @brief Test that we can instantiate a Game object.
     * @throws Exception
     */
    @Test
    public void testGameAppCanBeInstantiated() throws Exception {
        assertNotNull(this.gameApp);
    }
}
