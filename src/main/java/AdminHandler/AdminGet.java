package AdminHandler;

import DBHandler.DBHandler;
import Model.Professor;
import Model.Schedule;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminGet {
    DBHandler db;

    public AdminGet(DBHandler db) {
        this.db = db;
    }

    public String getSchedules(int admin_id){
        try{
            ArrayList<Schedule> schedules = this.db.getSchedulesAdmin(admin_id);
            return "{\"schedules\":" + schedules.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getSchedule(int admin_id, String schedule_id){
        int schedule_id_int = Integer.parseInt(schedule_id);
        try{
            Schedule schedule = this.db.getScheduleAdmin(admin_id, schedule_id_int);
            return schedule.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getProfessors(){
        try {
            ArrayList<Professor> professors = this.db.getProfessors();
            return "{\"professors\":" + professors.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getUsersForSchedule(String schedule_id){
        int schedule_id_int = Integer.parseInt(schedule_id);
        try {
            ArrayList<User> users = this.db.getUsersForSchedule(schedule_id_int);
            return "{\"users\":" + users.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
