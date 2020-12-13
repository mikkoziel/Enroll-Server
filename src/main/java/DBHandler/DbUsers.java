package DBHandler;

import Model.Schedule;
import Model.Status;
import Model.User;

import java.sql.*;
import java.util.ArrayList;

public class DbUsers {
    Connection conn;

    public DbUsers(Connection conn){
        this.conn = conn;
    }

    public ArrayList<User> getUsersForSchedule(int schedule_id) throws SQLException {
        String SQL_SELECT = "SELECT * FROM User WHERE user_id IN " +
                "(SELECT user_id FROM UserSchedule WHERE schedule_id=?)";
        PreparedStatement statement = this.conn.prepareStatement(SQL_SELECT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, schedule_id);

        ResultSet result = statement.executeQuery();
        ArrayList<User> users = new ArrayList<>();

        while (result.next()) {
            users.add(new User(
                    result.getInt("userId"),
                    result.getString("name"),
                    result.getString("surname"),
                    result.getString("password"),
                    result.getString("mail"),
                    result.getBoolean("admin")
            ));
        }
        return users;
    }

    public int addUser(User user) throws SQLException {
        String SQL_INSERT = "INSERT INTO User(name, surname, password, mail, admin)" +
                " VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getMail());
        statement.setBoolean(5, user.isAdmin());

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating schedule failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (int)(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating schedule failed, no ID obtained.");
            }
        }
    }

    public int updateUser(User user) throws SQLException {
        String SQL_UPDATE = "UPDATE User " +
                "name=?, surname=?, password=?, mail=?, admin=? " +
                "WHERE user_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getMail());
        statement.setBoolean(5, user.isAdmin());
        statement.setInt(5, user.getUserId());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }
}
