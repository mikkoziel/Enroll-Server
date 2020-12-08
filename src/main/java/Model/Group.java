package Model;

import java.time.LocalTime;

public class Group {
    int groupId;
    int day;
    LocalTime start;
    LocalTime end;
    String professor;

    public Group(int groupId, int day, LocalTime start, LocalTime end, String professor){
        this.groupId = groupId;
        this.day = day;
        this.start = start;
        this.end = end;
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "{\"groupId\":" + groupId +
                ", \"day\":" + day +
                ", \"start\":\"" + start.toString() +
                "\", \"end\":\"" + end.toString() +
                "\", \"professor\":\"" + professor +
                "\"}";
    }
}
