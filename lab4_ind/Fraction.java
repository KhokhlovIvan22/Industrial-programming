public class Fraction {
    private int m;
    private int n;

    public Fraction() {
        this.m=0;
        this.n=1;
    }

    public static int findGCD (int a,int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (a==0 || b==0)
            return (a+b);
        while (a!=b)
        {
            if (a>b)
                a-=b;
            else b-=a;
        }
        return a;
    }

    public static int findLCM(int a,int b) {
        if (a==0 && b==0)
            return 0;
        return (a*b)/findGCD(a,b);
    }

    private void fractionReducing () {
        int gcd=findGCD(n,m);
        n/=gcd;
        m/=gcd;
    }

    public Fraction(int numerator, int denominator) {
        if (denominator==0) throw new IllegalArgumentException("Error! Denominator cannot be zero");
        this.m=numerator;
        this.n=denominator;
        fractionReducing();
    }

    public Fraction (Fraction other) {
        if (other==null) throw new IllegalArgumentException("Error! Copying failed");
        this.m = other.m;
        this.n = other.n;
        fractionReducing();
    }

    public int cmp(Fraction other) {
        int gcm = findLCM(n, other.n);
        if (m*(gcm/n) == other.m*(gcm/other.n))
            return 0;
        else
        if (m*(gcm/n) < other.m*(gcm/other.n))
            return -1;
        else return 1;
    }

    public Fraction add (Fraction other) {
        int newDenominator = findLCM(n,other.n);
        int newNumerator = m*(newDenominator/n)+other.m*(newDenominator/other.n);
        return new Fraction(newNumerator,newDenominator);
    }

    public Fraction sub (Fraction other) {
        int newDenominator = findLCM(n,other.n);
        int newNumerator = m*(newDenominator/n)-other.m*(newDenominator/other.n);
        return new Fraction(newNumerator,newDenominator);
    }

    public Fraction mul (Fraction other) {
        return new Fraction(m*other.m,n*other.n);
    }

    public Fraction div (Fraction  other) {
        if (other.n==0) throw new IllegalArgumentException("Error! Division by zero");
        return new Fraction (m*other.n,n*other.m);
    }

    public static Fraction parse (String S) {
        String[] parts = S.split("/");
        if (parts.length != 2) throw new IllegalArgumentException("Error! Invalid fraction format");
        int m = Integer.parseInt(parts[0].trim());
        int n = Integer.parseInt(parts[1].trim());
        return new Fraction(m, n);
    }

    @Override
    public String toString() {
        if (m==0) return "0";
        if (m==n) return Integer.toString(m);
        return m + "/" + n;
    }
}