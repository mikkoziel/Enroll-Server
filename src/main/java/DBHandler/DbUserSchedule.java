package DBHandler;

import Model.UserSchedule;
import Model.UserType;

import java.sql.*;
import java.util.ArrayList;

public class DbUserSchedule {
    Connection conn;

    public DbUserSchedule(Connection conn){
        this.conn = conn;
    }

    public ArrayList<UserSchedule> getUserSchedule(int schedule_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM UserSchedule WHERE schedule_id =? ";

        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT);

        statement.setInt(1, schedule_id);

        ResultSet result = statement.executeQuery();
        ArrayList<UserSchedule> uss = new ArrayList<>();

        while (result.next()) {
            uss.add(new UserSchedule(
                    result.getInt("user_id"),
                    result.getInt("schedule_id"),
                    UserType.valueOf(result.getString("type"))
            ));
        }
        return uss;
    }

    public int addUserSchedule(UserSchedule us) throws SQLException {
        String SQL_INSERT = "INSERT INTO UserSchedule(user_id, schedule_id, type)" +
                " VALUES (?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT);

        statement.setInt(1, us.getUser_id());
        statement.setInt(2, us.getSchedule_id());
        statement.setString(3, us.getType().label);

        int i = statement.executeUpdate();
        System.out.println(i+ " records inserted");
        return i;
    }

    public int updateUserSchedule(UserSchedule us) throws SQLException {
        String SQL_UPDATE = "UPDATE UserSchedule " +
                " SET type=?" +
                " WHERE user_id=? AND schedule_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setString(1, us.getType().label);
        statement.setInt(2, us.getUser_id());
        statement.setInt(3, us.getSchedule_id());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }

    public int deleteUserSchedule(UserSchedule us) throws SQLException {
        String SQL_DELETE = "DELETE FROM UserSchedule " +
                "WHERE schedule_id=? AND user_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_DELETE);

        statement.setInt(1, us.getSchedule_id());
        statement.setInt(2, us.getUser_id());

        int i = statement.executeUpdate();
        System.out.println(i+ " records deleted");
        return i;
    }

}
