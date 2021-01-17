package GeneticAlgorithm;

public class Professor {
    private final int professorId;
    private final String professorName;
    private final String surname;
    private final String title;

    /**
     * Initalize new Professor
     *
     * @param professorId The ID for this professor
     * @param professorName The name of this professor
     */
    public Professor(int professorId, String professorName, String surname, String title){
        this.professorId = professorId;
        this.professorName = professorName;
        this.surname = surname;
        this.title = title;
    }

    /**
     * Get professorId
     *
     * @return professorId
     */
    public int getProfessorId(){
        return this.professorId;
    }

    /**
     * Get professor's name
     *
     * @return professorName
     */
    public String getProfessorName(){
        return this.professorName;
    }
}
