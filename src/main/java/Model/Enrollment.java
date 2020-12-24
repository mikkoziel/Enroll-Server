package Model;

import java.util.Date;

public class Enrollment {
    private int enroll_id;
    private int schedule_id;
    private Date startDate;
    private Date endDate;

    public Enrollment(int enroll_id, int schedule_id, Date startDate, Date endDate){
        this.enroll_id = enroll_id;
        this.schedule_id = schedule_id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getEnroll_id() {
        return enroll_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public java.sql.Date getStartDateSQL() {
        return new java.sql.Date(startDate.getTime());
    }

    public java.sql.Date getEndDateSQL() {
        return new java.sql.Date(endDate.getTime());
    }

    @Override
    public String toString() {
        return "{\"enroll_id\": " + enroll_id +
                ", \"schedule_id\": " + schedule_id +
                ", \"startDate\": \"" + startDate +
                "\", \"endDate\": \"" + endDate +
                "\"}";
    }
}
