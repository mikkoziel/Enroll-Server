package AdminHandler;

import DBHandler.DBHandler;
import Model.FieldOfStudy;
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

    public String getScheduleProfUS(String schedule_id, int admin_id){
        int schedule_id_int = Integer.parseInt(schedule_id);
        try{
            Schedule schedule = this.db.getScheduleAdmin(admin_id, schedule_id_int);
            ArrayList<Professor> professors = this.db.getProfessors();
            ArrayList<User> users = this.db.getUsersForSchedule(schedule_id_int);
            return "{\"schedule\":" + schedule.toString() +
                    ", \"professors\":" + professors.toString() +
                    ", \"users\":" + users.toString() +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }

    }

    public String getUsers(){
        try {
            ArrayList<User> users = this.db.getUsers();
            return "{\"users\":" + users.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getFieldsForId(int user_id){
        try {
            ArrayList<FieldOfStudy> fields = this.db.getFieldsForId(user_id);
            return "{\"fields\":" + fields.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getFieldsSchedules(int user_id){
        try {
            ArrayList<Schedule> schedules = this.db.getSchedulesAdmin(user_id);
            ArrayList<FieldOfStudy> fields = this.db.getFieldsForId(user_id);
            return "{\"schedules\":" + schedules.toString() + ", " +
                    "\"fields\":" + fields.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getFieldsDetails(String field_id){
        int field_id_int = Integer.parseInt(field_id);
        try {
            FieldOfStudy field = this.db.getField(field_id_int);
            ArrayList<User> users = this.db.getUsersForFoS(field_id_int);
            return "{\"field\":" + field.toString() +
                    ", \"users\":" + users.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
