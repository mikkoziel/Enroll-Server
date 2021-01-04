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

    public String putClass(String uri, String msg){
        Class_obj class_ = this.parser.parseStringToClassWithId(new JSONObject(msg));
        int schedule_id = Integer.parseInt(uri);
        int retVal = this.updateClass(class_, schedule_id);
        if(retVal>0){
            try {
                class_ = this.db.getClass(class_.getClassId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return class_.toString();
        } else {
            return "{\"updated\": " +
                    retVal +
                    "}";
        }
    }

    public String putGroup(String uri, String msg){
        Group group = this.parser.parseStringToGroupWithId(new JSONObject(msg));
        int class_id = Integer.parseInt(uri);
        int retVal = this.updateGroup(group, class_id);
        if(retVal>0){
            return group.toString();
        } else {
            return "{\"updated\": " +
                    retVal +
                    "}";
        }
    }

    public String putUserSchedule(String msg){
        UserSchedule us = this.parser.parseStringToUS(new JSONObject(msg));
        int retVal = this.updateUserSchedule(us);
        if(retVal>0){
            return this.adminGet.getUsersForSchedule(Integer.toString(us.getSchedule_id()));
        }
        return "{\"updated\": " +
                retVal +
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

    public String putFoS(String msg){
        FieldOfStudy fos = this.parser.parseStringToFoSWithId(new JSONObject(msg));
        int retVal = this.updateFoS(fos);
        if(retVal>0){
            return fos.toString();
        }
        return "{\"updated\": " +
                 retVal +
                "}";
    }

    public String putUserField(String msg){
        UserField uf = this.parser.parseStringToUF(new JSONObject(msg));
        int retVal = this.updateUserField(uf);
        if(retVal>0){
            return this.adminGet.getUsersForFoS(Integer.toString(uf.getField_id()));
        }
        return "{\"updated\": " +
                retVal +
                "}";
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

    public int updateClass(Class_obj class_, int schedule_id){
        try {
            return this.db.updateClass(class_, schedule_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateGroup(Group group, int class_id){
        try {
            return this.db.updateGroup(group, class_id);
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

    public int updateFoS(FieldOfStudy fos){
        try {
            return this.db.updateFoS(fos);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateUserField(UserField uf){
        try {
            return this.db.updateUserField(uf);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
