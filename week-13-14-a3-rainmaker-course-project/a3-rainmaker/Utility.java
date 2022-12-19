import java.util.Random;

/**
 * Utility
 */
class Utility {
    private static Random random = new Random();

    /**
     * @brief Generates a random number in the inclusive range [min, max]
     *        regardless of the order of min and max arguments passed in.
     * @param a the first number in the range (min or max)
     * @param b the second number in the range (min or max)
     * @return a random number in the inclusive range [min, max]
     */
    public static double generateRandomNumberInRange(double a, double b) {
        double min = Math.min(a, b);
        double max = Math.max(a, b);
        return ((random.nextDouble() * (max - min)) + min);
    }
}