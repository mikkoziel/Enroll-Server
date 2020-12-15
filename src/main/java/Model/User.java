package Model;

public class User {
    private int userId;
    private String name;
    private String surname;
    private String password;
    private String mail;
    private boolean admin;

    public User(int userId, String name, String surname, String mail, boolean admin){
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.admin = admin;
    }

    public User(int userId, String name, String surname, String password, String mail, boolean admin){
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.mail = mail;
        this.admin = admin;
    }

    public User(String name, String surname, String password, String mail, boolean admin){
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.mail = mail;
        this.admin = admin;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "{\"user_id\": " + userId +
                ", \"name\": \"" + name +
                "\", \"surname\": \"" + surname +
                "\", \"password\": \"" + password +
                "\", \"mail\": \"" + mail +
                "\", \"admin\": \"" + admin +
                "\"}";
    }
}
