package DBHandler;

import Model.Professor;

import java.sql.*;
import java.util.ArrayList;

public class DbProfessors {
    Connection conn;

    public DbProfessors(Connection conn){
        this.conn = conn;
    }

    public ArrayList<Professor> getProfessors() throws SQLException {
        String SQL_SELECT = "SELECT * FROM Professor";
        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        ResultSet result = statement.executeQuery();
        ArrayList<Professor> professors = new ArrayList<>();

        while (result.next()) {
            professors.add(new Professor(
                    result.getInt("professor_id"),
                    result.getString("name"),
                    result.getString("surname")
            ));
        }
        return professors;
    }

    public int addProfessor(Professor prof) throws SQLException{
        String SQL_INSERT = "INSERT INTO Professor(name, surname)" +
                " VALUES (?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, prof.getName());
        statement.setString(2, prof.getSurname());

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating schedule failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (int) generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating schedule failed, no ID obtained.");
            }
        }
    }

}
