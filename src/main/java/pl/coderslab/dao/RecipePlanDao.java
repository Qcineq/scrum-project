package pl.coderslab.dao;

import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipePlanDao {
    private static final String READ_LAST_PLAN_FOR_ADMIN_ID = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?)\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";

    public static List<RecipePlan> getLastPlanDetails(int admin_id){
        List<RecipePlan> recipePlanList = new ArrayList<>();
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(READ_LAST_PLAN_FOR_ADMIN_ID);
            statement.setInt(1, admin_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                RecipePlan recipePlan = new RecipePlan();
                recipePlan.setDayName(rs.getString("day_name"));
                recipePlan.setMealName(rs.getString("meal_name"));
                recipePlan.setRecipeName(rs.getString("recipe_name"));
                recipePlan.setRecipeDescription("recipe_description");
                recipePlanList.add(recipePlan);
            }
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return recipePlanList;
    }

}
