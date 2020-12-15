package Tools;

import Model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;

public class Parser {

    public Parser(){}

    public Schedule parseStringToSchedule(String msg){
        JSONObject jo = new JSONObject(msg);
        return new Schedule(
                jo.getString("name"),
                jo.getInt("semester"),
                jo.getString("description"),
                Status.valueOf(jo.getString("status")),
                this.parseStringToClasses(jo.getJSONArray("classes"))
        );
    }

    public ArrayList<Class_obj> parseStringToClasses(JSONArray classes_json){
        ArrayList<Class_obj> classes = new ArrayList<>();
        for(int i = 0; i < classes_json.length(); i++){
            JSONObject object = classes_json.getJSONObject(i);
            classes.add(this.parseStringToClass(object));
        }
        return classes;
    }

    public Class_obj parseStringToClass(JSONObject class_json){
        return new Class_obj(
                class_json.getString("name"),
                this.parseStringToGroups(class_json.getJSONArray("groups"))
        );
    }

    public ArrayList<Group> parseStringToGroups(JSONArray groups_json){
        ArrayList<Group> groups = new ArrayList<>();
        for(int i = 0; i < groups_json.length(); i++){
            JSONObject object = groups_json.getJSONObject(i);
            groups.add(this.parseStringToGroup(object));
        }
        return groups;
    }

    public Group parseStringToGroup(JSONObject group_json){
        return new Group(
                group_json.getInt("day"),
                LocalTime.parse(group_json.getString("start")),
                LocalTime.parse(group_json.getString("end")),
                group_json.getInt("professor_id")
        );
    }

    public UserSchedule parseStringToUS(JSONObject us_json){
        return new UserSchedule(
                us_json.getInt("user_id"),
                us_json.getInt("schedule_int"),
                us_json.getBoolean("admin"));
    }

    public Professor parseStringToProf(JSONObject prof_json){
        return new Professor(
                prof_json.getString("name"),
                prof_json.getString("surname"));
    }

    public UserPreference parseStringToUP(JSONObject up_json){
        return new UserPreference(
                up_json.getInt("user_id"),
                up_json.getInt("group_id"),
                up_json.getInt("points"));
    }

    public User parseStringToUser(JSONObject user_json){
        return new User(
                user_json.getString("name"),
                user_json.getString("surname"),
                user_json.getString("password"),
                user_json.getString("mail"),
                user_json.getBoolean("admin"));
    }
}
