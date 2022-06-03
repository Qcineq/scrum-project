package pl.coderslab.web;

import pl.coderslab.utils.DbUtil_DB;

import java.sql.*;

public class QuantityRecipe {

    public static void main(String[] args) {

        String sql= "SELECT MAX(id) FROM recipe ";

        try (Connection connection = DbUtil_DB.getConnection()){
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
