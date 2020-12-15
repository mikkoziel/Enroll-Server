package UserHandler;

import DBHandler.DBHandler;
import Model.UserPreference;
import Model.UserSchedule;
import Tools.Parser;
import org.json.JSONObject;

import java.sql.SQLException;

public class UserPut {
    DBHandler db;
    Parser parser;

    public UserPut(DBHandler db) {
        this.db = db;
        this.parser = new Parser();
    }

    public String putUserPreference(String msg){
        UserPreference up = this.parser.parseStringToUP(new JSONObject(msg));
        return "{\"updated\": " +
                this.updateUserPreference(up) +
                "}";
    }

    //-------UPDATE--------------------------------------------------------
    public int updateUserPreference(UserPreference up){
        try {
            return this.db.updateUserPreference(up);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
