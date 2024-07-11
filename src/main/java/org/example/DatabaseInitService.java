package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitService {
    public static void main(String[] args) {
        String path = "sql\\init_db.sql";
        try (Connection connection = Database.getInstance().getConnection()) {
            String sqlQuery = readSQLFile(path);
            executeSQLQuery(connection, sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void executeSQLQuery(Connection connection, String sqlQuery) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.executeUpdate();
    }

    private static String readSQLFile(String path) {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            br.lines().forEach(line -> sb.append(line).append("\n"));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
}
