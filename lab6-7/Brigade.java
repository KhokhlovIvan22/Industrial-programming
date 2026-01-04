import java.io.Serializable;
import java.util.ArrayList;

public class Brigade implements Serializable {
    private ArrayList<String> members;

    public Brigade() {
        members = new ArrayList<>();
    }

    public Brigade(ArrayList<String> members) {
        this.members = members;
    }

    private int foremanIndex = -1;

    public void addWorker(String worker) {
        members.add(worker);
    }

    public void setForeman(int index) {
        if (index >= 0 && index < members.size()) {
            foremanIndex = index;
        } else {
            System.out.println(Localizer.t("brigade.invalidIndex"));
        }
    }

    public void removeMember(String member) {
        int index = members.indexOf(member);
        if (index != -1) {
            members.remove(index);
            if (index == foremanIndex)
                foremanIndex = -1;
            else if (index < foremanIndex)
                foremanIndex--;
        }
    }

    public String getForeman() {
        if (foremanIndex >= 0 && foremanIndex < members.size())
            return members.get(foremanIndex);
        return null;
    }

    @Override
    public String toString() {
        if (members.isEmpty()) {
            return Localizer.t("brigade.empty");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < members.size(); ++i) {
                result.append(members.get(i));
                if (i == foremanIndex)
                    result.append(" ").append(Localizer.t("brigade.foreman"));
                result.append("\n");
            }
            return result.toString();
        }
    }
}
