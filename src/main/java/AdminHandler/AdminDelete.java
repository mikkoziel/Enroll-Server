package AdminHandler;

import DBHandler.DBHandler;
import Model.Class_obj;
import Model.Group;

import java.sql.SQLException;

public class AdminDelete {
    DBHandler db;

    public AdminDelete(DBHandler db) {
        this.db = db;
    }

    public String deleteSchedule(String uri){
        int schedule_id = Integer.parseInt(uri.replace("schedules/", ""));
        try {
            return "{\"deleted\": " +
                    this.db.deleteSchedule(schedule_id) +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String deleteClass(String uri){
        int class_id = Integer.parseInt(uri.replace("classes/", ""));
        try {
            Class_obj class_ = this.db.getClass(class_id);
            for(Group group: class_.getGroups()){
                this.db.deleteGroup(group.getGroupId());
            }
            int retVal = this.db.deleteClass(class_id);
            return "{\"deleted\": " +
                     retVal +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String deleteGroup(String uri){
        int group_id = Integer.parseInt(uri.replace("groups/", ""));
        try {
            return "{\"deleted\": " +
                    this.db.deleteGroup(group_id) +
                    "}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
