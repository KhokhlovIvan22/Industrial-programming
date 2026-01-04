import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;

public class Specification implements Serializable {
    private final String projectName;
    private long projectID;
    private long customerID;
    // architectural parameters
    private final int floors;
    private final double totalArea;
    private final double perimeter;
    private final String materialType;
    private final int elevatorsCount;
    private final boolean parking;

    private final double budget;
    private Date creationDate;
    private static long currentID = 1;

    private static synchronized long generateID() {
        return currentID++;
    }

    public Specification(String projectName, int floors, double totalArea,
                         double perimeter, String materialType, int elevatorsCount,
                         boolean parking, double budget) {
        if (projectName == null)
            throw new IllegalArgumentException(Localizer.t("spec.validation.name"));
        if (floors <= 0)
            throw new IllegalArgumentException(Localizer.t("spec.validation.floors"));
        if (totalArea <= 0)
            throw new IllegalArgumentException(Localizer.t("spec.validation.area"));
        if (perimeter <= 0)
            throw new IllegalArgumentException(Localizer.t("spec.validation.perimeter"));
        if (materialType == null)
            throw new IllegalArgumentException(Localizer.t("spec.validation.material"));
        if (elevatorsCount < 0)
            throw new IllegalArgumentException(Localizer.t("spec.validation.elevators"));
        if (budget <= 0)
            throw new IllegalArgumentException(Localizer.t("spec.validation.budget"));
        this.projectName = projectName;
        this.customerID = 0;
        this.floors = floors;
        this.totalArea = totalArea;
        this.perimeter = perimeter;
        this.materialType = materialType.toUpperCase();
        this.elevatorsCount = elevatorsCount;
        this.parking = parking;
        this.budget = budget;
        this.creationDate = new Date();
        this.projectID = generateID();
    }

    @Override
    public String toString() {
        return Localizer.t("spec.title") + " \"" + projectName + "\"\n" +
                Localizer.t("spec.projectID") + ": " + projectID + ", " +
                Localizer.t("spec.customerID") + ": " + customerID + "\n" +
                Localizer.t("spec.floors") + ": " + floors +
                "\n" + Localizer.t("spec.area") + ": " + totalArea + " m²" +
                "\n" + Localizer.t("spec.perimeter") + ": " + perimeter + " m" +
                "\n" + Localizer.t("spec.material") + ": " + materialType +
                "\n" + Localizer.t("spec.elevators") + ": " + elevatorsCount +
                "\n" + Localizer.t("spec.parking") + ": " + (parking ? Localizer.t("spec.parking.yes") : Localizer.t("spec.parking.no")) +
                "\n" + Localizer.t("spec.budget") + ": " + budget + " USD" +
                "\n" + Localizer.t("spec.created") + " " + creationDate + "\n";
    }

    public static Specification fill() {
        Scanner in = new Scanner(System.in);
        System.out.print(Localizer.t("spec.input.name"));
        String projectName = in.nextLine();
        System.out.print(Localizer.t("spec.input.floors"));
        int floors = in.nextInt();
        System.out.print(Localizer.t("spec.input.area"));
        double totalArea = in.nextDouble();
        System.out.print(Localizer.t("spec.input.perimeter"));
        double perimeter = in.nextDouble();
        in.nextLine();
        System.out.print(Localizer.t("spec.input.material"));
        String materialType = in.nextLine();
        System.out.print(Localizer.t("spec.input.elevators"));
        int elevatorsCount = in.nextInt();
        in.nextLine();
        System.out.print(Localizer.t("spec.input.parking"));
        String parkingAnswer = in.nextLine().trim().toUpperCase();
        boolean parking = parkingAnswer.equals("YES") || parkingAnswer.equals("ДА") || parkingAnswer.equals("ТАК");
        System.out.print(Localizer.t("spec.input.budget"));
        double budget = in.nextDouble();
        return new Specification(projectName, floors, totalArea,
                perimeter, materialType, elevatorsCount,
                parking, budget);
    }

    public void setCustomerID(long customerID) {
        if (customerID <= 0)
            throw new IllegalArgumentException(Localizer.t("spec.validation.customerID"));
        this.customerID = customerID;
    }

    public int getFloors() {
        return floors;
    }

    public double getBudget() {
        return budget;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public String getMaterialType() {
        return materialType;
    }

    public boolean hasParking() {
        return parking;
    }

    public int getElevatorsCount() {
        return elevatorsCount;
    }

    public String getProjectName() {
        return projectName;
    }

    public long getCustomerID() {
        return customerID;
    }

    public long getProjectID() {
        return projectID;
    }
}