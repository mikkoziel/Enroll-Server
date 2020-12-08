package DBHandler;

import Model.Schedule;
import Model.Status;

import java.sql.*;
import java.util.ArrayList;

public class DBHandler {
    private Connection conn;
    private String url;
    private String user;
    private String password;
    private String driverClass;

    public DBHandler() {
        this.url = "jdbc:mysql://mysql.agh.edu.pl:3306" +
                "/" +
                "mkoziel1" +
                "?characterEncoding=utf8";
        this.user = "mkoziel1";
        this.password = "NNY0J0kYBP6TmSUi";
        this.driverClass = "com.mysql.cj.jdbc.Driver";

        this.connectToDB();
    }

    public void connectToDB(){
        // Create connection with database
        try {
            Class.forName(this.driverClass);
            this.conn = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Schedule> getSchedules(int id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Schedule WHERE schedule_id IN " +
                "(SELECT schedule_id FROM UserSchedule WHERE user_id=? AND admin=1)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();
        ArrayList<Schedule> schedules = new ArrayList<>();

        while (result.next()) {

            schedules.add(new Schedule(
                    result.getInt("schedule_id"),
                    result.getString("name"),
                    result.getInt("semester"),
                    result.getString("description"),
                    Status.valueOf(result.getString("status"))
            ));
        }
        return schedules;
    }

    public long addSchedule(Schedule schedule) throws SQLException {
        String SQL_INSERT = "INSERT INTO Schedule(name, semester, description, status)" +
                " VALUES (?, ?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT,
                        Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, schedule.getName());
        statement.setInt(2, schedule.getSemester());
        statement.setString(3, schedule.getDescription());
        statement.setString(4, String.valueOf(schedule.getStatus()));

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
}
