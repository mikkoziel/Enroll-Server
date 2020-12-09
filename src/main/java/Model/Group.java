package Model;

import java.time.LocalTime;

public class Group {
    int groupId;
    int day;
    LocalTime start;
    LocalTime end;
    int professor_id;

    public Group(int groupId, int day, LocalTime start, LocalTime end, int professor_id){
        this.groupId = groupId;
        this.day = day;
        this.start = start;
        this.end = end;
        this.professor_id = professor_id;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getDay() {
        return day;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public int getProfessor_id() {
        return professor_id;
    }

    @Override
    public String toString() {
        return "{\"groupId\":" + groupId +
                ", \"day\":" + day +
                ", \"start\":\"" + start.toString() +
                "\", \"end\":\"" + end.toString() +
                "\", \"professor_id\":\"" + professor_id +
                "\"}";
    }
}
