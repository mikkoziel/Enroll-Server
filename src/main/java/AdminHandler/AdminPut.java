package AdminHandler;

import DBHandler.DBHandler;
import Model.*;
import Tools.Parser;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminPut {
    DBHandler db;
    Parser parser;
    AdminGet adminGet;

    public AdminPut(DBHandler db, AdminGet adminGet) {
        this.db = db;
        this.parser = new Parser();
        this.adminGet = adminGet;
    }

    public String putSchedule(String msg, int id){
        Schedule schedule = this.parser.parseStringToScheduleWithId(msg);
        int retVal = this.updateSchedule(schedule);
        if(retVal>0){
            return this.adminGet.getSchedule(id, String.valueOf(schedule.getScheduleID()));
        } else {
            return "{\"updated\": " +
                    retVal +
                    "}";
        }
    }

    public String putUserSchedule(String msg){
        UserSchedule us = this.parser.parseStringToUS(new JSONObject(msg));
        return "{\"updated\": " +
                this.updateUserSchedule(us) +
                "}";
    }

    public String putUser(String msg){
        User user = this.parser.parseStringToUser(new JSONObject(msg));
        return "{\"updated\": " +
                this.updateUser(user) +
                "}";
    }

    public String putEnroll(String msg, int id){
        Enrollment enroll = this.parser.parseStringToEnroll(new JSONObject(msg));
        try {
            int retVal = this.updateScheduleEnroll(enroll.getSchedule_id());
            if(retVal>0){
                this.db.addEnroll(enroll);
                ArrayList<User> users = this.db.getUsersForSchedule(enroll.getSchedule_id());
                ArrayList<Group> groups = this.db.getGroupsForSchedule(enroll.getSchedule_id());
                for(User user: users){
                    for(Group group: groups){
                        UserPreference up = new UserPreference(user.getUserId(), group.getGroupId(), 0);
                        this.db.addUserPreference(up);
                    }
                }
                return this.adminGet.getSchedule(id, String.valueOf(enroll.getSchedule_id()));
            } else {
                return "{\"updated\": " +
                        retVal +
                        "}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"updated\": \"Error\"";
        }
    }
    //-------UPDATE--------------------------------------------------------
    public int updateSchedule(Schedule schedule){
        try {
            return this.db.updateSchedule(schedule);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateUserSchedule(UserSchedule us){
        try {
            return this.db.updateUserSchedule(us);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateUser(User user){
        try {
            return this.db.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateScheduleEnroll(int schedule_id){
        try {
            return this.db.updateScheduleStatus(schedule_id, Status.ENROLLMENT);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
