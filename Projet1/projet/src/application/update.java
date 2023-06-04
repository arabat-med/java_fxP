package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.update;

public class update {
    public static void updatePerson(Connection connection, Person person) throws SQLException {
        String query = "UPDATE student SET Nom=?, Prenom=?, Code_massar=? WHERE ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, person.getNom());
        preparedStatement.setString(2, person.getPrenom());
        preparedStatement.setString(3, person.getCodeMassar());
        preparedStatement.setInt(4, person.getId());
        int rowsUpdated = preparedStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Mise à jour réussie.");
        }
    }
}