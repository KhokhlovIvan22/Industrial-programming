import java.text.NumberFormat;
import java.util.*;

public class Main {
    public static void runTests() {
        Angle a0 = new Angle();
        assert a0.compareTo(new Angle(0,0)) == 0 : "Error: default constructor failed";

        Angle a1 = new Angle(98, 67);
        assert a1.toString().equals("99°7''") : "Error: parametrized constructor failed";

        Angle sum = Angle.add(new Angle(10,30), new Angle(20,40));
        assert sum.toString().equals("31°10''"): "Error: addition failed";

        Angle diff = Angle.sub(new Angle(50,20), new Angle(20,50));
        assert diff.toString().equals("29°30''"): "Error: subtraction failed";

        Angle prod = Angle.mul(new Angle(10,30), 2);
        assert prod.toString().equals("21°0''"): "Error: multiplication failed";

        Angle quot = Angle.div(new Angle(30,10), 2);
        assert quot.toString().equals("15°5''"): "Error: division failed";

        boolean d = false;
        try {
            Angle.div(new Angle(10,0), 0);
        } catch (IllegalArgumentException e) {
            d = true;
        }
        assert d: "Error: division by zero not detected";

        double rad = new Angle(180,0).toRadians();
        assert Math.abs(rad - Math.PI) < 1e-9: "Error: toRadians method failed";

        Angle parsed = Angle.parse("45°30''");
        assert parsed.toString().equals("45°30''"): "Error: parse & toString methods failed";

        assert new Angle(10,0).compareTo(new Angle(20,0)) < 0: "Error: compareTo failed (less)";
        assert new Angle(20,0).compareTo(new Angle(10,0)) > 0: "Error: compareTo failed (greater)";
        assert new Angle(15,30).compareTo(new Angle(15,30)) == 0: "Error: compareTo failed (equal)";

        Angle d1 = new Angle(10,50);
        Angle d2 = new Angle(20,10);
        assert new Angle.CompareByDegrees().compare(d1,d2) < 0: "Error: CompareByDegrees failed";
        assert new Angle.CompareByMinutes().compare(d1,d2) > 0: "Error: CompareByMinutes failed";

        Angle a2 = new Angle(12,34);
        int[] parts = new int[2];
        int index = 0;
        for (int p: a2) {
            parts[index++] = p;
        }
        assert parts[0] == 12 && parts[1] == 34 : "Error: Iteration failed";

        boolean thrown = false;
        try {
            a2.next(); // current уже == 2 после for-each
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assert thrown: "Error: next() after iteration did not throw NoSuchElementException";

        System.out.println("All tests passed successfully");
    }

    public static void main(String[] args) {
        System.out.println("Available program options:");
        System.out.println("1. Work with an array of angles entered from keyboard");
        System.out.println("2. Run Angle class tests");
        System.out.print("Enter your choice: ");
        Scanner in = new Scanner(System.in);
        int ch = in.nextInt();
        System.out.println();
        switch (ch) {
            case 1: {
                System.out.print("Enter array size: ");
                int n = in.nextInt();
                if (n <= 0)
                    throw new InputMismatchException("Error: array size must be strictly positive");
                in.nextLine();
                ArrayList<Angle> list = new ArrayList<Angle>();
                System.out.println("Enter angles in format 100°00''");
                for (int i = 0; i < n; ++i) {
                    String S = in.nextLine();
                    list.add(Angle.parse(S));
                }
                System.out.println();
                NumberFormat formatter = NumberFormat.getNumberInstance();
                formatter.setMaximumFractionDigits(3);
                System.out.print("Angles in radians: ");
                for (Angle a: list)
                    System.out.print(formatter.format(a.toRadians()) + "   ");
                System.out.println();
                System.out.println();
                Angle sum = new Angle(0, 0);
                for (Angle a : list)
                    sum = Angle.add(sum, a);
                System.out.println("Summary of all elements = " + sum);
                System.out.println();
                int sumDeg = 0;
                for (Angle ang: list) {
                    int i = 0;
                    for (int part: ang) {
                        if (i==0)
                            sumDeg += part;
                        i++;
                    }
                }
                System.out.println("The summary of only degree parts of angles = " + sumDeg + "°");
                System.out.println();
                System.out.print("Enter an integer multiplier: ");
                int k = in.nextInt();
                System.out.print("The result of multiplying each element by " + k+ ": ");
                for (Angle a: list)
                    System.out.print(Angle.mul(a, k)+"  ");
                System.out.println();
                System.out.println();
                Collections.sort(list);
                System.out.println("Array sorted by natural order (degrees → minutes):");
                for (Angle a: list)
                    System.out.print(a + "  ");
                System.out.println();
                System.out.println();
                Collections.sort(list, new Angle.CompareByDegrees());
                System.out.println("Array sorted by degrees:");
                for (Angle a: list)
                    System.out.print(a + "  ");
                System.out.println();
                System.out.println();
                Collections.sort(list, new Angle.CompareByMinutes());
                System.out.println("Array sorted by minutes:");
                for (Angle a: list)
                    System.out.print(a + "  ");
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
