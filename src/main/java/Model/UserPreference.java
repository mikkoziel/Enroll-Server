package Model;

public class UserPreference {
    private int user_id;
    private int group_id;
    private int points;

    public UserPreference(int user_id, int group_id, int points){
        this.user_id = user_id;
        this.group_id = group_id;
        this.points = points;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "{\"user_id\": " + user_id +
                ", \"group_id\": " + group_id +
                ", \"points\": " + points +
                '}';
    }
}

