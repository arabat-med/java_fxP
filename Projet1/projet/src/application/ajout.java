package application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.ajout;

public class ajout {
    public static void addPerson(Connection connection, Person person) throws SQLException {
        String query = "INSERT INTO student (Nom, Prenom, Code_massar, ID) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, person.getNom());
        preparedStatement.setString(2, person.getPrenom());
        preparedStatement.setString(3, person.getCodeMassar());
        preparedStatement.setInt(4, person.getId());
        preparedStatement.executeUpdate();
    }
}

