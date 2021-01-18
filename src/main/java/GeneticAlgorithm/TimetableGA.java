package GeneticAlgorithm;

import DBHandler.DBHandler;
import Model.Class_obj;
import Model.Group;
import Model.Professor;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimetableGA {
    public TimetableGA() {
    }

    public ResultClass[] generateSchedules(Timetable timetable){
        // Initialize GA
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.9, 2, 5);

        // Initialize population
        Population population = ga.initPopulation(timetable);

        // Evaluate population
        ga.evalPopulation(population, timetable);

        // Keep track of current generation
        int generation = 1;

        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, 1000) == false
                && ga.isTerminationConditionMet(population) == false) {
            // Print fitness
            // System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population, timetable);

            // Evaluate population
            ga.evalPopulation(population, timetable);

            // Increment the current generation
            generation++;
        }

        // Print fitness
        Individual fittest = population.getFittest(0);
        timetable.createClasses(population.getFittest(0));
        System.out.println();
        System.out.println("Solution found in " + generation + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).getFitness());
        System.out.println("Clashes: " + timetable.calcClashes());

        // Print classes
        System.out.println();
        ResultClass resultClasses[] = timetable.getClasses();
        int classIndex = 1;

        for (ResultClass bestClass : resultClasses) {
            System.out.println("Class " + classIndex + ":");
            System.out.println("Module: " +
                    timetable.getModule(bestClass.getModuleId()).getModuleName());
            System.out.println("Group: " +
                    timetable.getGroup(bestClass.getGroupId()).getStudentId());
            System.out.println("Room: " +
                    timetable.getRoom(bestClass.getRoomId()).getRoomNumber());
//            System.out.println("Professor: " +
//                    timetable.getProfessor(bestClass.getProfessorId()).getProfessorName());
            System.out.println("Time: " +
                    timetable.getTimeslot(bestClass.getTimeslotId()).getDayAndHourDetails().getDay() + " " +
                    timetable.getTimeslot(bestClass.getTimeslotId()).getDayAndHourDetails().getStartHour() + " " +
                    timetable.getTimeslot(bestClass.getTimeslotId()).getDayAndHourDetails().getEndHour()
            );
            System.out.println("-----");
            classIndex++;
        }
        return resultClasses;
    }


    Timetable initializeTimetable()  throws SQLException {
        // Create timetable
        Timetable timetable = new Timetable();

        // Set up rooms
        timetable.addRoom(1, "A1", 20);

        // Set up professors
        DBHandler dbHandler = new DBHandler();
        ArrayList<Professor> professors = dbHandler.getProfessors();
        for(Professor p: professors){
            System.out.println(p);
            timetable.addProfessor(p.getProfessor_id(), p.getName(), p.getSurname(), p.getTitle());
        }

        // Set up modules and define the professors that teach them
        ArrayList<Class_obj> classes = dbHandler.getClasses(1);
        for(Class_obj class_obj: classes){
            timetable.addModule(class_obj.getClassId(), class_obj.getName(), class_obj.getFull_name());
        }

        timetable.addStudent(1, new int[] { 6,7,2,1,5 });
        timetable.addStudent(2, new int[] { 6,7,2,1,5 });
        timetable.addStudent(3, new int[] { 6,7,2,1,5 });
        timetable.addStudent(4, new int[] { 6,7,2,1,5 });
        timetable.addStudent(5, new int[] { 6,7,2,1,5 });

        int index = 1;
        for(Class_obj class_obj: classes) {
            ArrayList<Group> groups = dbHandler.getGroups(class_obj.getClassId()); // trzeba do wszystkich class id
            for (Group group : groups) {
                if(group.getType().equals("LABORATORY")){
                    System.out.println(group);
                    timetable.addTimeslot(index, new DayAndHouDetails(group.getDay(), group.getStart(), group.getEnd()), class_obj.getClassId() ,group.getProfessor_id(),4 );
                    index++;
                }

            }
        }

        timetable.addPreference(1, 1, 2, 7); // do preferencji dodac modulId?
        return timetable;
    }
}
