package AdminHandler;

import DBHandler.DBHandler;
import Model.*;
import org.json.JSONObject;

import java.sql.SQLException;

public class AdminPost {
    DBHandler db;
    AdminParser parser;

    public AdminPost(DBHandler db) {
        this.db = db;
        this.parser = new AdminParser();
    }

    public String postSchedule(String msg, int id){
        Schedule schedule = this.parser.parseStringToSchedule(msg);
        long schedule_id = this.addSchedule(id, schedule);
        if(!schedule.getClasses().isEmpty()){
            schedule.getClasses().forEach((class_) ->{
                long class_id = this.addClass(class_, (int)schedule_id);
                if(!class_.getGroups().isEmpty()){
                    class_.getGroups().forEach((group ->
                            this.addGroup(group, (int)class_id)));
                }
            });
        }
        return "{\"schedule_id\": " + schedule_id + "}";
    }

    public String postClass(String uri, String msg, int id){
        int schedule_id = Integer.parseInt(uri.replace("schedules/", ""));
        Class_obj class_ = this.parser.parseStringToClass(new JSONObject(msg));
        long class_id = this.addClass(class_, schedule_id);
        if(!class_.getGroups().isEmpty()){
            class_.getGroups().forEach((group ->
                    this.addGroup(group, (int)class_id)));
        }
        return "{\"class_id\": " + class_id + "}";
    }

    public String postGroup(String uri, String msg, int id){
        int class_id = Integer.parseInt(uri.split("/")[2]);
        Group group = this.parser.parseStringToGroup(new JSONObject(msg));
        long group_id = this.addGroup(group, class_id);
        return "{\"group_id\": " + group_id + "}";
    }

    public String postUserSchedule(String msg) {
        UserSchedule us = this.parser.parseStringToUS(new JSONObject(msg));
        int admin = us.isAdmin() ? 1 : 0;
        return "{\"added\": " +
                this.addUserSchedule(us.getUser_id(), us.getSchedule_int(), admin) +
                "}";
    }

    public String postProfessor(String msg) {
        Professor prof = this.parser.parseStringToProf(new JSONObject(msg));
        return "{\"professor_id\": " +
                this.addProfessor(prof) +
                "}";
    }

    public String postUserPreference(String msg) {
        UserPreference up = this.parser.parseStringToUP(new JSONObject(msg));
        return "{\"added\": " +
                this.addUserPreference(up) +
                "}";
    }

    public String postUser(String msg){
        User user = this.parser.parseStringToUser(new JSONObject(msg));
        return "{\"user_id\": " +
                this.addUser(user) +
                "}";
    }
    //-------ADD--------------------------------------------------------
    public long addSchedule(int admin_id, Schedule schedule){
        try {
            return this.db.addSchedule(admin_id, schedule);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long addClass(Class_obj class_, int schedule_id){
        try {
            return this.db.addClass(class_, schedule_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public long addGroup(Group group, int class_id){
        try {
            return this.db.addGroup(group, class_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int addUserSchedule(int user_id, int schedule_id, int admin){
        try {
            return this.db.addUserSchedule(user_id, schedule_id, admin);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addProfessor(Professor prof){
        try {
            return this.db.addProfessor(prof);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addUserPreference(UserPreference up){
        try {
            return this.db.addUserPreference(up);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addUser(User user){
        try {
            return this.db.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
