package Model;

import java.time.LocalTime;
import java.util.Arrays;

public class Mock {
    Schedule[] schedules;

    public Mock(){
        Group group1 = new Group(0,
                1,
                LocalTime.of(12, 0),
                LocalTime.of(13, 30),
                "Majchrowski");
        Group group2 = new Group(1,
                1,
                LocalTime.of(12, 0),
                LocalTime.of(13, 30),
                "Majchrowski");
        Class class1 = new Class(0,
                "ZTB",
                new Group[]{group1, group2});
        Class class2 = new Class(1,
                "ZPW",
                new Group[]{group1});
        this.schedules = new Schedule[]{
                new Schedule(0,
                        "this_one",
                        2,
                        new Class[]{class1, class2})
        };
    }

    public String getSchedules() {
        return "{\"schedules\":" + Arrays.toString(schedules) + "}";
    }

    public String getSchedule(String i){
        int id = Integer.parseInt(i);
        return this.schedules[id].toString();
    }
}
