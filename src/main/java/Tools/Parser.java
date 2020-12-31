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

    public Schedule parseStringToScheduleWithId(String msg){
        JSONObject jo = new JSONObject(msg);
        return new Schedule(
                jo.getInt("id"),
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
                class_json.getString("full_name"),
                this.parseStringToGroups(class_json.getJSONArray("groups"))
        );
    }

    public Class_obj parseStringToClassWithId(JSONObject class_json){
        return new Class_obj(
                class_json.getInt("id"),
                class_json.getString("name"),
                class_json.getString("full_name"),
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
        JSONObject start = group_json.getJSONObject("start");
        JSONObject end = group_json.getJSONObject("end");
        return new Group(
                group_json.getInt("day"),
                LocalTime.of(start.getInt("hour"), start.getInt("minute")),
                LocalTime.of(end.getInt("hour"), end.getInt("minute")),
                group_json.getInt("professor_id"),
                group_json.getString("type")
        );
    }

    public Group parseStringToGroupWithId(JSONObject group_json){
        JSONObject start = group_json.getJSONObject("start");
        JSONObject end = group_json.getJSONObject("end");
        return new Group(
                group_json.getInt("id"),
                group_json.getInt("day"),
                LocalTime.of(start.getInt("hour"), start.getInt("minute")),
                LocalTime.of(end.getInt("hour"), end.getInt("minute")),
                group_json.getInt("professor_id"),
                group_json.getString("type")
        );
    }

    public UserSchedule parseStringToUS(JSONObject us_json){
        return new UserSchedule(
                us_json.getInt("user_id"),
                us_json.getInt("schedule_id"),
                us_json.getBoolean("type"));
    }

    public Professor parseStringToProf(JSONObject prof_json){
        return new Professor(
                prof_json.getString("name"),
                prof_json.getString("surname"),
                prof_json.getString("title"));
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

    public Enrollment parseStringToEnroll(JSONObject enroll_json){
        return new Enrollment(
//                enroll_json.getInt("enroll_id"),
                enroll_json.getInt("schedule_id"),
                enroll_json.getString("startDate"),
                enroll_json.getString("endDate")
        );
    }
}
