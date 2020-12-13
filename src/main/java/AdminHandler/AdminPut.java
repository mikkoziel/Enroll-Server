package AdminHandler;

import DBHandler.DBHandler;
import Model.Schedule;

import java.sql.SQLException;

public class AdminPut {
    DBHandler db;

    public AdminPut(DBHandler db) {
        this.db = db;
    }

    public void putSchedule(Schedule schedule){
        try {
            this.db.updateSchedule(schedule);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putUserSchedule(int user_id, int schedule_id, int admin){
        try {
            this.db.updateUserSchedule(user_id, schedule_id, admin);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
