import java.util.ArrayList;
public class Group {
    private int course;
    private int groupNumber;
    private ArrayList<Student> students = new ArrayList<Student>();
    public Group()
    {
        this.course = 0;
        this.groupNumber = 0;
    }
    public Group (int course_, int group_)
    {
        assert course_ >= 1 && course_ <= 4 : "Курс может принимать значения от 1 до 4";
        assert group_ >= 1 && group_ <= 14 : "Группа может иметь номер от 1 до 14";
        this.course=course_;
        this.groupNumber=group_;
    }
    public void addStud (Student temp)
    {
        if (temp.getGroupNumber()!=groupNumber || temp.getCourse()!=course)
            throw new IllegalStateException ("Ошибка: студент " + temp.getFIO() + "не относится к группе №" + groupNumber + " " + course + " курса");
        students.add(temp);
    }
    public double groupAverageGrade(int sNumber)
    {
        double sum = 0.0;
        int count = 0;
        for (Student current : students) {
            for (Student.Session currentSession : current.sessions) {
                if (currentSession.getSessionNumber() == sNumber) {
                    count += currentSession.getExams().size();
                    for (int grade : currentSession.getExams().values())
                        sum += grade;
                }
            }
        }
        if (count == 0) {
            throw new IllegalStateException("Группа № " + groupNumber + " " + course + " курса ещё не сдавала сессию № " + sNumber);
        }
        return sum / count;
    }

   public int countExcellent ()
    {
        int count=0;
        for (Student current: students)
        {
            if (current.isExcellentStudent())
                count++;
        }
        return count;
    }

    public int countStudentsWithFour ()
    {
        int count=0;
        for (Student current: students)
        {
            if (current.hasFour())
                count++;
        }
        return count;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public int getCourse() {
        return course;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}