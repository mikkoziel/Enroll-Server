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

    public AdminPut(DBHandler db) {
        this.db = db;
        this.parser = new Parser();
    }

    public String putSchedule(String msg){
        Schedule schedule = this.parser.parseStringToSchedule(msg);
        return "{\"updated\": " +
                this.updateSchedule(schedule) +
                "}";
    }

    public String putUserSchedule(String msg){
        UserSchedule us = this.parser.parseStringToUS(new JSONObject(msg));
        int admin = us.isAdmin() ? 1 : 0;
        return "{\"updated\": " +
                this.updateUserSchedule(us.getUser_id(), us.getSchedule_int(), admin) +
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

    public int updateUserSchedule(int user_id, int schedule_id, int admin){
        try {
            return this.db.updateUserSchedule(user_id, schedule_id, admin);
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
