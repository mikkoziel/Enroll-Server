package Model;

public class Professor {
    int professor_id;
    String name;
    String surname;

    public Professor(int professor_id, String name, String surname){
        this.professor_id = professor_id;
        this.name = name;
        this.surname = surname;
    }

    public Professor(String name, String surname){
        this.name = name;
        this.surname = surname;
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

    public void setProfessor_id(int professor_id) {
        this.professor_id = professor_id;
    }

    @Override
    public String toString() {
        return "{\"professor_id\":" + professor_id +
                ", \"name\":\"" + name +
                "\", \"surname\":\"" + surname +
                "\"}";
    }
}
