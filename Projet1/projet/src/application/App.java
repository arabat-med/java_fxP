package application;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
// import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import application.Person;
import application.update;
import application.delete;
import application.ajout;
import java.sql.*;


public class App extends Application {

    // creer button zone text et zone password dans class pour defenir gloalement
    Button btn = new Button("ajouter");
    Button btn2 = new Button("quitter");
    Button updateButton = new Button("Update");
    TextField text = new TextField();
    PasswordField pass = new PasswordField();
    ToggleGroup groupbut = new ToggleGroup();
    private TableView<Person> tableView;
    private TextField nomField;
    private TextField prenomField;
    private TextField codeMassarField;
    private TextField idField;
    private ObservableList<Person> data = FXCollections.observableArrayList();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void start(Stage primaryStage) {
        connectToDatabase();

        // Création de la table des étudiants
        tableView = new TableView<>();
        tableView.setItems(data);

        // ...

        // Ajouter un événement de clic sur un étudiant dans le TableView

        // vbox ou hbox la maniere d'affichage du conte neur vertical ou horizontal
        connectToDatabase();

        // Création de la table des etudiant
        tableView = new TableView<>();
        tableView.setItems(data);
        // Ajouter un événement de sélection de ligne dans le TableView
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                printStudentInfo(newSelection);
            }
        });

        TableColumn<Person, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Person, String> prenomColumn = new TableColumn<>("Prénom");
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Person, String> codeMassarColumn = new TableColumn<>("CNE");
        codeMassarColumn.setCellValueFactory(new PropertyValueFactory<>("codeMassar"));

        TableColumn<Person, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        tableView.getColumns().addAll(idColumn, nomColumn, prenomColumn, codeMassarColumn);
        // button update

        // creation de la scene
        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(e -> deletePerson());
        VBox root = new VBox(10, tableView, btn, deleteButton, updateButton);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Page Etudiante ENSAO");
        // ajout de la scene au stage
        primaryStage.setScene(scene);

        // affichage de stage
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evnt) {
                evnt.consume();
                fermer();

            }
        });

        // creation de la troisieme scene pour imprimer information de personne
        tableView.setOnMouseClicked(event -> {
            Person selectedPerson = tableView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                printStudentInfo(selectedPerson);

                VBox infoBox = new VBox(20);
                Label idLabel = new Label("ID: " + selectedPerson. getId());
                Label nomLabel = new Label("Nom: " + selectedPerson. getNom());
                Label prenomLabel = new Label("Prénom: " + selectedPerson.getPrenom());
                Label codeMassarLabel = new Label("Code Massar: " + selectedPerson.getCodeMassar());

                infoBox.getChildren().addAll(idLabel, nomLabel, prenomLabel, codeMassarLabel);

                Button btnRetour = new Button("Précédent");
                Button btnQuitter2 = new Button("Quitter");
                Button imprimer = new Button("imprimer");

                VBox buttonBox = new VBox(10, btnRetour, btnQuitter2, imprimer);
                buttonBox.setPadding(new Insets(10));
                buttonBox.setSpacing(10);
                updateButton.setOnAction(e -> updatePerson());
                VBox scene3Layout = new VBox(10, infoBox, buttonBox);
                scene3Layout.setPadding(new Insets(10));
                scene3Layout.setSpacing(10);
                Scene scene3 = new Scene(scene3Layout, 400, 500);
                scene3.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());

                primaryStage.setScene(scene3);
                primaryStage.show();
                primaryStage.setTitle("Ajoutation d'un etudiant");
                imprimer.setOnAction(e -> {
                    print(scene.getRoot());
                });
                btnRetour.setOnAction(e -> {
                    primaryStage.setScene(scene);
                    text.setText("");
                    pass.setText("");
                });

                btnQuitter2.setOnAction(e -> fermer());
            }

        });

        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent evet) {

                nomField = new TextField();
                nomField.setPromptText("Nom");
                prenomField = new TextField();
                prenomField.setPromptText("prenom");
                codeMassarField = new TextField();
                codeMassarField.setPromptText("Code massar");
                idField = new TextField();
                idField.setPromptText("id");

                Button btnretour = new Button("precedente");
                Button btnquitter2 = new Button("Quitter");
                Button btnajouter = new Button("valider");

                VBox inputBox = new VBox(10, idField, nomField, prenomField, codeMassarField,
                        btnretour, btnquitter2, btnajouter);
                inputBox.getStyleClass().add("input-box");
                inputBox.setPadding(new Insets(10));
                inputBox.setSpacing(10);

                // Création de la disposition principale
                VBox root = new VBox(10, inputBox);
                root.setPadding(new Insets(10));
                root.setSpacing(10);
                Scene scene2 = new Scene(root, 400, 500);
                scene2.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
                primaryStage.setScene(scene2);

                primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    // pour ne pas fermer le travaille avant d'enregistrer
                    public void handle(WindowEvent evt) {
                        evt.consume();
                        fermer();
                    }

                });
                btnajouter.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent evnt) {
                        addPerson();
                    }
                });
                btnretour.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent evnt) {
                        primaryStage.setScene(scene);
                        text.setText("");
                        pass.setText("");
                    }
                });
                btnquitter2.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent eve) {
                        fermer();
                    }
                });

            }
        });// this car la class App est devenir handler donc tous les act
           // action ce fait dans la class handle
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                fermer();
            }

        });

        // Chargement des données depuis la base de données
        loadPeople();
    }

    private void updatePerson() {
        Person selectedPerson = tableView.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            // Création de la scène de modification
            TextField nomField = new TextField(selectedPerson.getNom());
            TextField prenomField = new TextField(selectedPerson.getPrenom());
            TextField codeMassarField = new TextField(selectedPerson.getCodeMassar());

            Button updateButton = new Button("Mettre à jour");
            updateButton.setOnAction(e -> {
                selectedPerson.setNom(nomField.getText());
                selectedPerson.setPrenom(prenomField.getText());
                selectedPerson.setCodeMassar(codeMassarField.getText());
                try {
                    updatePersonInDatabase(selectedPerson);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                loadPeople();
                clearFields();
            });

            VBox inputBox = new VBox(10, nomField, prenomField, codeMassarField, updateButton);
            inputBox.setPadding(new Insets(10));
            inputBox.setSpacing(10);

            Scene updateScene = new Scene(inputBox, 400, 300);
            updateScene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
            Stage updateStage = new Stage();
            updateStage.setTitle("Modifier l'étudiant");
            updateStage.setScene(updateScene);
            updateStage.show();
            updateStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                // pour ne pas fermer le travaille avant d'enregistrer
                public void handle(WindowEvent evt) {
                    evt.consume();
                    fermer();
                }

            });
        } else {
            afficherAlerte("Erreur", "Veuillez sélectionner une personne à mettre à jour.");
        }

    }

    private void updatePersonInDatabase(Person person) throws SQLException {
        String query = "UPDATE student SET Nom=?, Prenom=?, Code_massar=? WHERE ID=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, person.getNom());
        statement.setString(2, person.getPrenom());
        statement.setString(3, person.getCodeMassar());
        statement.setInt(4, person.getId());
        statement.executeUpdate();
        statement.close();
    }

    private void print(Node scene3) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean success = printerJob.showPrintDialog(null);
            if (success) {
                boolean printed = printerJob.printPage(scene3);
                if (printed) {
                    printerJob.endJob();
                }
            }
        }
    }

    private void printStudentInfo(Person person) {
        String id = String.valueOf(person.getId());
        String nom = person.getNom();
        String prenom = person.getPrenom();
        String codeMassar = person.getCodeMassar();

    }

    public void fermer() {
        Alert confirmer = new Alert(Alert.AlertType.CONFIRMATION);
        confirmer.setTitle("confirmation de fermer");
        confirmer.setContentText("voulez vous vrais quitter ?");
        confirmer.setHeaderText(null);
        confirmer.setGraphic(null);
        confirmer.getButtonTypes().removeAll(ButtonType.CANCEL, ButtonType.OK);
        ButtonType btnOUI = new ButtonType("Oui");
        ButtonType btnNON = new ButtonType("Non");
        confirmer.getButtonTypes().addAll(btnOUI, btnNON);
        Optional<ButtonType> resulta = confirmer.showAndWait();
        if (resulta.get() == btnOUI) {
            System.exit(0);
        }

    }

    private void connectToDatabase() {
        try {
            // Modifier les informations de connexion en fonction de votre base de données
            String url = "jdbc:mysql://localhost:3306/student";
            String username = "root";
            String password = "T#9758@qlph";

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPeople() {
        data.clear();

        try {
            resultSet = statement.executeQuery("SELECT * FROM student");

            while (resultSet.next()) {
                String nom = resultSet.getString("Nom");
                String prenom = resultSet.getString("Prenom");
                String codeMassar = resultSet.getString("Code_massar");
                int id = resultSet.getInt("ID");

                Person personne = new Person(id, nom, prenom, codeMassar);
                data.add(personne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addPerson() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String codeMassar = codeMassarField.getText();
        int id = Integer.parseInt(idField.getText());

        try {
            Person newPerson = new Person(id, nom, prenom, codeMassar);
            ajout.addPerson(connection, newPerson);
            loadPeople();
            clearFields();
            loadPeople();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deletePerson() {
        Person selectedPerson = tableView.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            try {
                delete.deletePerson(connection, selectedPerson);
                data.remove(selectedPerson);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            afficherAlerte("Erreur", "Veuillez sélectionner une personne à supprimer.");
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        codeMassarField.clear();
        idField.clear();
        tableView.getSelectionModel().clearSelection();
    }

    public static void main(String[] args) {
        launch(args);
    }

}