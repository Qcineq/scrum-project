package pl.coderslab.dao;

import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecipePlanDao {
    private static final String READ_LAST_PLAN_FOR_ADMIN_ID = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description, recipe.id as recipe_id, plan_id\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?)\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";

    public static Map<String, List<RecipePlan>> getLastPlanDetails(int admin_id){
        Map<String, List<RecipePlan>> recipePlanForEveryDay = new LinkedHashMap<>();
        List<RecipePlan> recipePlanList = new ArrayList<>();
        String actualDay = "";
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(READ_LAST_PLAN_FOR_ADMIN_ID);
            statement.setInt(1, admin_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                if(!rs.getString("day_name").equals(actualDay)){
                    recipePlanList = new ArrayList<RecipePlan>();
                }
                actualDay = rs.getString("day_name");
                RecipePlan recipePlan = new RecipePlan();
                recipePlan.setDayName(rs.getString("day_name"));
                recipePlan.setMealName(rs.getString("meal_name"));
                recipePlan.setRecipeName(rs.getString("recipe_name"));
                recipePlan.setRecipeDescription("recipe_description");
                recipePlan.setRecipeId(rs.getInt("recipe_id"));
                recipePlan.setPlanId(rs.getInt("plan_id"));
                recipePlanList.add(recipePlan);
                recipePlanForEveryDay.put(recipePlan.getDayName(), recipePlanList);

            }
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return recipePlanForEveryDay;
    }

}
