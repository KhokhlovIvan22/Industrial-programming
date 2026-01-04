import java.io.*;

public class ProjectConnector {
    public static void saveSpecification(Specification spec) throws IOException {
        String fileName = "spec" + spec.getProjectID() + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(spec);
        }
        System.out.println(Localizer.t("connector.specSaved") + " " + fileName);
    }

    public static Specification loadSpecification(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = in.readObject();
            if (obj instanceof Specification)
                return (Specification) obj;
            else
                throw new IOException(Localizer.t("connector.invalidSpec"));
        }
    }

    public static void saveProject(ProjectHandler project) throws IOException {
        String fileName = "project" + project.getSpec().getProjectID() + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(project);
        }
        System.out.println(Localizer.t("connector.projectSaved") + " " + fileName);
    }

    public static ProjectHandler loadProject(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = in.readObject();
            if (obj instanceof ProjectHandler)
                return (ProjectHandler) obj;
            else
                throw new IOException(Localizer.t("connector.invalidProject"));
        }
    }
}
