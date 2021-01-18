package GeneticAlgorithm;

public class Preference {
    private final int preferenceId;
    private final int studentId;
    private final int timeslotId;
    private final int moduleId;

    public int getPreferenceId() {
        return preferenceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getTimeslotId() {
        return timeslotId;
    }

    public Preference(int preferenceId, int studentId, int timeslotId, int moduleId){
        this.preferenceId = preferenceId;
        this.studentId = studentId;
        this.timeslotId = timeslotId;
        this.moduleId = moduleId;
    }
}
