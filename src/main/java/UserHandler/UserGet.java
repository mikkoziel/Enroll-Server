package UserHandler;

import DBHandler.DBHandler;
import Model.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserGet {
    DBHandler db;

    public UserGet(DBHandler db) {
        this.db = db;
    }

    public String getSchedules(int user_id){
        try{
            ArrayList<Schedule> schedules = this.db.getSchedulesUser(user_id);
            return "{\"schedules\":" + schedules.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getSchedule(int user_id, String schedule_id){
        int schedule_id_int = Integer.parseInt(schedule_id);
        try{
            Schedule schedule = this.db.getScheduleUser(user_id, schedule_id_int);
            return schedule.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
