package AdminHandler;

import Model.Class_obj;
import Model.Group;
import Model.Schedule;
import Model.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;

public class AdminParser {

    public AdminParser(){}

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
}
