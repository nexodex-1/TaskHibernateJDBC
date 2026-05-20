package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // Данные для подключения к локальной БД
    private static final String URL = "jdbc:postgresql://localhost:5432/IT_Mentor";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ошибка при установке соединения с БД!");
            e.printStackTrace();
        }
        return connection;
    }
}
