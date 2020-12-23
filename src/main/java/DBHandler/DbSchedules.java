package DBHandler;

import Model.Schedule;
import Model.Status;
import Model.UserSchedule;

import java.sql.*;
import java.util.ArrayList;

public class DbSchedules {
    Connection conn;
    DbClasses dbClasses;
    private DbUserSchedule dbUserSchedule;

    public DbSchedules(Connection conn, DbClasses dbClasses, DbUserSchedule dbUserSchedule){
        this.conn = conn;
        this.dbClasses = dbClasses;
        this.dbUserSchedule = dbUserSchedule;
    }

    //----SCHEDULES-------------------------------------------------------
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

    private ArrayList<Schedule> getSchedules(int user_id, String SQL_SELECT) throws SQLException {
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
            tmp.setClasses(this.dbClasses.getClasses(schedule_id));

            schedules.add(tmp);

        }
        return schedules;
    }

    //----SCHEDULE-------------------------------------------------------
    public long addSchedule(int admin_id, Schedule schedule) throws SQLException {
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
                long key = generatedKeys.getLong(1);
                this.dbUserSchedule.addUserSchedule(new UserSchedule(admin_id, (int)key, true));
                return key;
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

    private Schedule getSchedule(int user_id, int schedule_id, String SQL_SELECT) throws SQLException {
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
            schedule.setClasses(this.dbClasses.getClasses(schedule_id_));
        }
        return schedule;
    }

    public int updateSchedule(Schedule schedule) throws SQLException {
        String SQL_UPDATE = "UPDATE Schedule " +
                "SET name=?, semester=?, description=?, status=? " +
                "WHERE schedule_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setString(1, schedule.getName());
        statement.setInt(2, schedule.getSemester());
        statement.setString(3, schedule.getDescription());
        statement.setString(4, String.valueOf(schedule.getStatus()));
        statement.setInt(5, schedule.getScheduleID());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }

    public int deleteSchedule(int schedule_int) throws SQLException {
        String SQL_DELETE = "DELETE FROM Schedule WHERE schedule_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_DELETE);

        statement.setInt(1, schedule_int);

        int i = statement.executeUpdate();
        System.out.println(i+ " records deleted");
        return i;
    }
}
