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
        this.adminPost = new AdminPost(this.db);
        this.adminPut = new AdminPut(this.db);
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

    //----UPDATE-------------------------------------------------------
    public String putSchedule(String msg){
        return this.adminPut.putSchedule(msg);
    }

    public String putUserSchedule(String msg){
        return this.adminPut.putUserSchedule(msg);
    }

    //----DELETE-------------------------------------------------------
    public String deleteSchedule(String uri){
        return "{\"deleted\": " +
                this.adminDelete.deleteSchedule(uri) +
                "}";
    }
}
