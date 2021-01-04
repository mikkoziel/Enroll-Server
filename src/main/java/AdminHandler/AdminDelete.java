package AdminHandler;

import DBHandler.DBHandler;
import Model.*;
import Tools.Parser;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDelete {
    DBHandler db;
    Parser parser;

    public AdminDelete(DBHandler db) {
        this.db = db;
        this.parser = new Parser();
    }

    public String deleteSchedule(String uri){
        int schedule_id = Integer.parseInt(uri.replace("schedules/", ""));
        try {
            ArrayList<UserSchedule> uss = this.db.getUserSchedule(schedule_id);
            for(UserSchedule us: uss){
                this.db.deleteUserSchedule(us);
            }
            ArrayList<Class_obj> classes = this.db.getClasses(schedule_id);
            for(Class_obj class_: classes){
                this.deleteClass("classes/" + class_.getClassId());
            }
            int retVal  =this.db.deleteSchedule(schedule_id);
            return "{\"deleted\": " +
                    retVal +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String deleteClass(String uri){
        int class_id = Integer.parseInt(uri.replace("classes/", ""));
        try {
            Class_obj class_ = this.db.getClass(class_id);
            for(Group group: class_.getGroups()){
                this.db.deleteGroup(group.getGroupId());
            }
            int retVal = this.db.deleteClass(class_id);
            return "{\"deleted\": " +
                     retVal +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String deleteGroup(String uri){
        int group_id = Integer.parseInt(uri.replace("groups/", ""));
        try {
            return "{\"deleted\": " +
                    this.db.deleteGroup(group_id) +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String deleteFoS(String uri){
        int field_id = Integer.parseInt(uri.replace("fos/", ""));
        try {
            ArrayList<User> users = this.db.getUsersForFoS(field_id);
            for(User user: users){
                UserField uf = new UserField(
                        user.getUserId(),
                        field_id,
                        null
                );
                this.db.deleteUserField(uf);
            }
            int retVal = this.db.deleteFoS(field_id) ;
            return "{\"deleted\": " +
                    retVal +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String deleteUserField(String uri, int id){
        int field_id = Integer.parseInt(uri);
        UserField uf = new UserField(id, field_id, null);
        try {
            return "{\"deleted\": " +
                    this.db.deleteUserField(uf) +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String deleteUserSchedule(String uri, int id){
        int schedule_id = Integer.parseInt(uri);
        UserSchedule us = new UserSchedule(id, schedule_id, null);
        try {
            return "{\"deleted\": " +
                    this.db.deleteUserSchedule(us) +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
