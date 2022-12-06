import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import javafx.geometry.Point2D;

public class MoveableObjectTest {
    private MoveableObject moveableObject;

    @Before
    public void setUp() throws Exception {
        // an anonymous class that extends MoveableObject so we can test it.
        this.moveableObject = new MoveableObject(new Point2D(0, 0)) {
        };
    }

    /**
     * @brief Test that MoveableObject can be instantiated into a
     *        class that extends it.
     * @throws Exception
     */
    @Test
    public void canBe_InstantiatedFromSubClass() throws Exception {
        assertNotNull(this.moveableObject);
    }
}
