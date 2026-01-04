import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;

public class Invoice implements Serializable {
    private final long invoiceID;
    private final House house;
    private Date signingDate;

    private static long currentID = 1;
    private static synchronized long generateID() {
        return currentID++;
    }

    public Invoice(House house) {
        this.house = house;
        this.signingDate = new Date();
        this.invoiceID = generateID();
    }

    public long getInvoiceID() {
        return invoiceID;
    }

    public Date getIssueDate() {
        return signingDate;
    }

    public House getHouse() {
        return house;
    }

    public double getTotalCost() {
        return house.getDesignCost() + house.getConstructionCost();
    }

    public double getPricePerSquareMeter() {
        return getTotalCost() / house.getSpecification().getTotalArea();
    }

    @Override
    public String toString() {
        Specification spec = house.getSpecification();
        NumberFormat formatter = NumberFormat.getNumberInstance(Localizer.locale());
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        return Localizer.t("invoice.title") + " #" + invoiceID + " " +
                Localizer.t("invoice.forProject") + " \"" + spec.getProjectName() + "\"" +
                "\n" + Localizer.t("invoice.customerID") + ": " + spec.getCustomerID() +
                "\n" + Localizer.t("invoice.issuedOn") + ": " + signingDate +
                "\n-----------------------------" +
                "\n" + Localizer.t("invoice.designCost") + ": " + formatter.format(house.getDesignCost()) + " USD" +
                "\n" + Localizer.t("invoice.constructionCost") + ": " + formatter.format(house.getConstructionCost()) + " USD" +
                "\n" + Localizer.t("invoice.total") + ": " + formatter.format(getTotalCost()) + " USD" +
                "\n" + Localizer.t("invoice.ppm") + ": " + formatter.format(getPricePerSquareMeter()) + " USD";
    }
}
