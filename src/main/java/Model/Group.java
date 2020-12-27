package Model;

import java.time.LocalTime;

public class Group {
    private int groupId;
    private int day;
    private LocalTime start;
    private LocalTime end;
    private int professor_id;
    private String type;

    public Group(int groupId, int day, LocalTime start, LocalTime end, int professor_id, String type){
        this.groupId = groupId;
        this.day = day;
        this.start = start;
        this.end = end;
        this.professor_id = professor_id;
        this.type = type;
    }

    public Group(int day, LocalTime start, LocalTime end, int professor_id, String type){
        this.day = day;
        this.start = start;
        this.end = end;
        this.professor_id = professor_id;
        this.type = type;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{\"groupId\":" + groupId +
                ", \"day\":" + day +
                ", \"start\":\"" + start.toString() +
                "\", \"end\":\"" + end.toString() +
                "\", \"professor_id\":" + professor_id +
                ", \"type\":\"" + type +
                "\"}";
    }
}
