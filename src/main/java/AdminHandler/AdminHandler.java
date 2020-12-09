package AdminHandler;

import DBHandler.DBHandler;
import Model.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminHandler {
     DBHandler db;

    public AdminHandler() {
    this.db = new DBHandler();
    }

    public String getSchedules(int id){
        ArrayList<Schedule> schedules = null;
        try{
            schedules = this.db.getSchedulesAdmin(id);
            return "{\"schedules\":" + schedules.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getSchedule(int user_id, String schedule_id){
        int schedule_id_int = Integer.parseInt(schedule_id);
        Schedule schedule = null;
        try{
            schedule = this.db.getScheduleAdmin(user_id, schedule_id_int);
            return schedule.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
