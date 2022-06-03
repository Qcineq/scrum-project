package pl.coderslab.web;

import pl.coderslab.utils.DbUtil_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuantityPlan {

    public static void main(String[] args) {

        String sql= "SELECT MAX(id) FROM plan ";

        try (Connection connection = DbUtil_DB.getConnection()){
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
