import static org.junit.jupiter.api.Assertions.assertEquals;

import DBHandler.DBHandler;
import GeneticAlgorithm.DayAndHouDetails;
import GeneticAlgorithm.ResultClass;
import GeneticAlgorithm.Timetable;
import GeneticAlgorithm.TimetableGA;
import Model.Class_obj;
import Model.Group;
import Model.Professor;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;

class GeneticAlgorithmTest {

    @Test
    public void testTimeClashes() {
        TimetableGA tester = new TimetableGA();
        Timetable timetable = new Timetable();

        timetable.addRoom(1, "A1", 20);

        // Set up professors
        timetable.addProfessor(1, "Dariusz", "Jamróz", "dr");
        timetable.addProfessor(2, "Grzegorz","Rogus", "dr");
        timetable.addProfessor(3, "Konrad" ,"Kułakowski", "dr");
        timetable.addProfessor(4, "Radosław", "Klimek", "dr");
        timetable.addProfessor(5, "Paweł", "Skrzyński", "dr");
        timetable.addProfessor(6, "Sebastian", "Ernst", "dr");
        timetable.addProfessor(7, "Igor", "Wojnicki", "dr");

        // Set up modules and define the professors that teach them
        timetable.addModule(6, "cs1", "Studio Projektowe 2");//, new int[] { 3,4 });
        timetable.addModule(7, "en1", "Testowanie oprogramowania");//, new int[] { 2 });
        timetable.addModule(2, "ma1", "Zaawansowane technologie bazodanowe");//, new int[] { 6,7 });
        timetable.addModule(1, "ph1", "Zaawansowane programowanie webowe");//, new int[] { 2 });
        timetable.addModule(5, "hi1", "Zaawansowane systemy mobilne");//, new int[] { 5 });
        timetable.addModule(3, "hi1", "Zaawansowane algorytmy i struktury danych");//, new int[] { 1 });


        timetable.addStudent(1, new int[] { 6,7,2,1,5 });
        //timetable.addStudent(2, new int[] { 6,7,2,1,5 });

        // Testowanie oprogramowania
        timetable.addTimeslot(1, new DayAndHouDetails(2, LocalTime.of(11,00), LocalTime.of(12,30)), 7, 2, 1); // 10
        timetable.addTimeslot(2, new DayAndHouDetails(2, LocalTime.of(12,30), LocalTime.of(14,00)), 7, 2, 1); // 10 dla 20 studentow
        // Zaawansowane technologie bazodanowe
        timetable.addTimeslot(3, new DayAndHouDetails(5, LocalTime.of(11,00), LocalTime.of(12,30)), 2, 7, 1);
        timetable.addTimeslot(4, new DayAndHouDetails(5, LocalTime.of(9,30), LocalTime.of(11,00)), 2,6, 1);
        // Zaawansowane programowanie webowe
        timetable.addTimeslot(5, new DayAndHouDetails(1, LocalTime.of(18,30), LocalTime.of(20,00)), 1, 2, 1);
        timetable.addTimeslot(6, new DayAndHouDetails(5, LocalTime.of(11,00), LocalTime.of(12,30)), 1, 2, 1);
        // Zaawansowane systemy mobilne
        timetable.addTimeslot(7, new DayAndHouDetails(5, LocalTime.of(8,00), LocalTime.of(9,30)), 5, 6, 1);
        timetable.addTimeslot(8, new DayAndHouDetails(5, LocalTime.of(9,30), LocalTime.of(11,00)), 5, 7, 1);
        // Studio projektowe
        timetable.addTimeslot(9, new DayAndHouDetails(3, LocalTime.of(9,30), LocalTime.of(11,00)), 6, 7, 1);
        timetable.addTimeslot(10, new DayAndHouDetails(1, LocalTime.of(11,00), LocalTime.of(12,30)), 6,6, 1);
        // Zaawansowane algorytmy i struktury danych - tego jeszcze nie uwzgledniam w planie
        timetable.addTimeslot(11, new DayAndHouDetails(5, LocalTime.of(15,30), LocalTime.of(17,00)), 7, 1, 1);
        timetable.addTimeslot(12, new DayAndHouDetails(5, LocalTime.of(17,00), LocalTime.of(18,30)), 7, 1, 1);

        tester.generateSchedules(timetable);

        // assert statements
//        assertEquals(0, tester.multiply(10, 0), "10 x 0 must be 0");
//        assertEquals(0, tester.multiply(0, 10), "0 x 10 must be 0");
//        assertEquals(0, tester.multiply(0, 0), "0 x 0 must be 0");
    }

