package Model;

import java.util.Arrays;

public class Class {
    int classId;
    String name;
    Group[] groups;

    public Class(int classId, String name, Group[] groups){
        this.classId = classId;
        this.name = name;
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "{\"classId\":" + classId +
                ", \"name\":\"" + name + '\'' +
                "\", \"groups\":" + Arrays.toString(groups) +
                "}";
    }
}
