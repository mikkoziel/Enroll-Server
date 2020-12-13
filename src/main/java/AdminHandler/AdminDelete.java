package AdminHandler;

import DBHandler.DBHandler;

import java.sql.SQLException;

public class AdminDelete {
    DBHandler db;

    public AdminDelete(DBHandler db) {
        this.db = db;
    }

    public int deleteSchedule(String uri){
        int schedule_id = Integer.parseInt(uri.replace("schedules/", ""));
        try {
            return this.db.deleteSchedule(schedule_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