    @Test
    public void testPreferences() {
        TimetableGA tester = new TimetableGA();
        Timetable timetable = new Timetable();

        timetable.addRoom(1, "A1", 20);

        // Set up professors
        timetable.addProfessor(1, "Dariusz", "Jamróz", "dr");
        timetable.addProfessor(2, "Grzegorz","Rogus", "dr");
        timetable.addProfessor(3, "Konrad" ,"Kułakowski", "dr");
        timetable.addProfessor(4, "Radosław", "Klimek", "dr");
        timetable.addProfessor(5, "Paweł", "Skrzyński", "dr");
        timetable.addProfessor(6, "Sebastian", "Ernst", "dr");
        timetable.addProfessor(7, "Igor", "Wojnicki", "dr");

        // Set up modules and define the professors that teach them
        timetable.addModule(6, "cs1", "Studio Projektowe 2");//, new int[] { 3,4 });
        timetable.addModule(7, "en1", "Testowanie oprogramowania");//, new int[] { 2 });
        timetable.addModule(2, "ma1", "Zaawansowane technologie bazodanowe");//, new int[] { 6,7 });
        timetable.addModule(1, "ph1", "Zaawansowane programowanie webowe");//, new int[] { 2 });
        timetable.addModule(5, "hi1", "Zaawansowane systemy mobilne");//, new int[] { 5 });
        timetable.addModule(3, "hi1", "Zaawansowane algorytmy i struktury danych");//, new int[] { 1 });


        timetable.addStudent(1, new int[] { 6,7,2,1,5 });
        //timetable.addStudent(2, new int[] { 6,7,2,1,5 });

        // Testowanie oprogramowania
        timetable.addTimeslot(1, new DayAndHouDetails(2, LocalTime.of(11,00), LocalTime.of(12,30)), 7, 2, 1); // 10
        timetable.addTimeslot(2, new DayAndHouDetails(2, LocalTime.of(12,30), LocalTime.of(14,00)), 7, 2, 1); // 10 dla 20 studentow
        // Zaawansowane technologie bazodanowe
        timetable.addTimeslot(3, new DayAndHouDetails(5, LocalTime.of(11,00), LocalTime.of(12,30)), 2, 7, 1);
        timetable.addTimeslot(4, new DayAndHouDetails(5, LocalTime.of(9,30), LocalTime.of(11,00)), 2,6, 1);
        // Zaawansowane programowanie webowe
        timetable.addTimeslot(5, new DayAndHouDetails(1, LocalTime.of(18,30), LocalTime.of(20,00)), 1, 2, 1);
        timetable.addTimeslot(6, new DayAndHouDetails(5, LocalTime.of(11,00), LocalTime.of(12,30)), 1, 2, 1);
        // Zaawansowane systemy mobilne
        timetable.addTimeslot(7, new DayAndHouDetails(5, LocalTime.of(8,00), LocalTime.of(9,30)), 5, 6, 1);
        timetable.addTimeslot(8, new DayAndHouDetails(5, LocalTime.of(9,30), LocalTime.of(11,00)), 5, 7, 1);
        // Studio projektowe
        timetable.addTimeslot(9, new DayAndHouDetails(3, LocalTime.of(9,30), LocalTime.of(11,00)), 6, 7, 1);
        timetable.addTimeslot(10, new DayAndHouDetails(1, LocalTime.of(11,00), LocalTime.of(12,30)), 6,6, 1);
        // Zaawansowane algorytmy i struktury danych - tego jeszcze nie uwzgledniam w planie
        timetable.addTimeslot(11, new DayAndHouDetails(5, LocalTime.of(15,30), LocalTime.of(17,00)), 7, 1, 1);
        timetable.addTimeslot(12, new DayAndHouDetails(5, LocalTime.of(17,00), LocalTime.of(18,30)), 7, 1, 1);

        timetable.addPreference(1, 1, 2, 7);

        tester.generateSchedules(timetable);

        // assert statements
//        assertEquals(0, tester.multiply(10, 0), "10 x 0 must be 0");
//        assertEquals(0, tester.multiply(0, 10), "0 x 10 must be 0");
//        assertEquals(0, tester.multiply(0, 0), "0 x 0 must be 0");
    }


