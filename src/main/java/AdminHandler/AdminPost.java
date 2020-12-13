package AdminHandler;

import DBHandler.DBHandler;
import Model.Class_obj;
import Model.Group;
import Model.Schedule;
import org.json.JSONObject;

import java.sql.SQLException;

public class AdminPost {
    DBHandler db;
    AdminParser parser;

    public AdminPost(DBHandler db) {
        this.db = db;
        this.parser = new AdminParser();
    }

    public String postSchedule(String uri, String msg, int id){
        Schedule schedule = this.parser.parseStringToSchedule(msg);
        long schedule_id = this.addSchedule(id, schedule);
        if(!schedule.getClasses().isEmpty()){
            schedule.getClasses().forEach((class_) ->{
                long class_id = this.addClass(id, class_, (int)schedule_id);
                if(!class_.getGroups().isEmpty()){
                    class_.getGroups().forEach((group ->
                            this.addGroup(id, group, (int)class_id)));
                }
            });
        }
        return "{\"schedule_id\": " + schedule_id + "}";
    }

    public String postClass(String uri, String msg, int id){
        int schedule_id = Integer.parseInt(uri.replace("schedules/", ""));
        Class_obj class_ = this.parser.parseStringToClass(new JSONObject(msg));
        long class_id = this.addClass(id, class_, schedule_id);
        if(!class_.getGroups().isEmpty()){
            class_.getGroups().forEach((group ->
                    this.addGroup(id, group, (int)class_id)));
        }
        return "{\"class_id\": " + class_id + "}";
    }

    public String postGroup(String uri, String msg, int id){
        int class_id = Integer.parseInt(uri.split("/")[2]);
        Group group = this.parser.parseStringToGroup(new JSONObject(msg));
        long group_id = this.addGroup(id, group, class_id);
        return "{\"group_id\": " + group_id + "}";
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


    public void addUserSchedule(int user_id, int schedule_id, int admin){
        try {
            this.db.addUserSchedule(user_id, schedule_id, admin);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
