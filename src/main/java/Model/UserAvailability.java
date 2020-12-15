package Model;

import java.time.LocalTime;

public class UserAvailability {
    private int user_id;
    private int day;
    private LocalTime start;
    private LocalTime end;
    private String reason;
    private boolean accepted;

    public UserAvailability(int user_id, int day, LocalTime start, LocalTime end, String reason, boolean accepted){
        this.user_id = user_id;
        this.day = day;
        this.start = start;
        this.end = end;
        this.reason = reason;
        this.accepted = accepted;
    }

    public UserAvailability(int user_id, int day, String start, String end, String reason, boolean accepted){
        this.user_id = user_id;
        this.day = day;
        this.start = LocalTime.parse(start);
        this.end = LocalTime.parse(end);
        this.reason = reason;
        this.accepted = accepted;
    }

    public int getUser_id() {
        return user_id;
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

    public String getReason() {
        return reason;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
