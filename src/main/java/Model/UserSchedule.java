package Model;

public class UserSchedule {
    private int user_id;
    private int schedule_id;
    private boolean admin;

    public UserSchedule(int user_id, int schedule_id, boolean admin){
        this.user_id = user_id;
        this.schedule_id = schedule_id;
        this.admin = admin;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return "{\"user_id\": " + user_id +
                ", \"schedule_id\": " + schedule_id +
                ", \"admin\": " + admin +
                '}';
    }
}
