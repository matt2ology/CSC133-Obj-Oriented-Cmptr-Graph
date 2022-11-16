import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
    private Game game;

    @Before
    public void setUp() throws Exception {
        this.game = new Game();
    }

    /**
     * @brief Test that Game Class that extends Pane has setScaleY set to -1.
     * @summary We test this because we want to flip the y-axis so that
     *          up is positive and down is negative.
     * @throws Exception
     */
    @Test
    public void testGame_PaneIsVerticalAxisIsInverted() throws Exception {
        assertEquals(-1, this.game.getScaleY(), 0.001);
    }

}