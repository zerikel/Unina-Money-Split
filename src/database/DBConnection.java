package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    private static final String URL = "jdbc:postgresql://localhost:5432/UninaMoneySplit";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connessione al database riuscita!");
            } catch (SQLException e) {
                System.out.println("Errore di connessione al database!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}