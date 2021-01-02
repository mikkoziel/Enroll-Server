package DBHandler;

import Model.UserField;
import Model.UserSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUserField {
    Connection conn;

    public DbUserField(Connection conn){
        this.conn = conn;
    }

    public int addUserField(UserField uf) throws SQLException {
        String SQL_INSERT = "INSERT INTO UserField(user_id, field_id, type)" +
                " VALUES (?, ?, ?)";

        PreparedStatement statement = this.conn.prepareStatement(SQL_INSERT);

        statement.setInt(1, uf.getUser_id());
        statement.setInt(2, uf.getField_id());
        statement.setString(3, uf.getType().label);

        int i = statement.executeUpdate();
        System.out.println(i+ " records inserted");
        return i;
    }

    public int updateUserField(UserField uf) throws SQLException {
        String SQL_UPDATE = "UPDATE UserField(type)" +
                " VALUES (?) WHERE user_id=? AND field_id=?";

        PreparedStatement statement = this.conn.prepareStatement(SQL_UPDATE);

        statement.setString(1, uf.getType().label);
        statement.setInt(2, uf.getUser_id());
        statement.setInt(3, uf.getField_id());

        int i = statement.executeUpdate();
        System.out.println(i+ " records updated");
        return i;
    }
}
