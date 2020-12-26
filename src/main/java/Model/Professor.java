package Model;

public class Professor {
    int professor_id;
    String name;
    String surname;
    String title;

    public Professor(int professor_id, String name, String surname, String title){
        this.professor_id = professor_id;
        this.name = name;
        this.surname = surname;
        this.title = title;
    }

    public Professor(String name, String surname, String title){
        this.name = name;
        this.surname = surname;
        this.title = title;
    }

    public int getProfessor_id() {
        return professor_id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTitle() {
        return title;
    }

    public void setProfessor_id(int professor_id) {
        this.professor_id = professor_id;
    }

    @Override
    public String toString() {
        return "{\"professor_id\":" + professor_id +
                ", \"name\":\"" + name +
                "\", \"surname\":\"" + surname +
                "\", \"title\":\"" + title +
                "\"}";
    }
}
