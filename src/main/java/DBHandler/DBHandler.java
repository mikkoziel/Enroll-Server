package DBHandler;

import Model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBHandler {
    private Connection conn;
    private String url;
    private String user;
    private String password;
    private String driverClass;

    private DbSchedules dbSchedules;
    private DbClasses dbClasses;
    private DbGroups dbGroups;
    private DbProfessors dbProfessors;
    private DbUserSchedule dbUserSchedule;
    private DbUserPreference dbUserPreference;
    private DbUsers dbUsers;

    public DBHandler() {
        this.url = "jdbc:mysql://mysql.agh.edu.pl:3306" +
                "/" +
                "mkoziel1"; // +
//                "?characterEncoding=utf8";
        this.user = "mkoziel1";
        this.password = "NNY0J0kYBP6TmSUi";
        this.driverClass = "com.mysql.cj.jdbc.Driver";

        this.connectToDB();

        this.dbUserSchedule = new DbUserSchedule(this.conn);
        this.dbGroups = new DbGroups(this.conn);
        this.dbClasses = new DbClasses(this.conn, this.dbGroups);
        this.dbSchedules = new DbSchedules(this.conn, this.dbClasses, this.dbUserSchedule);
        this.dbProfessors = new DbProfessors(this.conn);
        this.dbUserPreference = new DbUserPreference(this.conn);
        this.dbUsers = new DbUsers(this.conn);
    }

    public void connectToDB(){
        try {
            Class.forName(this.driverClass);
            this.conn = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //----SCHEDULES-------------------------------------------------------
    public ArrayList<Schedule> getSchedulesUser(int user_id) throws SQLException {
        return this.dbSchedules.getSchedulesUser(user_id);
    }

    public ArrayList<Schedule> getSchedulesAdmin(int admin_id) throws SQLException {
        return this.dbSchedules.getSchedulesAdmin(admin_id);
    }

    //----SCHEDULE-------------------------------------------------------
    public long addSchedule(int admin_id, Schedule schedule) throws SQLException {
        return this.dbSchedules.addSchedule(admin_id, schedule);
    }

    public Schedule getScheduleUser(int user_id, int schedule_id) throws SQLException {
        return this.dbSchedules.getScheduleUser(user_id, schedule_id);
    }

    public Schedule getScheduleAdmin(int user_id, int schedule_id) throws SQLException {
        return this.dbSchedules.getScheduleAdmin(user_id, schedule_id);
    }

    public int updateSchedule(Schedule schedule) throws SQLException {
        return this.dbSchedules.updateSchedule(schedule);
    }

    public int deleteSchedule(int schedule_id) throws SQLException {
        return this.dbSchedules.deleteSchedule(schedule_id);
    }

    //----CLASSES-------------------------------------------------------
    public ArrayList<Class_obj> getClasses(int schedule_id) throws SQLException {
        return this.dbClasses.getClasses(schedule_id);
    }

    public long addClass(Class_obj class_, int schedule_id) throws SQLException {
        return this.dbClasses.addClass(class_, schedule_id);
    }

    public int deleteClass(int class_id) throws SQLException {
        return this.dbClasses.deleteClass(class_id);
    }

    //----GROUPS-------------------------------------------------------
    public ArrayList<Group> getGroups(int class_id) throws SQLException {
        return this.dbGroups.getGroups(class_id);
    }

    public long addGroup(Group group, int class_id) throws SQLException {
        return this.dbGroups.addGroup(group, class_id);
    }

    public int deleteGroup(int group_id) throws SQLException {
        return this.dbGroups.deleteGroup(group_id);
    }

    //----PROFESSORS-------------------------------------------------------
    public ArrayList<Professor> getProfessors() throws SQLException {
        return this.dbProfessors.getProfessors();
    }

    public int addProfessor(Professor prof) throws SQLException{
        return this.dbProfessors.addProfessor(prof);
    }

    //----USER SCHEDULE-------------------------------------------------------
    public int addUserSchedule(int user_id, int schedule_id, int admin) throws SQLException {
        return this.dbUserSchedule.addUserSchedule(user_id, schedule_id, admin);
    }

    public int updateUserSchedule(int user_id, int schedule_id, int admin) throws SQLException {
        return this.dbUserSchedule.updateUserSchedule(user_id, schedule_id, admin);
    }

    //----USER PREFERENCE-------------------------------------------------------
    public int addUserPreference(UserPreference up) throws SQLException {
        return this.dbUserPreference.addUserPreference(up);
    }

    public int updateUserPreference(UserPreference up) throws SQLException {
        return this.dbUserPreference.updateUserPreference(up);
    }

    public ArrayList<UserPreference> getUPForSchedule(int schedule_id) throws SQLException {
        return this.dbUserPreference.getUPForSchedule(schedule_id);
    }

    public ArrayList<UserPreference> getUPForGroup(int group_id) throws SQLException {
        return this.dbUserPreference.getUPForGroup(group_id);
    }

    public ArrayList<UserPreference> getUPForUser(int user_id) throws SQLException {
        return this.dbUserPreference.getUPForUser(user_id);
    }

    //----USERS --------------------------------------------------------------
    public ArrayList<User> getUsersForSchedule(int schedule_id)  throws SQLException {
        return this.dbUsers.getUsersForSchedule(schedule_id);
    }

    public int addUser(User user) throws SQLException {
        return this.dbUsers.addUser(user);
    }

    public int updateUser(User user) throws SQLException {
        return this.dbUsers.updateUser(user);
    }


}
