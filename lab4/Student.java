import java.util.ArrayList;
import java.util.HashMap;
public class Student
{
    class Session
    {
        private HashMap<String, Integer> exams = new HashMap<String, Integer>();
        private int sessionNumber;
        public Session(){
            this.sessionNumber=0;
        }
        public Session(int number){
            this.sessionNumber=number;
        }
        public void addExam(String exam, int grade) {
            if (grade < 4 || grade > 10) {
                throw new IllegalArgumentException("Удовлетворительная оценка должна находиться в диапазоне 4–10, введена: " + grade);
            }
            exams.put(exam, grade);
        }
        public int getSessionNumber()
        {
            return sessionNumber;
        }
        public HashMap<String, Integer> getExams()
        {
            return exams;
        }
    };
    ArrayList<Session> sessions = new ArrayList<Session>();
    private String studFIO;
    private int course;
    private int group;
    public Student ()
    {
        this.studFIO="";
        this.course=0;
        this.group=0;
    }
    public Student (String studFIO_, int course_, int group_)
    {
        assert course_ >= 1 && course_ <= 4 : "Курс может принимать значения от 1 до 4";
        assert group_ >= 1 && group_ <= 14 : "Группа может иметь номер от 1 до 14";
        this.studFIO=studFIO_;
        this.course=course_;
        this.group=group_;
    }
    boolean isExcellentStudent ()
    {
        for (Session currentSession: sessions)
        {
            for (int grade: currentSession.exams.values())
                if (grade<9)
                    return false;
        }
        return true;
    }
    boolean hasFour ()
    {
        for (Session currentSession: sessions)
        {
            for (int grade: currentSession.exams.values())
                if (grade==4)
                    return true;
        }
        return false;
    }
    double averageGrade ()
    {
        if (sessions.size()==0)
            throw new IllegalStateException ("Студент " + studFIO + " ещё не сдал ни одного экзамена");
        double sum=0.0;
        int сount=0;
        for (Session currentSession: sessions)
        {
            сount+=currentSession.exams.size();
            for (int grade: currentSession.exams.values())
                sum+=grade;
        }
        return sum/сount;
    }
    public int getGroupNumber()
    {
        return group;
    }
    public String getFIO()
    {
        return studFIO;
    }

    public int getCourse() {
        return course;
    }
}