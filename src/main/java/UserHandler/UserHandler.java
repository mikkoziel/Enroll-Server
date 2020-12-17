package UserHandler;

import DBHandler.DBHandler;

public class UserHandler {
    DBHandler db;
    UserGet userGet;
    UserPost userPost;
    UserPut userPut;
    UserDelete userDelete;

    public UserHandler() {
        this.db = new DBHandler();
        this.userGet = new UserGet(this.db);
        this.userPost = new UserPost(this.db);
        this.userPut = new UserPut(this.db);
        this.userDelete = new UserDelete(this.db);
    }

    //-----------GET----------------------------------------------------
    public String getSchedules(int user_id){
        return this.userGet.getSchedules(user_id);
    }

    public String getSchedule(int user_id, String schedule_id) {
        return this.userGet.getSchedule(user_id, schedule_id);
    }

    public String getProfessors(){
        return this.userGet.getProfessors();
    }

    public String getUPForUser(String user_id){
        return this.userGet.getUPForUser(user_id);
    }

    public String getScheduleProfUP(String schedule_id, int user_id){
        return this.userGet.getScheduleProfUP(schedule_id, user_id);
    }
    //----------POST----------------------------------------------------
    public String postUserPreference(String msg) {
        return this.userPost.postUserPreference(msg);
    }

    //----PUT-------------------------------------------------------
    public String putUserPreference(String msg){
        return this.userPut.putUserPreference(msg);
    }


    //----DELETE-------------------------------------------------------
}
