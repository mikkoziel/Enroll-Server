package Model;

public class UserSchedule {
    private int user_id;
    private int schedule_id;
    private UserType type;

    public UserSchedule(int user_id, int schedule_id, UserType type){
        this.user_id = user_id;
        this.schedule_id = schedule_id;
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public UserType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{\"user_id\": " + user_id +
                ", \"schedule_id\": " + schedule_id +
                ", \"type\": \"" + type.label +
                "\"}";
    }
}
