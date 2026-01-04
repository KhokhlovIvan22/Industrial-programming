import java.io.*;
import java.util.Scanner;

public class ProjectHandler implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String customerName;
    private final String authorName;
    private final Specification spec;
    private Invoice invoice;
    private Brigade brigade;

    public ProjectHandler(Specification spec, String customerName, String authorName) {
        this.spec = spec;
        this.customerName = customerName;
        this.authorName = authorName;
    }

    public boolean processProject() throws IOException {
        House house = new House(spec);
        invoice = new Invoice(house);
        if (spec.getBudget() < invoice.getTotalCost()) {
            System.out.println(Localizer.t("project.notEnoughFunds"));
            return false;
        }
        ProjectConnector.saveSpecification(spec);
        System.out.println();
        System.out.println(house);
        System.out.println("\n" + Localizer.t("project.budgetOk"));
        System.out.println();
        System.out.println(invoice);
        System.out.println();
        brigade = new Brigade();
        Scanner in = new Scanner(System.in);
        System.out.println(Localizer.t("project.enterBrigade"));
        String[] members = in.nextLine().split(",");
        for (String m: members)
            brigade.addWorker(m.trim());
        for (int i = 0; i < members.length; ++i)
            System.out.println((i + 1) + ". " + members[i]);
        System.out.println(Localizer.t("project.selectForeman"));
        int ch = in.nextInt();
        brigade.setForeman(ch - 1);
        System.out.println(Localizer.t("project.foremanSelected") + " " + brigade.getForeman());
        System.out.println();
        System.out.println(Localizer.t("project.brigadeFormed"));
        System.out.println(brigade);
        ProjectConnector.saveProject(this);
        return true;
    }

    @Override
    public String toString() {
        return Localizer.t("project.title") + " \"" + spec.getProjectName() + "\", "
                + Localizer.t("project.id") + ": " + spec.getProjectID()
                + ", " + Localizer.t("project.author") + ": " + authorName
                + ", " + Localizer.t("project.customer") + ": " + customerName;
    }

    public String info() {
        return Localizer.t("project.infoTitle") + "\n"
                + Localizer.t("project.infoCustomer") + ": " + customerName + "\n"
                + Localizer.t("project.infoAuthor") + ": " + authorName + "\n\n"
                + Localizer.t("project.infoSpec") + "\n"
                + spec.toString() + "\n"
                + Localizer.t("project.infoInvoice") + "\n"
                + invoice.toString() + "\n"
                + "\n" + Localizer.t("project.infoBrigade") + "\n"
                + brigade.toString();
    }

    public Specification getSpec() {
        return spec;
    }
}
