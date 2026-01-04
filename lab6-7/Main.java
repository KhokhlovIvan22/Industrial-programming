import java.io.*;
import java.util.*;

public class Main {
    private static HashMap<String, Long> customerIDs = new HashMap<>();
    private static long currentCustomerID = 1;

    private static long generateCustomerID(String customerName) {
        if (customerIDs.containsKey(customerName))
            return customerIDs.get(customerName);
        else {
            long newID = currentCustomerID++;
            customerIDs.put(customerName, newID);
            return newID;
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length >= 2)
            Localizer.init(args[0], args[1]);
        else
            Localizer.init("en", "GB");
        Scanner in = new Scanner(System.in);
        List<ProjectHandler> bureau = new ArrayList<>();
        System.out.println(Localizer.t("main.title") + "\n");
        while (true) {
            System.out.println(Localizer.t("main.currentList"));
            for (int i = 0; i < bureau.size(); ++i)
                System.out.println((i + 1) + ". " + bureau.get(i));
            System.out.println("\n" + Localizer.t("main.menuTitle"));
            System.out.println(Localizer.t("main.menu.create"));
            System.out.println(Localizer.t("main.menu.load"));
            System.out.println(Localizer.t("main.menu.exit"));
            System.out.print("\n" + Localizer.t("main.choose") + " ");
            String ch = in.nextLine().trim();
            System.out.println();
            switch (ch) {
                case "1":
                    System.out.println(Localizer.t("main.specTitle"));
                    System.out.print(Localizer.t("main.enterCustomer") + " ");
                    String customerName = in.nextLine().trim();
                    long customerID = generateCustomerID(customerName);
                    Specification spec = Specification.fill();
                    spec.setCustomerID(customerID);
                    System.out.println();
                    System.out.print(Localizer.t("main.enterAuthor") + " ");
                    String authorName = in.nextLine().trim();
                    ProjectHandler handler = new ProjectHandler(spec, customerName, authorName);
                    if (handler.processProject())
                        bureau.add(handler);
                    System.out.println();
                    break;
                case "2":
                    System.out.print(Localizer.t("main.enterFile") + " ");
                    String fileName = in.nextLine().trim();
                    ProjectHandler loaded = ProjectConnector.loadProject(fileName);
                    bureau.add(loaded);
                    System.out.println(Localizer.t("main.loaded"));
                    System.out.println(loaded.info());
                    break;
                case "3":
                    System.out.println(Localizer.t("main.finished"));
                    System.out.println(Localizer.t("main.total") + " " + bureau.size());
                    return;
                default:
                    System.out.println(Localizer.t("main.invalid"));
            }
        }
    }
}
