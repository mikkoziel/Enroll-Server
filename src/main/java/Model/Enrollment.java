package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Enrollment(int enroll_id, int schedule_id, String startDate, String endDate) {
        this.enroll_id = enroll_id;
        this.schedule_id = schedule_id;
        try {
            this.startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
            this.endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Enrollment(int schedule_id, String startDate, String endDate) {
        this.schedule_id = schedule_id;
        try {
            this.startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(startDate);
            this.endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public java.sql.Timestamp getStartDateSQL() {
        return new java.sql.Timestamp(startDate.getTime());
    }

    public java.sql.Timestamp getEndDateSQL() {
        return new java.sql.Timestamp(endDate.getTime());
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
