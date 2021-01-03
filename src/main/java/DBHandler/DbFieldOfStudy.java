package DBHandler;

import Model.FieldOfStudy;

import java.sql.*;
import java.util.ArrayList;

public class DbFieldOfStudy {
    Connection conn;

    public DbFieldOfStudy(Connection conn){
        this.conn = conn;
    }

    public ArrayList<FieldOfStudy> getFieldsForId(int user_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM FieldOfStudy WHERE field_id IN " +
                "(SELECT field_id FROM UserField WHERE user_id=?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT);

        statement.setInt(1, user_id);

        ResultSet result = statement.executeQuery();
        ArrayList<FieldOfStudy> fields = new ArrayList<>();

        while (result.next()) {
            fields.add(new FieldOfStudy(
                    result.getInt("field_id"),
                    result.getString("name"),
                    result.getString("short_name"),
                    result.getString("start_year")
            ));
        }
        return fields;
    }
}
