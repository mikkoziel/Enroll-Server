package Model;

import java.util.Arrays;

public class Schedule {
    int scheduleID;
    String name;
    int semester;
    Class[] classes;

    public Schedule(int scheduleID, String name, int semester, Class[] classes){
        this.scheduleID = scheduleID;
        this.name = name;
        this.semester = semester;
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "{\"scheduleID\":" + scheduleID +
                ", \"name\":\"" + name +
                "\", \"semester\":\"" + semester +
                "\", \"classes\":" + Arrays.toString(classes) +
                "}";
    }
}
