import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Scanner;

public class Baggage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String flightNumber;
    private LocalDateTime departure;
    private String destination;
    private String passengerName;
    private int baggagePlaces;
    private double baggageWeight;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean validDeparture(String s) {
        try
        {
            LocalDateTime.parse(s, FORMATTER);
            return true;
        }
        catch (DateTimeParseException e)
        {
            return false;
        }
    }

    public Baggage(String flightNumber, LocalDateTime departure, String destination, String passengerName, int baggagePlaces, double baggageWeight) {
        if (flightNumber.isEmpty())
            throw new IllegalArgumentException("Flight number must be entered");
        if (!validDeparture(departure.format(FORMATTER)))
            throw new IllegalArgumentException("Invalid departure date/time");
        if (destination.isEmpty())
            throw new IllegalArgumentException("Destination must be entered");
        if (passengerName.isEmpty())
            throw new IllegalArgumentException("Passenger's name must be entered");
        if (baggagePlaces < 0)
            throw new IllegalArgumentException("Baggage places can't be negative");
        if (baggageWeight < 0)
            throw new IllegalArgumentException("Baggage weight can't be negative");
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.passengerName = passengerName;
        this.baggagePlaces = baggagePlaces;
        this.baggageWeight = baggageWeight;
    }

    public String getFlightNumber() { return flightNumber; }
    public String getDestination() { return destination; }
    public double getBaggageWeight() { return baggageWeight; }
    public LocalDateTime getDeparture() { return departure; }

    public static Baggage read(Scanner fin, boolean interactive) {
        if (interactive)
            System.out.print("Enter flight number: ");
        String flightNumber = fin.nextLine().trim();
        if (interactive)
            System.out.print("Enter departure time in format YYYY-MM-DD HH:MM (e.g. 2025-11-21 14:30): ");
        LocalDateTime departureTime = LocalDateTime.parse(fin.nextLine().trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        if (interactive)
            System.out.print("Enter destination: ");
        String destination = fin.nextLine().trim();
        if (interactive)
            System.out.print("Enter passenger name: ");
        String passengerName = fin.nextLine().trim();
        if (interactive)
            System.out.print("Enter baggage places: ");
        int places = Integer.parseInt(fin.nextLine().trim());
        if (interactive)
            System.out.print("Enter baggage weight: ");
        double weight = Double.parseDouble(fin.nextLine().trim());
        return new Baggage(flightNumber, departureTime, destination, passengerName, places, weight);
    }

    @Override
    public String toString() {
        return "Flight № " + flightNumber +
                "\nDeparture: " + departure +
                "\nDestination: " + destination +
                "\nPassenger: " + passengerName +
                "\nPlaces: " + baggagePlaces +
                "\nWeight: " + baggageWeight;
    }

    public static final Comparator<Baggage> byFlight =
            Comparator.comparing(Baggage::getFlightNumber);

    public static final Comparator<Baggage> byDeparture =
            Comparator.comparing(Baggage::getDeparture);

    public static final Comparator<Baggage> byDestination =
            Comparator.comparing(Baggage::getDestination);

    public static final Comparator<Baggage> byWeight =
            Comparator.comparingDouble(Baggage::getBaggageWeight);
}
