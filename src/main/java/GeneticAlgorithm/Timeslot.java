package GeneticAlgorithm;

public class Timeslot {
    private final int timeslotId;
    private final DayAndHouDetails dayAndHourDetails;

    private final int moduleId;

    public int getModuleId() {
        return moduleId;
    }

    public int getProfessorId() {
        return professorId;
    }

    private final int professorId;
    private final int maxGroupSize;

    public int getMaxGroupSize() {
        return maxGroupSize;
    }


    /**
     * Initalize new Timeslot
     *
     * @param timeslotId The ID for this timeslot
     * @param dayAndHourDetails The timeslot being initalized
     */
    public Timeslot(int timeslotId, DayAndHouDetails dayAndHourDetails, int moduleId, int professorId, int groupSize){
        this.timeslotId = timeslotId;
        this.dayAndHourDetails = dayAndHourDetails;
        this.moduleId = moduleId;
        this.professorId = professorId;
        this.maxGroupSize = groupSize;
    }

    /**
     * Returns the timeslotId
     *
     * @return timeslotId
     */
    public int getTimeslotId(){
        return this.timeslotId;
    }

    /**
     * Returns the timeslot
     *
     * @return timeslot
     */
    public DayAndHouDetails getDayAndHourDetails(){
        return this.dayAndHourDetails;
    }
}
