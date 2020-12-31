package DBHandler;

import Model.Class_obj;

import java.sql.*;
import java.util.ArrayList;

public class DbClasses {
    Connection conn;
    DbGroups dbGroups;

    public DbClasses(Connection conn, DbGroups dbGroups){
        this.conn = conn;
        this.dbGroups = dbGroups;
    }

    public ArrayList<Class_obj> getClasses(int schedule_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Class WHERE schedule_id =? ";

        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, schedule_id);

        ResultSet result = statement.executeQuery();
        ArrayList<Class_obj> classes = new ArrayList<>();

        while (result.next()) {
            int class_id = result.getInt("class_id");
            Class_obj tmp = new Class_obj(
                    class_id,
                    result.getString("name"),
                    result.getString("full_name")
            );
            tmp.setGroups(this.dbGroups.getGroups(class_id));
            classes.add(tmp);
        }
        return classes;
    }

    public Class_obj getClass(int class_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Class WHERE class_id =? ";

        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, class_id);

        ResultSet result = statement.executeQuery();
        Class_obj cl = null;

        while (result.next()) {
            cl = new Class_obj(
                    result.getInt("class_id"),
                    result.getString("name"),
                    result.getString("full_name")
            );
            cl.setGroups(this.dbGroups.getGroups(class_id));
        }
        return cl;
    }

    public long addClass(Class_obj class_, int schedule_id) throws SQLException {
        String SQL_INSERT = "INSERT INTO Class(name, full_name, schedule_id)" +
                " VALUES (?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, class_.getName());
        statement.setString(2, class_.getFull_name());
        statement.setInt(3, schedule_id);

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating schedule failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating schedule failed, no ID obtained.");
            }
        }
    }

    public int updateClass(Class_obj class_, int schedule_id) throws SQLException {
        String SQL_UPDATE = "UPDATE Class " +
                "SET name=?, full_name=?, schedule_id=? " +
                "WHERE class_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setString(1, class_.getName());
        statement.setString(2, class_.getFull_name());
        statement.setInt(3, schedule_id);
        statement.setInt(4, class_.getClassId());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }

    public int deleteClass(int class_id) throws SQLException {
        String SQL_DELETE = "DELETE FROM Class WHERE class_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_DELETE);

        statement.setInt(1, class_id);

        int i = statement.executeUpdate();
        System.out.println(i+ " records deleted");
        return i;
    }

}
