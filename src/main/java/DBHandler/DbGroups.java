package DBHandler;

import Model.Class_obj;
import Model.Group;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class DbGroups {
    Connection conn;

    public DbGroups(Connection conn){
        this.conn = conn;
    }

    public ArrayList<Group> getGroups(int class_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Groups WHERE class_id =? ";

        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, class_id);

        ResultSet result = statement.executeQuery();
        ArrayList<Group> groups = new ArrayList<>();

        while (result.next()) {
            int group_id = result.getInt("group_id");
            Group tmp = new Group(
                    group_id,
                    result.getInt("day"),
                    LocalTime.parse(result.getString("start")),
                    LocalTime.parse(result.getString("end")),
                    result.getInt("professor_id"),
                    result.getString("type")
            );
            groups.add(tmp);
        }
        return groups;
    }

    public ArrayList<Group> getGroupsForSchedule(int schedule_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Groups WHERE class_id IN " +
                "(SELECT class_id FROM Class WHERE schedule_id=?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, schedule_id);

        ResultSet result = statement.executeQuery();
        ArrayList<Group> groups = new ArrayList<>();

        while (result.next()) {
            groups.add(new Group(
                    result.getInt("group_id"),
                    result.getInt("day"),
                    LocalTime.parse(result.getString("start")),
                    LocalTime.parse(result.getString("end")),
                    result.getInt("professor_id"),
                    result.getString("type")
            ));
        }
        return groups;
    }

    public long addGroup(Group group, int class_id) throws SQLException {
        String SQL_INSERT = "INSERT INTO Groups(day, start, end, class_id, professor_id, type)" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, group.getDay());
        statement.setString(2, group.getStart().toString());
        statement.setString(3, group.getEnd().toString());
        statement.setInt(4, class_id);
        statement.setInt(5, group.getProfessor_id());
        statement.setString(6, group.getType());

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

    public int updateGroup(Group group, int class_id) throws SQLException {
        String SQL_UPDATE = "UPDATE Groups " +
                "SET class_id=?, day=?, start=?, end=?, type=?, professor_id=? " +
                "WHERE group_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setInt(1, class_id);
        statement.setInt(2, group.getDay());
        statement.setString(3, group.getStart().toString());
        statement.setString(4, group.getEnd().toString());
        statement.setString(5, group.getType());
        statement.setInt(6, group.getProfessor_id());
        statement.setInt(7, group.getGroupId());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }

    public int deleteGroup(int group_id) throws SQLException {
        String SQL_DELETE = "DELETE FROM Groups WHERE group_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_DELETE);

        statement.setInt(1, group_id);

        int i = statement.executeUpdate();
        System.out.println(i+ " records deleted");
        return i;
    }

}
