package AdminHandler;

import DBHandler.DBHandler;

public class AdminHandler {
     DBHandler db;
     AdminGet adminGet;
     AdminPost adminPost;
     AdminPut adminPut;
     AdminDelete adminDelete;

    public AdminHandler() {
        this.db = new DBHandler();
        this.adminGet = new AdminGet(this.db);
        this.adminPost = new AdminPost(this.db, this.adminGet);
        this.adminPut = new AdminPut(this.db, this.adminGet);
        this.adminDelete = new AdminDelete(this.db);
    }

    //-----------GET----------------------------------------------------
    public String getSchedules(int admin_id){
        return this.adminGet.getSchedules(admin_id);
    }

    public String getSchedule(int admin_id, String schedule_id) {
        return this.adminGet.getSchedule(admin_id, schedule_id);
    }

    public String getProfessors(){
        return this.adminGet.getProfessors();
    }

    public String getUsersForSchedule(String schedule_id){
        return this.adminGet.getUsersForSchedule(schedule_id);
    }

    public String getScheduleProfUS(String schedule_id, int user_id){
        return this.adminGet.getScheduleProfUS(schedule_id, user_id);
    }

    public String getUsers(){
        return this.adminGet.getUsers();
    }
    //----------POST----------------------------------------------------
    public String postSchedule(String msg, int id){
        return this.adminPost.postSchedule(msg, id);
    }

    public String postClass(String uri, String msg, int id){
        return this.adminPost.postClass(uri, msg, id);
    }

    public String postGroup(String uri, String msg, int id){
        return this.adminPost.postGroup(uri, msg, id);
    }

    public String postUserSchedule(String msg){
        return this.adminPost.postUserSchedule(msg);
    }

    public String postProfessor(String msg){
        return this.adminPost.postProfessor(msg);
    }

    public String postUserPreference(String msg) {
        return this.adminPost.postUserPreference(msg);
    }

    public String postUser(String msg) {
        return  this.adminPost.postUser(msg);
    }

    //----PUT-------------------------------------------------------
    public String putSchedule(String msg, int id){
        return this.adminPut.putSchedule(msg, id);
    }

    public String putUserSchedule(String msg){
        return this.adminPut.putUserSchedule(msg);
    }

    public String putUser(String msg){
        return this.adminPut.putUser(msg);
    }

    public String putEnroll(String msg, int id){
        return this.adminPut.putEnroll(msg, id);
    }

    //----DELETE-------------------------------------------------------
    public String deleteSchedule(String uri){
        return this.adminDelete.deleteSchedule(uri);
    }

    public String deleteClass(String uri){
        return this.adminDelete.deleteClass(uri);
    }

    public String deleteGroup(String uri){
        return this.adminDelete.deleteGroup(uri);
    }
}
