package pl.coderslab.dao;

import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {

    // ZAPYTANIE SQL
    private static final String FIND_ALL_DAY_NAME_QUERY = "SELECT * FROM day_name;";

    // metoda findAll
    public List<DayName> findAll() {
        List<DayName> dayNameList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAY_NAME_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DayName dayName = new DayName();
                dayName.setId(resultSet.getInt("id"));
                dayName.setName(resultSet.getString("name"));
                dayName.setDisplayOrder(resultSet.getInt("display_order"));
                dayNameList.add(dayName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dayNameList;

    }
}