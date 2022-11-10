import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class GlobalsTest {
    private Globals globals;

    @Before
    public void setUp() throws Exception {
        this.globals = new Globals();
    }

    /**
     * @brief Test that Globals class can be instantiated.
     * @throws Exception
     */
    @Test
    public void testGlobalsCanBeInstantiated() throws Exception {
        assertNotNull(this.globals);
    }

    /**
     * @brief Test that Globals class has GAME_APP_DIMENSIONS set to 800x800.
     * @throws Exception
     */
    @Test
    public void testGlobalsGameAppDimensions() throws Exception {
        assertEquals(800, Globals.GAME_APP_DIMENSIONS.getWidth(), 0.001);
        assertEquals(800, Globals.GAME_APP_DIMENSIONS.getHeight(), 0.001);
    }
}
