package GeneticAlgorithm;

public class Student {
    private final int studentId;
    private final int moduleIds[];

    public Student(int studentId, int moduleIds[]){
        this.studentId = studentId;
        this.moduleIds = moduleIds;
    }

    public int getStudentId(){
        return this.studentId;
    }

    public int[] getModuleIds(){
        return this.moduleIds;
    }
}
