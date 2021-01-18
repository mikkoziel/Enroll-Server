package GeneticAlgorithm;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Get a Timetable object with all the available information.
        TimetableGA timetableGA = new TimetableGA();
        Timetable timetable = timetableGA.initializeTimetable();
        timetableGA.generateSchedules(timetable);

    }
}
