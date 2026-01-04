import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

public class House implements Serializable {
    private final Specification spec;
    private transient double designCost;
    private transient double constructionCost;

    private static final double DESIGN_RATE = 5.0;
    private static final double ELEVATOR_COST = 20000.0;
    private static final double PARKING_COST = 15000.0;
    private static final int FLOOR_HEIGHT = 3;
    private static final HashMap<String, Double> MATERIAL_PRICES = new HashMap<>();
    static {
        MATERIAL_PRICES.put("BRICK", 50.5);
        MATERIAL_PRICES.put("CONCRETE", 100.0);
        MATERIAL_PRICES.put("STEEL", 125.7);
        MATERIAL_PRICES.put("WOOD", 40.0);
        MATERIAL_PRICES.put("MIXED", 90.0);
    }

    private void calculateCosts() {
        designCost = spec.getTotalArea() * DESIGN_RATE;
        constructionCost = spec.getPerimeter() * FLOOR_HEIGHT * spec.getFloors() * MATERIAL_PRICES.get(spec.getMaterialType()) +
                spec.getElevatorsCount()*ELEVATOR_COST;
        if (spec.hasParking())
            constructionCost+=PARKING_COST;
    }

    public House(Specification spec) {
        this.spec = spec;
        calculateCosts();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        calculateCosts();
    }

    public double getDesignCost() {
        return designCost;
    }

    public double getConstructionCost() {
        return constructionCost;
    }

    public Specification getSpecification() {
        return spec;
    }

    @Override
    public String toString() {
        return Localizer.t("house.title") + " \"" + spec.getProjectName() + "\"" +
                "\n" + Localizer.t("house.floors") + ": " + spec.getFloors() +
                "\n" + Localizer.t("house.area") + ": " + spec.getTotalArea() + " m²" +
                "\n" + Localizer.t("house.perimeter") + ": " + spec.getPerimeter() + " m" +
                "\n" + Localizer.t("house.material") + ": " + spec.getMaterialType() +
                "\n" + Localizer.t("house.elevators") + ": " + spec.getElevatorsCount() +
                "\n" + Localizer.t("house.parking") + ": " + (spec.hasParking() ? Localizer.t("house.parking.yes") : Localizer.t("house.parking.no")) +
                "\n-----------------------------" +
                "\n" + Localizer.t("house.designCost") + ": " + designCost + " USD" +
                "\n" + Localizer.t("house.constructionCost") + ": " + constructionCost + " USD";
    }

}