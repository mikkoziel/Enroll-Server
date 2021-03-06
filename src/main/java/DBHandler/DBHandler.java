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
    private DbEnrollment dbEnrollment;
    private DbFieldOfStudy dbFieldOfStudy;
    private DbUserField dbUserField;

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
        this.dbEnrollment = new DbEnrollment(this.conn);
        this.dbFieldOfStudy = new DbFieldOfStudy(this.conn);
        this.dbUserField = new DbUserField(this.conn);
    }

    public void connectToDB() {
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

    public int updateScheduleStatus(int schedule_id, Status status) throws SQLException {
        return this.dbSchedules.updateScheduleStatus(schedule_id, status);
    }

    //----CLASSES-------------------------------------------------------
    public ArrayList<Class_obj> getClasses(int schedule_id) throws SQLException {
        return this.dbClasses.getClasses(schedule_id);
    }

    public Class_obj getClass(int class_id) throws SQLException {
        return this.dbClasses.getClass(class_id);
    }

    public long addClass(Class_obj class_, int schedule_id) throws SQLException {
        return this.dbClasses.addClass(class_, schedule_id);
    }

    public int updateClass(Class_obj class_, int schedule_id) throws SQLException {
        return this.dbClasses.updateClass(class_, schedule_id);
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

    public int updateGroup(Group group, int class_id) throws SQLException {
        return this.dbGroups.updateGroup(group, class_id);
    }

    public int deleteGroup(int group_id) throws SQLException {
        return this.dbGroups.deleteGroup(group_id);
    }

    public ArrayList<Group> getGroupsForSchedule(int schedule_id) throws SQLException {
        return this.dbGroups.getGroupsForSchedule(schedule_id);
    }

    //----PROFESSORS-------------------------------------------------------
    public ArrayList<Professor> getProfessors() throws SQLException {
        return this.dbProfessors.getProfessors();
    }

    public int addProfessor(Professor prof) throws SQLException {
        return this.dbProfessors.addProfessor(prof);
    }

    //----USER SCHEDULE-------------------------------------------------------
    public ArrayList<UserSchedule> getUserSchedule(int schedule_id) throws SQLException {
        return this.dbUserSchedule.getUserSchedule(schedule_id);
    }

    public int addUserSchedule(UserSchedule us) throws SQLException {
        return this.dbUserSchedule.addUserSchedule(us);
    }

    public int updateUserSchedule(UserSchedule us) throws SQLException {
        return this.dbUserSchedule.updateUserSchedule(us);
    }

    public int deleteUserSchedule(UserSchedule us) throws SQLException {
        return this.dbUserSchedule.deleteUserSchedule(us);
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
    public ArrayList<User> getUsersForSchedule(int schedule_id) throws SQLException {
        return this.dbUsers.getUsersForSchedule(schedule_id);
    }

    public ArrayList<User> getRequestsForSchedule(int schedule_id) throws SQLException {
        return this.dbUsers.getRequestsForSchedule(schedule_id);
    }

    public int addUser(User user) throws SQLException {
        return this.dbUsers.addUser(user);
    }

    public int updateUser(User user) throws SQLException {
        return this.dbUsers.updateUser(user);
    }

    public ArrayList<User> getUsers() throws SQLException {
        return this.dbUsers.getUsers();
    }

    public ArrayList<User> getUsersForFoS(int field_id) throws SQLException {
        return this.dbUsers.getUsersForFoS(field_id);
    }

    public ArrayList<User> getRequestsForFoS(int field_id) throws SQLException {
        return this.dbUsers.getRequestsForFoS(field_id);
    }
        //----ENROLLMENT -----------------------------------------------------------
    public int addEnroll(Enrollment enroll) throws SQLException {
        return this.dbEnrollment.addEnroll(enroll);
    }

    public ArrayList<Enrollment> getEnroll(int enroll_id) throws SQLException {
        return this.dbEnrollment.getEnroll(enroll_id);
    }

    //----USER FIELD -----------------------------------------------------------
    public int addUserField(UserField uf) throws SQLException {
        return this.dbUserField.addUserField(uf);
    }

    public int updateUserField(UserField uf) throws SQLException {
        return this.dbUserField.updateUserField(uf);
    }

    public int deleteUserField(UserField uf) throws SQLException {
        return this.dbUserField.deleteUserField(uf);
    }

    //----FIELD OF STUDY -----------------------------------------------------------
    public ArrayList<FieldOfStudy> getFieldsForId(int user_id) throws SQLException {
        return this.dbFieldOfStudy.getFieldsForId(user_id);
    }

    public FieldOfStudy getField(int field_id) throws SQLException {
        return this.dbFieldOfStudy.getField(field_id);
    }

    public int addFoS(FieldOfStudy fos) throws SQLException {
        return this.dbFieldOfStudy.addFoS(fos);
    }

    public int updateFoS(FieldOfStudy fos) throws SQLException {
        return this.dbFieldOfStudy.updateFoS(fos);
    }

    public int deleteFoS(int field_id) throws SQLException {
        return this.dbFieldOfStudy.deleteFoS(field_id);
    }
}
