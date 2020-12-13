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
     AdminGet adminGet;
     AdminPost adminPost;
     AdminPut adminPut;

    public AdminHandler() {
        this.db = new DBHandler();
        this.adminGet = new AdminGet(this.db);
        this.adminPost = new AdminPost(this.db);
        this.adminPut = new AdminPut(this.db);
    }

    //-----------GET----------------------------------------------------
    public String getSchedules(int admin_id){
        return this.adminGet.getSchedules(admin_id);
    }

    public String getSchedule(int admin_id, String schedule_id) {
        return this.adminGet.getSchedule(admin_id, schedule_id);
    }

    public String getProfessors(){
        return this.adminGet.getProfessors();
    }

    //----------POST----------------------------------------------------
    public String postSchedule(String uri, String msg, int id){
        return this.adminPost.postSchedule(uri, msg, id);
    }

    public String postClass(String uri, String msg, int id){
        return this.adminPost.postClass(uri, msg, id);
    }

    public String postGroup(String uri, String msg, int id){
        return this.adminPost.postGroup(uri, msg, id);
    }

    public void postUserSchedule(int user_id, int schedule_id, int admin){
        this.adminPost.addUserSchedule(user_id, schedule_id, admin);
    }

    //----UPDATE-------------------------------------------------------
    public void putSchedule(Schedule schedule){
        this.adminPut.putSchedule(schedule);
    }

    public void putUserSchedule(int user_id, int schedule_id, int admin){
        this.adminPut.putUserSchedule(user_id, schedule_id, admin);
    }

}
