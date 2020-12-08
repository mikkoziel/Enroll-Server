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
        Class class1 = new Class(0,
                "ZTB",
                new Group[]{group1});
        this.schedules = new Schedule[]{
                new Schedule(0,
                        "this_one",
                        2,
                        new Class[]{class1})
        };
    }

    public String getSchedules() {
        return "{\"schedules\":" + Arrays.toString(schedules) + "}";
    }
}
