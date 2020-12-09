package Model;

import java.util.ArrayList;

public class Class_obj {
    int classId;
    String name;
    ArrayList<Group> groups;

    public Class_obj(int classId, String name, ArrayList<Group> groups){
        this.classId = classId;
        this.name = name;
        this.groups = groups;
    }

    public Class_obj(String name, ArrayList<Group> groups){
        this.name = name;
        this.groups = groups;
    }

    public Class_obj(int classId, String name){
        this.classId = classId;
        this.name = name;
        this.groups = new ArrayList<Group>();
    }

    public void addGroup(Group group){
        this.groups.add(group);
    }

    public void setGroups(ArrayList<Group> groups){
        this.groups = groups;
    }

    public int getClassId() {
        return classId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    @Override
    public String toString() {
        return "{\"classId\":" + classId +
                ", \"name\":\"" + name +
                "\", \"groups\":" + groups.toString() +
                "}";
    }
}
