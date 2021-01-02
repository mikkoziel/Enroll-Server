package Model;

public class UserField {
    private int user_id;
    private int field_id;
    private UserType type;

    public UserField(int user_id, int field_id, UserType type){
        this.user_id = user_id;
        this.field_id = field_id;
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getField_id() {
        return field_id;
    }

    public UserType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{\"user_id\":" + user_id +
                ", \"field_id\":" + field_id +
                ", \"type\":\"" + type.label +
                "\"}";
    }
}
