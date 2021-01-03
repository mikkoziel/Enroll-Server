package AdminHandler;

import DBHandler.DBHandler;
import Model.*;
import Tools.Parser;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.sql.SQLException;

public class AdminPost {
    DBHandler db;
    Parser parser;
    AdminGet adminGet;

    public AdminPost(DBHandler db, AdminGet adminGet) {
        this.db = db;
        this.parser = new Parser();
        this.adminGet = adminGet;
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
        schedule.setScheduleID(Math.toIntExact(schedule_id));
        return schedule.toString();
    }

    public String postClass(String uri, String msg, int id){
        int schedule_id = Integer.parseInt(uri.replace("schedules/", ""));
        Class_obj class_ = this.parser.parseStringToClass(new JSONObject(msg));
        long class_id = this.addClass(class_, schedule_id);
        if(!class_.getGroups().isEmpty()){
            class_.getGroups().forEach((group ->
                    this.addGroup(group, (int)class_id)));
        }
        return class_.toString();
    }

    public String postGroup(String uri, String msg, int id){
        int class_id = Integer.parseInt(uri.split("/")[1]);
        Group group = this.parser.parseStringToGroup(new JSONObject(msg));
        long group_id = this.addGroup(group, class_id);
        if(group_id>0){
            group.setGroupId(Math.toIntExact(group_id));
            return group.toString();
        }
        return group.toString();
    }

    public String postUserSchedule(String msg) {
        UserSchedule us = this.parser.parseStringToUS(new JSONObject(msg));
        int retVal = this.addUserSchedule(us);
        if(retVal>0){
            return this.adminGet.getUsersForSchedule(Integer.toString(us.getSchedule_id()));
        }
        return "{\"added\": " +
                retVal +
                "}";
    }

    public String postProfessor(String msg) {
        Professor prof = this.parser.parseStringToProf(new JSONObject(msg));
        int retVal = this.addProfessor(prof);
        if(retVal>0){
            prof.setProfessor_id(retVal);
            return prof.toString();
        }
        return "{\"professor_id\": 0}";
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

    public String postFoS(String msg, int admin_id){
        FieldOfStudy fos = this.parser.parseStringToFoS(new JSONObject(msg));
        int retVal = this.addFoS(fos, admin_id);
        if(retVal>0){
            fos.setField_id(retVal);
            return fos.toString();
        }
        return "{\"field_id\": 0}";
    }

    public String postUserField(String msg){
        UserField uf = this.parser.parseStringToUF(new JSONObject(msg));
        int retVal = this.addUserField(uf);
        if(retVal>0){
            return uf.toString();
        }
        return "{\"field_id\": 0}";
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


    public int addUserSchedule(UserSchedule us){
        try {
            return this.db.addUserSchedule(us);
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

    public int addFoS(FieldOfStudy fos, int admin_id) {
        try {
            int retVal = this.db.addFoS(fos);
            UserField uf = new UserField(admin_id, retVal, UserType.ADMIN);
            this.db.addUserField(uf);
            return retVal;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addUserField(UserField uf){
        try {
            return this.db.addUserField(uf);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
