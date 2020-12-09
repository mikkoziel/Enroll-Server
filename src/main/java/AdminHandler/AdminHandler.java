package AdminHandler;

import DBHandler.DBHandler;
import Model.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminHandler {
     DBHandler db;

    public AdminHandler() {
    this.db = new DBHandler();
    }

    public String getSchedules(int id){
        ArrayList<Schedule> schedules = null;
        try{
            schedules = this.db.getSchedules(id);
            return "{\"schedules\":" + schedules.toString() + "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
