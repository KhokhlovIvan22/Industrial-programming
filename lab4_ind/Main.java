import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void runTests() {
        Fraction f1 = new Fraction();
        assert f1.cmp(new Fraction(0,1))==0 : "Error: default constructor failed";

        assert Fraction.findGCD(12,18)==6 : "Error: incorrect GCD calculation";
        assert Fraction.findLCM(4,10)==20 : "Error: incorrect LCM calculation";

        Fraction f2 = new Fraction(18,24);
        assert f2.cmp(new Fraction(3,4))==0 : "Error: parameterized constructor failed";

        assert new Fraction(3,4).cmp(new Fraction(6,8))==0 : "Error: comparison of equal fractions failed";
        assert new Fraction(1,3).cmp(new Fraction(1,4))==1 : "Error: comparison of larger fraction with smaller failed";
        assert new Fraction(-1,5).cmp(new Fraction())==-1 : "Error: comparison of smaller fraction with larger failed";

        Fraction sum = new Fraction(1,3).add(new Fraction(1,2));
        assert sum.cmp(new Fraction(5,6))==0 : "Error: addition failed";

        Fraction diff = new Fraction(1,4).sub(new Fraction(1,2));
        assert diff.cmp(new Fraction(-1,4))==0 : "Error: subtraction failed";

        Fraction prod = new Fraction(8,3).mul(new Fraction(1,8));
        assert prod.cmp(new Fraction(1,3))==0 : "Error: multiplication failed";

        Fraction quot = new Fraction(1,5).div(new Fraction(4,3));
        assert quot.cmp(new Fraction(3,20))==0 : "Error: division failed";

        Fraction f3 = Fraction.parse(" -6 / 8 ");
        assert f3.cmp(new Fraction(-3,4))==0 : "Error: parse method failed";

        assert new Fraction(0,5).toString().equals("0") : "Error: toString for zero fraction failed";
        assert new Fraction(5,5).toString().equals("1") : "Error: toString for fraction equal to 1 failed";
        assert new Fraction(-3,4).toString().equals("-3/4") : "Error: toString for negative fraction failed";

        System.out.println("All tests passed successfully");
    }

    public static void main(String[] args) {
        System.out.println("Available program options:");
        System.out.println("1. Work with an array of fractions entered from keyboard");
        System.out.println("2. Run Fraction class tests");
        System.out.print("Enter your choice: ");
        Scanner in = new Scanner(System.in);
        int ch = in.nextInt();
        System.out.println();
        switch (ch) {
            case 1: {
                System.out.print("Enter array size: ");
                int k = in.nextInt();
                if (k <= 0)
                    throw new InputMismatchException("Error: array size must be strictly positive");
                in.nextLine();
                ArrayList<Fraction> fractions = new ArrayList<Fraction>();
                System.out.println("Enter fractions in format \"m/n\"");
                for (int i = 0; i < k; ++i) {
                    String S = in.nextLine();
                    fractions.add(Fraction.parse(S));
                }
                System.out.println();
                fractions.sort((a, b) -> a.cmp(b));
                System.out.print("Sorted array of fractions: ");
                for (Fraction f : fractions) {
                    System.out.print(f + " ");
                }
                System.out.println();
                Fraction sum = new Fraction();
                Fraction prod = new Fraction(1, 1);
                for (Fraction f : fractions) {
                    sum = sum.add(f);
                    prod = prod.mul(f);
                }
                System.out.println("Sum of fractions: " + sum);
                System.out.println("Product of fractions: " + prod);
                break;
            }
            case 2: {
                runTests();
                break;
            }
            default: {
                throw new InputMismatchException("Error: only options 1 or 2 are allowed");
            }
        }
    }
}
