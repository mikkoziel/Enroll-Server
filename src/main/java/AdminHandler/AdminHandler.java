package AdminHandler;

import DBHandler.DBHandler;
import Model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class AdminHandler {
     DBHandler db;

    public AdminHandler() {
        this.db = new DBHandler();
    }

    //-----------GET----------------------------------------------------
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

    //----------POST----------------------------------------------------
    public String postSchedule(String uri, String msg, int id){
        Schedule schedule = parseStringToSchedule(msg);
        long schedule_id = this.addSchedule(id, schedule);
        if(!schedule.getClasses().isEmpty()){
            schedule.getClasses().forEach((class_) ->{
                long class_id = this.addClass(id, class_, (int)schedule_id);
                if(!class_.getGroups().isEmpty()){
                    class_.getGroups().forEach((group -> {
                        this.addGroup(id, group, (int)class_id);
                    }));
                }
            });
        }
        return "{\"schedule_id\": " + schedule_id + "}";
    }

    public String postClass(String uri, String msg, int id){
        int schedule_id = Integer.parseInt(uri.replace("schedules/", ""));
        Class_obj class_ = this.parseStringToClass(new JSONObject(msg));
        long class_id = this.addClass(id, class_, schedule_id);
        if(!class_.getGroups().isEmpty()){
            class_.getGroups().forEach((group -> {
                this.addGroup(id, group, (int)class_id);
            }));
        }
        return "{\"class_id\": " + class_id + "}";
    }

    public String postGroup(String uri, String msg, int id){
        int class_id = Integer.parseInt(uri.split("/")[2]);
        Group group = this.parseStringToGroup(new JSONObject(msg));
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

    //----PARSER-------------------------------------------------------

    private Schedule parseStringToSchedule(String msg){
        JSONObject jo = new JSONObject(msg);
        return new Schedule(
                jo.getString("name"),
                jo.getInt("semester"),
                jo.getString("description"),
                Status.valueOf(jo.getString("status")),
                this.parseStringToClasses(jo.getJSONArray("classes"))
        );
    }

    private ArrayList<Class_obj> parseStringToClasses(JSONArray classes_json){
        ArrayList<Class_obj> classes = new ArrayList<>();
        for(int i = 0; i < classes_json.length(); i++){
            JSONObject object = classes_json.getJSONObject(i);
            classes.add(this.parseStringToClass(object));
        }
        return classes;
    }

    private Class_obj parseStringToClass(JSONObject class_json){
        return new Class_obj(
                class_json.getString("name"),
                this.parseStringToGroups(class_json.getJSONArray("groups"))
        );
    }

    private ArrayList<Group> parseStringToGroups(JSONArray groups_json){
        ArrayList<Group> groups = new ArrayList<>();
        for(int i = 0; i < groups_json.length(); i++){
            JSONObject object = groups_json.getJSONObject(i);
            groups.add(this.parseStringToGroup(object));
        }
        return groups;
    }

    private Group parseStringToGroup(JSONObject group_json){
        return new Group(
                group_json.getInt("day"),
                LocalTime.parse(group_json.getString("start")),
                LocalTime.parse(group_json.getString("end")),
                group_json.getInt("professor_id")
        );
    }

}
