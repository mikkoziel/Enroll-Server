package UserHandler;

import DBHandler.DBHandler;
import Model.UserPreference;
import Tools.Parser;
import org.json.JSONObject;

import java.sql.SQLException;

public class UserPost {
    DBHandler db;
    Parser parser;

    public UserPost(DBHandler db) {
        this.db = db;
        this.parser = new Parser();
    }

    public String postUserPreference(String msg) {
        UserPreference up = this.parser.parseStringToUP(new JSONObject(msg));
        return "{\"added\": " +
                this.addUserPreference(up) +
                "}";
    }

    //-------ADD--------------------------------------------------------
    public int addUserPreference(UserPreference up){
        try {
            return this.db.addUserPreference(up);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
