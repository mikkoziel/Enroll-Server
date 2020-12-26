package DBHandler;

import Model.Enrollment;
import Model.User;

import java.sql.*;
import java.util.ArrayList;

public class DbEnrollment {
    Connection conn;

    public DbEnrollment(Connection conn){
        this.conn = conn;
    }

    public int addEnroll(Enrollment enroll) throws SQLException {
        String SQL_INSERT = "INSERT INTO Enrollment(schedule_id, start_date, end_date)" +
                " VALUES (?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT);

        statement.setInt(1, enroll.getSchedule_id());
        statement.setTimestamp(2, enroll.getStartDateSQL());
        statement.setTimestamp(3, enroll.getEndDateSQL());

        int i = statement.executeUpdate();
        System.out.println(i+ " records inserted");
        return i;
    }

    public ArrayList<Enrollment> getEnroll(int enroll_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Enrollment WHERE user_id=?";
        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, enroll_id);

        ResultSet result = statement.executeQuery();
        ArrayList<Enrollment> enrolls = new ArrayList<>();

        while (result.next()) {
            enrolls.add(new Enrollment(
                    result.getInt("enroll_id"),
                    result.getInt("schedule_id"),
                    result.getString("startDate"),
                    result.getString("endDate")
            ));
        }
        return enrolls;
    }
}
