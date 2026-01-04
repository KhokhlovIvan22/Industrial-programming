import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new File("input.txt"), "UTF-8");
        ArrayList<Group> groups = new ArrayList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(",");
            if (parts.length != 6)
                throw new IllegalArgumentException("Некорректная строка в input.txt: " + line + " - не все необходимые данные введены");
            String FIO = parts[0].trim();
            int course = Integer.parseInt(parts[1].trim());
            int groupNum = Integer.parseInt(parts[2].trim());
            int sessionNum = Integer.parseInt(parts[3].trim());
            String subject = parts[4].trim();
            int grade = Integer.parseInt(parts[5].trim());
            //определние группы считанного студента
            Group g = null;
            for (Group gr : groups) {
                if (gr.getGroupNumber() == groupNum && gr.getCourse() == course) {
                    g = gr;
                    break;
                }
            }
            if (g == null) {
                g = new Group(course, groupNum);
                groups.add(g);
            }
            // определение студента
            Student st = null;
            for (Student s : g.getStudents()) {
                if (s.getFIO().equals(FIO)) {
                    st = s;
                    break;
                }
            }
            if (st == null) {
                st = new Student(FIO, course, groupNum);
                g.addStud(st);
            }
            //определение сессии
            Student.Session se = null;
            for (Student.Session s : st.sessions) {
                if (s.getSessionNumber() == sessionNum) {
                    se = s;
                    break;
                }
            }
            if (se == null) {
                se = st.new Session(sessionNum);
                st.sessions.add(se);
            }
            se.addExam(subject, grade);
        }
        in.close();
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), StandardCharsets.UTF_8))) {
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            for (Group g : groups) {
                out.println("Курс " + g.getCourse() + ", группа №" + g.getGroupNumber());
                HashSet<Integer> sessionNumbers = new HashSet<>();
                for (Student st : g.getStudents()) {
                    for (Student.Session s : st.sessions) {
                        sessionNumbers.add(s.getSessionNumber());
                    }
                }
                for (int sn : sessionNumbers) {
                    out.println("Сессия №" + sn);
                    out.println("Средний балл по группе: " + formatter.format(g.groupAverageGrade(sn)));
                    out.println("Кол-во отличников: " + g.countExcellent());
                    out.println("Кол-во студентов с оценкой '4': " + g.countStudentsWithFour());
                    for (Student current : g.getStudents()) {
                        out.println();
                        out.println(current.getFIO() + ", курс " + current.getCourse() + ", группа " + current.getGroupNumber() + ", средний балл: " + formatter.format(current.averageGrade()));
                        for (Student.Session s : current.sessions) {
                            if (s.getSessionNumber() == sn) {
                                for (HashMap.Entry<String, Integer> entry : s.getExams().entrySet()) {
                                    out.println(entry.getKey() + " - " + entry.getValue());
                                }
                            }
                        }
                    }
                }
                out.println();
                out.println("---------------------------------------------------");
                out.println();
            }
            out.close();
        }
    }
}