    @Test
    public void testForMultipleStudents() {
        TimetableGA tester = new TimetableGA(); // MyClass is tested
        Timetable timetable = new Timetable();

        // Set up rooms
        timetable.addRoom(1, "A1", 20);

        // Set up professors
        timetable.addProfessor(1, "Dariusz", "Jamróz", "dr");
        timetable.addProfessor(2, "Grzegorz","Rogus", "dr");
        timetable.addProfessor(3, "Konrad" ,"Kułakowski", "dr");
        timetable.addProfessor(4, "Radosław", "Klimek", "dr");
        timetable.addProfessor(5, "Paweł", "Skrzyński", "dr");
        timetable.addProfessor(6, "Sebastian", "Ernst", "dr");
        timetable.addProfessor(7, "Igor", "Wojnicki", "dr");

        // Set up modules and define the professors that teach them
        timetable.addModule(6, "cs1", "Studio Projektowe 2");//, new int[] { 3,4 });
        timetable.addModule(7, "en1", "Testowanie oprogramowania");//, new int[] { 2 });
        timetable.addModule(2, "ma1", "Zaawansowane technologie bazodanowe");//, new int[] { 6,7 });
        timetable.addModule(1, "ph1", "Zaawansowane programowanie webowe");//, new int[] { 2 });
        timetable.addModule(5, "hi1", "Zaawansowane systemy mobilne");//, new int[] { 5 });
        timetable.addModule(3, "hi1", "Zaawansowane algorytmy i struktury danych");//, new int[] { 1 });

        timetable.addStudent(1, new int[] { 6,7,2,1,5 });
        timetable.addStudent(2, new int[] { 6,7,2,1,5 });
        timetable.addStudent(3, new int[] { 6,7,2,1,5 });
        timetable.addStudent(4, new int[] { 6,7,2,1,5 });
        timetable.addStudent(5, new int[] { 6,7,2,1,5 });

        // Testowanie oprogramowania
        timetable.addTimeslot(1, new DayAndHouDetails(2, LocalTime.of(11,00), LocalTime.of(12,30)), 7, 2, 4); // 10
        timetable.addTimeslot(2, new DayAndHouDetails(2, LocalTime.of(12,30), LocalTime.of(14,00)), 7, 2, 4); // 10 dla 20 studentow
        // Zaawansowane technologie bazodanowe
        timetable.addTimeslot(3, new DayAndHouDetails(5, LocalTime.of(11,00), LocalTime.of(12,30)), 2, 7, 4);
        timetable.addTimeslot(4, new DayAndHouDetails(5, LocalTime.of(9,30), LocalTime.of(11,00)), 2,6, 4);
        // Zaawansowane programowanie webowe
        timetable.addTimeslot(5, new DayAndHouDetails(1, LocalTime.of(18,30), LocalTime.of(20,00)), 1, 2, 4);
        timetable.addTimeslot(6, new DayAndHouDetails(5, LocalTime.of(11,00), LocalTime.of(12,30)), 1, 2, 4);
        // Zaawansowane systemy mobilne
        timetable.addTimeslot(7, new DayAndHouDetails(5, LocalTime.of(8,00), LocalTime.of(9,30)), 5, 6, 4);
        timetable.addTimeslot(8, new DayAndHouDetails(5, LocalTime.of(9,30), LocalTime.of(11,00)), 5, 7, 4);
        // Studio projektowe
        timetable.addTimeslot(9, new DayAndHouDetails(3, LocalTime.of(9,30), LocalTime.of(11,00)), 6, 7, 4);
        timetable.addTimeslot(10, new DayAndHouDetails(1, LocalTime.of(11,00), LocalTime.of(12,30)), 6,6, 4);
        // Zaawansowane algorytmy i struktury danych - tego jeszcze nie uwzgledniam w planie
        timetable.addTimeslot(11, new DayAndHouDetails(5, LocalTime.of(15,30), LocalTime.of(17,00)), 7, 1, 4);
        timetable.addTimeslot(12, new DayAndHouDetails(5, LocalTime.of(17,00), LocalTime.of(18,30)), 7, 1, 4);


        timetable.addPreference(1, 1, 2, 7); // do preferencji dodac modulId?

        timetable.addPreference(1, 1, 2, 7);
        tester.generateSchedules(timetable);

    }
}
