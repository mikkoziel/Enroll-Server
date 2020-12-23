package AdminHandler;

import DBHandler.DBHandler;
import Model.Schedule;
import Model.User;
import Model.UserSchedule;
import Tools.Parser;
import org.json.JSONObject;

import java.sql.SQLException;

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
}
