package DBHandler;

import Model.Class_obj;
import Model.Group;
import Model.Schedule;
import Model.Status;

import java.sql.*;
import java.time.LocalTime;
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
                "mkoziel1"; // +
//                "?characterEncoding=utf8";
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

    public ArrayList<Schedule> getSchedulesUser(int user_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Schedule WHERE schedule_id IN " +
                "(SELECT schedule_id FROM UserSchedule WHERE user_id=?)";
        return this.getSchedules(user_id, SQL_SELECT);
    }

    public ArrayList<Schedule> getSchedulesAdmin(int admin_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Schedule WHERE schedule_id IN " +
                "(SELECT schedule_id FROM UserSchedule WHERE user_id=? AND admin=1)";
        return this.getSchedules(admin_id, SQL_SELECT);
    }

    public ArrayList<Schedule> getSchedules(int user_id, String SQL_SELECT) throws SQLException {
        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, user_id);

        ResultSet result = statement.executeQuery();
        ArrayList<Schedule> schedules = new ArrayList<>();

        while (result.next()) {
            int schedule_id = result.getInt("schedule_id");
            Schedule tmp = new Schedule(
                    schedule_id,
                    result.getString("name"),
                    result.getInt("semester"),
                    result.getString("description"),
                    Status.valueOf(result.getString("status"))
            );
            tmp.setClasses(this.getClasses(schedule_id));

            schedules.add(tmp);

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

    public Schedule getScheduleUser(int user_id, int schedule_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Schedule WHERE schedule_id IN " +
                "(SELECT schedule_id FROM UserSchedule WHERE user_id=? AND schedule_id=?)";
        return this.getSchedule(user_id, schedule_id, SQL_SELECT);
    }

    public Schedule getScheduleAdmin(int user_id, int schedule_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM Schedule WHERE schedule_id IN " +
                "(SELECT schedule_id FROM UserSchedule WHERE user_id=? AND admin=1 AND schedule_id=?)";
        return this.getSchedule(user_id, schedule_id, SQL_SELECT);
    }

    public Schedule getSchedule(int user_id, int schedule_id, String SQL_SELECT) throws SQLException {
        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, user_id);
        statement.setInt(2, schedule_id);

        ResultSet result = statement.executeQuery();
        Schedule schedule = null;

        if (result.next()) {
            int schedule_id_ = result.getInt("schedule_id");
            schedule = new Schedule(
                    schedule_id_,
                    result.getString("name"),
                    result.getInt("semester"),
                    result.getString("description"),
                    Status.valueOf(result.getString("status"))
            );
            schedule.setClasses(this.getClasses(schedule_id_));
        }
        return schedule;
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
                    result.getString("name")
            );
            tmp.setGroups(this.getGroups(class_id));
            classes.add(tmp);
        }
        return classes;
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
                    result.getInt("professor_id")
            );
            groups.add(tmp);
        }
        return groups;
    }
}
