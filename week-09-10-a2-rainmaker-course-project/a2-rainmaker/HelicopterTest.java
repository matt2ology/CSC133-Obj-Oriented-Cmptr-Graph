
/**
 * @see https://www.oreilly.com/library/view/java-extreme-programming/0596003870/ch04s06.html
 * @see http://etutorials.org/Programming/Java+extreme+programming/Chapter+4.+JUnit/4.6+Set+Up+and+Tear+Down/
 * @see https://medium.com/feedzaitech/writing-testable-code-b3201d4538eb
 * @see https://www.toptal.com/qa/how-to-write-testable-code-and-why-it-matters
 */
import org.junit.Test;

import junit.framework.TestCase;

public class HelicopterTest extends TestCase {
    private Helicopter helicopter;

    @Override
    protected void setUp() throws Exception {
        this.helicopter = new Helicopter();
    }

    /**
     * @brief Test if helicopter is an instance of Helicopter class.
     *        In having this test we can be sure that the class
     *        can be instantiated.
     * 
     * @summary Test Steps:
     *          <ol>
     *          <li>Instantiate a helicopter object.</li>
     *          <li>Check if the object is an instance of
     *          Helicopter class.</li>
     *          </ol>
     */
    @Test
    public void testHelicopter() {
        assertTrue(helicopter instanceof Helicopter);
    }

    /**
     * @brief Test if helicopter initial ignition on Instantiation is false.
     * @summary Test Steps:
     *          <ol>
     *          <li>Assert that the helicopter ignition is false.</li>
     *          </ol>
     */
    @Test
    public void testHelicopterInitialIgnitionOnInstantiationFalse() {
        assertFalse(helicopter.isIgnitionOn());
    }

    /**
     * @brief Test if helicopter initial ignition on Instantiation is false.
     * @summary Test Steps:
     *          <ol>
     *          <li>Assert that the helicopter ignition is false.</li>
     *          </ol>
     */
    @Test
    public void testHelicopterToggleIgnitionTrue() {
        helicopter.toggleIgnition();
        assertTrue(helicopter.isIgnitionOn());
    }

}
