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

    public String getScheduleDetails(String schedule_id, int user_id){
        return this.adminGet.getScheduleDetails(schedule_id, user_id);
    }

    public String getUsers(){
        return this.adminGet.getUsers();
    }

    public String getFieldsForId(int user_id){
        return this.adminGet.getFieldsForId(user_id);
    }

    public String getFieldsSchedules(int user_id){
        return this.adminGet.getFieldsSchedules(user_id);
    }

    public String getFieldsDetails(String uri){
        return this.adminGet.getFieldsDetails(uri);
    }
    //----------POST----------------------------------------------------
    public String postSchedule(String msg, int id){
        return this.adminPost.postSchedule(msg, id);
    }

    public String postClass(String uri, String msg){
        return this.adminPost.postClass(uri, msg);
    }

    public String postGroup(String uri, String msg){
        return this.adminPost.postGroup(uri, msg);
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

    public String postFoS(String msg, int admin_id) {
        return  this.adminPost.postFoS(msg, admin_id);
    }

    public String postUserField(String msg){
        return this.adminPost.postUserField(msg);
    }
    //----PUT-------------------------------------------------------
    public String putSchedule(String msg, int id){
        return this.adminPut.putSchedule(msg, id);
    }

    public String putClass(String uri, String msg){
        return this.adminPut.putClass(uri, msg);
    }

    public String putGroup(String uri, String msg){
        return this.adminPut.putGroup(uri, msg);
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

    public String putFoS(String msg){
        return this.adminPut.putFoS(msg);
    }

    public String putUserField(String msg){
        return this.adminPut.putUserField(msg);
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

    public String deleteFoS(String uri){
        return this.adminDelete.deleteFoS(uri);
    }

    public String deleteUserField(String uri, int id){
        return this.adminDelete.deleteUserField(uri, id);
    }

    public String deleteUserSchedule(String uri, int id){
        return this.adminDelete.deleteUserSchedule(uri, id);
    }
}
