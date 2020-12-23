package DBHandler;

import Model.UserSchedule;

import java.sql.*;

public class DbUserSchedule {
    Connection conn;

    public DbUserSchedule(Connection conn){
        this.conn = conn;
    }

    public int addUserSchedule(UserSchedule us) throws SQLException {
        String SQL_INSERT = "INSERT INTO UserSchedule(user_id, schedule_id, admin)" +
                " VALUES (?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT);

        statement.setInt(1, us.getUser_id());
        statement.setInt(2, us.getSchedule_int());
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
        statement.setInt(3, us.getSchedule_int());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }
}
