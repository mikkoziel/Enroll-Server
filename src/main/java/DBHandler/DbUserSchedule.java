package DBHandler;

import Model.Class_obj;
import Model.UserSchedule;

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
                    result.getBoolean("admin")
            ));
        }
        return uss;
    }

    public int addUserSchedule(UserSchedule us) throws SQLException {
        String SQL_INSERT = "INSERT INTO UserSchedule(user_id, schedule_id, admin)" +
                " VALUES (?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT);

        statement.setInt(1, us.getUser_id());
        statement.setInt(2, us.getSchedule_id());
        statement.setInt(3, us.isAdmin()? 1:0);

        int i = statement.executeUpdate();
        System.out.println(i+ " records inserted");
        return i;
    }

    public int updateUserSchedule(UserSchedule us) throws SQLException {
        String SQL_UPDATE = "UPDATE UserSchedule(admin)" +
                " VALUES (?) WHERE user_id=? AND schedule_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setInt(1, us.isAdmin() ? 1 : 0);
        statement.setInt(2, us.getUser_id());
        statement.setInt(3, us.getSchedule_id());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }

    public int deleteUserSchedule(int schedule_id, int user_id) throws SQLException {
        String SQL_DELETE = "DELETE FROM UserSchedule " +
                "WHERE schedule_id=? AND user_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_DELETE);

        statement.setInt(1, schedule_id);
        statement.setInt(2, user_id);

        int i = statement.executeUpdate();
        System.out.println(i+ " records deleted");
        return i;
    }

}
