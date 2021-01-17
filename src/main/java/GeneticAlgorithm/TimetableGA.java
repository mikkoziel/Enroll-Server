package GeneticAlgorithm;

import DBHandler.DBHandler;
import Model.Class_obj;
import Model.Group;
import Model.Professor;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimetableGA {

    public static void main(String[] args) throws SQLException {


        // Get a Timetable object with all the available information.
        Timetable timetable = initializeTimetable();

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
            System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

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

//        for (ResultClass bestClass : resultClasses) {
//            System.out.println("Class " + classIndex + ":");
//            System.out.println("Module: " +
//                    timetable.getModule(bestClass.getModuleId()).getModuleName());
//            System.out.println("Group: " +
//                    timetable.getGroup(bestClass.getGroupId()).getStudentId());
//            System.out.println("Room: " +
//                    timetable.getRoom(bestClass.getRoomId()).getRoomNumber());
////            System.out.println("Professor: " +
////                    timetable.getProfessor(bestClass.getProfessorId()).getProfessorName());
//            System.out.println("Time: " +
//                    timetable.getTimeslot(bestClass.getTimeslotId()).getDayAndHourDetails().getDay() + " " +
//                    timetable.getTimeslot(bestClass.getTimeslotId()).getDayAndHourDetails().getStartHour() + " " +
//                    timetable.getTimeslot(bestClass.getTimeslotId()).getDayAndHourDetails().getEndHour()
//            );
//            System.out.println("-----");
//            classIndex++;
//        }


    }

    /**
     * Creates a Timetable with all the necessary course information.
     *
     * Normally you'd get this info from a database.
     *
     * @return
     */
    private static Timetable initializeTimetable()  throws SQLException {
        // Create timetable
        Timetable timetable = new Timetable();

        // Set up rooms
        timetable.addRoom(1, "A1", 20);
//        timetable.addRoom(2, "B1", 30);
//        timetable.addRoom(3, "C1", 15);

        // Set up professors
        DBHandler dbHandler = new DBHandler();
        ArrayList<Professor> professors = dbHandler.getProfessors();
        for(Professor p: professors){
            System.out.println(p);
            timetable.addProfessor(p.getProfessor_id(), p.getName(), p.getSurname(), p.getTitle());
        }
        //timetable.addProfessor(1, "Dariusz", "Jamróz", "dr");
//        timetable.addProfessor(2, "Grzegorz","Rogus", "dr");
//        timetable.addProfessor(3, "Konrad" ,"Kułakowski", "dr");
//        timetable.addProfessor(4, "Radosław", "Klimek", "dr");
//        timetable.addProfessor(5, "Paweł", "Skrzyński", "dr");
//        timetable.addProfessor(6, "Sebastian", "Ernst", "dr");
//        timetable.addProfessor(7, "Igor", "Wojnicki", "dr");

        // Set up modules and define the professors that teach them
        ArrayList<Class_obj> classes = dbHandler.getClasses(1);
        for(Class_obj class_obj: classes){
            timetable.addModule(class_obj.getClassId(), class_obj.getName(), class_obj.getFull_name());
        }
        /*
        timetable.addModule(1, "cs1", "Studio Projektowe 2");//, new int[] { 3,4 });
        timetable.addModule(2, "en1", "Testowanie oprogramowania");//, new int[] { 2 });
        timetable.addModule(3, "ma1", "Zaawansowane technologie bazodanowe");//, new int[] { 6,7 });
        timetable.addModule(4, "ph1", "Zaawansowane programowanie webowe");//, new int[] { 2 });
        timetable.addModule(5, "hi1", "Zaawansowane systemy mobilne");//, new int[] { 5 });
        timetable.addModule(6, "hi1", "Zaawansowane algorytmy i struktury danych");//, new int[] { 1 });
        */

        // dodanie do User modules czyli liczbę przedmiotów jakie bierze
        // albo iteracja po classes dla danego semestru
        timetable.addStudent(1, new int[] { 6,7,2,1,5 }); // zamiast 4 --> 1, zamiast 1 --> 6
        timetable.addStudent(2, new int[] { 6,7,2,1,5 });
        timetable.addStudent(3, new int[] { 6,7,2,1,5 });
        timetable.addStudent(4, new int[] { 6,7,2,1,5 });
        timetable.addStudent(5, new int[] { 6,7,2,1,5 });
//        timetable.addStudent(6, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(7, new int[] { 1,2,3,4,5 });
//
//        timetable.addStudent(8, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(9, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(10, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(11, new int[] { 1,2,3,4,5 });
//
//        timetable.addStudent(12, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(13, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(14, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(15, new int[] { 1,2,3,4,5 });
//
//        timetable.addStudent(16, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(17, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(18, new int[] { 1,2,3,4,5 });
//        timetable.addStudent(19, new int[] { 1,2,3,4,5 });
//
//        timetable.addStudent(20, new int[] { 1,2,3,4,5 });

        // Set up timeslots
        /*
            Timesloty moga byc nastepujace:
            8-9:30
            9:30-11
            11-12:30
            12:30-14
            14-15:30
            15:30-17
            17-18:30
            18:30-20
         */

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

        /*
        // Testowanie oprogramowania
        timetable.addTimeslot(1, new DayAndHouDetails(2, LocalTime.of(11,00), LocalTime.of(12,30)), 7, 2, 4); // 10
        timetable.addTimeslot(2, new DayAndHouDetails(2, LocalTime.of(12,30), LocalTime.of(14,00)), 7, 2, 4); // 10 dla 20 studentow
        // Zaawansowane technologie bazodanowe
        // DO PRZYWROCENIA
        //timetable.addTimeslot(3, "Piątek 8:00 - 9:30", 3, 6, 2);
        // wprowadzic obluge zajec co 2 tydzien albo ten sam termin, inny wykladowca
        //timetable.addTimeslot(4, "Piątek 8:00 - 9:30", 3, 7, 2);
        // wartosc do usuniecia
        timetable.addTimeslot(3, new DayAndHouDetails(5, LocalTime.of(11,00), LocalTime.of(12,30)), 2, 7, 4);
        timetable.addTimeslot(4, new DayAndHouDetails(5, LocalTime.of(9,30), LocalTime.of(11,00)), 2,6, 4);
        // Zaawansowane programowanie webowe
        timetable.addTimeslot(5, new DayAndHouDetails(1, LocalTime.of(18,30), LocalTime.of(20,00)), 1, 2, 4);
        timetable.addTimeslot(6, new DayAndHouDetails(5, LocalTime.of(11,00), LocalTime.of(12,30)), 1, 2, 4);
        // Zaawansowane systemy mobilne
        timetable.addTimeslot(7, new DayAndHouDetails(5, LocalTime.of(8,00), LocalTime.of(9,30)), 5, 6, 4);
        timetable.addTimeslot(8, new DayAndHouDetails(5, LocalTime.of(9,30), LocalTime.of(11,00)), 5, 7, 4);
        // DO PRZYWROCENIA
        //timetable.addTimeslot(10, "Piątek 11:00 - 12:30", 5,6, 2);
        // Studio projektowe
        timetable.addTimeslot(9, new DayAndHouDetails(3, LocalTime.of(9,30), LocalTime.of(11,00)), 6, 7, 4);
        timetable.addTimeslot(10, new DayAndHouDetails(1, LocalTime.of(11,00), LocalTime.of(12,30)), 6,6, 4);
        // Zaawansowane algorytmy i struktury danych - tego jeszcze nie uwzgledniam w planie
        timetable.addTimeslot(11, new DayAndHouDetails(5, LocalTime.of(15,30), LocalTime.of(17,00)), 7, 1, 4);
        timetable.addTimeslot(12, new DayAndHouDetails(5, LocalTime.of(17,00), LocalTime.of(18,30)), 7, 1, 4);
        // wartosc do usuniecia
        //timetable.addTimeslot(15, "Piątek 18:30 - 19:00", 6,1, 2);
        */

        timetable.addPreference(1, 1, 2, 7); // do preferencji dodac modulId?
//        timetable.addPreference(2, 2, 2, 2); // do preferencji dodac modulId?
//        timetable.addPreference(3, 3, 2, 2); // do preferencji dodac modulId?
//        timetable.addPreference(4, 4, 2, 2); // do preferencji dodac modulId?
//        timetable.addPreference(5, 5, 2, 2); // do preferencji dodac modulId?
//        timetable.addPreference(6, 6, 2, 2); // do preferencji dodac modulId?
        // czyli grupa 1 ma TO we wtorek 13:00 - 14:30
        return timetable;
    }
}
