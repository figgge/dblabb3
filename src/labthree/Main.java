package labthree;


import java.sql.*;
import java.util.Scanner;

import static labthree.Menu.displayMainMenu;
import static labthree.Menu.mainMenu;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        mainMenu();
    }

    private static Connection connect() {

        String url = "jdbc:sqlite:src/labb3.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    protected static void selectAll() {
        String sql = "SELECT movieId, movieName, movieRating, movieTopList, movieBudget, genreName " +
                "FROM movie INNER JOIN genre g ON g.genreId = movieGenreId";

        try {
            Statement statement = connect().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.print("ID: " + resultSet.getInt(1) + ", " +
                        "Namn: " + resultSet.getString(2) + ", " +
                        "Betyg: " + resultSet.getInt(3) + ", " +
                        "Plats på topplistan: " + resultSet.getInt(4) + ", " +
                        "Budget: $" + resultSet.getInt(5) + ", " +
                        "Genre ID: " + resultSet.getString(6) + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void insertMovie() {
        String sql = "INSERT INTO movie( " +
                "movieName," +
                "movieRating," +
                "movieTopList," +
                "movieBudget," +
                "movieGenreID)" +
                "VALUES(?,?,?,?,?)";

        try {
            System.out.println("Lägga till ny film: ");
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            System.out.print("Filmnamn: ");
            preparedStatement.setString(1, scanner.nextLine());
            System.out.print("Rating: ");
            preparedStatement.setInt(2, Integer.parseInt(scanner.nextLine()));
            System.out.print("Plats på topplistan: ");
            preparedStatement.setInt(3, Integer.parseInt(scanner.nextLine()));
            System.out.print("Budget: ");
            preparedStatement.setInt(4, Integer.parseInt(scanner.nextLine()));
            displayGenre();
            System.out.print("Genre ID: ");
            preparedStatement.setString(5, scanner.nextLine());
            preparedStatement.executeUpdate();
            System.out.println("Du har lagt till en ny film.");
            displayMainMenu();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void updateMovie() {
        String sql = "UPDATE movie SET " +
                "movieName = ? , " +
                "movieRating = ? , " +
                "movieTopList = ? , " +
                "movieBudget =  ? , " +
                "movieGenreID = ? " +
                "WHERE movieId = ?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            System.out.print("Film ID: ");
            preparedStatement.setInt(6, Integer.parseInt(scanner.nextLine()));
            System.out.print("Filmnamn: ");
            preparedStatement.setString(1, scanner.nextLine());
            System.out.print("Rating: ");
            preparedStatement.setInt(2, Integer.parseInt(scanner.nextLine()));
            System.out.print("Plats på topplistan: ");
            preparedStatement.setInt(3, Integer.parseInt(scanner.nextLine()));
            System.out.print("Budget: ");
            preparedStatement.setInt(4, Integer.parseInt(scanner.nextLine()));
            System.out.print("Genre ID: ");
            preparedStatement.setString(5, scanner.nextLine());
            preparedStatement.executeUpdate();
            System.out.println("Film uppdaterad.");
            displayMainMenu();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void deleteMovie() {
        String sql = "DELETE FROM movie WHERE movieID= ?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            System.out.println("Vilken film vill du ta bort?");
            System.out.print("Film ID: ");
            preparedStatement.setInt(1, Integer.parseInt(scanner.nextLine()));
            preparedStatement.executeUpdate();
            System.out.println("Film borttagen.");
            displayMainMenu();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void displayGenre() {
        String sql = "SELECT * FROM genre";

        try {
            Statement statement = connect().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("genreId") + ": " + resultSet.getString("genreName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void insertGenre() {
        String sql = "INSERT INTO genre (genreName) VALUES(?)";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            System.out.print("Lägg till genre.\nNamn: ");
            preparedStatement.setString(1, scanner.nextLine());
            preparedStatement.executeUpdate();
            System.out.println("Genre tillagd");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateGenre() {
        String sql = "UPDATE genre SET genreName = ? WHERE genreId = ?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            displayGenre();
            System.out.println("Uppdatera genre: ");
            System.out.print("Ange genre ID: ");
            preparedStatement.setInt(2, Integer.parseInt(scanner.nextLine()));
            System.out.println("Ange nytt namn: ");
            preparedStatement.setString(1, scanner.nextLine());
            preparedStatement.executeUpdate();
            System.out.println("Genre uppdaterad");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    protected static void deleteGenre() {
        String sql = "DELETE FROM genre WHERE genreId = ?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            displayGenre();
            System.out.println("Ta bort genre:");
            System.out.print("Ange genre ID: ");
            preparedStatement.setInt(1, Integer.parseInt(scanner.nextLine()));
            preparedStatement.executeUpdate();
            System.out.println("Genre borttagen");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void movieCount() {
        String sql = "SELECT COUNT(movieID) AS Filmer FROM movie";

        try {
            Statement statement = connect().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("Antal filmer i databasen: " + resultSet.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void averageMovieBudgetByGenre() {
        String sql = "SELECT AVG(movieBudget), genreName FROM movie INNER JOIN genre g ON genreId = movieGenreId GROUP BY genreName";

        try {
            Statement statement = connect().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("Kategori: " + resultSet.getString("genreName") + ", Medelbudget: $" + resultSet.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected static void selectGenre() {
        String sql = "SELECT genreName, movieName, movieRating FROM movie INNER JOIN genre g ON genreId = movieGenreId WHERE genreName = ?";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            System.out.print("Ange genre: ");
            preparedStatement.setString(1, scanner.nextLine());
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean firstRun = true;


            while (resultSet.next()) {
                if (firstRun)
                    System.out.println("Alla filmer med kategori: " + resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                firstRun = false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
