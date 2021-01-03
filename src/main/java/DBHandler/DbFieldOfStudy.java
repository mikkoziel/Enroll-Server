package DBHandler;

import Model.FieldOfStudy;
import Model.Group;

import java.lang.reflect.Field;
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
                    result.getString("start_year"),
                    result.getInt("cycle")
            ));
        }
        return fields;
    }

    public FieldOfStudy getField(int field_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM FieldOfStudy WHERE field_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT);

        statement.setInt(1, field_id);

        ResultSet result = statement.executeQuery();
        FieldOfStudy field= null;

        while (result.next()) {
            field = new FieldOfStudy(
                    result.getInt("field_id"),
                    result.getString("name"),
                    result.getString("short_name"),
                    result.getString("start_year"),
                    result.getInt("cycle")
            );
        }
        return field;
    }

    public int addFoS(FieldOfStudy fos) throws SQLException {
        String SQL_INSERT = "INSERT INTO FieldOfStudy(name, short_name, start_year, cycle)" +
                " VALUES (?, ?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, fos.getName());
        statement.setString(2, fos.getShort_name());
        statement.setString(3, fos.getStart_year());
        statement.setInt(4, fos.getCycle());

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating FieldOfStudy failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating FieldOfStudy failed, no ID obtained.");
            }
        }
    }

    public int updateFoS(FieldOfStudy fos) throws SQLException {
        String SQL_UPDATE = "UPDATE FieldOfStudy " +
                "SET name=?, short_name=?, start_year=?, cycle=? " +
                "WHERE field_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setString(1, fos.getName());
        statement.setString(2, fos.getShort_name());
        statement.setString(3, fos.getStart_year());
        statement.setInt(4, fos.getCycle());
        statement.setInt(5, fos.getField_id());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }

    public int deleteFoS(int field_id) throws SQLException {
        String SQL_DELETE = "DELETE FROM FieldOfStudy WHERE field_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_DELETE);

        statement.setInt(1, field_id);

        int i = statement.executeUpdate();
        System.out.println(i+ " records deleted");
        return i;
    }
}
