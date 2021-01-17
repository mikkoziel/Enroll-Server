package GeneticAlgorithm;

import java.time.LocalTime;

public class DayAndHouDetails {
    int day;
    LocalTime start;
    LocalTime end;

    public DayAndHouDetails(int day, LocalTime start, LocalTime end) {
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LocalTime getStartHour() {
        return start;
    }

    public void setStartHour(LocalTime startHour) {
        this.start = startHour;
    }

    public LocalTime getEndHour() {
        return end;
    }

    public void setEndHour(LocalTime endHour) {
        this.end = endHour;
    }
}
