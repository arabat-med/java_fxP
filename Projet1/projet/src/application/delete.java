package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.delete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class delete {
    public static void deletePerson(Connection connection, Person person) throws SQLException {
        String query = "DELETE FROM student WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, person.getId());
        preparedStatement.executeUpdate();
    }
}
