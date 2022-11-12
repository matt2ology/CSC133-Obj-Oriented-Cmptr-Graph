import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

// The Three Rules of TDD
// 1. Write production code only to pass a failing unit test.
// 2. Write no more of a unit test than sufficient to fail
// (compilation failures are failures).
// 3. Write no more production code than necessary to pass
// the one failing unit test. 

public class GameObjectTest {
    private GameObject gameObject;

    @Before
    public void setUp() throws Exception {
        // Instantiate an anonymous subclass of GameObject to test.
        gameObject = new GameObject() { }; 
    }

    /**
     * @brief Test that GameObject can be instantiated.
     */
    @Test
    public void testGameObjectCanBeInstantiated() {
        assertNotNull(this.gameObject);
    }

}
