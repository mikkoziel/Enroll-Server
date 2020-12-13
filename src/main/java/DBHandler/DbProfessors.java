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

}
