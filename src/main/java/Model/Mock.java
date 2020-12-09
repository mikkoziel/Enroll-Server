package Model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Mock {
    Schedule[] schedules;

    public Mock(){
        Group group1 = new Group(0,
                1,
                LocalTime.of(12, 0),
                LocalTime.of(13, 30),
                1);
        Group group2 = new Group(1,
                1,
                LocalTime.of(12, 0),
                LocalTime.of(13, 30),
                1);
        Class_obj class1 = new Class_obj(0,
                "ZTB",
                new ArrayList<Group>());
        class1.addGroup(group1);
        class1.addGroup(group2);
        Class_obj class2 = new Class_obj(1,
                "ZPW",
                new ArrayList<Group>());
        class2.addGroup(group1);
        this.schedules = new Schedule[]{
                new Schedule(0,
                        "this_one",
                        2,
                        "Something Something",
                        Status.CREATED,
                        new ArrayList<Class_obj>())
//                        new Class[]{class1, class2})
        };
        this.schedules[0].addClass(class1);
        this.schedules[0].addClass(class2);
    }

    public String getSchedules() {
        return "{\"schedules\":" + Arrays.toString(schedules) + "}";
    }

    public String getSchedule(String i){
        int id = Integer.parseInt(i);
        return this.schedules[id].toString();
    }
}
