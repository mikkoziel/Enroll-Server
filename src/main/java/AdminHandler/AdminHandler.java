package AdminHandler;

import DBHandler.DBHandler;
import Model.Class_obj;
import Model.Group;
import Model.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminHandler {
     DBHandler db;

    public AdminHandler() {
        this.db = new DBHandler();
    }

    public String getSchedules(int admin_id){
        ArrayList<Schedule> schedules = null;
        try{
            schedules = this.db.getSchedulesAdmin(admin_id);
            return "{\"schedules\":" + schedules.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getSchedule(int admin_id, String schedule_id){
        int schedule_id_int = Integer.parseInt(schedule_id);
        Schedule schedule = null;
        try{
            schedule = this.db.getScheduleAdmin(admin_id, schedule_id_int);
            return schedule.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public long addSchedule(int admin_id, Schedule schedule){
        try {
            return this.db.addSchedule(admin_id, schedule);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long addClass(int admin_id, Class_obj class_, int schedule_id){
        try {
            return this.db.addClass(admin_id,class_, schedule_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long addGroup(int admin_id, Group group, int class_id){
        try {
            return this.db.addGroup(admin_id, group, class_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
