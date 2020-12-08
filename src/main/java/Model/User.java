package Model;

public class User {
    int userId;
    String name;
    String surname;
    boolean admin;

    public User(int userId, String name, String surname, boolean admin){
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.admin = admin;
    }
}
