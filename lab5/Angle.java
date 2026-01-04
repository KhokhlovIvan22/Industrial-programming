import java.util.*;

public class Angle implements Comparable<Angle>, Iterable <Integer>, Iterator <Integer> {
    private int degrees;
    private int minutes;

    public Angle (){
        degrees=0;
        minutes=0;
    }

    private void reduceRange() {
        while(minutes >= 60) {
            degrees += minutes / 60;
            minutes = minutes % 60;
        }
        while(minutes < 0) {
            minutes += 60;
            degrees -= 1;
        }
        while(degrees >= 360)
            degrees -= 360;
        while(degrees< 0)
            degrees += 360;
    }

    public Angle (int degrees, int minutes) {
        this.degrees=degrees;
        this.minutes=minutes;
        reduceRange();
    }

    public static Angle add (Angle a, Angle b) {
        return new Angle (a.degrees+b.degrees, a.minutes+b.minutes);
    }

    public static Angle sub (Angle a, Angle b) {
        return new Angle (a.degrees-b.degrees, a.minutes-b.minutes);
    }

    public static Angle mul (Angle a, int k) {
        return new Angle (a.degrees*k, a.minutes*k);
    }

    public static Angle div (Angle a, int k) {
        if (k==0) throw new IllegalArgumentException("Error! Division by 0");
        return new Angle (a.degrees/k, a.minutes/k);
    }

    public double toRadians () {
        double total = degrees + minutes / 60.0;
        return Math.toRadians(total);
    }

    @Override
    public String toString () {
        return degrees + "°" + minutes + "''";
    }

    public static Angle parse (String S) {
        if (!(S.contains("°") || (S.contains("''"))))
            throw new IllegalArgumentException("Error! Invalid input format");
        String[] parts = S.split("°");
        if (parts.length!=2)
            throw new IllegalArgumentException("Error! Invalid input format");
        int deg = Integer.parseInt(parts[0].trim());
        int min = Integer.parseInt(parts[1].replace("''","").trim());
        return new Angle (deg,min);
    }

    @Override
    public int compareTo (Angle other) {
        if (this.degrees < other.degrees)
            return -1;
        else if (this.degrees > other.degrees)
            return 1;
        else
        {
            if (this.minutes==other.minutes)
                return 0;
            else if (this.minutes<other.minutes)
                return -1;
            else
                return 1;
        }
    }

    @Override
    public Iterator <Integer> iterator(){
        return this;
    }

    private int current=0;

    @Override
    public boolean hasNext () {
        return current<2;
    }

    @Override
    public void remove () {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer next() {
        if (current == 0) {
            current++;
            return degrees;
        }
        if (current == 1) {
            current++;
            return minutes;
        }
        throw new NoSuchElementException();
    }

    public static class CompareByDegrees implements Comparator<Angle> {
        @Override
        public int compare(Angle a1, Angle a2) {
            return Integer.compare(a1.degrees, a2.degrees);
        }
    }

    public static class CompareByMinutes implements Comparator<Angle> {
        @Override
        public int compare(Angle a1, Angle a2) {
            return Integer.compare(a1.minutes, a2.minutes);
        }
    }
}
