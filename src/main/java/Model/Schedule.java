package Model;

import java.util.ArrayList;

public class Schedule {
    int scheduleID;
    String name;
    int semester;
    String description;
    Status status;
    ArrayList<Class_obj> classes;
    int field_id;

    public Schedule(int scheduleID, String name, int semester, String desc, Status status, ArrayList<Class_obj> classes, int field_id){
        this.scheduleID = scheduleID;
        this.name = name;
        this.semester = semester;
        this.description = desc;
        this.status = status;
        this.classes = classes;
        this.field_id = field_id;
    }

    public Schedule(String name, int semester, String desc, Status status, ArrayList<Class_obj> classes, int field_id){
        this.name = name;
        this.semester = semester;
        this.description = desc;
        this.status = status;
        this.classes = classes;
        this.field_id = field_id;
    }

    public Schedule(int scheduleID, String name, int semester, String desc, Status status, int field_id){
        this.scheduleID = scheduleID;
        this.name = name;
        this.semester = semester;
        this.description = desc;
        this.status = status;
        this.classes = new ArrayList<Class_obj>();
        this.field_id = field_id;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
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

    public ArrayList<Class_obj> getClasses() {
        return classes;
    }

    public void addClass(Class_obj class_obj) {
        this.classes.add(class_obj);
    }

    public int getField_id() {
        return field_id;
    }

    public void setClasses(ArrayList<Class_obj> classes){
        this.classes = classes;
    }
    @Override
    public String toString() {
        return "{\"scheduleID\":" + scheduleID +
                ", \"name\":\"" + name +
                "\", \"semester\":" + semester +
                ", \"description\":\"" + description +
                "\", \"status\":\"" + status.label +
                "\", \"classes\":" + classes.toString() +
                ", \"field_id\":" + field_id +
                "}";
    }
}
