package Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Schedule {
    int scheduleID;
    String name;
    int semester;
    String description;
    Status status;
    ArrayList<Class> classes;

    public Schedule(int scheduleID, String name, int semester, String desc, Status status, ArrayList<Class> classes){
        this.scheduleID = scheduleID;
        this.name = name;
        this.semester = semester;
        this.description = desc;
        this.status = status;
        this.classes = classes;
    }

    public Schedule(String name, int semester, String desc, Status status, ArrayList<Class> classes){
        this.name = name;
        this.semester = semester;
        this.description = desc;
        this.status = status;
        this.classes = classes;
    }

    public Schedule(int scheduleID, String name, int semester, String desc, Status status){
        this.scheduleID = scheduleID;
        this.name = name;
        this.semester = semester;
        this.description = desc;
        this.status = status;
        this.classes = new ArrayList<Class>();
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public String getName() {
        return name;
    }

    public int getSemester() {
        return semester;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void addClass(Class class_) {
        this.classes.add(class_);
    }

    @Override
    public String toString() {
        return "{\"scheduleID\":" + scheduleID +
                ", \"name\":\"" + name +
                "\", \"semester\":\"" + semester +
                "\", \"description\":\"" + description +
                "\", \"status\":\"" + status.label +
                "\", \"classes\":" + classes.toString() +
                "}";
    }
}
