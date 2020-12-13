package AdminHandler;

import DBHandler.DBHandler;
import Model.Professor;
import Model.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminGet {
    DBHandler db;

    public AdminGet(DBHandler db) {
        this.db = db;
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

    public String getProfessors(){
        ArrayList<Professor> professors = null;
        try {
            professors = this.db.getProfessors();
            return "{\"professors\":" + professors.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
