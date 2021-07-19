package repository.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JdbcConnection {

    private static Connection connection;
    private static Statement statement;

    public static synchronized Statement getConnection() {

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (connection == null) {

            try {
                Class.forName(properties.getProperty("driver"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                 connection = DriverManager.getConnection(properties.getProperty("url"),
                         properties.getProperty("username"), properties.getProperty("password"));

                 connection.createStatement(
                         ResultSet.TYPE_SCROLL_SENSITIVE,
                         ResultSet.CONCUR_UPDATABLE
                 );

                  statement = connection.createStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return statement;
    }

}
