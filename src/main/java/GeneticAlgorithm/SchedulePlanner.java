package GeneticAlgorithm;

import DBHandler.DBHandler;
import DBHandler.DbClasses;
import Model.Class_obj;
import Model.Professor;

import java.sql.SQLException;
import java.util.ArrayList;

public class SchedulePlanner {

    public SchedulePlanner() throws SQLException {
        DBHandler dbHandler = new DBHandler();
        ArrayList<Class_obj> classes = dbHandler.getClasses(1);
        ArrayList<Professor> professors = dbHandler.getProfessors();
    }
}
