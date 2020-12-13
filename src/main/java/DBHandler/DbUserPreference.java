package DBHandler;

import Model.Group;
import Model.UserPreference;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class DbUserPreference {
    Connection conn;

    public DbUserPreference(Connection conn){
        this.conn = conn;
    }

    public int addUserPreference(UserPreference up) throws SQLException {
        String SQL_INSERT = "INSERT INTO UserPreference()" +
                " VALUES (?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT);

        statement.setInt(1, up.getUser_id());
        statement.setInt(2, up.getGroup_id());
        statement.setInt(3, up.getGroup_id());

        int i = statement.executeUpdate();
        System.out.println(i+ " records inserted");
        return i;
    }

    public int updateUserPreference(UserPreference up) throws SQLException {
        String SQL_UPDATE = "UPDATE UserPreference(points)" +
                " VALUES (?) WHERE user_id=? AND group_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setInt(1, up.getGroup_id());
        statement.setInt(2, up.getUser_id());
        statement.setInt(3, up.getGroup_id());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }

    public ArrayList<UserPreference> getUPForSchedule(int schedule_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM UserPreference WHERE group_id IN" +
                "(SELECT group_id FROM Groups WHERE class_id IN" +
                "(SELECT class_id FROM Class WHERE schedule_id=?))";
        return this.getUserPreferences(SQL_SELECT, schedule_id);
    }

    public ArrayList<UserPreference> getUPForGroup(int group_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM UserPreference" +
                "WHERE group_id=?";
        return this.getUserPreferences(SQL_SELECT, group_id);
    }

    public ArrayList<UserPreference> getUPForUser(int user_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM UserPreference" +
                "WHERE user_id=?";
        return this.getUserPreferences(SQL_SELECT, user_id);
    }

    private ArrayList<UserPreference> getUserPreferences(String SQL_SELECT, int id) throws SQLException {
        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();
        ArrayList<UserPreference> ups = new ArrayList<>();

        while (result.next()) {
            ups.add(new UserPreference(
                    result.getInt("user_id"),
                    result.getInt("group_id"),
                    result.getInt("points")
            ));
        }
        return ups;
    }

}
