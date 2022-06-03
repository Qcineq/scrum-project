package pl.coderslab.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil_DB {

    private static final String JDBC_URL="jdbc:mysql://localhost:3306/scrumlab?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private static final String USER="root";
    private static final String PASSWORD="coderslab";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(JDBC_URL,USER,PASSWORD);
    }
}
