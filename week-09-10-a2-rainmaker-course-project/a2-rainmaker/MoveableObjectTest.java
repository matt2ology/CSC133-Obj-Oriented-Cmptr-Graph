import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoveableObjectTest {
    private MoveableObject moveableObject;

    @Before
    public void setUp() throws Exception {
        /**
         * Although MoveableObject is an abstract class, we can still
         * instantiate it because we are not instantiating the abstract
         * class itself, but rather an anonymous subclass of it.
         */
        moveableObject = new MoveableObject() {
            @Override
            public void move() {
            }
        };
    }

    /**
     * @brief Test that MoveableObject can be instantiated.
     */
    @Test
    public void canBeInstantiated() {
        assertNotNull(this.moveableObject);
    }


}
