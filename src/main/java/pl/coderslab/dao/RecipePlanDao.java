package pl.coderslab.dao;

import pl.coderslab.model.RecipePlan;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
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

    private static final String READ_PLAN_FOR_PLAN_ID = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description, recipe.id as recipe_id, plan_id\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE plan_id = ?\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";

    private static final String INSERT_RECIPE_INTO_PLAN = "INSERT INTO recipe_plan (recipe_id, meal_name, display_order, day_name_id, plan_id) values (?, ?, ?, ?, ?);";


    public static void putRecipeIntoPlan(int recipe_id, String meal_name, int display_order, int day_name_id, int plan_id){
        try(Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_RECIPE_INTO_PLAN, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, recipe_id);
            statement.setString(2, meal_name);
            statement.setInt(3, display_order);
            statement.setInt(4, day_name_id);
            statement.setInt(5, plan_id);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            RecipePlan recipePlan = new RecipePlan();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


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

    public static Map<String, List<RecipePlan>> getPlanDetails(int plan_id){
        Map<String, List<RecipePlan>> recipePlanForEveryDay = new LinkedHashMap<>();
        List<RecipePlan> recipePlanList = new ArrayList<>();
        String actualDay = "";
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(READ_PLAN_FOR_PLAN_ID);
            statement.setInt(1, plan_id);
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
