package Model;

public class UserSchedule {
    private int user_id;
    private int schedule_int;
    private boolean admin;

    public UserSchedule(int user_id, int schedule_int, boolean admin){
        this.user_id = user_id;
        this.schedule_int = schedule_int;
        this.admin = admin;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getSchedule_int() {
        return schedule_int;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return "{\"user_id\": " + user_id +
                ", \"schedule_int\": " + schedule_int +
                ", \"admin\": " + admin +
                '}';
    }
}
