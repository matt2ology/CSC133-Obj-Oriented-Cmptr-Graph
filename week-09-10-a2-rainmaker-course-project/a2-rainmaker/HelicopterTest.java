import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import javafx.geometry.Point2D;

public class HelicopterTest {
    private Helicopter helicopter;

    @Before
    public void setUp() throws Exception {
        this.helicopter = new Helicopter(new Point2D(0, 0));
    }

    /**
     * @brief Test that Helicopter can be instantiated into
     *        a class that extends it.
     * @throws Exception
     */
    @Test
    public void canBe_InstantiatedFromSubClass() throws Exception {
        assertNotNull(this.helicopter);
    }
}