import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException{
        // Define 6 edge-case polynomials
        Polynomial normal = new Polynomial(
            new double[]{6, -2, 5}, 
            new int[]{0, 1, 3});       // 6 - 2x + 5x^3

        Polynomial constant = new Polynomial(
            new double[]{7}, 
            new int[]{0});             // 7

        Polynomial zero = new Polynomial(
            new double[]{}, 
            new int[]{});              // 0

        Polynomial negative = new Polynomial(
            new double[]{-3, -2}, 
            new int[]{0, 1});          // -3 - 2x

        Polynomial bigExp = new Polynomial(
            new double[]{1}, 
            new int[]{10});            // x^10

        // NEW: unordered exponents
        Polynomial unordered = new Polynomial(
            new double[]{2, 5, 3}, 
            new int[]{5, 0, 2});       // input not sorted: 5 + 3x^2 + 2x^5

        Polynomial[] polys = {normal, constant, zero, negative, bigExp, unordered};
        String[] names = {"normal", "constant", "zero", "negative", "bigExp", "unordered"};

        // Test add method for all pairs
        double[] testXs = {-1, 0, 1, 2};

        for (int i = 0; i < polys.length; i++) {
            for (int j = 0; j < polys.length; j++) {
                Polynomial sum = polys[i].add(polys[j]);
                System.out.println("Testing " + names[i] + " + " + names[j]);

                for (double x : testXs) {
                    double expected = polys[i].evaluate(x) + polys[j].evaluate(x);
                    double actual = sum.evaluate(x);

                    if (Math.abs(expected - actual) < 1e-9) {
                        System.out.println("  PASS at x=" + x);
                    } else {
                        System.out.println("  FAIL at x=" + x +
                                           " expected " + expected +
                                           " but got " + actual);
                    }
                }
            }
        }
    

        // Test multiply
        for (int i = 0; i < polys.length; i++) {
            for (int j = 0; j < polys.length; j++) {
                Polynomial product = polys[i].multiply(polys[j]);
                System.out.println("Testing " + names[i] + " * " + names[j]);

                for (double x : testXs) {
                    double expected = polys[i].evaluate(x) * polys[j].evaluate(x);
                    double actual = product.evaluate(x);

                    if (Math.abs(expected - actual) < 1e-9) {
                        System.out.println("  PASS at x=" + x);
                    } else {
                        System.out.println("  FAIL at x=" + x +
                                           " expected " + expected +
                                           " but got " + actual);
                    }
                }
            }
        }

        System.out.println("=== Testing evaluate ===");
        System.out.println("normal at x=1: " + normal.evaluate(1));   // should be 9
        System.out.println("constant at x=100: " + constant.evaluate(100)); // should be 7
        System.out.println("zero at x=5: " + zero.evaluate(5));       // should be 0
        System.out.println("negative at x=2: " + negative.evaluate(2)); // should be -7
        System.out.println("bigExp at x=2: " + bigExp.evaluate(2));   // should be 1024
        System.out.println("unordered at x=2: " + unordered.evaluate(2)); // should be 5 + 12 + 64 = 81

        // =====================
        // 4. Test HASROOT
        // =====================
        System.out.println("=== Testing hasRoot ===");
        Polynomial rootPoly = new Polynomial(
            new double[]{-1, 1}, 
            new int[]{0, 1}); // x - 1
        System.out.println("rootPoly has root 1? " + rootPoly.hasRoot(1)); // true
        System.out.println("rootPoly has root 0? " + rootPoly.hasRoot(0)); // false

        System.out.println("zero poly has root 5? " + zero.hasRoot(5)); // tricky: true
    
        // =====================
        // 5. Test SaveToFile + File Constructor
        // =====================
        for (int i = 0; i < polys.length; i++) {
            Polynomial p = polys[i];

            // Save polynomial to file
            String filename = names[i] + ".txt";
            p.saveToFile(filename);

            // Load it back
            Polynomial reload = new Polynomial(new File(filename));

            // Test equivalence by comparing evaluate at different x
            System.out.println("Testing save/load for " + names[i]);
            for (double x : testXs) {
                double originalVal = p.evaluate(x);
                double reloadVal = reload.evaluate(x);

                if (Math.abs(originalVal - reloadVal) < 1e-9) {
                    System.out.println("  PASS at x=" + x);
                } else {
                    System.out.println("  FAIL at x=" + x +
                                       " original=" + originalVal +
                                       " reload=" + reloadVal);
                }
            }
        }
    }
}
