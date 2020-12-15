package DBHandler;

import java.sql.*;

public class DbUserSchedule {
    Connection conn;

    public DbUserSchedule(Connection conn){
        this.conn = conn;
    }

    public int addUserSchedule(int user_id, int schedule_id, int admin) throws SQLException {
        String SQL_INSERT = "INSERT INTO UserSchedule(user_id, schedule_id, admin)" +
                " VALUES (?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT);

        statement.setInt(1, user_id);
        statement.setInt(2, schedule_id);
        statement.setInt(3, admin);

        int i = statement.executeUpdate();
        System.out.println(i+ " records inserted");
        return i;
    }

    public int updateUserSchedule(int user_id, int schedule_id, int admin) throws SQLException {
        String SQL_UPDATE = "UPDATE UserSchedule(admin)" +
                " VALUES (?) WHERE user_id=? AND schedule_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setInt(1, admin);
        statement.setInt(2, user_id);
        statement.setInt(3, schedule_id);

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }
}
